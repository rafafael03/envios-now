package com.prototipo.enviosnow.activity.ui.entregas;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.prototipo.enviosnow.R;
import com.prototipo.enviosnow.adapter.EncomendaAdapter;
import com.prototipo.enviosnow.adapter.MinhasEncomendasAdapter;
import com.prototipo.enviosnow.config.ConfiguracaoFirebase;
import com.prototipo.enviosnow.helper.Base64Custom;
import com.prototipo.enviosnow.model.Encomenda;

import java.util.ArrayList;


public class MinhasEntregasFragment extends Fragment {

    private RecyclerView recyclerViewListaEncomendas;
    private ArrayList<Encomenda> listaEncomendas;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerEncomendas;
    private DatabaseReference verificadorUsuario;
    private FirebaseAuth autenticacao;


    public MinhasEntregasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_minhas_entregas, container, false);

        //Instanciar objetos
        listaEncomendas = new ArrayList<>();

        recyclerViewListaEncomendas = view.findViewById(R.id.recyclerListaEncomendas);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewListaEncomendas.setLayoutManager(layoutManager);

        MinhasEncomendasAdapter adapter = new MinhasEncomendasAdapter(listaEncomendas,getActivity());

        recyclerViewListaEncomendas.setAdapter(adapter);

        //Recuperar encomendas
        firebase = ConfiguracaoFirebase.getFirebase().child("encomendas");

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        String recuprarIdUsuario = Base64Custom.codificarBase64(autenticacao.getCurrentUser().getEmail());
        //Listar para recuperar encomendas
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaEncomendas.clear();
                for (DataSnapshot dados: dataSnapshot.getChildren()){
                    Encomenda encomenda = dados.getValue(Encomenda.class);

                    if(encomenda.getIdEntregador().equals(recuprarIdUsuario) && encomenda.getDataConclusao().equals("")){
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


}