public class SensorAereo extends Sensor<Float>{

    public SensorAereo(double raio){
        super(raio);
    }
    
    @Override
    //Método abstrato para monitorar a altura do robô
    Float monitorar(){//Método abstrato para monitorar a altura do robô
        return null;   
    }
}