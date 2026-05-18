package com.grupitxo.classes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import com.grupitxo.enums.Pagamento;
import com.grupitxo.enums.Status;

public class Compra {
	// Contador para autoincrementar idCompra
	private static AtomicInteger count = new AtomicInteger(0);
	private final int idCompra;
	private int idCliente;
	private int idFuncionario;
	private LocalDateTime dataHora;
	private ArrayList<CompraProduto> listaCompraProdutos = null;
	private double subtotal = 0;
	private double desconto;
	private double total = 0;
	private Pagamento pagamento = null;
	private Status status = Status.EM_PROCESSAMENTO;
	
	public Compra(int idCliente, int idFuncionario, ArrayList<CompraProduto> listaCompraProdutos, double desconto, Pagamento pagamento, boolean autoAdd) {
		idCompra = count.incrementAndGet();
		this.idCliente = idCliente;
		this.idFuncionario = idFuncionario;
		dataHora = LocalDateTime.now();
		this.listaCompraProdutos = listaCompraProdutos;
		for (CompraProduto compraProduto : listaCompraProdutos) {
			subtotal += compraProduto.getTotal();
		}
		// autoAdd é usado como flag pra indicar se foi criado pelo gson
		if (autoAdd == true) {
			if (desconto != 0) {
				// Math.abs para deixar positivo
				this.desconto = Math.abs(desconto - 100) / 100;
				this.total = subtotal * desconto;
			} else {			
				this.total = subtotal;
			}
		} else {
			if (desconto != 0) {
				this.desconto = desconto;
				this.total = subtotal * desconto;
			} else {			
				this.total = subtotal;
			}
		}
		this.pagamento = pagamento;
		// Alterando qtd em estoque após sucesso
		Compra.decrementarEstoque(this.listaCompraProdutos);
		status = Status.SUCESSO;
		// Salvar compra no histórico
		if (autoAdd) {			
			Historico.addToHistorico(this);
		}
	}
	
