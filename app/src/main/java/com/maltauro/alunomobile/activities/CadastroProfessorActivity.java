package com.maltauro.alunomobile.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.maltauro.alunomobile.R;
import com.maltauro.alunomobile.dao.ProfessorDAO;
import com.maltauro.alunomobile.models.Professor;
import com.maltauro.alunomobile.utils.Mask;
import com.maltauro.alunomobile.utils.Util;
import java.util.Calendar;
import java.util.Objects;

public class CadastroProfessorActivity extends AppCompatActivity {

    private ConstraintLayout ctCadastroProfessor;
    private TextInputEditText edtRa;
    private TextInputEditText edtNome;
    private TextInputEditText edtCpf;
    private TextInputEditText edtDataNascimento;
    private int dia;
    private int mes;
    private int ano;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_professor);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ctCadastroProfessor = findViewById(R.id.ct_cadastro_professor);

        edtRa = findViewById(R.id.edt_ra_professor);
        edtNome = findViewById(R.id.edt_nome_professor);
        edtCpf = findViewById(R.id.edt_cpf_professor);
        edtDataNascimento = findViewById(R.id.edt_data_nascimento_professor);

        edtCpf.addTextChangedListener(Mask.insert(edtCpf, Mask.maskCPF));

        FloatingActionButton fab_grava_professor = findViewById(R.id.fab_grava_professor);
        fab_grava_professor.setOnClickListener(view -> gravaProfessor());

        setDataAtual();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean validaCampos() {
        if (edtRa.getText().toString().equals("")) {
            edtRa.setError("Informe o RA do professor!");
            edtRa.requestFocus();
            return false;
        }

        if (edtNome.getText().toString().equals("")) {
            edtNome.setError("Informe o nome do professor!");
            edtNome.requestFocus();
            return false;
        }

        if (edtCpf.getText().toString().equals("")) {
            edtCpf.setError("Informe o CPF do professor!");
            edtCpf.requestFocus();
            return false;
        }

        if (edtDataNascimento.getText().toString().equals("")) {
            edtDataNascimento.setError("Informe a data do nascimento do professor!");
            return false;
        }

        return true;
    }

    private void gravaProfessor() {
        if (validaCampos()) {
            Professor professor = new Professor();

            professor.setRa(Integer.parseInt(edtRa.getText().toString()));
            professor.setNome(edtNome.getText().toString());
            professor.setCpf(edtCpf.getText().toString());
            professor.setDataNascimento(edtDataNascimento.getText().toString());

            if (ProfessorDAO.salvar(professor) > 0) {
                setResult(Activity.RESULT_OK);
                finish();
            }
            else
                Util.showSnackBar(ctCadastroProfessor, "Erro ao salvar o professor, verifique o log!");
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, setDatePicker, ano, mes, dia);
    }

    private final DatePickerDialog.OnDateSetListener setDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            edtDataNascimento.setText(new StringBuilder().append(day).append("/")
                    .append(month + 1).append("/")
                    .append(year));
        }
    };

    public void selecionarData(View view) {
        setDataAtual();
        showDialog(0);
    }

    private void setDataAtual() {
        final Calendar calendar = Calendar.getInstance();
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        mes = calendar.get(Calendar.MONTH);
        ano = calendar.get(Calendar.YEAR);
    }
}
