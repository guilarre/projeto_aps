package com.grupitxo.classes;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.grupitxo.classes.Compra.CompraProduto;
import com.grupitxo.enums.Status;

public class Historico {
	// por enquanto tem que ser assim diretamente pelo histórico, mas futuramente seria tudo pelo controller
	private static FuncionarioStorageStrategy adapter;
	static {
        try {
            adapter = new FuncionarioDbAdapter(DatabaseConfig.getConnection());
        } catch (SQLException e) {
            System.err.println("Erro fatal: Não foi possível conectar ao banco de dados para os Funcionários.");
            e.printStackTrace();
            // Se der erro na conexão, o adapter ficará nulo, 
            // então você já sabe que se estourar NullPointerException depois, foi o banco que não subiu.
        }
    }

	private static ArrayList<Compra> historico = new ArrayList<Compra>();
	
	// CRUD
	// CREATE para carregar .json
	public static void addToHistorico(Compra compra) {
		historico.add(compra);
	}
	// READ do objeto historico
	public static ArrayList<Compra> getHistorico() {
		return historico;
	}
	// READ para exibir histórico geral ao usuário
	public static String exibirHistoricoCompleto() {
		if (historico.isEmpty()) {
			System.err.println("ERRO! Ainda não existem compras registradas");
			return null;
		}
		StringBuilder historicoCompleto = new StringBuilder();
		for (int i = 0; i < historico.size(); i++) {
			historicoCompleto.append(String.format("Índice: %d\nCompra:\n%s\n\n", i + 1, historico.get(i).toString()));
		}
		return String.valueOf(historicoCompleto);
	}
	// READ para exibir compra específica ao usuário
	public static String getCompraByIndex(int idx) {
		try {
			return String.format("Índice: %d\nCompra:\n%s", idx + 1, historico.get(idx).toString());
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	// READ para exibir ao usuário compras de um cliente específico
	public static String getHistoricoCliente(int idCliente) {
		// Checar primeiro se idCliente existe
		ArrayList<Integer> idsDisponiveis = new ArrayList<Integer>();
		for (Cliente cliente : Cliente.getListaClientes()) {
			idsDisponiveis.add(cliente.getIdCliente());
		}
		if (idsDisponiveis.contains(idCliente) == false) {
			System.out.println(String.format("ERRO! A id '%d' não foi registrada ainda", idCliente));
			return null;
		}
		// Existindo, pegar nome do cliente
		String nomeCliente = Cliente.getClienteById(idCliente).getNome();
		// Pegar histórico do cliente
		StringBuilder historicoCliente = new StringBuilder();
		for (int i = 0; i < historico.size(); i++) {
			Compra compra = historico.get(i);
			if (compra.getIdCliente() == idCliente) {
				historicoCliente.append(compra.toString());
			}
		}
		if (historicoCliente.isEmpty()) {
			System.out.println(String.format("ERRO! O cliente de id '%d' ainda não possui compras registradas", idCliente));
			return null;
		}
		return String.format("""

Compras de %s (id do cliente: '%d'):
%s""", nomeCliente, idCliente, String.valueOf(historicoCliente));
	}
	// READ para exibir ao usuário vendas de um funcionário específico
	public static String getHistoricoFuncionario(int idFuncionario) {
		// Checar primeiro se idFuncionario existe
		ArrayList<Integer> idsDisponiveis = new ArrayList<Integer>(); 
		for (Funcionario funcionario : adapter.buscarTodos()) {
			idsDisponiveis.add(funcionario.getIdFuncionario());
		}
		if (idsDisponiveis.contains(idFuncionario) == false) {
			System.out.println(String.format("ERRO! A id '%d' não foi registrada ainda", idFuncionario));
			return null;
		}
		// Existindo, pegar nome do cliente
		String nomeFuncionario = adapter.buscarPorId(idFuncionario).getNome();
		// Pegar histórico do funcionário
		StringBuilder historicoFuncionario = new StringBuilder();
		for (int i = 0; i < historico.size(); i++) {
			Compra compra = historico.get(i);
			if (compra.getIdFuncionario() == idFuncionario) {
				historicoFuncionario.append(compra.toString());
			}
		}
		if (historicoFuncionario.isEmpty()) {
			System.out.println(String.format("ERRO! O funcionário de id '%d' ainda não possui vendas registradas", idFuncionario));
			return null;
		}
		return String.format("""
				
Vendas de %s (id '%d'):
%s""", nomeFuncionario, idFuncionario, String.valueOf(historicoFuncionario));
	}
	// UPDATE
	public static void cancelarCompra(int idx) {
		Compra compraACancelar = historico.get(idx);
		// Alterando qtd em estoque após cancelar
		Compra.incrementarEstoque(compraACancelar.getListaCompraProdutos());
		compraACancelar.setStatus(Status.CANCELADA);
	}

	// Gerar relatório do mês
	public static String gerarRelatorio() {
		ArrayList<Compra> comprasMes = new ArrayList<Compra>();
		for (Compra compra : historico) {
			if (compra.getObjetoDataHora().getMonth() == LocalDate.now().getMonth()) {
				comprasMes.add(compra);
			}
		}
		int qtdCompradaMes = 0;
		double totalCompradoMes = 0;
		for (Compra compra : comprasMes) {
			for (CompraProduto compraProduto : compra.getListaCompraProdutos()) {
				qtdCompradaMes += compraProduto.getQtdComprada();
			}
			totalCompradoMes += compra.getTotal();
		}
		return String.format("""

############ Relatório do mês ############

Foram realizadas %d compras neste mês,
Totalizando: R$ %.2f.

""", qtdCompradaMes, totalCompradoMes);
	}
}
