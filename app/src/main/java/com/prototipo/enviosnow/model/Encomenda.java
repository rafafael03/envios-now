package com.prototipo.enviosnow.model;

import java.io.Serializable;

public class Encomenda implements Serializable {

    private String descricao;
    private String destinatario;
    private String remetente;
    private String localColeta;
    private String localEntrega;
    private String contatoDestinatario;
    private String codigoProduto;
    private String idEntregador;
    private String dataConclusao;

    public Encomenda() {
    }

    public String getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(String dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public String getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(String codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public String getIdEntregador() {
        return idEntregador;
    }

    public void setIdEntregador(String idEntregador) {
        this.idEntregador = idEntregador;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getRemetente() {
        return remetente;
    }

    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

    public String getLocalColeta() {
        return localColeta;
    }

    public void setLocalColeta(String localColeta) {
        this.localColeta = localColeta;
    }

    public String getLocalEntrega() {
        return localEntrega;
    }

    public void setLocalEntrega(String localEntrega) {
        this.localEntrega = localEntrega;
    }

    public String getContatoDestinatario() {
        return contatoDestinatario;
    }

    public void setContatoDestinatario(String contatoDestinatario) {
        this.contatoDestinatario = contatoDestinatario;
    }

}
