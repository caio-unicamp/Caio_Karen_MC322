public class SensorTerrestre extends Sensor<Float>{

    public SensorTerrestre(double raio){
        super(raio);
    }

    @Override
    Float monitorar(){//Método abstrato para monitorar a altura do robô
        return null;   
    }
    
}