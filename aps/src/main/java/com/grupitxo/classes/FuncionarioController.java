package com.grupitxo.classes;

import java.util.List;
import java.util.Scanner;

import com.grupitxo.enums.Cargo;

public class FuncionarioController {
    private FuncionarioView view;
    private FuncionarioViewFactory factory;
    private FuncionarioStorageStrategy adapter;

    // Construtor
    public FuncionarioController(FuncionarioView view, FuncionarioViewFactory factory, FuncionarioStorageStrategy adapter) {
        this.view = view;
        this.factory = factory;
        this.adapter = adapter;
    }

    // SELEÇÃO E BUSCA
    public Funcionario selecionarFuncionarioParaAcao(Scanner sc) {
        int opcao = view.mostrarMenuSelecao(sc);

        switch (opcao) {
            case 1:
                int idBusca = view.pedirIdParaBusca(sc);
                return adapter.buscarPorId(idBusca); 

            case 2:
                String nomeBusca = view.pedirNomeParaBusca(sc);
                return adapter.buscarPorNome(nomeBusca); 

            case 0:
                System.out.println("Voltando ao menu anterior...");
                return null;

            default:
                System.err.println("ERRO! Opção inválida");
                return null;
        }
    }

    public void rotinaListarFuncionarios() {
        List<Funcionario> lista = adapter.buscarTodos();
        view.exibirListaFuncionarios(lista);
    }

    // rotinas CREATE, UPDATE, DELETE
    // CREATE
    public void rotinaCriarFuncionario(Scanner sc) {
        // A View/Factory tenta criar. Se der IllegalArgumentException vai retornar null
        Funcionario novoFuncionario = factory.criarFuncionarioNovo(sc);
        
        if (novoFuncionario != null) {
            adapter.salvar(novoFuncionario);
            System.out.println("Funcionário registrado com sucesso!");
        } else {
            System.out.println("ERRO! Cadastro cancelado devido a dados inválidos.");
        }
    }

    // UPDATE
    public void rotinaAtualizarFuncionario(Scanner sc) {
        // seleciona o funcionário p atualizar
        Funcionario alvo = selecionarFuncionarioParaAcao(sc);
        if (alvo == null) {
            System.out.println("Nenhum funcionário selecionado. Operação cancelada.");
            return;
        }

        // Mostra os dados atuais para o usuário conferir
        view.exibirFuncionario(alvo);

        // Mostra o menu e pega o que o usuário quer alterar
        int opcao = view.opcaoEdicao(sc);

        if (opcao == 0) {
            System.out.println("Modificação cancelada. Voltando...");
            return;
        }

        // Estrutura para capturar o dado, alterar no objeto (em memória) e mandar pro DB
        try {
            switch (opcao) {
                case 1:
                    String novoNome = view.pedirNovoDadoTexto(sc, "nome");
                    alvo.setNome(novoNome);
                    break;
                case 2:
                    String novoCpf = view.pedirNovoDadoTexto(sc, "CPF");
                    alvo.setCpf(novoCpf);
                    break;
                case 3:
                    String novoTelefone = view.pedirNovoDadoTexto(sc, "telefone");
                    alvo.setTelefone(novoTelefone);
                    break;
                case 4:
                    String novoEmail = view.pedirNovoDadoTexto(sc, "email");
                    alvo.setEmail(novoEmail);
                    break;
                case 5:
                    String novaPref = view.pedirNovoDadoTexto(sc, "preferência de comunicação");
                    alvo.setPreferenciaComunicacao(novaPref);
                    break;
                case 6:
                    String novoEndereco = view.pedirNovoDadoTexto(sc, "endereço");
                    alvo.setEndereco(novoEndereco);
                    break;
                case 7:
                    String novoAniversario = view.pedirNovoDadoTexto(sc, "aniversário");
                    alvo.setAniversario(novoAniversario);
                    break;
                case 8:
                    String novoGenero = view.pedirNovoDadoTexto(sc, "gênero");
                    alvo.setGenero(novoGenero);
                    break;
                case 9:
                    int opCargo = view.pedirNovoCargo(sc);
                    switch (opCargo) {
                        case 1 -> alvo.setCargo(Cargo.VENDEDOR);
                        case 2 -> alvo.setCargo(Cargo.CAIXA);
                        case 3 -> alvo.setCargo(Cargo.ESTOQUISTA);
                        case 4 -> alvo.setCargo(Cargo.SOCIO);
                        default -> throw new IllegalArgumentException("Opção de cargo inválida.");
                    }
                    break;
                case 10:
                    double novoSalario = view.pedirNovoDadoDecimal(sc, "salário");
                    alvo.setSalario(novoSalario);
                    break;
                default:
                    System.err.println("ERRO! Opção inválida.");
                    return; // Sai do método sem atualizar o banco
            }

            // Se chegou aqui, o dado foi alterado na memória com sucesso. Vamos salvar no banco.
            adapter.atualizar(alvo);
            System.out.println("Funcionário atualizado com sucesso no sistema!");

        } catch (IllegalArgumentException e) {
            // Caso o setter dispare um erro (ex: CPF inválido, Salário negativo), capturamos aqui
            System.err.println("ERRO! Falha ao atualizar: " + e.getMessage());
        }
    }
    
    // REMOVE
    public void rotinaRemoverFuncionario(Scanner sc) {
        Funcionario alvo = selecionarFuncionarioParaAcao(sc);

        if (alvo == null) {
            System.out.println("Nenhum funcionário selecionado. Operação cancelada.");
            return;
        }

        view.exibirFuncionario(alvo);
        view.pedirConfirmacao(sc); // TODO: testar isso
        adapter.remover(alvo);
        System.out.println("Funcionário removido com sucesso!");
    }
}