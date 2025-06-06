package interfaces;
import excecoes.*;
/**
 * Interface para robôs com capacidade de usar sensores.
 */
public interface Sensoreavel {
    /**
     * Aciona os sensores do robô.
     * @throws RoboDesligadoException se o robô tentar usar os sensores enquanto estiver desligado.
     * @throws SensorDesligadoException se algum sensore estiver descarregado.
     */
    void acionarSensores() throws RoboDesligadoException, SensorDesligadoException; 
}
