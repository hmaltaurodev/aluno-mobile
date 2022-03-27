package com.maltauro.alunomobile.adapters;

import static com.maltauro.alunomobile.utils.Util.raPad;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.textview.MaterialTextView;
import com.maltauro.alunomobile.R;
import com.maltauro.alunomobile.models.Professor;
import java.util.List;

public class ProfessorAdapter extends RecyclerView.Adapter<ProfessorAdapter.ProfessorViewHolder> {

    private final List<Professor> professores;
    private final Context context;

    public ProfessorAdapter(List<Professor> professores, Context context) {
        this.professores = professores;
        this.context = context;
    }

    public static class ProfessorViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView textViewRaProfessor;
        MaterialTextView textViewNomeProfessor;
        MaterialTextView textViewCpfProfessor;
        MaterialTextView textViewDataNascimentoProfessor;

        public ProfessorViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewRaProfessor = itemView.findViewById(R.id.text_view_ra_professor);
            textViewNomeProfessor = itemView.findViewById(R.id.text_view_nome_professor);
            textViewCpfProfessor = itemView.findViewById(R.id.text_view_cpf_professor);
            textViewDataNascimentoProfessor = itemView.findViewById(R.id.text_view_data_nascimento_professor);
        }
    }

    @NonNull
    @Override
    public ProfessorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_professor, parent, false);
        return new ProfessorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfessorViewHolder holder, int position) {
        Professor professor = professores.get(position);

        holder.textViewRaProfessor.setText(raPad(String.valueOf(professor.getRa())));
        holder.textViewNomeProfessor.setText(professor.getNome());
        holder.textViewCpfProfessor.setText(professor.getCpf());
        holder.textViewDataNascimentoProfessor.setText(professor.getDataNascimento());
    }

    @Override
    public int getItemCount() {
        return professores.size();
    }
}
