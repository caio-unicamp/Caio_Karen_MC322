package robo;
import ambiente.*;
import excecoes.*;
import sensores.*;
public class RoboTerrestre extends Robo{
    private int velocidadeMaxima; //Velocidade máxima permitida para o robô
    private int tempoLocomocaoTerrestre; //Tempo que o robô demora em uma locomoção
    
    public RoboTerrestre(String nome, String direcao, int posicaoX, int posicaoY, int velocidadeMaxima, int tempoLocomocaoTerrestre){
        super(nome, direcao, posicaoX, posicaoY, 0); //Herança da classe robô
        this.velocidadeMaxima = velocidadeMaxima;
        this.tempoLocomocaoTerrestre = tempoLocomocaoTerrestre; 
    }

    @Override
    public void mover(int deltaX, int deltaY, Ambiente ambiente) throws SensorDesligadoException, RoboDesligadoException, ColisaoException  { //Função sobreposta para mover o robô terrestre
        super.mover(deltaX, deltaY, ambiente); 
        //Essa função será chamada junto da velMaxAtingida na main para que ele só se mova no caso de não ter sido atingido o limite de velocidade
    }

    public int getVelocidadeMaxima(){ //Função para retornar a velocidade máxima do robô
        return velocidadeMaxima;
    }

    public void setVelMaxima(int velMax){ //Função para alterar a velocidade máxima do robô
        this.velocidadeMaxima = velMax;
    }

    public int getTempoLocomocao(){ //Função para ver o tempo de locomoção do robô terrestre
        return tempoLocomocaoTerrestre;
    }

    public void setTempoLocomocao(int novoTempo){ //Função para setar o novo tempo de locomoção do robo terrestre
        this.tempoLocomocaoTerrestre = novoTempo;
    }

    public SensorVelocidade getSensorVelocidade(Robo robo){ //Acessa o sensor de altitude do robô aérero
        SensorVelocidade sensorVelocidade = null;
        for (Sensor<?> sensor : robo.getSensores()){
            if (sensor instanceof SensorVelocidade){
                sensorVelocidade = (SensorVelocidade) sensor;
            }
        }
        return sensorVelocidade;
    }
} 