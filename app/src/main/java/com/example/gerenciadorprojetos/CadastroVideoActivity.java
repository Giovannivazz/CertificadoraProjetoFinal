package com.example.gerenciadorprojetos;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.time.LocalDate;
import java.util.List;





public class CadastroVideoActivity extends AppCompatActivity {

    private EditText editTitulo;
    private RadioGroup rgFormato;
    private RadioButton rbHorizontal, rbVertical;
    private Spinner spCanais;
    private DatePicker datePicker;

    private AppDao dao;
    private List<Canal> listaCanais;
    private Video videoEdicao = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_video);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        editTitulo = findViewById(R.id.edit_titulo);
        rgFormato = findViewById(R.id.rg_formato);
        rbHorizontal = findViewById(R.id.rb_horizontal);
        rbVertical = findViewById(R.id.rb_vertical);
        spCanais = findViewById(R.id.sp_canais);
        datePicker = findViewById(R.id.date_picker);
        dao = AppDatabase.getInstance(this).appDao();


        listaCanais = dao.getAllCanais();
        ArrayAdapter<Canal> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaCanais);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCanais.setAdapter(adapter);



        if (getIntent().hasExtra("video_editar")) {
            videoEdicao = (Video) getIntent().getSerializableExtra("video_editar");
            carregarCampos();
        } else {
            SharedPreferences pref = getSharedPreferences("config_app", MODE_PRIVATE);
            boolean sugerir = pref.getBoolean("sugerir_nome", false);
            if (sugerir) {
                String ultimoTitulo = pref.getString("ultimo_titulo", "");
                editTitulo.setText(ultimoTitulo);
            }
        }
        invalidateOptionsMenu();
    }


    private void carregarCampos() {


        editTitulo.setText(videoEdicao.getTitulo());
        if (videoEdicao.getFormato().equals("Vertical")) {
            rbVertical.setChecked(true);
        } else {
            rbHorizontal.setChecked(true);
        }



        for (int i = 0; i < listaCanais.size(); i++) {
            if (listaCanais.get(i).getId() == videoEdicao.getCanalId()) {
                spCanais.setSelection(i);
                break;
            }
        }


        LocalDate data = videoEdicao.getDataPublicacao();
        datePicker.updateDate(data.getYear(), data.getMonthValue() - 1, data.getDayOfMonth());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cadastro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.menu_salvar) {
            salvarDados();
            return true;
        } else if (id == R.id.menu_limpar) {

            editTitulo.setText("");
            rbHorizontal.setChecked(true);
            if (spCanais.getCount() > 0) {
                spCanais.setSelection(0);
            }

            LocalDate hoje = LocalDate.now();
            datePicker.updateDate(hoje.getYear(), hoje.getMonthValue() - 1, hoje.getDayOfMonth());

            Toast.makeText(this, R.string.msg_cleared, Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }







    private void salvarDados() {
        String titulo = editTitulo.getText().toString().trim();

        if (titulo.isEmpty() || spCanais.getSelectedItem() == null) {
            Toast.makeText(this, R.string.msg_error, Toast.LENGTH_SHORT).show();
            return;
        }

        String formato = rbVertical.isChecked() ? "Vertical" : "Horizontal";
        Canal canalSelecionado = (Canal) spCanais.getSelectedItem();
        int canalId = (int) canalSelecionado.getId(); // convert o long do room p int ****

        LocalDate data = LocalDate.of(datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth());

        if (videoEdicao == null) {
            Video novo = new Video(titulo, formato, canalId, data);
            dao.inserirVideo(novo);
        } else {
            videoEdicao.setTitulo(titulo);
            videoEdicao.setFormato(formato);
            videoEdicao.setCanalId(canalId);
            videoEdicao.setDataPublicacao(data);
            dao.atualizarVideo(videoEdicao);
        }

        SharedPreferences pref = getSharedPreferences("config_app", MODE_PRIVATE);
        pref.edit().putString("ultimo_titulo", titulo).apply();

        finish();
    }
}