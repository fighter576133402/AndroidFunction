package cn.wangjianlog.ipcstudy.impl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import cn.wangjianlog.ipcstudy.R;

public class MessengerActivity extends AppCompatActivity {
    private static final String TAG = "MessengerActivity";

    private Messenger mService;
    private boolean isConn;
    private TextView tv_connect_status;

    private static final int MSG_BOOK_COUNT = 0x110;

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MessengerActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        tv_connect_status = (TextView) findViewById(R.id.tv_connect_status);
        findViewById(R.id.btn_send_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (mService != null) {
                        Message message = Message.obtain(null, MSG_BOOK_COUNT);
                        message.replyTo = messenger;
                        mService.send(message);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        bindService();
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setAction("cn.wangjianlog.aidlserver.MessengerService");
        intent.setPackage("cn.wangjianlog.aidlserver");
        bindService(intent, mConn, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConn);
    }

    public void setStatus(String status){
        tv_connect_status.setText(status);
    }


    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
            isConn = true;
            setStatus("connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            isConn = false;
            setStatus("disconnected");
        }
    };

    private static class MessengerHandler extends Handler{

        private WeakReference<MessengerActivity> weakReference;

        public MessengerHandler(MessengerActivity messengerActivity){
            weakReference = new WeakReference<>(messengerActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_BOOK_COUNT:
                    Bundle bundle = msg.getData();
                    String reply = bundle.getString("reply");
                    MessengerActivity messengerActivity = weakReference.get();
                    if (messengerActivity != null){
                        messengerActivity.setStatus(reply);
                    }
                    break;
            }
        }
    }

    private Messenger messenger = new Messenger(new MessengerHandler(this));


}
