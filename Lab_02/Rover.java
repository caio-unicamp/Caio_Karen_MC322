public class Rover extends RoboTerrestre{
    //atributo próprio
    int posicaoX;
    int posicaoY;
    int qtdRobosEmpurrados;
    int tempoLocomocaoTerrestre;
    int qtdRobosDerrubados = 0;
    Ambiente ambiente;
    //Construtor
    public Rover(String nome, String direcao, int x, int y, int velocidadeMaxima, Ambiente ambiente, int tempoLocomocaoTerrestre){
        super(nome, direcao, x, y, velocidadeMaxima, ambiente, tempoLocomocaoTerrestre);
        this.posicaoX = x;
        this.posicaoY = y;
        this.ambiente = ambiente;
        this.qtdRobosEmpurrados = 0;
        this.tempoLocomocaoTerrestre = tempoLocomocaoTerrestre;
    }
    
    public void mover(int deltaX, int deltaY){ //Função para mover recursivamente o rover e acessar a função empurrar robô
        int[] passos = this.getPasso(deltaX, deltaY);

        if (deltaX + this.posicaoX > 0 && deltaX + this.posicaoX < ambiente.getLimites()[0]){
            if (identificarRobo(this.posicaoX + passos[0], this.posicaoY, 0, this.getNome())){
                Robo roboEmpurrado = getRoboNaPosicao(this.posicaoX + passos[0], this.posicaoY);
                if (roboEmpurrado != null){
                    empurrarRobo(roboEmpurrado, passos[0], 0);
                }
            } 
            this.posicaoX += passos[0];
            mover(deltaX - passos[0], deltaY);
        }else if (deltaY + this.posicaoY > 0 && deltaY + this.posicaoY < ambiente.getLimites()[1]){
            if (identificarRobo(this.posicaoX, this.posicaoY + passos[1], 0, this.getNome())){
                Robo roboEmpurrado = getRoboNaPosicao(this.posicaoX, this.posicaoY + passos[1]);
                if (roboEmpurrado != null){
                    empurrarRobo(roboEmpurrado, 0, passos[1]);
                }
            }
            this.posicaoY += passos[1];
            mover(0, deltaY - passos[1]);
        }
    }
    
    //metodo de mover com empurrar um robo junto
    public void empurrarRobo(Robo empurrado, int deltaX, int deltaY){
        int[] novaPosicao = getPosicaoFinalRoboEmpurrado(deltaX, deltaY, empurrado);
        empurrado.setPosicao(novaPosicao[0], novaPosicao[1], 0); //Como o robô está sendo empurrado pelo rover ele também passará a ignorar qualquer obstáculo, então basta setar a posição nova dele até a posição final dele até o fim da locomoção do rover
    }   

    public int[] getPosicaoFinalRoboEmpurrado(int deltaX, int deltaY, Robo empurrado){ //Função que retorna as posições finais dos robôs empurrados pelo rover
        int posicaoX = empurrado.getPosicao()[0] + deltaX, posicaoY = empurrado.getPosicao()[1] + deltaY;
        
        if (!ambiente.dentroDosLimites(posicaoX, posicaoY, 0)){ //Se o robô que foi empurrado sai dos limites, ele é eliminado da lista de robôs ativos
            ambiente.getLista().remove(empurrado);
            qtdRobosDerrubados++;
        }
        
        return new int[] {posicaoX, posicaoY};
    }

    public int getRobosDerrubados(){ //Função que mostra quantos robôs foram derrubados
        return qtdRobosDerrubados;
    }
    
    private Robo getRoboNaPosicao(int x, int y) { //Função para não ter que percorrer a lista inteira de robôs até achar um específico
        for (Robo robo : ambiente.getLista()) {
            if (robo.getPosicao()[0] == x && robo.getPosicao()[1] == y) {
                return robo;
            }
        }
        return null;
    }
}