# Projeto da disciplina de Análise e Projeto de Software

## Visão geral
O objetivo principal deste projeto consistiu em refatorar o código de um sistema já existente, de acordo com os princípios SOLID, GRASP e ao menos 3 padrões de projeto GoF, e seguindo a metodologia TDD (Test-Driven Development).

O [sistema escolhido](https://github.com/guilarre/UPE_programacao_2) para refatoração foi um sistema de estoque e venda de roupas escrito em Java.

Como exemplo do escopo deste projeto, foi possível diminuir a classe Funcionario de 512 linhas de código para apenas 68 linhas, aplicando-se estratégias diferentes para atingir um desacoplamento maior nesta e na classe Cliente, e eliminarmos anti-padrões diferentes.

[Relatório completo](https://github.com/guilarre/projeto_aps/blob/main/Projeto%20APS%20-%20Documento%20de%20Arquitetura%20e%20Design.pdf)

## Pra rodar o sistema:
- Abra um terminal e clone o repositório:
`git clone https://github.com/guilarre/projeto_aps.git`

- Verifique se vc tem o Java 17 (ou uma versão superior) instalado:
`java --version`

- Se não tiver instalado, instale o Java 17 (ou uma versão mais recente) e verifique novamente.

- Você pode rodar o projeto pelo terminal (mais simples), usando o .jar:
```bash
cd ./projeto_aps/aps/target
java -jar aps-0.1.0-jar-with-dependencies.jar
```

- Ou abrir no VSCode (ou outra IDE) e rodar a classe Main.java dentro de:
`aps/src/main/java/com/grupitxo/classes/`
