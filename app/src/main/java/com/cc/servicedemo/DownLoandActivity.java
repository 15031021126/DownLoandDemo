package com.cc.servicedemo;

import android.content.Intent;
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
import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.common.RequestEnum;
import com.arialyy.aria.core.download.DownloadTask;
import com.cc.servicedemo.service.MyIntentService;
import com.cc.servicedemo.service.MyService;

public class DownLoandActivity extends AppCompatActivity {

    @BindView(R.id.button)
    Button start;
    @BindView(R.id.button2)
    Button pause;
    @BindView(R.id.button4)
    Button jx;
    @BindView(R.id.button3)
    Button stop;
    @BindView(R.id.progress)
    TextView progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_loand);
        ButterKnife.bind(this);
        Aria.download(this).register();
    }

    @OnClick({R.id.button, R.id.button2, R.id.button4, R.id.button3})
    public void onViewClicked(View view) {
        Intent intent = new Intent(this, MyIntentService.class);
        MyIntentService service = new MyIntentService();

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


}
