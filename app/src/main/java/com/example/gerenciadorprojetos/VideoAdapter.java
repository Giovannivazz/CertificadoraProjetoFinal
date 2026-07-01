package com.example.gerenciadorprojetos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.time.format.DateTimeFormatter;
import java.util.List;





public class VideoAdapter extends BaseAdapter {

    private final Context ctx;
    private final List<Video> lista;
    private final AppDao dao;



    public VideoAdapter(Context context, List<Video> listaVideos) {
        this.ctx = context;
        this.lista = listaVideos;
        this.dao = AppDatabase.getInstance(context).appDao();
    }




    @Override
    public int getCount() { return lista.size(); }

    @Override
    public Object getItem(int pos) { return lista.get(pos); }
    @Override
    public long getItemId(int pos) { return lista.get(pos).getId(); }
    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_video, parent, false);
        }

        Video v = lista.get(pos);

        TextView txtTitulo = convertView.findViewById(R.id.txt_item_titulo);
        TextView txtCanal = convertView.findViewById(R.id.txt_item_canal);
        TextView txtDetalhes = convertView.findViewById(R.id.txt_item_detalhes);

        txtTitulo.setText(v.getTitulo());


        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = v.getDataPublicacao().format(formatador);
        txtDetalhes.setText(v.getFormato() + " | " + dataFormatada);


        txtCanal.setText("Canal Independente");
        List<Canal> todosCanais = dao.getAllCanais();
        for (Canal c : todosCanais) {
            if (c.getId() == v.getCanalId()) {
                txtCanal.setText(c.getNome());
                break;
            }
        }

        return convertView;
    }
}