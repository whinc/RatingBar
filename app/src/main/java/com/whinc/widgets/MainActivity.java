package com.whinc.widgets;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.whinc.widget.RatingBar;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout root = (LinearLayout) findViewById(R.id.root);
        final RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        findViewById(R.id.decrease_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingBar.setCount(ratingBar.getCount() - 1);
            }
        });
        findViewById(R.id.increase_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingBar.setCount(ratingBar.getCount() + 1);
            }
        });
        ratingBar.setOnCountChangeListener(new RatingBar.OnCountChangeListener() {
            @Override
            public void onChange(int preCount, int curCount) {
                Log.i("TAG", String.format("previous count:%d, current count:%d", preCount, curCount));
            }
        });

        root.addView(new RatingBar(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
