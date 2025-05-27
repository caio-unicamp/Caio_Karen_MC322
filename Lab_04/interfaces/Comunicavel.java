package interfaces;
/**
 * Interface para robôs com capacidade de comunicação.
 */
public interface Comunicavel {
    /**
     * Envia uma mensagem para outro robô comunicável.
     * @param destinatario O robô que receberá a mensagem.
     * @param mensagem O conteúdo da mensagem.
     * @throws RoboDesligadoException se o remetente estiver desligado.
     * @throws ErroComunicacaoException se o destinatário não for comunicável.
     */
    void enviarMensagem(Comunicavel destinatario, String mensagem) throws RoboDesligadoException, ErroComunicacaoException;

    /**
     * Recebe uma mensagem.
     * @param remetente O nome do robô que enviou a mensagem.
     * @param mensagem O conteúdo da mensagem.
     * @throws RoboDesligadoException se o receptor estiver desligado.
     */
    void receberMensagem(String remetente, String mensagem) throws RoboDesligadoException; 
}
