package ishiicai.shohoku.ac.jp.yami_tweet_mixir;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.concurrent.TransferQueue;

import androidx.appcompat.app.AppCompatActivity;

public class MixirActivity_empty extends AppCompatActivity {

    private VideoView mVideoView;
    public static final String TWEET_POINT = "SendToOtherActivity.DATA";
    public int sumTextNum;              //総文字数
    private int textPointSum=0;
    private TestOpenHelper helper;
    private SQLiteDatabase db;
    private TextView strNumText;
    private TextView textView;

    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mixir_empty_layout);
        mVideoView = (VideoView)findViewById(R.id.videoView6);
        mVideoView.setVideoURI(Uri.parse("android.resource://" + this.getPackageName() + "/" +R.raw.normal_mixer));
        strNumText=findViewById(R.id.StrNumText);
        textView=findViewById(R.id.textView);

        //画面に表示する部分のテキストをTweetActivityから取得
        Intent intent = getIntent();
        String acceptText = intent.getStringExtra(TweetActivity_Empty.TWEET_TEXT);
        textView.setText(acceptText);

        readData();
        textPointSum = sumTextNum;
        strNumText.setText(String.valueOf(textPointSum));

        //ボタンを押した際にカウントを増やし、一定値以上で切り替える

        //動画の再生開始
        mVideoView.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent T_EVENT){
        //一定値以上ならば画面を遷移する
        if (textPointSum >= 20) {
            ScreenChange_two();
        } else {
            ScreenChange_emp();
        }
        return true;
    }

    public void ScreenChange_emp(){
        Intent intent = new Intent(getApplication(), TweetActivity_Empty.class);
        intent.putExtra(TWEET_POINT, textPointSum);
        startActivity(intent);
    }

    public void ScreenChange_two(){
        Intent intent = new Intent(getApplication(), TweetActivity_TwentyPer.class);
        intent.putExtra(TWEET_POINT, textPointSum);
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

