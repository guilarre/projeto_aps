package com.grupitxo.classes;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// Carregar arquivos em memória
		try {			
			JsonReader.carregarClientes();
			JsonReader.carregarFuncionarios();
			JsonReader.carregarEstoque();
			JsonReader.carregarHistorico();
		} catch (NullPointerException e) {
		}
		
		// Menu principal
		Scanner sc = new Scanner(System.in);
		loopMain: while (true) {
			System.out.println(StringsMenu.menuPrincipal);
			int opcao = sc.nextInt();
			switch (opcao) {
				// Menu clientes
				case 1:
					Cliente cliente = null;
					loopCliente: while (true) {
						System.out.println(StringsMenu.menuClientes);
						opcao = sc.nextInt();
						switch (opcao) {
							// Exibir todos os clientes
							case 1:
								System.out.println(Cliente.getClientes());
								break;
							// Exibir informações de um cliente
							case 2:
								cliente = Cliente.selecionarCliente();
								if (cliente != null) {
									System.out.println(cliente.toString());
								} else {
									System.out.println("Operação cancelada");
								}
								break;
							// Exibir histórico de compras de um cliente
							case 3:
								cliente = Cliente.selecionarCliente();
								if (cliente != null) {
									System.out.println(Historico.getHistoricoCliente(cliente.getIdCliente()));
								} else {
									System.out.println("Operação cancelada!");
								}
								break;
							// Registrar um cliente
							case 4:
								Cliente.getClienteNovo();
								break;
							// Modificar um cliente
							case 5:
								Cliente clienteModificado = null;
								Cliente clienteAModificar = Cliente.selecionarCliente();
								if (clienteAModificar != null) {
									loopModificarCliente: while (true) {
										System.out.println(StringsMenu.menuModificarCliente);
										opcao = sc.nextInt();
										switch (opcao) {
											case 1:
												clienteModificado = Cliente.modificarCliente(clienteAModificar, opcao);
												if (clienteModificado == null) {
													System.out.println("Modificação cancelada!");
												}
												System.out.println(String.format("""

Cliente modificado com sucesso:
%s""", clienteModificado.toString()));
												break;
											case 2:
												clienteModificado = Cliente.modificarCliente(clienteAModificar, opcao);
												if (clienteModificado == null) {
													System.out.println("Modificação cancelada!");
												}
												System.out.println(String.format("""

Cliente modificado com sucesso:
%s""", clienteModificado.toString()));
												break;
											case 3:
												clienteModificado = Cliente.modificarCliente(clienteAModificar, opcao);
												if (clienteModificado == null) {
													System.out.println("Modificação cancelada!");
												}
												System.out.println(String.format("""

Cliente modificado com sucesso:
%s""", clienteModificado.toString()));
												break;
											case 4:
												clienteModificado = Cliente.modificarCliente(clienteAModificar, opcao);
												if (clienteModificado == null) {
													System.out.println("Modificação cancelada!");
												}
												System.out.println(String.format("""

Cliente modificado com sucesso:
%s""", clienteModificado.toString()));
												break;
											case 5:
												clienteModificado = Cliente.modificarCliente(clienteAModificar, opcao);
												if (clienteModificado == null) {
													System.out.println("Modificação cancelada!");
												}
												System.out.println(String.format("""

Cliente modificado com sucesso:
%s""", clienteModificado.toString()));
												break;
											case 6:
												clienteModificado = Cliente.modificarCliente(clienteAModificar, opcao);
												if (clienteModificado == null) {
													System.out.println("Modificação cancelada!");
												}
												System.out.println(String.format("""

Cliente modificado com sucesso:
%s""", clienteModificado.toString()));
												break;
											case 7:
												clienteModificado = Cliente.modificarCliente(clienteAModificar, opcao);
												if (clienteModificado == null) {
													System.out.println("Modificação cancelada!");
												}
												System.out.println(String.format("""

Cliente modificado com sucesso:
%s""", clienteModificado.toString()));
												break;
											case 8:
												clienteModificado = Cliente.modificarCliente(clienteAModificar, opcao);
												if (clienteModificado == null) {
													System.out.println("Modificação cancelada!");
												}
												System.out.println(String.format("""

Cliente modificado com sucesso:
%s""", clienteModificado.toString()));
												break;
											case 0:
												break loopModificarCliente;
											default:
												System.err.println("ERRO! Opção inválida");
												break;
										}
									}
								} else {
									System.out.println("Operação cancelada");
								}
								break;
							// Remover um cliente
							case 6:
								Cliente clienteARemover = Cliente.selecionarCliente();
								if (clienteARemover != null) {
									Cliente.removerCliente(clienteARemover);
								} else {
									System.out.println("Operação cancelada");
								}
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
				case 2:
					Funcionario funcionario = null;
					loopFuncionario: while (true) {
						System.out.println(StringsMenu.menuFuncionarios);
						opcao = sc.nextInt();
						switch (opcao) {
						// Exibir todos os funcionários
						case 1:
							System.out.println(Funcionario.getFuncionarios());
							break;
						// Exibir informações de um funcionário
						case 2:
							funcionario = Funcionario.selecionarFuncionario();
							if (funcionario != null) {
								System.out.println(funcionario.toString());
							} else {
								System.out.println("Operação cancelada");
							}
							break;
						// Exibir histórico de vendas de um funcionário
						case 3:
							funcionario = Funcionario.selecionarFuncionario();
							if (funcionario != null) {
								System.out.println(Historico.getHistoricoFuncionario(funcionario.getIdFuncionario()));
							} else {
								System.out.println("Operação cancelada!");
							}
							break;
						// Registrar um funcionário
						case 4:
							Funcionario.getFuncionarioNovo();
							break;
						// Modificar um funcionário
						case 5:
							Funcionario funcionarioModificado = null;
							Funcionario funcionarioAModificar = Funcionario.selecionarFuncionario();
							if (funcionarioAModificar != null) {
								loopModificarFuncionario: while (true) {
									System.out.println(StringsMenu.menuModificarFuncionario);
									opcao = sc.nextInt();
									switch (opcao) {
										case 1:
											funcionarioModificado = Funcionario.modificarFuncionario(funcionarioAModificar, opcao);
											if (funcionarioModificado == null) {
												System.out.println("Modificação cancelada!");
											}
											System.out.println(String.format("""

Funcionario modificado com sucesso:
%s""", funcionarioModificado.toString()));
											break;
										case 2:
											funcionarioModificado = Funcionario.modificarFuncionario(funcionarioAModificar, opcao);
											if (funcionarioModificado == null) {
												System.out.println("Modificação cancelada!");
											}
											System.out.println(String.format("""

Funcionario modificado com sucesso:
%s""", funcionarioModificado.toString()));
											break;
										case 3:
											funcionarioModificado = Funcionario.modificarFuncionario(funcionarioAModificar, opcao);
											if (funcionarioModificado == null) {
												System.out.println("Modificação cancelada!");
											}
											System.out.println(String.format("""

Funcionario modificado com sucesso:
%s""", funcionarioModificado.toString()));
											break;
										case 4:
											funcionarioModificado = Funcionario.modificarFuncionario(funcionarioAModificar, opcao);
											if (funcionarioModificado == null) {
												System.out.println("Modificação cancelada!");
											}
											System.out.println(String.format("""

Funcionario modificado com sucesso:
%s""", funcionarioModificado.toString()));
											break;
										case 5:
											funcionarioModificado = Funcionario.modificarFuncionario(funcionarioAModificar, opcao);
											if (funcionarioModificado == null) {
												System.out.println("Modificação cancelada!");
											}
											System.out.println(String.format("""

Funcionario modificado com sucesso:
%s""", funcionarioModificado.toString()));
											break;
										case 6:
											funcionarioModificado = Funcionario.modificarFuncionario(funcionarioAModificar, opcao);
											if (funcionarioModificado == null) {
												System.out.println("Modificação cancelada!");
											}
											System.out.println(String.format("""

Funcionario modificado com sucesso:
%s""", funcionarioModificado.toString()));
											break;
										case 7:
											funcionarioModificado = Funcionario.modificarFuncionario(funcionarioAModificar, opcao);
											if (funcionarioModificado == null) {
												System.out.println("Modificação cancelada!");
											}
											System.out.println(String.format("""

Funcionario modificado com sucesso:
%s""", funcionarioModificado.toString()));
											break;
										case 8:
											funcionarioModificado = Funcionario.modificarFuncionario(funcionarioAModificar, opcao);
											if (funcionarioModificado == null) {
												System.out.println("Modificação cancelada!");
											}
											System.out.println(String.format("""

Funcionario modificado com sucesso:
%s""", funcionarioModificado.toString()));
											break;
										case 9:
											funcionarioModificado = Funcionario.modificarFuncionario(funcionarioAModificar, opcao);
											if (funcionarioModificado == null) {
												System.out.println("Modificação cancelada!");
											}
											System.out.println(String.format("""

Funcionario modificado com sucesso:
%s""", funcionarioModificado.toString()));
											break;
										case 10:
											funcionarioModificado = Funcionario.modificarFuncionario(funcionarioAModificar, opcao);
											if (funcionarioModificado == null) {
												System.out.println("Modificação cancelada!");
											}
											System.out.println(String.format("""

Funcionario modificado com sucesso:
%s""", funcionarioModificado.toString()));
											break;
										case 0:
											break loopModificarFuncionario;
										default:
											System.err.println("ERRO! Opção inválida");
											break;
									}
								}
							} else {
								System.out.println("Operação cancelada");
							}
							break;
						// Remover um funcionário
						case 6:
							Funcionario funcionarioARemover = Funcionario.selecionarFuncionario();
							if (funcionarioARemover != null) {
								Funcionario.removerFuncionario(funcionarioARemover);
							} else {
								System.out.println("Operação cancelada");
							}
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
								vendaSucesso = Venda.realizarVenda();
								if (vendaSucesso) {
									System.out.println("Venda registrada com sucesso!");
									break;
								}
								System.out.println("A venda falhou!");
								break;
							// Pesquisar vendas
							case 2:
								pesquisaSucesso = Venda.pesquisarVendas();
								if (pesquisaSucesso == false) {
									System.out.println("A pesquisa falhou!");
								}
								break;
							// Cancelar venda realizada
							case 3:
								cancelamentoSucesso = Venda.cancelarVenda();
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