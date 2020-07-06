package com.prototipo.enviosnow.activity.ui.info;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prototipo.enviosnow.R;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;


public class InfoFragment extends Fragment {

    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        String descricao = "Aplicativo do Mercado Now, desenvolvido como um protótipo para o desafio do Mercado livre do MegaHack 3.0";

        Element versao = new Element();
        versao.setTitle("Versao 1.0");

        return new AboutPage(getActivity())
                .setImage(R.drawable.logotipo_amarelo)
                .setDescription(descricao)

                .addGroup("Entre em contato")
                .addEmail("bataleiteemneve@gmail.com", "Envie um e-mail")
                .addWebsite("https://www.mercadolivre.com.br/","Acesse o site do Mercado Livre")

                .addGroup("Redes Sociais")
                .addFacebook("victor.m.borges.1", "UX designer")
                .addFacebook("guilherme.ribeirof", "Desenvolvedor")
                .addFacebook("natalia.l.moreno.7","Desenvolvedora")
                .addFacebook("rafael.macedo.m","Lider")
                .addFacebook("samara.rayane.28","Business")

                .addItem( versao )

                .create();


        //return inflater.inflate(R.layout.fragment_sobre, container, false);
    }



//        return view

}