package com.grupitxo.typeadapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import com.grupitxo.classes.Compra;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HistoricoTypeAdapter extends TypeAdapter<List<Compra>> {
    private final CompraTypeAdapter compraAdapter;

    public HistoricoTypeAdapter(CompraTypeAdapter compraAdapter) {
        this.compraAdapter = compraAdapter;
    }

    @Override
    public void write(JsonWriter out, List<Compra> compras) throws IOException {
        out.beginArray();
        for (Compra compra : compras) {
            compraAdapter.write(out, compra);
        }
        out.endArray();
    }

    @Override
    public List<Compra> read(JsonReader in) throws IOException {
        List<Compra> compras = new ArrayList<>();
        in.beginArray();
        while (in.hasNext()) {
            Compra compra = compraAdapter.read(in);
            compras.add(compra);
        }
        in.endArray();
        return compras;
    }
}