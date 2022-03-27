package com.maltauro.alunomobile.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
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
    private TextInputEditText edtCodigoMEC;
    private TextInputEditText edtDescricao;
    private MaterialSpinner spGrauAcademico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_curso);
        ctCadastroCurso = findViewById(R.id.ct_cadastro_curso);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        edtCodigoMEC = findViewById(R.id.edt_codigo_mec_curso);
        edtDescricao = findViewById(R.id.edt_descricao_curso);
        spGrauAcademico = findViewById(R.id.sp_grau_academico);

        FloatingActionButton fab_grava_curso = findViewById(R.id.fab_grava_curso);
        fab_grava_curso.setOnClickListener(view -> gravaCurso());

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
        spGrauAcademico.setAdapter(adapterGrauAcademico);
    }

    private boolean validaCampos() {
        if (edtCodigoMEC.getText().toString().equals("")) {
            edtCodigoMEC.setError("Informe o código MEC do curso!");
            edtCodigoMEC.requestFocus();
            return false;
        }

        if (edtDescricao.getText().toString().equals("")) {
            edtDescricao.setError("Informe a descrição do curso!");
            edtDescricao.requestFocus();
            return false;
        }

        if (spGrauAcademico.getSelectedItemPosition() == 0) {
            spGrauAcademico.setError("Selecione um grau acadêmico!");
            return false;
        }

        return true;
    }

    private void gravaCurso() {
        if (validaCampos()) {
            Curso curso = new Curso();

            curso.setCodigoMEC(Integer.parseInt(edtCodigoMEC.getText().toString()));
            curso.setDescricao(edtDescricao.getText().toString());
            curso.setGrauAcademico((GrauAcademico) spGrauAcademico.getSelectedItem());

            if (CursoDAO.salvar(curso) > 0) {
                setResult(Activity.RESULT_OK);
                finish();
            }
            else
                Util.showSnackBar(ctCadastroCurso, "Erro ao salvar o curso, verifique o log!");
        }
    }
}
