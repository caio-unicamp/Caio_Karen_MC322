import java.util.ArrayList;

public class Robo {
    private String nome;    //nome do robô
    private String direcao;   //direção do robô
    private int posicaoX;   //coordenada X no Ambiente
    private int posicaoY;   //coordenada Y no Ambiente
    private int posicaoZ;   //coordenada Z no Ambiente no caso de robôs aéreos
    private Ambiente ambiente; 
    private ArrayList<Robo> listaRobosAtivos; //lista de robos ativos

    public Robo (String nomeRobo, String direcaoRobo, int x, int y, int z, Ambiente ambiente) {    //Construtor para inicializar os atributos do robô aéreo;
        this.nome = nomeRobo;
        this.direcao = direcaoRobo;
        this.posicaoX = x;
        this.posicaoY = y;
        this.posicaoZ = z;
        this.listaRobosAtivos = ambiente.getLista();
    }

    public void mover(int deltaX, int deltaY) { //Atualiza a posicão do robô de modo que ele anda primeiro no eixo x e depois no eixo y
        //Analisa se o passo do robô é positivo ou negativo
        int passoX = 0, passoY = 0;
        if (deltaX > 0){
            passoX = 1;
        }else if (deltaX < 0){
            passoX = -1;
        }
        if (deltaY > 0){
            passoY = 1;
        }else if (deltaY < 0){
            passoY = -1;
        }
        
        if (deltaX + this.posicaoX > 0 && deltaX + this.posicaoX < ambiente.getLimites()[0] && !identificarRobo(this.posicaoX + passoX, this.posicaoY, 0, this.nome)){ //Segue recursivamente no eixo x para analisar caso identifique algum obstáculo
            this.posicaoX += passoX;
            mover(deltaX - passoX, deltaY);
        }else if (deltaY + this.posicaoY > 0 && deltaY + this.posicaoY < ambiente.getLimites()[1] && !identificarRobo(this.posicaoX, this.posicaoY + passoY, 0,this.nome)){ //Depois de ter andado tudo em x ele segue recursivamente no eixo y analisando caso identifique algum obstáculo
            this.posicaoY += passoY;
            mover(0, deltaY - passoY); //Ele só começa a se mover em y depois de ter movido tudo em x
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
    
    public boolean identificarRobo(int x, int y, int z, String nome){
        for (Robo robo: listaRobosAtivos){ //Para cada Robô na lista de robôs (obviamente não sendo o robô que está tentando identificar um obstáculo), ele analisa se a posição que o robô em questão quer ir já está ocupada 
            if (robo.getPosicao()[0] == x && robo.getPosicao()[1] == y && robo.getPosicao()[2] == z && robo.getNome() != nome){
                return true;
            }
        }return false;
    }

    public void setPosicao(int x, int y, int z){ //Função para setar a posição dos robôs
        this.posicaoX = x;
        this.posicaoY = y;
        this.posicaoZ = z;
    }
}