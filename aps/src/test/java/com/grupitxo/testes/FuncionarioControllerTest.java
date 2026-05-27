package com.grupitxo.testes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.grupitxo.classes.Funcionario;
import com.grupitxo.classes.FuncionarioController;
import com.grupitxo.classes.FuncionarioView;
import com.grupitxo.classes.FuncionarioViewFactory;
import com.grupitxo.enums.Cargo;

public class FuncionarioControllerTest {

    private FuncionarioController controller;
    private FuncionarioDbAdapterFake adapterFake;
    private FuncionarioView view;
    private FuncionarioViewFactory factory;
    private final InputStream systemInOriginal = System.in;
    
    private Funcionario fTeste;

    @Before
    public void setUp() {
        view = new FuncionarioView();
        factory = new FuncionarioViewFactory();
        adapterFake = new FuncionarioDbAdapterFake();

         fTeste = new Funcionario("Guilherme", "12312322123", "81999999999", "g@g", "email", "Rua A", "20/07/1996", "M", Cargo.CAIXA, 1500.0);

        adapterFake.salvar(fTeste);

        controller = new FuncionarioController(view, factory, adapterFake);
    }

    @After
    public void restoreSystemInput() {
        // Devolve o teclado pro sistema operacional
        System.setIn(systemInOriginal);
    }

    @Test
    public void testaSelecionarFuncionarioPorIdComSucesso() {
        // Pega dinamicamente o ID que o sistema gerou para o funcionárioTeste
        int idGerado = fTeste.getIdFuncionario();

        // Monta a string do teclado usando o ID dinâmico
        String entradaSimulada = "1\n" + idGerado + "\n";
        
        ByteArrayInputStream in = new ByteArrayInputStream(entradaSimulada.getBytes());
        System.setIn(in); 

        Scanner scannerTeste = new Scanner(System.in);

        // Ação
        Funcionario resultado = controller.selecionarFuncionarioParaAcao(scannerTeste);

        // Validação
        assertNotNull("O funcionário não deveria ser nulo", resultado);
        assertEquals("Os IDs devem bater", idGerado, resultado.getIdFuncionario());
    }
}