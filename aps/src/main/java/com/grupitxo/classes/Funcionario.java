package com.grupitxo.classes;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import com.grupitxo.enums.Cargo;

public class Funcionario extends Pessoa {
	private final int idFuncionario;
	private Cargo cargo;
	private double salario;
	
	// Construtor 1: PASSANDO ID, usado pelo READ do DbAdapter pra retornar um objeto Funcionario com o ID criado pelo BD
	public Funcionario(int idFuncionario, String nome, String cpf, String telefone, String email, String preferenciaComunicacao, String endereco, String aniversario, String genero, Cargo cargo, double salario) {
		super(nome, cpf, telefone, email, preferenciaComunicacao, endereco, aniversario, genero);
		
		// pra proteger contra um NullPointerException
		if (cargo == null) {
        throw new IllegalArgumentException("ERRO: O cargo do funcionário não pode ser nulo!");
		}

		this.idFuncionario = idFuncionario;
		this.cargo = cargo;
		this.salario = salario;
	}
	// Construtor 2: SEM ID, usado pelo CREATE da ViewFactory
	public Funcionario(String nome, String cpf, String telefone, String email, String preferenciaComunicacao, String endereco, String aniversario, String genero, Cargo cargo, double salario) {
        // O 'this()' chama o construtor de cima
		// Passamos 0 como ID provisório (como é final, precisa disso). O Banco de Dados vai ignorar esse 0 e criar o ID real.
        this(0, nome, cpf, telefone, email, preferenciaComunicacao, endereco, aniversario, genero, cargo, salario);
    }

	// Getters/setters
	public int getIdFuncionario() {
		return idFuncionario;
	}
	public Cargo getCargo() {
		return cargo;
	}
	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
	// CHECK: precisa disso? pode só chamar getCargo().toString()
	public static String getCargoValue(Cargo cargo) {
		switch (cargo) {
			case VENDEDOR:
				return "Vendedor";
			case CAIXA:
				return "Caixa";
			case ESTOQUISTA:
				return "Estoquista";
			case SOCIO:
				return "Sócio";
			default:
				return "ERRO! Cargo ainda não registrado";
		}
	}
	public double getSalario() {
		return salario;
	}
	public void setSalario(double salario) {
		this.salario = salario;
	}
	public static String getFuncionarios() {
		StringBuilder funcionarios = new StringBuilder();
		for (Funcionario funcionario: listaFuncionarios) {
			funcionarios.append(funcionario.toString());
		}
		if (funcionarios.isEmpty()) {
			return "ERRO! Nenhum funcionário foi registrado ainda";
		}
		return String.valueOf(funcionarios);
	}
	
