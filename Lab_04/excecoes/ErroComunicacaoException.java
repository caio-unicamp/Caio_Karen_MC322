package excecoes;
/**
 * Exceção lançada quando ocorre um erro de comunicação. <p>
 * Esta exceção é usada para indicar que houve um problema na comunicação
 * entre os robôs.
 */
public class ErroComunicacaoException extends Exception {
    public ErroComunicacaoException(String message) {
        super("Erros de comunicações são comuns entre relacionamentos, mas precisam ser tratados antes de piorar tudo. Normalmente um terapetua ajuda bastante.");
    }
}
