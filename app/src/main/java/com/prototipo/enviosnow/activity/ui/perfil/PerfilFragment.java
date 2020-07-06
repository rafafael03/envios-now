package com.prototipo.enviosnow.activity.ui.perfil;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.prototipo.enviosnow.R;
import com.prototipo.enviosnow.config.ConfiguracaoFirebase;
import com.prototipo.enviosnow.helper.Base64Custom;
import com.prototipo.enviosnow.model.Usuario;

public class PerfilFragment extends Fragment {

    private TextView textNome;
    private TextView textEmail;
    private TextView textVeiculo;
    private TextView textRg;
    private TextView textCpf;
    private Usuario usuario;

    private FirebaseAuth autenticacao;
    private DatabaseReference firebase;

    public PerfilFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        textNome = view.findViewById(R.id.textNome);
        textEmail = view.findViewById(R.id.textEmail);
        textVeiculo = view.findViewById(R.id.textVeiculo);
        textCpf = view.findViewById(R.id.textCpf);
        textRg = view.findViewById(R.id.textRG);


        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        String recuperarIdUsuario = Base64Custom.codificarBase64(autenticacao.getCurrentUser().getEmail());

        usuario = new Usuario();
        firebase = ConfiguracaoFirebase.getFirebase().child("usuarios").child(recuperarIdUsuario);
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nome = dataSnapshot.child("nome").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                String veiculo = dataSnapshot.child("veiculo").getValue().toString();
                String rg = dataSnapshot.child("rg").getValue().toString();
                String cpf = dataSnapshot.child("cpf").getValue().toString();

                textRg.setText("RG: " + rg);
                textNome.setText("Nome: " + nome);
                textEmail.setText("E-mail: " + email);
                textVeiculo.setText("Veículo: " + veiculo);
                textCpf.setText("CPF: " + cpf);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }
}