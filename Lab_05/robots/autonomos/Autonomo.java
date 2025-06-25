package robots.autonomos;
import robots.Robo;
import ambiente.*;
import excecoes.*;
import missions.*;

public abstract class Autonomo extends Robo{
    protected Missao missao; //Cada agente inteligente tem uma missão associada
    /**
     * Construtor da classe Autonomo.
     * Este construtor inicializa o agente inteligente com um ID, direção e coordenadas no ambiente.
     * @param idRobo representando seu nome
     * @param direcaoRobo 
     * @param x
     * @param y
     * @param z
     */
    public Autonomo(String idRobo, String direcaoRobo, int x, int y, int z){
        super(idRobo, direcaoRobo, x, y, z);
    }
    /**
     * Executa a missão do agente inteligente no ambiente.
     * @param ambiente 
     */
    public abstract void executarMissao(Ambiente ambiente)throws SensorDesligadoException, RoboDesligadoException;
    /**
     * Define a missão do agente inteligente.
     * @param missao
     */
    public void setMissao(Missao missao){
        this.missao = missao;
    }
    /**
     * Verifica se o agente inteligente tem uma missão definida.
     * @return true se tem missão, false caso contrário.
     */
    public boolean temMissao() {
        return this.missao != null;
    }

}