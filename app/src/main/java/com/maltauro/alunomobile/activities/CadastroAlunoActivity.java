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

    private TextInputEditText edtRaAluno;
    private TextInputEditText edtNomeAluno;
    private TextInputEditText edtCpfAluno;
    private TextInputEditText edtDataNascimentoAluno;
    private TextInputEditText edtDataMatriculaAluno;
    private ConstraintLayout ctCadastroAluno;

    private int dia;
    private int mes;
    private int ano;
    private View dataSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_aluno);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        edtRaAluno = findViewById(R.id.edt_ra_aluno);
        edtNomeAluno = findViewById(R.id.edt_nome_aluno);
        edtCpfAluno = findViewById(R.id.edt_cpf_aluno);
        edtDataNascimentoAluno = findViewById(R.id.edt_data_nascimento_aluno);
        edtDataMatriculaAluno = findViewById(R.id.edt_data_matricula_aluno);
        ctCadastroAluno = findViewById(R.id.ct_cadastro_aluno);

        edtCpfAluno.addTextChangedListener(Mask.insert(edtCpfAluno, Mask.maskCPF));

        FloatingActionButton fab_grava_aluno = findViewById(R.id.fab_grava_aluno);
        fab_grava_aluno.setOnClickListener(view -> gravaAluno());

        setDataAtual();
    }

    private boolean validaCampos() {
        if (edtRaAluno.getText().toString().equals("")) {
            edtRaAluno.setError("Informe o RA do aluno!");
            edtRaAluno.requestFocus();
            return false;
        }

        if (edtNomeAluno.getText().toString().equals("")) {
            edtNomeAluno.setError("Informe o nome do aluno!");
            edtNomeAluno.requestFocus();
            return false;
        }

        if (edtCpfAluno.getText().toString().equals("")) {
            edtCpfAluno.setError("Informe o CPF do aluno!");
            edtCpfAluno.requestFocus();
            return false;
        }

        if (edtDataNascimentoAluno.getText().toString().equals("")) {
            edtDataNascimentoAluno.setError("Informe a data do nascimento do aluno!");
            return false;
        }

        if (edtDataMatriculaAluno.getText().toString().equals("")) {
            edtDataMatriculaAluno.setError("Informa a data da matricula do aluno!");
            return false;
        }

        return true;
    }

    private void gravaAluno() {
        if (validaCampos()) {
            Aluno aluno = new Aluno();

            aluno.setRa(Integer.parseInt(edtRaAluno.getText().toString()));
            aluno.setNome(edtNomeAluno.getText().toString());
            aluno.setCpf(edtCpfAluno.getText().toString());
            aluno.setDataNascimento(edtDataNascimentoAluno.getText().toString());
            aluno.setDataMatricula(edtDataMatriculaAluno.getText().toString());

            if (AlunoDAO.salvar(aluno) > 0) {
                setResult(Activity.RESULT_OK);
                finish();
            }
            else
                Util.showSnackBar(ctCadastroAluno, "Erro ao salvar o aluno, verifique o log!");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void selecionarData(View view) {
        dataSelecionada = view;
        setDataAtual();
        showDialog(0);
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

    private void atualizaData() {
        TextInputEditText edit = (TextInputEditText)dataSelecionada;
        edit.setText(new StringBuilder().append(dia).append("/")
                .append(mes + 1).append("/")
                .append(ano));
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, setDatePicker, ano, mes, dia);
    }

    private void setDataAtual() {
        final Calendar calendar = Calendar.getInstance();
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        mes = calendar.get(Calendar.MONTH);
        ano = calendar.get(Calendar.YEAR);
    }
}
