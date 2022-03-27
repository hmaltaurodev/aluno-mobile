package com.maltauro.alunomobile.adapters;

import static com.maltauro.alunomobile.utils.Util.raPad;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.textview.MaterialTextView;
import com.maltauro.alunomobile.R;
import com.maltauro.alunomobile.dao.DisciplinaDAO;
import com.maltauro.alunomobile.dao.FrequenciaDAO;
import com.maltauro.alunomobile.dao.GradeCurricularDAO;
import com.maltauro.alunomobile.dao.NotaDAO;
import com.maltauro.alunomobile.dao.TurmaAlunoDAO;
import com.maltauro.alunomobile.enums.RegimeAcademico;
import com.maltauro.alunomobile.models.Aluno;
import com.maltauro.alunomobile.models.Nota;
import com.maltauro.alunomobile.models.TurmaAluno;
import java.util.List;

public class TurmaAlunoAdapter extends RecyclerView.Adapter<TurmaAlunoAdapter.TurmaAlunoViewHolder> {

    private final List<Aluno> alunos;
    private final long idTurma;
    private final long idDisciplina;
    private final Context context;

    public TurmaAlunoAdapter(List<Aluno> alunos, long idTurma, long idDisciplina, Context context) {
        this.alunos = alunos;
        this.idTurma = idTurma;
        this.idDisciplina = idDisciplina;
        this.context = context;
    }

    public static class TurmaAlunoViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView textViewNomeAluno;
        MaterialTextView textViewRaAluno;
        MaterialTextView textViewFrequencia;
        MaterialTextView textViewPrimeiroBimestre;
        MaterialTextView textViewSegundoBimestre;
        MaterialTextView textViewTerceiroBimestre;
        MaterialTextView textViewQuartoBimestre;
        MaterialTextView textViewMedia;
        MaterialTextView textViewStatus;
        LinearLayout lnTerceiroBimestre;
        LinearLayout lnQuartoBimestre;

        public TurmaAlunoViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNomeAluno = itemView.findViewById(R.id.text_view_nome_aluno);
            textViewRaAluno = itemView.findViewById(R.id.text_view_ra_aluno);
            textViewFrequencia = itemView.findViewById(R.id.text_view_frequencia);
            textViewPrimeiroBimestre = itemView.findViewById(R.id.text_view_primeiro_bimestre);
            textViewSegundoBimestre = itemView.findViewById(R.id.text_view_segundo_bimestre);
            textViewTerceiroBimestre = itemView.findViewById(R.id.text_view_terceiro_bimestre);
            textViewQuartoBimestre = itemView.findViewById(R.id.text_view_quarto_bimestre);
            textViewMedia = itemView.findViewById(R.id.text_view_media);
            textViewStatus = itemView.findViewById(R.id.text_view_status);
            lnTerceiroBimestre = itemView.findViewById(R.id.ln_terceiro_bimestre);
            lnQuartoBimestre = itemView.findViewById(R.id.ln_quarto_bimestre);
        }
    }

    @NonNull
    @Override
    public TurmaAlunoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_turma_aluno, parent, false);
        return new TurmaAlunoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TurmaAlunoViewHolder holder, int position) {
        Aluno aluno = alunos.get(position);
        TurmaAluno turmaAluno = TurmaAlunoDAO.getListTurmaAlunos("TURMA = ? AND ALUNO = ?", new String[]{ String.valueOf(idTurma), String.valueOf(aluno.getId()) }, "").get(0);

        int frequencias = FrequenciaDAO.getFrequenciaTurmaDisciplinaAluno(turmaAluno.getId(), idDisciplina);
        int aulasMinistradas = FrequenciaDAO.getAulasMinistradasTurmaDisciplina(idTurma, idDisciplina);
        int quantidadeAulas = DisciplinaDAO.getDisciplina(idDisciplina).getQuantidadeAulas();
        int porcentagemFrequencia = aulasMinistradas == 0 ? 0 : (frequencias * 100) / aulasMinistradas;

        List<Nota> notas = NotaDAO.getNotasAlunoDisciplina(turmaAluno.getId(), idDisciplina);
        double notaPrimeiroBimestre = (notas.size() >= 1) ? notas.get(0).getNota() : 0d;
        double notaSegundoBimestre = (notas.size() >= 2) ? notas.get(1).getNota() : 0d;
        double somaNotas = notaPrimeiroBimestre + notaSegundoBimestre;
        double media;
        boolean emAndamento;

        holder.textViewNomeAluno.setText(aluno.getNome());
        holder.textViewRaAluno.setText(raPad(String.valueOf(aluno.getRa())));
        holder.textViewFrequencia.setText(String.format("%s%%", porcentagemFrequencia));
        holder.textViewPrimeiroBimestre.setText(String.valueOf(notaPrimeiroBimestre));
        holder.textViewSegundoBimestre.setText(String.valueOf(notaSegundoBimestre));

        RegimeAcademico regimeAcademico = GradeCurricularDAO.getGradeCurricularTurma(idTurma).getRegimeAcademico();
        if (regimeAcademico.equals(RegimeAcademico.SEMESTRAL)) {
            holder.lnTerceiroBimestre.setVisibility(View.GONE);
            holder.lnQuartoBimestre.setVisibility(View.GONE);
            media = somaNotas / 2;
            emAndamento = !(notas.size() == 2);
        }
        else {
            double notaTerceiroBimestre = (notas.size() >= 3) ? notas.get(2).getNota() : 0d;
            double notaQuartoBimestre = (notas.size() >= 4) ? notas.get(3).getNota() : 0d;
            somaNotas += notaTerceiroBimestre + notaQuartoBimestre;
            media = somaNotas / 4;
            emAndamento = !(notas.size() == 4);

            holder.textViewTerceiroBimestre.setText(String.valueOf(notaTerceiroBimestre));
            holder.textViewQuartoBimestre.setText(String.valueOf(notaQuartoBimestre));
        }

        holder.textViewMedia.setText(String.valueOf(media));

        if (quantidadeAulas != aulasMinistradas || emAndamento) {
            holder.textViewStatus.setText("EM ANDAMENTO");
            holder.textViewStatus.setTextColor(Color.parseColor("#616161"));
        }
        else {
            if (porcentagemFrequencia < 70 && media < 60) {
                holder.textViewStatus.setText("REPROVADO POR NOTA E FALTAS");
                holder.textViewStatus.setTextColor(Color.parseColor("#FF0000"));
            }
            else if (porcentagemFrequencia < 70) {
                holder.textViewStatus.setText("REPROVADO POR FALTAS");
                holder.textViewStatus.setTextColor(Color.parseColor("#FF0000"));
            }
            else if (media < 60) {
                holder.textViewStatus.setText("REPROVADO POR NOTA");
                holder.textViewStatus.setTextColor(Color.parseColor("#FF0000"));
            }
            else {
                holder.textViewStatus.setText("APROVADO");
                holder.textViewStatus.setTextColor(Color.parseColor("#008000"));
            }
        }
    }

    @Override
    public int getItemCount() {
        return alunos.size();
    }
}
