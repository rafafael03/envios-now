package com.prototipo.enviosnow.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.prototipo.enviosnow.R;
import com.prototipo.enviosnow.activity.TelaMinhaEncomendaActivity;
import com.prototipo.enviosnow.activity.ui.entregas.MinhasEntregasFragment;
import com.prototipo.enviosnow.config.ConfiguracaoFirebase;
import com.prototipo.enviosnow.helper.Base64Custom;
import com.prototipo.enviosnow.model.Encomenda;

import java.util.ArrayList;

public class MinhasEncomendasAdapter extends RecyclerView.Adapter<MinhasEncomendasAdapter.MyViewHolder> {

    private ArrayList<Encomenda> listaEncomendas;
    private Context context;
    private DatabaseReference firebase;
    private FirebaseAuth autenticacao;

    public MinhasEncomendasAdapter(ArrayList<Encomenda> listaEncomendas, Context context) {
        this.listaEncomendas = listaEncomendas;
        this.context = context;
    }

    @Override
    public MinhasEncomendasAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.encomenda_detalhe, parent, false);

        return new MinhasEncomendasAdapter.MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(MinhasEncomendasAdapter.MyViewHolder holder, int position) {

        Encomenda encomenda = listaEncomendas.get(position);
        holder.descricaoPorduto.setText( encomenda.getDescricao());
        holder.localEntrega.setText( "Entregar em: " + encomenda.getLocalEntrega());
        holder.localColeta.setText( "Coletar em: " + encomenda.getLocalColeta());

        holder.encomendaDetalhe.setOnClickListener(v -> {
            Intent intent = new Intent(context, TelaMinhaEncomendaActivity.class);
            intent.putExtra("encomenda",encomenda);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.getApplicationContext().startActivity(intent);

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
