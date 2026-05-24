package com.grupitxo.classes;

import java.util.ArrayList;
import java.util.Scanner;

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
	public double getSalario() {
		return salario;
	}
	public void setSalario(double salario) {
		this.salario = salario;
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

""", this.getIdFuncionario(), this.getNome(), this.getCpf(), this.getTelefone(), this.getEmail(), this.getPreferenciaComunicacao(), this.getEndereco(), this.getAniversario(), this.getIdade(), this.getGenero(), this.getCargo().toString(), this.getSalario());
	}
}
