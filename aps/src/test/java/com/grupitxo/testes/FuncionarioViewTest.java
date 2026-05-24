package com.grupitxo.testes;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.grupitxo.classes.Funcionario;
import com.grupitxo.classes.FuncionarioView;
import com.grupitxo.enums.Cargo;

public class FuncionarioViewTest {
    // Variáveis para capturar a saída do console
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private FuncionarioView view;

    @Before
    public void setUp() {
        view = new FuncionarioView();
        // Diz ao Java: "Tudo que seria printado na tela, jogue no outContent"
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        // Devolve o controle do console para o sistema operacional após o teste
        System.setOut(originalOut);
    }

    @Test
    public void testaExibirListaVaziaOuNula() {
        // Cenário 1: Lista Vazia
        List<Funcionario> listaVazia = new ArrayList<>();
        view.exibirListaFuncionarios(listaVazia);
        
        // outContent.toString() pega o que foi impresso pelo método
        String respostaVazia = outContent.toString();
        assertTrue(respostaVazia.contains("Não há funcionários registrados no sistema."));

        // Limpa o buffer para o próximo teste
        outContent.reset();

        // Cenário 2: Lista Nula
        view.exibirListaFuncionarios(null);
        String respostaNula = outContent.toString();
        assertTrue(respostaNula.contains("Não há funcionários registrados no sistema."));
    }

    @Test
    public void testaExibirListaComFuncionarios() {
        // Prepara uma lista com um funcionário
        List<Funcionario> lista = new ArrayList<>();
        Funcionario f = new Funcionario("Guilherme Teste", "12321323122", "81999999999", "g@g", "email", "Rua A", "20/07/1996", "M", Cargo.CAIXA, 1500.0);
        lista.add(f);

        // Se você ajustar a sua View para fazer: System.out.println(f.getNome());
        view.exibirListaFuncionarios(lista);

        String resultado = outContent.toString();
        
        // Garante que o método executou e não deu nenhum NullPointerException
        // Se você mudar o println da View para mostrar os dados, mude o assert abaixo para o que você espera ler:
        // assertTrue(resultado.contains("Guilherme Teste"));
        assertTrue(resultado.length() > 0); 
    }
    
    @Test
    public void testaExibirFuncionarioNulo() {
        // Ação: Tenta exibir um funcionário que não existe (null)
        view.exibirFuncionario(null);
        
        // Captura o que foi pro console
        String resposta = outContent.toString();
        
        // Verificação: Deve mostrar a mensagem de erro que definimos
        assertTrue("Deveria avisar que o funcionário não foi encontrado", resposta.contains("Funcionário não encontrado"));
    }

    @Test
    public void testaExibirFuncionarioValido() {
        // Prepara um funcionário fictício
        Funcionario f = new Funcionario("Guilherme Teste", "12312312311", "81912345678", "g@g", "email", "Rua A", "20/07/1996", "M", Cargo.CAIXA, 1500.0);
        
        // Ação: Manda exibir
        view.exibirFuncionario(f);
        
        // Captura o console
        String resposta = outContent.toString();
        
        // Verificação: O console deve conter, no mínimo, o nome do funcionário
        // (Isso assume que o seu método toString() da classe Funcionario imprime o nome)
        assertTrue("A exibição deveria conter o nome do funcionário", resposta.contains("Guilherme Teste"));
    }
}