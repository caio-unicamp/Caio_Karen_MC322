package robot.terrestre;
import ambiente.*;
import excecoes.*;
import interfaces.Veloz;
import sensores.*;
import robot.Robo;

public class RoboTerrestre extends Robo implements Veloz{
    private int velocidadeMaxima; //Velocidade máxima permitida para o robô
    private int tempoLocomocaoTerrestre; //Tempo que o robô demora em uma locomoção
    
    public RoboTerrestre(String nome, String direcao, int posicaoX, int posicaoY, int velocidadeMaxima, int tempoLocomocaoTerrestre){
        super(nome, direcao, posicaoX, posicaoY, 0); //Herança da classe robô
        this.velocidadeMaxima = velocidadeMaxima;
        this.tempoLocomocaoTerrestre = tempoLocomocaoTerrestre; 
    }
    /**
     * Movimentação dos robôs terrestres
     * @throws ErroComunicacaoException 
     */
    @Override
    public void mover(int deltaX, int deltaY, Ambiente ambiente) throws SensorDesligadoException, RoboDesligadoException, ColisaoException, ErroComunicacaoException {
        super.mover(deltaX, deltaY, ambiente); 
        //Essa função será chamada junto da velMaxAtingida na main para que ele só se mova no caso de não ter sido atingido o limite de velocidade
    }
    /**
     * Verifica qual a velocidade máxima que o robô terrestre pode assumir
     * @return
     */
    public int getVelocidadeMaxima(){ 
        return velocidadeMaxima;
    }
    /**
     * Altera a velocidade máxima que o robô terrestre pode atingir
     * @param velMax
     */
    public void setVelMaxima(int velMax){ 
        this.velocidadeMaxima = velMax;
    }
    /**
     * Verifica qual o tempo de locomoção do robô terrestre
     * @return tempo de locomoção
     */
    public int getTempoLocomocao(){
        return tempoLocomocaoTerrestre;
    }
    /**
     * Seta o tempo de locomoção do robô terrestre
     * @param novoTempo
     */
    public void setTempoLocomocao(int novoTempo){ 
        this.tempoLocomocaoTerrestre = novoTempo;
    }
    /**
     * Busca pelo sensor de velocidade do robô
     * @return o sensor de velocidade que ele possui
     */
    public SensorVelocidade getSensorVelocidade(){ 
        return gerenciadorSensores.getSensorVelocidade(); //Busca pelo sensor de velocidade do robô
    }
} 