package com.akshay.playwithui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by akshaynandwana on
 * 12, September, 2019
 **/
public class SecondActivity extends AppCompatActivity {

    private TextView tvDisplayData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tvDisplayData = (TextView) findViewById(R.id.tvDisplay);
        String dataFromPrevScreen = getIntent().getStringExtra("data");
        tvDisplayData.setText(dataFromPrevScreen);
    }
}
