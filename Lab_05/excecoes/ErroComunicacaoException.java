package excecoes;
/**
 * Exceção lançada quando ocorre um erro de comunicação. <p>
 * Esta exceção é usada para indicar que houve um problema na comunicação
 * entre os robôs.
 */
public class ErroComunicacaoException extends Exception {
    /**
     * Passe a mensagem referente ao erro de comunicação
     * @param message
     */
    public ErroComunicacaoException(String message) {
        super(message);
    }
}