	// CRUD para Funcionario
	// CREATE e retornar objeto Funcionario novo
	public static Funcionario getFuncionarioNovo() {
		// Setup
		Scanner sc = new Scanner(System.in);
		Funcionario funcionario = null;
		Cargo cargo = null;
		// Prompts
		System.out.println("Digite o nome do funcionário: ");
		String nome = sc.nextLine();
		System.out.println("Digite o cpf do funcionário: ");
		String cpf = sc.nextLine();
		System.out.println("Digite o telefone do funcionário: ");
		String telefone = sc.nextLine();
		System.out.println("Digite o email do funcionário: ");
		String email = sc.nextLine();
		System.out.println("Digite a preferência de comunicação do funcionário: ");
		String preferenciaComunicacao = sc.nextLine();
		System.out.println("Digite o endereco residencial do funcionário: ");
		String endereco = sc.nextLine();
		System.out.println("Digite o aniversário do funcionário (e.g. 12/12/2012): ");
		String aniversario = sc.nextLine();
		System.out.println("Digite o gênero do funcionário: ");
		String genero = sc.nextLine();
		System.out.println("""

Escolha o cargo do funcionário:

[1] = Vendedor
[2] = Caixa
[3] = Estoquista
[4] = Sócio
""");
		int opcaoCargo = sc.nextInt();
		switch (opcaoCargo) {
			case 1:
				cargo = Cargo.VENDEDOR;
				break;
			case 2:
				cargo = Cargo.CAIXA;
				break;
			case 3:
				cargo = Cargo.ESTOQUISTA;
				break;
			case 4:
				cargo = Cargo.SOCIO;
				break;
			default:
				System.err.println("ERRO! Opção inválida");
				break;
		}
		System.out.println("Digite o salário do funcionário (e.g. 1459,90): ");
		double salario = sc.nextDouble();
		try {
			funcionario = new Funcionario(nome, cpf, telefone, email, preferenciaComunicacao, endereco, aniversario, genero, cargo, salario, true);			
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
			return funcionario;
		}
		System.out.println("Funcionário registrado com sucesso!");
		return funcionario;
	}
	// READ para selecionar objeto Funcionario
	public static Funcionario getFuncionarioById(int idFuncionario) {
		for (Funcionario funcionario : Funcionario.getListaFuncionarios()) {
			if (funcionario.getIdFuncionario() == idFuncionario) {
				return funcionario;
			}
		}
		System.err.println("ERRO! Funcionário inexistente");
		return null;
	}
	public static Funcionario getFuncionarioByNome(String nomeFuncionario) {
		for (Funcionario funcionario : Funcionario.getListaFuncionarios()) {
			if (funcionario.getNome().equals(nomeFuncionario)) {
				return funcionario;
			}
		}
		System.err.println("ERRO! Funcionário inexistente");
		return null;
	}
	// Seleção de um funcionário através da UI
	// TODO: isso aqui é coisa pro ViewFactory, eu acho
	public static Funcionario selecionarFuncionario() {
		String menuSelecionarFuncionario = """

[1] = Procurar funcionário por seu id
[2] = Procurar funcionário por seu nome

[0] = Retornar ao menu anterior
""";
		System.out.println(menuSelecionarFuncionario);
		Scanner sc = new Scanner(System.in);
		int opcao = sc.nextInt();
		switch (opcao) {
			case 1:
				System.out.println("Digite o id do funcionário:");
				int idFuncionario = sc.nextInt();
				return Funcionario.getFuncionarioById(idFuncionario);
			case 2:
				System.out.println("Digite o nome do funcionário:");
				// HACK: tem que dar esse nextLine() antes pra apagar o \n que sobra do nextInt()
				sc.nextLine();
				String nomeFuncionario = sc.nextLine();
				return Funcionario.getFuncionarioByNome(nomeFuncionario);
			case 0:
				break;
			default:
				System.err.println("ERRO! Opção inválida");
				break;
		}
		return null;
	}
	// UPDATE
	public static Funcionario modificarFuncionario(Funcionario funcionarioAModificar, int opcao) {
		// Setup
		Scanner sc = new Scanner(System.in);
		Funcionario funcionarioModificado = null;
		char confirmacao = 'n';
		// Prompts
		switch (opcao) {
			// Nome
			case 1:
				String nomeAtual = funcionarioAModificar.getNome();
				System.out.println("Digite o nome novo:");
				String nomeNovo = sc.nextLine();
				System.out.println(String.format("""
						
Você tem certeza que deseja modificar o nome do funcionário?
	
Funcionário:
%s
	
Nome atual: %s
Nome novo: %s
	
Se sim, digite 's', se não digite 'n' (CUIDADO: Essa operação não pode ser revertida):""", funcionarioAModificar, nomeAtual, nomeNovo));
				confirmacao = sc.next().charAt(0);
				if (Character.toLowerCase(confirmacao) == 's') {
					funcionarioAModificar.setNome(nomeNovo);
					funcionarioModificado = Funcionario.getFuncionarioById(funcionarioAModificar.getIdFuncionario());
					return funcionarioModificado;
				} else {
					return funcionarioModificado;
				}
			// CPF
			case 2:
				String cpfAtual = funcionarioAModificar.getCpf();
				System.out.println("Digite o CPF novo:");
				String cpfNovo = sc.nextLine();
				System.out.println(String.format("""
						
Você tem certeza que deseja modificar o CPF do funcionário?
	
Funcionário:
%s
	
CPF atual: %s
CPF novo: %s
	
Se sim, digite 's', se não digite 'n' (CUIDADO: Essa operação não pode ser revertida):""", funcionarioAModificar, cpfAtual, cpfNovo));
				confirmacao = sc.next().charAt(0);
				if (Character.toLowerCase(confirmacao) == 's') {
					funcionarioAModificar.setCpf(cpfNovo);
					funcionarioModificado = Funcionario.getFuncionarioById(funcionarioAModificar.getIdFuncionario());
					return funcionarioModificado;
				} else {
					return funcionarioModificado;
				}
			// Telefone
			case 3:
				String telefoneAtual = funcionarioAModificar.getTelefone();
				System.out.println("Digite o telefone novo:");
				String telefoneNovo = sc.nextLine();
				System.out.println(String.format("""
						
Você tem certeza que deseja modificar o telefone do funcionário?
	
Funcionário:
%s
	
Telefone atual: %s
Telefone novo: %s
	
Se sim, digite 's', se não digite 'n' (CUIDADO: Essa operação não pode ser revertida):""", funcionarioAModificar, telefoneAtual, telefoneNovo));
				confirmacao = sc.next().charAt(0);
				if (Character.toLowerCase(confirmacao) == 's') {
					funcionarioAModificar.setTelefone(telefoneNovo);
					funcionarioModificado = Funcionario.getFuncionarioById(funcionarioAModificar.getIdFuncionario());
					return funcionarioModificado;
				} else {
					return funcionarioModificado;
				}
			// Email
			case 4:
				String emailAtual = funcionarioAModificar.getEmail();
				System.out.println("Digite o email novo:");
				String emailNovo = sc.nextLine();
				System.out.println(String.format("""
						
Você tem certeza que deseja modificar o email do funcionário?
	
Funcionário:
%s
	
Email atual: %s
Email novo: %s
	
Se sim, digite 's', se não digite 'n' (CUIDADO: Essa operação não pode ser revertida):""", funcionarioAModificar, emailAtual, emailNovo));
				confirmacao = sc.next().charAt(0);
				if (Character.toLowerCase(confirmacao) == 's') {
					funcionarioAModificar.setEmail(emailNovo);
					funcionarioModificado = Funcionario.getFuncionarioById(funcionarioAModificar.getIdFuncionario());
					return funcionarioModificado;
				} else {
					return funcionarioModificado;
				}
			// Preferência de comunicação
			case 5:
				String preferenciaComunicacaoAtual = funcionarioAModificar.getPreferenciaComunicacao();
				System.out.println("Digite a preferência de comunicação nova:");
				String preferenciaComunicacaoNova = sc.nextLine();
				System.out.println(String.format("""
						
Você tem certeza que deseja modificar a preferência de comunicação do funcionário?
	
Funcionário:
%s
	
Preferência de comunicacao atual: %s
Preferência de comunicacao nova: %s
	
Se sim, digite 's', se não digite 'n' (CUIDADO: Essa operação não pode ser revertida):""", funcionarioAModificar, preferenciaComunicacaoAtual, preferenciaComunicacaoNova));
				confirmacao = sc.next().charAt(0);
				if (Character.toLowerCase(confirmacao) == 's') {
					funcionarioAModificar.setPreferenciaComunicacao(preferenciaComunicacaoNova);
					funcionarioModificado = Funcionario.getFuncionarioById(funcionarioAModificar.getIdFuncionario());
					return funcionarioModificado;
				} else {
					return funcionarioModificado;
				}
			// Endereço
			case 6:
				String enderecoAtual = funcionarioAModificar.getEndereco();
				System.out.println("Digite o endereço novo:");
				String enderecoNovo = sc.nextLine();
				System.out.println(String.format("""
						
Você tem certeza que deseja modificar o endereço do funcionário?
	
Funcionário:
%s
	
Endereço atual: %s
Endereço novo: %s
	
Se sim, digite 's', se não digite 'n' (CUIDADO: Essa operação não pode ser revertida):""", funcionarioAModificar, enderecoAtual, enderecoNovo));
				confirmacao = sc.next().charAt(0);
				if (Character.toLowerCase(confirmacao) == 's') {
					funcionarioAModificar.setEndereco(enderecoNovo);
					funcionarioModificado = Funcionario.getFuncionarioById(funcionarioAModificar.getIdFuncionario());
					return funcionarioModificado;
				} else {
					return funcionarioModificado;
				}
			// Aniversário
			case 7:
				String aniversarioAtual = funcionarioAModificar.getAniversario();
				System.out.println("Digite o aniversário novo (e.g. 31/12/2020):");
				String aniversarioNovo = sc.nextLine();
				System.out.println(String.format("""
						
Você tem certeza que deseja modificar o aniversário do funcionário?
	
Funcionário:
%s
	
Aniversário atual: %s
Aniversário novo: %s
	
Se sim, digite 's', se não digite 'n' (CUIDADO: Essa operação não pode ser revertida):""", funcionarioAModificar, aniversarioAtual, aniversarioNovo));
				confirmacao = sc.next().charAt(0);
				if (Character.toLowerCase(confirmacao) == 's') {
					funcionarioAModificar.setAniversario(aniversarioNovo);
					funcionarioModificado = Funcionario.getFuncionarioById(funcionarioAModificar.getIdFuncionario());
					return funcionarioModificado;
				} else {
					return funcionarioModificado;
				}
			// Gênero
			case 8:
				String generoAtual = funcionarioAModificar.getGenero();
				System.out.println("Digite o gênero novo:");
				String generoNovo = sc.nextLine();
				System.out.println(String.format("""
						
Você tem certeza que deseja modificar o gênero do funcionário?
	
Funcionário:
%s
	
Gênero atual: %s
Gênero novo: %s
	
Se sim, digite 's', se não digite 'n' (CUIDADO: Essa operação não pode ser revertida):""", funcionarioAModificar, generoAtual, generoNovo));
				confirmacao = sc.next().charAt(0);
				if (Character.toLowerCase(confirmacao) == 's') {
					funcionarioAModificar.setGenero(generoNovo);
					funcionarioModificado = Funcionario.getFuncionarioById(funcionarioAModificar.getIdFuncionario());
					return funcionarioModificado;
				} else {
					return funcionarioModificado;
				}
			// Cargo
			case 9:
				Cargo cargoAtual = funcionarioAModificar.getCargo();
				Cargo cargoNovo = null;
				System.out.println("""

Escolha o cargo do funcionário:

[1] = Vendedor
[2] = Caixa
[3] = Estoquista
[4] = Sócio
""");
				int opcaoCargo = sc.nextInt();
				switch (opcaoCargo) {
					case 1:
						cargoNovo = Cargo.VENDEDOR;
						break;
					case 2:
						cargoNovo = Cargo.CAIXA;
						break;
					case 3:
						cargoNovo = Cargo.ESTOQUISTA;
						break;
					case 4:
						cargoNovo = Cargo.SOCIO;
						break;
					default:
						System.err.println("ERRO! Opção inválida");
						break;
				}
				System.out.println(String.format("""
						
Você tem certeza que deseja modificar o cargo do funcionário?
	
Funcionário:
%s
	
Cargo atual: %s
Cargo novo: %s
	
Se sim, digite 's', se não digite 'n' (CUIDADO: Essa operação não pode ser revertida):""", funcionarioAModificar, cargoAtual, cargoNovo));
				confirmacao = sc.next().charAt(0);
				if (Character.toLowerCase(confirmacao) == 's') {
					funcionarioAModificar.setCargo(cargoNovo);
					funcionarioModificado = Funcionario.getFuncionarioById(funcionarioAModificar.getIdFuncionario());
					return funcionarioModificado;
				} else {
					return funcionarioModificado;
				}
			// Salário
			case 10:
				double salarioAtual = funcionarioAModificar.getSalario();
				System.out.println("Digite o salário novo (e.g. 1459,90):");
				double salarioNovo = sc.nextDouble();
				System.out.println(String.format("""
						
Você tem certeza que deseja modificar o salário do funcionário?
	
Funcionário:
%s
	
Salário atual: %s
Salário novo: %s
	
Se sim, digite 's', se não digite 'n' (CUIDADO: Essa operação não pode ser revertida):""", funcionarioAModificar, salarioAtual, salarioNovo));
				confirmacao = sc.next().charAt(0);
				if (Character.toLowerCase(confirmacao) == 's') {
					funcionarioAModificar.setSalario(salarioNovo);
					funcionarioModificado = Funcionario.getFuncionarioById(funcionarioAModificar.getIdFuncionario());
					return funcionarioModificado;
				} else {
					return funcionarioModificado;
				}
			case 0:
				return funcionarioModificado;
			default:
				System.err.println("ERRO! Opção inválida");
				return funcionarioModificado;
		}
	}
	// REMOVE
	public static void removerFuncionario(Funcionario funcionario) {
		// Confirmar operação
		Scanner sc = new Scanner(System.in);
		System.out.println(String.format("""
				
Você tem certeza que deseja remover o funcionário a seguir?

Funcionário:
%s

Se sim, digite 's', se não digite 'n' (CUIDADO: Essa operação não pode ser revertida):""", funcionario));
		char confirmacao = sc.next().charAt(0);
		if (Character.toLowerCase(confirmacao) == 's') {
			listaFuncionarios.remove(funcionario.getIdFuncionario() - 1);
			System.out.println("Funcionário foi removido com sucesso!");
		} else {
			System.out.println("Operação cancelada!");
		}
	}
	
	// Usado por JsonReader
	public static ArrayList<Funcionario> getListaFuncionarios() {
		return listaFuncionarios;
	}
	public static void addToListaFuncionarios(Funcionario funcionario) {
		listaFuncionarios.add(funcionario);
		count = new AtomicInteger(listaFuncionarios.size());
	}
	
	// toString()
	@Override
	public String toString() {
		return String.format("""
				
Id do funcionário: '%d'

Nome: %s
CPF: %s
Telefone: %s
Email: %s
Preferência de comunicação: %s
Endereço: %s
Aniversário: %s
Idade: %d
Gênero: %s
Cargo: '%s'
Salário: 'R$ %.2f'

""", this.getIdFuncionario(), this.getNome(), this.getCpf(), this.getTelefone(), this.getEmail(), this.getPreferenciaComunicacao(), this.getEndereco(), this.getAniversario(), this.getIdade(), this.getGenero(), Funcionario.getCargoValue(cargo), this.getSalario());
	}
}
