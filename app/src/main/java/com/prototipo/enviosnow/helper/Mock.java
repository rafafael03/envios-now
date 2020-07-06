package com.prototipo.enviosnow.helper;

import com.prototipo.enviosnow.model.Encomenda;

import java.util.Random;

public class Mock {

    private static String[] numerosTelefone = {"(11)11111-1111","(22)22222-2222","(33)33333-3333","(44)44444-4444","(55)55555-5555","(66)66666-6666","(77)77777-7777","(88)88888-8888","(99)99999-9999","(10)10101-1010"};
    private static String[] nomes = {"Pafuncio", "Jobiraldo", "Aquila Boy", "Jorsevaldo", "Aneyclaivison","Alexnaldo","Apoema","Mara","Yamba","Artemio"};
    private static String[] locais = {"Shopping Vila Olímpia", "Shopping cidade São Paulo", "Shopping Morumbi", "Shopping Iguatemi", "Masp", "av Brigadeiro Faria Lima", "rua Augusta","av Jabaquara", "rua Almaden", "Shopping Jardim Sul"};
    private static String[] descricao = {"15x15x15cm", "25x25x25cm", "30x30x30cm", "45x45x45cm","55x55x55cm","65x65x65cm","75x75x75cm","90x90x90cm","120x120x120cm","250x250x250cm"};


    public static Encomenda criarEncomenda(Encomenda encomenda){

        Random indiceRandomico = new Random();
        String escolha;
        //Seta nome do remetente aleatorio
        escolha = nomes[indiceRandomico.nextInt(10)];

        encomenda.setRemetente(escolha);
        //Seta nome do destinatario aleatorio
        while (encomenda.getRemetente().equals(escolha)){
            escolha = nomes[indiceRandomico.nextInt(10)];
        }
        encomenda.setDestinatario(escolha);

        //Seta numero de telefone aleatorio
        escolha = numerosTelefone[indiceRandomico.nextInt(10)];
        encomenda.setContatoDestinatario(escolha);

        //Seta descricao aleatoria
        escolha = descricao[indiceRandomico.nextInt(10)];
        encomenda.setDescricao(escolha);

        //Seta local aleatorio de entrega
        escolha = locais[indiceRandomico.nextInt(10)];

        encomenda.setLocalEntrega(escolha);

        //Seta local de coleta aleatorio
        while (encomenda.getLocalEntrega().equals(escolha)){
            escolha = locais[indiceRandomico.nextInt(10)];
        }
        encomenda.setLocalColeta(escolha);

        encomenda.setIdEntregador("");
        encomenda.setDataConclusao("");

        return encomenda;
    }

}
