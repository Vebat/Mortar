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
        TextView distance= findViewById(R.id.distance);
        TextView scopediv= findViewById(R.id.scopediv);
        TextView scopethous= findViewById(R.id.scopethosands);
        TextView time = findViewById(R.id.time);
        TextView ratioup= findViewById(R.id.ratioup);
        TextView ratiodown= findViewById(R.id.ratiodown);
        EditText stringn=findViewById(R.id.stringnumber);
        // Получение данных из EditText
        String in= stringn.getText().toString();
        int i=Integer.parseInt(in);
        // Создание переменных и списка для получения дальности
        int dif;
        int listn = 0;
        int distdif1;
        int distdif2;
        int scopedivdif1;
        int scopedivdif2;
        int scopethdif1;
        int scopethdif2;
        int timedif1;
        int timedif2;
        int ratioupdif1;
        int ratioupdif2;
        int ratiodowndif1;
        int ratiodowndif2;
        String dist = "";
        String scoped = "";
        String scopet = "";
        String t = "";
        String ratiou = "";
        String ratiod = "";
        ArrayList<Integer> ndist=new ArrayList<>();


        // Открытие таблицы с данными
        Cursor cursor = mDb.rawQuery("SELECT * FROM dalnoboi", null);
        cursor.moveToFirst();
        // Заполнение списка
        while(!cursor.isAfterLast()){
            ndist.add(Integer.parseInt(cursor.getString(1)));
            cursor.moveToNext();
        }
        // Нахождение номера строки с необходимой дальностью
        for(int j=0; j<ndist.size(); j++){
        if(i-ndist.get(j)<0){
            listn=j;
            break;
        }
        }

        cursor.moveToFirst();
        if(!cursor.isAfterLast()) {
            if  (listn!=1){  // Перенос курсора на необходимую строку
                for(int j=1;j<listn;j++){
                    cursor.moveToNext();
                }
            }
            // Получение данных из первой строки
            distdif1 = Integer.parseInt(cursor.getString(1));
            scopedivdif1 = Integer.parseInt(cursor.getString(2));
            scopethdif1 = Integer.parseInt(cursor.getString(3));
            timedif1 = Integer.parseInt(cursor.getString(4));
            ratioupdif1 = Integer.parseInt(cursor.getString(5));
            ratiodowndif1 = Integer.parseInt(cursor.getString(6));

            cursor.moveToNext();

            // получение данных из второй строки
            distdif2 = Integer.parseInt(cursor.getString(1));
            scopedivdif2 = Integer.parseInt(cursor.getString(2));
            scopethdif2 = Integer.parseInt(cursor.getString(3));
            timedif2 = Integer.parseInt(cursor.getString(4));
            ratioupdif2 = Integer.parseInt(cursor.getString(5));
            ratiodowndif2 = Integer.parseInt(cursor.getString(6));
            


        }

        cursor.close();
        // Передача данных в интерфейс
        distance.setText(dist);
        scopediv.setText(scoped);
        scopethous.setText(scopet);
        time.setText(t);
        ratioup.setText(ratiou);
        ratiodown.setText(ratiod);


    }

}