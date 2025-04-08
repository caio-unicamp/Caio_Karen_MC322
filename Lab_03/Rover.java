import java.util.ArrayList;

public class Rover extends RoboTerrestre{
    //atributo próprio
    int posicaoX;
    int posicaoY;
    int tempoLocomocaoTerrestre;
    int qtdRobosDerrubados;
    ArrayList<Robo> listaRobosEmpurados;
    Ambiente ambiente;
    //Construtor
    public Rover(String nome, String direcao, int x, int y, int velocidadeMaxima, Ambiente ambiente, int tempoLocomocaoTerrestre){
        super(nome, direcao, x, y, velocidadeMaxima, ambiente, tempoLocomocaoTerrestre);
        this.posicaoX = x;
        this.posicaoY = y;
        this.ambiente = ambiente;
        this.qtdRobosDerrubados = 0;
        this.tempoLocomocaoTerrestre = tempoLocomocaoTerrestre;
    }
    
    @Override
    public void mover(int deltaX, int deltaY){ //Função para mover recursivamente o rover e acessar a função empurrar robô
        int[] passos = this.getPasso(deltaX, deltaY);
        if (passos[0] == 0 && passos[1] == 0) {
            return; // Evita chamadas infinitas
        }

        if (deltaX == 0 && deltaY == 0) { 
            return; // O rover já chegou ao destino, então para a recursão
        }
        
        if (passos[0] != 0 && ambiente.dentroDosLimites(this.posicaoX + passos[0], this.posicaoY, 0)){
            if (identificarRobo(this.posicaoX + passos[0], this.posicaoY, 0, this.getNome())){
                Robo roboEmpurrado = getRoboNaPosicao(this.posicaoX + passos[0], this.posicaoY);
                if (roboEmpurrado != null){
                    empurrarRobo(roboEmpurrado, passos[0], 0);
                }
            } 
            this.posicaoX += passos[0];
            this.setPosicao(posicaoX, posicaoY, 0);
            this.mover(deltaX - passos[0], deltaY); //Continua o caminho em X decrementando o tanto que já foi andado
            return;
        }else if (passos[1] != 0 && ambiente.dentroDosLimites(this.posicaoX, this.posicaoY + passos[1], 0)){
            if (identificarRobo(this.posicaoX, this.posicaoY + passos[1], 0, this.getNome())){
                Robo roboEmpurrado = getRoboNaPosicao(this.posicaoX, this.posicaoY + passos[1]);
                if (roboEmpurrado != null){
                    empurrarRobo(roboEmpurrado, 0, passos[1]);
                }
            }
            this.posicaoY += passos[1];
            this.setPosicao(posicaoX, posicaoY, 0);
            this.mover(0, deltaY - passos[1]); //Continua o caminho em Y decrementando o tanto que já foi andado
            return;
        }
    }
    
    //metodo de mover com empurrar um robo junto
    public void empurrarRobo(Robo empurrado, int deltaX, int deltaY){
        int[] novaPosicao = getPosicaoFinalRoboEmpurrado(deltaX, deltaY, empurrado);
        if (identificarRobo(novaPosicao[0], novaPosicao[1], 0, empurrado.getNome())){ //Caso encontre um robô enquanto está se mexendo, também irá empurrar este
            Robo novoRoboEmpurrado = getRoboNaPosicao(novaPosicao[0], novaPosicao[1]);
            if (novoRoboEmpurrado != null){
                listaRobosEmpurados.add(novoRoboEmpurrado);
                empurrarRobo(novoRoboEmpurrado, deltaX, deltaY);
            }
        }
        empurrado.setPosicao(novaPosicao[0], novaPosicao[1], 0); //Como o robô está sendo empurrado pelo rover ele também passará a ignorar qualquer obstáculo, então basta setar a posição nova dele até a posição final dele até o fim da locomoção do rover
    }   

    public int[] getPosicaoFinalRoboEmpurrado(int deltaX, int deltaY, Robo empurrado){ //Função que retorna as posições finais dos robôs empurrados pelo rover
        int posicaoX = empurrado.getPosicao()[0] + deltaX;
        int posicaoY = empurrado.getPosicao()[1] + deltaY;
        
        if (!ambiente.dentroDosLimites(posicaoX, posicaoY, 0)){ //Se o robô que foi empurrado sai dos limites, ele é eliminado da lista de robôs ativos
            ambiente.getListaRobos().remove(empurrado);
            qtdRobosDerrubados++;
        }

        return new int[] {posicaoX, posicaoY};
    }

    public int getQtdRobosEmpurrados(){ //Função que mostra quantos robôs foram empurrados
        return listaRobosEmpurados.size();
    }
    public int getRobosDerrubados(){ //Função que mostra quantos robôs foram derrubados
        return qtdRobosDerrubados;
    }
    
    private Robo getRoboNaPosicao(int x, int y) { //Função para não ter que percorrer a lista inteira de robôs até achar um específico
        for (Robo robo : ambiente.getListaRobos()) {
            if (robo.getPosicao()[0] == x && robo.getPosicao()[1] == y) {
                return robo;
            }
        }
        return null;
    }
}