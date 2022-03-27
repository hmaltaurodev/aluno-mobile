package com.maltauro.alunomobile.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.maltauro.alunomobile.R;
import com.maltauro.alunomobile.dao.CursoDAO;
import com.maltauro.alunomobile.enums.GrauAcademico;
import com.maltauro.alunomobile.models.Curso;
import com.maltauro.alunomobile.utils.Util;
import java.util.Objects;
import fr.ganfra.materialspinner.MaterialSpinner;

public class CadastroCursoActivity extends AppCompatActivity {

    private ConstraintLayout ctCadastroCurso;
    private TextInputEditText edtCodigoMECCurso;
    private TextInputEditText edtDescricaoCurso;
    private MaterialSpinner spGrauAcademicoCurso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_curso);
        ctCadastroCurso = findViewById(R.id.ct_cadastro_curso);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        edtCodigoMECCurso = findViewById(R.id.edt_codigo_mec_curso);
        edtDescricaoCurso = findViewById(R.id.edt_descricao_curso);
        spGrauAcademicoCurso = findViewById(R.id.sp_grau_academico_curso);

        FloatingActionButton fabGravaCurso = findViewById(R.id.fab_grava_curso);
        FloatingActionButton fabCancelaCurso = findViewById(R.id.fab_cancela_curso);
        FloatingActionButton fabLimpaCurso = findViewById(R.id.fab_limpa_curso);

        fabGravaCurso.setOnClickListener(view -> gravaCurso());
        fabCancelaCurso.setOnClickListener(view -> finish());
        fabLimpaCurso.setOnClickListener(view -> limpaCampos());

        iniciaSpinner();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void iniciaSpinner() {
        ArrayAdapter adapterGrauAcademico = new ArrayAdapter(this, android.R.layout.simple_list_item_1, GrauAcademico.values());
        spGrauAcademicoCurso.setAdapter(adapterGrauAcademico);
    }

    private boolean validaCampos() {
        if (edtCodigoMECCurso.getText().toString().equals("")) {
            edtCodigoMECCurso.setError("Informe o código MEC do curso!");
            edtCodigoMECCurso.requestFocus();
            return false;
        }

        if (edtDescricaoCurso.getText().toString().equals("")) {
            edtDescricaoCurso.setError("Informe a descrição do curso!");
            edtDescricaoCurso.requestFocus();
            return false;
        }

        if (spGrauAcademicoCurso.getSelectedItemPosition() == 0) {
            spGrauAcademicoCurso.setError("Selecione um grau acadêmico!");
            return false;
        }

        return true;
    }

    private void gravaCurso() {
        if (validaCampos()) {
            Curso curso = new Curso();

            curso.setCodigoMEC(Integer.parseInt(edtCodigoMECCurso.getText().toString()));
            curso.setDescricao(edtDescricaoCurso.getText().toString());
            curso.setGrauAcademico((GrauAcademico) spGrauAcademicoCurso.getSelectedItem());

            if (CursoDAO.salvar(curso) > 0) {
                setResult(Activity.RESULT_OK);
                finish();
            }
            else
                Util.showSnackBar(ctCadastroCurso, "Erro ao salvar o curso, verifique o log!");
        }
    }

    private void limpaCampos() {
        edtCodigoMECCurso.setText("");
        edtDescricaoCurso.setText("");
        spGrauAcademicoCurso.setSelection(0);

        InputMethodManager manager = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(ctCadastroCurso.getWindowToken(), 0);
    }
}
