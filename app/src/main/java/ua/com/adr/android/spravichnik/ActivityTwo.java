package ua.com.adr.android.spravichnik;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ActivityTwo extends AppCompatActivity {
    ListView spisok;
    private static final int CM_DELETE_ID = 1;
    DB db;
    SimpleCursorAdapter scAdapter;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

//        Intent intent = getIntent();
//        int clickLetter = intent.getIntExtra("OMG", 0);

        // открываем подключение к БД
        db = new DB(this);
        db.open();

        // получаем курсор
        cursor = db.getAllData();
        startManagingCursor(cursor);

        // формируем столбцы сопоставления
        String[] from = new String[] { DB.COLUMN_NAME};
        int[] to = new int[] { R.id.tvText };

        // создааем адаптер и настраиваем список
        scAdapter = new SimpleCursorAdapter(this, R.layout.item, cursor, from, to);
        spisok = (ListView) findViewById(R.id.lvCity);
        spisok.setAdapter(scAdapter);

        // добавляем контекстное меню к списку
        registerForContextMenu(spisok);
    }

    // обработка нажатия кнопки
    public void onButtonClick(View view) {
        // добавляем запись
        db.addRec("sometext " + (cursor.getCount() + 1), "sometext ");
        // обновляем курсор
        cursor.requery();
    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_DELETE_ID, 0, R.string.delete_record);
    }

    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CM_DELETE_ID) {
            // получаем из пункта контекстного меню данные по пункту списка
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            // извлекаем id записи и удаляем соответствующую запись в БД
            db.delRec(acmi.id);
            // обновляем курсор
            cursor.requery();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    protected void onDestroy() {
        super.onDestroy();
        // закрываем подключение при выходе
        db.close();
    }

}