package com.reid.designpatterns;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "liyang";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Room room = (new WorkBuild())
                .makeWindow("法式窗户")
                .makeFloor("地板").build();
        Log.d(TAG, "room: " + room.toString());
    }
}
