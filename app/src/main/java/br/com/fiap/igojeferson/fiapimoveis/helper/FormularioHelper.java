package br.com.fiap.igojeferson.fiapimoveis.helper;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import br.com.fiap.igojeferson.fiapimoveis.FormularioActivity;
import br.com.fiap.igojeferson.fiapimoveis.R;
import br.com.fiap.igojeferson.fiapimoveis.model.Imovel;

/**
 * Created by IgoJeferson on 2017-08-11.
 */

public class FormularioHelper {

    private final EditText campoNome;
    private final EditText campoEndereco;
    private final EditText campoTelefone;
    private final Spinner campoTipoImovel;
    private final Spinner campoTamanhoImovel;
    private final CheckBox campoEmConstrucao;
    private final EditText campoObservacao;

    private Imovel imovel;

    public FormularioHelper(FormularioActivity activity){
        campoNome = (EditText) activity.findViewById(R.id.formulario_etNome);
        campoEndereco = (EditText) activity.findViewById(R.id.formulario_etEndereco);
        campoTelefone = (EditText) activity.findViewById(R.id.formulario_etTelefone);
        campoTipoImovel = (Spinner) activity.findViewById(R.id.formulario_spTipoImovel);
        campoTamanhoImovel = (Spinner) activity.findViewById(R.id.formulario_spTamanhoImovel);
        campoEmConstrucao = (CheckBox) activity.findViewById(R.id.formulario_ckEmConstrucao);
        campoObservacao = (EditText) activity.findViewById(R.id.formulario_etObservacao);

        imovel = new Imovel();
    }

    public Imovel pegaImovel() {
        imovel.setNome(campoNome.getText().toString());
        imovel.setEndereco(campoEndereco.getText().toString());
        imovel.setTelefone(campoTelefone.getText().toString());
        imovel.setTipo(campoTipoImovel.getSelectedItemPosition());
        imovel.setTamanho(campoTamanhoImovel.getSelectedItemPosition());
        imovel.setEmConstrucao(campoEmConstrucao.isChecked() ? 1 : 0);
        imovel.setObservacao(campoObservacao.getText().toString());
        return imovel;
    }

    public void preencheFormulario(Imovel imovel){
        campoNome.setText(imovel.getNome());
        campoEndereco.setText(imovel.getEndereco());
        campoTelefone.setText(imovel.getTelefone());
        campoTipoImovel.setSelection(imovel.getTipo());
        campoTamanhoImovel.setSelection(imovel.getTamanho());
        campoEmConstrucao.setChecked(imovel.getEmConstrucao() > 0 ? true : false);
        campoObservacao.setText(imovel.getObservacao());
        this.imovel = imovel;
    }
}
