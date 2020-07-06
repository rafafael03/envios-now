package com.prototipo.enviosnow.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prototipo.enviosnow.R;
import com.prototipo.enviosnow.config.ConfiguracaoFirebase;
import com.prototipo.enviosnow.helper.Base64Custom;
import com.prototipo.enviosnow.model.Encomenda;

import java.util.ArrayList;

public class EncomendaAdapter extends RecyclerView.Adapter<EncomendaAdapter.MyViewHolder> {

    private ArrayList<Encomenda> listaEncomendas;
    private Context context;
    private DatabaseReference firebase;
    private FirebaseAuth autenticacao;

    public EncomendaAdapter(ArrayList<Encomenda> listaEncomendas, Context context) {
        this.listaEncomendas = listaEncomendas;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.encomenda_detalhe, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {

        Encomenda encomenda = listaEncomendas.get(position);
        holder.descricaoPorduto.setText( encomenda.getDescricao());
        holder.localEntrega.setText( "Entregar em: " + encomenda.getLocalEntrega());
        holder.localColeta.setText( "Coletar em: " + encomenda.getLocalColeta());

        holder.encomendaDetalhe.setOnClickListener(v -> {
            //Configurando o alert dialog
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

            alertDialog.setTitle("Deseja aceitar esta entrega?");
            alertDialog.setMessage("Por favor confirme sua decisão");
            alertDialog.setCancelable(false);

            firebase = ConfiguracaoFirebase.getFirebase().child("encomendas");

            alertDialog.setPositiveButton("Aceitar", (dialog, which) -> {
                autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
                String recuperaIdUsuario = Base64Custom.codificarBase64(autenticacao.getCurrentUser().getEmail());


                firebase.child(encomenda.getCodigoProduto()).child("idEntregador").setValue(recuperaIdUsuario);

            });

            alertDialog.setNegativeButton("Cancelar", (dialog, which) -> {

            });

            alertDialog.create();
            alertDialog.show();

        });

    }

    @Override
    public int getItemCount() {
        return this.listaEncomendas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView descricaoPorduto;
        TextView localEntrega;
        TextView localColeta;
        CardView encomendaDetalhe;

        public MyViewHolder(View itemView) {
            super(itemView);

            encomendaDetalhe = itemView.findViewById(R.id.encomendaDetalhe);
            descricaoPorduto = itemView.findViewById(R.id.textDescricao);
            localEntrega = itemView.findViewById(R.id.textLocalEntrega);
            localColeta = itemView.findViewById(R.id.textLocalColeta);


        }
    }




}