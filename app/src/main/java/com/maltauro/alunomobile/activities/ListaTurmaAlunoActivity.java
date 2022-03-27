package com.maltauro.alunomobile.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.maltauro.alunomobile.R;
import com.maltauro.alunomobile.adapters.TurmaAlunoAdapter;
import com.maltauro.alunomobile.dao.AlunoDAO;
import com.maltauro.alunomobile.models.Aluno;
import java.util.List;
import java.util.Objects;

public class ListaTurmaAlunoActivity extends AppCompatActivity {

    private long idTurma;
    private long idDisciplina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_turma_aluno);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        idTurma = intent.getLongExtra("idTurma", 0);
        idDisciplina = intent.getLongExtra("idDisciplina", 0);

        atualizaListaAlunos();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void atualizaListaAlunos() {
        List<Aluno> alunos = AlunoDAO.getListAlunosTurma(idTurma);

        RecyclerView rvListaTurmaAlunos = findViewById(R.id.rv_lista_turma_alunos);
        rvListaTurmaAlunos.setLayoutManager(new LinearLayoutManager(this));
        rvListaTurmaAlunos.setAdapter(new TurmaAlunoAdapter(alunos, idTurma, idDisciplina, this));
    }
}