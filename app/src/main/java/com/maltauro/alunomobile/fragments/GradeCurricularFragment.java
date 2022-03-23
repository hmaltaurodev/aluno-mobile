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
import com.maltauro.alunomobile.activities.CadastroGradeCurricularActivity;
import com.maltauro.alunomobile.adapters.GradeCurricularAdapter;
import com.maltauro.alunomobile.dao.GradeCurricularDAO;
import com.maltauro.alunomobile.models.GradeCurricular;
import com.maltauro.alunomobile.utils.Util;
import java.util.List;

public class GradeCurricularFragment extends Fragment {

    private Activity activity;
    private ConstraintLayout ctGradeCurricular;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = requireActivity();
        ctGradeCurricular = view.findViewById(R.id.ct_grade_curricular);

        FloatingActionButton fabAddGradeCurricular = view.findViewById(R.id.fab_add_grade_curricular);
        fabAddGradeCurricular.setOnClickListener(view1 -> addGradeCurricular());

        atualizaListaGradesCurriculares();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grade_curricular, container, false);
    }

    private void addGradeCurricular() {
        Intent intent = new Intent(activity, CadastroGradeCurricularActivity.class);
        startActivityForResult(intent, 1);
    }

    public void atualizaListaGradesCurriculares() {
        List<GradeCurricular> gradesCurriculares = GradeCurricularDAO.getListGradesCurriculares("", new String[]{}, "");

        RecyclerView rvListaGradesCurriculares = activity.findViewById(R.id.rv_lista_grades_curriculares);
        rvListaGradesCurriculares.setLayoutManager(new LinearLayoutManager(activity));
        rvListaGradesCurriculares.setAdapter(new GradeCurricularAdapter(gradesCurriculares, activity));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            Util.showSnackBar(ctGradeCurricular, "Grade Curricular salva com sucesso!");
            atualizaListaGradesCurriculares();
        }
    }
}