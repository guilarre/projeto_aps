import com.grupitxo.classes.Cliente;

public class ClienteController {

    private ClienteView view;
    
    public ClienteController(ClienteView view) {
        this.view = view;
    }

    public void cadastrarCliente() {

        String nome = view.pedirNome();
        String cpf = view.pedirCpf();
        String telefone = view.pedirTelefone();
        String email = view.pedirEmail();
        String preferencia = view.pedirPreferencia();
        String endereco = view.pedirEndereco();
        String dataAniversario = view.pedirDataNiver();
        String genero = view.pedirGenero();

        Cliente cliente = new Cliente(
            nome,
            cpf,
            telefone,
            email,
            preferencia,
            endereco,
            dataAniversario,
            genero,
            true
        );

        view.mostrarMensagem("Cliente criado com sucesso!");    }
}