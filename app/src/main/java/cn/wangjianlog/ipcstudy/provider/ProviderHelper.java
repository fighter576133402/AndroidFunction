package cn.wangjianlog.ipcstudy.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import cn.wangjianlog.aidl.Book;

/**
 * <pre>
 * 业务名:
 * 功能说明:
 * 编写日期: 2019/4/4
 * 作者:	 WangJian
 * 备注:
 *
 * 历史记录
 * 1、修改日期：
 *    修改人：WangJian
 *    修改内容：
 * </pre>
 */

public class ProviderHelper {

    public static final String AUTHORITY = "cn.wangjianlog.bookprovider.BookProvider";

    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/book");
    public static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/user");

    public static final String BOOK_ID = "_id";
    public static final String BOOK_NAME = "name";

    private Context context;
    public ProviderHelper(Context context){
        this.context = context;
    }

    public void addBook(){
        ContentValues values = new ContentValues();
        values.put(BOOK_NAME,"编译原理");
        context.getContentResolver().insert(BOOK_CONTENT_URI,values);
    }

    public List<Book> findBooks(){
        List<Book> books = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(BOOK_CONTENT_URI,new String[]{BOOK_ID,BOOK_NAME},null,null,null);
        while (cursor!= null && cursor.moveToNext()){
            Book book = new Book();
            book.setBookId(cursor.getInt(cursor.getColumnIndex(BOOK_ID)));
            book.setBookName(cursor.getString(cursor.getColumnIndex(BOOK_NAME)));
            books.add(book);
        }
        if (cursor != null){
            cursor.close();
        }
        return books;
    }
    
}
