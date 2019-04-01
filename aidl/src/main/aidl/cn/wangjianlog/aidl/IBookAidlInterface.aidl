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
    * 都需要在前面加上定向tag，具体加什么量需而定
    */

    void addBookIn(in Book book);

    void addBookOut(out Book book);

    void addBookInout(inout Book book);
}
