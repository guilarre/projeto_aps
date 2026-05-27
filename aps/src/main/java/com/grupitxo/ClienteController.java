package com.grupitxo.classes;

public class ClienteController {

    public String listarClientes() {
        return Cliente.getClientes();
    }

    public Cliente selecionarCliente() {
        return Cliente.selecionarCliente();
    }

    public void criarCliente() {
        Cliente.getClienteNovo();
    }

    public void removerCliente() {
        Cliente clienteARemover = Cliente.selecionarCliente();

        if (clienteARemover != null) {
            Cliente.removerCliente(clienteARemover);
        } else {
            System.out.println("Operação cancelada");
        }
    }

    public String verCliente(Cliente cliente) {
        if (cliente != null) {
            return cliente.toString();
        }
        return "Operação cancelada";
    }

    public String historicoCliente(Cliente cliente) {
        if (cliente != null) {
            return Historico.getHistoricoCliente(cliente.getIdCliente());
        }
        return "Operação cancelada!";
    }

    public Cliente modificarCliente(Cliente cliente, int opcao) {
        return Cliente.modificarCliente(cliente, opcao);
    }
}