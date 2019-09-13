package com.akshay.playwithui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnSendDataToAnotherScreen;
    private EditText etInputDataToSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSendDataToAnotherScreen = (Button) findViewById(R.id.btnSendData);
        etInputDataToSend = (EditText) findViewById(R.id.etInput);

        btnSendDataToAnotherScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data =  etInputDataToSend.getText().toString();
                if(data.isEmpty()) {
                    Toast.makeText(MainActivity.this,
                            "Please input some data to send to another screen.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    intent.putExtra("data", data);
                    startActivity(intent);
                }
            }
        });

    }
}
