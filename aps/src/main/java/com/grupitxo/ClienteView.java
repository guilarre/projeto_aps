		import java.util.Scanner;

public class ClienteView {

    Scanner sc = new Scanner(System.in);

    public String pedirNome() {
        System.out.println("Digite o nome do cliente:");
        return sc.nextLine();
    }

    public String pedirCpf() {
        System.out.println("Digite o CPF do cliente:");
        return sc.nextLine();
    }
    public String pedirTelefone(){
        System.out.println("Digite o telefone do cliente: ");
		return sc.nextLine();
    }
    public String pedirEmail(){
        System.out.println("Digite o email do cliente: ");
		return sc.nextLine();
    }
	public String pedirPreferencia(){
	    System.out.println("Digite a preferência de comunicação do cliente: ");
		return sc.nextLine();
	}		
	public String pedirEndereco(){
	    System.out.println("Digite o endereco residencial do cliente: ");
		return sc.nextLine();
	}		
	public String pedirDataniver(){
	    System.out.println("Digite o aniversário do cliente (e.g. 12/12/2012): ");
		return sc.nextLine();
	}		
	public String pedirGenero(){
			System.out.println("Digite o gênero do cliente: ");
			return sc.nextLine();
	}
	public void mostrarMensagem(String msg) {
    System.out.println(msg);
    }
}		
	