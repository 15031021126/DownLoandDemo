package com.cc.servicedemo.untils;

/*
 *@Auther:陈浩
 *@Date: 2019/9/11
 *@Time:16:48
 *@Description:${DESCRIPTION}
 * */

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.common.RequestEnum;
import com.arialyy.aria.core.download.DownloadTask;
import com.arialyy.aria.util.CommonUtil;

import java.io.File;

public class AnyRunnModule {
    String TAG = "AnyRunnModule";
    private Context mContext;
    private String mUrl;

    public AnyRunnModule(Context context) {
        Aria.download(this).register();
        mContext = context;
    }

    @Download.onWait void onWait(DownloadTask task) {
        Log.d(TAG, "wait ==> " + task.getDownloadEntity().getFileName());
    }

    @Download.onPre protected void onPre(DownloadTask task) {
        Log.d(TAG, "onPre");
    }

    @Download.onTaskStart void taskStart(DownloadTask task) {
        Log.d(TAG, "onStart");
    }

    @Download.onTaskRunning protected void running(DownloadTask task) {
        Log.d(TAG, "running");
    }

    @Download.onTaskResume void taskResume(DownloadTask task) {
        Log.d(TAG, "resume");
    }

    @Download.onTaskStop void taskStop(DownloadTask task) {
        Log.d(TAG, "stop");
    }

    @Download.onTaskCancel void taskCancel(DownloadTask task) {
        Log.d(TAG, "cancel");
    }

    @Download.onTaskFail void taskFail(DownloadTask task) {
        Log.d(TAG, "fail");
    }

    @Download.onTaskComplete void taskComplete(DownloadTask task) {
        Log.d(TAG, "path ==> " + task.getDownloadEntity().getDownloadPath());
        Log.d(TAG, "md5Code ==> " + CommonUtil.getFileMD5(new File(task.getDownloadPath())));
    }


    void start(String url) {
        mUrl = url;
        Aria.download(this)
                .load(url)
                .addHeader("Accept-Encoding", "gzip, deflate")
                //.setRequestMode(RequestEnum.GET)
                .setFilePath(Environment.getExternalStorageDirectory().getPath() + "/service.apk")
                .resetState()
                .start();
    }

    void stop() {
        Aria.download(this).load(mUrl).stop();
    }

    void cancel() {
        Aria.download(this).load(mUrl).cancel();
    }

    void unRegister() {
        Aria.download(this).unRegister();
    }
}

