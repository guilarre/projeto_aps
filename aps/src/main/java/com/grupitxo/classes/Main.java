package com.grupitxo.classes;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try {
            JsonReader.carregarClientes();
            JsonReader.carregarFuncionarios();
            JsonReader.carregarEstoque();
            JsonReader.carregarHistorico();
        } catch (NullPointerException e) {
        }

        Scanner sc = new Scanner(System.in);

        ClienteView clienteView = new ClienteView();

        loopMain: while (true) {

            System.out.println(Menu.menuPrincipal);
            int opcao = sc.nextInt();

            switch (opcao) {

                case 1:
                    clienteView.menuClientes();
                    break;

                case 0:

                    System.out.println("Até logo!");

                    JsonWriter.salvarClientes();
                    JsonWriter.salvarFuncionarios();
                    JsonWriter.salvarEstoque();
                    JsonWriter.salvarHistorico();

                    sc.close();
                    break loopMain;

                default:
                    System.err.println("ERRO! Opção inválida");
                    break;
            }
        }
    }
}