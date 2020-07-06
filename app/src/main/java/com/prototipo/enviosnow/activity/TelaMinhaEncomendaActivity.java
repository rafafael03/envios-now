package com.prototipo.enviosnow.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.prototipo.enviosnow.R;
import com.prototipo.enviosnow.config.ConfiguracaoFirebase;
import com.prototipo.enviosnow.model.Encomenda;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TelaMinhaEncomendaActivity extends AppCompatActivity {

    private TextView textDescricao;
    private TextView textRemetente;
    private TextView textDestinatario;
    private TextView textContatoDestinatario;
    private Encomenda encomenda;
    private DatabaseReference firebase;
    private FirebaseAuth autenticacao;

    private Button botaoConfirmarEntrega;
    private Button botaoAbrirLocalColeta;
    private Button botaoAbrirLocalEntrega;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_minha_encomenda);

        textDescricao = findViewById(R.id.textDescricao);
        textRemetente = findViewById(R.id.textRemetente);
        textDestinatario = findViewById(R.id.textDestinatario);
        textContatoDestinatario = findViewById(R.id.textContatoDestinatario);
        botaoAbrirLocalColeta = findViewById(R.id.botaoAbrirLocalColeta);
        botaoAbrirLocalEntrega = findViewById(R.id.botaoAbrirLocalEntrega);

        botaoConfirmarEntrega = findViewById(R.id.botaoConfirmarEntrega);

        Bundle dados = getIntent().getExtras();
        encomenda = (Encomenda) dados.getSerializable("encomenda");

        textDescricao.setText("Descrição: " + encomenda.getDescricao());
        textRemetente.setText("Remetente: " + encomenda.getRemetente());
        textDestinatario.setText("Destinatario: " + encomenda.getDestinatario());
        textContatoDestinatario.setText("Tel destinatário: " + encomenda.getContatoDestinatario());

        botaoConfirmarEntrega.setOnClickListener(v -> {

            firebase = ConfiguracaoFirebase.getFirebase().child("encomendas").child(encomenda.getCodigoProduto()).child("dataConclusao");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            Calendar calendar = Calendar.getInstance();
            String date = dateFormat.format(calendar.getTime());

            firebase.setValue(date);
            finish();

        });

        botaoAbrirLocalEntrega.setOnClickListener(v -> {

            String urlMapa = "https://www.google.com/maps/dir/?api=1&travelmode=driving&destination=%s";
            String localEntregaUrlEncoded = URLEncoder.encode(encomenda.getLocalEntrega());
            urlMapa = String.format(urlMapa, localEntregaUrlEncoded);

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlMapa));
            startActivity(intent);

        });

        botaoAbrirLocalColeta.setOnClickListener(v -> {
            String urlMapa = "https://www.google.com/maps/dir/?api=1&travelmode=driving&destination=%s";
            String localColetaUrlEncoded = URLEncoder.encode(encomenda.getLocalColeta());
            urlMapa = String.format(urlMapa, localColetaUrlEncoded);

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlMapa));
            startActivity(intent);
        });




    }



}