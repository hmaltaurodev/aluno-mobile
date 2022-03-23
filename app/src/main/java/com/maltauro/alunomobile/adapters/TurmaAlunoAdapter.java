package com.maltauro.alunomobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.textview.MaterialTextView;
import com.maltauro.alunomobile.R;
import com.maltauro.alunomobile.models.Aluno;
import java.util.List;

public class TurmaAlunoAdapter extends RecyclerView.Adapter<TurmaAlunoAdapter.TurmaAlunoAdapterViewHolder> {

    private List<Aluno> alunos;
    private Context context;

    public TurmaAlunoAdapter(List<Aluno> alunos, Context context) {
        this.alunos = alunos;
        this.context = context;
    }

    public static class TurmaAlunoAdapterViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView textViewTurmaAluno;

        public TurmaAlunoAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTurmaAluno = itemView.findViewById(R.id.text_view_turma_aluno);
        }
    }

    @NonNull
    @Override
    public TurmaAlunoAdapter.TurmaAlunoAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_turma_aluno, parent, false);
        return new TurmaAlunoAdapter.TurmaAlunoAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TurmaAlunoAdapter.TurmaAlunoAdapterViewHolder holder, int position) {
        Aluno aluno = alunos.get(position);
        holder.textViewTurmaAluno.setText(aluno.getNome());
    }

    @Override
    public int getItemCount() {
        return alunos.size();
    }
}
