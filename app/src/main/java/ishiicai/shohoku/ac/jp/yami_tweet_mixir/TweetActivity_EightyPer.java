/**
 *なんかあったらここに書く
 *
 * String.valuesOf(points)で文字列を変換させてBrokenMixerActivityに持っていく
 * 参考先：Necesary_tech --TranceActivityEightPer.java
 */
package ishiicai.shohoku.ac.jp.yami_tweet_mixir;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.AlertDialog;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;


public class TweetActivity_EightyPer extends AppCompatActivity {
    private EditText inputEditText;
    private Button button;
    private TextView textView;
    private ImageView imageView;
    private InputMethodManager inputMethodManager;

    public int points;
    public static final String TWEET_POINT ="TweetToOtherActivity.DATA";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweet_eightyper_layout);
        button.findViewById(R.id.MixedButton);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String text = inputEditText.getText().toString();

                if(text.length()<1){    //文字を入力しなかった場合の処理　入力するようにダイアログで促す
                    new AlertDialog.Builder(view.getContext())
                            .setTitle("")
                            .setMessage("入力したい文字がない！？アプリを消せ！！")
                            .setPositiveButton("close",null)
                            .show();
                }else if(text.length()>1 && text.length()<20){  //１～20文字未満の場合　EditTextに文字を入れる
                    textView.setText(text);
                    inputEditText.setText("");

                    textView.findViewById(R.id.InputEditText);

                    ScreenChange();

                }else if(text.length()>20){     //文字が20文字以上の場合　1文字目から30文字目までを切り取って表示
                    textView.setText(text.substring(0,29));
                    inputEditText.setText("");

                    ScreenChange();

                }else{  //その他の処理　入力するように促すけど、どこで使われるのか全く想定できない
                    Toast.makeText(view.getContext(),"入力してくださあああい！",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
    public void ScreenChange(){     //BrokenMixerActivityに移行するコード
        textView.setText(String.valueOf(points));   //入力した文字を「points」に見立てて以降先で砕いてもらう根端
        Intent intent = new Intent(getApplicationContext(),MixirActivity_EightyPer.class);
        intent.putExtra(TWEET_POINT,points); //pointsをちゃんと受け取って欲しいという願望のコード
        startActivity(intent);
    }

}
