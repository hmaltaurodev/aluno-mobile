package com.maltauro.alunomobile.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.maltauro.alunomobile.R;
import com.maltauro.alunomobile.adapters.DisciplinaAdapter;
import com.maltauro.alunomobile.adapters.TurmaDisciplinaAdapter;
import com.maltauro.alunomobile.dao.DisciplinaDAO;
import com.maltauro.alunomobile.models.Disciplina;
import com.maltauro.alunomobile.models.Turma;
import com.maltauro.alunomobile.utils.RecyclerItemClickListener;

import java.util.List;
import java.util.Objects;

public class ListaTurmaDisciplinasActivity extends AppCompatActivity {

    private RecyclerView rvListaTurmaDisciplinas;
    private List<Disciplina> disciplinas;
    private long idTurma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_turma_disciplinas);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Activity activity = this;

        Intent intent = getIntent();
        idTurma = intent.getLongExtra("idTurma", 0);

        rvListaTurmaDisciplinas = findViewById(R.id.rv_lista_turma_disciplinas);
        rvListaTurmaDisciplinas.addOnItemTouchListener(new RecyclerItemClickListener(this, rvListaTurmaDisciplinas, new RecyclerItemClickListener.OnItemClickListener() {
            @Override public void onItemClick(View view, int position) {
                Intent intent = new Intent(activity, ListaTurmaAlunoActivity.class);
                intent.putExtra("idTurma", idTurma);
                intent.putExtra("idDisciplina", disciplinas.get(position).getId());
                startActivity(intent);
            }

            @Override public void onLongItemClick(View view, int position) { }
        }));

        atualizaListaDisciplinas();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void atualizaListaDisciplinas() {
        disciplinas = DisciplinaDAO.getListDisciplinasGradeCurricular(String.valueOf(idTurma));
        rvListaTurmaDisciplinas.setLayoutManager(new LinearLayoutManager(this));
        rvListaTurmaDisciplinas.setAdapter(new TurmaDisciplinaAdapter(disciplinas, idTurma, this));
    }
}