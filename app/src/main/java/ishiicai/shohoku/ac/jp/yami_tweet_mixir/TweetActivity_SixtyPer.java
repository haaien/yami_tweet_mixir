package ishiicai.shohoku.ac.jp.yami_tweet_mixir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TweetActivity_SixtyPer extends AppCompatActivity {
    private EditText inputEditText;
    private Button button;
    private TextView textView;
    private ImageView imageView;

    public int points;
    public static final String TWEET_POINT = "TweetToOtherActivity.DATA";

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweet_sixtyper_layout);
        button.findViewById(R.id.MixedButton);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String text = inputEditText.getText().toString();
                if(text.length()<1){
                    new
                }
            }
        });
    }
    public void ScreenChange(){
        textView.setText(String.valueOf(points));
        Intent intent = new Intent((getApplicationContext(),MixirActivity_SixtyPer.class);
        intent.putExtra(TWEET_POINT,points);
        startActivity(intent);
    }
}
