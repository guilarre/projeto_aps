package com.grupitxo.testes;

import java.util.ArrayList;
import java.util.List;
import com.grupitxo.classes.Funcionario;
import com.grupitxo.classes.FuncionarioStorageStrategy;

// vai servir de mock pros testes
public class FuncionarioDbAdapterFake implements FuncionarioStorageStrategy {
    public List<Funcionario> dbFalso = new ArrayList<>();

    @Override
    public List<Funcionario> buscarTodos() {
        return dbFalso;
    }

    @Override
    public Funcionario buscarPorId(int id) {
        for (Funcionario f : dbFalso) {
            if (f.getIdFuncionario() == id) return f;
        }
        return null; // Não achou
    }

    @Override
    public boolean salvar(Funcionario f) {
        dbFalso.add(f);
        return true;
    }

    @Override
    public boolean remover(Funcionario f) {
        return dbFalso.remove(f);
    }

    @Override
    public Funcionario buscarPorNome(String nome) {
        for (Funcionario f : dbFalso) {
            if (f.getNome().equalsIgnoreCase(nome)) return f;
        }
        return null;
    }

    @Override
    public Funcionario atualizar(Funcionario f) {
        for (int i = 0; i < dbFalso.size(); i++) {
            if (dbFalso.get(i).getIdFuncionario() == f.getIdFuncionario()) {
                dbFalso.set(i, f);
                return f;
            }
        }
        return null;
    }

    @Override
    public int getQtd() {
        return dbFalso.size();
    }
}