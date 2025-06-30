package sensores;
import excecoes.VelocidadeMaximaAtingidaException;
import interfaces.Fiscalizador;
import robot.terrestre.RoboTerrestre;
public class SensorVelocidade extends Sensor<Double> implements Fiscalizador{

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

        float x = ((Integer) atributo[0]).floatValue();
        float y = ((Integer) atributo[1]).floatValue();
        RoboTerrestre robo = (RoboTerrestre) atributo[2];
        
        return (Math.pow(x, 2) + Math.pow(y, 2)) / robo.getTempoLocomocao(); 
    }
    /**
     * Analisa quão rápido o robô terrestre tentou se mover
     * @param velocidade
     * @param velocidadeMax
     * @return a porcentagem de velocidade do robô em relação à velocidade máxima que ele pode andar
     * @throws VelocidadeMaximaAtingidaException Caso o robô queira ir mais rápido do que é permitido para ele
     */
    public double porcentoVelocidade(double velocidade, double velocidadeMax) throws VelocidadeMaximaAtingidaException{
        if (((velocidade * 100)/ velocidadeMax) > 100){
            throw new VelocidadeMaximaAtingidaException("Você sabia que estava tentando ir a " + velocidade + "km/h? Infelizmente não poderei permitir, vai ter que tentar mover ele de novo");
        }
        return (double) ((velocidade * 100) / velocidadeMax);
    }

    public boolean isMuito(double taxaVelocidade){
        if (taxaVelocidade >= 90){
            return true;
        }else{
            return false;
        }
    }
}