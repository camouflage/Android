package com.example.sunsheng.lab4;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.util.Log;

/**
 * Created by sunsheng on 11/13/15.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        if ( intent.getAction().equals("dynamicReceiver") ) {
            remoteViews.setTextViewText(R.id.tv, intent.getStringExtra("message"));
        } else if ( intent.getAction().equals("staticReceiver") ) {
            remoteViews.setTextViewText(R.id.tv1, intent.getStringExtra("message"));
        }
        AppWidgetManager.getInstance(context).updateAppWidget(
                new ComponentName(context.getApplicationContext(), MyWidgetProvider.class), remoteViews);
    }
}

