package ambiente;
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
     * Verifica o histórico de mensagens formatado.
     * @return lista das mensagens
     */
    public List<String> getMensagensFormatadas() {
        List<String> logFormatado = new ArrayList<>();
        logFormatado.add("\n--- Log de Comunicações ---");
        if (mensagens.isEmpty()) {
            logFormatado.add("Nenhuma mensagem trocada.");
        } else {
            for (String msg : mensagens) {
                logFormatado.add(msg);
            }
        }
        logFormatado.add("---------------------------\n");
        return logFormatado;
    }
}