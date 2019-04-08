package cn.wangjianlog.socket;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

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

public class SocketService extends Service {

    private static final String TAG = "SocketService";

    private boolean isServiceDestoryed = false;
    private String[] definedMessages = new String[]{
            "你好啊，哈哈",
            "请问你叫什么名字",
            "今天北京天气不错啊"
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(getPackageName(), "cn.wangjianlog.aidlserver.MainActivity"));

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String channelStr = "CHANNEL_ID_STRING";
            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(channelStr, "Socket", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
            Notification notify = new Notification.Builder(this, channelStr)
                    // 设置状态栏中的小图片，尺寸一般建议在24×24，这个图片同样也是在下拉状态栏中所显示，如果在那里需要更换更大的图片，可以使用setLargeIcon(Bitmap icon)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setTicker("Service")// 设置在status
                    // bar上显示的提示文字
                    .setContentTitle("Notification Title")// 设置在下拉status
                    // bar后Activity，本例子中的NotififyMessage的TextView中显示的标题
                    .setContentText("This is Service")// TextView中显示的详细内容
                    // 关联PendingIntent
                    .setContentIntent(pendingIntent)
                    // 在TextView的右方显示的数字，可放大图片看，在最右侧。这个number同时也起到一个序列号的左右，如果多个触发多个通知（同一ID），可以指定显示哪一个。
                    .setNumber(1)
                    .build();

            startForeground(1, notify);
        }
        new Thread(new SocketServer()).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        isServiceDestoryed = true;
        super.onDestroy();
    }

    private class SocketServer implements Runnable {

        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8688);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            while (!isServiceDestoryed) {
                try {
                    final Socket client = serverSocket.accept();
                    Log.i(TAG, "accept");
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            try {
                                responseClient(client);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void responseClient(Socket client) throws IOException {
        //用于接收客户端消息
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        //用于向客户端发送消息
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
        out.println("欢迎来到聊天室");
        while (!isServiceDestoryed) {
            String str = in.readLine();
            Log.i(TAG, "msg from client:" + str);
            if (str == null) {
                //客户端断开连接
                break;
            }
            int i = new Random().nextInt(definedMessages.length);
            String msg = definedMessages[i];
            out.println(msg);
            Log.i(TAG, "send : " + msg);
        }

        Log.i(TAG, "client quit.");
        out.close();
        in.close();
        client.close();
    }
}
