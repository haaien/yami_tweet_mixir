package ishiicai.shohoku.ac.jp.yami_tweet_mixir;

import android.content.ContentValues;
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

public class MixirActivity_empty extends AppCompatActivity {

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
        setContentView(R.layout.mixir_empty_layout);
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
        if (textPointSum >= 20) {
            mVideoView = (VideoView)findViewById(R.id.videoView6);
            mVideoView.setVideoURI(Uri.parse("android.resource://" + this.getPackageName() + "/" +R.raw.empty_next_up));
        } else {
            mVideoView = (VideoView)findViewById(R.id.videoView6);
            mVideoView.setVideoURI(Uri.parse("android.resource://" + this.getPackageName() + "/" +R.raw.empty_mixer_up));
        }
        //文字の移動開始
        StartAnimation();
        //動画の再生開始
        mVideoView.start();
        //再生完了リスナーの取得
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer mp){
                if (textPointSum >= 20) {
                    ScreenChange_two();
                } else {
                    ScreenChange_emp();
                }
            }
        });


    }

    public void StartAnimation(){
        translateAnimation = new TranslateAnimation(
                Animation.ABSOLUTE,0.0f,
                Animation.ABSOLUTE,0.0f,
                Animation.ABSOLUTE,0.0f,
                Animation.ABSOLUTE,380.0f
        );
        //秒数、ミリ秒
        translateAnimation.setDuration(3000);
        //繰り返す回数
        translateAnimation.setRepeatCount(0);
        //やれ
        translateAnimation.setFillAfter(true);
        textView.startAnimation(translateAnimation);
    }

    public void ScreenChange_emp(){
        Intent intent = new Intent(getApplication(), TweetActivity_Empty.class);
        intent.putExtra(TWEET_TEXT, textPointSum);    //もしエラーが吐かれたらここが原因の可能性あり　dtextPointSum
        startActivity(intent);
    }

    public void ScreenChange_two(){
        Intent intent = new Intent(getApplication(), TweetActivity_TwentyPer.class);
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

    //テスト用のデータベース編集関数
    public void saveData(SQLiteDatabase db, int score){
        ContentValues values = new ContentValues();
        values.put("StrNumber", score);

        db.insert("strNumdb", null, values);
    }
}

