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
import com.maltauro.alunomobile.activities.CadastroTurmaActivity;

public class TurmaFragment extends Fragment {

    private Activity activity;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = requireActivity();

        FloatingActionButton fab_add_turma = view.findViewById(R.id.fab_add_turma);
        fab_add_turma.setOnClickListener(view1 -> addTurma());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_turma, container, false);
    }

    private void addTurma() {
        Intent intent = new Intent(activity, CadastroTurmaActivity.class);
        startActivity(intent);
    }
}