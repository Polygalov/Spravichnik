package ua.com.adr.android.spravichnik;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Andy on 06.06.2018.
 */

public class DB {

    private static final String DB_NAME = "mydb";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "mytab";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "img";
    public static final String COLUMN_DESC = "txt";

    private static final String DB_CREATE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_NAME + " text, " +
                    COLUMN_DESC + " text" +
                    ");";

    private final Context mCtx;


    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public DB(Context ctx) {
        mCtx = ctx;
    }

    // открыть подключение
    public void open() {
        mDBHelper = new DBHelper(mCtx);
        mDB = mDBHelper.getWritableDatabase();
    }

    // закрыть подключение
    public void close() {
        if (mDBHelper != null) mDBHelper.close();
    }

    // получить все данные из таблицы DB_TABLE
    public Cursor getAllData() {
        return mDB.query(DB_TABLE, null, null, null, null, null, null);
    }
  //  String query = "SELECT * FROM mytab WHERE " + COLUMN_NAME + " LIKE 'A%' ";
   // String query = "SELECT " + COLUMN_NAME + " FROM mytab";
    // String query = "SELECT * FROM mytab WHERE " + COLUMN_NAME + " LIKE 'А%'";

    public  Cursor getSepficItem(String letter){
        return  mDB.rawQuery("SELECT * FROM mytab WHERE " + COLUMN_NAME + " LIKE '" + letter + "%'", null);
    }

    // добавить запись в DB_TABLE
    public void addRec(String txt, String desc) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, txt);
        cv.put(COLUMN_DESC, desc);
        mDB.insert(DB_TABLE, null, cv);
    }

    // удалить запись из DB_TABLE
    public void delRec(long id) {
        mDB.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
    }

    // класс по созданию и управлению БД
    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "myDB", null, 1);
        }

//        public DBHelper(Context ctx, String dbName, Context context, int dbVersion) {
//            // конструктор суперкласса
//            super(context, "myDB", null, 1);
//        }
        // создаем и заполняем БД
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);

            ContentValues cv = new ContentValues();

            cv.put(COLUMN_NAME, "Астрахань ");
            cv.put(COLUMN_DESC, "город в...");
            db.insert(DB_TABLE, null, cv);

            cv.put(COLUMN_NAME, "Архангельск ");
            cv.put(COLUMN_DESC, "город в...");
            db.insert(DB_TABLE, null, cv);

            cv.put(COLUMN_NAME, "Брянск ");
            cv.put(COLUMN_DESC, "город в...");
            db.insert(DB_TABLE, null, cv);

            cv.put(COLUMN_NAME, "Воронеж ");
            cv.put(COLUMN_DESC, "город в...");
            db.insert(DB_TABLE, null, cv);

        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}