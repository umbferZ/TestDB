package com.example.urania.testdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Umberto Ferracci from urania on 29/05/16.
 * email:   umberto.ferracci@gmail.com
 * Package: com.example.urania.testdb
 * Project: TestDB
 * Name:    VivzDatabaseAdapter
 */
public class VivzDatabaseAdapter {

    VivzOpenHelper helper;


    public VivzDatabaseAdapter(Context context) {
        helper = new VivzOpenHelper(context);
    }

    public String getData(String name) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {VivzOpenHelper.c_name, VivzOpenHelper.c_password};
        String condition = VivzOpenHelper.c_name + " = '" + name + "'";
        Cursor cursor = db.query(VivzOpenHelper.TABLE_NAME, columns, condition, null, null, null, null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            int index1 = cursor.getColumnIndex(VivzOpenHelper.c_name);
            int index2 = cursor.getColumnIndex(VivzOpenHelper.c_password);
            String personName = cursor.getString(index1);
            String password = cursor.getString(index2);
            buffer.append("user: " + personName + ", pw: " + password + "\n");
        }
        return buffer.toString();
    }

    public String getAllData() {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {VivzOpenHelper.c_name, VivzOpenHelper.c_password};
        Cursor cursor = db.query(VivzOpenHelper.TABLE_NAME, columns, null, null, null, null, null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(VivzOpenHelper.c_name));
            String password = cursor.getString(cursor.getColumnIndex(VivzOpenHelper.c_password));
            buffer.append("user: " + name + ", pw: " + password + "\n");
        }
        return buffer.toString();
    }

    public long insertData(String name, String password) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(VivzOpenHelper.c_name, name);
        contentValues.put(VivzOpenHelper.c_password, password);
        return db.insert(VivzOpenHelper.TABLE_NAME, null, contentValues);
    }

    public int updateData(String oldName, String newName) {
        // UPDATE tableName set name='x' WHERE name='y'
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(VivzOpenHelper.c_name, newName);
        String whereClause = VivzOpenHelper.c_name + " =? ";
        String[] whereArgs = {oldName};
        return db.update(VivzOpenHelper.TABLE_NAME, values, whereClause, whereArgs);

    }

    public int deleteRow(String name) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String whereClause = VivzOpenHelper.c_name + " =? ";
        String[] whereArgs = {name};
        return db.delete(VivzOpenHelper.TABLE_NAME, whereClause, whereArgs);


    }

    static class VivzOpenHelper extends SQLiteOpenHelper {
        private static final String DB_NAME = "vivzdatabase.db";
        private static final String TABLE_NAME = "VIVZTABLE";
        private static final int DB_VERSION = 1;
        private static final String c_id = "_id";
        private static final String c_name = "name";
        private static final String c_password = "password";
        private Context context;


        public VivzOpenHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            this.context = context;
            MessageDebug.message(context, "constructor called");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                createTable(db);
                MessageDebug.message(context, "onCreate called");

            } catch (SQLException e) {
                MessageDebug.message(context, "Exception: " + e);
                e.printStackTrace();

            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                deleteTable(db);
                createTable(db);
                MessageDebug.message(context, "onUpgrade called");
            } catch (SQLException e) {
                MessageDebug.message(context, "Exception: " + e);
                e.printStackTrace();
            }
        }

        private void createTable(SQLiteDatabase db) {
            String qry = "CREATE TABLE " +
                    TABLE_NAME + " ( " +
                    c_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    c_name + " TEXT, " +
                    c_password + " TEXT " +
                    ");";
            db.execSQL(qry);
        }

        private void deleteTable(SQLiteDatabase db) {
            String qry = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
            db.execSQL(qry);
        }
    }
}