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
import com.maltauro.alunomobile.activities.CadastroDisciplinaActivity;
import com.maltauro.alunomobile.adapters.DisciplinaAdapter;
import com.maltauro.alunomobile.dao.DisciplinaDAO;
import com.maltauro.alunomobile.models.Disciplina;
import com.maltauro.alunomobile.utils.Util;

import java.util.List;

public class DisciplinaFragment extends Fragment {

    private Activity activity;
    private ConstraintLayout ctDisciplina;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = requireActivity();

        ctDisciplina = view.findViewById(R.id.ct_disciplina);

        FloatingActionButton fab_add_disciplina = view.findViewById(R.id.fab_add_disciplina);
        fab_add_disciplina.setOnClickListener(view1 -> addDisciplina());

        atualizaListaDisciplinas();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_disciplina, container, false);
    }

    private void addDisciplina() {
        Intent intent = new Intent(activity, CadastroDisciplinaActivity.class);
        startActivityForResult(intent, 1);
    }

    private void atualizaListaDisciplinas() {
        List<Disciplina> disciplinas = DisciplinaDAO.getListDisciplinas("", new String[]{}, "descricao asc");

        RecyclerView rv_lista_disciplina = activity.findViewById(R.id.rv_lista_disciplinas);
        rv_lista_disciplina.setLayoutManager(new LinearLayoutManager(activity));
        rv_lista_disciplina.setAdapter(new DisciplinaAdapter(disciplinas, activity));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            Util.showSnackBar(ctDisciplina, "Disciplina salvo com sucesso!");
            atualizaListaDisciplinas();
        }
    }
}
