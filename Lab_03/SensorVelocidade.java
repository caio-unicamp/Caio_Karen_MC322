public class SensorVelocidade extends Sensor<Double>{

    public SensorVelocidade(double raio){
        super(raio);
    }

    @Override
    public Double monitorar(Object... atributo){//Método abstrato para monitorar a altura do robô
        return (Math.pow((float) atributo[0],2) + Math.pow((float) atributo[1],2) / ((RoboTerrestre) atributo[4]).getTempoLocomocao()); //Retorna a velocidade do robô
    }
    
    public double porcentoVelocidade(double velocidade, double velocidadeMax){
        return (double) ((velocidade * 100) / velocidadeMax); //Retorna a porcentagem de velocidade do robô em relação a velocidade máxima do que ele pode andar
    }
    
}