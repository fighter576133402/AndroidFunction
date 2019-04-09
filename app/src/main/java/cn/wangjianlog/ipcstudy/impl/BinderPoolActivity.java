package cn.wangjianlog.ipcstudy.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import cn.wangjianlog.aidl.BinderPool;
import cn.wangjianlog.aidl.Book;
import cn.wangjianlog.aidl.BookAidlInterfaceImpl;
import cn.wangjianlog.aidl.ComputeImpl;
import cn.wangjianlog.aidl.IBookAidlInterface;
import cn.wangjianlog.aidl.ICompute;
import cn.wangjianlog.ipcstudy.R;

public class BinderPoolActivity extends AppCompatActivity {

    private static final String TAG = "BinderPoolActivity";

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, BinderPoolActivity.class);
        context.startActivity(intent);
    }

    private IBookAidlInterface bookInterface;
    private ICompute computeInterface;

    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_pool);
        new Thread(new BinderPoolWork()).start();
    }

    private class BinderPoolWork implements Runnable{

        @Override
        public void run() {
            BinderPool binderPool = BinderPool.getInstance(BinderPoolActivity.this);
            IBinder bookBinder = binderPool.queryBinder(BinderPool.BINDER_BOOK);
            bookInterface = BookAidlInterfaceImpl.asInterface(bookBinder);

            try {
                Book book = new Book();
                book.setBookName("编程珠玑");
                book.setBookId(index++);
                bookInterface.addBookIn(book);

                List<Book> books =  bookInterface.getBooks();
                Log.i(TAG,"图书列表： " + books.toString());


            } catch (RemoteException e) {
                e.printStackTrace();
            }

            IBinder computeBinder = binderPool.queryBinder(BinderPool.BINDER_COMPUTE);
            computeInterface = ComputeImpl.asInterface(computeBinder);
            try {
                Log.i(TAG,"加法运算:" + computeInterface.add(10,20));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
