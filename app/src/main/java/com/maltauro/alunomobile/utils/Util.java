package com.maltauro.alunomobile.utils;

import android.view.View;
import com.google.android.material.snackbar.Snackbar;

public class Util {

    public static void showSnackBar(View view, String mensagem) {
        Snackbar snackbar = Snackbar.make(view, mensagem, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}