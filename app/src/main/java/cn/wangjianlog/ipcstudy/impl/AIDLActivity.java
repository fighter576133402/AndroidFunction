package cn.wangjianlog.ipcstudy.impl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import cn.wangjianlog.aidl.Book;
import cn.wangjianlog.aidl.IBookAidlInterface;
import cn.wangjianlog.ipcstudy.R;

public class AIDLActivity extends AppCompatActivity {

    private static final String TAG = "AIDLActivity";

    public static void start(Context context){
        Intent intent = new Intent();
        intent.setClass(context,AIDLActivity.class);
        context.startActivity(intent);
    }

    private IBookAidlInterface bookAidlInterface;

    private TextView tv_show_info;

    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);

        tv_show_info = (TextView)findViewById(R.id.tv_show_info);

        // 绑定服务
        findViewById(R.id.btn_bind_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //由于是隐式启动Service 所以要添加对应的action，A和之前服务端的一样。
                intent.setAction("cn.wangjianlog.aidlserver.bookservice");
                //android 5.0以后直设置action不能启动相应的服务，需要设置packageName或者Component。
                intent.setPackage("cn.wangjianlog.aidlserver"); //packageName 需要和服务端的一致.
                bindService(intent, serviceConnection, BIND_AUTO_CREATE);
            }
        });

        // 获取图书数量
        findViewById(R.id.btn_get_count).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bookAidlInterface != null){
                    try {
                        int count = bookAidlInterface.count(1);
                        tv_show_info.setText(String.valueOf(count));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        findViewById(R.id.btn_add_book).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bookAidlInterface != null){
                    try {
                        Book book = new Book();
                        book.setBookId(index++);
                        book.setBookName("白夜行");
                        bookAidlInterface.addBookIn(book);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        findViewById(R.id.btn_get_book_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (bookAidlInterface != null){
                        List<Book> books = bookAidlInterface.getBooks();
                        Log.i(TAG,"books:" + books);
                        showBooks(books);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void showBooks(List<Book> books){
        if (books != null){
            StringBuilder builder = new StringBuilder();
            for (Book book : books){
                builder.append(book.toString()).append("\n");
            }
            tv_show_info.setText(builder);
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //调用asInterface()方法获得IMyAidlInterface实例
            bookAidlInterface = IBookAidlInterface.Stub.asInterface(service);
            if (bookAidlInterface == null) {
                Log.e("MainActivity", "the mStub is null");
            } else {
                try {
                    int value = bookAidlInterface.count(0);
                    tv_show_info.setText("绑定成功");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
