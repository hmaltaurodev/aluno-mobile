package com.maltauro.alunomobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.textview.MaterialTextView;
import com.maltauro.alunomobile.R;
import com.maltauro.alunomobile.dao.FrequenciaDAO;
import com.maltauro.alunomobile.models.Disciplina;

import java.util.List;

public class TurmaDisciplinaAdapter extends RecyclerView.Adapter<TurmaDisciplinaAdapter.TurmaDisciplinaViewHolder> {

    private final List<Disciplina> disciplinas;
    private final long idTurma;
    private final Context context;

    public TurmaDisciplinaAdapter(List<Disciplina> disciplinas, long idTurma, Context context) {
        this.disciplinas = disciplinas;
        this.idTurma = idTurma;
        this.context = context;
    }

    public static class TurmaDisciplinaViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView textViewDescricao;
        MaterialTextView textViewNomeProfessor;
        MaterialTextView textViewAulasNinistradas;
        MaterialTextView textViewAulasTotais;

        public TurmaDisciplinaViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewDescricao = itemView.findViewById(R.id.text_view_descricao);
            textViewNomeProfessor = itemView.findViewById(R.id.text_view_nome_professor);
            textViewAulasNinistradas = itemView.findViewById(R.id.text_view_aulas_ministradas);
            textViewAulasTotais = itemView.findViewById(R.id.text_view_aulas_totais);
        }
    }

    @NonNull
    @Override
    public TurmaDisciplinaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_turma_disciplina, parent, false);
        return new TurmaDisciplinaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TurmaDisciplinaViewHolder holder, int position) {
        Disciplina disciplina = disciplinas.get(position);
        int aulasMinistradas = FrequenciaDAO.getAulasMinistradasTurmaDisciplina(idTurma, disciplina.getId());

        holder.textViewDescricao.setText(disciplina.getDescricao());
        holder.textViewNomeProfessor.setText(disciplina.getProfessor().getNome());
        holder.textViewAulasNinistradas.setText(String.valueOf(aulasMinistradas));
        holder.textViewAulasTotais.setText(String.valueOf(disciplina.getQuantidadeAulas()));
    }

    @Override
    public int getItemCount() {
        return disciplinas.size();
    }
}
