package robots;
import ambiente.*;
import missions.*;

public abstract class AgenteInteligente extends Robo{
    protected Missao missao;
    
    public AgenteInteligente(String idRobo, String direcaoRobo, int x, int y, int z) {
        super(idRobo, direcaoRobo, x, y, z);
    }

    public void definirMissao(Missao missao){
        this.missao = missao;
    }
}