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
