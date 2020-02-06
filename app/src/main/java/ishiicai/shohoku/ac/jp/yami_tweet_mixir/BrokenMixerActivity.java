package ishiicai.shohoku.ac.jp.yami_tweet_mixir;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.media.MediaPlayer;

import androidx.appcompat.app.AppCompatActivity;

public class BrokenMixerActivity extends AppCompatActivity {

     MediaPlayer mp = null;



    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.broken_mixir_layout);

        mp = MediaPlayer.create(this, R.raw.himei);
        //音声の再生開始
       mp.start();

        HandlerThread handlerThread = new HandlerThread("foo");
        handlerThread.start();
        new Handler(handlerThread.getLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // 10秒後に表示
                //インテントに、この画面と、遷移する別の画面を指定する
                Intent intent = new Intent(getApplication(), OpeningActivity.class);
                //インテントで指定した別の画面に遷移する
                startActivity(intent);
            }
        }, 10000);



    }
}
