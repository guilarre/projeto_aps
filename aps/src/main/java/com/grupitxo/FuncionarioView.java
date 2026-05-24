import java.util.Scanner;

public class FuncionarioView {

    Scanner sc = new Scanner(System.in);

    public String pedirNome() {
        System.out.println("Digite o nome do funcionário:");
        return sc.nextLine();
    }

    public String pedirCpf() {
        System.out.println("Digite o CPF do funcionário:");
        return sc.nextLine();
    }

    public String pedirTelefone() {
        System.out.println("Digite o telefone do funcionário:");
        return sc.nextLine();
    }

    public String pedirEmail() {
        System.out.println("Digite o email do funcionário:");
        return sc.nextLine();
    }

    public String pedirPreferencia() {
        System.out.println("Digite a preferência de comunicação:");
        return sc.nextLine();
    }

    public String pedirEndereco() {
        System.out.println("Digite o endereço:");
        return sc.nextLine();
    }

    public String pedirDataNiver() {
        System.out.println("Digite a data de nascimento:");
        return sc.nextLine();
    }

    public String pedirGenero() {
        System.out.println("Digite o gênero:");
        return sc.nextLine();
    }

    public void mostrarMensagem(String msg) {
        System.out.println(msg);
    }
}