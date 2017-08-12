package br.com.fiap.igojeferson.fiapimoveis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.Serializable;

import br.com.fiap.igojeferson.fiapimoveis.dao.ImovelDAO;
import br.com.fiap.igojeferson.fiapimoveis.helper.FormularioHelper;
import br.com.fiap.igojeferson.fiapimoveis.model.Imovel;

public class FormularioActivity extends AppCompatActivity {

    private FormularioHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        helper = new FormularioHelper(this);

        Intent intent = getIntent();
        Imovel imovel = (Imovel) intent.getSerializableExtra("imovel");
        if(imovel != null){
            helper.preencheFormulario(imovel);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_formulario_ok:

                Imovel imovel = helper.pegaImovel();
                ImovelDAO dao = new ImovelDAO(this);

                if(imovel.getId() != null){
                    dao.altera(imovel);
                } else {
                    dao.insere(imovel);
                }
                dao.close();

                Toast.makeText(FormularioActivity.this, "Imovel " + imovel.getNome() + " salvo!", Toast.LENGTH_SHORT).show();

                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
