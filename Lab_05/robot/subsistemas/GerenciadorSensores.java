package robot.subsistemas;

import java.util.ArrayList;
import java.util.List;

import enums.EstadoRobo;
import excecoes.RoboDesligadoException;
import excecoes.SensorDesligadoException;
import robot.*;
import sensores.*;

public class GerenciadorSensores {
    private final Robo roboPai;
    private final List<Sensor<?>> listaSensores;

    private SensorProximidade sensorProximidade;
    private SensorAltitude sensorAltitude;
    private SensorVelocidade sensorVelocidade;

    public GerenciadorSensores(Robo roboPai) {
        this.roboPai = roboPai;
        this.listaSensores = new ArrayList<>();
    }
    /**
     * Adiciona um sensor ao gerenciador de sensores do robô.
     * @param sensor
     */
    public void adicionarSensor(Sensor<?> sensor) {
        if (sensor instanceof SensorProximidade) {
            this.sensorProximidade = (SensorProximidade) sensor;
        } else if (sensor instanceof SensorAltitude) {
            this.sensorAltitude = (SensorAltitude) sensor;
        } else if (sensor instanceof SensorVelocidade) {
            this.sensorVelocidade = (SensorVelocidade) sensor;
        }
        listaSensores.add(sensor);
    }
    /**
     * Adiciona um sensor ao gerenciador de sensores do robô.
     * @param sensor
     */
    public void removerSensores(Sensor<?> sensor) {
        if (sensor instanceof SensorProximidade) {
            this.sensorProximidade = (SensorProximidade) sensor;
        } else if (sensor instanceof SensorAltitude) {
            this.sensorAltitude = (SensorAltitude) sensor;
        } else if (sensor instanceof SensorVelocidade) {
            this.sensorVelocidade = (SensorVelocidade) sensor;
        }
        listaSensores.remove(sensor);
    }
    /**
     * Método que aciona os sensores do robô, verificando se o robô está ligado e se os sensores estão com bateria.
     * @throws SensorDesligadoException
     * @throws RoboDesligadoException
     */
    public void acionarSensores() throws SensorDesligadoException, RoboDesligadoException {
        if (roboPai.getEstadoRobo().equals(EstadoRobo.DESLIGADO)){ //Confere se o robô está desligado
            throw new RoboDesligadoException(roboPai.getNome());
        }
        for (Sensor<?> sensor : listaSensores) { //Percorre a lista de sensores do robô
            if (sensor.getBateria() == 0){ //Se a bateria do sensor acabar, ele não consegue mais monitorar o ambiente
                throw new SensorDesligadoException(sensor, roboPai.getNome());
            }else{
                continue;
            }
        }
    }

    public SensorProximidade getSensorProximidade() {
        return sensorProximidade;
    }
    public SensorAltitude getSensorAltitude() {
        return this.sensorAltitude;
    }
    public SensorVelocidade getSensorVelocidade() {
        return this.sensorVelocidade;
    }
}