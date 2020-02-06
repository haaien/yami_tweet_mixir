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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class OpeningActivity extends AppCompatActivity {

    private TestOpenHelper helper;
    private SQLiteDatabase db;
    public int sumTextNum;          //総文字数
    public Intent intent;
    private VideoView mVideoView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opening_layout);

        final TextView textView1 = (TextView)findViewById(R.id.StrNumText);
        final Button button1 = (Button)findViewById(R.id.button);
        final Button button2 = (Button)findViewById(R.id.button2);
        final Button button3 = (Button)findViewById(R.id.button3);
        final Button button4 = (Button)findViewById(R.id.button4);
        final Button button5 = (Button)findViewById(R.id.button5);

        //これまでの総文字数をDBから呼び出す
        readData();
        Log.d("Debug","readDataまで動いたよ");
        textView1.setText(String.valueOf(sumTextNum));

        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                saveData(db, 0);
                intent = new Intent(OpeningActivity.this, TweetActivity_Empty.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                saveData(db, 20);
                intent = new Intent(OpeningActivity.this, TweetActivity_TwentyPer.class);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                saveData(db, 40);
                intent = new Intent(OpeningActivity.this, TweetActivity_FortyPer.class);
                startActivity(intent);
            }
        });

        button4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                saveData(db, 60);
                intent = new Intent(OpeningActivity.this, TweetActivity_SixtyPer.class);
                startActivity(intent);
            }
        });

        button5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                saveData(db, 80);
                intent = new Intent(OpeningActivity.this, TweetActivity_EightyPer.class);
                startActivity(intent);
            }
        });

        mVideoView = (VideoView)findViewById(R.id.open);
        mVideoView.setVideoURI(Uri.parse("android.resource://" + this.getPackageName() + "/" +R.raw.open));
        //動画の再生開始
        mVideoView.start();
        // 動画再生完了通知のリスナー設定
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // 先頭に戻す
                mVideoView.seekTo(0);
                // 再生開始
                mVideoView.start();
            }

        });


    }

    //画面を押し(離し)たときにミキサー画面へ遷移する
    @Override
    public boolean onTouchEvent(MotionEvent T_Event) {
        if (T_Event.getAction() == MotionEvent.ACTION_UP) {
            if(sumTextNum<=20) {
                intent = new Intent(OpeningActivity.this, TweetActivity_Empty.class);
            }else if(sumTextNum<=40){
                intent = new Intent(OpeningActivity.this, TweetActivity_TwentyPer.class);
            }else if(sumTextNum<=60){
                intent = new Intent(OpeningActivity.this, TweetActivity_FortyPer.class);
            }else if(sumTextNum<=80){
                intent = new Intent(OpeningActivity.this, TweetActivity_SixtyPer.class);
            }else if(sumTextNum<=100){
                intent = new Intent(OpeningActivity.this, TweetActivity_EightyPer.class);
            //}else if(100<sumTextNum){
                //intent = new Intent(OpeningActivity.this, BrokenMixerActivity.class);
            }else{
                Intent intent = new Intent(OpeningActivity.this, TmpStab.class);
            }
            startActivity(intent);
        }
        return true;
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

    public void saveData(SQLiteDatabase db, int score){
        ContentValues values = new ContentValues();
        values.put("StrNumber", score);

        db.insert("strNumdb", null, values);
    }
}
