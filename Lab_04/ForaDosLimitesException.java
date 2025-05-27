/**
 * Exceção lançada quando uma entidade tenta passar dos limites definidos. <p>
 * Esta exceção é usada para indicar que uma ação ou movimento está fora dos
 * limites permitidos, como tentar mover um robô para fora do ambiente
 */
public class ForaDosLimitesException extends Exception {
    public ForaDosLimitesException(String message) {
        super(message);
    }
}
