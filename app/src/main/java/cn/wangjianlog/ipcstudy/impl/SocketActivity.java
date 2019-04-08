package cn.wangjianlog.ipcstudy.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import cn.wangjianlog.ipcstudy.R;

public class SocketActivity extends AppCompatActivity {
    private static final String TAG = "SocketClient";
    private static final int MESSAGE_RECEIVE_NEW_MSG = 1;
    private static final int MESSAGE_SOCKET_CONNECTED = 2;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_RECEIVE_NEW_MSG:
                    tv_message.setText(tv_message.getText() + (String) msg.obj);
                    break;
                case MESSAGE_SOCKET_CONNECTED:
                    btn_send.setEnabled(true);
                    break;
            }
        }
    };

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, SocketActivity.class);
        context.startActivity(intent);
    }

    private TextView tv_message;
    private Button btn_send;
    private EditText et_input;

    private PrintWriter printWriter;
    private Socket clientSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        initView();
    }

    private void initView() {
        tv_message = (TextView) findViewById(R.id.tv_message);
        btn_send = (Button) findViewById(R.id.btn_send);
        et_input = (EditText) findViewById(R.id.et_input);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String input = et_input.getText().toString().trim();
                if (!TextUtils.isEmpty(input) && printWriter != null) {
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            printWriter.println(input);
                        }
                    }.start();

                }
                et_input.setText("");
                String showMsg = "self :" + input + "\n";
                tv_message.setText(new StringBuilder().append(tv_message.getText()).append(showMsg).toString());
            }
        });

        findViewById(R.id.btn_start_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService();
            }
        });

        findViewById(R.id.btn_start_socket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        connectSocketServer();
                    }
                }.start();
            }
        });

    }

    private void startService() {
        Intent intent = new Intent();
        intent.setAction("cn.wangjianlog.socket.SocketService");
        intent.setPackage("cn.wangjianlog.aidlserver");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (clientSocket != null) {
            try {
                clientSocket.shutdownInput();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void connectSocketServer() {
        Socket socket = null;
        while (socket == null) {
            try {
                socket = new Socket("localhost", 8688);
                clientSocket = socket;
                printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                handler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);
                Log.i(TAG, "connect server success");
            } catch (IOException e) {
                SystemClock.sleep(1000);
                e.printStackTrace();
                Log.i(TAG, "connect server failed, retry...");
            }
        }

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (!isFinishing()) {
                String msg = br.readLine();
                Log.i(TAG, "receive :" + msg);
                if (msg != null) {
                    String showMsg = "server:" + msg + "\n";
                    handler.obtainMessage(MESSAGE_RECEIVE_NEW_MSG, showMsg).sendToTarget();
                }
            }
            Log.i(TAG, "quit...");
            printWriter.close();
            br.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
