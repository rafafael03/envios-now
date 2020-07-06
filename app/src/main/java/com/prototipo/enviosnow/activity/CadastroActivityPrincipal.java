package com.prototipo.enviosnow.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.prototipo.enviosnow.R;
import com.prototipo.enviosnow.config.ConfiguracaoFirebase;
import com.prototipo.enviosnow.helper.Base64Custom;
import com.prototipo.enviosnow.helper.Preferencias;
import com.prototipo.enviosnow.model.Usuario;

public class CadastroActivityPrincipal extends AppCompatActivity{

    private TextInputEditText nome;
    private TextInputEditText email;
    private TextInputEditText senha;
    private Button botaoProximaTela;
    private Usuario usuario;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        nome = findViewById(R.id.editCadastroNome);
        email = findViewById(R.id.editCadastroEmail);
        senha = findViewById(R.id.editCadastroSenha);
        botaoProximaTela = findViewById(R.id.botaoProximo);

        //Função do botão proxima tela
        botaoProximaTela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = new Usuario();
                usuario.setNome(nome.getText().toString());
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());
                cadastrarUsuario();
            }
        });


    }

    private void cadastrarUsuario(){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(CadastroActivityPrincipal.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
//                    Toast.makeText(CadastroActivityPrincipal.this, "Sucesso ao cadastrar usuário!",Toast.LENGTH_SHORT).show();

                    FirebaseUser usuarioFirebase = task.getResult().getUser();

                    String identificadorUsuario = Base64Custom.codificarBase64(usuario.getEmail());

                    usuario.setId(identificadorUsuario);
                    usuario.salvar();

                    Preferencias preferencias = new Preferencias(CadastroActivityPrincipal.this);
                    preferencias.salvarDados(identificadorUsuario);
                    abrirSegundaEtapaLogin();

                }else {

                    String erroExcesssao = "";

                    try{
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e){
                        erroExcesssao = "Digite uma senha mais forte, contendo mais caracteres e com letras e números!";
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        erroExcesssao = "O e-mail digitado é inválido, digite um novo e-mail!";
                    } catch (FirebaseAuthUserCollisionException e){
                        erroExcesssao = "Esse e-mail já está em uso no App!";
                    } catch (Exception e) {
                        erroExcesssao = "Erro ao efetuar o cadastro!";
                        e.printStackTrace();
                    }

                    Toast.makeText(CadastroActivityPrincipal.this, "Erro: " + erroExcesssao,Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    public void abrirSegundaEtapaLogin(){
        Intent intent = new Intent(CadastroActivityPrincipal.this, CadastroActivitySecundaria.class);
        intent.putExtra("usuario",usuario);
        startActivity(intent);
        finish();
    }


}