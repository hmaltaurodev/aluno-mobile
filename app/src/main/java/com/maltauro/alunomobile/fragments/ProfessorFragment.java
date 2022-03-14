package com.maltauro.alunomobile.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maltauro.alunomobile.R;
import com.maltauro.alunomobile.activities.CadastroProfessorActivity;

public class ProfessorFragment extends Fragment {

    private Activity activity;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = requireActivity();

        FloatingActionButton fab_add_professor = view.findViewById(R.id.fab_add_professor);
        fab_add_professor.setOnClickListener(view1 -> addProfessor());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_professor, container, false);
    }

    private void addProfessor() {
        Intent intent = new Intent(activity, CadastroProfessorActivity.class);
        startActivity(intent);
    }
}
