package cn.wangjianlog.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <pre>
 * 业务名:
 * 功能说明:
 * 编写日期: 2019/3/30
 * 作者:	 WangJian
 * 备注:
 *
 * 历史记录
 * 1、修改日期：
 *    修改人：WangJian
 *    修改内容：
 * </pre>
 */

public class Book implements Parcelable {

    private int bookId;

    private String bookName;

    public Book(){

    }

    public Book(Parcel in) {
        bookId = in.readInt();
        bookName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookId);
        dest.writeString(bookName);
    }

    /**
     * 参数是一个Parcel,用它来存储与传输数据
     * @param dest
     */
    public void readFromParcel(Parcel dest) {
        //注意，此处的读值顺序应当是和writeToParcel()方法中一致的
        bookId = dest.readInt();
        bookName = dest.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public int getBookId() {
        return bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                '}';
    }
}
