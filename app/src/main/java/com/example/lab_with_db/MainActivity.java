package com.example.lab_with_db;

import android.app.Activity;
import android.content.ContentValues;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Map;

public class MainActivity extends Activity {
    AssetManager assetManager;
    private static final String DBName = "labIP.db";
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.assetManager = getAssets();
    }

    public void createDB(View v) {
        TextView createDBTextView = (TextView) findViewById(R.id.createDDResult);
        dbHelper = new DBHelper(this, DBName);
        db = dbHelper.getWritableDatabase();
        String message = "Create DB = " + DBName + " DB was created \nOR Opened the exiting one!";
        Log.d("[DEBUG]", message);
        createDBTextView.setText(message);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void createTAB(View v) {
        TextView createTABTextView = (TextView) findViewById(R.id.createTablesResult);
        createTABTextView.setText("");
        FileHelper fileHelper = new FileHelper(this.assetManager);
        String[] tableNames = fileHelper.readTableNames();
        for (String tableName : tableNames) {
            Map<String, String> columns = fileHelper.readTableStructure(tableName);
            String message = dbHelper.createTable(db, tableName, columns);
            createTABTextView.append(message);
        }
    }


    public void fillTables(View v) {
        FileHelper fileHelper = new FileHelper(this.assetManager);
        String[] tableNames = fileHelper.readTableNames();
        TextView fillTablesTextView = (TextView) findViewById(R.id.fillTablesResult);
        fillTablesTextView.setMovementMethod(new ScrollingMovementMethod());
        fillTablesTextView.setText("");
        for (String tableName : tableNames) {
            String[] columns = fileHelper.readColumns(tableName);
            String[] values = fileHelper.readValues(tableName);
            fillTablesTextView.append("Table: "+ tableName +"\n\n");
            String columnsString = Arrays.toString(columns);
            fillTablesTextView.append(columnsString + "\n\n");
            for (String value : values) {
                String[] valueArray = value.split("\t");
                Log.d("[DEBUG]", "valueArray = " + Arrays.toString(valueArray));
                Log.e("COLUMN", Arrays.toString(columns));
                String valuesString = Arrays.toString(valueArray);
                fillTablesTextView.append(valuesString + "\n\n");
                ContentValues cv = new ContentValues();
                for (int i = 0; i < columns.length; i++) {
                    cv.put(columns[i], valueArray[i]);
                }
                db.insert(tableName, null, cv);
            }
            fillTablesTextView.append("-------------------------------------------------------\n");
        }
    }

    public void showTables(View v) {
        TextView tv = (TextView) findViewById(R.id.showTablesResult);
        tv.setText("");
        String[] tableNames = dbHelper.getTableNames(db);
        Log.i("[INFO]", "Table names = " + Arrays.toString(tableNames));

        for (int i = 1; i < tableNames.length; i++) {
            Log.e("TABLE", tableNames[i]);
            tv.append(tableNames[i] + "\n");
            dbHelper.readTable(db, tableNames[i]);
        }
    }
}
