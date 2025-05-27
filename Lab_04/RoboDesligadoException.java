/**
 * Exceção lançada quando um robô está desligado. <p>
 * Esta exceção é usada para indicar que uma ação foi tentada em um robô que
 * não está ligado, como tentar movê-lo.
 */
public class RoboDesligadoException extends Exception {
    public RoboDesligadoException(String nomeRobo) {
        super("Você desliga o "+ nomeRobo + " e depois quer que eu faça magia pra ligar ele de novo? Se quiser tentar movê-lo (obviamente) terá de ligá-lo novamente.");
    }
}
