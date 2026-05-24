package com.grupitxo.classes;

import java.util.List;

public interface FuncionarioStorageStrategy {
    // CREATE
    boolean salvar(Funcionario f);
    // READ
    List<Funcionario> buscarTodos();
    Funcionario buscarPorId(int id);
    Funcionario buscarPorNome(String nome);
    // UPDATE
    Funcionario atualizar(Funcionario f);
    // DELETE
    boolean remover(Funcionario f);
    // Auxiliares
    int getQtd();
}
