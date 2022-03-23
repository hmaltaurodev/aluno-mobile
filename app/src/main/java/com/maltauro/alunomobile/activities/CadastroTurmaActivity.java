package com.maltauro.alunomobile.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.maltauro.alunomobile.R;
import com.maltauro.alunomobile.adapters.GradeCurricularDisciplinaAdapter;
import com.maltauro.alunomobile.adapters.TurmaAlunoAdapter;
import com.maltauro.alunomobile.dao.AlunoDAO;
import com.maltauro.alunomobile.dao.GradeCurricularDAO;
import com.maltauro.alunomobile.dao.TurmaAlunoDAO;
import com.maltauro.alunomobile.dao.TurmaDAO;
import com.maltauro.alunomobile.models.Aluno;
import com.maltauro.alunomobile.models.GradeCurricular;
import com.maltauro.alunomobile.models.Turma;
import com.maltauro.alunomobile.models.TurmaAluno;
import com.maltauro.alunomobile.utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.ganfra.materialspinner.MaterialSpinner;

public class CadastroTurmaActivity extends AppCompatActivity {

    ConstraintLayout ctCadastroTurma;
    MaterialSpinner spGradeCurricularTurma;
    TextInputEditText edtAnoPeriodoTurma;
    MaterialSpinner spAlunosTurma;
    RecyclerView rvAlunoTurma;
    List<Aluno> alunos = new ArrayList<>();
    List<Aluno> alunosTurma = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_turma);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ctCadastroTurma = findViewById(R.id.ct_cadastro_turma);
        spGradeCurricularTurma = findViewById(R.id.sp_grade_curricular_turma);
        edtAnoPeriodoTurma = findViewById(R.id.edt_ano_periodo_turma);
        spAlunosTurma = findViewById(R.id.sp_alunos_turma);
        rvAlunoTurma = findViewById(R.id.rv_aluno_turma);

        FloatingActionButton fabAddAlunos = findViewById(R.id.fab_add_alunos);
        FloatingActionButton fabGravaTurma = findViewById(R.id.fab_grava_turma);

        fabAddAlunos.setOnClickListener(view -> adicionaAluno());
        fabGravaTurma.setOnClickListener(view -> gravaTurma());

        iniciaSpinners();
    }

    private void iniciaSpinners() {
        List<GradeCurricular> gradesCurriculares = GradeCurricularDAO.getListGradesCurriculares("", new String[]{}, "curso asc");
        ArrayAdapter adapterGradesCurriculares = new ArrayAdapter(this, android.R.layout.simple_list_item_1, gradesCurriculares);
        spGradeCurricularTurma.setAdapter(adapterGradesCurriculares);

        alunos = AlunoDAO.getListAlunosSemTurma();
        ArrayAdapter adapterAlunos = new ArrayAdapter(this, android.R.layout.simple_list_item_1, alunos);
        spAlunosTurma.setAdapter(adapterAlunos);
    }

    private boolean validaCampos() {
        return true;
    }

    private void gravaTurma() {
        if (validaCampos()) {
            Turma turma = new Turma();

            turma.setGradeCurricular((GradeCurricular) spGradeCurricularTurma.getSelectedItem());
            turma.setAnoPeriodo(Integer.parseInt(edtAnoPeriodoTurma.getText().toString()));

            if (TurmaDAO.salvar(turma) > 0) {

                for (Aluno aluno : alunosTurma) {
                    TurmaAluno turmaAluno = new TurmaAluno();

                    turmaAluno.setTurma(turma);
                    turmaAluno.setAluno(aluno);

                    if (TurmaAlunoDAO.salvar(turmaAluno) <= 0)
                        Util.showSnackBar(ctCadastroTurma, "Erro ao salvar o aluno da turma, verifique o log!");
                }

                setResult(Activity.RESULT_OK);
                finish();
            }
            else
                Util.showSnackBar(ctCadastroTurma, "Erro ao salvar a turma, verifique o log!");
        }
    }

    private void adicionaAluno() {
        Aluno aluno = (Aluno) spAlunosTurma.getSelectedItem();
        alunosTurma.add(aluno);
        alunos.remove(aluno);
        spAlunosTurma.setSelection(0);
        carregaRecycleView();
    }

    private void carregaRecycleView() {
        rvAlunoTurma.setLayoutManager(new LinearLayoutManager(this));
        rvAlunoTurma.setAdapter(new TurmaAlunoAdapter(alunosTurma, this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
