package cn.wangjianlog.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import cn.wangjianlog.aidl.Book;
import cn.wangjianlog.aidl.IBookAidlInterface;
import cn.wangjianlog.aidl.IOnNewBookArrivedListener;

/**
 * <pre>
 * 业务名:
 * 功能说明:
 * 编写日期: 2019/3/29
 * 作者:	 WangJian
 * 备注:
 *
 * 历史记录
 * 1、修改日期：
 *    修改人：WangJian
 *    修改内容：
 * </pre>
 */

public class BookService extends Service {

    private static final String TAG = "BookService";

    private List<Book> books = new ArrayList<>();

    private AtomicBoolean isServiceDestory = new AtomicBoolean(false);

    private RemoteCallbackList<IOnNewBookArrivedListener> bookArrivedListeners = new RemoteCallbackList<>();

    IBookAidlInterface.Stub mStub = new IBookAidlInterface.Stub() {
        @Override
        public int count(int type) throws RemoteException {
            synchronized (BookService.this) {
                if (type == 0) {
                    return 100;
                } else {
                    return books.size();
                }
            }
        }

        @Override
        public List<Book> getBooks() throws RemoteException {
            synchronized (BookService.this) {
                return books;
            }
        }

        @Override
        public void addBookIn(Book book) throws RemoteException {
            serviceAddBookIn(book);
        }

        @Override
        public void addBookOut(Book book) throws RemoteException {
            synchronized (BookService.this) {
                books.add(book);
            }
        }

        @Override
        public void addBookInout(Book book) throws RemoteException {
            synchronized (BookService.this) {
                book.setBookName("编译原理");
                books.add(book);
            }
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            bookArrivedListeners.register(listener);
            printListenerCount("register");
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            bookArrivedListeners.unregister(listener);
            printListenerCount("unregister");
        }
    };

    private void serviceAddBookIn(Book book){
        synchronized (BookService.this) {
            books.add(book);
        }
    }

    private void printListenerCount(String tag){
        int count = bookArrivedListeners.beginBroadcast();
        Log.i(TAG,tag + " listener数量：" + count);
        bookArrivedListeners.finishBroadcast();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new ServiceWorker()).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mStub;
    }

    @Override
    public void onDestroy() {
        isServiceDestory.set(true);
        super.onDestroy();
    }

    private void onNewBookArrived(Book book) throws RemoteException{
        serviceAddBookIn(book);
        final  int N = bookArrivedListeners.beginBroadcast();
        for(int i = 0; i < N; i ++){
            IOnNewBookArrivedListener onNewBookArrivedListener = bookArrivedListeners.getBroadcastItem(i);
            if (onNewBookArrivedListener != null){
                onNewBookArrivedListener.onNewBookArrived(book);
            }
        }
        bookArrivedListeners.finishBroadcast();
    }

    private class ServiceWorker implements Runnable{

        @Override
        public void run() {
            while (!isServiceDestory.get()){

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Book newBook = new Book();
                newBook.setBookId(books.size());
                newBook.setBookName("Python基础");

                try {
                    onNewBookArrived(newBook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
