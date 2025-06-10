package excecoes;
import sensores.Sensor;
/**
 * Exceção lançada quando um robô está desligado. <p>
 * Esta exceção é usada para indicar que uma ação foi tentada em um robô que
 * não está ligado, como tentar movê-lo ou realizar uma operação.
 */
public class SensorDesligadoException extends Exception{
    /**
     * Já existe uma mensagem padrão para os sensores descarregados, basta passar qual o sensor e o nome do robô que está sendo tratado
     * @param sensor
     * @param nomeRobo
     */
    public SensorDesligadoException(Sensor<?> sensor, String nomeRobo){
        super("A bateria do " + sensor.getNomeSensor() + " do "+ nomeRobo + " acabou, tente recarregar antes de prosseguir com a simulação.");
    }
}