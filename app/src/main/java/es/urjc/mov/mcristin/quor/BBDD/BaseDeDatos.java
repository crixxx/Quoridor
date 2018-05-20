package es.urjc.mov.mcristin.quor.BBDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class BaseDeDatos extends SQLiteOpenHelper {
    private final static String NAME = "app.db";
    private final static int VERSION = 1;
    public int jBD;
    public int gBD;

    public final String TABLASCORE = "App";
    private final String CREATE_TABLASCORE = "CREATE TABLE " + TABLASCORE + " (" +
            " _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " Nombre TEXT, " +
            " Ganadas INTEGER, " +
            " Jugadas INTEGER);";


    public BaseDeDatos(final Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v(BaseDeDatos.class.getName(), "Creating DB.");
        db.execSQL(CREATE_TABLASCORE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int from, int to) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLASCORE);
        database.execSQL(CREATE_TABLASCORE);
    }

    public boolean consultaBD(String name) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(" SELECT * FROM " + TABLASCORE + " WHERE Nombre='"+name+"'",null);
        if (c.moveToFirst()) {
            String codigo= c.getString(0);
            String nombre = c.getString(1);
            return true;
        }
        db.close();
        c.close();
        return false;
    }

    public void insertar(String name){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Nombre", name);
        values.put("Ganadas", 0);
        values.put("Jugadas", 0);
        db.insert(TABLASCORE, null,values);
        db.close();
    }

    public JugadorIN getDatos(String name){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(" SELECT _id, Nombre, Ganadas, MAX(Jugadas) FROM " + TABLASCORE + " WHERE Nombre='"+name+"'", null);
        if (c != null) {
            c.moveToFirst();
        }
        JugadorIN subject = new JugadorIN(c.getInt(0), c.getString(1), c.getInt(2), c.getInt(3));
        gBD = c.getInt(2);
        jBD = c.getInt(3);
        db.close();
        c.close();
        return subject;
    }

    public void upDateDatos(String name, int ganadas, int jugadas){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(" SELECT _id, Nombre, Ganadas, MAX(Jugadas) FROM " + TABLASCORE + " WHERE Nombre='"+name+"'", null);
        if (c != null) {
            c.moveToFirst();
        }
        JugadorIN subject = new JugadorIN(c.getInt(0), c.getString(1), c.getInt(2), c.getInt(3));
        int id = c.getInt(0);

        db.close();
        c.close();

        SQLiteDatabase db2 = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("_id", id);
        values.put("Nombre", name);
        values.put("Ganadas", ganadas);
        values.put("Jugadas", jugadas);
        db2.update(TABLASCORE, values, "_id=" + id, null);
        db2.close();
    }
}