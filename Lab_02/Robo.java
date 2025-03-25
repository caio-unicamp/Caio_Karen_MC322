import java.util.ArrayList;

public class Robo {
    private String nome;    //nome do robô
    private String direcao;   //direção do robô
    private int posicaoX;   //coordenada X no Ambiente
    private int posicaoY;   //coordenada Y no Ambiente
    private int posicaoZ;   //coordenada Z no Ambiente no caso de robôs aéreos
    private ArrayList<Robo> listaRobosAtivos; //lista de robos ativos

    public Robo (String nomeRobo, String direcaoRobo, int x, int y, Ambiente ambiente) {    //Construtor para inicializar os atributos do robô terrestre;
        this.nome = nomeRobo;
        this.direcao = direcaoRobo;
        this.posicaoX = x;
        this.posicaoY = y;
        this.listaRobosAtivos = ambiente.getLista();
    }

    public Robo (String nomeRobo, String direcaoRobo, int x, int y, int z, Ambiente ambiente) {    //Construtor para inicializar os atributos do robô aéreo;
        this.nome = nomeRobo;
        this.direcao = direcaoRobo;
        this.posicaoX = x;
        this.posicaoY = y;
        this.posicaoZ = z;
        this.listaRobosAtivos = ambiente.getLista();
    }

    public void mover(int deltaX, int deltaY) { //Atualiza a posicão do robô de modo que ele anda primeiro no eixo x e depois no eixo y
        if (deltaX > 0 && !identificarRobo(this.posicaoX + 1, this.posicaoY)){ //Segue recursivamente no eixo x para analisar caso identifique algum obstáculo
            this.posicaoX += 1;
            mover(deltaX - 1, deltaY);
        }else if (deltaY > 0 && !identificarRobo(this.posicaoX, this.posicaoY + 1)){ //Depois de ter andado tudo em x ele segue recursivamente no eixo y analisando caso identifique algum obstáculo
            this.posicaoY += 1;
            mover(0, deltaY - 1);
        }
    }

    public String getNome(){ //Retorna o nome do robô
        return nome;
    }

    public int[] getPosicao(){ //Retorna a posição do robô
        int[] posicao = {this.posicaoX, this.posicaoY, this.posicaoZ};
        return posicao;
    }

    public String getDirecao(){ //Retorna a direção que o robô está encarando
        return direcao;
    }
    
    //Fazer o método identificar obstáculo!!!
    public boolean identificarRobo(int x, int y){
        if ()
        return true;
    }
}