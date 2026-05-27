package com.grupitxo.classes;

import java.util.List;

public interface ClienteStorageStrategy {
    // CREATE
    boolean salvar(Cliente c);
    // READ
    List<Cliente> buscarTodos();
    Cliente buscarPorId(int id);
    Cliente buscarPorNome(String nome);
    // UPDATE
    Cliente atualizar(Cliente c);
    // DELETE
    boolean remover(Cliente c);
    // Auxiliares
    int getQtd();
}
