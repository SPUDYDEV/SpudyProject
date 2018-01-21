package com.example.spudydev.spudy.usuario.aluno.gui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.spudydev.spudy.infraestrutura.gui.LoginActivity;
import com.example.spudydev.spudy.perfil.gui.MeuPerfilAlunoActivity;
import com.example.spudydev.spudy.perfil.negocio.DadosMenuLateral;
import com.example.spudydev.spudy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainAlunoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DadosMenuLateral dadosMenuDAO = new DadosMenuLateral();
    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_aluno);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.sp_navigation_drawer_open, R.string.sp_navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //Chamando a classe para setar nome e email do nav_header_main_aluno
        dadosMenuDAO.resgatarUsuario(navigationView, user);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_meu_perfil:
                        abrirTelaMeuPerfilAlunoActivity();
                        return true;
                    case R.id.nav_turmas:
                        //Activity de turmas
                        Toast.makeText(MainAlunoActivity.this, "Em construção", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_sair:
                        sair();
                        //sair
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_aluno, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_turmas) {
            // Handle the camera action
        } else if (id == R.id.nav_meu_perfil) {

        } else if (id == R.id.nav_sair) {
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void sair() {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Sair");
        //define a mensagem
        builder.setMessage("Tem certeza que deseja sair?");
        //define um botão como positivo
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface arg0, int arg1) {
                FirebaseAuth.getInstance().signOut();
                abrirTelaLoginActivity();
            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                alerta.dismiss();
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }
    public void abrirTelaLoginActivity(){
        Intent intentAbrirTelaLoginAcitivty = new Intent(MainAlunoActivity.this, LoginActivity.class);
        startActivity(intentAbrirTelaLoginAcitivty);
        finish();
    }
    public void abrirTelaMeuPerfilAlunoActivity(){
        Intent intentAbrirTelaMeuPerfilAlunoAcitivty = new Intent(MainAlunoActivity.this, MeuPerfilAlunoActivity.class);
        startActivity(intentAbrirTelaMeuPerfilAlunoAcitivty);
    }
}
