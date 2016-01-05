package com.gdb.servicetest;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Created by devdatta on 31/12/15.
 */
public class CounterService extends IntentService {

    private static final String TAG = CounterService.class.getSimpleName();
    private int counter = 0;
    private Runnable r;

    {
        r = new Runnable() {
            @Override
            public void run() {
                int c = getCounter();
                setCounter(c + 1);
                broadcastActionInc(c);
                Log.d(TAG, "Counter : " + c);
                handler.postDelayed(r, 1000);
            }
        };
    }

    private Handler handler = new Handler();

    public static final String BROADCAST_ACTION_INC = "com.gdb.broadcast_action.INCREMENT";
    public static final String NEW_COUNTER_PARAM = "com.gdb.COUNTER_PARAM";

    public static final String ACTION_START_COUNTER = "com.gdb.broadcast_action.START_COUNTER";
    public static final String ACTION_STOP_COUNTER = "com.gdb.broadcast_action.STOP_COUNTER";


    public CounterService() {
        super("CounterService");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public CounterService(String name) {
        super(name);
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int cnt) {
        counter = cnt;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_START_COUNTER.equals(action)) {
                handler.postDelayed(r, 1000);
            } else if (ACTION_STOP_COUNTER.equals(action)){
                stopSelf();
            }
        }
    }

    public static void startActionStartCounter (Context context) {
        Intent intent = new Intent(context, CounterService.class);
        intent.setAction(ACTION_START_COUNTER);
        context.startService(intent);
    }
    public static void startActionStopCounter (Context context) {
        Intent intent = new Intent(context, CounterService.class);
        intent.setAction(ACTION_STOP_COUNTER);
        context.stopService(intent);
    }

    public void broadcastActionInc(int param) {
        Intent broadcastIntent = new Intent(BROADCAST_ACTION_INC);
        broadcastIntent.putExtra(NEW_COUNTER_PARAM,param);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.sendBroadcast(broadcastIntent);
    }
}