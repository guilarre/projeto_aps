package com.grupitxo;

//imports pro junit (framework de teste) e jdbc (pro sqlite)
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.Connection;
//nossas classes
import com.grupitxo.classes.DatabaseConfig;
import com.grupitxo.classes.Produto;
import com.grupitxo.classes.ProdutoDbAdapter;
import com.grupitxo.enums.Categoria;

public class ProdutoDbAdapterTest {
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        // nossa classe de configuração inicia o banco
        DatabaseConfig.inicializarBanco();
        // usa a classe de config pra abrir a conexão
        this.conn = DatabaseConfig.getConnection();
    }

    @Test
    public void salvarProduto() throws Exception {
        // cria um obj adapter
        ProdutoDbAdapter adapter = new ProdutoDbAdapter(conn);

        // qtd antes pra comparação
        int qtdAntes = adapter.getQtd();

        // cria um obj funcionario
        Produto produtoNovo = new Produto("00001", "Calça jeans", "calça jeans azul fem 40", 300.01, Categoria.CALCA, 50, false);

        // tenta salvar
        boolean salvou = adapter.salvar(produtoNovo);

        // verificação
        assertTrue(salvou);
        assertEquals(qtdAntes + 1, adapter.getQtd());
    }
}
