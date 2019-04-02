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

public abstract class Stub extends android.os.Binder implements IBookManager {
    protected static final String DESCRIPTOR = "cn.wangjianlog.aidlserver.IBookManager";

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
        return new Proxy(obj);
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



    static final int TRANSACTION_count = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_getBooks = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    static final int TRANSACTION_addBookIn = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
    static final int TRANSACTION_addBookOut = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
    static final int TRANSACTION_addBookInout = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
}
