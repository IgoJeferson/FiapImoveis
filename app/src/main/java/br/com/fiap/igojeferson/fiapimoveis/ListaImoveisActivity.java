package br.com.fiap.igojeferson.fiapimoveis;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.fiap.igojeferson.fiapimoveis.dao.ImovelDAO;
import br.com.fiap.igojeferson.fiapimoveis.model.Imovel;

public class ListaImoveisActivity extends AppCompatActivity {

    private ListView listaImoveis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_imoveis);

        listaImoveis = (ListView) findViewById(R.id.lista_imoveis);

        listaImoveis.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Imovel imovel = (Imovel) listaImoveis.getItemAtPosition(position);

                Intent vaiProFormulario = new Intent(ListaImoveisActivity.this, FormularioActivity.class);
                vaiProFormulario.putExtra("imovel", imovel);
                startActivity(vaiProFormulario);
            }
        });

        Button botaoNovoImovel = (Button) findViewById(R.id.novo_imovel);
        botaoNovoImovel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent vaiProFormulario = new Intent(ListaImoveisActivity.this, FormularioActivity.class);
                startActivity(vaiProFormulario);
            }
        });

        registerForContextMenu(listaImoveis);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Imovel imovel = (Imovel) listaImoveis.getItemAtPosition(info.position);

        final MenuItem itemLigar = menu.add("Ligar");
        itemLigar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(ActivityCompat.checkSelfPermission(ListaImoveisActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ListaImoveisActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},123);
                } else {
                    Intent intentLigar = new Intent(Intent.ACTION_CALL);
                    intentLigar.setData(Uri.parse("tel:" + imovel.getTelefone()));
                    startActivity(intentLigar);
                }
                return false;
            }
        });

        MenuItem itemSMS = menu.add("Enviar SMS");
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:" + imovel.getTelefone()));
        itemSMS.setIntent(intentSMS);

        MenuItem itemMapa = menu.add("Visualizar no mapa");
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?q=" + imovel.getEndereco()));
        itemMapa.setIntent(intentMapa);

        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ImovelDAO dao = new ImovelDAO(ListaImoveisActivity.this);
                dao.deleta(imovel);
                dao.close();

                carregaLista();

                Toast.makeText(ListaImoveisActivity.this, "Deletado imóvel " + imovel.getNome(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void carregaLista() {
        ImovelDAO dao = new ImovelDAO(this);
        List<Imovel> imoveis = dao.buscaImoveis();
        dao.close();

        ArrayAdapter<Imovel> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, imoveis);
        listaImoveis.setAdapter(adapter);
    }
}
