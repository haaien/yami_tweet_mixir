package ishiicai.shohoku.ac.jp.yami_tweet_mixir;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class TweetActivity_TwentyPer extends AppCompatActivity {
    private EditText inputeditText;
    private ImageButton imageButton;
    private TextView textView;
    private int sumTextNum;              //総文字数
    private TestOpenHelper helper;
    private SQLiteDatabase db;

    public int points;
    public static final String TWEET_TEXT="SendToOtherActivity.DATA";
    public String text, cutText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweet_twentyper_layout);
        imageButton=(ImageButton)findViewById(R.id.MixedButton);
        inputeditText=(EditText)findViewById(R.id.InputEditText);
        textView=findViewById(R.id.textView);
        Intent intent = getIntent();

        //これまでの総文字数をDBから呼び出す
        readData();
        Log.d("Debug","readDataまで動いたよ");
        points=sumTextNum;

        //本番はコメントアウトする部分（現在の文字数）
        TextView textView_texsum = (TextView)findViewById(R.id.StrNumText);
        textView_texsum.setText(String.valueOf(points));

        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                //データベースを認識させる（初回のみ）
                if(helper == null){
                    helper = new TestOpenHelper(getApplicationContext());
                }
                if(db == null){
                    db = helper.getWritableDatabase();
                }

                text = inputeditText.getText().toString();

                points += text.length();
                saveData(db, points);
                if(text.length()<1){
                    new AlertDialog.Builder(view.getContext())
                            .setTitle("")
                            .setMessage("入力したい文字がない？！アプリを消せ！！")
                            .setPositiveButton("close",null)
                            .show();
                }else if(text.length()>=1&&text.length()<20){
                    cutText=text;
                    inputeditText.setText("");
                    ScreenChange();
                }else if(text.length()>=20){
                    cutText=text.substring(0,10);
                    inputeditText.setText("");
                    ScreenChange();
                }else {
                    Toast.makeText(view.getContext(),"入力してくださあああい！",Toast.LENGTH_SHORT);
                }
            }
        });
    }
    public void ScreenChange(){
        //textView.setText(String.valueOf(points));
        Intent intent = new Intent(getApplication(), MixirActivity_TwentyPer.class);
        intent.putExtra(TWEET_TEXT, cutText);
        startActivity(intent);
    }

    /**
     * 総文字数をDBに保存する（関数を呼び出すための）関数
     * @param db    保存するデータベースの情報
     * @param score 保存する値
     */
    public void saveData(SQLiteDatabase db, int score){
        ContentValues values = new ContentValues();
        values.put("StrNumber", score);

        db.insert("strNumdb", null, values);
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
