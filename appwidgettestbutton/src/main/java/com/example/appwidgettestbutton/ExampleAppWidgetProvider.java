package com.example.appwidgettestbutton;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * 主要实现功能：点击AppWidget的button，跳转到我们的TargetActivity
 * 明白知识点：
 * 1. AppWidgetProvider  extends BroadcastReceiver 所以AppWidgetProvider就是一个专门用来接收广播消息的类
 * 2. 下面的所有复写的方法，都是用来接收固定广播信息的。（onReceive是接收所有广播的入口，然后分配给其他的方法）比如onUpdate看帮助文档就有说。
 * Called in response to the ACTION_APPWIDGET_UPDATE and ACTION_APPWIDGET_RESTORED broadcasts
 * when this AppWidget provider is being asked to provide RemoteViews for a set of AppWidgets.
 * 3.明白onUpdate为什么要用那个for循环
 */
public class ExampleAppWidgetProvider extends AppWidgetProvider {
    String TAG = getClass().getSimpleName();

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // TODO Auto-generated method stub
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onDisabled(Context context) {
        // TODO Auto-generated method stub
        super.onDisabled(context);
    }

    @Override
    public void onEnabled(Context context) {
        // TODO Auto-generated method stub
        super.onEnabled(context);
    }

    /**
     * 这个是接收所有广播的入口，然后分配给其他类
     * Implements onReceive(Context, Intent) to dispatch calls to the various other methods on AppWidgetProvider.
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        super.onReceive(context, intent);
    }

    /**
     *
     * @param context
     * @param appWidgetManager
     * @param appWidgetIds
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        // 之所以要用for循环原因：我们可能会添加多个AppWidget到安卓手机桌面上，所以要为每个AppWidget的button都要定义方法
        // 之所以要用for只是MarsChen想看打印log设计的，其实可以吧for循环去掉，然后把最下面那个方法弄成这个appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);

        for (int i = 0; i < appWidgetIds.length; i++) {
            Log.d(TAG, "onUpdate: appWidgetIds = " + appWidgetIds[i]);
            //创建一个Intent对象（相当于一个“妙计”）
            Intent intent = new Intent(context, TargetActivity.class);
            //创建一个PendingIntent（相当一个“锦囊”，把我们的Intent“妙计”放进去了）
            // 用getActivity的原因（下面我们会讲另一个方法）Retrieve a PendingIntent that will start a new activity, like calling Context.startActivity(Intent).
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.example_appwidget);
            //为按钮绑定事件处理器
            //第一个参数用来指定被绑定处理器的控件的ID
            //第二个参数用来指定当事件发生时，哪个PendingIntent将会被执行
            remoteViews.setOnClickPendingIntent(R.id.widgetButtonId, pendingIntent);
            //更新AppWidget
            //第一个参数用于指定被更新AppWidget的ID
            appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

}

