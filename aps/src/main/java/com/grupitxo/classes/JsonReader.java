package com.grupitxo.classes;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import com.grupitxo.typeadapter.ClienteTypeAdapter;
import com.grupitxo.typeadapter.CompraTypeAdapter;
import com.grupitxo.typeadapter.FuncionarioTypeAdapter;
import com.grupitxo.typeadapter.HistoricoTypeAdapter;
import com.grupitxo.typeadapter.ProdutoTypeAdapter;

public class JsonReader {
	private static Gson gson = new GsonBuilder()
			.registerTypeAdapter(Cliente.class, new ClienteTypeAdapter())
			.registerTypeAdapter(Funcionario.class, new FuncionarioTypeAdapter())
	        .registerTypeAdapter(Produto.class, new ProdutoTypeAdapter())
			.registerTypeAdapter(Compra.class, new CompraTypeAdapter())
			.registerTypeAdapter(new TypeToken<ArrayList<Compra>>(){}.getType(), 
                    new HistoricoTypeAdapter(new CompraTypeAdapter()))
			.create();
	
	// Métodos para carregar objetos em memória
	public static void carregarClientes() {
		try (FileReader fr = new FileReader("clientes.json")) {
			Cliente[] clientes = gson.fromJson(fr, Cliente[].class);
			for (Cliente cliente : clientes) {
				Cliente.addToListaClientes(cliente);
			}
		} catch (IOException | JsonIOException e) {
		}
	}
	
	public static void carregarFuncionarios() {
		try (FileReader fr = new FileReader("funcionarios.json")) {
			Funcionario[] funcionarios = gson.fromJson(fr, Funcionario[].class);
			for (Funcionario funcionario : funcionarios) {
				Funcionario.addToListaFuncionarios(funcionario);
			}
		} catch (IOException | JsonIOException e) {
		}
	}
	
	public static void carregarHistorico() {
		try (FileReader fr = new FileReader("historico.json")) {
			Compra[] historico = gson.fromJson(fr, Compra[].class);
			for (Compra compra : historico) {
				Historico.addToHistorico(compra);
			}
		} catch (IOException | JsonIOException e) {
		}
	}
	
	public static void carregarEstoque() {
		try (FileReader fr = new FileReader("estoque.json")) {
			Produto[] estoque = gson.fromJson(fr, Produto[].class);
			for (Produto produto : estoque) {
				Produto.addToListaProdutos(produto);
			}
		} catch (IOException | JsonIOException e) {
		}
	}
}
