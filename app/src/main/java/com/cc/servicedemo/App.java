package com.cc.servicedemo;

import android.app.Application;
import com.arialyy.aria.core.Aria;

/*
 *@Auther:陈浩
 *@Date: 2019/9/11
 *@Time:16:22
 *@Description:${DESCRIPTION}
 * */public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Aria.init(this);
       //RxTool.init(this);

    }
}
