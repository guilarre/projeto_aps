package com.grupitxo.classes;

public class Cliente extends Pessoa {
	private final int idCliente;
	
	// Construtor 1: PASSANDO ID
	public Cliente(int idCliente, String nome, String cpf, String telefone, String email, String preferenciaComunicacao, String endereco, String aniversario, String genero) {
		super(nome, cpf, telefone, email, preferenciaComunicacao, endereco, aniversario, genero);

		this.idCliente = idCliente;
	}

	// Construtor 2: SEM ID
	public Cliente(String nome, String cpf, String telefone, String email, String preferenciaComunicacao, String endereco, String aniversario, String genero) {
		this(0, nome, cpf, telefone, email, preferenciaComunicacao, endereco, aniversario, genero);
	}
	
	// Getters/setters
	public int getIdCliente() {
		return idCliente;
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