	// Getters/setters
	public int getIdCompra() {
		return idCompra;
	}
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public int getIdFuncionario() {
		return idFuncionario;
	}
	public void setIdFuncionario(int idFuncionario) {
		this.idFuncionario = idFuncionario;
	}
	public String getDataHora() {
		String formatterString = "dd/MM/yyyy";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatterString);
		return dataHora.format(formatter);
	}
	public LocalDateTime getObjetoDataHora() {
		return dataHora;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public double getDesconto() {
		return desconto;
	}
	public void setDesconto(double desconto) {
		this.desconto = desconto;
		// Atualizando o total após alterar desconto
		total = subtotal * this.desconto;
	}
	public double getTotal() {
		return total;
	}
	public Pagamento getPagamento() {
		return pagamento;
	}
	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}
	public static String getPagamentoValue(Pagamento pagamento) {
		switch (pagamento) {
			case A_VISTA:
				return "À vista";
			case PIX:
				return "Pix";
			case DEBITO:
				return "Débito";
			case CREDITO:
				return "Crédito";
			case CHEQUE:
				return "Cheque";
			default:
				return "ERRO! Forma de pagamento ainda não registrada";
		}
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public static String getStatusValue(Status status) {
		switch (status) {
			case CANCELADA:
				return "Cancelada";
			case EM_PROCESSAMENTO:
				return "Em processamento";
			case SUCESSO:
				return "Sucesso";
			default:
				return "ERRO! Status ainda não registrado";
		}
	}
	
	// CREATE e retornar objeto Compra novo (usado pela Main)
	public static ArrayList<CompraProduto> getCompraProdutoNovo() {
		ArrayList<CompraProduto> listaCompraProdutos = new ArrayList<CompraProduto>();
		Produto produtoSelecionado = null;
		double desconto = 1;
		int qtdComprada = 0;
		CompraProduto compraProduto = null;
		
		Scanner sc = new Scanner(System.in);
		loopSelecaoProdutos: while (true) {
			String menuSelecionarProduto = """

Selecione um ou mais produtos:

[1] = Procurar produto por seu id
[2] = Procurar produto por seu nome
[3] = TERMINAR seleção de produtos

[0] = Retornar ao menu anterior
""";
			System.out.println(menuSelecionarProduto);
			int opcao = sc.nextInt();
			switch (opcao) {
				case 1:
					// Selecionando o produto pelo id
					System.out.println("Digite o id do produto:");
					int idProduto = sc.nextInt();
					produtoSelecionado = Produto.getProdutoById(idProduto);
					System.out.println(String.format("Produto selecionado:\n\n%s", produtoSelecionado));
					// Desconto
					System.out.println("Digite o desconto DO PRODUTO em porcentagem, se aplicável (e.g. 12,5).\nSe não houver desconto, digite 0:");
					desconto = sc.nextDouble();
					// Qtd comprada
					System.out.println("Digite a quantidade que será comprada:");
					qtdComprada = sc.nextInt();
					compraProduto = new Compra.CompraProduto(produtoSelecionado, qtdComprada, desconto, true);
					listaCompraProdutos.add(compraProduto);
					break;
				case 2:
					// Selecionando o produto pelo nome
					System.out.println("Digite o nome do produto:");
					// HACK: tem que dar esse nextLine() antes pra apagar o \n que sobra do nextInt()
					sc.nextLine();
					String nomeProduto = sc.nextLine();
					produtoSelecionado = Produto.getProdutoByNome(nomeProduto);
					System.out.println(String.format("Produto selecionado:\n\n%s", produtoSelecionado));
					// Desconto
					System.out.println("Digite o desconto DO PRODUTO em porcentagem, se aplicável (e.g. 12,5).\nSe não houver desconto, digite 0:");
					desconto = sc.nextDouble();
					// Qtd comprada
					System.out.println("Digite a quantidade que será comprada:");
					qtdComprada = sc.nextInt();
					compraProduto = new Compra.CompraProduto(produtoSelecionado, qtdComprada, desconto, true);
					listaCompraProdutos.add(compraProduto);
					break;
				case 3:
					return listaCompraProdutos;
				case 0:
					System.out.println("Operação cancelada!");
					break loopSelecaoProdutos;
				default:
					System.err.println("ERRO! Opção inválida");
					break;
			}
		}
		return null;
	}
	public static Compra getCompraNova(Cliente cliente, Funcionario funcionario) {
		// listaCompraProdutos
		ArrayList<CompraProduto> listaCompraProdutos = Compra.getCompraProdutoNovo();
		if (listaCompraProdutos == null) {
			return null;
		}
		Scanner sc = new Scanner(System.in);
		// Desconto
		System.out.println("Digite o desconto DA COMPRA em porcentagem, se aplicável (e.g. 12,5).\nSe não houver desconto, digite 0:");
		double desconto = sc.nextDouble();
		// Pagamento (forma de pagamento)
		Pagamento pagamento = null;
		System.out.println("""

Escolha a forma de pagamento:

[1] = À vista
[2] = Pix
[3] = Débito
[4] = Crédito
[5] = Cheque

""");
		int opcaoPagamento = sc.nextInt();
		switch (opcaoPagamento) {
			case 1:
				pagamento = Pagamento.A_VISTA;
				break;
			case 2:
				pagamento = Pagamento.PIX;
				break;
			case 3:
				pagamento = Pagamento.DEBITO;
				break;
			case 4:
				pagamento = Pagamento.CREDITO;
				break;
			case 5:
				pagamento = Pagamento.CHEQUE;
				break;
			default:
				System.err.println("ERRO! Opção inválida");
				break;
		}
		// Criar e retornar objeto Compra
		Compra compra = new Compra(cliente.getIdCliente(), funcionario.getIdFuncionario(), listaCompraProdutos, desconto, pagamento, true);
		return compra;
	}
	
	// Métodos para listaCompraProdutos
	public ArrayList<CompraProduto> getListaCompraProdutos() {
		return listaCompraProdutos;
	}
	public double getTotalListaCompraProdutos() {
		double total = 0;
		for (CompraProduto compraProduto : listaCompraProdutos) {
			total += compraProduto.getTotal();
		}
		return total;
	}
	// Chamado após compra ter sucesso
	public static void decrementarEstoque(ArrayList<CompraProduto> listaCompraProdutos) {
		for (CompraProduto compraProduto : listaCompraProdutos) {
			Produto produto = compraProduto.getProduto();
			int qtdEmEstoqueAtual = produto.getQtdEstoque();
			int qtdComprada = compraProduto.getQtdComprada();
			int qtdEmEstoqueNova = qtdEmEstoqueAtual - qtdComprada;
			produto.setQtdEstoque(qtdEmEstoqueNova);
		}
	}
	// Chamado após compra ser cancelada
	public static void incrementarEstoque(ArrayList<CompraProduto> listaCompraProdutos) {
		for (CompraProduto compraProduto : listaCompraProdutos) {
			Produto produto = compraProduto.getProduto();
			int qtdEmEstoqueAtual = produto.getQtdEstoque();
			int qtdComprada = compraProduto.getQtdComprada();
			int qtdEmEstoqueNova = qtdEmEstoqueAtual + qtdComprada;
			produto.setQtdEstoque(qtdEmEstoqueNova);
		}
	}
	
	@Override 
	public String toString() {
		return String.format("""

Id da compra: '%d'

Id do cliente: '%d'
Id do Funcionário: '%d'
Data e hora da compra: '%s'
Subtotal: '%f'
Desconto: '%f'
Total: R$ %.2f
Forma de pagamento: '%s'
Status: '%s'

""", this.getIdCompra(), this.getIdCliente(), this.getIdFuncionario(), this.getDataHora(), this.getSubtotal(), this.getDesconto(), this.getTotal(), Compra.getPagamentoValue(pagamento), Compra.getStatusValue(status));
	}
	
	public static class CompraProduto {
		private Produto produto;
		private int qtdComprada = 0;
		private double desconto;
		private double total = 0;
		
		public CompraProduto(Produto produto, int qtdComprada, double desconto, boolean autoAdd) {
			this.produto = produto;
			this.qtdComprada = qtdComprada;
			// autoAdd é usado como flag pra indicar se foi criado pelo gson
			if (autoAdd == true) {
				if (desconto != 0) {
					// Math.abs para deixar positivo
					this.desconto = Math.abs(desconto - 100) / 100;
					this.total = produto.getValor() * qtdComprada * desconto;
				} else {
					this.total = produto.getValor() * qtdComprada;
				}
			} else {
				if (desconto != 0) {
					this.desconto = desconto;
					this.total = produto.getValor() * qtdComprada * desconto;
				} else {
					this.total = produto.getValor() * qtdComprada;
				}
			}
		}
		
		//Getters/Setters
		public Produto getProduto() {
			return produto;
		}
		public void setProduto(Produto produto) {
			this.produto = produto;
			// Atualizando total após alterar produto
			total = this.produto.getValor() * qtdComprada * desconto;
		}
		public int getQtdComprada() {
			return qtdComprada;
		}
		public void setQtdComprada(int qtdComprada) {
			this.qtdComprada = qtdComprada;
			// Atualizando total após alterar qtdComprada
			total = produto.getValor() * this.qtdComprada * desconto;
		}
		public double getDesconto() {
			return desconto;
		}
		public void setDesconto(int desconto) {
			this.desconto = desconto;
			// Atualizando total após alterar o produto
			total = produto.getValor() * qtdComprada * this.desconto;
		}
		public double getTotal() {
			return total;
		}

		@Override
		public String toString () {
			return String.format("""
					
Id do Produto: '%d'
Nome do Produto: '%s'
Quantidade: '%d'
Desconto: '%f'
Total: R$ %.2f

""", this.getProduto().getIdProduto(), this.getProduto().getNomeProduto(), this.getQtdComprada(), this.getDesconto(), this.getTotal());
		}
	}
}