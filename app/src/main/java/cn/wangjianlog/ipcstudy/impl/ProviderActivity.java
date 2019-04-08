package cn.wangjianlog.ipcstudy.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.List;

import cn.wangjianlog.aidl.Book;
import cn.wangjianlog.ipcstudy.R;
import cn.wangjianlog.ipcstudy.provider.ProviderHelper;

public class ProviderActivity extends AppCompatActivity {

    private static final String TAG = "ProviderActivity";

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ProviderActivity.class);
        context.startActivity(intent);
    }

    private ProviderHelper providerHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);

        findViewById(R.id.btn_find_books).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                providerHelper = new ProviderHelper(ProviderActivity.this);
                providerHelper.addBook();
                List<Book> books = providerHelper.findBooks();
                Log.i(TAG,"图书列表：" + books.toString());
            }
        });

    }
}
