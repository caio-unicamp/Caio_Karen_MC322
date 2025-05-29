package excecoes;
/**
 * Exceção lançada quando um robô está desligado. <p>
 * Esta exceção é usada para indicar que uma ação foi tentada em um robô que
 * não está ligado, como tentar movê-lo.
 */
public class RoboDesligadoException extends Exception {
    public RoboDesligadoException(String message) {
        super(message);
    }
}
