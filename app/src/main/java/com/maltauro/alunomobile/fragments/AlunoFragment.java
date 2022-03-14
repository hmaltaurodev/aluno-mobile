package com.maltauro.alunomobile.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maltauro.alunomobile.R;
import com.maltauro.alunomobile.activities.CadastroAlunoActivity;
import com.maltauro.alunomobile.adapters.AlunoAdapter;
import com.maltauro.alunomobile.dao.AlunoDAO;
import com.maltauro.alunomobile.models.Aluno;
import com.maltauro.alunomobile.utils.Util;

import java.util.List;

public class AlunoFragment extends Fragment {

    private Activity activity;
    private ConstraintLayout ctAluno;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = requireActivity();

        ctAluno = view.findViewById(R.id.ct_aluno);

        FloatingActionButton fab_add_aluno = view.findViewById(R.id.fab_add_aluno);
        fab_add_aluno.setOnClickListener(view1 -> addAluno());

        atualizaListaAlunos();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_aluno, container, false);
    }

    private void addAluno() {
        Intent intent = new Intent(activity, CadastroAlunoActivity.class);
        startActivityForResult(intent, 1);
    }

    public void atualizaListaAlunos() {
        List<Aluno> alunos;
        alunos = AlunoDAO.getListAlunos("", new String[]{}, "nome asc");

        RecyclerView rv_lista_alunos = activity.findViewById(R.id.rv_lista_alunos);
        rv_lista_alunos.setLayoutManager(new LinearLayoutManager(activity));
        rv_lista_alunos.setAdapter(new AlunoAdapter(alunos, activity));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            Util.showSnackBar(ctAluno, "Aluno salvo com sucesso!");
            atualizaListaAlunos();
        }
    }
}
