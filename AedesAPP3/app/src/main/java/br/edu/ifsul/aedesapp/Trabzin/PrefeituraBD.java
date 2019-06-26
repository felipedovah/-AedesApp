package br.edu.ifsul.aedesapp.Trabzin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsul.aedesapp.Modelo.Prefeitura;

public class PrefeituraBD extends SQLiteOpenHelper {
    private static final String NOME_BD = "prefeiturasnovo.sqlite";
    private static final int VERSAO = 2;
    private static PrefeituraBD prefeituraBD= null; //Singleton

    //Construtor
    public PrefeituraBD(Context context) {
        // context, nome do banco, factory, versão
        super(context, NOME_BD, null, VERSAO);
    }

    // utilizado para instanciar a classe, note o IF
    public static PrefeituraBD getInstance(Context context){
        if(prefeituraBD == null){
            prefeituraBD = new PrefeituraBD(context);
            return prefeituraBD;
        }else{
            return prefeituraBD;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table if not exists prefeitura" +
                "( _id integer primary key autoincrement, " +
                " nome text);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS prefeitura");
        onCreate(sqLiteDatabase); // chama onCreate e recria o banco de dados
    }

    public long save(Prefeitura prefeitura){
        SQLiteDatabase db = getWritableDatabase(); //abre a conexão com o banco
        try{
            //tupla com: chave, valor
            ContentValues values = new ContentValues();
            values.put("nome", prefeitura.getNome());
            if(prefeitura.get_id() == null){
                //insere no banco de dados
                return db.insert("prefeitura", null, values);
            }else
            {//altera no banco de dados
                values.put("_id", prefeitura.get_id());
                return db.update("prefeitura", values, "_id=" + prefeitura.get_id(), null);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {db.close(); }//não esquecer de liberar o recurso
        return 0; //caso não realize as operações
    }

    public List<Prefeitura> getAll(){
        SQLiteDatabase db = getReadableDatabase();
        try {
            //retorna uma List para os registros contidos no banco de dados
            // d.rawQuery retorna um Cursor, então temos que encaminhar para outro método
            // chamado toList que fará o conversão para lista que precisamos.
            // note que podemos criar outros selects e mandar igualmente para o toList
            return toList(db.rawQuery("SELECT  * FROM prefeitura", null));
        } finally {
            db.close();
        }
    }

    public List<Prefeitura> getByname(String nome){
        SQLiteDatabase db = getReadableDatabase();
        try {
            //retorna uma List para os registros contidos no banco de dados
            // select * from contato where nome LIKE
            return toList(db.rawQuery("SELECT * FROM prefeitura where nome LIKE'" + nome + "%'", null));
        } finally {
            db.close();
        }
    }

    private List<Prefeitura> toList(Cursor c) {
        List<Prefeitura> prefeituras = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                Prefeitura prefeitura = new Prefeitura();
                // recupera os atributos do cursor para o conta
                prefeitura.set_id(c.getLong(c.getColumnIndex("_id")));
                prefeitura.setNome(c.getString(c.getColumnIndex("nome")));
                //contato.setColorado(Boolean.parseBoolean(String.valueOf(Integer.parseInt("colorado"))));
                prefeituras.add(prefeitura);
            } while (c.moveToNext());
        }
        return prefeituras;
    }

    public long delete(Prefeitura prefeitura){
        SQLiteDatabase db = getWritableDatabase(); //abre a conexão com o banco
        try{
            return db.delete("prefeitura", "_id=?", new String[]{String.valueOf(prefeitura.get_id())});
        }
        finally {
            db.close(); //não esquecer de liberar o recurso
        }
    }
}
