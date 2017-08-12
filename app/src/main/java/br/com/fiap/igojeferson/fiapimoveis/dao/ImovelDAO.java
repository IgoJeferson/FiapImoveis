package br.com.fiap.igojeferson.fiapimoveis.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.igojeferson.fiapimoveis.model.Imovel;

/**
 * Created by IgoJeferson on 2017-08-11.
 */
public class ImovelDAO extends SQLiteOpenHelper{

    public ImovelDAO(Context context) {
        super(context, "Imoveis", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Imoveis (id INTEGER PRIMARY KEY, nome TEXT NOT NULL, endereco TEXT, telefone TEXT, tipo INTEGER, tamanho INTEGER, emConstrucao INTEGER, observacao TEXT, ativo INTEGER)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Imoveis";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insere(Imovel novoImovel) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = this.pegaDadosDoImovel(novoImovel);
        db.insert("Imoveis", null, dados);
    }

    public List<Imovel> buscaImoveis() {
        String sql = "SELECT * FROM Imoveis;";
        SQLiteDatabase db = getReadableDatabase();
        String[] params = {"1"};
        Cursor c = db.rawQuery(sql, null);

        List<Imovel> imoveis = new ArrayList<>();
        while (c.moveToNext()){
            Imovel imovel = new Imovel();
            imovel.setId(c.getLong(c.getColumnIndex("id")));
            imovel.setNome(c.getString(c.getColumnIndex("nome")));
            imovel.setEndereco(c.getString(c.getColumnIndex("endereco")));
            imovel.setTelefone(c.getString(c.getColumnIndex("telefone")));
            imovel.setTipo(c.getInt(c.getColumnIndex("tipo")));
            imovel.setTamanho(c.getInt(c.getColumnIndex("tamanho")));
            imovel.setEmConstrucao(c.getInt(c.getColumnIndex("emConstrucao")));
            imovel.setObservacao(c.getString(c.getColumnIndex("observacao")));
            imovel.setAtivo(c.getInt(c.getColumnIndex("ativo")));

            // TODO - Alterar - Incluir filtro na query
            if(imovel.getAtivo() > 0 ) {
                imoveis.add(imovel);
            }

        }
        c.close();
        return imoveis;
    }

    public void deleta(Imovel imovel) {
        imovel.setAtivo(0);
        this.altera(imovel);
    }

    public void altera(Imovel imovel) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = this.pegaDadosDoImovel(imovel);
        String[] params = {imovel.getId().toString()};

        db.update("Imoveis", dados, "id = ?", params );
    }

    @NonNull
    private ContentValues pegaDadosDoImovel(Imovel imovel) {
        ContentValues dados = new ContentValues();
        dados.put("nome", imovel.getNome());
        dados.put("endereco", imovel.getEndereco());
        dados.put("telefone", imovel.getTelefone());
        dados.put("tipo", imovel.getTipo());
        dados.put("tamanho", imovel.getTamanho());
        dados.put("emConstrucao", imovel.getEmConstrucao());
        dados.put("observacao", imovel.getObservacao());
        dados.put("ativo", imovel.getAtivo());
        return dados;
    }
}
