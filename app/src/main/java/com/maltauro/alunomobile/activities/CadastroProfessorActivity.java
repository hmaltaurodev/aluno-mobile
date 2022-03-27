package com.maltauro.alunomobile.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
    private TextInputEditText edtRaProfessor;
    private TextInputEditText edtNomeProfessor;
    private TextInputEditText edtCpfProfessor;
    private TextInputEditText edtDataNascimentoProfessor;
    private int dia;
    private int mes;
    private int ano;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_professor);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ctCadastroProfessor = findViewById(R.id.ct_cadastro_professor);

        edtRaProfessor = findViewById(R.id.edt_ra_professor);
        edtNomeProfessor = findViewById(R.id.edt_nome_professor);
        edtCpfProfessor = findViewById(R.id.edt_cpf_professor);
        edtDataNascimentoProfessor = findViewById(R.id.edt_data_nascimento_professor);
        edtCpfProfessor.addTextChangedListener(Mask.insert(edtCpfProfessor, Mask.maskCPF));

        FloatingActionButton fabGravaProfessor = findViewById(R.id.fab_grava_professor);
        FloatingActionButton fabCancelaProfessor = findViewById(R.id.fab_cancela_professor);
        FloatingActionButton fabLimpaProfessor = findViewById(R.id.fab_limpa_professor);

        fabGravaProfessor.setOnClickListener(view -> gravaProfessor());
        fabCancelaProfessor.setOnClickListener(view -> finish());
        fabLimpaProfessor.setOnClickListener(view -> limpaCampos());

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
        if (edtRaProfessor.getText().toString().equals("")) {
            edtRaProfessor.setError("Informe o RA do professor!");
            edtRaProfessor.requestFocus();
            return false;
        }

        if (edtNomeProfessor.getText().toString().equals("")) {
            edtNomeProfessor.setError("Informe o nome do professor!");
            edtNomeProfessor.requestFocus();
            return false;
        }

        if (edtCpfProfessor.getText().toString().equals("")) {
            edtCpfProfessor.setError("Informe o CPF do professor!");
            edtCpfProfessor.requestFocus();
            return false;
        }

        if (edtDataNascimentoProfessor.getText().toString().equals("")) {
            edtDataNascimentoProfessor.setError("Informe a data do nascimento do professor!");
            return false;
        }

        return true;
    }

    private void gravaProfessor() {
        if (validaCampos()) {
            Professor professor = new Professor();

            professor.setRa(Integer.parseInt(edtRaProfessor.getText().toString()));
            professor.setNome(edtNomeProfessor.getText().toString());
            professor.setCpf(edtCpfProfessor.getText().toString());
            professor.setDataNascimento(edtDataNascimentoProfessor.getText().toString());

            if (ProfessorDAO.salvar(professor) > 0) {
                setResult(Activity.RESULT_OK);
                finish();
            }
            else
                Util.showSnackBar(ctCadastroProfessor, "Erro ao salvar o professor, verifique o log!");
        }
    }

    private void limpaCampos() {
        edtRaProfessor.setText("");
        edtNomeProfessor.setText("");
        edtCpfProfessor.setText("");
        edtDataNascimentoProfessor.setText("");

        InputMethodManager manager = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(ctCadastroProfessor.getWindowToken(), 0);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, setDatePicker, ano, mes, dia);
    }

    private final DatePickerDialog.OnDateSetListener setDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            edtDataNascimentoProfessor.setText(new StringBuilder().append(day).append("/")
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
