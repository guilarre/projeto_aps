package com.grupitxo.classes;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import com.grupitxo.typeadapter.ClienteTypeAdapter;
import com.grupitxo.typeadapter.CompraTypeAdapter;
import com.grupitxo.typeadapter.FuncionarioTypeAdapter;
import com.grupitxo.typeadapter.HistoricoTypeAdapter;
import com.grupitxo.typeadapter.ProdutoTypeAdapter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class JsonWriter {
	// Criando objeto Gson com as opções abaixo
	private static Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.registerTypeAdapter(Cliente.class, new ClienteTypeAdapter())
			.registerTypeAdapter(Funcionario.class, new FuncionarioTypeAdapter())
	        .registerTypeAdapter(Produto.class, new ProdutoTypeAdapter())
			.registerTypeAdapter(Compra.class, new CompraTypeAdapter())
			.registerTypeAdapter(new TypeToken<ArrayList<Compra>>(){}.getType(), 
                    new HistoricoTypeAdapter(new CompraTypeAdapter()))
			.serializeNulls()
			.create();
	
	// Método geral para salvar no .json
	public static void salvarEmArquivo(ArrayList<?> lista, String caminhoArquivo) {
		try (FileWriter fw = new FileWriter(caminhoArquivo)) {
			gson.toJson(lista, fw);
		} catch (IOException | JsonIOException e) {
		}
	}
	
	// Métodos específicos (chamados pelo main)
	public static void salvarClientes() {
		ArrayList<Cliente> listaClientes = Cliente.getListaClientes();
		String caminhoArquivo = "clientes.json";
		JsonWriter.salvarEmArquivo(listaClientes, caminhoArquivo);
	}
	
	public static void salvarFuncionarios() {
		ArrayList<Funcionario> listaFuncionarios = Funcionario.getListaFuncionarios();
		String caminhoArquivo = "funcionarios.json";
		JsonWriter.salvarEmArquivo(listaFuncionarios, caminhoArquivo);
	}
	
	public static void salvarEstoque() {
		ArrayList<Produto> estoque = Produto.getListaProdutos();
		String caminhoArquivo = "estoque.json";
		JsonWriter.salvarEmArquivo(estoque, caminhoArquivo);
	}

	public static void salvarHistorico() {
		ArrayList<Compra> historico = Historico.getHistorico();
		String caminhoArquivo = "historico.json";
		JsonWriter.salvarEmArquivo(historico, caminhoArquivo);
	}
}