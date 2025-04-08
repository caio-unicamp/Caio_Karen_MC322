public class Robo {
    private String nome;    //nome do robô
    private String direcao;   //direção do robô
    private int posicaoX;   //coordenada X no Ambiente
    private int posicaoY;   //coordenada Y no Ambiente
    private int posicaoZ;   //coordenada Z no Ambiente no caso de robôs aéreos
    public Robo (String nomeRobo, String direcaoRobo, int x, int y, int z, Ambiente ambiente) {    //Construtor para inicializar os atributos do robô aéreo;
        this.nome = nomeRobo;
        this.direcao = direcaoRobo;
        this.posicaoX = x;
        this.posicaoY = y;
        this.posicaoZ = z;
    }

    public void mover(int deltaX, int deltaY, Ambiente ambiente) { //Atualiza a posicão do robô de modo que ele anda primeiro no eixo x e depois no eixo y
        if (deltaX == 0 && deltaY == 0) { //Condição de parada da recursão
            return;
        }
        
        //Analisa se o passo do robô é positivo ou negativo ou nulo
        int[] passos = getPasso(deltaX, deltaY);
        boolean moveu = false;
        
        if (deltaX != 0 && deltaX + this.posicaoX > 0 && deltaX + this.posicaoX < ambiente.getLimites()[0] && !identificarRobo(this.posicaoX + passos[0], this.posicaoY, 0, this.nome, ambiente)){ //Segue recursivamente no eixo x para analisar caso identifique algum obstáculo
            this.posicaoX += passos[0];
            moveu = true;
            mover(deltaX - passos[0], deltaY, ambiente);
            return;
        }else if (deltaY != 0 && deltaY + this.posicaoY > 0 && deltaY + this.posicaoY < ambiente.getLimites()[1] && !identificarRobo(this.posicaoX, this.posicaoY + passos[1], 0,this.nome, ambiente)){ //Depois de ter andado tudo em x ele segue recursivamente no eixo y analisando caso identifique algum obstáculo
            this.posicaoY += passos[1];
            moveu = true;
            mover(0, deltaY - passos[1], ambiente); //Ele só começa a se mover em y depois de ter movido tudo em x
            return;
        }

        if (!moveu){ //Se não conseguiu mover nem em X e nem em Y ele para a função
            return;
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
    
    public void setDirecao(String direcao){ //Função para alterar a direção que o robô está encarando
        this.direcao = direcao;
    }
    
    public boolean identificarRobo(int x, int y, int z, String nome, Ambiente ambiente){
        for (Robo robo: ambiente.getListaRobos()){ //Para cada Robô na lista de robôs (obviamente não sendo o robô que está tentando identificar um obstáculo), ele analisa se a posição que o robô em questão quer ir já está ocupada 
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

    public int[] getPasso(int deltaX, int deltaY){ //Retorna o passo negativo ou positivo de x e de y
        int passoX = 0, passoY = 0;
        if (deltaX != 0){
            passoX = deltaX/Math.abs(deltaX);
        }
        if (deltaY != 0){
            passoY = deltaY/Math.abs(deltaY);
        }
        int[] listaPasso = {passoX, passoY};
        return listaPasso;
    }
}