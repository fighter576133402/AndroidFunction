package cn.wangjianlog.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

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

public class MessengerService extends Service {

    private static final int MSG_BOOK_COUNT = 0x110;

    private static class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //返回给客户端的消息
            Message msgToClient = Message.obtain(msg);
            switch (msg.what) {
                //msg 客户端传来的消息
                case MSG_BOOK_COUNT:
                    msgToClient.what = MSG_BOOK_COUNT;
                    try {
                        msgToClient.arg1 = 100;
                        Bundle bundle = new Bundle();
                        bundle.putString("reply","我收到你的信息了");
                        msgToClient.setData(bundle);
                        msg.replyTo.send(msgToClient);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    private Messenger messenger = new Messenger(new MessengerHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
