package com.grupitxo.classes;

import java.util.Scanner;

public class ClienteView {

    ClienteController controller = new ClienteController();

    public void menuClientes() {

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println(Menu.menuClientes);
            int opcao = sc.nextInt();

            switch (opcao) {

                case 1:
                    System.out.println(controller.listarClientes());
                    break;

                case 2: {
                    Cliente cliente = controller.selecionarCliente();
                    System.out.println(controller.verCliente(cliente));
                    break;
                }

                case 3: {
                    Cliente cliente = controller.selecionarCliente();
                    System.out.println(controller.historicoCliente(cliente));
                    break;
                }

                case 4:
                    controller.criarCliente();
                    break;

                case 5: {

                    Cliente clienteAModificar = controller.selecionarCliente();

                    if (clienteAModificar != null) {

                        while (true) {

                            System.out.println(Menu.menuModificarCliente);
                            int opcaoMod = sc.nextInt();

                            if (opcaoMod == 0) return;

                            Cliente clienteModificado =
                                controller.modificarCliente(clienteAModificar, opcaoMod);

                            if (clienteModificado != null) {
                                System.out.println("Cliente modificado com sucesso:");
                                System.out.println(clienteModificado);
                            } else {
                                System.out.println("Modificação cancelada!");
                            }
                        }

                    } else {
                        System.out.println("Operação cancelada");
                    }

                    break;
                }

                case 6:
                    controller.removerCliente();
                    break;

                case 0:
                    return;

                default:
                    System.err.println("ERRO! Opção inválida");
                    break;
            }
        }
    }
}