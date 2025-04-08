abstract class Sensor<T> {
    double raio;
    
    Sensor(double raio){
        this.raio = raio;
        
    }
    
    abstract T monitorar();
}