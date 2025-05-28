package excecoes;
/**
 * Exceção lançada quando um robô terrestre tenta exceder o limite de velocidade permitido para ele.
 */
public class VelocidadeMaximaAtingidaException extends Exception{

    public VelocidadeMaximaAtingidaException(String message){
        super(message);
    }
}