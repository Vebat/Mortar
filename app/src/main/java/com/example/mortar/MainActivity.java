package com.example.mortar;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDBHelper = new DatabaseHelper(this);

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

    }


    public void buttonclicked(View view) {
        // Создание элементов интерфейса в коде
        TextView distance = findViewById(R.id.distance);
        TextView scopediv = findViewById(R.id.scopediv);
        TextView scopethous = findViewById(R.id.scopethosands);
        TextView time = findViewById(R.id.time);
        TextView ratioup = findViewById(R.id.ratioup);
        TextView ratiodown = findViewById(R.id.ratiodown);
        EditText stringn = findViewById(R.id.stringnumber);
        TextView typev = findViewById(R.id.typev);
        // Создание переменных и списка для получения дальности
        double difratio;
        int listn = 0;
        double distdif1 = 0;
        double distdif2 = 0;
        double scopedivdif1 = 0;
        double scopedivdif2 = 0;
        double scopethdif1 = 0;
        double scopethdif2 = 0;
        double timedif1 = 0;
        double timedif2 = 0;
        double ratioupdif1 = 0;
        double ratioupdif2 = 0;
        double ratiodowndif1 = 0;
        double ratiodowndif2 = 0;
        String dist = "";
        String scoped = "";
        String scopet = "";
        String t = "";
        String ratiou = "";
        String ratiod = "";

        ArrayList<Integer> ndist = new ArrayList<>();

        // Получение данных из EditText
        String in = stringn.getText().toString();
        int i = Integer.parseInt(in);
        if (i > 3922 | i < 91) {
            distance.setText("Дальность вне диапазона действия");
        } else {
            String type = "dalnoboi";
            String typeview = null;
            if (i > 3107) {
                type = "dalnoboi";
            }
            if (i > 2336 & i <= 3107) {
                type = "tretiy";
                typeview = "Тип заряда: третий";
            }
            if (i > 1478 & i <= 2336) {
                type = "vtoroi";
                typeview = "Тип заряда: второй";
            }
            if (i > 538 & i <= 1478) {
                type = "perviy";
                typeview = "Тип заряда: первый";
            }
            if (i > 91 & i <= 538) {
                type = "main";
                typeview = "Тип заряда: основной";
            }


            Cursor cursor = mDb.rawQuery("SELECT * FROM " + type, null);


            // Открытие таблицы с данными
          /*  if(type=="dalnoboi"){
                cursor = mDb.rawQuery("SELECT * FROM dalnoboi ", null);
            }if(type=="tretiy"){
              cursor = mDb.rawQuery("SELECT * FROM tretiy ", null);
            }if(type=="vtoroi"){
                cursor = mDb.rawQuery("SELECT * FROM vtoroi ", null);
            }if(type=="perviy"){
                cursor = mDb.rawQuery("SELECT * FROM perviy ", null);
            }if(type=="main"){
                cursor = mDb.rawQuery("SELECT * FROM main ", null);
            } */

            cursor.moveToFirst();
            // Заполнение списка
            while (!cursor.isAfterLast()) {
                ndist.add(Integer.parseInt(cursor.getString(1)));
                cursor.moveToNext();
            }
            // Нахождение номера строки с необходимой дальностью
            for (int j = 0; j < ndist.size(); j++) {
                if (i - ndist.get(j) < 0) {
                    listn = j;
                    break;
                }
            }

            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                if (listn != 1) {  // Перенос курсора на необходимую строку
                    for (int j = 1; j < listn; j++) {
                        cursor.moveToNext();
                    }
                }

                // Получение данных из первой строки
                dist = cursor.getString(1);
                scoped = cursor.getString(2);
                scopet = cursor.getString(3);
                t = cursor.getString(4);
                ratiou = cursor.getString(5);
                ratiod = cursor.getString(6);

                try {
                    distdif1 = Double.parseDouble(dist);
                    scopedivdif1 = Double.parseDouble(scoped);
                    scopethdif1 = Double.parseDouble(scopet);
                    timedif1 = Double.parseDouble(t);
                    ratioupdif1 = Double.parseDouble(ratiou);
                    if (ratiod != null) {
                        ratiodowndif1 = Double.parseDouble(ratiod);
                    } else {
                        ratiodowndif1 = 0;
                    }
                } catch (NumberFormatException e) {
                    distance.setText("Ошибка при вводе данных");
                }

                cursor.moveToNext();

                // получение данных из второй строки
                try {
                    distdif2 = Double.parseDouble(cursor.getString(1));
                    scopedivdif2 = Double.parseDouble(cursor.getString(2));
                    scopethdif2 = Double.parseDouble(cursor.getString(3));
                    timedif2 = Double.parseDouble(cursor.getString(4));
                    ratioupdif2 = Double.parseDouble(cursor.getString(5));
                    if (ratiod != null) {
                        ratiodowndif2 = Double.parseDouble(ratiod);
                    } else {
                        ratiodowndif2 = 0;
                    }
                } catch (NumberFormatException e) {
                    distance.setText("Ошибка при вводе данных");
                }


                distdif2 = distdif2 - distdif1;
                scopedivdif2 = scopedivdif2 - scopedivdif1;
                scopethdif2 = scopethdif2 - scopethdif1;
                timedif2 = timedif2 - timedif1;
                ratioupdif2 = ratioupdif2 - ratioupdif1;
                ratiodowndif2 = ratiodowndif2 - ratiodowndif1;


                difratio = (i - distdif1) / distdif2;
                dist = String.valueOf(i);
                scoped = String.valueOf(scopedivdif2 * difratio + scopedivdif1);
                scopet = String.valueOf(scopethdif2 * difratio + scopethdif1);
                t = String.valueOf(timedif2 * difratio + timedif1);
                ratiou = String.valueOf(ratioupdif2 * difratio + ratioupdif1);
                ratiod = String.valueOf(ratiodowndif2 * difratio + ratiodowndif1);
            }

            cursor.close();
            // Передача данных в интерфейс
            typev.setText(typeview);
            distance.setText(dist);
            scopediv.setText(scoped);
            scopethous.setText(scopet);
            time.setText(t);
            ratioup.setText(ratiou);
            ratiodown.setText(ratiod);

        }
    }
}
