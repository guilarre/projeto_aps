package com.grupitxo;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.Connection;
import com.grupitxo.classes.DatabaseConfig;
import com.grupitxo.classes.Funcionario;
import com.grupitxo.classes.FuncionarioDbAdapter;
import com.grupitxo.enums.Cargo;

public class FuncionarioDbAdapterTest {
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        // nossa classe de configuração inicia o banco
        DatabaseConfig.inicializarBanco();
        // usa a classe de config pra abrir a conexão
        this.conn = DatabaseConfig.getConnection();
    }

    @Test
    public void salvarFuncionario() throws Exception {
        // cria um obj adapter    
        FuncionarioDbAdapter adapter = new FuncionarioDbAdapter(conn);
        
        // qtd antes pra comparação
        int qtdAntes = adapter.getQtd();

        // cria um obj funcionario
        Funcionario funcionarioNovo = new Funcionario("asdasd", "12312312311", "81999999999", "gui@gui", "email", "radsad", "20/07/1996", "masc", Cargo.CAIXA, 1231d, false);
        
        // tenta salvar
        boolean salvou = adapter.salvar(funcionarioNovo);

        // verificação
        assertTrue(salvou);
        assertEquals(qtdAntes + 1, adapter.getQtd());
    }
}
