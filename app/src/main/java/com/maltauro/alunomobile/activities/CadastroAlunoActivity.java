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
import com.maltauro.alunomobile.dao.AlunoDAO;
import com.maltauro.alunomobile.models.Aluno;
import com.maltauro.alunomobile.utils.Mask;
import com.maltauro.alunomobile.utils.Util;
import java.util.Calendar;
import java.util.Objects;

public class CadastroAlunoActivity extends AppCompatActivity {

    private ConstraintLayout ctCadastroAluno;
    private TextInputEditText edtRa;
    private TextInputEditText edtNome;
    private TextInputEditText edtCpf;
    private TextInputEditText edtDataNascimento;
    private TextInputEditText edtDataMatricula;
    private View dataSelecionada;
    private int dia;
    private int mes;
    private int ano;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_aluno);
        ctCadastroAluno = findViewById(R.id.ct_cadastro_aluno);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        edtRa = findViewById(R.id.edt_ra_aluno);
        edtNome = findViewById(R.id.edt_nome_aluno);
        edtCpf = findViewById(R.id.edt_cpf_aluno);
        edtDataNascimento = findViewById(R.id.edt_data_nascimento_aluno);
        edtDataMatricula = findViewById(R.id.edt_data_matricula_aluno);

        edtCpf.addTextChangedListener(Mask.insert(edtCpf, Mask.maskCPF));

        FloatingActionButton fab_grava_aluno = findViewById(R.id.fab_grava_aluno);
        fab_grava_aluno.setOnClickListener(view -> gravaAluno());

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
            edtRa.setError("Informe o RA do aluno!");
            edtRa.requestFocus();
            return false;
        }

        if (edtNome.getText().toString().equals("")) {
            edtNome.setError("Informe o nome do aluno!");
            edtNome.requestFocus();
            return false;
        }

        if (edtCpf.getText().toString().equals("")) {
            edtCpf.setError("Informe o CPF do aluno!");
            edtCpf.requestFocus();
            return false;
        }

        if (edtDataNascimento.getText().toString().equals("")) {
            edtDataNascimento.setError("Informe a data do nascimento do aluno!");
            return false;
        }

        if (edtDataMatricula.getText().toString().equals("")) {
            edtDataMatricula.setError("Informa a data da matricula do aluno!");
            return false;
        }

        return true;
    }

    private void gravaAluno() {
        if (validaCampos()) {
            Aluno aluno = new Aluno();

            aluno.setRa(Integer.parseInt(edtRa.getText().toString()));
            aluno.setNome(edtNome.getText().toString());
            aluno.setCpf(edtCpf.getText().toString());
            aluno.setDataNascimento(edtDataNascimento.getText().toString());
            aluno.setDataMatricula(edtDataMatricula.getText().toString());

            if (AlunoDAO.salvar(aluno) > 0) {
                setResult(Activity.RESULT_OK);
                finish();
            }
            else
                Util.showSnackBar(ctCadastroAluno, "Erro ao salvar o aluno, verifique o log!");
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, setDatePicker, ano, mes, dia);
    }

    private final DatePickerDialog.OnDateSetListener setDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            ano = year;
            mes = month;
            dia = day;

            atualizaData();
        }
    };

    public void selecionarData(View view) {
        dataSelecionada = view;
        setDataAtual();
        showDialog(0);
    }

    private void atualizaData() {
        TextInputEditText edit = (TextInputEditText)dataSelecionada;
        edit.setText(new StringBuilder().append(dia).append("/")
                .append(mes + 1).append("/")
                .append(ano));
    }

    private void setDataAtual() {
        final Calendar calendar = Calendar.getInstance();
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        mes = calendar.get(Calendar.MONTH);
        ano = calendar.get(Calendar.YEAR);
    }
}
