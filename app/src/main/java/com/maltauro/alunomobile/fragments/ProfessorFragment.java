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
import com.maltauro.alunomobile.activities.CadastroProfessorActivity;
import com.maltauro.alunomobile.adapters.ProfessorAdapter;
import com.maltauro.alunomobile.dao.ProfessorDAO;
import com.maltauro.alunomobile.models.Professor;
import com.maltauro.alunomobile.utils.Util;

import java.util.List;

public class ProfessorFragment extends Fragment {

    private Activity activity;
    private ConstraintLayout ctProfessor;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = requireActivity();

        ctProfessor = view.findViewById(R.id.ct_professor);

        FloatingActionButton fab_add_professor = view.findViewById(R.id.fab_add_professor);
        fab_add_professor.setOnClickListener(view1 -> addProfessor());

        atualizaListaProfessores();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_professor, container, false);
    }

    private void addProfessor() {
        Intent intent = new Intent(activity, CadastroProfessorActivity.class);
        startActivityForResult(intent, 1);
    }

    public void atualizaListaProfessores() {
        List<Professor> professores;
        professores = ProfessorDAO.getListProfessores("", new String[]{}, "nome asc");

        RecyclerView rv_lista_professores = activity.findViewById(R.id.rv_lista_professores);
        rv_lista_professores.setLayoutManager(new LinearLayoutManager(activity));
        rv_lista_professores.setAdapter(new ProfessorAdapter(professores, activity));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            Util.showSnackBar(ctProfessor, "Professor salvo com sucesso!");
            atualizaListaProfessores();
        }
    }
}
