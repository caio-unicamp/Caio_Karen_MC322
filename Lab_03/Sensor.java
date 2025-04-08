abstract class Sensor<T> {
    private double raio;
    
    Sensor(double raio){
        this.raio = raio;
    }
    
    abstract T monitorar();
}