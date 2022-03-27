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
import com.maltauro.alunomobile.dao.DisciplinaDAO;
import com.maltauro.alunomobile.dao.ProfessorDAO;
import com.maltauro.alunomobile.models.Disciplina;
import com.maltauro.alunomobile.models.Professor;
import com.maltauro.alunomobile.utils.Util;
import java.util.List;
import java.util.Objects;
import fr.ganfra.materialspinner.MaterialSpinner;

public class CadastroDisciplinaActivity extends AppCompatActivity {

    private ConstraintLayout ctCadastroDisciplina;
    private TextInputEditText edtDescricao;
    private TextInputEditText edtHorasAulas;
    private TextInputEditText edtQuantidadeAulas;
    private MaterialSpinner spProfessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_disciplina);
        ctCadastroDisciplina = findViewById(R.id.ct_cadastro_disciplina);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        edtDescricao = findViewById(R.id.edt_descricao_disciplina);
        edtHorasAulas = findViewById(R.id.edt_hora_aula_disciplina);
        edtQuantidadeAulas = findViewById(R.id.edt_quantidade_aula_disciplina);
        spProfessor = findViewById(R.id.sp_professor_disciplina);

        FloatingActionButton fab_grava_disciplina = findViewById(R.id.fab_grava_disciplina);
        fab_grava_disciplina.setOnClickListener(view -> gravaDisciplina());

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
        List<Professor> professores = ProfessorDAO.getListProfessores("", new String[]{}, "nome asc");
        ArrayAdapter adapterProfessores = new ArrayAdapter(this, android.R.layout.simple_list_item_1, professores);
        spProfessor.setAdapter(adapterProfessores);
    }

    private boolean validaCampos() {
        if (edtDescricao.getText().toString().equals("")) {
            edtDescricao.setError("Informe a descrição da disciplina!");
            edtDescricao.requestFocus();
            return false;
        }

        if (edtHorasAulas.getText().toString().equals("")) {
            edtHorasAulas.setError("Informe as horas-aulas da disciplina!");
            edtHorasAulas.requestFocus();
            return false;
        }

        if (edtQuantidadeAulas.getText().toString().equals("")) {
            edtQuantidadeAulas.setError("Informa a quantidade de aulas da disciplina!");
            edtQuantidadeAulas.requestFocus();
            return false;
        }

        if (spProfessor.getSelectedItemPosition() == 0) {
            spProfessor.setError("Selecione um professor!");
            return false;
        }

        return true;
    }

    private void gravaDisciplina() {
        if (validaCampos()) {
            Disciplina disciplina = new Disciplina();

            disciplina.setDescricao(edtDescricao.getText().toString());
            disciplina.setHorasAulas(Integer.parseInt(edtHorasAulas.getText().toString()));
            disciplina.setQuantidadeAulas(Integer.parseInt(edtQuantidadeAulas.getText().toString()));
            disciplina.setProfessor((Professor) spProfessor.getSelectedItem());

            if (DisciplinaDAO.salvar(disciplina) > 0) {
                setResult(Activity.RESULT_OK);
                finish();
            }
            else
                Util.showSnackBar(ctCadastroDisciplina, "Erro ao salvar a disciplina, verifique o log!");
        }
    }
}
