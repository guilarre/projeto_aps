package com.grupitxo.classes;

import java.util.List;
import java.util.Scanner;

public class ClienteView {
    // mostra tds os clientes da lista na tela
    public void exibirListaClientes(List<Cliente> lista) {
        if (lista == null || lista.isEmpty()) {
            System.out.println("Não há clientes registrados no sistema.");
            return;
        }
        for (Cliente cliente : lista) {
            System.out.println(cliente.toString());
        }
    }
    
    public void exibirCliente(Cliente cliente) {
        if (cliente == null) {
            System.out.println("ERRO Cliente não encontrado ou registro inválido.");
            return;
        }
        System.out.println("\n=== DETALHES DO CLIENTE ===");
        System.out.println(cliente);
        System.out.println("===============================\n");
    }
    
    public int mostrarMenuSelecao(Scanner sc) {
        System.out.println(StringsMenu.menuSelecionarCliente);
        int opcao = sc.nextInt();
        // NOTE: tem que dar esse nextLine() antes pra apagar o \n que sobra do nextInt() no buffer!
        if (sc.hasNextLine()) {
            sc.nextLine(); 
        }
        return opcao;
    } 
    
    public int pedirIdParaBusca(Scanner sc) {
        System.out.println("Digite o id do cliente:");
        int id = sc.nextInt();
        if (sc.hasNextLine()) {
            sc.nextLine();
        }
        return id;
    }

    public String pedirNomeParaBusca(Scanner sc) {
        System.out.println("Digite o nome do cliente:");
        return sc.nextLine();
    }

    // Métodos pro UPDATE
    // pra pedir o campo a ser modificado
    public int opcaoEdicao(Scanner sc) {
        // chamando o menu na tela
        System.out.println(StringsMenu.menuModificarCliente);
        int opcao = sc.nextInt();
        if (sc.hasNextLine()) {
            sc.nextLine();
        }
        return opcao;
    }
    
    // Método genérico para pedir textos (Nome, CPF, Email, etc.)
    public String pedirNovoDadoTexto(Scanner sc, String campo) {
        System.out.println("Digite o novo " + campo + ": ");
        return sc.nextLine();
    }

    // Método específico para pedir números (Salário)
    public double pedirNovoDadoDecimal(Scanner sc, String campo) {
        System.out.println("Digite o novo " + campo + " (e.g. 1500,00): ");
        double valor = sc.nextDouble();
        if (sc.hasNextLine()) {
            sc.nextLine();
        }
        return valor;
    }

    // Método pra pedir confirmação do user
    public int pedirConfirmacao(Scanner sc) {
        System.out.println("Tem certeza que deseja continuar? Digite 1 se sim, 0 se deseja voltar:");
        int opcao = sc.nextInt();
        if (sc.hasNextLine()) {
            sc.nextLine();
        }
        return opcao;
    }
}
