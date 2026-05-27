package com.grupitxo.classes;

import java.util.List;
import java.util.Scanner;

public class ClienteController {
    private ClienteView view;
    private ClienteViewFactory factory;
    private ClienteStorageStrategy adapter;

    // Construtor
    public ClienteController(ClienteView view, ClienteViewFactory factory, ClienteStorageStrategy adapter) {
        this.view = view;
        this.factory = factory;
        this.adapter = adapter;
    }

    // SELEÇÃO E BUSCA
    public Cliente selecionarClienteParaAcao(Scanner sc) {
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

    public void rotinaListarClientes() {
        List<Cliente> lista = adapter.buscarTodos();
        view.exibirListaClientes(lista);
    }

    // rotinas CREATE, UPDATE, DELETE
    // CREATE
    public void rotinaCriarCliente(Scanner sc) {
        // A View/Factory tenta criar. Se der IllegalArgumentException vai retornar null
        Cliente novoCliente = factory.criarClienteNovo(sc);
        
        if (novoCliente != null) {
            adapter.salvar(novoCliente);
            System.out.println("Cliente registrado com sucesso!");
        } else {
            System.out.println("ERRO! Cadastro cancelado devido a dados inválidos.");
        }
    }

    // UPDATE
    public void rotinaAtualizarCliente(Scanner sc) {
        // seleciona o cliente p atualizar
        Cliente alvo = selecionarClienteParaAcao(sc);
        if (alvo == null) {
            System.out.println("Nenhum cliente selecionado. Operação cancelada.");
            return;
        }

        // Mostra os dados atuais para o usuário conferir
        view.exibirCliente(alvo);

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
                default:
                    System.err.println("ERRO! Opção inválida.");
                    return; // Sai do método sem atualizar o banco
            }

            // Se chegou aqui, o dado foi alterado na memória com sucesso. Vamos salvar no banco.
            adapter.atualizar(alvo);
            System.out.println("Cliente atualizado com sucesso no sistema!");

        } catch (IllegalArgumentException e) {
            // Caso o setter dispare um erro (ex: CPF inválido, Salário negativo), capturamos aqui
            System.err.println("ERRO! Falha ao atualizar: " + e.getMessage());
        }
    }
    
    // REMOVE
    public void rotinaRemoverCliente(Scanner sc) {
        Cliente alvo = selecionarClienteParaAcao(sc);

        if (alvo == null) {
            System.out.println("Nenhum cliente selecionado. Operação cancelada.");
            return;
        }

        view.exibirCliente(alvo);
        view.pedirConfirmacao(sc); // TODO: testar isso
        adapter.remover(alvo);
        System.out.println("Cliente removido com sucesso!");
    }
}