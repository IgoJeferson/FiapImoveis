package br.com.fiap.igojeferson.fiapimoveis;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import br.com.fiap.igojeferson.fiapimoveis.dao.ImovelDAO;
import br.com.fiap.igojeferson.fiapimoveis.helper.FormularioHelper;
import br.com.fiap.igojeferson.fiapimoveis.model.Imovel;

public class FormularioActivity extends AppCompatActivity {

    public static final int CODIGO_CAMERA = 567;
    private FormularioHelper helper;
    private String caminhoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        // Para resolver a FileUriExposedException que ocorria ao tentar abrir a camera
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        helper = new FormularioHelper(this);

        Intent intent = getIntent();
        Imovel imovel = (Imovel) intent.getSerializableExtra("imovel");
        if(imovel != null){
            helper.preencheFormulario(imovel);
        }

        Button botaoFoto = (Button) findViewById(R.id.formulario_botaoFoto);
        botaoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
                File arquivoFoto = new File(caminhoFoto);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
                startActivityForResult(intentCamera, CODIGO_CAMERA);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == CODIGO_CAMERA) {
            helper.carregaImagem(caminhoFoto);
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
                    imovel.setAtivo(1);
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