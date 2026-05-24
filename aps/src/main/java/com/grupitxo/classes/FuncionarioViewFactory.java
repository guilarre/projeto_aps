package com.grupitxo.classes;

import java.util.Scanner;

import com.grupitxo.enums.Cargo;

public class FuncionarioViewFactory {
    public Funcionario criarFuncionarioNovo(Scanner sc) {
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

        // Limpa o buffer do Scanner (importante após ler nextInt ou nextDouble)
        if (sc.hasNextLine()) {
            sc.nextLine();
        }

        System.out.println("Funcionário criado em memória com sucesso!");
        
        // Retorna o objeto limpo. Ele não é salvo no banco AQUI. Isso é papel do Controller.
        try {
            Funcionario funcionario = new Funcionario(nome, cpf, telefone, email, preferenciaComunicacao, endereco, aniversario, genero, cargo, salario);
            System.out.println("Funcionário criado em memória com sucesso!");
            return funcionario;
        } catch (IllegalArgumentException e) { //Captura o erro do construtor (ex: Cargo nulo, CPF inválido, etc)
            System.err.println("ERRO ao criar funcionário: " + e.getMessage());
            return null; 
        }
    }
}
