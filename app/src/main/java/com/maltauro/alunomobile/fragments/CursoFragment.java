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
import com.maltauro.alunomobile.activities.CadastroCursoActivity;
import com.maltauro.alunomobile.adapters.CursoAdapter;
import com.maltauro.alunomobile.dao.CursoDAO;
import com.maltauro.alunomobile.models.Curso;
import com.maltauro.alunomobile.utils.Util;
import java.util.List;

public class CursoFragment extends Fragment {

    private Activity activity;
    private ConstraintLayout ctCurso;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = requireActivity();
        ctCurso = view.findViewById(R.id.ct_curso);

        FloatingActionButton fabAddCurso = view.findViewById(R.id.fab_add_curso);
        fabAddCurso.setOnClickListener(view1 -> addCurso());

        atualizaListaCursos();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_curso, container, false);
    }

    private void addCurso() {
        Intent intent = new Intent(activity, CadastroCursoActivity.class);
        startActivityForResult(intent, 1);
    }

    private void atualizaListaCursos() {
        List<Curso> cursos = CursoDAO.getListCursos("", new String[]{}, "descricao asc");

        RecyclerView rvListaCursos = activity.findViewById(R.id.rv_lista_cursos);
        rvListaCursos.setLayoutManager(new LinearLayoutManager(activity));
        rvListaCursos.setAdapter(new CursoAdapter(cursos, activity));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            Util.showSnackBar(ctCurso, "Curso salvo com sucesso!");
            atualizaListaCursos();
        }
    }
}