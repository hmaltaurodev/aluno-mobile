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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.maltauro.alunomobile.R;
import com.maltauro.alunomobile.dao.AlunoDAO;
import com.maltauro.alunomobile.dao.DisciplinaDAO;
import com.maltauro.alunomobile.dao.NotaDAO;
import com.maltauro.alunomobile.dao.ProfessorDAO;
import com.maltauro.alunomobile.dao.TurmaAlunoDAO;
import com.maltauro.alunomobile.dao.TurmaDAO;
import com.maltauro.alunomobile.enums.Bimestre;
import com.maltauro.alunomobile.models.Aluno;
import com.maltauro.alunomobile.models.Disciplina;
import com.maltauro.alunomobile.models.Nota;
import com.maltauro.alunomobile.models.Turma;
import com.maltauro.alunomobile.models.TurmaAluno;
import com.maltauro.alunomobile.utils.Util;
import java.util.List;
import fr.ganfra.materialspinner.MaterialSpinner;

public class NotaFragment extends Fragment {

    private Activity activity;
    private ConstraintLayout ctNota;
    private MaterialSpinner spTurmaNota;
    private MaterialSpinner spDisciplinaNota;
    private MaterialSpinner spAlunoNota;
    private MaterialSpinner spBimestreNota;
    private TextInputEditText edtNota;
    private LinearLayout lnDisciplinaNota;
    private LinearLayout lnAlunoNota;
    private LinearLayout lnBimestreNota;
    private FloatingActionButton fabGravaNota;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = requireActivity();
        ctNota = view.findViewById(R.id.ct_nota);

        spTurmaNota = activity.findViewById(R.id.sp_turma_nota);
        spDisciplinaNota = activity.findViewById(R.id.sp_disciplina_nota);
        spAlunoNota = activity.findViewById(R.id.sp_aluno_nota);
        spBimestreNota = activity.findViewById(R.id.sp_bimestre_nota);
        edtNota = activity.findViewById(R.id.edt_nota);
        lnDisciplinaNota = activity.findViewById(R.id.ln_disciplina_nota);
        lnAlunoNota = activity.findViewById(R.id.ln_aluno_nota);
        lnBimestreNota = activity.findViewById(R.id.ln_bimestre_nota);
        fabGravaNota = activity.findViewById(R.id.fab_grava_nota);

        spTurmaNota.setOnItemSelectedListener(turmaNotaListener);
        fabGravaNota.setOnClickListener(view1 -> gravaNota());

        iniciaSpinners();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nota, container, false);
    }

    private final AdapterView.OnItemSelectedListener turmaNotaListener = new AdapterView.OnItemSelectedListener() {
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

                List<Disciplina> disciplinas = DisciplinaDAO.getListDisciplinasGradeCurricular(String.valueOf(((Turma) spTurmaNota.getSelectedItem()).getId()));
                ArrayAdapter adapterDisciplinas = new ArrayAdapter(activity, android.R.layout.simple_list_item_1, disciplinas);
                spDisciplinaNota.setAdapter(adapterDisciplinas);

                List<Aluno> alunos = AlunoDAO.getListAlunosTurma(String.valueOf(((Turma) spTurmaNota.getSelectedItem()).getId()));
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
        public void onNothingSelected(AdapterView<?> adapterView) { }
    };

    private void iniciaSpinners() {
        List<Turma> turmas = TurmaDAO.getListTurmas("", new String[]{}, "ano_periodo desc");
        ArrayAdapter adapterTurmas = new ArrayAdapter(activity, android.R.layout.simple_list_item_1, turmas);
        spTurmaNota.setAdapter(adapterTurmas);

        ArrayAdapter adapterBimestre = new ArrayAdapter(activity, android.R.layout.simple_list_item_1, Bimestre.values());
        spBimestreNota.setAdapter(adapterBimestre);
    }

    private boolean validaCampos() {
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
