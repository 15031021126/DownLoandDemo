package com.cc.servicedemo.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import androidx.core.app.NotificationCompat;
import com.cc.servicedemo.MainActivity;
import com.cc.servicedemo.R;

/*
 *@Auther:陈浩
 *@Date: 2019/9/11
 *@Time:17:08
 *@Description:${DESCRIPTION}
 * */public class DownloadNotification {

    private NotificationManager mManager;
    private Context mContext;
    private NotificationCompat.Builder mBuilder;
    private static final int mNotifiyId = 0;

    public DownloadNotification(Context context) {
        mContext = context;
        init();
    }

    private void init() {
        mManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(mContext);
        mBuilder.setContentTitle("正在下载")
                .setContentText("下载中...")
                .setProgress(100, 0, false)
                .setSmallIcon(R.mipmap.ic_launcher_round);
        mManager.notify(mNotifiyId, mBuilder.build());
    }

    /**
     * 更新进度
     * @param progress
     */
    public void upload(int progress) {
        if (mBuilder != null) {
            mBuilder.setProgress(100, progress, false);
            mManager.notify(mNotifiyId, mBuilder.build());
        }
    }

    /**
     * 更新uI
     * @param contentTitle
     * @param ContentText
     * @param SmallIcon
     * @param progress
     */
    public void uploadUi(String contentTitle, String ContentText, int SmallIcon, int progress) {
        mBuilder.setContentTitle(contentTitle)
                .setContentText(ContentText)
                .setProgress(100, progress, false)
                .setSmallIcon(SmallIcon);
        mManager.notify(mNotifiyId, mBuilder.build());
    }

    public void uploadUi(String contentTitle, String ContentText, int progress) {
        mBuilder.setContentTitle(contentTitle)
                .setContentText(ContentText)
                .setProgress(100, progress, false)
                .setSmallIcon(R.mipmap.ic_launcher_round);
        mManager.notify(mNotifiyId, mBuilder.build());
    }

    public void uploadUi(String contentTitle, String ContentText) {
        mBuilder.setContentTitle(contentTitle)
                .setContentText(ContentText)
                .setSmallIcon(R.mipmap.ic_launcher_round);
        mManager.notify(mNotifiyId, mBuilder.build());
    }


}
