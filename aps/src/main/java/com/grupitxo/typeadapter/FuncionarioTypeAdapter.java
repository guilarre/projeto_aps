package com.grupitxo.typeadapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import com.grupitxo.enums.Cargo;
import com.grupitxo.classes.Funcionario;

public class FuncionarioTypeAdapter extends TypeAdapter<Funcionario> {

    @Override
    public void write(JsonWriter out, Funcionario funcionario) throws IOException {
        out.beginObject();
        // Serializa todos os campos, incluindo os herdados de Pessoa
        out.name("idFuncionario").value(funcionario.getIdFuncionario());
        out.name("nome").value(funcionario.getNome());
        out.name("cpf").value(funcionario.getCpf());
        out.name("telefone").value(funcionario.getTelefone());
        out.name("email").value(funcionario.getEmail());
        out.name("preferenciaComunicacao").value(funcionario.getPreferenciaComunicacao());
        out.name("endereco").value(funcionario.getEndereco());
        out.name("aniversario").value(funcionario.getAniversario());
        out.name("genero").value(funcionario.getGenero());
        // Campos específicos de Funcionario
        out.name("cargo").value(funcionario.getCargo().name()); // Serializa o enum como string
        out.name("salario").value(funcionario.getSalario());
        out.endObject();
    }

    @Override
    public Funcionario read(JsonReader in) throws IOException {
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
        Cargo cargo = null;
        double salario = 0.0;
        
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
                case "cargo":
                    cargo = Cargo.valueOf(in.nextString()); // Desserializa o enum
                    break;
                case "salario":
                    salario = in.nextDouble();
                    break;
                case "idFuncionario":
                    in.nextInt(); // Ignoramos pois será gerado automaticamente
                    break;
                default:
                    in.skipValue(); // Ignora campos desconhecidos
                    break;
            }
        }
        in.endObject();
        
        // Cria e retorna um novo Funcionario com os dados lidos
        return new Funcionario(nome, cpf, telefone, email, preferenciaComunicacao, 
                             endereco, aniversario, genero, cargo, salario, false);
    }
}