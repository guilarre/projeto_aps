package com.grupitxo.classes;

import java.util.Scanner;

public class ClienteViewFactory {
    public Cliente criarClienteNovo(Scanner sc) {
        // Limpa o buffer do Scanner (importante após ler nextInt ou nextDouble)
        if (sc.hasNextLine()) {
			sc.nextLine();
        }
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

        // Limpa o buffer do Scanner (importante após ler nextInt ou nextDouble)
        if (sc.hasNextLine()) {
            sc.nextLine();
        }

        System.out.println("Cliente criado em memória com sucesso!");
        
        // Retorna o objeto limpo. Ele não é salvo no banco AQUI. Isso é papel do Controller.
        try {
            Cliente cliente = new Cliente(nome, cpf, telefone, email, preferenciaComunicacao, endereco, aniversario, genero);
            System.out.println("Cliente criado em memória com sucesso!");
            return cliente;
        } catch (IllegalArgumentException e) {
            System.err.println("ERRO ao criar cliente: " + e.getMessage());
            return null; 
        }
    }
}
