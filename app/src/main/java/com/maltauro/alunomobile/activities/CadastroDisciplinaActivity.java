package com.maltauro.alunomobile.activities;

import static com.maltauro.alunomobile.utils.Util.mensagemDialog;

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
    private TextInputEditText edtDescricaoDisciplina;
    private TextInputEditText edtHorasAulasDisciplina;
    private TextInputEditText edtQuantidadeAulasDisciplina;
    private MaterialSpinner spProfessorDisciplina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_disciplina);
        ctCadastroDisciplina = findViewById(R.id.ct_cadastro_disciplina);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        edtDescricaoDisciplina = findViewById(R.id.edt_descricao_disciplina);
        edtHorasAulasDisciplina = findViewById(R.id.edt_hora_aula_disciplina);
        edtQuantidadeAulasDisciplina = findViewById(R.id.edt_quantidade_aula_disciplina);
        spProfessorDisciplina = findViewById(R.id.sp_professor_disciplina);

        FloatingActionButton fabGravaDisciplina = findViewById(R.id.fab_grava_disciplina);
        FloatingActionButton fabCancelaDisciplina = findViewById(R.id.fab_cancela_disciplina);
        FloatingActionButton fabLimpaDisciplina = findViewById(R.id.fab_limpa_disciplina);

        fabGravaDisciplina.setOnClickListener(view -> gravaDisciplina());
        fabCancelaDisciplina.setOnClickListener(view -> finish());
        fabLimpaDisciplina.setOnClickListener(view -> limpaCampos());

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
        spProfessorDisciplina.setAdapter(adapterProfessores);

        if (professores.size() == 0)
            mensagemDialog("Atenção", "Não será possível realizar o cadastro da disciplina, pois não existem professores cadastrados!", this);
    }

    private boolean validaCampos() {
        if (edtDescricaoDisciplina.getText().toString().equals("")) {
            edtDescricaoDisciplina.setError("Informe a descrição da disciplina!");
            edtDescricaoDisciplina.requestFocus();
            return false;
        }

        if (edtHorasAulasDisciplina.getText().toString().equals("")) {
            edtHorasAulasDisciplina.setError("Informe as horas-aulas da disciplina!");
            edtHorasAulasDisciplina.requestFocus();
            return false;
        }

        if (edtQuantidadeAulasDisciplina.getText().toString().equals("")) {
            edtQuantidadeAulasDisciplina.setError("Informa a quantidade de aulas da disciplina!");
            edtQuantidadeAulasDisciplina.requestFocus();
            return false;
        }

        if (spProfessorDisciplina.getSelectedItemPosition() == 0) {
            spProfessorDisciplina.setError("Selecione um professor!");
            return false;
        }

        return true;
    }

    private void gravaDisciplina() {
        if (validaCampos()) {
            Disciplina disciplina = new Disciplina();

            disciplina.setDescricao(edtDescricaoDisciplina.getText().toString());
            disciplina.setHorasAulas(Integer.parseInt(edtHorasAulasDisciplina.getText().toString()));
            disciplina.setQuantidadeAulas(Integer.parseInt(edtQuantidadeAulasDisciplina.getText().toString()));
            disciplina.setProfessor((Professor) spProfessorDisciplina.getSelectedItem());

            if (DisciplinaDAO.salvar(disciplina) > 0) {
                setResult(Activity.RESULT_OK);
                finish();
            }
            else
                Util.showSnackBar(ctCadastroDisciplina, "Erro ao salvar a disciplina, verifique o log!");
        }
    }

    private void limpaCampos() {
        edtDescricaoDisciplina.setText("");
        edtHorasAulasDisciplina.setText("");
        edtQuantidadeAulasDisciplina.setText("");
        spProfessorDisciplina.setSelection(0);
    }
}
