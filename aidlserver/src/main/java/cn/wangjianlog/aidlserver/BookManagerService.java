package cn.wangjianlog.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import cn.wangjianlog.aidl.Book;
import cn.wangjianlog.aidl.IBookManager;

/**
 * <pre>
 * 业务名:
 * 功能说明:
 * 编写日期: 2019/4/1
 * 作者:	 WangJian
 * 备注:
 *
 * 历史记录
 * 1、修改日期：
 *    修改人：WangJian
 *    修改内容：
 * </pre>
 */

public class BookManagerService extends Service {

    IBookManager.Stub mSub = new IBookManager.Stub() {
        @Override
        public int count(int type) throws RemoteException {
            return 1000;
        }

        @Override
        public List<Book> getBooks() throws RemoteException {
            return new ArrayList<>();
        }

        @Override
        public void addBookIn(Book book) throws RemoteException {

        }

        @Override
        public void addBookOut(Book book) throws RemoteException {

        }

        @Override
        public void addBookInout(Book book) throws RemoteException {

        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mSub;
    }
}
