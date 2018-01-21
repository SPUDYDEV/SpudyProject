package com.example.spudydev.spudy;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.spudydev.spudy.entidades.Turma;
import com.example.spudydev.spudy.infraestrutura.persistencia.AcessoFirebase;
import com.example.spudydev.spudy.infraestrutura.utils.MD5;

public class TurmaActivity extends AppCompatActivity {

    private EditText nomeTurma;
    private EditText cargaHorariaDiaria;

    private Turma turma = new Turma();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_criar_turma);

        nomeTurma = findViewById(R.id.edtNomeTurma);
        cargaHorariaDiaria = findViewById(R.id.edtCargaHorariaDiaria);

        //criando turma
        String codigoTurma = criarTurma(nomeTurma.getText().toString(), cargaHorariaDiaria.getText().toString());
        Toast.makeText(TurmaActivity.this, "Codigo: " + codigoTurma, Toast.LENGTH_SHORT).show();
    }

    private String criarTurma(String nomedaturma, String cargaHorariaDiaria){

        Turma turma = criarObjetoTurma(nomedaturma, cargaHorariaDiaria);

        String uid = AcessoFirebase.getFirebaseAutenticacao().getCurrentUser().getUid();
        String codigoTurma = MD5.hashCodigoTurma(uid, nomedaturma);

        //Salvando a turma na árvore turma
        AcessoFirebase.getFirebase().child("turma").child(codigoTurma).setValue(turma.toMapTurma());
        //Salvando a turma na árvore professor
        AcessoFirebase.getFirebase().child("professor").child("turmasMinistradas").child(codigoTurma).setValue("0");

        return codigoTurma;
    }

    @NonNull
    private static Turma criarObjetoTurma(String nomedaturma, String cargaHorariaDiaria) {
        Turma turma = new Turma();

        turma.setNome(nomedaturma);
        turma.setCargaHorariaDiaria(cargaHorariaDiaria);
        return turma;
    }
}
