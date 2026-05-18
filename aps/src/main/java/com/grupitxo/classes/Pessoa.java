package com.grupitxo.classes;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public abstract class Pessoa implements Validavel {
	private String nome;
	private String cpf;
	private String telefone;
	private String email;
	private String preferenciaComunicacao;
	private String endereco;
	private String aniversario;
	private String genero;
	
	// Construtor parametrizado (força subclasses a implementarem esse construtor)
	public Pessoa(String nome, String cpf, String telefone, String email, String preferenciaComunicacao,
			String endereco, String aniversario, String genero) {
		this.nome = nome;
		if (validarCpf(cpf) == false) {
			throw new IllegalArgumentException("ERRO: CPF inválido!");
		}
		this.cpf = cpf;
		if (validarTelefone(telefone) == false) {
			throw new IllegalArgumentException("ERRO: Telefone inválido! Escreva o DDD + número (e.g. 00-000000000).");
		}
		this.telefone = telefone;
		if (validarEmail(email) == false) {
			throw new IllegalArgumentException("ERRO: Email inválido!");
		}
		this.email = email;
		this.preferenciaComunicacao = preferenciaComunicacao;
		this.endereco = endereco;
		if (validarAniversario(aniversario) == false) {
			throw new IllegalArgumentException("ERRO: Aniversário inválido! Usar o formato: 31/12/2020");
		}
		this.aniversario = aniversario;
		this.genero = genero;
	}
	
	// Getters/setters
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPreferenciaComunicacao() {
		return preferenciaComunicacao;
	}
	public void setPreferenciaComunicacao(String preferenciaComunicacao) {
		this.preferenciaComunicacao = preferenciaComunicacao;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getAniversario() {
		return aniversario;
	}
	public void setAniversario(String aniversario) {
		if (validarAniversario(aniversario) == false) {
			throw new IllegalArgumentException("ERRO: Aniversário inválido! Usar o formato: 31/12/2020");
		}
		this.aniversario = aniversario;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	
	// Validador básico de CPF (sem validação do DV)
	@Override
	public boolean validarCpf(String cpf) {
		// Retira tudo que não é dígito
		cpf = cpf.replaceAll("[^0-9]", "");
		// Checa se é nulo/vazio, diferente de 11 ou se é uma sequência de números repetidos
		if (cpf == null || cpf.isEmpty() || cpf.length() != 11 || cpf.matches("(.)\\1{10,}")) {
            return false;
        }
        return true;
	}
	
	// Validador de telefone
	@Override
	public boolean validarTelefone(String telefone) {
		// Retira tudo que não é dígito
		telefone = telefone.replaceAll("[^0-9]", "");
		
		// Checa se telefone contém 10 ou 11 dígitos
		if (telefone.length() == 10 || telefone.length() == 11) {
			return true;
		}
		return false;
	}
	
	// Validador de email
	@Override
	public boolean validarEmail(String email) {
		// Verifica se é nulo/vazio
		if (email == null || email.isEmpty()) {
			return false;
		}
		// Verifica se contém @
		return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
	}
	
	// Validador de aniversário
	@Override
	public boolean validarAniversario(String aniversario) {
		// Tenta realizar parse para transformar em objeto LocalDate
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate.parse(aniversario, formatter);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}
	
	// Calculadora de idade
	public int calcularIdade(String aniversario) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate aniversarioData = LocalDate.parse(aniversario, formatter);
		int anoNascimento = aniversarioData.getYear();
		return LocalDate.now().getYear() - anoNascimento;
	}
	
	// getIdade
	public int getIdade() {
		return calcularIdade(this.aniversario);
	}
}