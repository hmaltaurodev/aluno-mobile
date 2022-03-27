package com.maltauro.alunomobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.textview.MaterialTextView;
import com.maltauro.alunomobile.R;
import com.maltauro.alunomobile.models.GradeCurricular;
import java.util.List;

public class GradeCurricularAdapter extends RecyclerView.Adapter<GradeCurricularAdapter.GradeCurricularViewHolder> {

    private final List<GradeCurricular> gradesCurriculares;
    private final Context context;

    public GradeCurricularAdapter(List<GradeCurricular> gradesCurriculares, Context context) {
        this.gradesCurriculares = gradesCurriculares;
        this.context = context;
    }

    public static class GradeCurricularViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView textViewCursoGradeCurricular;
        MaterialTextView textViewAnoSemestreAcademicoGradeCurricular;

        public GradeCurricularViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewCursoGradeCurricular = itemView.findViewById(R.id.text_view_curso_grade_curricular);
            textViewAnoSemestreAcademicoGradeCurricular = itemView.findViewById(R.id.text_view_ano_semestre_academico_grade_curricular);
        }
    }

    @NonNull
    @Override
    public GradeCurricularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_grade_curricular, parent, false);
        return new GradeCurricularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GradeCurricularViewHolder holder, int position) {
        GradeCurricular gradeCurricular = gradesCurriculares.get(position);

        holder.textViewCursoGradeCurricular.setText(gradeCurricular.getCurso().getDescricao());
        if (gradeCurricular.getSemestrePeriodo() != null)
            holder.textViewAnoSemestreAcademicoGradeCurricular.setText(String.format("%s/%s", gradeCurricular.getAnoAcademico().toString(), gradeCurricular.getSemestrePeriodo().toString()));
        else
            holder.textViewAnoSemestreAcademicoGradeCurricular.setText(gradeCurricular.getAnoAcademico().toString());
    }

    @Override
    public int getItemCount() {
        return gradesCurriculares.size();
    }
}
