package br.edu.ifro.vilhena.novaagendadecontatos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifro.vilhena.novaagendadecontatos.Model.Contato;

public class ContatoDAO extends SQLiteOpenHelper {


    public ContatoDAO(Context context){
        super (context, "Agenda", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table contatos (id integer primary key, nome text, email text, telefone text)";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void inserir(Contato contato) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = new ContentValues();
        dados.put("nome", contato.getNome());
        dados.put("email", contato.getEmail());
        dados.put("telefone", contato.getTelefone());

        db.insert("contatos", null, dados);
    }

    public List<Contato> listar(){
        String sql = "select * from contatos";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        List<Contato> lista = new ArrayList<Contato>();
        while (c.moveToNext()){
            Contato contato = new Contato();
            contato.setId(c.getInt(c.getColumnIndex("id")));
            contato.setNome(c.getString(c.getColumnIndex("nome")));
            contato.setEmail(c.getString(c.getColumnIndex("email")));
            contato.setTelefone(c.getString(c.getColumnIndex("telefone")));

            lista.add(contato);
        }

        return lista;
    }

    public void deletar(Contato contato) {
        SQLiteDatabase db = getWritableDatabase();
        String[] parametros = {String.valueOf(contato.getId())};
        db.delete("contatos", "id = ?", parametros);
    }

    public void alterar(Contato contato) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("id", contato.getId());
        valores.put("nome", contato.getNome());
        valores.put("email", contato.getEmail());
        valores.put("telefone", contato.getTelefone());

        String[] args = {String.valueOf(contato.getId())};

        db.update("contatos", valores, "id = ?", args);
    }
}
