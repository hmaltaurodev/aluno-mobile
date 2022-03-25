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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.maltauro.alunomobile.R;
import com.maltauro.alunomobile.dao.AlunoDAO;
import com.maltauro.alunomobile.dao.CursoDAO;
import com.maltauro.alunomobile.dao.DisciplinaDAO;
import com.maltauro.alunomobile.dao.NotaDAO;
import com.maltauro.alunomobile.dao.TurmaAlunoDAO;
import com.maltauro.alunomobile.dao.TurmaDAO;
import com.maltauro.alunomobile.enums.Bimestre;
import com.maltauro.alunomobile.models.Aluno;
import com.maltauro.alunomobile.models.Curso;
import com.maltauro.alunomobile.models.Disciplina;
import com.maltauro.alunomobile.models.Nota;
import com.maltauro.alunomobile.models.Turma;
import com.maltauro.alunomobile.models.TurmaAluno;
import com.maltauro.alunomobile.utils.Util;
import java.util.ArrayList;
import java.util.List;
import fr.ganfra.materialspinner.MaterialSpinner;

public class NotaFragment extends Fragment {

    private Activity activity;
    private ConstraintLayout ctNota;
    private MaterialSpinner spCursoNota;
    private MaterialSpinner spTurmaNota;
    private MaterialSpinner spDisciplinaNota;
    private MaterialSpinner spAlunoNota;
    private MaterialSpinner spBimestreNota;
    private TextInputEditText edtNota;
    private LinearLayout lnTurmaNota;
    private LinearLayout lnDisciplinaNota;
    private LinearLayout lnAlunoNota;
    private LinearLayout lnBimestreNota;
    private FloatingActionButton fabGravaNota;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = requireActivity();
        ctNota = view.findViewById(R.id.ct_nota);

        spCursoNota = activity.findViewById(R.id.sp_curso_nota);
        spTurmaNota = activity.findViewById(R.id.sp_turma_nota);
        spDisciplinaNota = activity.findViewById(R.id.sp_disciplina_nota);
        spAlunoNota = activity.findViewById(R.id.sp_aluno_nota);
        spBimestreNota = activity.findViewById(R.id.sp_bimestre_nota);
        edtNota = activity.findViewById(R.id.edt_nota);
        lnTurmaNota = activity.findViewById(R.id.ln_turma_nota);
        lnDisciplinaNota = activity.findViewById(R.id.ln_disciplina_nota);
        lnAlunoNota = activity.findViewById(R.id.ln_aluno_nota);
        lnBimestreNota = activity.findViewById(R.id.ln_bimestre_nota);
        fabGravaNota = activity.findViewById(R.id.fab_grava_nota);

        spCursoNota.setOnItemSelectedListener(cursoListener);
        spTurmaNota.setOnItemSelectedListener(turmaListener);
        fabGravaNota.setOnClickListener(view1 -> gravaNota());

