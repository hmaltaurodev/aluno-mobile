package com.maltauro.alunomobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.textview.MaterialTextView;
import com.maltauro.alunomobile.R;
import com.maltauro.alunomobile.models.Curso;
import java.util.List;

public class CursoAdapter extends RecyclerView.Adapter<CursoAdapter.CursoViewHolder> {

    private List<Curso> cursos;
    private Context context;

    public CursoAdapter(List<Curso> cursos, Context context) {
        this.cursos = cursos;
        this.context = context;
    }

    public static class CursoViewHolder extends  RecyclerView.ViewHolder {

        MaterialTextView textViewCodigoMecCurso;
        MaterialTextView textViewDescricaoCurso;
        MaterialTextView textViewGrauAcademicoCurso;

        public CursoViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewCodigoMecCurso = itemView.findViewById(R.id.text_view_codigo_mec_curso);
            textViewDescricaoCurso = itemView.findViewById(R.id.text_view_descricao_curso);
            textViewGrauAcademicoCurso = itemView.findViewById(R.id.text_view_grau_academico_curso);
        }
    }

    @NonNull
    @Override
    public CursoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_curso, parent, false);
        return new CursoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CursoViewHolder holder, int position) {
        Curso curso = cursos.get(position);

        holder.textViewCodigoMecCurso.setText(String.valueOf(curso.getCodigoMEC()));
        holder.textViewDescricaoCurso.setText(curso.getDescricao());
        holder.textViewGrauAcademicoCurso.setText(curso.getGrauAcademico().toString());
    }

    @Override
    public int getItemCount() {
        return cursos.size();
    }
}
