package sensores;
import robo.*;
public class SensorVelocidade extends Sensor<Double>{

    public SensorVelocidade(double raio, String nomeSensor){
        super(raio, nomeSensor);
    }

    // Aqui o atributo[0] será o deltaX e o atributo[1] será o deltaY e o atributo[2] é o próprio robo terrestre
    @Override
    public Double monitorar(Object... atributo){//Método abstrato para monitorar a altura do robô
        this.consumirBateria(5); // Consome 5% da bateria a cada monitoramento
        return (Math.pow((float) atributo[0],2) + Math.pow((float) atributo[1],2) / ((RoboTerrestre) atributo[2]).getTempoLocomocao()); //Retorna a velocidade do robô
    }
    
    public double porcentoVelocidade(double velocidade, double velocidadeMax){
        return (double) ((velocidade * 100) / velocidadeMax); //Retorna a porcentagem de velocidade do robô em relação a velocidade máxima que ele pode andar
    }

    public boolean isMuitoRapido(double taxaVelocidade){
        if (taxaVelocidade >= 90){
            return true;
        }else{
            return false;
        }
    }

    public boolean velMaxAtingida(double velocidade, double velocidadeMax){
        if (porcentoVelocidade(velocidade, velocidadeMax) >= 100){   
            return false;
        }else{
            return true;
        }
    }
}