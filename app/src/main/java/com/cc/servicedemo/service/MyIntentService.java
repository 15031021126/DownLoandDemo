package com.cc.servicedemo.service;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadTask;
import com.cc.servicedemo.MainActivity;
import com.cc.servicedemo.R;
import com.cc.servicedemo.notification.DownloadNotification;
import com.cc.servicedemo.untils.T;

/*
 *@Auther:陈浩
 *@Date: 2019/9/11
 *@Time:16:55
 *@Description:${DESCRIPTION}
 * */public class MyIntentService extends Service {

    private String TAG = "MyIntentService";
    private DownloadNotification mNotify;
    private NotificationManager systemService;
    private Notification notification;
    private String url = "http://172.17.8.100/media/movie.apk";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyIntentService", "onCreate");
        mNotify = new DownloadNotification(getApplicationContext());
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d("MyIntentService", "onStart");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyIntentService", "onStartCommand()");
        //注册
        Aria.download(this).register();
        Aria.download(this)
                .load(url)
                .setFilePath(Environment.getExternalStorageDirectory().getPath() + "/movie.apk")
                .start();   //启动下载

        //获取一个Notification构造器
        Notification.Builder builder = new Notification.Builder(this.getApplicationContext());
        Intent nfIntent = new Intent(this, MainActivity.class);
        // 获取构建好的Notification
        notification = builder.build();
        notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音
        // 参数一：唯一的通知标识；参数二：通知消息。
        startForeground(110, notification);// 开始前台服务
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
        Aria.download(this).unRegister();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 暂停
     */
    public void pause() {
        Aria.download(this).load(url).pause();
    }

    /**
     * 停止下载
     */
    public void stop() {
        Aria.download(this).load(url).cancel();
    }

    @Download.onNoSupportBreakPoint
    public void onNoSupportBreakPoint(DownloadTask task) {
        T.showShort(getApplicationContext(), "该下载链接不支持断点");
    }

    @Download.onTaskStart
    public void onTaskStart(DownloadTask task) {
        T.showShort(getApplicationContext(), task.getDownloadEntity().getFileName() + "，开始下载");
        if (mNotify != null) {
            long len = task.getFileSize();
            int p = (int) (task.getCurrentProgress() * 100 / len);
            Toast.makeText(this, "p:" + p, Toast.LENGTH_SHORT).show();
            mNotify.uploadUi("正在下载", "下载进度"+p, p);
        }
    }

    @Download.onTaskStop
    public void onTaskStop(DownloadTask task) {
        T.showShort(getApplicationContext(), task.getDownloadEntity().getFileName() + "，停止下载");
        if (mNotify != null) {
            long len = task.getFileSize();
            int p = (int) (task.getCurrentProgress() * 100 / len);
            mNotify.uploadUi("暂停", "下载进度"+p, p);
        }
    }

    @Download.onTaskCancel
    public void onTaskCancel(DownloadTask task) {
        T.showShort(getApplicationContext(), task.getDownloadEntity().getFileName() + "，取消下载");
        mNotify.uploadUi("取消下载", "取消下载l "+0, 0);
    }

    @Download.onTaskFail
    public void onTaskFail(DownloadTask task) {
        T.showShort(getApplicationContext(), task.getDownloadEntity().getFileName() + "，下载失败");
    }

    @Download.onTaskComplete
    public void onTaskComplete(DownloadTask task) {
        T.showShort(getApplicationContext(), task.getDownloadEntity().getFileName() + "，下载完成");
        // mNotify.upload(100);
        long len = task.getFileSize();
        int p = (int) (task.getCurrentProgress() * 100 / len);
        mNotify.uploadUi("下载完成", "下载完成"+p, 100);
    }

    @Download.onTaskRunning
    public void onTaskRunning(DownloadTask task) {
        long len = task.getFileSize();
        int p = (int) (task.getCurrentProgress() * 100 / len);
        mNotify.upload(p);
        mNotify.uploadUi("正在下载", "下载进度"+p, p);
    }
}
