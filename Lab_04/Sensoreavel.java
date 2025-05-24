/**
 * Interface para robôs com capacidade de usar sensores.
 */
public interface Sensoreavel {
    /**
     * Aciona os sensores do robô.
     * @throws RoboDesligadoException se o robô tentar usar os sensores enquanto estiver desligado.
     */
    void acionarSensores() throws RoboDesligadoException; 
}
