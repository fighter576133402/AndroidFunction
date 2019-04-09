package cn.wangjianlog.aidl;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <pre>
 * 业务名:
 * 功能说明:
 * 编写日期: 2019/4/8
 * 作者:	 WangJian
 * 备注:
 *
 * 历史记录
 * 1、修改日期：
 *    修改人：WangJian
 *    修改内容：
 * </pre>
 */

public class BookAidlInterfaceImpl extends IBookAidlInterface.Stub {

    private static final String TAG = "BookAidlInterfaceImpl";

    private List<Book> books = new ArrayList<>();

    private AtomicBoolean isServiceDestory = new AtomicBoolean(false);

    private RemoteCallbackList<IOnNewBookArrivedListener> bookArrivedListeners = new RemoteCallbackList<>();

    private Context mContext;

    public BookAidlInterfaceImpl(Context context) {
        mContext = context;
    }

    @Override
    public int count(int type) throws RemoteException {
        synchronized (this) {
            if (type == 0) {
                return 100;
            } else {
                return books.size();
            }
        }
    }

    @Override
    public List<Book> getBooks() throws RemoteException {
        synchronized (this) {
            return books;
        }
    }

    @Override
    public void addBookIn(Book book) throws RemoteException {
        synchronized (this) {
            books.add(book);
        }
    }

    @Override
    public void addBookOut(Book book) throws RemoteException {
        synchronized (this) {
            books.add(book);
        }
    }

    @Override
    public void addBookInout(Book book) throws RemoteException {
        synchronized (this) {
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

    @Override
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        // 权限验证的测试
        int check = mContext.checkCallingOrSelfPermission("cn.wangjianlog.aidlserver.BookService");
        if (check == PackageManager.PERMISSION_DENIED) {
            return false;
        }

        // 包名验证
        String packageName = null;
        String[] packages = mContext.getPackageManager().getPackagesForUid(getCallingUid());
        if (packages != null && packages.length > 0) {
            packageName = packages[0];
        }

        if (packageName != null && !packageName.startsWith("cn.wangjianlog")) {
            return false;
        }

        return super.onTransact(code, data, reply, flags);
    }

    private void printListenerCount(String tag) {
        int count = bookArrivedListeners.beginBroadcast();
        Log.i(TAG, tag + " listener数量：" + count);
        bookArrivedListeners.finishBroadcast();
    }
}
