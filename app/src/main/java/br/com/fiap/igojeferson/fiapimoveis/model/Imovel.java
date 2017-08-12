package br.com.fiap.igojeferson.fiapimoveis.model;

import java.io.Serializable;

/**
 * Created by IgoJeferson on 2017-08-11.
 */
public class Imovel implements Serializable {

    private Long id;
    private String nome;
    private String endereco;
    private String telefone;
    private Integer tamanho;
    private Integer tipo;
    private Integer emConstrucao;
    private String observacao;
    private Integer ativo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Integer getTamanho() {
        return tamanho;
    }

    public void setTamanho(Integer tamanho) {
        this.tamanho = tamanho;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Integer getEmConstrucao() {
        return emConstrucao;
    }

    public void setEmConstrucao(Integer emConstrucao) {
        this.emConstrucao = emConstrucao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Integer getAtivo() {
        return ativo;
    }

    public void setAtivo(Integer ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return this.id + " - " + this.nome;
    }
}