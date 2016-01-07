package com.gdb.servicetest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private boolean counterStatus = false;
    private MyHandler handler = new MyHandler();

    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            int counter = msg.what;
            if (msg.what == CounterService.EMPLOYEE_OBJECT) {
                Bundle data = msg.getData();
                Employee e = data.getParcelable("employee");

                if (e != null) {
                    TextView textView = (TextView) findViewById(R.id.textView);
                    textView.setText(e.getName());
                    TextView textView2 = (TextView) findViewById(R.id.textView2);
                    textView2.setText(String.valueOf(e.getAge()));
                    TextView textView3 = (TextView) findViewById(R.id.textView3);
                    textView3.setText(String.valueOf(e.getWeight()));
                    TextView textView4 = (TextView) findViewById(R.id.textView4);
                    textView4.setText(getString(R.string.text_view_boolean) + e.isOnBench());
                }
            }
            TextView textViewTimer = (TextView) findViewById(R.id.textViewTimer);
            textViewTimer.setText(String.valueOf(counter));
        }
    }
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == CounterService.BROADCAST_ACTION_INC) {
                final int count = intent.getIntExtra(CounterService.NEW_COUNTER_PARAM,-1);/*
                TextView textViewTimer = (TextView) findViewById(R.id.textViewTimer);
                textViewTimer.setText(String.valueOf(count));*/
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button buttonStartCounter = (Button) findViewById(R.id.buttonStart);
        buttonStartCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counterStatus == false) {
                    counterStatus = true;
                    Messenger messenger = new Messenger(handler);
                    CounterService.startActionStartCounter(getApplicationContext(), messenger);
                } else {
                    counterStatus = false;
                    CounterService.startActionStopCounter(getApplicationContext());
                }
            }
        });

        final IntentFilter filter = new IntentFilter();
        filter.addAction(CounterService.BROADCAST_ACTION_INC);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


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

    @Override
    protected void onDestroy() {
        unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }
}
