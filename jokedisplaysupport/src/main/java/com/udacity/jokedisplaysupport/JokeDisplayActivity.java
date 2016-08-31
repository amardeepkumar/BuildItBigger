package com.udacity.jokedisplaysupport;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class JokeDisplayActivity extends AppCompatActivity {

    public static final String EXTRA_JOKE = "joke";

    private TextView mJokeTextView;

    public static Intent getJokeDisplayActivityIntent(Context context, String joke) {
        Intent intent = new Intent(context, JokeDisplayActivity.class);
        intent.putExtra(EXTRA_JOKE, joke);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);

        mJokeTextView = (TextView) findViewById(R.id.tv_joke);
        String joke = getIntent().getExtras().getString(EXTRA_JOKE);

        mJokeTextView.setText(joke);
    }
}
