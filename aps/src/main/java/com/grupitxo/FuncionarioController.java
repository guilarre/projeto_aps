import com.grupitxo.classes.Funcionario;

public class FuncionarioController {

    private FuncionarioView view;

    public FuncionarioController(FuncionarioView view) {
        this.view = view;
    }

    public void cadastrarFuncionario() {

        String nome = view.pedirNome();
        String cpf = view.pedirCpf();
        String telefone = view.pedirTelefone();
        String email = view.pedirEmail();
        String preferencia = view.pedirPreferencia();
        String endereco = view.pedirEndereco();
        String dataAniversario = view.pedirDataNiver();
        String genero = view.pedirGenero();

        Funcionario funcionario = new Funcionario(
            nome,
            cpf,
            telefone,
            email,
            preferencia,
            endereco,
            dataAniversario,
            genero,
            null,
            0.0,
            true
        );

        view.mostrarMensagem("Funcionário criado com sucesso!");
    }
}