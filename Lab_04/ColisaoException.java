/**
 * Exceção lançada quando ocorre uma colisão. <p>
 * Esta exceção é usada para indicar que um evento de colisão foi detectado
 * e pode ser tratado de maneira específica.
 */
public class ColisaoException extends Exception {
    public ColisaoException(String message) {
        super(message);
    }
}
