package com.grupitxo.classes;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Cliente extends Pessoa {
	private static ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();
	// Contador para autoincrementar idCliente
	private static AtomicInteger count = new AtomicInteger(0);
	private final int idCliente;
	
	public Cliente(String nome, String cpf, String telefone, String email, String preferenciaComunicacao, String endereco, String aniversario, String genero, boolean autoAdd) {
		super(nome, cpf, telefone, email, preferenciaComunicacao, endereco, aniversario, genero);
		idCliente = count.incrementAndGet();
		if (autoAdd) {
			listaClientes.add(this);			
		}
	}
	
	// Getters/setters
	public int getIdCliente() {
		return idCliente;
	}
	public static String getClientes() {
		StringBuilder clientes = new StringBuilder();
		for (Cliente cliente : listaClientes) {
			clientes.append(cliente.toString());			
		}
		if (clientes.isEmpty()) {
			return "ERRO! Nenhum cliente foi registrado ainda";
		}
		return String.valueOf(clientes);
	}
	
	// CRUD para Cliente
	// CREATE e retornar objeto Cliente novo
		public static Cliente getClienteNovo() {
			// Setup
			Scanner sc = new Scanner(System.in);
			Cliente cliente = null;
			// Prompts
			System.out.println("Digite o nome do cliente: ");
			String nome = sc.nextLine();
			System.out.println("Digite o cpf do cliente: ");
			String cpf = sc.nextLine();
			System.out.println("Digite o telefone do cliente: ");
			String telefone = sc.nextLine();
			System.out.println("Digite o email do cliente: ");
			String email = sc.nextLine();
			System.out.println("Digite a preferência de comunicação do cliente: ");
			String preferenciaComunicacao = sc.nextLine();
			System.out.println("Digite o endereco residencial do cliente: ");
			String endereco = sc.nextLine();
			System.out.println("Digite o aniversário do cliente (e.g. 12/12/2012): ");
			String aniversario = sc.nextLine();
			System.out.println("Digite o gênero do cliente: ");
			String genero = sc.nextLine();
			try {
				cliente = new Cliente(nome, cpf, telefone, email, preferenciaComunicacao, endereco, aniversario, genero, true);			
			} catch (IllegalArgumentException e) {
				System.err.println(e.getMessage());
				return cliente;
			}
			System.out.println("Cliente registrado com sucesso!");
			return cliente;
		}
	// READ para selecionar objeto Cliente
	public static Cliente getClienteById(int idCliente) {
		for (Cliente cliente : Cliente.getListaClientes()) {
			if (cliente.getIdCliente() == idCliente) {
				return cliente;
			}
		}
		System.err.println("ERRO! Cliente inexistente");
		return null;
	}
	public static Cliente getClienteByNome(String nomeClientePesquisa) {
		for (Cliente cliente : Cliente.getListaClientes()) {
			if (cliente.getNome().equals(nomeClientePesquisa)) {
				return cliente;
			}
		}
		System.err.println("ERRO! Cliente inexistente");
		return null;
	}
	public static Cliente selecionarCliente() {
		// Setup
		Cliente cliente = null;
		Scanner sc = new Scanner(System.in);
		// Prompts
		System.out.println(StringsMenu.menuSelecionarCliente);
		int opcao = sc.nextInt();
		switch (opcao) {
			case 1:
				System.out.println("Digite o id do cliente: ");
				int idCliente = sc.nextInt();
				cliente = Cliente.getClienteById(idCliente);
				break;
			case 2:
				System.out.println("Digite o nome do cliente: ");
				// HACK: tem que dar esse nextLine() antes pra apagar o \n que sobra do nextInt()
				sc.nextLine();
				String nomeCliente = sc.nextLine();
				cliente = Cliente.getClienteByNome(nomeCliente);
				break;
			case 0:
				break;
			default:
				System.err.println("ERRO! Opção inválida");
				break;
		}
		return cliente;
	}
	// UPDATE
	public static Cliente modificarCliente(Cliente clienteAModificar, int opcao) {
		// Setup
		Scanner sc = new Scanner(System.in);
		Cliente clienteModificado = null;
		char confirmacao = 'n';
		// Prompts
		switch (opcao) {
			// Nome
			case 1:
				String nomeAtual = clienteAModificar.getNome();
				System.out.println("Digite o nome novo: ");
				String nomeNovo = sc.nextLine();
				System.out.println(String.format("""
						
Você tem certeza que deseja modificar o nome do cliente?
	
Cliente:
%s
	
Nome atual: %s
Nome novo: %s
	
Se sim, digite 's', se não digite 'n' (CUIDADO: Essa operação não pode ser revertida):""", clienteAModificar, nomeAtual, nomeNovo));
				confirmacao = sc.next().charAt(0);
				if (Character.toLowerCase(confirmacao) == 's') {
					clienteAModificar.setNome(nomeNovo);
					clienteModificado = Cliente.getClienteById(clienteAModificar.getIdCliente());
					return clienteModificado;
				} else {
					return clienteModificado;
				}
			// CPF
			case 2:
				String cpfAtual = clienteAModificar.getCpf();
				System.out.println("Digite o CPF novo: ");
				String cpfNovo = sc.nextLine();
				System.out.println(String.format("""
						
Você tem certeza que deseja modificar o CPF do cliente?
	
Cliente:
%s
	
CPF atual: %s
CPF novo: %s
	
Se sim, digite 's', se não digite 'n' (CUIDADO: Essa operação não pode ser revertida):""", clienteAModificar, cpfAtual, cpfNovo));
				confirmacao = sc.next().charAt(0);
				if (Character.toLowerCase(confirmacao) == 's') {
					clienteAModificar.setCpf(cpfNovo);
					clienteModificado = Cliente.getClienteById(clienteAModificar.getIdCliente());
					return clienteModificado;
				} else {
					return clienteModificado;
				}
			// Telefone
			case 3:
				String telefoneAtual = clienteAModificar.getTelefone();
				System.out.println("Digite o telefone novo: ");
				String telefoneNovo = sc.nextLine();
				System.out.println(String.format("""
						
Você tem certeza que deseja modificar o telefone do cliente?
	
Cliente:
%s
	
Telefone atual: %s
Telefone novo: %s
	
Se sim, digite 's', se não digite 'n' (CUIDADO: Essa operação não pode ser revertida):""", clienteAModificar, telefoneAtual, telefoneNovo));
				confirmacao = sc.next().charAt(0);
				if (Character.toLowerCase(confirmacao) == 's') {
					clienteAModificar.setTelefone(telefoneNovo);
					clienteModificado = Cliente.getClienteById(clienteAModificar.getIdCliente());
					return clienteModificado;
				} else {
					return clienteModificado;
				}
			// Email
			case 4:
				String emailAtual = clienteAModificar.getEmail();
				System.out.println("Digite o email novo: ");
				String emailNovo = sc.nextLine();
				System.out.println(String.format("""
						
Você tem certeza que deseja modificar o email do cliente?
	
Cliente:
%s
	
Email atual: %s
Email novo: %s
	
Se sim, digite 's', se não digite 'n' (CUIDADO: Essa operação não pode ser revertida):""", clienteAModificar, emailAtual, emailNovo));
				confirmacao = sc.next().charAt(0);
				if (Character.toLowerCase(confirmacao) == 's') {
					clienteAModificar.setEmail(emailNovo);
					clienteModificado = Cliente.getClienteById(clienteAModificar.getIdCliente());
					return clienteModificado;
				} else {
					return clienteModificado;
				}
			// Preferência de comunicação
			case 5:
				String preferenciaComunicacaoAtual = clienteAModificar.getPreferenciaComunicacao();
				System.out.println("Digite a preferência de comunicação nova: ");
				String preferenciaComunicacaoNova = sc.nextLine();
				System.out.println(String.format("""
						
Você tem certeza que deseja modificar a preferência de comunicação do cliente?
	
Cliente:
%s
	
Preferência de comunicacao atual: %s
Preferência de comunicacao nova: %s
	
Se sim, digite 's', se não digite 'n' (CUIDADO: Essa operação não pode ser revertida):""", clienteAModificar, preferenciaComunicacaoAtual, preferenciaComunicacaoNova));
				confirmacao = sc.next().charAt(0);
				if (Character.toLowerCase(confirmacao) == 's') {
					clienteAModificar.setPreferenciaComunicacao(preferenciaComunicacaoNova);
					clienteModificado = Cliente.getClienteById(clienteAModificar.getIdCliente());
					return clienteModificado;
				} else {
					return clienteModificado;
				}
			// Endereço
			case 6:
				String enderecoAtual = clienteAModificar.getEndereco();
				System.out.println("Digite o endereço novo: ");
				String enderecoNovo = sc.nextLine();
				System.out.println(String.format("""
						
Você tem certeza que deseja modificar o endereço do cliente?
	
Cliente:
%s
	
Endereço atual: %s
Endereço novo: %s
	
Se sim, digite 's', se não digite 'n' (CUIDADO: Essa operação não pode ser revertida):""", clienteAModificar, enderecoAtual, enderecoNovo));
				confirmacao = sc.next().charAt(0);
				if (Character.toLowerCase(confirmacao) == 's') {
					clienteAModificar.setEndereco(enderecoNovo);
					clienteModificado = Cliente.getClienteById(clienteAModificar.getIdCliente());
					return clienteModificado;
				} else {
					return clienteModificado;
				}
			// Aniversário
			case 7:
				String aniversarioAtual = clienteAModificar.getAniversario();
				System.out.println("Digite o aniversário novo (e.g. 31/12/2020): ");
				String aniversarioNovo = sc.nextLine();
				System.out.println(String.format("""
						
Você tem certeza que deseja modificar o aniversário do cliente?
	
Cliente:
%s
	
Aniversário atual: %s
Aniversário novo: %s
	
Se sim, digite 's', se não digite 'n' (CUIDADO: Essa operação não pode ser revertida):""", clienteAModificar, aniversarioAtual, aniversarioNovo));
				confirmacao = sc.next().charAt(0);
				if (Character.toLowerCase(confirmacao) == 's') {
					clienteAModificar.setAniversario(aniversarioNovo);
					clienteModificado = Cliente.getClienteById(clienteAModificar.getIdCliente());
					return clienteModificado;
				} else {
					return clienteModificado;
				}
			// Gênero
			case 8:
				String generoAtual = clienteAModificar.getGenero();
				System.out.println("Digite o gênero novo: ");
				String generoNovo = sc.nextLine();
				System.out.println(String.format("""
						
Você tem certeza que deseja modificar o gênero do cliente?
	
Cliente:
%s
	
Gênero atual: %s
Gênero novo: %s
	
Se sim, digite 's', se não digite 'n' (CUIDADO: Essa operação não pode ser revertida):""", clienteAModificar, generoAtual, generoNovo));
				confirmacao = sc.next().charAt(0);
				if (Character.toLowerCase(confirmacao) == 's') {
					clienteAModificar.setGenero(generoNovo);
					clienteModificado = Cliente.getClienteById(clienteAModificar.getIdCliente());
					return clienteModificado;
				} else {
					return clienteModificado;
				}
			case 0:
				return clienteModificado;
			default:
				System.err.println("ERRO! Opção inválida");
				return clienteModificado;
		}
	}
	//REMOVE
	public static void removerCliente(Cliente cliente) {
		// Confirmar operação
		Scanner sc = new Scanner(System.in);
		System.out.println(String.format("""
				
Você tem certeza que deseja remover o cliente a seguir?

Cliente:
%s

Se sim, digite 's', se não digite 'n' (CUIDADO: Essa operação não pode ser revertida):""", cliente));
		char confirmacao = sc.next().charAt(0);
		if (Character.toLowerCase(confirmacao) == 's') {
			listaClientes.remove(cliente.getIdCliente() - 1);
			System.out.println("Cliente foi removido com sucesso!");
		} else {
			System.out.println("Operação cancelada!");
		}
	}
	
	// Usado por JsonReader
	public static ArrayList<Cliente> getListaClientes() {
		return listaClientes;
	}
	public static void addToListaClientes(Cliente cliente) {
		listaClientes.add(cliente);
		count = new AtomicInteger(listaClientes.size());
	}
	
	// toString()
	@Override
	public String toString() {
		return String.format("""

Id do cliente: '%d'

Nome: %s
CPF: %s
Telefone: %s
Email: %s
Preferência de comunicação: %s
Endereço: %s
Aniversário: %s
Idade: %d
Gênero: %s

""", this.getIdCliente(), this.getNome(), this.getCpf(), this.getTelefone(), this.getEmail(), this.getPreferenciaComunicacao(), this.getEndereco(), this.getAniversario(), this.getIdade(), this.getGenero());
	}
}