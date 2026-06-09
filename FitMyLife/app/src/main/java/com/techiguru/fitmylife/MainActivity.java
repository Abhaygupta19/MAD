package com.techiguru.fitmylife;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button bt_play, bt_pause, bt_stop;
    TextView tv_userName;
    ImageView iv_logout;
    SharedPreferences sp;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        String name = sp.getString("name", "User");

        tv_userName = findViewById(R.id.tv_userName);
        tv_userName.setText(getString(R.string.hi_user, name));

        iv_logout = findViewById(R.id.iv_logout);
        iv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("isLoggedIn", false);
                editor.apply();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        bt_play = findViewById(R.id.bt_play);
        bt_pause = findViewById(R.id.bt_pause);
        bt_stop = findViewById(R.id.bt_stop);

        // Simple audio placeholder
        mp = MediaPlayer.create(this, Uri.parse("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"));

        bt_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mp != null) {
                    mp.start();
                    Toast.makeText(MainActivity.this, "Playing Focus Music", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mp != null && mp.isPlaying()) {
                    mp.pause();
                }
            }
        });

        bt_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mp != null) {
                    mp.stop();
                    // Need to recreate or prepare for next play after stop
                    try {
                        mp.prepare();
                        mp.seekTo(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mp != null) {
            mp.release();
            mp = null;
        }
    }
}