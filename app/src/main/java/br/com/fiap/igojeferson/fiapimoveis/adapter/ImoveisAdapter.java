package br.com.fiap.igojeferson.fiapimoveis.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.fiap.igojeferson.fiapimoveis.R;
import br.com.fiap.igojeferson.fiapimoveis.model.Imovel;

/**
 * Created by IgoJeferson on 2017-08-14.
 */
public class ImoveisAdapter extends BaseAdapter {

    private Context context;
    private List<Imovel> imoveis;

    public ImoveisAdapter(Context context, List<Imovel> imoveis) {
        this.context = context;
        this.imoveis = imoveis;
    }

    @Override
    public int getCount() {
        return this.imoveis.size();
    }

    @Override
    public Object getItem(int position) {
        return this.imoveis.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.imoveis.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Imovel imovel = this.imoveis.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);

        // Para reaproveitar as views já infladas
        View view = convertView;
        if(view == null){
            view = inflater.inflate(R.layout.list_item, parent, false);
        }

        TextView campoNome = (TextView) view.findViewById(R.id.item_nome);
        campoNome.setText(imovel.getNome());

        TextView campoTelefone = (TextView) view.findViewById(R.id.item_telefone);
        campoTelefone.setText(imovel.getTelefone());

        ImageView campoFoto = (ImageView) view.findViewById(R.id.item_foto);
        String caminhoFoto = imovel.getCaminhoFoto();
        if(caminhoFoto != null){
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            campoFoto.setImageBitmap(bitmapReduzido);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        return view;
    }
}
