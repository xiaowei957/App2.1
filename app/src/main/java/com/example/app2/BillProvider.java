package com.example.app2;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class BillProvider extends ContentProvider {
    //声明UriMatcher对象
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    //声明并初始化权限
    private static String authority = "com.thundersoft.login.billProvider";
    //初始化匹配码
    private static final int USER_CODE = 1;
    //声明数据库
    private DBManager myDataBase;
    //声明管理数据库
    private SQLiteDatabase db;

    //当MATCHER调用match()方法时，会进行匹配，并返回相应的自定义匹配码，根据匹配码进行操作
    static {
        MATCHER.addURI(authority, null, USER_CODE);
    }

    //创建或者打开数据库，只执行一次。当指定数据库名的数据库不存在时，创建新的数据库，当存在时，打开已有数据库
    @Override
    public boolean onCreate() {
        myDataBase = new DBManager(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        db = myDataBase.getWritableDatabase();
        return db.query("michilay", new String[]{"time", "money"}, null, null, null, null, sortOrder);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        db = myDataBase.getWritableDatabase();
        db.insert("michilay", null, values);
        db.close();
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
