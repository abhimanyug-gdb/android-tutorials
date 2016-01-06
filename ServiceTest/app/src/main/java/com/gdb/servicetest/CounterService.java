package com.gdb.servicetest;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Created by devdatta on 31/12/15.
 */
public class CounterService extends IntentService {

    private static final String TAG = CounterService.class.getSimpleName();
    private static final int COUNT_NOTIFICATION_ID = 5616;
    private int counter = 0;
    private Runnable r;

    {
        r = new Runnable() {
            @Override
            public void run() {
                int c = getCounter();
                broadcastActionInc(c);
                if (c % 20 == 0) {
                    notifyCount();
                }
                if (c % 20 == 15)
                {
                    cancleNotification();
                }
                setCounter(c + 1);
                Log.d(TAG, "Counter : " + c);
                handler.postDelayed(r, 1000);
            }
        };
    }

    private void cancleNotification() {
        NotificationManager manager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(COUNT_NOTIFICATION_ID);
    }

    private void notifyCount() {

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(R.drawable.ic_alarm_on_24dp)
                        .setContentTitle("Your Counter")
                        .setContentText("Counter in service reached : " + getCounter());
        Intent countIntent = new Intent(getApplicationContext(), MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());

        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(countIntent);

        PendingIntent weatherPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        notificationBuilder.setContentIntent(weatherPendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = notificationBuilder.build();
        notification.flags = Notification.DEFAULT_LIGHTS | Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(COUNT_NOTIFICATION_ID, notification);

    }

    private Handler handler = new Handler();

    public static final String BROADCAST_ACTION_INC = "com.gdb.broadcast_action.INCREMENT";
    public static final String NEW_COUNTER_PARAM = "com.gdb.COUNTER_PARAM";

    public static final String ACTION_START_COUNTER = "com.gdb.broadcast_action.START_COUNTER";
    public static final String ACTION_STOP_COUNTER = "com.gdb.broadcast_action.STOP_COUNTER";


    public CounterService() {
        super("CounterService");
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