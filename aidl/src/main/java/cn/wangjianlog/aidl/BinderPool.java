package cn.wangjianlog.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.concurrent.CountDownLatch;

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

public class BinderPool {

    private static final String TAG = "BinderPool";

    public static final int BINDER_NONE = -1;
    public static final int BINDER_BOOK = 0;
    public static final int BINDER_COMPUTE = 1;

    private Context mContext;
    private IBinderPool binderPool;
    private static volatile BinderPool instance;
    private CountDownLatch countDownLatch;

    private BinderPool(Context context) {
        mContext = context.getApplicationContext();
        connectBinderPoolService();
    }

    public static BinderPool getInstance(Context context) {
        if (instance == null) {
            synchronized (BinderPool.class) {
                if (instance == null) {
                    instance = new BinderPool(context);
                }
            }
        }
        return instance;
    }

    private synchronized void connectBinderPoolService() {
        countDownLatch = new CountDownLatch(1);
        Intent intent = new Intent();
        intent.setAction("cn.wangjianlog.aidlserver.BinderPoolService");
        intent.setPackage("cn.wangjianlog.aidlserver");
        mContext.bindService(intent, binderPoolConnection, Context.BIND_AUTO_CREATE);

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public IBinder queryBinder(int binderCode) {
        IBinder binder = null;
        try {
            if (binderPool != null) {
                binder = binderPool.queryBinder(binderCode);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return binder;
    }

    private ServiceConnection binderPoolConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binderPool = IBinderPool.Stub.asInterface(service);
            try {
                binderPool.asBinder().linkToDeath(deathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            binderPool.asBinder().unlinkToDeath(deathRecipient, 0);
            binderPool = null;
            connectBinderPoolService();
        }
    };

    public static class BinderPoolImpl extends IBinderPool.Stub {
        private Context mContext;

        public BinderPoolImpl(Context context) {
            super();
            mContext = context.getApplicationContext();
        }

        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            IBinder binder = null;
            switch (binderCode) {
                case BINDER_BOOK:
                    binder = new BookAidlInterfaceImpl(mContext);
                    break;
                case BINDER_COMPUTE:
                    binder = new ComputeImpl();
                    break;
                default:
                    break;
            }
            return binder;
        }
    }
}
