package com.maltauro.alunomobile.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.maltauro.alunomobile.BuildConfig;
import com.maltauro.alunomobile.R;

public class SobreFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sobre, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView labelSobre = view.findViewById(R.id.label_sobre);
        Resources resources = getResources();

        labelSobre.setText(String.format(resources.getString(R.string.app_versao), BuildConfig.VERSION_NAME));
    }
}
