package com.maltauro.alunomobile.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public abstract class Mask {

    public static final String maskCPF = "###.###.###-##";

    public static String unMask(String s) {
        return s.replaceAll("[^0-9]*", "");
    }

    public static TextWatcher insert(final EditText editText, final String maskPattern) {
        return new TextWatcher() {
            boolean isUpdating;
            String old = "";

            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String str = Mask.unMask(charSequence.toString());
                String mask = "";

                if (isUpdating) {
                    old = str;
                    isUpdating = false;
                    return;
                }

                int i = 0;
                for (char m : maskPattern.toCharArray()) {
                    if ((m != '#' && str.length() > old.length()) || (m != '#' && str.length() < old.length() && str.length() != i)) {
                        mask += m;
                        continue;
                    }

                    try {
                        mask += str.charAt(i);
                    }
                    catch (Exception ex) {
                        break;
                    }

                    i++;
                }

                isUpdating = true;
                editText.setText(mask);
                editText.setSelection(mask.length());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }
}