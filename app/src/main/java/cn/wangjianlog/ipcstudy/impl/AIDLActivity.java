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
import cn.wangjianlog.aidl.IOnNewBookArrivedListener;
import cn.wangjianlog.ipcstudy.R;

public class AIDLActivity extends AppCompatActivity {

    private static final String TAG = "AIDLActivity";

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, AIDLActivity.class);
        context.startActivity(intent);
    }

    private IBookAidlInterface bookAidlInterface;

    private TextView tv_show_info;

    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);

        tv_show_info = (TextView) findViewById(R.id.tv_show_info);

        bindService();

        // 获取图书数量
        findViewById(R.id.btn_get_count).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bookAidlInterface != null) {
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
                if (bookAidlInterface != null) {
                    try {
                        Book book = new Book();
                        book.setBookId(index++);
                        book.setBookName("算法导论");
                        //Log.i(TAG,"添加前：" + book.toString());
                        bookAidlInterface.addBookInout(book);
                        //Log.i(TAG,"添加后：" + book.toString());
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
                    if (bookAidlInterface != null) {
                        //TODO 如果远程服务比较耗时需要开启线程调用
                        List<Book> books = bookAidlInterface.getBooks();
                        Log.i(TAG, "books:" + books);
                        showBooks(books);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void bindService(){
        Intent intent = new Intent();
        intent.setAction("cn.wangjianlog.aidlserver.BookService");
        intent.setPackage("cn.wangjianlog.aidlserver");
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    private void showBooks(List<Book> books) {
        if (books != null) {
            StringBuilder builder = new StringBuilder();
            for (Book book : books) {
                builder.append(book.toString()).append("\n");
            }
            tv_show_info.setText(builder);
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bookAidlInterface = IBookAidlInterface.Stub.asInterface(service);
            if (bookAidlInterface == null) {
                Log.e("MainActivity", "the mStub is null");
            } else {
                try {
                    service.linkToDeath(deathRecipient,0);
                    int value = bookAidlInterface.count(0);
                    tv_show_info.setText("绑定成功");
                    bookAidlInterface.registerListener(onNewBookArrivedListener);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    IOnNewBookArrivedListener onNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            Log.i(TAG,"有新书来了：" + newBook.toString());

            //TODO 这里如果要做UI更新需要切换到UI线程
        }
    };

    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (bookAidlInterface == null) {
                return;
            }

            bookAidlInterface.asBinder().unlinkToDeath(deathRecipient,0);
            bookAidlInterface = null;

            //TODO 重新绑定远程服务

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bookAidlInterface != null && bookAidlInterface.asBinder().isBinderAlive()){
            try {
                bookAidlInterface.unregisterListener(onNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            unbindService(serviceConnection);
        }

    }
}
