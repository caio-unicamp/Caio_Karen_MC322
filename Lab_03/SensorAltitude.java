public class SensorAltitude extends Sensor<Float>{

    public SensorAltitude(double raio){
        super(raio);
    }

    @Override
    Float monitorar(){//Método abstrato para monitorar a altura do robô
        return null;   
    }
    
}