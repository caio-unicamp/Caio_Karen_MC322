public class RoboDesligadoException extends Exception {
    public RoboDesligadoException(String nomeRobo) {
        super("Você desliga o "+ nomeRobo + " e depois quer que eu faça magia pra ligar ele de novo? Se quiser tentar movê-lo (obviamente) terá de ligá-lo novamente.");
    }
}
