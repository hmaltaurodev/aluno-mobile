package com.maltauro.alunomobile.activities;

import static com.maltauro.alunomobile.utils.Util.mensagemDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maltauro.alunomobile.R;
import com.maltauro.alunomobile.adapters.GradeCurricularDisciplinaAdapter;
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

    ConstraintLayout ctCadastroGradeCurricular;
    MaterialSpinner spCursoGradeCurricular;
    MaterialSpinner spAnoAcademicoGradeCurricular;
    MaterialSpinner spRegimeAcademicoGradeCurricular;
    MaterialSpinner spSemestrePeriodoGradeCurricular;
    MaterialSpinner spDisciplinasGradeCurricular;
    RecyclerView rvDisciplinasGradeCurricular;
    List<Disciplina> disciplinas = new ArrayList<>();
    List<Disciplina> disciplinasGradeCurricular = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_grade_curricular);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        spCursoGradeCurricular = findViewById(R.id.sp_curso_grade_curricular);
        spAnoAcademicoGradeCurricular = findViewById(R.id.sp_ano_academico_grade_curricular);
        spRegimeAcademicoGradeCurricular = findViewById(R.id.sp_regime_academico_grade_curricular);
        spSemestrePeriodoGradeCurricular = findViewById(R.id.sp_semestre_periodo_grade_curricular);
        spDisciplinasGradeCurricular = findViewById(R.id.sp_disciplinas_grade_curricular);
        ctCadastroGradeCurricular = findViewById(R.id.ct_cadastro_grade_curricular);
        rvDisciplinasGradeCurricular = findViewById(R.id.rv_disciplinas_grade_curricular);

        FloatingActionButton fabAddDisciplina = findViewById(R.id.fab_add_disciplina);
        FloatingActionButton fabGravaGradeCurricular = findViewById(R.id.fab_grava_grade_curricular);

        fabAddDisciplina.setOnClickListener(view -> adicionaDisciplina());
        fabGravaGradeCurricular.setOnClickListener(view -> gravaGradeCurricular());

        iniciaSpinners();
    }

    private void iniciaSpinners() {
        List<Curso> cursos = CursoDAO.getListCursos("", new String[]{}, "descricao asc");
        ArrayAdapter adapterCursos = new ArrayAdapter(this, android.R.layout.simple_list_item_1, cursos);
        spCursoGradeCurricular.setAdapter(adapterCursos);

        ArrayAdapter adapterAnoAcademico = new ArrayAdapter(this, android.R.layout.simple_list_item_1, AnoAcademico.values());
        spAnoAcademicoGradeCurricular.setAdapter(adapterAnoAcademico);

        ArrayAdapter adapterRegimeAcademico = new ArrayAdapter(this, android.R.layout.simple_list_item_1, RegimeAcademico.values());
        spRegimeAcademicoGradeCurricular.setAdapter(adapterRegimeAcademico);

        ArrayAdapter adapterSemestrePeriodo = new ArrayAdapter(this, android.R.layout.simple_list_item_1, SemestrePeriodo.values());
        spSemestrePeriodoGradeCurricular.setAdapter(adapterSemestrePeriodo);

        disciplinas = DisciplinaDAO.getListDisciplinas("", new String[]{}, "descricao asc");
        ArrayAdapter adapterDisciplinas = new ArrayAdapter(this, android.R.layout.simple_list_item_1, disciplinas);
        spDisciplinasGradeCurricular.setAdapter(adapterDisciplinas);
    }

    private boolean validaCampos() {
        if (spCursoGradeCurricular.getSelectedItemPosition() == 0) {
            spCursoGradeCurricular.setError("Selecione um curso!");
            return false;
        }

        if (spAnoAcademicoGradeCurricular.getSelectedItemPosition() == 0) {
            spAnoAcademicoGradeCurricular.setError("Selecione um ano acâdemico!");
            return false;
        }

        if (spRegimeAcademicoGradeCurricular.getSelectedItemPosition() == 0) {
            spRegimeAcademicoGradeCurricular.setError("Selecione um regime acâdemico!");
            return false;
        }

        if (spRegimeAcademicoGradeCurricular.getSelectedItemPosition() == 1 &&
            spSemestrePeriodoGradeCurricular.getSelectedItemPosition() == 0) {
            spSemestrePeriodoGradeCurricular.setError("Selecione um semestre!");
            return false;
        }

        Curso curso = (Curso) spCursoGradeCurricular.getSelectedItem();
        int anoAcademico = spAnoAcademicoGradeCurricular.getSelectedItemPosition();
        int regimeAcademico = spRegimeAcademicoGradeCurricular.getSelectedItemPosition();
        int semestrePeriodo = spSemestrePeriodoGradeCurricular.getSelectedItemPosition();

        GradeCurricular gradeCurricular = GradeCurricularDAO.getGradeCurricular(
                String.valueOf(curso.getId()),
                String.valueOf(anoAcademico),
                String.valueOf(regimeAcademico),
                String.valueOf(semestrePeriodo));

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

            gradeCurricular.setCurso((Curso) spCursoGradeCurricular.getSelectedItem());
            gradeCurricular.setAnoAcademico(spAnoAcademicoGradeCurricular.getSelectedItemPosition());
            gradeCurricular.setRegimeAcademico(spRegimeAcademicoGradeCurricular.getSelectedItemPosition());
            gradeCurricular.setSemestrePeriodo(spSemestrePeriodoGradeCurricular.getSelectedItemPosition());

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
        Disciplina disciplina = (Disciplina) spDisciplinasGradeCurricular.getSelectedItem();
        disciplinasGradeCurricular.add(disciplina);
        disciplinas.remove(disciplina);
        spDisciplinasGradeCurricular.setSelection(0);
        carregaRecycleView();
    }

    private void carregaRecycleView() {
        rvDisciplinasGradeCurricular.setLayoutManager(new LinearLayoutManager(this));
        rvDisciplinasGradeCurricular.setAdapter(new GradeCurricularDisciplinaAdapter(disciplinasGradeCurricular, this));
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
