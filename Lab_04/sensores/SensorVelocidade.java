package sensores;
import excecoes.VelocidadeMaximaAtingidaException;
import robots.terrestre.RoboTerrestre;
public class SensorVelocidade extends Sensor<Double>{

    public SensorVelocidade(double raio, String nomeSensor){
        super(raio, nomeSensor);
    }

    // Aqui o atributo[0] será o deltaX e o atributo[1] será o deltaY e o atributo[2] é o próprio robo terrestre
    /**
     * Monitoramento da velocidade do robô terrestre
     * @param atributo lista de objetos diversos
     * @implNote atributo[0] e atributo[1] são o quanto o robô deseja andar em x e em y respectivamente
     * @implNote atributo[2] é o próprio robô terrestre
     * @return a velocidade do robô
     */
    @Override
    public Double monitorar(Object... atributo){//Método abstrato para monitorar a altura do robô
        this.consumirBateria(5); // Consome 5% da bateria a cada monitoramento
        return (Math.pow((float) atributo[0],2) + Math.pow((float) atributo[1],2) / ((RoboTerrestre) atributo[2]).getTempoLocomocao()); 
    }
    /**
     * Analisa quão rápido o robô terrestre tentou se mover
     * @param velocidade
     * @param velocidadeMax
     * @return a porcentagem de velocidade do robô em relação à velocidade máxima que ele pode andar
     * @throws VelocidadeMaximaAtingidaException Caso o robô queira ir mais rápido do que é permitido para ele
     */
    public double porcentoVelocidade(double velocidade, double velocidadeMax) throws VelocidadeMaximaAtingidaException{
        if (((velocidade * 100)/ velocidadeMax) >= 100){
            throw new VelocidadeMaximaAtingidaException("");
        }
        return (double) ((velocidade * 100) / velocidadeMax);
    }

    public boolean isMuitoRapido(double taxaVelocidade){
        if (taxaVelocidade >= 90){
            return true;
        }else{
            return false;
        }
    }
}