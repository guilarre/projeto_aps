package com.grupitxo.typeadapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import com.grupitxo.classes.Cliente;

import java.io.IOException;

public class ClienteTypeAdapter extends TypeAdapter<Cliente> {

    @Override
    public void write(JsonWriter out, Cliente cliente) throws IOException {
        out.beginObject();
        // Serializa todos os campos, incluindo os herdados de Pessoa
        out.name("idCliente").value(cliente.getIdCliente());
        out.name("nome").value(cliente.getNome());
        out.name("cpf").value(cliente.getCpf());
        out.name("telefone").value(cliente.getTelefone());
        out.name("email").value(cliente.getEmail());
        out.name("preferenciaComunicacao").value(cliente.getPreferenciaComunicacao());
        out.name("endereco").value(cliente.getEndereco());
        out.name("aniversario").value(cliente.getAniversario());
        out.name("genero").value(cliente.getGenero());
        out.endObject();
    }

    @Override
    public Cliente read(JsonReader in) throws IOException {
        in.beginObject();
        
        // Inicializa variáveis para todos os campos necessários
        String nome = null;
        String cpf = null;
        String telefone = null;
        String email = null;
        String preferenciaComunicacao = null;
        String endereco = null;
        String aniversario = null;
        String genero = null;
        
        while (in.hasNext()) {
            String fieldName = in.nextName();
            switch (fieldName) {
                case "nome":
                    nome = in.nextString();
                    break;
                case "cpf":
                    cpf = in.nextString();
                    break;
                case "telefone":
                    telefone = in.nextString();
                    break;
                case "email":
                    email = in.nextString();
                    break;
                case "preferenciaComunicacao":
                    preferenciaComunicacao = in.nextString();
                    break;
                case "endereco":
                    endereco = in.nextString();
                    break;
                case "aniversario":
                    aniversario = in.nextString();
                    break;
                case "genero":
                    genero = in.nextString();
                    break;
                case "idCliente":
                    in.nextInt(); // Ignoramos pois será gerado automaticamente
                    break;
                default:
                    in.skipValue(); // Ignora campos desconhecidos
                    break;
            }
        }
        in.endObject();
        
        // Cria e retorna um novo Cliente com os dados lidos
        return new Cliente(nome, cpf, telefone, email, preferenciaComunicacao, 
                         endereco, aniversario, genero, false);
    }
}