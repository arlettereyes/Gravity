package red2009.gravity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
    //    private final String DEVICE_NAME="MyBTBee";
//    private final String DEVICE_ADDRESS="20:16:08:10:44:32";
//    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");//Serial Port Service ID
//    private BluetoothDevice device;
//    private BluetoothSocket socket;
//    private OutputStream outputStream;
//    private InputStream inputStream;
//    boolean deviceConnected=false;
//    Thread thread;
//    byte buffer[];
//    boolean stopThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        Button startButton = (Button) findViewById(R.id.startButton);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.gametitle);
        tv.setText(stringFromJNI());

    }
//    public boolean BTinit()
//    {
//        boolean found=false;
//        BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
//        if (bluetoothAdapter == null) {
//            Toast.makeText(getApplicationContext(),"Device doesnt Support Bluetooth",Toast.LENGTH_SHORT).show();
//        }
//        if(!bluetoothAdapter.isEnabled())
//        {
//            Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(enableAdapter, 0);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
//        if(bondedDevices.isEmpty())
//        {
//            Toast.makeText(getApplicationContext(),"Please Pair the Device first",Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
//            for (BluetoothDevice iterator : bondedDevices)
//            {
//                if(iterator.getAddress().equals(DEVICE_ADDRESS))
//                {
//                    device=iterator;
//                    found=true;
//                    Log.d("Found","FOUND DEVICE!");
//                    break;
//                }
//                Log.d("Found","NOT FOUND DEVICE!");
//            }
//        }
//        return found;
//    }
//    public boolean BTconnect()
//    {
//        if (socket!=null)
//        {
//            Log.d("BT","Socket NULL");
//            try
//            {
//
//                socket.close();
//            }
//            catch (IOException e)
//            { Log.d("BT","Bluetooth Disconnect Error");}
//        }
//        boolean connected=true;
//        try {
//            socket = device.createRfcommSocketToServiceRecord(PORT_UUID);
//            socket.connect();
//        } catch (IOException e) {
//            Log.d("BT","BT NOT CONNECTED!");
//            e.printStackTrace();
//            connected=false;
//            try {
//                socket.close();
//            } catch(IOException e2){
//                    Log.d("BT", "BT FAILED TO DISCONNECT!");
//                }
//            }
//
//        if(connected)
//        {   Log.d("BT","BT CONNECTED!");
//            try {
//                outputStream=socket.getOutputStream();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            try {
//                inputStream=socket.getInputStream();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//
//        return connected;
//    }
//
//    void beginListenForData()
//    {
//        final Handler handler = new Handler();
//        stopThread = false;
//        buffer = new byte[1024];
//        Thread thread  = new Thread(new Runnable()
//        {
//            public void run()
//            {
//                while(!Thread.currentThread().isInterrupted() && !stopThread)
//                {
//                    try
//                    {
//                        int byteCount = inputStream.available();
//                        if(byteCount > 0)
//                        {
//                            Log.d("READ","Something read!");
//                            byte[] rawBytes = new byte[byteCount];
//                            inputStream.read(rawBytes);
//                            final String string=new String(rawBytes,"UTF-8");
//                            handler.post(new Runnable() {
//                                public void run()
//                                {
//                                    Log.d("READ_Arduino",string);
//
//                                }
//                            });
//
//                        }
//                    }
//                    catch (IOException ex)
//                    {
//                        stopThread = true;
//                    }
//                }
//            }
//        });
//
//        thread.start();
//    }
//
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
//
////    public void onClickStop(View view) throws IOException {
////        stopThread = true;
////        outputStream.close();
////        inputStream.close();
////        socket.close();
////        deviceConnected=false;
////        textView.append("\nConnection Closed!\n");
////    }


    public void HowtoPlay_Clicked(View view) {
        Intent intent = new Intent(this, Instructions.class);
        startActivity(intent);
    }

        public void startClicked(View view) {
//        if(BTinit())
//        {
//            if(BTconnect())
//            {
//                deviceConnected=true;
//                Log.d("Connection","Connection Opened!");
////                beginListenForData();
////                onClickSend(view);
            Intent intent = new Intent(this, StartClicked_LevelSelection.class);

            startActivity(intent);
        }

//            }
//            Log.d("Connection","Connection FAILED!");
//
//
//        }

//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

}