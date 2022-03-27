package com.maltauro.alunomobile.fragments;

import static com.maltauro.alunomobile.utils.Util.mensagemDialog;
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
import com.maltauro.alunomobile.dao.FrequenciaDAO;
import com.maltauro.alunomobile.dao.TurmaAlunoDAO;
import com.maltauro.alunomobile.dao.TurmaDAO;
import com.maltauro.alunomobile.models.Aluno;
import com.maltauro.alunomobile.models.Curso;
import com.maltauro.alunomobile.models.Disciplina;
import com.maltauro.alunomobile.models.Frequencia;
import com.maltauro.alunomobile.models.Turma;
import com.maltauro.alunomobile.models.TurmaAluno;
import com.maltauro.alunomobile.utils.Util;
import java.util.ArrayList;
import java.util.List;
import fr.ganfra.materialspinner.MaterialSpinner;

public class FrequenciaFragment extends Fragment {

    private Activity activity;
    private ConstraintLayout ctFrequencia;
    private MaterialSpinner spCursoFrequencia;
    private MaterialSpinner spTurmaFrequencia;
    private MaterialSpinner spDisciplinaFrequencia;
    private MaterialSpinner spAulaFrequencia;
    private ListView lvAlunoFrequencia;
    private LinearLayout lnTurmaFrequencia;
    private LinearLayout lnDisciplinaFrequencia;
    private LinearLayout lnAulaFrequencia;
    private FloatingActionButton fabGravaFrequencia;
    private List<Aluno> alunos;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = requireActivity();

        ctFrequencia = activity.findViewById(R.id.ct_frequencia);
        spCursoFrequencia = activity.findViewById(R.id.sp_curso_frequencia);
        spTurmaFrequencia = activity.findViewById(R.id.sp_turma_frequencia);
        spDisciplinaFrequencia = activity.findViewById(R.id.sp_disciplina_frequencia);
        spAulaFrequencia = activity.findViewById(R.id.sp_aula_frequencia);
        lvAlunoFrequencia = activity.findViewById(R.id.lv_aluno_frequencia);
        lnTurmaFrequencia = activity.findViewById(R.id.ln_turma_frequencia);
        lnDisciplinaFrequencia = activity.findViewById(R.id.ln_disciplina_frequencia);
        lnAulaFrequencia = activity.findViewById(R.id.ln_aula_frequencia);
        fabGravaFrequencia = activity.findViewById(R.id.fab_grava_frequencia);

        spCursoFrequencia.setOnItemSelectedListener(cursoListener);
        spTurmaFrequencia.setOnItemSelectedListener(turmaListener);
        spDisciplinaFrequencia.setOnItemSelectedListener(disciplinaListener);
        lvAlunoFrequencia.setOnItemClickListener(alunoListener);
        fabGravaFrequencia.setOnClickListener(view1 -> gravaFrequencia());

