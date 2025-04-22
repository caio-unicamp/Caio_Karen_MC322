public class SensorAltitude extends Sensor<Integer>{

    public SensorAltitude(double raio){
        super(raio);
    }

    @Override
    public Integer monitorar(Object... atributo){//Método abstrato para monitorar a altura do robô
        return 0;   
    }
    
}