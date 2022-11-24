package com.example.lab_with_db;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import android.content.res.AssetManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.ArrayMap;
import android.util.Log;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class FileHelper {
    private final AssetManager assetManager;

    public FileHelper(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public String[] readTableNames() {
        String filename = "TablesM.txt";
        String[] tableNames = null;
        try {
            InputStream ip = assetManager.open(filename);
            int size = ip.available();
            byte[] buffer = new byte[size];
            ip.read(buffer);
            ip.close();
            String text = new String(buffer);
            tableNames = text.split("\n");
        } catch (Exception e) {
            Log.d("FileHelper", "readTableNames: " + e.getMessage());
        }
        return tableNames;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Map<String, String> readTableStructure(String tableName) {
        String filename = tableName + "s.txt";
        ArrayMap<String, String> tableStructure = new ArrayMap<>();
        try {
            InputStream ip = assetManager.open(filename);
            int size = ip.available();
            byte[] buffer = new byte[size];
            ip.read(buffer);
            ip.close();
            String text = new String(buffer);
            String[] tableStructureArray = text.split("\n");
            for (String s : tableStructureArray) {
                String[] pair = s.split("\t");
                tableStructure.put(pair[0], pair[1]);
            }
        } catch (Exception e) {
            Log.d("FileHelper", "readTableStructure: " + e.getMessage());
        }
        return tableStructure;
    }

    public String[] readColumns(String tableName) {
        String filename = tableName + ".txt";
        String[] firstLine = null;
        try {
            InputStream ip = assetManager.open(filename);
            int size = ip.available();
            byte[] buffer = new byte[size];
            ip.read(buffer);
            ip.close();
            String text = new String(buffer);
            String[] tableNames = text.split("\n");
            firstLine = tableNames[0].split("\t");
        } catch (Exception e) {
            Log.d("FileHelper", "readTableNames: " + e.getMessage());
        }
        return firstLine;
    }

    public String[] readValues(String tableName) {
        String filename = tableName + ".txt";
        String[] data = null;
        try {
            InputStream ip = assetManager.open(filename);
            int size = ip.available();
            byte[] buffer = new byte[size];
            ip.read(buffer);
            ip.close();
            String text = new String(buffer);
            String[] tableNames = text.split("\n");
            data = Arrays.copyOfRange(tableNames, 1, tableNames.length);
        } catch (Exception e) {
            Log.d("FileHelper", "readTableNames: " + e.getMessage());
        }
        return data;
    }
}

