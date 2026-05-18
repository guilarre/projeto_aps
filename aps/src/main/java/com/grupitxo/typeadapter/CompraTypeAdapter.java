package com.grupitxo.typeadapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.grupitxo.enums.Pagamento;
import com.grupitxo.enums.Status;
import com.grupitxo.classes.Compra;
import com.grupitxo.classes.Produto;

public class CompraTypeAdapter extends TypeAdapter<Compra> {

    @Override
    public void write(JsonWriter out, Compra compra) throws IOException {
        out.beginObject();
        // Serializa apenas os campos essenciais (não gera automaticamente)
        out.name("idCliente").value(compra.getIdCliente());
        out.name("idFuncionario").value(compra.getIdFuncionario());
        out.name("desconto").value(compra.getDesconto());
        out.name("pagamento").value(compra.getPagamento().name());
        out.name("status").value(compra.getStatus().name());
        
        // Serializa apenas IDs e quantidades dos produtos
        out.name("produtos").beginArray();
        for (Compra.CompraProduto cp : compra.getListaCompraProdutos()) {
            out.beginObject();
            out.name("idProduto").value(cp.getProduto().getIdProduto());
            out.name("quantidade").value(cp.getQtdComprada());
            out.name("descontoProduto").value(cp.getDesconto());
            out.endObject();
        }
        out.endArray();
        
        out.endObject();
    }

    @Override
    public Compra read(JsonReader in) throws IOException {
        in.beginObject();
        
        int idCliente = 0;
        int idFuncionario = 0;
        double desconto = 1;
        Pagamento pagamento = null;
        Status status = Status.EM_PROCESSAMENTO;
        List<Compra.CompraProduto> produtos = new ArrayList<>();
        
        while (in.hasNext()) {
            String fieldName = in.nextName();
            switch (fieldName) {
                case "idCliente":
                    idCliente = in.nextInt();
                    break;
                case "idFuncionario":
                    idFuncionario = in.nextInt();
                    break;
                case "desconto":
                    desconto = in.nextDouble();
                    break;
                case "pagamento":
                    pagamento = Pagamento.valueOf(in.nextString());
                    break;
                case "status":
                    status = Status.valueOf(in.nextString());
                    break;
                case "produtos":
                    in.beginArray();
                    while (in.hasNext()) {
                        in.beginObject();
                        int idProduto = 0;
                        int quantidade = 0;
                        double descontoProduto = 1;
                        
                        while (in.hasNext()) {
                            String prodField = in.nextName();
                            switch (prodField) {
                                case "idProduto":
                                    idProduto = in.nextInt();
                                    break;
                                case "quantidade":
                                    quantidade = in.nextInt();
                                    break;
                                case "descontoProduto":
                                    descontoProduto = in.nextDouble();
                                    break;
                                default:
                                    in.skipValue();
                            }
                        }
                        // Busca o produto na lista existente
                        Produto produto = Produto.getProdutoById(idProduto);
                        if (produto != null) {
                            produtos.add(new Compra.CompraProduto(produto, quantidade, descontoProduto, false));
                        }
                        in.endObject();
                    }
                    in.endArray();
                    break;
                default:
                    in.skipValue(); // Ignora campos calculados automaticamente
            }
        }
        in.endObject();
        
        // Cria a compra (os campos calculados serão gerados automaticamente)
        Compra compra = new Compra(idCliente, idFuncionario, new ArrayList<>(produtos), desconto, pagamento, false);
        compra.setStatus(status);
        return compra;
    }
}