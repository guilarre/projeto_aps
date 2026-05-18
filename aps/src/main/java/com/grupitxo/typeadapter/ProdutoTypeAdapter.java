package com.grupitxo.typeadapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import com.grupitxo.enums.Categoria;
import com.grupitxo.classes.Produto;

public class ProdutoTypeAdapter extends TypeAdapter<Produto> {

    @Override
    public void write(JsonWriter out, Produto produto) throws IOException {
        out.beginObject();
        out.name("idProduto").value(produto.getIdProduto());
        out.name("sku").value(produto.getSku());
        out.name("nomeProduto").value(produto.getNomeProduto());
        out.name("descricao").value(produto.getDescricao());
        out.name("valor").value(produto.getValor());
        out.name("categoria").value(produto.getCategoria().name());
        out.name("qtdEstoque").value(produto.getQtdEstoque());
        out.endObject();
    }

    @Override
    public Produto read(JsonReader in) throws IOException {
        in.beginObject();
        
        int idProduto = 0;
        String sku = null;
        String nomeProduto = null;
        String descricao = null;
        double valor = 0.0;
        Categoria categoria = null;
        int qtdEstoque = 0;
        
        while (in.hasNext()) {
            String fieldName = in.nextName();
            switch (fieldName) {
                case "idProduto":
                    idProduto = in.nextInt();
                    break;
                case "sku":
                    sku = in.nextString();
                    break;
                case "nomeProduto":
                    nomeProduto = in.nextString();
                    break;
                case "descricao":
                    descricao = in.nextString();
                    break;
                case "valor":
                    valor = in.nextDouble();
                    break;
                case "categoria":
                    categoria = Categoria.valueOf(in.nextString());
                    break;
                case "qtdEstoque":
                    qtdEstoque = in.nextInt();
                    break;
                default:
                    in.skipValue();
            }
        }
        in.endObject();
        
        // Criar novo produto
        Produto produto = new Produto(sku, nomeProduto, descricao, valor, categoria, qtdEstoque, false);
        
        // Definir o ID manualmente para manter consistência
        try {
            java.lang.reflect.Field idField = Produto.class.getDeclaredField("idProduto");
            idField.setAccessible(true);
            idField.set(produto, idProduto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return produto;
    }
}