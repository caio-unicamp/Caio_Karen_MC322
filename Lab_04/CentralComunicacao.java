import java.util.ArrayList;
import java.util.List;

/**
 * Gerencia e armazena o histórico de mensagens trocadas entre robôs.
 */
public class CentralComunicacao {
    /**
     * Lista para armazenar o histórico de mensagens
     */
    private final List<String> mensagens;
    private static CentralComunicacao instancia;

    private CentralComunicacao() {
        mensagens = new ArrayList<>();
    }

    /**
     * Implementação do padrão Singleton para garantir uma única instância da central
     */
    public static CentralComunicacao getInstancia() {
        if (instancia == null) {
            instancia = new CentralComunicacao();
        }
        return instancia;
    }

    /**
     * Registra uma mensagem no histórico. 
     * @param remetente O nome do robô que enviou.
     * @param destinatario O nome do robô que recebeu.
     * @param msg A mensagem.
     */
    public void registrarMensagem(String remetente, String destinatario, String msg) {
        String log = "[De: " + remetente + " | Para: " + destinatario + "]: " + msg;
        mensagens.add(log);
    }

    /**
     * Exibe todas as mensagens registradas no console.
     */
    public void exibirMensagens() { //Atenção!!! PASSAR ESSA PARTE PARA A MAIN
        System.out.println("\n--- Log de Comunicações ---");
        if (mensagens.isEmpty()) {
            System.out.println("Nenhuma mensagem trocada.");
        } else {
            for (String msg : mensagens) {
                System.out.println(msg);
            }
        }
        System.out.println("---------------------------\n");
    }
}