package com.prototipo.enviosnow.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.prototipo.enviosnow.R;
import com.prototipo.enviosnow.config.ConfiguracaoFirebase;
import com.prototipo.enviosnow.helper.Base64Custom;
import com.prototipo.enviosnow.helper.Preferencias;
import com.prototipo.enviosnow.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText email;
    private TextInputEditText senha;
    private Button botaoLogar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;
    private TextView textoCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        verificarUsuarioLogado();

        email = findViewById(R.id.editEmail);
        senha = findViewById(R.id.editSenha);
        botaoLogar = findViewById(R.id.botaoLogar);
        textoCadastro = findViewById(R.id.textViewCadastro);

        //Botao para abrir a tela de cadastro
        textoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirCadastroUsuario(view);
            }
        });

        //Botao para logar no app
        botaoLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = new Usuario();
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());
                validarLogin();
            }
        });

    }

    private void verificarUsuarioLogado(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if(autenticacao.getCurrentUser() != null){
            abrirTelaPrincipal();
        }
    }

    private void validarLogin(){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Preferencias preferencias = new Preferencias(LoginActivity.this);
                    String identificadorUsuarioLogado = Base64Custom.codificarBase64(usuario.getEmail().toString());
                    preferencias.salvarDados(identificadorUsuarioLogado);

                    abrirTelaPrincipal();
                    Toast.makeText(LoginActivity.this, "Sucesso ao fazer login!",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this, "ERRO ao fazer login!",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }


    private void abrirTelaPrincipal(){
//        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        Intent intent = new Intent(LoginActivity.this, MainActivity2.class);
        startActivity(intent);
        finish();
    }


    public void abrirCadastroUsuario(View view){
        Intent intent = new Intent (LoginActivity.this, CadastroActivityPrincipal.class);
        startActivity(intent);
    }


}