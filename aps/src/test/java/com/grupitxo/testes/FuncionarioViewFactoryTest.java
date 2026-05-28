package com.grupitxo.testes;

// imports pro junit e scanner
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import com.grupitxo.classes.Funcionario;
import com.grupitxo.classes.FuncionarioViewFactory;
// nossas classes
import com.grupitxo.enums.Cargo;

public class FuncionarioViewFactoryTest {
    @Test
    public void testeCriarFuncionarioViaConsole() {
        // inserindo um \n antes por causa do limpador do buffer que roda aqui
        String entradaSimulada = "\nGui\n" + //nome
                                 "12312312311\n" + //cpf
                                 "81999999999\n" + //telefone
                                 "gui@gui\n" + //email
                                 "email\n" + //preferenciaComunicacao
                                 "Rua X\n" + //endereco
                                 "20/07/1996\n" + //aniversario
                                 "masc\n" + //genero
                                 "2\n" + //cargo (2 = Caixa)
                                 "1231.00\n"; //salario

        // converte a string pra um formato que o scanner entende (InputStream)
        InputStream in = new ByteArrayInputStream(entradaSimulada.getBytes());
        Scanner scannerSimulado = new Scanner(in);

        // 2. Ação: Chama a fábrica passando nosso scanner falso
        FuncionarioViewFactory factory = new FuncionarioViewFactory();
        Funcionario f = factory.criarFuncionarioNovo(scannerSimulado);

        // 3. Verificação: O objeto retornado deve conter os dados exatos digitados
        assertNotNull("O funcionário não deveria ser nulo", f);
        assertEquals("Gui", f.getNome());
        assertEquals("12312312311", f.getCpf());
        assertEquals(Cargo.CAIXA, f.getCargo());
        assertEquals(1231.0, f.getSalario(), 0.001); // O 3º parâmetro é a margem de erro pro double
    }
}
