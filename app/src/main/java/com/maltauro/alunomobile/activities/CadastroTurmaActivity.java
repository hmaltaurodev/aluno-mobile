package com.maltauro.alunomobile.activities;

import static com.maltauro.alunomobile.utils.Util.mensagemDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
    private MaterialSpinner spCursosTurma;
    private MaterialSpinner spGradeCurricularTurma;
    private TextInputEditText edtAnoPeriodoTurma;
    private MaterialSpinner spAlunosTurma;
    private ListView lvAlunosTurma;
    private LinearLayout lnGradeCurricularTurma;
    private TextInputLayout ilAnoPeriodoTurma;
    private LinearLayout lnAlunosTurma;
    private FloatingActionButton fabGravaTurma;
    private List<Aluno> alunos = new ArrayList<>();
    private final List<Aluno> alunosTurma = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_turma);
        activity = this;
        ctCadastroTurma = findViewById(R.id.ct_cadastro_turma);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        spCursosTurma = findViewById(R.id.sp_curso_turma);
        spGradeCurricularTurma = findViewById(R.id.sp_grade_curricular_turma);
        edtAnoPeriodoTurma = findViewById(R.id.edt_ano_periodo_turma);
        spAlunosTurma = findViewById(R.id.sp_alunos_turma);
        lvAlunosTurma = findViewById(R.id.lv_aluno_turma);
        lnGradeCurricularTurma = findViewById(R.id.ln_grade_curricular_turma);
        ilAnoPeriodoTurma = findViewById(R.id.il_ano_periodo_turma);
        lnAlunosTurma = findViewById(R.id.ln_alunos_turma);

        FloatingActionButton fabAddAlunos = findViewById(R.id.fab_add_alunos);
        fabGravaTurma = findViewById(R.id.fab_grava_turma);
        FloatingActionButton fabCancelaTurma  = findViewById(R.id.fab_cancela_turma);
        FloatingActionButton fabLimpaTurma  = findViewById(R.id.fab_limpa_turma);

        spCursosTurma.setOnItemSelectedListener(cursoListener);
        fabAddAlunos.setOnClickListener(view -> adicionaAluno());
        fabGravaTurma.setOnClickListener(view -> gravaTurma());
        fabCancelaTurma .setOnClickListener(view -> finish());
        fabLimpaTurma .setOnClickListener(view -> limpaCampos());

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
            spGradeCurricularTurma.setSelection(0);
            edtAnoPeriodoTurma.setText("");
            spAlunosTurma.setSelection(0);

            if (spCursosTurma.getSelectedItemPosition() != 0) {
                lnGradeCurricularTurma.setVisibility(View.VISIBLE);
                ilAnoPeriodoTurma.setVisibility(View.VISIBLE);
                lnAlunosTurma.setVisibility(View.VISIBLE);
                lvAlunosTurma.setVisibility(View.VISIBLE);
                fabGravaTurma.setVisibility(View.VISIBLE);

                long idCurso = ((Curso) spCursosTurma.getSelectedItem()).getId();

                List<GradeCurricular> gradesCurriculares = GradeCurricularDAO.getListGradesCurriculares(" CURSO = ? ", new String[]{ String.valueOf(idCurso) }, "curso asc");
                ArrayAdapter adapterGradesCurriculares = new ArrayAdapter(activity, android.R.layout.simple_list_item_1, gradesCurriculares);
                spGradeCurricularTurma.setAdapter(adapterGradesCurriculares);

                if (gradesCurriculares.size() == 0)
                    mensagemDialog("Atenção", "Não será possível realizar o cadastro da turma, pois não existem grades curriculares cadastradas para esse curso!", activity);
            }
            else {
                lnGradeCurricularTurma.setVisibility(View.GONE);
                ilAnoPeriodoTurma.setVisibility(View.GONE);
                lnAlunosTurma.setVisibility(View.GONE);
                lvAlunosTurma.setVisibility(View.GONE);
                fabGravaTurma.setVisibility(View.GONE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            lnGradeCurricularTurma.setVisibility(View.GONE);
            ilAnoPeriodoTurma.setVisibility(View.GONE);
            lnAlunosTurma.setVisibility(View.GONE);
            lvAlunosTurma.setVisibility(View.GONE);
            fabGravaTurma.setVisibility(View.GONE);
        }
    };

    private void iniciaSpinners() {
        List<Curso> cursos = CursoDAO.getListCursos("", new String[]{}, "descricao asc");
        ArrayAdapter adapterCursos = new ArrayAdapter(this, android.R.layout.simple_list_item_1, cursos);
        spCursosTurma.setAdapter(adapterCursos);

        carregaSpinnerAluno();

        if (cursos.size() == 0)
            mensagemDialog("Atenção", "Não será possível realizar o cadastro da turma, pois não existem cursos cadastrados!", this);
        else if (alunos.size() == 0)
            mensagemDialog("Atenção", "Não será possível realizar o cadastro da turma, pois não existem alunos cadastrados sem turma!", this);
    }

    private void carregaSpinnerAluno() {
        alunos = AlunoDAO.getListAlunosSemTurma();
        ArrayAdapter adapterAlunos = new ArrayAdapter(this, android.R.layout.simple_list_item_1, alunos);
        spAlunosTurma.setAdapter(adapterAlunos);
    }

    private boolean validaCampos() {
        if (spGradeCurricularTurma.getSelectedItemPosition() == 0) {
            spGradeCurricularTurma.setError("Selecine uma grade curricular!");
            return false;
        }

        if (edtAnoPeriodoTurma.getText().toString().equals("")) {
            edtAnoPeriodoTurma.setError("Informe o ano da turma!");
            edtAnoPeriodoTurma.requestFocus();
            return false;
        }

        if (alunosTurma.size() == 0) {
            spAlunosTurma.setError("Adicione ao menos um aluno a turma!");
            return false;
        }

        long idGradeCurricular = ((GradeCurricular) spGradeCurricularTurma.getSelectedItem()).getId();
        int anoPeriodo = Integer.parseInt(edtAnoPeriodoTurma.getText().toString());

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

            turma.setGradeCurricular((GradeCurricular) spGradeCurricularTurma.getSelectedItem());
            turma.setAnoPeriodo(Integer.parseInt(edtAnoPeriodoTurma.getText().toString()));

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

    private void limpaCampos() {
        spCursosTurma.setSelection(0);
        alunosTurma.clear();

        carregaSpinnerAluno();
        carregaListViewAlunos();

        InputMethodManager manager = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(ctCadastroTurma.getWindowToken(), 0);
    }

    private void adicionaAluno() {
        Aluno aluno = (Aluno) spAlunosTurma.getSelectedItem();
        alunosTurma.add(aluno);
        alunos.remove(aluno);
        spAlunosTurma.setSelection(0);

        carregaListViewAlunos();
    }

    private void carregaListViewAlunos() {
        ArrayAdapter adapterAlunos = new ArrayAdapter(this, android.R.layout.simple_list_item_1, alunosTurma);
        lvAlunosTurma.setAdapter(adapterAlunos);
    }
}
