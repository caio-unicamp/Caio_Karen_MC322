public class SensorAltitude extends Sensor<Integer>{

    public SensorAltitude(double raio){
        super(raio);
    }

    @Override
    public Integer monitorar(Object... atributo){//Método abstrato para monitorar a altura do robô
        this.consumirBateria(5); // Consome 5% da bateria a cada monitoramento
        return (int) atributo[2];   
    }
    
    public double porcentoAltura(int altura, int alturaMax){
        return (double) ((altura * 100) / alturaMax); //Retorna a porcentagem de altura do robô em relação a altura máxima do ambiente
    }

    public boolean isMuitoAlto(double taxaAltura){
        if (taxaAltura >= 90){
            return true;
        }else{
            return false;
        }
    }
}