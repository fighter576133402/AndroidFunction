// IBookAidlInterface.aidl
package cn.wangjianlog.aidl;

//导入所需要使用的非默认支持数据类型的包
import cn.wangjianlog.aidl.Book;

interface IBookAidlInterface {
    /**
     * 支持的类型： int long boolean float double String 序列号对象
     * 所有的返回值前都不需要加任何东西，不管是什么数据类型
     */
    int count(int type);

    List<Book> getBooks();

   /**
    * 传参时除了Java基本类型以及String，CharSequence之外的类型
    * in客户端的参数输入
    */

    void addBookIn(in Book book);

   /**
    * 服务端的参数输入
    *
    */
    void addBookOut(out Book book);
   /**
    * 这个可以叫输入输出参数，客户端可输入、服务端也可输入。
    * 客户端输入了参数到服务端后，服务端也可对该参数进行修改等，最后在客户端上得到的是服务端输出的参数
    */
    void addBookInout(inout Book book);
}
