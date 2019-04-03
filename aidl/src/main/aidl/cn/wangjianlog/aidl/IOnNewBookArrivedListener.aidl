// IOnNewBookArrivedListener.aidl
package cn.wangjianlog.aidl;

import cn.wangjianlog.aidl.Book;

interface IOnNewBookArrivedListener {

    void onNewBookArrived(in Book newBook);

}
