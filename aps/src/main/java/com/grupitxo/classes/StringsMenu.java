package com.grupitxo.classes;

public class StringsMenu {
	public static String menuPrincipal = """
###### Sistema de gerenciamento LaSca ######

############## Menu Principal ##############

Selecione a opção desejada:

[1] = Clientes
[2] = Funcionários
[3] = Estoque
[4] = Vendas
[5] = Gerar relatório mensal

[0] = Sair do sistema
""";
	
	public static String menuClientes = """

############## Menu Clientes ##############


[1] = Exibir todos os clientes
[2] = Exibir informações de um cliente
[3] = Exibir histórico de compras de um cliente
[4] = Registrar um cliente
[5] = Modificar um cliente
[6] = Remover um cliente

[0] = Retornar ao menu principal
""";
	
	public static String menuSelecionarCliente = """

########### Selecionar Cliente ###########

[1] = Procurar cliente por seu id
[2] = Procurar cliente por seu nome

[0] = Retornar ao menu anterior
""";
	
	public static String menuModificarCliente = """

############ Modificar Cliente ############

O que deseja alterar?

[1] = Nome
[2] = CPF
[3] = Telefone
[4] = Email
[5] = Preferência de comunicação
[6] = Endereço
[7] = Aniversário
[8] = Gênero

[0] = Retornar ao menu anterior
""";
	
	public static String menuFuncionarios = """

############ Menu Funcionários ############

[1] = Exibir todos os funcionários
[2] = Exibir informações de um funcionário
[3] = Exibir histórico de vendas de um funcionário
[4] = Registrar um funcionário
[5] = Modificar um funcionário
[6] = Remover um funcionário

[0] = Retornar ao menu principal
""";
	
	public static String menuSelecionarFuncionario = """

######### Selecionar Funcionário #########

[1] = Procurar funcionário por seu id
[2] = Procurar funcionário por seu nome

[0] = Retornar ao menu anterior
""";
	
	public static String menuModificarFuncionario = """

########## Modificar Funcionário ##########

O que deseja alterar?

[1] = Nome
[2] = CPF
[3] = Telefone
[4] = Email
[5] = Preferência de comunicação
[6] = Endereço
[7] = Aniversário
[8] = Gênero
[9] = Cargo
[10] = Salário

[0] = Retornar ao menu anterior
""";
	
	public static String menuEstoque = """

############## Menu Estoque ##############

[1] = Pesquisar um produto em estoque
[2] = Exibir todas as categorias
[3] = Exibir todo o estoque
[4] = Gerenciar estoque

[0] = Retornar ao menu principal
""";
	
	public static String menuPesquisarEstoque = """

########### Selecionar Produto ###########

[1] = Procurar por id do produto
[2] = Procurar pelo nome do produto

[0] = Retornar ao menu anterior
""";
	
	public static String menuGerenciarEstoque = """

########### Gerenciar Estoque ###########

[1] = Adicionar produto novo ao estoque
[2] = Modificar produto no estoque
[3] = Remover produto do estoque

[0] = Retornar ao menu anterior
""";
	
	public static String menuSelecionarProduto = """

########### Selecionar Produto ###########

[1] = Procurar por id do produto
[2] = Procurar pelo nome do produto

[0] = Retornar ao menu anterior
""";
	
	public static String menuModificarProduto = """

########### Modificar Produto ###########

O que deseja alterar?

[1] = SKU
[2] = Nome do produto
[3] = Descrição
[4] = Valor
[5] = Categoria
[6] = Quantidade em estoque

[0] = Retornar ao menu anterior
""";
	
	public static String menuVendas = """

############## Menu Vendas ##############

[1] = Realizar venda
[2] = Pesquisar vendas
[3] = Cancelar venda realizada

[0] = Retornar ao menu anterior
""";
	
	public static String menuPesquisarVendas = """

########### Pesquisar vendas ###########

[1] = Pesquisar por cliente
[2] = Pesquisar por vendedor
[3] = Exibir todas as vendas

[0] = Retornar ao menu anterior
""";	
	
}