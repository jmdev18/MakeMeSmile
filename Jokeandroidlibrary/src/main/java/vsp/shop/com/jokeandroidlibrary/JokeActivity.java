package vsp.shop.com.jokeandroidlibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        String joke = getIntent().getExtras().get("JOKE").toString();
        TextView jokeTV = findViewById(R.id.joke_tv);
        jokeTV.setText(joke);
    }
}
