package com.cc.servicedemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import androidx.annotation.Nullable;
import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadTask;
import com.cc.servicedemo.notification.DownloadNotification;
import com.cc.servicedemo.untils.T;

/*
 *@Auther:陈浩
 *@Date: 2019/9/11
 *@Time:17:25
 *@Description:${DESCRIPTION}
 * */public class MyService extends Service {
    private static final String DOWNLOAD_URL =
            "http://rs.0.gaoshouyou.com/d/df/db/03df9eab61dbc48a5939f671f05f1cdf.apk";
    private DownloadNotification mNotify;

    @Nullable
    @Override public IBinder onBind(Intent intent) {
        return null;
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override public void onCreate() {
        super.onCreate();
        mNotify = new DownloadNotification(getApplicationContext());
        Aria.download(this).register();
        Aria.download(this)
                .load(DOWNLOAD_URL)
                .setFilePath(Environment.getExternalStorageDirectory().getPath() + "/service_task.apk")
                .start();
    }

    @Override public void onDestroy() {
        super.onDestroy();
        Aria.download(this).unRegister();
    }

    @Download.onNoSupportBreakPoint
    public void onNoSupportBreakPoint(DownloadTask task) {
        T.showShort(getApplicationContext(), "该下载链接不支持断点");
    }

    @Download.onTaskStart
    public void onTaskStart(DownloadTask task) {
        T.showShort(getApplicationContext(), task.getDownloadEntity().getFileName() + "，开始下载");
    }

    @Download.onTaskStop
    public void onTaskStop(DownloadTask task) {
        T.showShort(getApplicationContext(), task.getDownloadEntity().getFileName() + "，停止下载");
    }

    @Download.onTaskCancel
    public void onTaskCancel(DownloadTask task) {
        T.showShort(getApplicationContext(), task.getDownloadEntity().getFileName() + "，取消下载");
    }

    @Download.onTaskFail
    public void onTaskFail(DownloadTask task) {
        T.showShort(getApplicationContext(), task.getDownloadEntity().getFileName() + "，下载失败");
    }

    @Download.onTaskComplete
    public void onTaskComplete(DownloadTask task) {
        T.showShort(getApplicationContext(), task.getDownloadEntity().getFileName() + "，下载完成");
        mNotify.upload(100);
    }

    @Download.onTaskRunning
    public void onTaskRunning(DownloadTask task) {
        long len = task.getFileSize();
        int p = (int) (task.getCurrentProgress() * 100 / len);
        mNotify.upload(p);
    }
}