package com.example.gerenciadorprojetos;

import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private TextView txtVazio;
    private AppDao dao;
    private List<Video> listaVideos;
    private VideoAdapter adapter;

    private ActionMode actionMode;
    private int posicaoSelecionada = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // faz a  barra superior a aparecer com o título do app **anotar caderno

        if (getSupportActionBar() != null) {
            getSupportActionBar().show();
            getSupportActionBar().setTitle(R.string.app_name);
        }

        listView = findViewById(R.id.list_videos);
        txtVazio = findViewById(R.id.txt_vazio);

        dao = AppDatabase.getInstance(this).appDao();


        List<Canal> canaisExistentes = dao.getAllCanais();
        if (canaisExistentes.isEmpty()) {
            dao.inserirCanal(new Canal("YouTube"));
            dao.inserirCanal(new Canal("TikTok"));
            dao.inserirCanal(new Canal("Instagram"));
        }


        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            if (actionMode != null) return false;

            posicaoSelecionada = position;
            actionMode = startActionMode(actionModeCallback);
            view.setSelected(true);
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarDados();
    }

    private void carregarDados() {
        listaVideos = dao.getAllVideos();
        if (listaVideos.isEmpty()) {
            txtVazio.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        } else {
            txtVazio.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            adapter = new VideoAdapter(this, listaVideos);
            listView.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_listagem, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_add) {
            startActivity(new Intent(this, CadastroVideoActivity.class));
            return true;
        } else if (id == R.id.menu_canais) {
            startActivity(new Intent(this, ListagemCanaisActivity.class));
            return true;
        } else if (id == R.id.menu_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.menu_sobre) {
            startActivity(new Intent(this, AutoriaActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // gerencia a barra de acoes quando segura um item
    private final ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_contexto, menu);
            mode.setTitle("Opções");
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) { return false; }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int id = item.getItemId();
            Video selecionado = listaVideos.get(posicaoSelecionada);

            if (id == R.id.menu_editar) {
                Intent it = new Intent(MainActivity.this, CadastroVideoActivity.class);
                it.putExtra("video_editar", selecionado);
                startActivity(it);
                mode.finish();
                return true;
            } else if (id == R.id.menu_excluir) {
                exibirAlertaConfirmacao(mode, selecionado);
                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
        }
    };

    // popup de confirmacao antes de deletar
    private void exibirAlertaConfirmacao(ActionMode mode, Video video) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.title_confirm)
                .setMessage(R.string.msg_confirm_delete)
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    dao.excluirVideo(video);
                    Toast.makeText(this, R.string.msg_removed, Toast.LENGTH_SHORT).show();
                    carregarDados();
                    mode.finish();
                })
                .setNegativeButton(R.string.no, (dialog, which) -> mode.finish())
                .show();
    }
}