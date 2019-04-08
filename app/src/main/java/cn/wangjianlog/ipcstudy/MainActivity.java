package cn.wangjianlog.ipcstudy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.wangjianlog.ipcstudy.impl.AIDLActivity;
import cn.wangjianlog.ipcstudy.impl.AIDLManagerActivity;
import cn.wangjianlog.ipcstudy.impl.MessengerActivity;
import cn.wangjianlog.ipcstudy.impl.ProviderActivity;
import cn.wangjianlog.ipcstudy.impl.SocketActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_aidl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AIDLActivity.start(v.getContext());
            }
        });

        findViewById(R.id.btn_aidl_custom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AIDLManagerActivity.start(v.getContext());
            }
        });

        findViewById(R.id.btn_messenger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessengerActivity.start(v.getContext());
            }
        });

        findViewById(R.id.btn_provider).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProviderActivity.start(v.getContext());
            }
        });

        findViewById(R.id.btn_socket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SocketActivity.start(v.getContext());
            }
        });
    }
}
