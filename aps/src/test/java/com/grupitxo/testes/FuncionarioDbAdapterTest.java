package com.grupitxo.testes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;

//imports pro junit (framework de teste) e jdbc (pro sqlite)
import org.junit.Before;
import org.junit.Test;

//nossas classes
import com.grupitxo.classes.DatabaseConfig;
import com.grupitxo.classes.Funcionario;
import com.grupitxo.classes.FuncionarioDbAdapter;
import com.grupitxo.classes.FuncionarioStorageStrategy;
import com.grupitxo.enums.Cargo;

public class FuncionarioDbAdapterTest {
    private Connection conn;
    private FuncionarioStorageStrategy adapter;

    @Before
    public void setUp() throws Exception {
        // nossa classe de configuração inicia o banco
        DatabaseConfig.inicializarBanco();
        // usa a classe de config pra abrir a conexão
        this.conn = DatabaseConfig.getConnection();
        // cria um objeto de tipo StorageStrategy usando o construtor de DbAdapter (a coisa da implementação da interface)
        this.adapter = new FuncionarioDbAdapter(conn);
    }

    @Test
    public void testaSalvarFuncionario() throws Exception {
        // qtd antes pra comparação
        int qtdAntes = adapter.getQtd();

        // cria um obj funcionario
        Funcionario funcionarioNovo = new Funcionario("asdasd", "12312312311", "81999999999", "gui@gui", "email", "radsad", "20/07/1996", "masc", Cargo.CAIXA, 1231d);
        
        // tenta salvar
        boolean salvou = adapter.salvar(funcionarioNovo);

        // verificações
        assertTrue(salvou);
        assertEquals(qtdAntes + 1, adapter.getQtd());
    }

    @Test
    public void testaBuscarFuncionarioPorId() throws Exception {
        // criando um funcionario teste
        Funcionario fNovo = new Funcionario("Teste Busca ID", "12312312311", "81999999999", "gui@gui", "email", "radsad", "20/07/1996", "masc", Cargo.CAIXA, 1000d);
        
        // salva no banco
        adapter.salvar(fNovo);
        
        // pega o ID direto do BD pra usarmos na busca
        Funcionario fSalvoNoBanco = adapter.buscarPorNome("Teste Busca ID");
        int idGerado = fSalvoNoBanco.getIdFuncionario();
        
        // busca usando o ID pegado do banco
        Funcionario fEncontrado = adapter.buscarPorId(idGerado);
        
        // verificações
        assertNotNull("O funcionário deveria ter sido encontrado no BD", fEncontrado);
        assertEquals("Teste Busca ID", fEncontrado.getNome());
        assertEquals("12312312311", fEncontrado.getCpf());
        assertEquals(1000d, fEncontrado.getSalario(), 0.01); //delta é a margem de erro na comparação de doubles
    }
    
    @Test
    public void testaBuscarFuncionarioPorNome() throws Exception {
        // cria um funcionario novo
        Funcionario fNovo = new Funcionario("Guilherme Teste", "33344433322", "81999999997", "b@b", "email", "end", "20/07/1996", "masc", Cargo.SOCIO, 1500d);
        adapter.salvar(fNovo);

        // busca por um pedaço do nome pra testar o LIKE no BD
        Funcionario fEncontrado = adapter.buscarPorNome("Guilherme");

        // verificações
        assertNotNull("Deveria encontrar Guilherme", fEncontrado);
        assertEquals("Guilherme Teste", fEncontrado.getNome());
    }
    
    @Test
    public void testaAtualizarFuncionario() throws Exception {
        // cria um funcionario novo
        Funcionario fNovo = new Funcionario("Teste Update", "11122233344", "81999996997", "b@b", "email", "end", "20/07/1996", "masc", Cargo.SOCIO, 1500d);
        adapter.salvar(fNovo);

        // pega o ID gerado no BD
        Funcionario fParaAtualizar = adapter.buscarPorNome("Teste Update");
        
        // muda nome e salário e atualiza
        fParaAtualizar.setNome("Teste Update Modificado");
        fParaAtualizar.setSalario(2500d);
        Funcionario fAtualizado = adapter.atualizar(fParaAtualizar);

        // verificações
        assertNotNull("O método atualizar não deve retornar null em caso de sucesso", fAtualizado);
        Funcionario fConferencia = adapter.buscarPorId(fAtualizado.getIdFuncionario());
        assertEquals("Teste Update Modificado", fConferencia.getNome());
        assertEquals(2500d, fConferencia.getSalario(), 0.01);
    }

    @Test
    public void testaRemoverFuncionario() throws Exception {
        // cria um funcionario novo
        Funcionario fNovo = new Funcionario("Teste Remover", "55544433322", "81912345678", "d@d", "email", "end", "20/07/1996", "masc", Cargo.ESTOQUISTA, 3000d);
        adapter.salvar(fNovo);

        // pega o ID gerado no BD
        Funcionario fParaRemover = adapter.buscarPorNome("Teste Remover");
        int idParaApagar = fParaRemover.getIdFuncionario();

        // tenta remover
        boolean removeu = adapter.remover(fParaRemover);

        // verificações
        assertTrue("O método remover deveria retornar true", removeu);
        Funcionario fDeletado = adapter.buscarPorId(idParaApagar);
        assertNull("O funcionário não deveria mais existir no banco", fDeletado);
    }
}
