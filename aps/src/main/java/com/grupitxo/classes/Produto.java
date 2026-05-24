package com.grupitxo.classes;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import com.grupitxo.enums.Categoria;

public class Produto {
	private static ArrayList<Produto> listaProdutos = new ArrayList<Produto>();
	private static AtomicInteger count = new AtomicInteger(0);
	private int idProduto;
	private String sku;
	private String nomeProduto;
	private String descricao;
	private double valor;
	private Categoria categoria = null;
	private int qtdEstoque;
	
	public Produto(String sku, String nomeProduto, String descricao, double valor, Categoria categoria, int qtdEstoque, boolean autoAdd) {
		idProduto = count.incrementAndGet();
		this.sku = sku;
		this.nomeProduto= nomeProduto;
		this.descricao = descricao;
		this.valor = valor;
		this.categoria = categoria;
		this.qtdEstoque = qtdEstoque;
		if (autoAdd) { //
			listaProdutos.add(this);
		}
	}
	
	// Getters/setters
	public int getIdProduto() {
		return idProduto;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getNomeProduto() {
		return nomeProduto;
	}
	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	// CHECK: talvez dê pra tirar isso e usar getCategoria().toString
	public static String getCategoriaValue(Categoria categoria) {
		switch (categoria) {
			case CALCA:
				return "Calça";
			case SAIA:
				return "Saia";
			case JAQUETA:
				return "Jaqueta";
			case BLUSA:
				return "Blusa";
			case SHORTS:
				return "Shorts";
			default:
				return "ERRO! Categoria ainda não registrada";
		}
	}
	public static String getTodasCategorias() {
		StringBuilder stringBuilder = new StringBuilder();
		for (Categoria categoria : Categoria.values()) {
			stringBuilder.append(Produto.getCategoriaValue(categoria) + "\n");
		}
		return String.valueOf(stringBuilder);
	}
	public int getQtdEstoque() {
		return qtdEstoque;
	}
	public void setQtdEstoque(int qtdEstoque) {
		this.qtdEstoque = qtdEstoque;
	}

	// CRUD para produto
	// CREATE e retornar objeto Produto novo (usado pela Main)
	public static Produto getProdutoNovo() {
		// Setup
		Scanner sc = new Scanner(System.in);
		Categoria categoria = null;
		Produto produtoNovo = null;
		// Prompts
		System.out.println("Digite o código de estoque (SKU): ");
		String sku = sc.nextLine();
		System.out.println("Digite o nome do produto: ");
		String nomeProduto = sc.nextLine();
		System.out.println("Digite a descrição do produto: ");
		String descricao = sc.nextLine();
		System.out.println("Digite o valor do produto (e.g. 12,50): ");
		double valor = sc.nextDouble();
		System.out.println("""

Selecione a categoria do produto:

[1] = Calça
[2] = Saia
[3] = Jaqueta
[4] = Blusa
[5] = Shorts
""");
		int opcaoCategoria = sc.nextInt();
		switch (opcaoCategoria) {
			case 1:
				categoria = Categoria.CALCA;
				break;
			case 2:
				categoria = Categoria.SAIA;
				break;
			case 3:
				categoria = Categoria.JAQUETA;
				break;
			case 4:
				categoria = Categoria.BLUSA;
				break;
			case 5:
				categoria = Categoria.SHORTS;
				break;
		}
		System.out.println("Digite a quantidade em estoque do produto: ");
		int qtdEstoque = sc.nextInt();
		produtoNovo = new Produto(sku, nomeProduto, descricao, valor, categoria, qtdEstoque, true);
		System.out.println(String.format("O produto foi adicionado com sucesso!\n\n%s", produtoNovo.toString()));
		return produtoNovo;
	}
	// READ para selecionar objeto Produto
	public static Produto getProdutoById(int idProduto) {
		for (Produto produto : Produto.getListaProdutos()) {
			if (produto.getIdProduto() == idProduto) {
				return produto;
			}
		}
		System.err.println("ERRO! Produto inexistente");
		return null;
	}
	public static Produto getProdutoByNome(String nomeProduto) {
		for (Produto produto : Produto.getListaProdutos()) {
			if (produto.getNomeProduto().equals(nomeProduto)) {
				return produto;
			}
		}
		System.err.println("ERRO! Produto inexistente");
		return null;
	}
	public static Produto selecionarProduto() {
		// Setup
		Produto produto = null;
		Scanner sc = new Scanner(System.in);
		// Prompts
		System.out.println(Menu.menuSelecionarProduto);
		int opcao = sc.nextInt();
		switch (opcao) {
			case 1:
				System.out.println("Digite o id do produto: ");
				int idProduto = sc.nextInt();
				produto = Produto.getProdutoById(idProduto);
				break;
			case 2:
				System.out.println("Digite o nome do produto: ");
				// HACK: tem que dar esse nextLine() antes pra apagar o \n que sobra do nextInt()
				sc.nextLine();
				String nomeProduto = sc.nextLine();
				produto = Produto.getProdutoByNome(nomeProduto);
				break;
			case 0:
				break;
			default:
				System.err.println("ERRO! Opção inválida");
				break;
		}
		return produto;
	}
	// UPDATE
	public static Produto modificarProduto(Produto produtoAModificar, int opcao) {
		// Setup
		Scanner sc = new Scanner(System.in);
		Produto produtoModificado = null;
		char confirmacao = 'n';
		// Prompts
		switch (opcao) {
			// SKU
			case 1:
				String skuAtual = produtoAModificar.getSku();
				System.out.println("Digite o SKU novo: ");
				String skuNovo = sc.nextLine();
				System.out.println(String.format("""
						
Você tem certeza que deseja modificar o SKU do produto?
	
Produto:
%s
	
SKU atual: %s
SKU novo: %s
	
Se sim, digite 's', se não digite 'n' (CUIDADO: Essa operação não pode ser revertida):""", produtoAModificar, skuAtual, skuNovo));
				confirmacao = sc.next().charAt(0);
				if (Character.toLowerCase(confirmacao) == 's') {
					produtoAModificar.setSku(skuNovo);
					produtoModificado = Produto.getProdutoById(produtoAModificar.getIdProduto());
					return produtoModificado;
				} else {
					return produtoModificado;
				}
			// Nome do produto
			case 2:
				String nomeAtual = produtoAModificar.getNomeProduto();
				System.out.println("Digite o nome novo: ");
				String nomeNovo = sc.nextLine();
				System.out.println(String.format("""
						
Você tem certeza que deseja modificar o nome do produto?

Produto:
%s

Nome atual: %s
Nome novo: %s

Se sim, digite 's', se não digite 'n' (CUIDADO: Essa operação não pode ser revertida):""", produtoAModificar, nomeAtual, nomeNovo));
				confirmacao = sc.next().charAt(0);
				if (Character.toLowerCase(confirmacao) == 's') {
					produtoAModificar.setNomeProduto(nomeNovo);
					produtoModificado = Produto.getProdutoById(produtoAModificar.getIdProduto());
					return produtoModificado;
				} else {
					return produtoModificado;
				}
			// Descrição
			case 3:
				String descricaoAtual = produtoAModificar.getDescricao();
				System.out.println("Digite a descrição nova: ");
				String descricaoNova = sc.nextLine();
				System.out.println(String.format("""
						
Você tem certeza que deseja modificar a descrição do produto?

Produto:
%s

Descrição atual: %s
Descrição nova: %s

Se sim, digite 's', se não digite 'n' (CUIDADO: Essa operação não pode ser revertida):""", produtoAModificar, descricaoAtual, descricaoNova));
				confirmacao = sc.next().charAt(0);
				if (Character.toLowerCase(confirmacao) == 's') {
					produtoAModificar.setDescricao(descricaoNova);
					produtoModificado = Produto.getProdutoById(produtoAModificar.getIdProduto());
					return produtoModificado;
				} else {
					return produtoModificado;
				}
			case 4:
				double valorAtual = produtoAModificar.getValor();
				System.out.println("Digite o valor novo (e.g. 12,50): ");
				double valorNovo = sc.nextDouble();
				System.out.println(String.format("""
						
Você tem certeza que deseja modificar o valor do produto?

Produto:
%s

Valor atual: %s
Valor novo: %s

Se sim, digite 's', se não digite 'n' (CUIDADO: Essa operação não pode ser revertida):""", produtoAModificar, valorAtual, valorNovo));
				confirmacao = sc.next().charAt(0);
				if (Character.toLowerCase(confirmacao) == 's') {
					produtoAModificar.setValor(valorNovo);
					produtoModificado = Produto.getProdutoById(produtoAModificar.getIdProduto());
					return produtoModificado;
				} else {
					return produtoModificado;
				}
			// Categoria
			case 5:
				String categoriaAtual = Produto.getCategoriaValue(produtoAModificar.getCategoria());
				Categoria categoriaNova = null;
				System.out.println("""

Selecione a categoria nova:

[1] = Calça
[2] = Saia
[3] = Jaqueta
[4] = Blusa
[5] = Shorts
""");
				int opcaoCategoriaNova = sc.nextInt();
				switch (opcaoCategoriaNova) {
					case 1:
						categoriaNova = Categoria.CALCA;
						break;
					case 2:
						categoriaNova = Categoria.SAIA;
						break;
					case 3:
						categoriaNova = Categoria.JAQUETA;
						break;
					case 4:
						categoriaNova = Categoria.BLUSA;
						break;
					case 5:
						categoriaNova = Categoria.SHORTS;
						break;
					default:
						System.err.println("ERRO! Opção inválida");
						break;
				}
				System.out.println(String.format("""
						
Você tem certeza que deseja modificar a categoria do produto?

Produto:
%s

Categoria atual: %s
Categoria nova: %s

Se sim, digite 's', se não digite 'n' (CUIDADO: Essa operação não pode ser revertida):""", produtoAModificar, categoriaAtual, categoriaNova));
				confirmacao = sc.next().charAt(0);
				if (Character.toLowerCase(confirmacao) == 's') {
					produtoAModificar.setCategoria(categoriaNova);
					produtoModificado = Produto.getProdutoById(produtoAModificar.getIdProduto());
					return produtoModificado;
				} else {
					return produtoModificado;
				}
			// Quantidade em estoque
			case 6:
				int qtdAtual = produtoAModificar.getQtdEstoque();
				System.out.println("Digite a quantidade nova: ");
				int qtdNova = sc.nextInt();
				System.out.println(String.format("""

Você tem certeza que deseja modificar a quantidade do produto?

Produto:
%s

Quantidade atual: %s
Quantidade nova: %s

Se sim, digite 's', se não digite 'n' (CUIDADO: Essa operação não pode ser revertida):""", produtoAModificar, qtdAtual, qtdNova));
				confirmacao = sc.next().charAt(0);
				if (Character.toLowerCase(confirmacao) == 's') {
					produtoAModificar.setQtdEstoque(qtdNova);
					produtoModificado = Produto.getProdutoById(produtoAModificar.getIdProduto());
					return produtoModificado;
				} else {
					return produtoModificado;
				}
			case 0:
				return produtoModificado;
			default:
				System.err.println("ERRO! Opção inválida");
				return produtoModificado;
		}
	}
	// REMOVE
	public static void removerProduto(Produto produto) {
		// Confirmar operação
		Scanner sc = new Scanner(System.in);
		System.out.println(String.format("""
Você tem certeza que deseja remover o produto a seguir?

Produto:
%s

Se sim, digite 's', se não digite 'n' (CUIDADO: Essa operação não pode ser revertida):""", produto));
		char confirmacao = sc.next().charAt(0);
		if (Character.toLowerCase(confirmacao) == 's') {
			listaProdutos.remove(produto.getIdProduto() - 1);
			System.out.println("Produto foi removido com sucesso!");
		} else {
			System.out.println("Operação cancelada!");
		}
	}
	
	// Métodos para listaProdutos (usados para carregar/atualizar estoque em JsonReader e Main)
	public static ArrayList<Produto> getListaProdutos() {
		return listaProdutos;
	}
	public static void addToListaProdutos(Produto produto) {
		listaProdutos.add(produto);
		count = new AtomicInteger(listaProdutos.size());
	}

	// toString()
	@Override
	public String toString() {
		return String.format("""

Id do produto: '%d'

SKU: %s
Nome: %s
Descrição: %s
Valor: R$ %.2f
Categoria: '%s'
Quantidade em estoque: %d.

""", this.getIdProduto(), this.getSku(), this.getNomeProduto(), this.getDescricao(), this.getValor(), Produto.getCategoriaValue(categoria), getQtdEstoque());
	}
}