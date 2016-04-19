package incalpaca.pedidosincalpaca;

/**
 * Created by josimar on 17/04/16.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    public static final String  DATABASE_FILE_PATH = "/mnt/sdcard/PedidosIncalpaca/db/";

    private static final String DATABASE_NAME = "PedidosIncalpaca";

    private static final String TABLE_PEDIDOS = "pedidos";
    private static final String TABLE_EMAILS = "emails";

    private static final String KEY_ID = "id";
    private static final String KEY_ITEMS = "items";
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_EMPRESA = "empresa";
    private static final String KEY_DIRECCION = "direccion";
    private static final String KEY_PAIS = "pais";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_OBSERVACIONES = "observaciones";
    private static final String KEY_FOTO = "foto";
    private static final String KEY_FECHA = "fecha";

    private static final String KEY2_ID = "id";
    private static final String KEY2_EMAIL1 = "email1";
    private static final String KEY2_EMAIL2 = "email2";
    private static final String KEY2_EMAIL3 = "email3";

    private static DBHandler sInstance;



    public DBHandler(Context context) {
        super(context, DATABASE_FILE_PATH + DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DBHandler getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DBHandler(context.getApplicationContext());
        }
        return sInstance;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_PEDIDOS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_ITEMS + " TEXT,"
                + KEY_NOMBRE + " TEXT,"
                + KEY_EMPRESA + " TEXT,"
                + KEY_DIRECCION + " TEXT,"
                + KEY_PAIS + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_OBSERVACIONES + " TEXT,"
                + KEY_FOTO + " TEXT,"
                + KEY_FECHA + " INT" + ")";
        db.execSQL(CREATE_TABLE);

        CREATE_TABLE = "CREATE TABLE " + TABLE_EMAILS + "("
                + KEY2_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY2_EMAIL1 + " TEXT,"
                + KEY2_EMAIL2 + " TEXT,"
                + KEY2_EMAIL3 + " TEXT" + ")";

        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PEDIDOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMAILS);

        onCreate(db);
    }




    public void addPedido(Pedido pedido) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ITEMS, pedido.getItems());
        values.put(KEY_NOMBRE, pedido.getNombre());
        values.put(KEY_EMPRESA, pedido.getEmpresa());
        values.put(KEY_DIRECCION, pedido.getDireccion());
        values.put(KEY_PAIS, pedido.getPais());
        values.put(KEY_EMAIL, pedido.getEmail());
        values.put(KEY_OBSERVACIONES, pedido.getObservaciones());
        values.put(KEY_FOTO, pedido.getFoto());
        values.put(KEY_FECHA, pedido.getFecha());


        db.insert(TABLE_PEDIDOS, null, values);
        db.close();
    }

    public List<Pedido> getAllPedidos() {
        List<Pedido> pedidoList = new ArrayList<Pedido>();
        String selectQuery = "SELECT * FROM " + TABLE_PEDIDOS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Pedido pedido = new Pedido();
                pedido.setId(Integer.parseInt(cursor.getString(0)));
                pedido.setItems(cursor.getString(1));
                pedido.setNombre(cursor.getString(2));
                pedido.setEmpresa(cursor.getString(3));
                pedido.setDireccion(cursor.getString(4));
                pedido.setPais(cursor.getString(5));
                pedido.setEmail(cursor.getString(6));
                pedido.setObservaciones(cursor.getString(7));
                pedido.setFoto(cursor.getString(8));
                pedido.setFecha(cursor.getString(9));

                pedidoList.add(pedido);
            } while (cursor.moveToNext());
        }

        return pedidoList;
    }

    public List<Pedido> getPedidosFecha(String ff) {
        List<Pedido> pedidoList = new ArrayList<Pedido>();
        String selectQuery = "SELECT * FROM " + TABLE_PEDIDOS + " WHERE " + KEY_FECHA +"='"+ff+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Pedido pedido = new Pedido();
                pedido.setId(Integer.parseInt(cursor.getString(0)));
                pedido.setItems(cursor.getString(1));
                pedido.setNombre(cursor.getString(2));
                pedido.setEmpresa(cursor.getString(3));
                pedido.setDireccion(cursor.getString(4));
                pedido.setPais(cursor.getString(5));
                pedido.setEmail(cursor.getString(6));
                pedido.setObservaciones(cursor.getString(7));
                pedido.setFoto(cursor.getString(8));
                pedido.setFecha(cursor.getString(9));

                pedidoList.add(pedido);
            } while (cursor.moveToNext());
        }

        return pedidoList;
    }


    public int getPedidosCount() {
        String countQuery = "SELECT * FROM " + TABLE_PEDIDOS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }


    public int updatePedido(Pedido pedido) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ITEMS, pedido.getItems());
        values.put(KEY_NOMBRE, pedido.getNombre());
        values.put(KEY_EMPRESA, pedido.getEmpresa());
        values.put(KEY_DIRECCION, pedido.getDireccion());
        values.put(KEY_PAIS, pedido.getPais());
        values.put(KEY_EMAIL, pedido.getEmail());
        values.put(KEY_OBSERVACIONES, pedido.getObservaciones());
        values.put(KEY_FOTO, pedido.getFoto());
        values.put(KEY_FECHA, pedido.getFecha());

        return db.update(TABLE_PEDIDOS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(pedido.getId())});
    }

    public void deletePedido(Pedido pedido) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PEDIDOS, KEY_ID + " = ?",
                new String[]{String.valueOf(pedido.getId())});
        db.close();
    }

    public void addEmail(Emails em) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY2_ID, em.getId());
        values.put(KEY2_EMAIL1, em.getEmail1());
        values.put(KEY2_EMAIL2, em.getEmail2());
        values.put(KEY2_EMAIL3, em.getEmail3());

        db.insert(TABLE_EMAILS, null, values);
        db.close();
    }

    public int updateEmail(Emails em) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY2_EMAIL1, em.getEmail1());
        values.put(KEY2_EMAIL2, em.getEmail2());
        values.put(KEY2_EMAIL3, em.getEmail3());

        return db.update(TABLE_EMAILS, values, KEY2_ID + " = ?",
                new String[]{String.valueOf(em.getId())});
    }

    public List<Emails> getAllEmails() {
        List<Emails> emailList = new ArrayList<Emails>();
        String selectQuery = "SELECT * FROM " + TABLE_EMAILS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Emails em = new Emails();
                em.setId(Integer.parseInt(cursor.getString(0)));
                em.setEmail1(cursor.getString(1));
                em.setEmail2(cursor.getString(2));
                em.setEmail3(cursor.getString(3));

                emailList.add(em);
            } while (cursor.moveToNext());
        }

        return emailList;
    }


}