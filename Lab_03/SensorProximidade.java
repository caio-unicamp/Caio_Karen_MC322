public class SensorProximidade extends Sensor<Float>{

    public SensorProximidade(double raio){
        super(raio);
    }
    
    @Override
    //Método abstrato para monitorar a altura do robô
    Float monitorar(){//Método abstrato para monitorar a altura do robô
        return null;   
    }
}