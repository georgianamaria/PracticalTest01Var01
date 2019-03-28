package ro.pub.cs.systems.eim.practicaltest01var01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01Var01MainActivity extends AppCompatActivity {

    private EditText editText = null;
    private Button northButton = null;
    private Button southButton = null;
    private Button eastButton = null;
    private Button westButton = null;
    private Button navigateButton = null;

    int serviceStatus = 0;
    private IntentFilter intentFilter = new IntentFilter();

    int contor = 0;

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.north_button:
                    contor++;
                    String text1 = editText.getText().toString();
                    editText.setText(String.valueOf(text1 + "North,"));
                    break;
                case R.id.south_button:
                    contor++;
                    String text2 = editText.getText().toString();
                    editText.setText(String.valueOf(text2 + "South,"));
                    break;
                case R.id.west_button:
                    contor++;
                    String text3 = editText.getText().toString();
                    editText.setText(String.valueOf(text3 + "West,"));
                    break;
                case R.id.east_button:
                    contor++;
                    String text4 = editText.getText().toString();
                    editText.setText(String.valueOf(text4 + "East,"));
                    break;
                case R.id.navigate_button:
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var01SecondaryActivity.class);
                    intent.putExtra("text", editText.getText().toString());
                    startActivityForResult(intent, 1);
                    break;
            }

            if (contor > 4
                    && serviceStatus == 0) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Var01Service.class);
                intent.putExtra("text", editText.getText().toString());
                getApplicationContext().startService(intent);
                serviceStatus =1;
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var01_main);

        editText = (EditText)findViewById(R.id.edit_text);
        editText.setText(String.valueOf(""));

        northButton = (Button)findViewById(R.id.north_button);
        southButton = (Button)findViewById(R.id.south_button);
        eastButton = (Button)findViewById(R.id.east_button);
        westButton = (Button)findViewById(R.id.west_button);
        navigateButton = (Button)findViewById(R.id.navigate_button);

        northButton.setOnClickListener(buttonClickListener);
        southButton.setOnClickListener(buttonClickListener);
        eastButton.setOnClickListener(buttonClickListener);
        westButton.setOnClickListener(buttonClickListener);
        navigateButton.setOnClickListener(buttonClickListener);

//        if (savedInstanceState.containsKey("Count")) {
//            contor = savedInstanceState.getString("Count");
//        } else {
//            contor = 0;
//        }

        intentFilter.addAction("ACT");
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("Count", String.valueOf(contor));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey("Count")) {
            contor = Integer.valueOf(savedInstanceState.getString("Count"));
        } else {
            contor = 0;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 1) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var01Service.class);
        stopService(intent);
        super.onDestroy();
    }
}
