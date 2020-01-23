package ishiicai.shohoku.ac.jp.yami_tweet_mixir;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

public class OpeningActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opening_layout);
    }

    //画面を押し(離し)たときにミキサー画面へ遷移する
    @Override
    public boolean onTouchEvent(MotionEvent T_Event) {
        if (T_Event.getAction() == MotionEvent.ACTION_UP) {
            Intent intent = new Intent(OpeningActivity.this, TweetActivity_Empty.class);
            startActivity(intent);
        }
        return true;
    }
}
