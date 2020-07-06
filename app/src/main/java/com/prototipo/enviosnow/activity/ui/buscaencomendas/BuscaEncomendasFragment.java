package com.prototipo.enviosnow.activity.ui.buscaencomendas;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.prototipo.enviosnow.R;
import com.prototipo.enviosnow.adapter.EncomendaAdapter;
import com.prototipo.enviosnow.config.ConfiguracaoFirebase;
import com.prototipo.enviosnow.helper.Base64Custom;
import com.prototipo.enviosnow.helper.Mock;
import com.prototipo.enviosnow.model.Encomenda;

import java.util.ArrayList;


public class BuscaEncomendasFragment extends Fragment {

    private RecyclerView recyclerViewListaEncomendas;
    private ArrayList<Encomenda> listaEncomendas;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerEncomendas;

    private DatabaseReference verificadorUsuario;
    private FirebaseAuth autenticacao;


    public BuscaEncomendasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_busca_encomendas, container, false);

        //Instanciar objetos
        listaEncomendas = new ArrayList<>();

        recyclerViewListaEncomendas = view.findViewById(R.id.recyclerListaEncomendas);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewListaEncomendas.setLayoutManager(layoutManager);

        EncomendaAdapter adapter = new EncomendaAdapter(listaEncomendas, getActivity());

        recyclerViewListaEncomendas.setAdapter(adapter);

        //Recuperar encomendas
        firebase = ConfiguracaoFirebase.getFirebase().child("encomendas");

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        String recuprarIdUsuario = Base64Custom.codificarBase64(autenticacao.getCurrentUser().getEmail());
        String idEntregadorPadrao = "";
        //Listar para recuperar encomendas
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaEncomendas.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Encomenda encomenda = dados.getValue(Encomenda.class);

                    if (encomenda.getIdEntregador().equals(idEntregadorPadrao)) {
                        listaEncomendas.add(encomenda);
                    }


//                    listaEncomendas.stream().filter((item1) -> item1.getCodigoProduto().equals(encomenda.getCodigoProduto())).findFirst();
//                    boolean jaExiste = false;
//
//                    for(Encomenda item: listaEncomendas){
//                        if(item.getCodigoProduto().equals(encomenda.getCodigoProduto())){
//                            jaExiste = true;
//                        }
//                    }
//                    if(!jaExiste){
//                        listaEncomendas.add(encomenda);
//                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }

    private void abrirCadastroEncomenda() {

        final Encomenda encomenda = new Encomenda();
        final String codigoProduto = Base64Custom.codificarBase64(firebase.push().getKey());

        encomenda.setCodigoProduto(codigoProduto);
        Mock.criarEncomenda(encomenda);

        //Recuparar a instância Firebase
        firebase = ConfiguracaoFirebase.getFirebase().child("encomendas");
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                firebase.child(codigoProduto).setValue(encomenda);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }


}