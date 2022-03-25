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
import com.maltauro.alunomobile.activities.CadastroTurmaActivity;
import com.maltauro.alunomobile.activities.DetalheTurmaActivity;
import com.maltauro.alunomobile.adapters.TurmaAdapter;
import com.maltauro.alunomobile.dao.TurmaDAO;
import com.maltauro.alunomobile.models.Turma;
import com.maltauro.alunomobile.utils.RecyclerItemClickListener;
import com.maltauro.alunomobile.utils.Util;
import java.util.List;

public class TurmaFragment extends Fragment {

    private Activity activity;
    private ConstraintLayout ctTurma;
    private RecyclerView rvListaTurmas;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = requireActivity();
        ctTurma = view.findViewById(R.id.ct_turma);
        rvListaTurmas = activity.findViewById(R.id.rv_lista_turmas);

        FloatingActionButton fabAddTurma = view.findViewById(R.id.fab_add_turma);
        fabAddTurma.setOnClickListener(view1 -> addTurma());

        atualizaListaTurmas();

        rvListaTurmas.addOnItemTouchListener(new RecyclerItemClickListener(activity, rvListaTurmas, new RecyclerItemClickListener.OnItemClickListener() {
                @Override public void onItemClick(View view, int position) {
                    Intent intent = new Intent(activity, DetalheTurmaActivity.class);
                    startActivity(intent);
                }

                @Override public void onLongItemClick(View view, int position) { }
            })
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_turma, container, false);
    }

    private void addTurma() {
        Intent intent = new Intent(activity, CadastroTurmaActivity.class);
        startActivityForResult(intent, 1);
    }

    private void atualizaListaTurmas() {
        List<Turma> turmas = TurmaDAO.getListTurmas("", new String[]{}, "ano_periodo desc");
        rvListaTurmas.setLayoutManager(new LinearLayoutManager(activity));
        rvListaTurmas.setAdapter(new TurmaAdapter(turmas, activity));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            Util.showSnackBar(ctTurma, "Turma salva com sucesso!");
            atualizaListaTurmas();
        }
    }
}