        iniciaSpinner();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nota, container, false);
    }

    private final AdapterView.OnItemSelectedListener cursoListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            spTurmaNota.setSelection(0);

            if (spCursoNota.getSelectedItemPosition() != 0) {
                lnTurmaNota.setVisibility(View.VISIBLE);

                String idCurso = String.valueOf(((Curso) spCursoNota.getSelectedItem()).getId());

                List<Turma> turmas = TurmaDAO.getListTurmasCurso(idCurso);
                ArrayAdapter adapterTurmas = new ArrayAdapter(activity, android.R.layout.simple_list_item_1, turmas);
                spTurmaNota.setAdapter(adapterTurmas);
            }
            else {
                lnTurmaNota.setVisibility(View.GONE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            lnTurmaNota.setVisibility(View.GONE);
        }
    };

    private final AdapterView.OnItemSelectedListener turmaListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            spDisciplinaNota.setSelection(0);
            spAlunoNota.setSelection(0);
            spBimestreNota.setSelection(0);
            edtNota.setText("");

            if (spTurmaNota.getSelectedItemPosition() != 0) {
                lnDisciplinaNota.setVisibility(View.VISIBLE);
                lnAlunoNota.setVisibility(View.VISIBLE);
                lnBimestreNota.setVisibility(View.VISIBLE);
                fabGravaNota.setVisibility(View.VISIBLE);

                Turma turma = (Turma) spTurmaNota.getSelectedItem();

                List<Bimestre> bimestres = new ArrayList<>();
                if (turma.getGradeCurricular().getRegimeAcademico().getId() == 1) {
                    bimestres.add(Bimestre.PRIMEIRO);
                    bimestres.add(Bimestre.SEGUNDO);
                }
                else if (turma.getGradeCurricular().getRegimeAcademico().getId() == 2) {
                    bimestres.add(Bimestre.PRIMEIRO);
                    bimestres.add(Bimestre.SEGUNDO);
                    bimestres.add(Bimestre.TERCEIRO);
                    bimestres.add(Bimestre.QUARTO);
                }

                ArrayAdapter adapterBimestre = new ArrayAdapter(activity, android.R.layout.simple_list_item_1, bimestres);
                spBimestreNota.setAdapter(adapterBimestre);

                List<Disciplina> disciplinas = DisciplinaDAO.getListDisciplinasGradeCurricular(String.valueOf(turma.getId()));
                ArrayAdapter adapterDisciplinas = new ArrayAdapter(activity, android.R.layout.simple_list_item_1, disciplinas);
                spDisciplinaNota.setAdapter(adapterDisciplinas);

                List<Aluno> alunos = AlunoDAO.getListAlunosTurma(String.valueOf(turma.getId()));
                ArrayAdapter adapterAlunos = new ArrayAdapter(activity, android.R.layout.simple_list_item_1, alunos);
                spAlunoNota.setAdapter(adapterAlunos);
            }
            else {
                lnDisciplinaNota.setVisibility(View.GONE);
                lnAlunoNota.setVisibility(View.GONE);
                lnBimestreNota.setVisibility(View.GONE);
                fabGravaNota.setVisibility(View.GONE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            lnDisciplinaNota.setVisibility(View.GONE);
            lnAlunoNota.setVisibility(View.GONE);
            lnBimestreNota.setVisibility(View.GONE);
            fabGravaNota.setVisibility(View.GONE);
        }
    };

    private void iniciaSpinner() {
        List<Curso> cursos = CursoDAO.getListCursos("", new String[]{}, "codigo_mec desc");
        ArrayAdapter adapterCursos = new ArrayAdapter(activity, android.R.layout.simple_list_item_1, cursos);
        spCursoNota.setAdapter(adapterCursos);
        spCursoNota.setSelection(0);
    }

    private boolean validaCampos() {
        if (spDisciplinaNota.getSelectedItemPosition() == 0) {
            spDisciplinaNota.setError("Selecione uma disciplina!");
            return false;
        }

        if (spAlunoNota.getSelectedItemPosition() == 0) {
            spAlunoNota.setError("Selecione um aluno!");
            return false;
        }

        if (spBimestreNota.getSelectedItemPosition() == 0) {
            spBimestreNota.setError("Selecione um bimestre!");
            return false;
        }

        if (edtNota.getText().toString() == "") {
            edtNota.setError("Informe uma nota!");
            return false;
        }

        String idTurma = String.valueOf(((Turma) spTurmaNota.getSelectedItem()).getId());
        String idAluno = String.valueOf(((Aluno) spAlunoNota.getSelectedItem()).getId());
        TurmaAluno turmaAluno = TurmaAlunoDAO.getListTurmaAlunos("TURMA = ? AND ALUNO = ?", new String[]{ idTurma, idAluno }, "").get(0);
        Disciplina disciplina = (Disciplina) spDisciplinaNota.getSelectedItem();
        int bimestre = spBimestreNota.getSelectedItemPosition();

        Nota nota = NotaDAO.getNotaExistente(
                String.valueOf(turmaAluno.getId()),
                String.valueOf(disciplina.getId()),
                String.valueOf(bimestre));

        if (nota != null) {
            mensagemDialog("Atenção", "Já existe uma nota cadastrada para esse aluno, disciplina e bimestre!", activity);
            return false;
        }

        return true;
    }

    private void gravaNota() {
        if (validaCampos()) {
            Nota nota = new Nota();

            String idTurma = String.valueOf(((Turma) spTurmaNota.getSelectedItem()).getId());
            String idAluno = String.valueOf(((Aluno) spAlunoNota.getSelectedItem()).getId());
            TurmaAluno turmaAluno = TurmaAlunoDAO.getListTurmaAlunos("TURMA = ? AND ALUNO = ?", new String[]{ idTurma, idAluno }, "").get(0);

            nota.setTurmaAluno(turmaAluno);
            nota.setDisciplina((Disciplina) spDisciplinaNota.getSelectedItem());
            nota.setBimestre(spBimestreNota.getSelectedItemPosition());
            nota.setNota(Double.parseDouble(edtNota.getText().toString()));

            if (NotaDAO.salvar(nota) > 0)
                Util.showSnackBar(ctNota, "Nota salva com sucesso!");
            else
                Util.showSnackBar(ctNota, "Erro ao salvar a nota, verifique o log!");
        }
    }
}
