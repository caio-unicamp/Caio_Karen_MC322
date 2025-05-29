package excecoes;
/**
 * Exceção lançada quando um robô está desligado. <p>
 * Esta exceção é usada para indicar que uma ação foi tentada em um robô que
 * não está ligado, como tentar movê-lo.
 */
public class RoboDesligadoException extends Exception {
    /**
     * Passe a mensagem completa do erro do robô desligado
     * @param message
     */
    public RoboDesligadoException(String message) {
        super(message);
    }
}
