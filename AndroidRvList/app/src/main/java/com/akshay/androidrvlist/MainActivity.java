package com.akshay.androidrvlist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvAndroidList;
    private AndroidListAdapter androidListAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvAndroidList = (RecyclerView) findViewById(R.id.rvAndroidList);
        rvAndroidList.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        rvAndroidList.setLayoutManager(layoutManager);

        AndroidVersion[] androidVersionsDataList = new AndroidVersion[]{
                new AndroidVersion("donut", R.drawable.donut),
                new AndroidVersion("eclair", R.drawable.eclair),
                new AndroidVersion("froyo", R.drawable.froyo),
                new AndroidVersion("gingerbread", R.drawable.gingerbread),
                new AndroidVersion("honeycomb", R.drawable.honeycomb),
                new AndroidVersion("icecream", R.drawable.icecream),
                new AndroidVersion("jellybean", R.drawable.jellybean),
                new AndroidVersion("kitkat", R.drawable.kitkat),
                new AndroidVersion("lollipop", R.drawable.lollipop),
                new AndroidVersion("marshmallow", R.drawable.marshmallow)
        };


        // specify an adapter (see also next example)
        androidListAdapter = new AndroidListAdapter(androidVersionsDataList);
        rvAndroidList.setAdapter(androidListAdapter);


    }
}