        iniciaSpinner();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_frequencia, container, false);
    }

    private final AdapterView.OnItemSelectedListener cursoListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            spTurmaFrequencia.setSelection(0);

            if (spCursoFrequencia.getSelectedItemPosition() != 0) {
                lnTurmaFrequencia.setVisibility(View.VISIBLE);

                String idCurso = String.valueOf(((Curso) spCursoFrequencia.getSelectedItem()).getId());

                List<Turma> turmas = TurmaDAO.getListTurmasCurso(idCurso);
                ArrayAdapter adapterTurmas = new ArrayAdapter(activity, android.R.layout.simple_list_item_1, turmas);
                spTurmaFrequencia.setAdapter(adapterTurmas);
            }
            else
                lnTurmaFrequencia.setVisibility(View.GONE);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            lnTurmaFrequencia.setVisibility(View.GONE);
        }
    };

    private final AdapterView.OnItemSelectedListener turmaListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            spDisciplinaFrequencia.setSelection(0);

            if (spTurmaFrequencia.getSelectedItemPosition() != 0) {
                lnDisciplinaFrequencia.setVisibility(View.VISIBLE);

                Turma turma = (Turma) spTurmaFrequencia.getSelectedItem();

                List<Disciplina> disciplinas = DisciplinaDAO.getListDisciplinasGradeCurricular(String.valueOf(turma.getId()));
                ArrayAdapter adapterDisciplinas = new ArrayAdapter(activity, android.R.layout.simple_list_item_1, disciplinas);
                spDisciplinaFrequencia.setAdapter(adapterDisciplinas);

                alunos = AlunoDAO.getListAlunosTurma(String.valueOf(turma.getId()));
                ArrayAdapter adapterAlunos = new ArrayAdapter(activity, android.R.layout.simple_list_item_multiple_choice, alunos);
                lvAlunoFrequencia.setAdapter(adapterAlunos);
            }
            else
                lnDisciplinaFrequencia.setVisibility(View.GONE);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            lnDisciplinaFrequencia.setVisibility(View.GONE);
        }
    };

    private final AdapterView.OnItemSelectedListener disciplinaListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            spAulaFrequencia.setSelection(0);

            if (spDisciplinaFrequencia.getSelectedItemPosition() != 0) {
                lnAulaFrequencia.setVisibility(View.VISIBLE);
                lvAlunoFrequencia.setVisibility(View.VISIBLE);
                fabGravaFrequencia.setVisibility(View.VISIBLE);

                Disciplina disciplina = (Disciplina) spDisciplinaFrequencia.getSelectedItem();

                List<Integer> aulasNumeros = new ArrayList<>();
                for (int aulaNumero = 1; aulaNumero <= disciplina.getQuantidadeAulas(); aulaNumero++)
                    aulasNumeros.add(aulaNumero);

                ArrayAdapter adapterAulas = new ArrayAdapter(activity, android.R.layout.simple_list_item_1, aulasNumeros);
                spAulaFrequencia.setAdapter(adapterAulas);
            }
            else {
                lnAulaFrequencia.setVisibility(View.GONE);
                lvAlunoFrequencia.setVisibility(View.GONE);
                fabGravaFrequencia.setVisibility(View.GONE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            lnAulaFrequencia.setVisibility(View.GONE);
            lvAlunoFrequencia.setVisibility(View.GONE);
            fabGravaFrequencia.setVisibility(View.GONE);
        }
    };

    private final AdapterView.OnItemClickListener alunoListener = (adapterView, view, position, id) -> {
        alunos.get(position).marcaDesmarcaPrecensa();
    };

    private void iniciaSpinner() {
        List<Curso> cursos = CursoDAO.getListCursos("", new String[]{}, "codigo_mec desc");
        ArrayAdapter adapterCursos = new ArrayAdapter(activity, android.R.layout.simple_list_item_1, cursos);
        spCursoFrequencia.setAdapter(adapterCursos);
        spCursoFrequencia.setSelection(0);
    }

    private boolean validaCampos() {
        if (spAulaFrequencia.getSelectedItemPosition() == 0) {
            spAulaFrequencia.setError("Selecione uma aula!");
            return false;
        }

        long idTurma = ((Turma) spTurmaFrequencia.getSelectedItem()).getId();
        long idTurmaAluno = TurmaAlunoDAO.getListTurmaAlunos("TURMA = ? AND ALUNO = ?", new String[]{ String.valueOf(idTurma), String.valueOf(alunos.get(0).getId()) }, "").get(0).getId();
        long idDisciplina = ((Disciplina) spDisciplinaFrequencia.getSelectedItem()).getId();
        int numeroAula = (Integer) spAulaFrequencia.getSelectedItem();

        Frequencia frequencia = FrequenciaDAO.getFrequenciaExistente(idTurmaAluno, idDisciplina, numeroAula);
        if (frequencia != null) {
            mensagemDialog("Atenção", "Já existe uma frequência cadastrada para essa aula!", activity);
            return false;
        }

        return true;
    }

    private void gravaFrequencia() {
        if (validaCampos()) {
            boolean sucesso = false;

            long idTurma = ((Turma) spTurmaFrequencia.getSelectedItem()).getId();
            Disciplina disciplina = (Disciplina) spDisciplinaFrequencia.getSelectedItem();
            int numeroAula = (Integer) spAulaFrequencia.getSelectedItem();

            for (Aluno aluno : alunos) {
                TurmaAluno turmaAluno = TurmaAlunoDAO.getListTurmaAlunos("TURMA = ? AND ALUNO = ?", new String[]{ String.valueOf(idTurma), String.valueOf(aluno.getId()) }, "").get(0);

                Frequencia frequencia = new Frequencia();
                frequencia.setDisciplina(disciplina);
                frequencia.setNumeroAula(numeroAula);
                frequencia.setTurmaAluno(turmaAluno);
                frequencia.setPresenca(aluno.isPrecensa());

                sucesso = FrequenciaDAO.salvar(frequencia) > 0;
                if (!sucesso)
                    Util.showSnackBar(ctFrequencia, "Erro ao salvar a frequência, verifique o log!");
            }

            if (sucesso)
                Util.showSnackBar(ctFrequencia, "Frequência salva com sucesso!");
        }
    }
}
