package ishiicai.shohoku.ac.jp.yami_tweet_mixir;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import java.util.concurrent.TransferQueue;

import androidx.appcompat.app.AppCompatActivity;

public class MixirActivity_empty extends AppCompatActivity {

    public int textpointSum = 0;
    private VideoView mVideoView;
    public static final String TWEET_POINT = "SendToOtherActivity.DATA";

    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mixir_empty_layout);
        Intent intent = getIntent();
        int acceptpoints = intent.getIntExtra(TweetActivity_Empty.TWEET_POINT, 0);
        textpointSum = acceptpoints;

        mVideoView = (VideoView)findViewById(R.id.videoView6);
        mVideoView.setVideoURI(Uri.parse("android.resource://" + this.getPackageName() + "/" +R.raw.empty_mixer));

        //ボタンを押した際にカウントを増やし、一定値以上で切り替える

        //動画の再生開始
        mVideoView.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent T_EVENT){
        //一定値以上ならば画面を遷移する
        if (textpointSum >= 20) {
            ScreenChange_two();
        } else {
            ScreenChange_emp();
        }
        return true;
    }

    public void ScreenChange_emp(){
        Intent intent = new Intent(getApplication(), TweetActivity_Empty.class);
        intent.putExtra(TWEET_POINT, textpointSum);
        startActivity(intent);
    }

    public void ScreenChange_two(){
        Intent intent = new Intent(getApplication(), TweetActivity_TwentyPer.class);
        intent.putExtra(TWEET_POINT, textpointSum);
        startActivity(intent);
    }
}

