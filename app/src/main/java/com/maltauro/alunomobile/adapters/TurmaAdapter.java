package com.maltauro.alunomobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.textview.MaterialTextView;
import com.maltauro.alunomobile.R;
import com.maltauro.alunomobile.models.Turma;
import java.util.List;

public class TurmaAdapter extends RecyclerView.Adapter<TurmaAdapter.TurmaViewHolder> {

    private final List<Turma> turmas;
    private final Context context;

    public TurmaAdapter(List<Turma> turmas, Context context) {
        this.turmas = turmas;
        this.context = context;
    }

    public static class TurmaViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView textViewAnoCursoTurma;
        MaterialTextView textViewAnoSemestreAcademicoTurma;

        public TurmaViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewAnoCursoTurma = itemView.findViewById(R.id.text_view_ano_curso_turma);
            textViewAnoSemestreAcademicoTurma = itemView.findViewById(R.id.text_view_ano_semestre_academico_turma);
        }
    }

    @NonNull
    @Override
    public TurmaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_turma, parent, false);
        return new TurmaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TurmaAdapter.TurmaViewHolder holder, int position) {
        Turma turma = turmas.get(position);

        holder.textViewAnoCursoTurma.setText(String.format("%s - %s", turma.getAnoPeriodo(), turma.getGradeCurricular().getCurso().getDescricao()));
        if (turma.getGradeCurricular().getSemestrePeriodo() != null)
            holder.textViewAnoSemestreAcademicoTurma.setText(String.format("%s/%s", turma.getGradeCurricular().getAnoAcademico(), turma.getGradeCurricular().getSemestrePeriodo()));
        else
            holder.textViewAnoSemestreAcademicoTurma.setText(turma.getGradeCurricular().getAnoAcademico().toString());
    }

    @Override
    public int getItemCount() {
        return turmas.size();
    }
}
