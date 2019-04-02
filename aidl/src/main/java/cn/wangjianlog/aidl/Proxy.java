package cn.wangjianlog.aidl;

/**
 * <pre>
 * 业务名:
 * 功能说明:
 * 编写日期: 2019/4/2
 * 作者:	 WangJian
 * 备注:
 *
 * 历史记录
 * 1、修改日期：
 *    修改人：WangJian
 *    修改内容：
 * </pre>
 */

public class Proxy implements IBookManager {
    private android.os.IBinder mRemote;

    Proxy(android.os.IBinder remote) {
        mRemote = remote;
    }

    @Override
    public android.os.IBinder asBinder() {
        return mRemote;
    }

    public String getInterfaceDescriptor() {
        return Stub.DESCRIPTOR;
    }

    @Override
    public int count(int type) throws android.os.RemoteException {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
            _data.writeInterfaceToken(Stub.DESCRIPTOR);
            _data.writeInt(type);
            mRemote.transact(Stub.TRANSACTION_count, _data, _reply, 0);
            _reply.readException();
            _result = _reply.readInt();
        } finally {
            _reply.recycle();
            _data.recycle();
        }
        return _result;
    }

    @Override
    public java.util.List<Book> getBooks() throws android.os.RemoteException {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        java.util.List<Book> _result;
        try {
            _data.writeInterfaceToken(Stub.DESCRIPTOR);
            mRemote.transact(Stub.TRANSACTION_getBooks, _data, _reply, 0);
            _reply.readException();
            _result = _reply.createTypedArrayList(Book.CREATOR);
        } finally {
            _reply.recycle();
            _data.recycle();
        }
        return _result;
    }

    /**
     * 传参时除了Java基本类型以及String，CharSequence之外的类型
     * 都需要在前面加上定向tag，具体加什么量需而定
     */
    @Override
    public void addBookIn(Book book) throws android.os.RemoteException {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
            _data.writeInterfaceToken(Stub.DESCRIPTOR);
            if ((book != null)) {
                _data.writeInt(1);
                book.writeToParcel(_data, 0);
            } else {
                _data.writeInt(0);
            }
            mRemote.transact(Stub.TRANSACTION_addBookIn, _data, _reply, 0);
            _reply.readException();
        } finally {
            _reply.recycle();
            _data.recycle();
        }
    }

    @Override
    public void addBookOut(Book book) throws android.os.RemoteException {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
            _data.writeInterfaceToken(Stub.DESCRIPTOR);
            mRemote.transact(Stub.TRANSACTION_addBookOut, _data, _reply, 0);
            _reply.readException();
            if ((0 != _reply.readInt())) {
                book.readFromParcel(_reply);
            }
        } finally {
            _reply.recycle();
            _data.recycle();
        }
    }

    @Override
    public void addBookInout(Book book) throws android.os.RemoteException {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
            _data.writeInterfaceToken(Stub.DESCRIPTOR);
            if ((book != null)) {
                _data.writeInt(1);
                book.writeToParcel(_data, 0);
            } else {
                _data.writeInt(0);
            }
            mRemote.transact(Stub.TRANSACTION_addBookInout, _data, _reply, 0);
            _reply.readException();
            if ((0 != _reply.readInt())) {
                book.readFromParcel(_reply);
            }
        } finally {
            _reply.recycle();
            _data.recycle();
        }
    }
}
