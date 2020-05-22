package com.example.conjure;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by ssaurel on 02/12/2016.
 */
public class splash_activity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT =1500;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(splash_activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}






//package com.example.conjure;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
///**
// * Created by ssaurel on 02/12/2016.
//     */
//    public class splash_activity extends AppCompatActivity {
//
//        private static int SPLASH_TIME_OUT =1500;
//
//        @Override
//        protected void onCreate(@Nullable Bundle savedInstanceState) {
//
//            super.onCreate(savedInstanceState);
//
//            setContentView(R.layout.activity_splash);
//            new Handler().postDelayed(new Runnable() {
//
//                @Override
//                public void run() {
//                    Intent intent = new Intent(splash_activity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }, SPLASH_TIME_OUT);
//        }
//    }
//
//
//
//
//
