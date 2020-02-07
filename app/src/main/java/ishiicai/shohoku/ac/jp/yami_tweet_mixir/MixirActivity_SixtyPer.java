package ishiicai.shohoku.ac.jp.yami_tweet_mixir;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.VideoView;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation;

import androidx.appcompat.app.AppCompatActivity;

public class MixirActivity_SixtyPer extends AppCompatActivity {

    private VideoView mVideoView;
    public static final String TWEET_TEXT= "SendToOtherActivity.DATA";
    public int sumTextNum;              //総文字数
    private int textPointSum=0;
    private TestOpenHelper helper;
    private SQLiteDatabase db;
    private TextView strNumText;
    private TextView textView;
    private TranslateAnimation translateAnimation;  //viewのアニメーション

    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mixir_sixtyper_layout);
        strNumText=findViewById(R.id.StrNumText);
        textView=findViewById(R.id.textView);

        //画面に表示する部分のテキストをTweetActivityから取得
        Intent intent = getIntent();
        String acceptText = intent.getStringExtra(TweetActivity_Empty.TWEET_TEXT);
        textView.setText(acceptText);

        readData();
        textPointSum = sumTextNum;
        strNumText.setText(String.valueOf(textPointSum));

        //取得文字数によって再生する動画を切り替える
        if (textPointSum >= 80) {
            mVideoView = (VideoView)findViewById(R.id.videoView4);
            mVideoView.setVideoURI(Uri.parse("android.resource://" + this.getPackageName() + "/" +R.raw.sixty_next_up));
        } else {
            mVideoView = (VideoView)findViewById(R.id.videoView4);
            mVideoView.setVideoURI(Uri.parse("android.resource://" + this.getPackageName() + "/" +R.raw.sixty_mixer_up));
        }

        //textviewの移動開始
        StartAnimation();

        //動画の再生開始
        mVideoView.start();
        //再生完了リスナーの取得
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer mp){
                if (textPointSum >= 80) {
                    ScreenChange_eig();
                } else {
                    ScreenChange_six();
                }
            }
        });
    }

    public void StartAnimation(){
        translateAnimation = new TranslateAnimation(
                Animation.ABSOLUTE,0.0f,
                Animation.ABSOLUTE,0.0f,
                Animation.ABSOLUTE,0.0f,
                Animation.ABSOLUTE,110.0f
        );
        translateAnimation.setDuration(3000);

        translateAnimation.setRepeatCount(0);

        translateAnimation.setFillAfter(true);

        textView.startAnimation(translateAnimation);
    }


    public void ScreenChange_six(){
        Intent intent = new Intent(getApplication(), TweetActivity_SixtyPer.class);
        intent.putExtra(TWEET_TEXT, textPointSum);
        startActivity(intent);
    }

    public void ScreenChange_eig(){
        Intent intent = new Intent(getApplication(), TweetActivity_EightyPer.class);
        intent.putExtra(TWEET_TEXT, textPointSum);
        startActivity(intent);
    }

    public void readData(){
        //データベースを認識させる（初回のみ）
        if(helper == null){
            helper = new TestOpenHelper(getApplicationContext());
        }
        if(db == null){
            db = helper.getReadableDatabase();
        }

        Log.d("Debug","nullチェックまで動いたよ");

        //データベースに格納された数値すべてを呼び出す
        Cursor cursor = db.query(
                "strNumdb",
                new String[] {"StrNumber"},
                null,
                null,
                null,
                null,
                null
        );

        Log.d("Debug","queryまで動いたよ");

        //integer型の値として、直近に更新された数値を（上書きする形で）呼び出す
        if(cursor.moveToFirst()){
            do{
                sumTextNum = cursor.getInt(cursor.getColumnIndex("StrNumber"));
            }while (cursor.moveToNext());
        }
    }
}

