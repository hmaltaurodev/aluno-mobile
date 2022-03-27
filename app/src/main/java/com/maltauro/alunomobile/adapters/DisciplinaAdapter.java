package com.maltauro.alunomobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.textview.MaterialTextView;
import com.maltauro.alunomobile.R;
import com.maltauro.alunomobile.models.Disciplina;
import java.util.List;

public class DisciplinaAdapter extends RecyclerView.Adapter<DisciplinaAdapter.DisciplinaViewHolder> {

    private final List<Disciplina> disciplinas;
    private final Context context;

    public DisciplinaAdapter(List<Disciplina> disciplinas, Context context) {
        this.disciplinas = disciplinas;
        this.context = context;
    }

    public static class DisciplinaViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView textViewDescricaoDisciplina;
        MaterialTextView textViewProfessorDisciplina;
        MaterialTextView textViewHorasAulasDisciplina;
        MaterialTextView textViewQuantidadeAulasDisciplina;

        public DisciplinaViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewDescricaoDisciplina = itemView.findViewById(R.id.text_view_descricao_disciplina);
            textViewProfessorDisciplina = itemView.findViewById(R.id.text_view_professor_disciplina);
            textViewHorasAulasDisciplina = itemView.findViewById(R.id.text_view_horas_aulas_disciplina);
            textViewQuantidadeAulasDisciplina = itemView.findViewById(R.id.text_view_quantidade_aulas_disciplina);
        }
    }

    @NonNull
    @Override
    public DisciplinaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_disciplina, parent, false);
        return new DisciplinaAdapter.DisciplinaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DisciplinaViewHolder holder, int position) {
        Disciplina disciplina = disciplinas.get(position);

        holder.textViewDescricaoDisciplina.setText(disciplina.getDescricao());
        holder.textViewProfessorDisciplina.setText(disciplina.getProfessor().getNome());
        holder.textViewHorasAulasDisciplina.setText(String.valueOf(disciplina.getHorasAulas()));
        holder.textViewQuantidadeAulasDisciplina.setText(String.valueOf(disciplina.getQuantidadeAulas()));
    }

    @Override
    public int getItemCount() {
        return disciplinas.size();
    }
}
