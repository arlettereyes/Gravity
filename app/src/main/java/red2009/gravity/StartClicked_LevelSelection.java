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
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class StartClicked_LevelSelection extends AppCompatActivity {
    RadioButton radio_easy,radio_med,radio_hard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_clicked__level_selection);


    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?

    }
    public void BeginGame(View view) {
        Intent intent = new Intent(this, GamePlay.class);
        Log.d("Checked","Made an intent");
//        boolean checked = ((RadioButton) view).isChecked();
        int sessionId = 1;
        RadioButton radio_easy = (RadioButton) findViewById(R.id.radio_easy);
        RadioButton radio_med = (RadioButton) findViewById(R.id.radio_med);
        RadioButton radio_hard = (RadioButton) findViewById(R.id.radio_hard);

        boolean re = radio_easy.isChecked();
//        Log.d("Checked","EASY");
        boolean rm = radio_med.isChecked();
//        Log.d("Checked","MED");
        boolean rh = radio_hard.isChecked();
//        Log.d("Checked","HARD");
        if (re) {
            sessionId = 0;
        }
        else if (rm) {
            sessionId =1;
        }
        else if (rh) {
            sessionId =2;
        }
        else { sessionId =1;}

        Log.d("Checked","Made it pass the Switch-Case");
        intent.putExtra("EXTRA_SESSION_ID", sessionId);
        Log.d("Checked", String.valueOf(sessionId));
        startActivity(intent);


    }

}
