package com.grupitxo.classes;

import java.util.Scanner;


public class Venda {
	// Métodos retornam true/false e Main gerencia mensagem de erro/sucesso
	public static boolean realizarVenda() {
		Cliente cliente = Cliente.selecionarCliente();
		if (cliente == null) {
			return false;
		}
		Funcionario funcionario = Funcionario.selecionarFuncionario();
		if (funcionario == null) {
			return false;
		}
		Compra compra = Compra.getCompraNova(cliente, funcionario);
		if (compra != null) {
			return true;
		}
		return false;
	}
	
	public static boolean pesquisarVendas() {
		// Setup
		Scanner sc = new Scanner(System.in);
		int opcao;
		// Prompts
		loopPesquisarVendas: while (true) {
			System.out.println(StringsMenu.menuPesquisarVendas);
			opcao = sc.nextInt();
			switch (opcao) {
				// Pesquisar por cliente
				case 1:
					Cliente cliente = Cliente.selecionarCliente();
					if (cliente != null) {
						System.out.println(Historico.getHistoricoCliente(cliente.getIdCliente()));
					} else {
						return false;
					}
					return true;
				// Pesquisar por funcionário
				case 2:
					Funcionario funcionario = Funcionario.selecionarFuncionario();
					if (funcionario != null) {
						System.out.println(Historico.getHistoricoFuncionario(funcionario.getIdFuncionario()));
					} else {
						return false;
					}
					return true;
				// Exibir todas as vendas
				case 3:
					String historico = Historico.exibirHistoricoCompleto();
					if (historico != null) {
						System.out.println(historico);
					} else {
						return false;
					}
					return true;
				// Retornar ao menu anterior
				case 0:
					break loopPesquisarVendas;
				default:
					System.err.println("ERRO! Opção inválida");
					break;
			}
		}
		return false;
	}
	
	public static boolean cancelarVenda() {
		// Pegar índice da compra a cancelar
		Scanner sc = new Scanner(System.in);
		System.out.println("Digite o índice da venda que deseja cancelar: ");
		int idx = sc.nextInt();
		// Pegar compra a cancelar para confirmar
		String compraACancelar = Historico.getCompraByIndex(idx - 1);
		if (compraACancelar == null) {
			System.out.println(String.format("ERRO! Índice '%d' não existe", idx));
			return false;
		}
		// Confirmar operação
		System.out.println(String.format("""
				
Você tem certeza que deseja cancelar a compra a seguir?

Compra:
%s

Se sim, digite 's', se não digite 'n' (CUIDADO: Essa operação não pode ser revertida):""", compraACancelar));
		char confirmacao = sc.next().charAt(0);
		if (Character.toLowerCase(confirmacao) == 's') {
			Historico.cancelarCompra(idx - 1);
			return true;
		} else {
			return false;
		}
	}
}