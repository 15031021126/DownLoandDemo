package com.cc.servicedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.arialyy.aria.core.Aria;
import com.cc.servicedemo.service.MyIntentService;
import me.itangqi.waveloadingview.WaveLoadingView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class DownLoandActivity extends AppCompatActivity {

    @BindView(R.id.button)
    Button start;
    @BindView(R.id.button2)
    Button pause;
    @BindView(R.id.button4)
    Button jx;
    @BindView(R.id.button3)
    Button stop;
    @BindView(R.id.progres)
    TextView progress;
    @BindView(R.id.cc)
    WaveLoadingView mWave;
    MyIntentService service = new MyIntentService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_loand);
        ButterKnife.bind(this);
        Aria.download(this).register();
        //注册广播接收器
        MyReceiver myReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.cc.servicedemo.service.MyIntentService");
        registerReceiver(myReceiver, filter);


    }

    @OnClick({R.id.button, R.id.button2, R.id.button4, R.id.button3})
    public void onViewClicked(View view) {
        Intent intent = new Intent(this, MyIntentService.class);

        switch (view.getId()) {
            //开始下
            case R.id.button:
                startService(intent);
                break;
            //暂停
            case R.id.button2:
                service.pause();
                break;
            //继续
            case R.id.button4:
                startService(intent);
                break;
            //停止
            case R.id.button3:
                service.stop();
                break;
        }
    }

    /**
     * 获取广播数据
     *
     * @author jiqinlin
     */
    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            int count = bundle.getInt("count");
            progress.setText(count + "%");
            mWave.setProgressValue(count);
            mWave.setCenterTitle(count+"%");
            if(count==100){
                mWave.setAmplitudeRatio(0);
                mWave.stopNestedScroll();
            }
        }
    }


}
