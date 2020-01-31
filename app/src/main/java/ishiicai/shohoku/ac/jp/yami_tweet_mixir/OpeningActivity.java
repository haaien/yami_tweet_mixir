package ishiicai.shohoku.ac.jp.yami_tweet_mixir;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OpeningActivity extends AppCompatActivity {

    private TestOpenHelper helper;
    private SQLiteDatabase db;
    public int sumTextNum;          //総文字数
    public Intent intent;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opening_layout);

        final TextView textView1 = (TextView)findViewById(R.id.StrNumText);

        //これまでの総文字数をDBから呼び出す
        readData();
        Log.d("Debug","readDataまで動いたよ");
        textView1.setText(String.valueOf(sumTextNum));

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
            }else if(100<sumTextNum){
                intent = new Intent(OpeningActivity.this, BrokenMixerActivity.class);
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
}
