public class Rover extends RoboTerrestre{
    //atributo próprio
    int qtdRobosEmpurrados;
    int tempoLocomocaoTerrestre;
    int qtdRobosDerrubados = 0
    Ambiente ambiente;
    //Construtor
    public Rover(String nome, String direcao, int x, int y, int velocidadeMaxima, Ambiente ambiente, int tempoLocomocaoTerrestre){
        super(nome, direcao, x, y, velocidadeMaxima, ambiente, tempoLocomocaoTerrestre);
        this.ambiente = ambiente;
        this.qtdRobosEmpurrados = 0;
        this.tempoLocomocaoTerrestre = tempoLocomocaoTerrestre;
    }
    
    public void mover(int deltaX, int deltaY){ //Função para mover o rover e acessar a função empurrar robô
        int deltaXpercorrida = 0, deltaYpercorrida = 0, posXinicial = this.getPosicao()[0], posYinicial = this.getPosicao()[1];
        super.mover(deltaX, deltaY);
        for (Robo robo : ambiente.getLista()){
            if (qtdRobosEmpurrados <= 8){ //O rover tem uma limitação de só poder empurrar no máximo 8 robôs por vez
                if (robo.getPosicao()[0] == (this.getPosicao()[0] + this.getPasso(deltaX, deltaY)[0]) && robo.getPosicao()[1] == (this.getPosicao()[1] + this.getPasso(deltaX, deltaY)[1])){
                    empurrarRobo(robo, deltaX, deltaY);
                    deltaXpercorrida += this.getPosicao()[0] - posXinicial;
                    deltaYpercorrida += this.getPosicao()[1] - posYinicial;
                    qtdRobosEmpurrados++;
                    mover(deltaX - (deltaXpercorrida)*getPasso(deltaX, deltaY)[0], deltaY - (deltaYpercorrida)*getPasso(deltaX, deltaY)[1]);
                }
            }
        }
    }
    
    //metodo de mover com empurrar um robo junto
    public void empurrarRobo(Robo empurrado, int deltaX, int deltaY){
        empurrado.setPosicao(this.getPosicaoFinalRoboEmpurrado(deltaX, deltaY, empurrado)[0], this.getPosicaoFinalRoboEmpurrado(deltaX, deltaY, empurrado)[1], 0); //Como o robô está sendo empurrado pelo rover ele também passará a ignorar qualquer obstáculo, então basta setar a posição nova dele até a posição final dele após o fim da locomoção do rover
    }   

    public int[] getPosicaoFinalRoboEmpurrado(int deltaX, int deltaY, Robo empurrado){ //Função que retorna as posições finais dos robôs empurrados pelo rover
        int posicaoX = this.getPosicao()[0] + deltaX, posicaoY = this.getPosicao()[1] + deltaY;
        //Condicionais para posicionar o máximo de 8 robôs empurrados
        if (qtdRobosEmpurrados == 1){
            posicaoX++;
        }else if(qtdRobosEmpurrados == 2){
            posicaoX++;
            posicaoY++;
        }else if(qtdRobosEmpurrados == 3){
            posicaoX++;
            posicaoY--;
        }else if(qtdRobosEmpurrados == 4){
            posicaoY++;
        }else if(qtdRobosEmpurrados == 5){
            posicaoY--;
        }else if(qtdRobosEmpurrados == 6){
            posicaoX--;
        }else if(qtdRobosEmpurrados == 7){
            posicaoX--;
            posicaoY++;
        }else if(qtdRobosEmpurrados == 8){
            posicaoX--;
            posicaoY--;
        }

        if (!ambiente.dentroDosLimites(posicaoX, posicaoY, 0)){ //Se o robô que foi empurrado sai dos limites, ele é eliminado da lista de robôs ativos
            ambiente.getLista().remove(empurrado);
            qtdRobosDerrubados++;
        }
        int[] coordenadas = {posicaoX, posicaoY};
        return coordenadas;
    }

    public int getRobosDerrubados(){ //Função que mostra quantos robôs foram derrubados
        return qtdRobosDerrubados;
    }
    
}