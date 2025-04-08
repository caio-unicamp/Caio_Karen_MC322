abstract class Sensor<T> {
    double raio;
    
    Sensor(double){
        
    }
    
    abstract T monitorar();
}