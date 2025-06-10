package robots;
import ambiente.*;
import missions.*;

public abstract class AgenteInteligente extends Robo{
    protected Missao missao;
    /**
     * Construtor da classe AgenteInteligente.
     * Este construtor inicializa o agente inteligente com um ID, direção e coordenadas no ambiente.
     * @param idRobo representando seu nome
     * @param direcaoRobo 
     * @param x
     * @param y
     * @param z
     */
    public AgenteInteligente(String idRobo, String direcaoRobo, int x, int y, int z) {
        super(idRobo, direcaoRobo, x, y, z);
    }
    /**
     * Executa a missão do agente inteligente no ambiente.
     * @param ambiente 
     */
    public abstract void executarMissao(Ambiente ambiente);
    /**
     * Define a missão do agente inteligente.
     * @param missao
     */
    public void definirMissao(Missao missao){
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