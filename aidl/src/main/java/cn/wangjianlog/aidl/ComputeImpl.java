package cn.wangjianlog.aidl;

import android.os.RemoteException;

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

public class ComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
