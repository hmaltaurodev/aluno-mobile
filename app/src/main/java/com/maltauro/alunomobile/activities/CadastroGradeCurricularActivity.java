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
import com.maltauro.alunomobile.R;
import com.maltauro.alunomobile.dao.CursoDAO;
import com.maltauro.alunomobile.dao.DisciplinaDAO;
import com.maltauro.alunomobile.dao.GradeCurricularDAO;
import com.maltauro.alunomobile.dao.GradeCurricularDisciplinaDAO;
import com.maltauro.alunomobile.enums.AnoAcademico;
import com.maltauro.alunomobile.enums.RegimeAcademico;
import com.maltauro.alunomobile.enums.SemestrePeriodo;
import com.maltauro.alunomobile.models.Curso;
import com.maltauro.alunomobile.models.Disciplina;
import com.maltauro.alunomobile.models.GradeCurricular;
import com.maltauro.alunomobile.models.GradeCurricularDisciplina;
import com.maltauro.alunomobile.utils.Util;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import fr.ganfra.materialspinner.MaterialSpinner;

public class CadastroGradeCurricularActivity extends AppCompatActivity {

    private ConstraintLayout ctCadastroGradeCurricular;
    private MaterialSpinner spCurso;
    private MaterialSpinner spAnoAcademico;
    private MaterialSpinner spRegimeAcademico;
    private MaterialSpinner spSemestrePeriodo;
    private MaterialSpinner spDisciplinas;
    private ListView lvDisciplinas;
    private LinearLayout lnSemestrePeriodo;
    private List<Disciplina> disciplinas = new ArrayList<>();
    private final List<Disciplina> disciplinasGradeCurricular = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_grade_curricular);
        ctCadastroGradeCurricular = findViewById(R.id.ct_cadastro_grade_curricular);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        spCurso = findViewById(R.id.sp_curso_grade_curricular);
        spAnoAcademico = findViewById(R.id.sp_ano_academico_grade_curricular);
        spRegimeAcademico = findViewById(R.id.sp_regime_academico_grade_curricular);
        spSemestrePeriodo = findViewById(R.id.sp_semestre_periodo_grade_curricular);
        spDisciplinas = findViewById(R.id.sp_disciplinas_grade_curricular);
        lvDisciplinas = findViewById(R.id.lv_disciplinas_grade_curricular);
        lnSemestrePeriodo = findViewById(R.id.ln_semestre_periodo);

        FloatingActionButton fabAddDisciplina = findViewById(R.id.fab_add_disciplina);
        FloatingActionButton fabGravaGradeCurricular = findViewById(R.id.fab_grava_grade_curricular);

        spRegimeAcademico.setOnItemSelectedListener(regimeAcademicoListener);
        fabAddDisciplina.setOnClickListener(view -> adicionaDisciplina());
        fabGravaGradeCurricular.setOnClickListener(view -> gravaGradeCurricular());

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

    private final AdapterView.OnItemSelectedListener regimeAcademicoListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            spSemestrePeriodo.setSelection(0);

            if (spRegimeAcademico.getSelectedItemPosition() == 1)
                lnSemestrePeriodo.setVisibility(View.VISIBLE);
            else
                lnSemestrePeriodo.setVisibility(View.GONE);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            lnSemestrePeriodo.setVisibility(View.GONE);
        }
    };

    private void iniciaSpinners() {
        List<Curso> cursos = CursoDAO.getListCursos("", new String[]{}, "descricao asc");
        ArrayAdapter adapterCursos = new ArrayAdapter(this, android.R.layout.simple_list_item_1, cursos);
        spCurso.setAdapter(adapterCursos);

        ArrayAdapter adapterAnoAcademico = new ArrayAdapter(this, android.R.layout.simple_list_item_1, AnoAcademico.values());
        spAnoAcademico.setAdapter(adapterAnoAcademico);

        ArrayAdapter adapterRegimeAcademico = new ArrayAdapter(this, android.R.layout.simple_list_item_1, RegimeAcademico.values());
        spRegimeAcademico.setAdapter(adapterRegimeAcademico);

        ArrayAdapter adapterSemestrePeriodo = new ArrayAdapter(this, android.R.layout.simple_list_item_1, SemestrePeriodo.values());
        spSemestrePeriodo.setAdapter(adapterSemestrePeriodo);

        disciplinas = DisciplinaDAO.getListDisciplinas("", new String[]{}, "descricao asc");
        ArrayAdapter adapterDisciplinas = new ArrayAdapter(this, android.R.layout.simple_list_item_1, disciplinas);
        spDisciplinas.setAdapter(adapterDisciplinas);
    }

    private boolean validaCampos() {
        if (spCurso.getSelectedItemPosition() == 0) {
            spCurso.setError("Selecione um curso!");
            return false;
        }

        if (spAnoAcademico.getSelectedItemPosition() == 0) {
            spAnoAcademico.setError("Selecione um ano acâdemico!");
            return false;
        }

        if (spRegimeAcademico.getSelectedItemPosition() == 0) {
            spRegimeAcademico.setError("Selecione um regime acâdemico!");
            return false;
        }

        if (spRegimeAcademico.getSelectedItemPosition() == 1 &&
            spSemestrePeriodo.getSelectedItemPosition() == 0) {
            spSemestrePeriodo.setError("Selecione um semestre!");
            return false;
        }

        if (disciplinasGradeCurricular.size() == 0) {
            spDisciplinas.setError("Adicione ou menos uma disciplina a grade curricular!");
            return false;
        }

        long idCurso = ((Curso) spCurso.getSelectedItem()).getId();
        int anoAcademico = spAnoAcademico.getSelectedItemPosition();
        int regimeAcademico = spRegimeAcademico.getSelectedItemPosition();
        int semestrePeriodo = spSemestrePeriodo.getSelectedItemPosition();

        GradeCurricular gradeCurricular = GradeCurricularDAO.getGradeCurricularExistente(idCurso, anoAcademico, regimeAcademico, semestrePeriodo);
        if (gradeCurricular != null) {
            String mensagem = (semestrePeriodo != 0) ? ", ano acâdemico e semestre!" : " e ano acâdemico!";
            mensagemDialog("Atenção", "Já existe uma grade curricular ativa para esse curso" + mensagem, this);
            return false;
        }

        return true;
    }

    private void gravaGradeCurricular() {
        if (validaCampos()) {
            GradeCurricular gradeCurricular = new GradeCurricular();

            gradeCurricular.setCurso((Curso) spCurso.getSelectedItem());
            gradeCurricular.setAnoAcademico(spAnoAcademico.getSelectedItemPosition());
            gradeCurricular.setRegimeAcademico(spRegimeAcademico.getSelectedItemPosition());
            gradeCurricular.setSemestrePeriodo(spSemestrePeriodo.getSelectedItemPosition());

            if (GradeCurricularDAO.salvar(gradeCurricular) > 0) {
                for (Disciplina disciplina : disciplinasGradeCurricular) {
                    GradeCurricularDisciplina gradeCurricularDisciplina = new GradeCurricularDisciplina();

                    gradeCurricularDisciplina.setGradeCurricular(gradeCurricular);
                    gradeCurricularDisciplina.setDisciplina(disciplina);

                    if (GradeCurricularDisciplinaDAO.salvar(gradeCurricularDisciplina) <= 0)
                        Util.showSnackBar(ctCadastroGradeCurricular, "Erro ao salvar a disciplina da grade curricular, verifique o log!");
                }
                
                setResult(Activity.RESULT_OK);
                finish();
            }
            else
                Util.showSnackBar(ctCadastroGradeCurricular, "Erro ao salvar a grade curricular, verifique o log!");
        }
    }

    private void adicionaDisciplina() {
        Disciplina disciplina = (Disciplina) spDisciplinas.getSelectedItem();
        disciplinasGradeCurricular.add(disciplina);
        disciplinas.remove(disciplina);
        spDisciplinas.setSelection(0);

        ArrayAdapter adapterDisciplinas = new ArrayAdapter(this, android.R.layout.simple_list_item_1, disciplinasGradeCurricular);
        lvDisciplinas.setAdapter(adapterDisciplinas);
    }
}
