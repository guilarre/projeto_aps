package com.grupitxo.classes;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// instanciando os view/controller/adapter pra funcionário:
		FuncionarioStorageStrategy funcionarioAdapter = null;
        FuncionarioView funcionarioView = null;
        FuncionarioViewFactory funcionarioFactory = null; 
        FuncionarioController funcionarioController = null;
		// instanciando pra cliente:
		ClienteStorageStrategy clienteAdapter = null;
        ClienteView clienteView = null;
        ClienteViewFactory clienteFactory = null; 
        ClienteController clienteController = null;
		// módulo de venda
        Venda moduloVenda = null;
		
		try {
            // Instanciando as dependências para funcionário (Adapter, View, Factory)
            funcionarioAdapter = new FuncionarioDbAdapter(DatabaseConfig.getConnection());
            funcionarioView = new FuncionarioView();
            funcionarioFactory = new FuncionarioViewFactory(); 
			funcionarioController = new FuncionarioController(funcionarioView, funcionarioFactory, funcionarioAdapter);
            // Instanciando as dependências para cliente (Adapter, View, Factory)
			clienteAdapter = new ClienteDbAdapter(DatabaseConfig.getConnection());
            clienteView = new ClienteView();
            clienteFactory = new ClienteViewFactory();
			clienteController = new ClienteController(clienteView, clienteFactory, clienteAdapter);

            // Instanciando a classe Venda e INJETANDO o controller
            moduloVenda = new Venda(funcionarioController, clienteController);

        } catch (SQLException e) {
            System.err.println("Falha crítica: Não foi possível conectar ao banco de dados ao iniciar o sistema.");
            e.printStackTrace();
        }

		// se der certo, vai inicializar o BD:
		DatabaseConfig.inicializarBanco();

		// Carregar arquivos em memória
		try {			
			JsonReader.carregarEstoque();
			JsonReader.carregarHistorico();
		} catch (NullPointerException e) {
		}
		
		// Menu principal
		loopMain: while (true) {
			System.out.println(StringsMenu.menuPrincipal);
			int opcao = sc.nextInt();
			switch (opcao) {
				// Menu clientes
				// NOTE: exemplo do que foi mudado após refatoração
				case 1:
					Cliente cliente = null;
					loopCliente: while (true) {
						System.out.println(StringsMenu.menuClientes);
						opcao = sc.nextInt();
						switch (opcao) {
							// Exibir todos os clientes
							case 1:
								clienteController.rotinaListarClientes();
								break;
							// Exibir informações de um cliente
							case 2:
								cliente = clienteController.selecionarClienteParaAcao(sc);
								if (cliente != null) {
									clienteView.exibirCliente(cliente);
								} else {
									System.out.println("Operação cancelada");
								}
								break;
							// Exibir histórico de compras de um cliente
							case 3:
								cliente = clienteController.selecionarClienteParaAcao(sc);
								if (cliente != null) {
									System.out.println(Historico.getHistoricoCliente(cliente.getIdCliente()));
								} else {
									System.out.println("Operação cancelada!");
								}
								break;
							// Registrar um cliente
							case 4:
								clienteController.rotinaCriarCliente(sc);
								break;
							// Modificar um cliente
							case 5:
								clienteController.rotinaAtualizarCliente(sc);
								break;
							// Remover um cliente
							case 6:
								clienteController.rotinaRemoverCliente(sc);
								break;
							// Retornar ao menu principal
							case 0:
								break loopCliente;
							default:
								System.err.println("ERRO! Opção inválida");
								break;
						}
					}
					break;
				// Menu funcionários
				// NOTE: exemplo do que foi mudado após refatoração
				case 2:
					Funcionario funcionario = null;
					loopFuncionario: while (true) {
						System.out.println(StringsMenu.menuFuncionarios);
						opcao = sc.nextInt();
						switch (opcao) {
						// Exibir todos os funcionários
						case 1:
							funcionarioController.rotinaListarFuncionarios();
							break;
						// Exibir informações de um funcionário
						case 2:
							funcionario = funcionarioController.selecionarFuncionarioParaAcao(sc);
							if (funcionario != null) {
								funcionarioView.exibirFuncionario(funcionario);
							} else {
								System.out.println("Operação cancelada");
							}
							break;
						// Exibir histórico de vendas de um funcionário
						case 3:
							funcionario = funcionarioController.selecionarFuncionarioParaAcao(sc);
							if (funcionario != null) {
								System.out.println(Historico.getHistoricoFuncionario(funcionario.getIdFuncionario()));
							} else {
								System.out.println("Operação cancelada!");
							}
							break;
						// Registrar um funcionário
						case 4:
							funcionarioController.rotinaCriarFuncionario(sc);
							break;
						// Modificar um funcionário
						// NOTE: exemplo da maior mudança
						case 5:
							funcionarioController.rotinaAtualizarFuncionario(sc);
							break;
						// Remover um funcionário
						case 6:
							funcionarioController.rotinaRemoverFuncionario(sc);
							break;
						// Retornar ao menu principal
						case 0:
							break loopFuncionario;
						default:
							System.err.println("ERRO! Opção inválida");
							break;
						}
					}
					break;
				// Menu estoque
				case 3:
					loopEstoque: while (true) {
						System.out.println(StringsMenu.menuEstoque);
						opcao = sc.nextInt();
						switch (opcao) {
							// Pesquisar um produto em estoque
							case 1:
								Produto produto = Produto.selecionarProduto();
								if (produto != null) {
									System.out.println(produto.toString());
								} else {
									System.out.println("Operação cancelada");
								}
								break;
							// Exibir todas as categorias
							case 2:
								System.out.println(String.format("""

Categorias disponíveis

%s""", Produto.getTodasCategorias()));
								break;
							// Exibir todo o estoque
							case 3:
								for (Produto produtoAExibir : Produto.getListaProdutos()) {
									System.out.println(produtoAExibir);
								}
								break;
							// Gerenciar estoque
							case 4:
								loopGerenciarEstoque: while (true) {
									System.out.println(StringsMenu.menuGerenciarEstoque);
									opcao = sc.nextInt();
									switch (opcao) {
										// Adicionar produto novo ao estoque
										case 1:
											Produto produtoNovo = Produto.getProdutoNovo();
											if (produtoNovo != null) {
												System.out.println(String.format("""

Produto adicionado com sucesso:
%s""", produtoNovo.toString()));
											} else {
												System.out.println("Operação cancelada");
											}
											break;
										// Modificar produto no estoque
										case 2:
											Produto produtoModificado = null;
											Produto produtoAModificar = Produto.selecionarProduto();
											if (produtoAModificar != null) {
												loopModificarProduto: while (true) {
													System.out.println(StringsMenu.menuModificarProduto);
													opcao = sc.nextInt();
													switch (opcao) {
														case 1:
															produtoModificado = Produto.modificarProduto(produtoAModificar, opcao);
															if (produtoModificado == null) {
																System.out.println("Modificação cancelada!");
															}
															System.out.println(String.format("""

Produto modificado com sucesso:
%s""", produtoModificado.toString()));
															break;
														case 2:
															produtoModificado = Produto.modificarProduto(produtoAModificar, opcao);
															if (produtoModificado == null) {
																System.out.println("Modificação cancelada!");
															}
															System.out.println(String.format("""

Produto modificado com sucesso:
%s""", produtoModificado.toString()));
															break;
														case 3:
															produtoModificado = Produto.modificarProduto(produtoAModificar, opcao);
															if (produtoModificado == null) {
																System.out.println("Modificação cancelada!");
															}
															System.out.println(String.format("""

Produto modificado com sucesso:
%s""", produtoModificado.toString()));
															break;
														case 4:
															produtoModificado = Produto.modificarProduto(produtoAModificar, opcao);
															if (produtoModificado == null) {
																System.out.println("Modificação cancelada!");
															}
															System.out.println(String.format("""

Produto modificado com sucesso:
%s""", produtoModificado.toString()));
															break;
														case 5:
															produtoModificado = Produto.modificarProduto(produtoAModificar, opcao);
															if (produtoModificado == null) {
																System.out.println("Modificação cancelada!");
															}
															System.out.println(String.format("""

Produto modificado com sucesso:
%s""", produtoModificado.toString()));
															break;
														case 6:
															produtoModificado = Produto.modificarProduto(produtoAModificar, opcao);
															if (produtoModificado == null) {
																System.out.println("Modificação cancelada!");
															}
															System.out.println(String.format("""

Produto modificado com sucesso:
%ss
""", produtoModificado.toString()));
															break;
														case 0:
															break loopModificarProduto;
														default:
															System.err.println("ERRO! Opção inválida");
															break;
													}
												}
											} else {
												System.out.println("Operação cancelada");
											}
											break;
										// Remover produto do estoque
										case 3:
											Produto produtoARemover = Produto.selecionarProduto();
											if (produtoARemover != null) {
												Produto.removerProduto(produtoARemover);
											} else {
												System.out.println("Operação cancelada");
											}
											break;
										// Retornar ao menu anterior
										case 0:
											break loopGerenciarEstoque;
										default:
											System.err.println("ERRO! Opção inválida");
											break;
									}
								}
								break;
							// Retornar ao menu principal
							case 0:
								break loopEstoque;
							default:
								System.err.println("ERRO! Opção inválida");
								break;
						}
					}
					break;
				// Menu vendas
				case 4:
					loopMenuVendas: while (true) {
						System.out.println(StringsMenu.menuVendas);
						boolean vendaSucesso = false;
						boolean pesquisaSucesso = false;
						boolean cancelamentoSucesso = false;
						opcao = sc.nextInt();
						switch (opcao) {
							// Realizar venda
							case 1:
								vendaSucesso = moduloVenda.realizarVenda(sc);
								if (vendaSucesso) {
									System.out.println("Venda registrada com sucesso!");
									break;
								}
								System.out.println("A venda falhou!");
								break;
							// Pesquisar vendas
							case 2:
								pesquisaSucesso = moduloVenda.pesquisarVendas(sc);
								if (pesquisaSucesso == false) {
									System.out.println("A pesquisa falhou!");
								}
								break;
							// Cancelar venda realizada
							case 3:
								cancelamentoSucesso = moduloVenda.cancelarVenda(sc);
								if (cancelamentoSucesso == false) {
									System.out.println("O cancelamento falhou!");
								}
								System.out.println("Venda cancelada com sucesso!");
								break;
							// Retornar ao menu anterior
							case 0:
								break loopMenuVendas;
							default:
								System.err.println("ERRO! Opção inválida");
								break;
						}
					}
					break;
				// TEST: Gerar relatório mensal
				case 5:
					System.out.println(Historico.gerarRelatorio());
					break;
				// Sair do sistema
				case 0:
					System.out.println("Até logo!");
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