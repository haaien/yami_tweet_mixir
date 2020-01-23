package ishiicai.shohoku.ac.jp.yami_tweet_mixir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class TweetActivity_FortyPer extends AppCompatActivity {
    private EditText inputEditText;
    private ImageButton imageButton;
    private TextView textView;
    private ImageView imageView;

    public int points;
    public static final String TWEET_POINT="TweetToOtherActivity.DATA";

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweet_fortyper_layout);
        imageButton.findViewById(R.id.MixedButton);

        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String text = inputEditText.getText().toString();

                if(text.length()<1){
                    new AlertDialog.Builder(view.getContext())
                            .setTitle("")
                            .setMessage("入力したい文字がない！？アプリを消せ！")
                            .setPositiveButton("close",null)
                            .show();
                }else if(text.length()>=1&&text.length()<20){
                    textView.setText(text.substring(0,20));
                    inputEditText.setText("");
                    textView.findViewById(R.id.InputEditText);
                    ScreenChange();

                }else if(text.length()>=20){
                    textView.setText(text.substring(0,19));
                    inputEditText.setText("");
                    ScreenChange();
                }else{
                    Toast.makeText(view.getContext(),"入力してくださああああい",Toast.LENGTH_SHORT);
                }
            }
        });
    }
    public void ScreenChange(){
        textView.setText(String.valueOf(points));
        Intent intent = new Intent(getApplicationContext(),MixirActivity_FortyPer.class);
        intent.putExtra(TWEET_POINT,points);
        startActivity(intent);
    }
}
