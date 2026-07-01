package com.example.gerenciadorprojetos;

import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;







public class ListagemCanaisActivity extends AppCompatActivity {

    private EditText editNome;
    private Button btnSalvar;
    private ListView listView;
    private AppDao dao;
    private List<Canal> listaCanais;
    private ArrayAdapter<Canal> adapter;
    private ActionMode actionMode;
    private int posicaoSelecionada = -1;
    private Canal canalEdicao = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_canais);


        if (getSupportActionBar() != null) {
            getSupportActionBar().show();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        editNome = findViewById(R.id.edit_nome_canal);
        btnSalvar = findViewById(R.id.btn_salvar_canal);
        listView = findViewById(R.id.list_canais);

        dao = AppDatabase.getInstance(this).appDao();

        btnSalvar.setOnClickListener(v -> {
            String nome = editNome.getText().toString().trim();
            if (nome.isEmpty()) return;

            if (canalEdicao == null) {
                dao.inserirCanal(new Canal(nome));
            } else {

                canalEdicao.setNome(nome);
                dao.atualizarCanal(canalEdicao);
                canalEdicao = null;
                btnSalvar.setText("Add");
            }
            editNome.setText("");
            atualizarLista();
        });



        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            if (actionMode != null) return false;
            posicaoSelecionada = position;
            actionMode = startActionMode(actionModeCallback);
            view.setSelected(true);
            return true;
        });

        atualizarLista();
    }


    private void atualizarLista() {

        listaCanais = dao.getAllCanais();
        ArrayAdapter<Canal> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaCanais);
        listView.setAdapter(adapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private final ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_contexto, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) { return false; }
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int id = item.getItemId();
            Canal selecionado = listaCanais.get(posicaoSelecionada);

            if (id == R.id.menu_editar) {
                canalEdicao = selecionado;
                editNome.setText(selecionado.getNome());
                btnSalvar.setText("Ok");
                mode.finish();
                return true;
            } else if (id == R.id.menu_excluir) {
                exibirConfirmacao(mode, selecionado);
                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) { actionMode = null; }
    };




    private void exibirConfirmacao(ActionMode mode, Canal canal) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.title_confirm)
                .setMessage(R.string.msg_confirm_delete)
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    dao.excluirCanal(canal);
                    atualizarLista();
                    mode.finish();
                })
                .setNegativeButton(R.string.no, (dialog, which) -> mode.finish())
                .show();
    }
}