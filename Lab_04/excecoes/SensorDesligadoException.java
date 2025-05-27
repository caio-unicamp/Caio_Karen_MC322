public class SensorDesligadoException extends Exception{
    private final String nomeSensor;
    public SensorDesligadoException(Sensor sensor, String nomeRobo){
        super("A bateria do " + nomeSensor + " do "+ nomeRobo + " acabou, tente recarregar antes de prosseguir com a simulação.");
        if (sensor instanceof SensorAltitude){
            nomeSensor = "Sensor de Altitude";
        }else if (sensor instanceof SensorProximidade){
            nomeSensor = "Sensor de Proximidade";
        }else if (sensor instanceof SensorVelocidade){
            nomeSensor = "Sensor de Velocidade";
        }else{
            nomeSensor = "Sensor desconhecido";
        }
    }
}