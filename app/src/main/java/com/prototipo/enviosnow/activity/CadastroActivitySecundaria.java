package com.prototipo.enviosnow.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
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

import org.w3c.dom.Text;

public class CadastroActivitySecundaria extends AppCompatActivity {
    private TextInputEditText editCpf;
    private TextInputEditText editRg;
    private TextInputEditText editVeiculo;
    private Button botaoFinalizarCadastro;
    private Usuario usuario;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_secundaria);

        editCpf = findViewById(R.id.editCpf);
        editRg = findViewById(R.id.editRg);
        editVeiculo = findViewById(R.id.editVeiculo);


        SimpleMaskFormatter mascaraCpf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtw = new MaskTextWatcher(editCpf, mascaraCpf);
        editCpf.addTextChangedListener(mtw);


        botaoFinalizarCadastro = findViewById(R.id.botaoFinalizarCadastro);

        Bundle dados = getIntent().getExtras();
        usuario = (Usuario) dados.getSerializable("usuario");

        botaoFinalizarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editCpf.getText().toString().length() < 14){
                    Toast.makeText(getApplicationContext(),"Por favor digite um CPF valido!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (editRg.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Por favor informe seu RG!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (editVeiculo.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Por favor informe o veículo utilizado para entregas!", Toast.LENGTH_SHORT).show();
                    return;
                }

                usuario.setCpf(editCpf.getText().toString());
                usuario.setRg(editRg.getText().toString());
                usuario.setVeiculo(editVeiculo.getText().toString());
                usuario.salvar();
                abrirLoginUsuario();
                Toast.makeText(CadastroActivitySecundaria.this, "Sucesso ao cadastrar usuário!",Toast.LENGTH_SHORT).show();

            }
        });

    }


    public void abrirLoginUsuario(){
        Intent intent = new Intent(CadastroActivitySecundaria.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}