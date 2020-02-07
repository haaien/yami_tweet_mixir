package ishiicai.shohoku.ac.jp.yami_tweet_mixir;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.media.MediaPlayer;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class BrokenMixerActivity extends AppCompatActivity {

     MediaPlayer mp = null;
    private TestOpenHelper helper;
    private SQLiteDatabase db;
    public int sumTextNum;          //総文字数


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

                //総文字数を変更
                readData();
                saveData(db, 0);
                //インテントに、この画面と、遷移する別の画面を指定する
                Intent intent = new Intent(getApplication(), OpeningActivity.class);
                //インテントで指定した別の画面に遷移する
                startActivity(intent);
            }
        }, 10000);
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
