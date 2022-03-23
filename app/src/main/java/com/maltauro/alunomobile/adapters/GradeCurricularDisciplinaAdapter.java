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

public class GradeCurricularDisciplinaAdapter extends RecyclerView.Adapter<GradeCurricularDisciplinaAdapter.GradeCurricularDisciplinaViewHolder> {

    private List<Disciplina> disciplinas;
    private Context context;

    public GradeCurricularDisciplinaAdapter(List<Disciplina> disciplinas, Context context) {
        this.disciplinas = disciplinas;
        this.context = context;
    }

    public static class GradeCurricularDisciplinaViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView textViewGradeCurricularDisciplina;

        public GradeCurricularDisciplinaViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewGradeCurricularDisciplina = itemView.findViewById(R.id.text_view_grade_curricular_disciplina);
        }
    }

    @NonNull
    @Override
    public GradeCurricularDisciplinaAdapter.GradeCurricularDisciplinaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_grade_curricular_disciplina, parent, false);
        return new GradeCurricularDisciplinaAdapter.GradeCurricularDisciplinaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GradeCurricularDisciplinaAdapter.GradeCurricularDisciplinaViewHolder holder, int position) {
        Disciplina disciplina = disciplinas.get(position);
        holder.textViewGradeCurricularDisciplina.setText(disciplina.getDescricao());
    }

    @Override
    public int getItemCount() {
        return disciplinas.size();
    }
}
