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
import com.maltauro.alunomobile.models.Aluno;
import java.util.List;

public class AlunoAdapter extends RecyclerView.Adapter<AlunoAdapter.AlunoViewHolder> {

    private final List<Aluno> alunos;
    private final Context context;

    public AlunoAdapter(List<Aluno> alunos, Context context) {
        this.alunos = alunos;
        this.context = context;
    }

    public static class AlunoViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView textViewRaAluno;
        MaterialTextView textViewNomeAluno;
        MaterialTextView textViewCpfAluno;
        MaterialTextView textViewDataNascimentoAluno;
        MaterialTextView textViewDataMatriculaAluno;

        public AlunoViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewRaAluno = itemView.findViewById(R.id.text_view_ra_aluno);
            textViewNomeAluno = itemView.findViewById(R.id.text_view_nome_aluno);
            textViewCpfAluno = itemView.findViewById(R.id.text_view_cpf_aluno);
            textViewDataNascimentoAluno = itemView.findViewById(R.id.text_view_data_nascimento_aluno);
            textViewDataMatriculaAluno = itemView.findViewById(R.id.text_view_data_matricula_aluno);
        }
    }

    @NonNull
    @Override
    public AlunoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_aluno, parent, false);
        return new AlunoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlunoViewHolder holder, int position) {
        Aluno aluno = alunos.get(position);

        holder.textViewRaAluno.setText(raPad(String.valueOf(aluno.getRa())));
        holder.textViewNomeAluno.setText(aluno.getNome());
        holder.textViewCpfAluno.setText(aluno.getCpf());
        holder.textViewDataNascimentoAluno.setText(aluno.getDataNascimento());
        holder.textViewDataMatriculaAluno.setText(aluno.getDataMatricula());
    }

    @Override
    public int getItemCount() {
        return alunos.size();
    }
}
