package com.example.lab_with_db;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;


import java.util.Map;

class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public DBHelper(Context context, String DBName) {
        super(context, DBName, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("DBHelper", "onCreate");
    }

    public String createTable(SQLiteDatabase sqLiteDatabase, String tableName, Map<String, String> columns) {
        // DROP TABLE IF EXISTS
        String dropSql = "DROP TABLE IF EXISTS " + tableName;
        sqLiteDatabase.execSQL(dropSql);

        Log.d("DBHelper", "createTable");
        StringBuilder sql = new StringBuilder("CREATE TABLE " + tableName + " (");
        for (Map.Entry<String, String> entry : columns.entrySet()) {
            sql.append(entry.getKey()).append(" ").append(entry.getValue()).append(", ");
        }
        sql = new StringBuilder(sql.substring(0, sql.length() - 2));
        sql.append(");");
        Log.e("DBHelper", "SQL: " + sql);
        sqLiteDatabase.execSQL(sql.toString());
        Log.d("DBHelper", "TABLE CREATED: " + sql);
        return "Table " + tableName + " was created!\n";
    }

    public String[] getTableNames(SQLiteDatabase sqLiteDatabase) {
        @SuppressLint("Recycle")
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        String[] tableNames = new String[cursor.getCount()];
        int i = 0;
        while (cursor.moveToNext()) {
            tableNames[i] = cursor.getString(0);
            i++;
        }
        return tableNames;
    }

    @SuppressLint("Range")
    public void readTable(SQLiteDatabase sqLiteDatabase, String tableName) {
        String sql = "SELECT * FROM " + tableName;
        @SuppressLint("Recycle")
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        Log.d("DBHelper", "readTable");
        while (cursor.moveToNext()) {
            String[] columnNames = cursor.getColumnNames();
            for (String columnName : columnNames) {
                Log.d("DBHelper", columnName + " = " + cursor.getString(cursor.getColumnIndex(columnName)));
            }
            Log.e("===========", "================");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
