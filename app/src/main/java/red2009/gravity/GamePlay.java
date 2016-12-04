package red2009.gravity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class GamePlay extends AppCompatActivity {
    private final String DEVICE_ADDRESS="20:16:08:10:44:32";
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");//Serial Port Service ID
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private OutputStream outputStream;
    private InputStream inputStream;
    boolean deviceConnected=false;
    Thread thread;
    byte buffer[];
    boolean stopThread;
    TextView textScore;
    String msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("GAMEPLAY","Started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        textScore = (TextView) findViewById(R.id.textScore);
        String msg = "";

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String gameMode= bundle.getString("EXTRA_SESSION_ID");

//        String gameMode = getIntent().getStringExtra("EXTRA_SESSION_ID");

        if(BTinit())
        {
            if(BTconnect())
            {
                deviceConnected=true;
                Log.d("Connection","Connection Opened!");

                //Send GameMode to Master controller
//                Log.d("Write",gamemode);
//                gamemode.concat("\n");
                try {
                    int lev = 1;
                    outputStream.write(lev);
//                    outputStream.wr
                    Log.d("Write","Sent");
                } catch (IOException e) {
                    Log.d("Write","WRITE GAMEMODE FAILED");
                    e.printStackTrace();
                }

                //Listen for Points
                beginListenForData();
//                onClickSend(view);
            }
            else{ Log.d("Connection","Connection FAILED!");}
        }
    }


    public boolean BTinit()
    {
        boolean found=false;
        BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(),"Device doesnt Support Bluetooth",Toast.LENGTH_SHORT).show();
        }
        if(!bluetoothAdapter.isEnabled())
        {
            Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableAdapter, 0);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
        if(bondedDevices.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Please Pair the Device first",Toast.LENGTH_SHORT).show();
        }
        else
        {
            for (BluetoothDevice iterator : bondedDevices)
            {
                if(iterator.getAddress().equals(DEVICE_ADDRESS))
                {
                    device=iterator;
                    found=true;
                    Log.d("Found","FOUND DEVICE!");
                    break;
                }
                Log.d("Found","NOT FOUND DEVICE!");
            }
        }
        return found;
    }
    public boolean BTconnect()
    {
        if (socket!=null)
        {
            Log.d("BT","Socket NULL");
            try
            {

                socket.close();
            }
            catch (IOException e)
            { Log.d("BT","Bluetooth Disconnect Error");}
        }
        boolean connected=true;
        try {
            socket = device.createRfcommSocketToServiceRecord(PORT_UUID);
            Log.d("BT","Create RF");
            socket.connect();
        } catch (IOException e) {
            Log.d("BT","BT NOT CONNECTED!");
            e.printStackTrace();
            connected=false;
            try {
                socket.close();
            } catch(IOException e2){
                Log.d("BT", "BT FAILED TO DISCONNECT!");
            }
        }

        if(connected)
        {   Log.d("BT","BT CONNECTED!");
            try {
                outputStream=socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream=socket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        return connected;
    }

    void beginListenForData()
    {
//        TextView textScore = (TextView)findViewById(R.id.textScore);
        final Handler handler = new Handler();
        stopThread = false;
        buffer = new byte[1024];
        msg = "";
        Thread thread  = new Thread(new Runnable()
        {
            public void run()
            {
                while(!Thread.currentThread().isInterrupted() && !stopThread)
                {
                    try
                    {
//                        Log.d("READ","new loop");
                        int byteCount = inputStream.available();
                        if(byteCount > 0)
                        {
                            Log.d("READ","Something read!");
                            byte[] rawBytes = new byte[byteCount];
                            inputStream.read(rawBytes);
                            final String string=new String(rawBytes,"ASCII").substring(0,byteCount);
//                            string = string.substring(0);
                            final String delim = new String("p");
//                            Log.d("READ DELIM",delim);
                            Log.d("READ STRING",string);
                            Log.d("READ raw",string.substring(0,1).toString());
                            Log.d("READ DelimB",delim.toString());
                            Log.d("READ Equal?",String.valueOf(string==delim));
                            if (string == delim.toString()) {
                                Log.d("READ","EQUAL P");

                                handler.post(new Runnable() {
                                    public void run() {
                                        Log.d("READ_Arduino", string);
                                        textScore.setText(msg);
                                        msg = "";
                                    }
                                });
                            }
                            else {
                                msg.concat(string);
                                Log.d("READ MSG",msg);
                            }
                        }

                    }
                    catch (IOException ex)
                    {
                        stopThread = true;
                    }
                }
            }
        });

        thread.start();
    }

//    public void onClickSend(View view) {
//        String string = "10";
//        Log.d("Write","HELLO");
//        string.concat("\n");
//        try {
//
//            outputStream.write(string.getBytes());
//        } catch (IOException e) {
//            Log.d("Write","WRITE FAILED");
//            e.printStackTrace();
//        }
//    }

//    public void onClickStop(View view) throws IOException {
//        stopThread = true;
//        outputStream.close();
//        inputStream.close();
//        socket.close();
//        deviceConnected=false;
//        textView.append("\nConnection Closed!\n");
//    }


}
