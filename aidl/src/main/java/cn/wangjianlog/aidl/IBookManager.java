package cn.wangjianlog.aidl;

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

public interface IBookManager extends android.os.IInterface {
    /**
     * Local-side IPC implementation stub class.
     */
    public static abstract class Stub extends android.os.Binder implements IBookManager {
        private static final String DESCRIPTOR = "cn.wangjianlog.aidlserver.IBookManager";

        /**
         * Construct the stub at attach it to the interface.
         */
        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        /**
         * Cast an IBinder object into an cn.wangjianlog.aidlserver.IBookManager interface,
         * generating a proxy if needed.
         */
        public static IBookManager asInterface(android.os.IBinder obj) {
            if ((obj == null)) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (((iin != null) && (iin instanceof IBookManager))) {
                return ((IBookManager) iin);
            }
            return new Stub.Proxy(obj);
        }

        @Override
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override
        public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException {
            String descriptor = DESCRIPTOR;
            switch (code) {
                case INTERFACE_TRANSACTION: {
                    reply.writeString(descriptor);
                    return true;
                }
                case TRANSACTION_count: {
                    data.enforceInterface(descriptor);
                    int _arg0;
                    _arg0 = data.readInt();
                    int _result = this.count(_arg0);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                }
                case TRANSACTION_getBooks: {
                    data.enforceInterface(descriptor);
                    java.util.List<Book> _result = this.getBooks();
                    reply.writeNoException();
                    reply.writeTypedList(_result);
                    return true;
                }
                case TRANSACTION_addBookIn: {
                    data.enforceInterface(descriptor);
                    Book _arg0;
                    if ((0 != data.readInt())) {
                        _arg0 = Book.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    this.addBookIn(_arg0);
                    reply.writeNoException();
                    return true;
                }
                case TRANSACTION_addBookOut: {
                    data.enforceInterface(descriptor);
                    Book _arg0;
                    _arg0 = new Book();
                    this.addBookOut(_arg0);
                    reply.writeNoException();
                    if ((_arg0 != null)) {
                        reply.writeInt(1);
                        _arg0.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                }
                case TRANSACTION_addBookInout: {
                    data.enforceInterface(descriptor);
                    Book _arg0;
                    if ((0 != data.readInt())) {
                        _arg0 = Book.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    this.addBookInout(_arg0);
                    reply.writeNoException();
                    if ((_arg0 != null)) {
                        reply.writeInt(1);
                        _arg0.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                }
                default: {
                    return super.onTransact(code, data, reply, flags);
                }
            }
        }

        private static class Proxy implements IBookManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder remote) {
                mRemote = remote;
            }

            @Override
            public android.os.IBinder asBinder() {
                return mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override
            public int count(int type) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                int _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
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
                    _data.writeInterfaceToken(DESCRIPTOR);
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
                    _data.writeInterfaceToken(DESCRIPTOR);
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
                    _data.writeInterfaceToken(DESCRIPTOR);
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
                    _data.writeInterfaceToken(DESCRIPTOR);
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

        static final int TRANSACTION_count = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
        static final int TRANSACTION_getBooks = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
        static final int TRANSACTION_addBookIn = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
        static final int TRANSACTION_addBookOut = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
        static final int TRANSACTION_addBookInout = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
    }

    public int count(int type) throws android.os.RemoteException;

    public java.util.List<Book> getBooks() throws android.os.RemoteException;

    /**
     * 传参时除了Java基本类型以及String，CharSequence之外的类型
     * 都需要在前面加上定向tag，具体加什么量需而定
     */
    public void addBookIn(Book book) throws android.os.RemoteException;

    public void addBookOut(Book book) throws android.os.RemoteException;

    public void addBookInout(Book book) throws android.os.RemoteException;
}
