package ishiicai.shohoku.ac.jp.yami_tweet_mixir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opening_layout);

        Button button1 = (Button) findViewById(R.id.opening);     //画面遷移ボタン
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //インテントに、この画面と、遷移する別の画面を指定する
                Intent intent = new Intent(getApplication(), TweetActivity_TwentyPer.class);
                //インテントで指定した別の画面に遷移する
                startActivity(intent);
            }
        });
    }
}
