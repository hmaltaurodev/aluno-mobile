package com.maltauro.alunomobile.fragments;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maltauro.alunomobile.R;
import com.maltauro.alunomobile.dao.AlunoDAO;
import com.maltauro.alunomobile.dao.CursoDAO;
import com.maltauro.alunomobile.dao.DisciplinaDAO;
import com.maltauro.alunomobile.dao.TurmaDAO;
import com.maltauro.alunomobile.enums.Bimestre;
import com.maltauro.alunomobile.models.Aluno;
import com.maltauro.alunomobile.models.Curso;
import com.maltauro.alunomobile.models.Disciplina;
import com.maltauro.alunomobile.models.Turma;

import java.util.ArrayList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

public class FrequenciaFragment extends Fragment {

    private Activity activity;
    private ConstraintLayout ctFrequencia;
    private MaterialSpinner spCursoFrequencia;
    private MaterialSpinner spTurmaFrequencia;
    private MaterialSpinner spDisciplinaFrequencia;
    private ListView lvAlunoNota;
    private LinearLayout lnTurmaFrequencia;
    private LinearLayout lnDisciplinaFrequencia;
    private FloatingActionButton fabGravaFrequencia;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = requireActivity();

        spCursoFrequencia = activity.findViewById(R.id.sp_curso_frequencia);
        spTurmaFrequencia = activity.findViewById(R.id.sp_turma_frequencia);
        spDisciplinaFrequencia = activity.findViewById(R.id.sp_disciplina_frequencia);
        lvAlunoNota = activity.findViewById(R.id.lv_aluno_frequencia);
        lnTurmaFrequencia = activity.findViewById(R.id.ln_turma_frequencia);
        lnDisciplinaFrequencia = activity.findViewById(R.id.ln_disciplina_frequencia);
        fabGravaFrequencia = activity.findViewById(R.id.fab_grava_frequencia);

        spCursoFrequencia.setOnItemSelectedListener(cursoListener);
        spTurmaFrequencia.setOnItemSelectedListener(turmaListener);
        fabGravaFrequencia.setOnClickListener(view1 -> gravaFrequencia());

        iniciaSpinner();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_frequencia, container, false);
    }

    private final AdapterView.OnItemSelectedListener cursoListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            spTurmaFrequencia.setSelection(0);

            if (spCursoFrequencia.getSelectedItemPosition() != 0) {
                lnTurmaFrequencia.setVisibility(View.VISIBLE);

                String idCurso = String.valueOf(((Curso) spCursoFrequencia.getSelectedItem()).getId());

                List<Turma> turmas = TurmaDAO.getListTurmasCurso(idCurso);
                ArrayAdapter adapterTurmas = new ArrayAdapter(activity, android.R.layout.simple_list_item_1, turmas);
                spTurmaFrequencia.setAdapter(adapterTurmas);
            }
            else {
                lnTurmaFrequencia.setVisibility(View.GONE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            lnTurmaFrequencia.setVisibility(View.GONE);
        }
    };

    private final AdapterView.OnItemSelectedListener turmaListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            spDisciplinaFrequencia.setSelection(0);

            if (spTurmaFrequencia.getSelectedItemPosition() != 0) {
                lnDisciplinaFrequencia.setVisibility(View.VISIBLE);
                lvAlunoNota.setVisibility(View.VISIBLE);
                fabGravaFrequencia.setVisibility(View.VISIBLE);

                Turma turma = (Turma) spTurmaFrequencia.getSelectedItem();

                List<Disciplina> disciplinas = DisciplinaDAO.getListDisciplinasGradeCurricular(String.valueOf(turma.getId()));
                ArrayAdapter adapterDisciplinas = new ArrayAdapter(activity, android.R.layout.simple_list_item_1, disciplinas);
                spDisciplinaFrequencia.setAdapter(adapterDisciplinas);

                List<Aluno> alunos = AlunoDAO.getListAlunosTurma(String.valueOf(turma.getId()));
                ArrayAdapter adapterAlunos = new ArrayAdapter(activity, android.R.layout.simple_list_item_multiple_choice, alunos);
                lvAlunoNota.setAdapter(adapterAlunos);
            }
            else {
                lnDisciplinaFrequencia.setVisibility(View.GONE);
                lvAlunoNota.setVisibility(View.GONE);
                fabGravaFrequencia.setVisibility(View.GONE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            lnDisciplinaFrequencia.setVisibility(View.GONE);
            lvAlunoNota.setVisibility(View.GONE);
            fabGravaFrequencia.setVisibility(View.GONE);
        }
    };

    private void iniciaSpinner() {
        List<Curso> cursos = CursoDAO.getListCursos("", new String[]{}, "codigo_mec desc");
        ArrayAdapter adapterCursos = new ArrayAdapter(activity, android.R.layout.simple_list_item_1, cursos);
        spCursoFrequencia.setAdapter(adapterCursos);
        spCursoFrequencia.setSelection(0);
    }

    private boolean validaCampos() {
        return true;
    }

    private void gravaFrequencia() {

    }
}
