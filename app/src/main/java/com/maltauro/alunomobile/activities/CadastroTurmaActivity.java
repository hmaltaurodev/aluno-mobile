package com.maltauro.alunomobile.activities;

import static com.maltauro.alunomobile.utils.Util.mensagemDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.maltauro.alunomobile.R;
import com.maltauro.alunomobile.dao.AlunoDAO;
import com.maltauro.alunomobile.dao.CursoDAO;
import com.maltauro.alunomobile.dao.GradeCurricularDAO;
import com.maltauro.alunomobile.dao.TurmaAlunoDAO;
import com.maltauro.alunomobile.dao.TurmaDAO;
import com.maltauro.alunomobile.models.Aluno;
import com.maltauro.alunomobile.models.Curso;
import com.maltauro.alunomobile.models.GradeCurricular;
import com.maltauro.alunomobile.models.Turma;
import com.maltauro.alunomobile.models.TurmaAluno;
import com.maltauro.alunomobile.utils.Util;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import fr.ganfra.materialspinner.MaterialSpinner;

public class CadastroTurmaActivity extends AppCompatActivity {

    private Activity activity;
    private ConstraintLayout ctCadastroTurma;
    private MaterialSpinner spCursos;
    private MaterialSpinner spGradeCurricular;
    private TextInputEditText edtAnoPeriodo;
    private MaterialSpinner spAlunos;
    private ListView lvAlunos;
    private LinearLayout lnGradeCurricular;
    private TextInputLayout ilAnoPeriodo;
    private LinearLayout lnAlunos;
    private FloatingActionButton fabGravaTurma;
    private List<Aluno> alunos = new ArrayList<>();
    private final List<Aluno> alunosTurma = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_turma);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ctCadastroTurma = findViewById(R.id.ct_cadastro_turma);
        activity = this;

        spCursos = findViewById(R.id.sp_curso_turma);
        spGradeCurricular = findViewById(R.id.sp_grade_curricular_turma);
        edtAnoPeriodo = findViewById(R.id.edt_ano_periodo_turma);
        spAlunos = findViewById(R.id.sp_alunos_turma);
        lvAlunos = findViewById(R.id.lv_aluno_turma);
        lnGradeCurricular = findViewById(R.id.ln_grade_curricular);
        ilAnoPeriodo = findViewById(R.id.il_ano_periodo);
        lnAlunos = findViewById(R.id.ln_alunos);

        FloatingActionButton fabAddAlunos = findViewById(R.id.fab_add_alunos);
        fabGravaTurma = findViewById(R.id.fab_grava_turma);

        spCursos.setOnItemSelectedListener(cursoListener);
        fabAddAlunos.setOnClickListener(view -> adicionaAluno());
        fabGravaTurma.setOnClickListener(view -> gravaTurma());

        iniciaSpinners();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private final AdapterView.OnItemSelectedListener cursoListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            spGradeCurricular.setSelection(0);
            edtAnoPeriodo.setText("");
            spAlunos.setSelection(0);

            if (spCursos.getSelectedItemPosition() != 0) {
                lnGradeCurricular.setVisibility(View.VISIBLE);
                ilAnoPeriodo.setVisibility(View.VISIBLE);
                lnAlunos.setVisibility(View.VISIBLE);
                lvAlunos.setVisibility(View.VISIBLE);
                fabGravaTurma.setVisibility(View.VISIBLE);

                long idCurso = ((Curso) spCursos.getSelectedItem()).getId();

                List<GradeCurricular> gradesCurriculares = GradeCurricularDAO.getListGradesCurriculares(" CURSO = ? ", new String[]{ String.valueOf(idCurso) }, "curso asc");
                ArrayAdapter adapterGradesCurriculares = new ArrayAdapter(activity, android.R.layout.simple_list_item_1, gradesCurriculares);
                spGradeCurricular.setAdapter(adapterGradesCurriculares);
            }
            else {
                lnGradeCurricular.setVisibility(View.GONE);
                ilAnoPeriodo.setVisibility(View.GONE);
                lnAlunos.setVisibility(View.GONE);
                lvAlunos.setVisibility(View.GONE);
                fabGravaTurma.setVisibility(View.GONE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            lnGradeCurricular.setVisibility(View.GONE);
            ilAnoPeriodo.setVisibility(View.GONE);
            lnAlunos.setVisibility(View.GONE);
            lvAlunos.setVisibility(View.GONE);
            fabGravaTurma.setVisibility(View.GONE);
        }
    };

    private void iniciaSpinners() {
        List<Curso> cursos = CursoDAO.getListCursos("", new String[]{}, "descricao asc");
        ArrayAdapter adapterCursos = new ArrayAdapter(this, android.R.layout.simple_list_item_1, cursos);
        spCursos.setAdapter(adapterCursos);

        alunos = AlunoDAO.getListAlunosSemTurma();
        ArrayAdapter adapterAlunos = new ArrayAdapter(this, android.R.layout.simple_list_item_1, alunos);
        spAlunos.setAdapter(adapterAlunos);

        if (alunos.size() == 0) {
            mensagemDialog("Atenção", "Não será possível realizar o cadastro da turma, pois não existem alunos sem turma!", this);
        }
    }

    private boolean validaCampos() {
        if (spGradeCurricular.getSelectedItemPosition() == 0) {
            spGradeCurricular.setError("Selecine uma grade curricular!");
            return false;
        }

        if (edtAnoPeriodo.getText().toString().equals("")) {
            edtAnoPeriodo.setError("Informe o ano da turma!");
            edtAnoPeriodo.requestFocus();
            return false;
        }

        if (alunosTurma.size() == 0) {
            spAlunos.setError("Adicione ao menos um aluno a turma!");
            return false;
        }

        long idGradeCurricular = ((GradeCurricular) spGradeCurricular.getSelectedItem()).getId();
        int anoPeriodo = Integer.parseInt(edtAnoPeriodo.getText().toString());

        Turma turma = TurmaDAO.getTurmaExistente(idGradeCurricular, anoPeriodo);
        if (turma != null) {
            mensagemDialog("Atenção", "Já existe uma turma ativa para essa grade curricular e ano!", this);
            return false;
        }

        return true;
    }

    private void gravaTurma() {
        if (validaCampos()) {
            Turma turma = new Turma();
            boolean sucesso = false;

            turma.setGradeCurricular((GradeCurricular) spGradeCurricular.getSelectedItem());
            turma.setAnoPeriodo(Integer.parseInt(edtAnoPeriodo.getText().toString()));

            if (TurmaDAO.salvar(turma) > 0) {
                for (Aluno aluno : alunosTurma) {
                    TurmaAluno turmaAluno = new TurmaAluno();

                    turmaAluno.setTurma(turma);
                    turmaAluno.setAluno(aluno);

                    sucesso = TurmaAlunoDAO.salvar(turmaAluno) > 0;
                    if (!sucesso)
                        Util.showSnackBar(ctCadastroTurma, "Erro ao salvar o aluno da turma, verifique o log!");
                }

                if (sucesso) {
                    setResult(Activity.RESULT_OK);
                    finish();
                }
            }
            else
                Util.showSnackBar(ctCadastroTurma, "Erro ao salvar a turma, verifique o log!");
        }
    }

    private void adicionaAluno() {
        Aluno aluno = (Aluno) spAlunos.getSelectedItem();
        alunosTurma.add(aluno);
        alunos.remove(aluno);
        spAlunos.setSelection(0);

        ArrayAdapter adapterAlunos = new ArrayAdapter(this, android.R.layout.simple_list_item_1, alunosTurma);
        lvAlunos.setAdapter(adapterAlunos);
    }
}
