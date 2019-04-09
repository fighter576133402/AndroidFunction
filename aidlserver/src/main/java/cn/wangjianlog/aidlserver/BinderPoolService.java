package cn.wangjianlog.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import cn.wangjianlog.aidl.BinderPool;

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

public class BinderPoolService extends Service {

    private Binder binderPool = null;
    @Override
    public void onCreate() {
        super.onCreate();
        binderPool = new BinderPool.BinderPoolImpl(getApplicationContext());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binderPool;
    }
}
