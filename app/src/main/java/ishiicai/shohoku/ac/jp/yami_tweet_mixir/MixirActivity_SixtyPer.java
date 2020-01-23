package ishiicai.shohoku.ac.jp.yami_tweet_mixir;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class MixirActivity_SixtyPer extends AppCompatActivity {

    public int points = 0;
    private VideoView mVideoView;
    public static final String SEND_POINT = "SendToOtherActivity.DATA";

    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mixir_sixtyper_layout);

        mVideoView = (VideoView)findViewById(R.id.videoView3);
        mVideoView.setVideoURI(Uri.parse("android.resource://" + this.getPackageName() + "/" +R.raw.mixer_sixper));

        //ボタンを押した際にカウントを増やし、一定値以上で切り替える


                //動画の再生開始
                mVideoView.start();
                //カウントを１増やす、その際一定値以上ならば画面を遷移する
                points++;

                Button button1 = (Button) findViewById(R.id.button3);
                button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(points >= 16){
                    Intent intent = new Intent(getApplication(), TweetActivity_EightyPer.class);
                    intent.putExtra(SEND_POINT, points);      //現在のカウントを次の画面へ受け渡す
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(getApplication(), TweetActivity_SixtyPer.class);
                    intent.putExtra(SEND_POINT, points);      //現在のカウントを次の画面へ受け渡す
                    startActivity(intent);
                }
            }
        });
    }
}
