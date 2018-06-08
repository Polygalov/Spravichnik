package ua.com.adr.android.spravichnik;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ActivityTwo extends AppCompatActivity {
    String[] letters = { "А", "Б", "В", "Г", "Д", "Е", "Ё", "Ж", "З", "И", "Й", "К",
            "Л", "М", "Н", "О", "П" };
    ListView spisok;
    DB db;
    SimpleCursorAdapter scAdapter;
    Cursor cursor;
    final String LOG_TAG = "myLogs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        Intent intent = getIntent();
        int clickLetter = intent.getIntExtra("OMG", 0);

        // открываем подключение к БД
        db = new DB(this);
        db.open();

        // получаем курсор
        cursor = db.getSepficItem(letters[clickLetter]);

        logCursor(cursor);
        Log.d(LOG_TAG, "Cursor is null22");
        startManagingCursor(cursor);


        // формируем столбцы сопоставления
        String[] from = new String[] { DB.COLUMN_NAME};
        int[] to = new int[] { R.id.tvText };

        // создааем адаптер и настраиваем список
        scAdapter = new SimpleCursorAdapter(this, R.layout.item, cursor, from, to);
        spisok = (ListView) findViewById(R.id.lvCity);
        spisok.setAdapter(scAdapter);

        spisok.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String item_content = "";
                cursor.moveToFirst();
                for (int i = 0; i <= position; i++) {
                    item_content = cursor.getString(cursor
                            .getColumnIndex(DB.COLUMN_NAME));
                    cursor.moveToNext();
                }
                Intent intent = new Intent(ActivityTwo.this, DetailActivity.class);
                intent.putExtra("WOW", item_content);
                startActivity(intent);

            }
        });

    }

    // вывод в лог данных из курсора
    void logCursor(Cursor c) {
        if (c != null) {
            if (c.moveToFirst()) {
                String str;
                do {
                    str = "";
                    for (String cn : c.getColumnNames()) {
                        str = str.concat(cn + " = " + c.getString(c.getColumnIndex(cn)) + "; ");
                    }
                    Log.d(LOG_TAG, str);
                } while (c.moveToNext());
            }
        } else
            Log.d(LOG_TAG, "Cursor is null");
    }


    protected void onDestroy() {
        super.onDestroy();
        // закрываем подключение при выходе
        db.close();
    }

}