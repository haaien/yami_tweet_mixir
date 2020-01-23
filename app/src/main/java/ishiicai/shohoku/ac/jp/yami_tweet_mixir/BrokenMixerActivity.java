package ishiicai.shohoku.ac.jp.yami_tweet_mixir;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class BrokenMixerActivity extends AppCompatActivity {

    private VideoView mVideoView;



    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tmp_stab_layout);

        mVideoView = (VideoView)findViewById(R.id.videoView6);
        mVideoView.setVideoURI(Uri.parse("android.resource://" + this.getPackageName() + "/" +R.raw.burn_shock));
        //動画の再生開始
        mVideoView.start();

        HandlerThread handlerThread = new HandlerThread("foo");
        handlerThread.start();
        new Handler(handlerThread.getLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // 3秒後に表示
                //インテントに、この画面と、遷移する別の画面を指定する
                Intent intent = new Intent(getApplication(), OpeningActivity.class);
                //インテントで指定した別の画面に遷移する
                startActivity(intent);
            }
        }, 10000);



    }
}
