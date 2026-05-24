package com.grupitxo.classes;

public interface FuncionarioStorageStrategy {
    // CREATE
    boolean salvar(Funcionario f);
    // READ
    Funcionario buscarPorId(int id);
    Funcionario buscarPorNome(String nome);
    // UPDATE
    Funcionario atualizar(Funcionario f);
    // DELETE
    boolean remover(Funcionario f);
    // Auxiliares
    int getQtd();
}
