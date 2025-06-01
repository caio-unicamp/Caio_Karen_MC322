package interfaces;
import sensores.SensorVelocidade;
/**
 * Interface para robôs que possuem controle de velocidade,
 * permitindo a definição de uma velocidade máxima e a obtenção de seu
 * sensor de velocidade.
 */
public interface Veloz {
    /**
     * Verifica a velocidade máxima que o robô pode atingir.
     * @return A velocidade máxima do robô.
     */
    int getVelocidadeMaxima();
    /**
     * Define a velocidade máxima que o robô pode atingir.
     * @param velMax A nova velocidade máxima.
     */
    void setVelMaxima(int velMax);
    /**
     * Retorna o sensor de velocidade associado ao robô.
     * @return O sensor de velocidade do robô.
     */
    SensorVelocidade getSensorVelocidade();

}