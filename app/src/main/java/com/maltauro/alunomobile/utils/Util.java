package com.maltauro.alunomobile.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import com.google.android.material.snackbar.Snackbar;

public class Util {

    public static void showSnackBar(View view, String mensagem) {
        Snackbar snackbar = Snackbar.make(view, mensagem, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static String raPad(String string) {
        return String.format("%8s", string).replace(' ', '0');
    }

    public static void mensagemDialog(String titulo, String msg, Context context) {
        AlertDialog alerta;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titulo);
        builder.setMessage(msg);
        builder.setPositiveButton("Ok", (dialog, which) -> { });

        alerta = builder.create();
        alerta.show();
    }
}
