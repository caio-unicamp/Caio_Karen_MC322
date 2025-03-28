//importar a biblioteca para mexer com a lista
import java.util.ArrayList;
//fazer a classe
public class Aspirador extends RoboTerrestre{
    //atributo numero de robos que eliminou
    private int robosEliminados = 0;
    private Ambiente ambiente;
   
    //Construtor para inicializar os atributos
    public Aspirador(String nome, String direcao, int x, int y, int velocidadeMaxima, Ambiente ambiente, int tempoLocomocaoTerrestre){
        super(nome, direcao, x, y, velocidadeMaxima, ambiente, tempoLocomocaoTerrestre);
        this.robosEliminados = 0;   //inicializar o atritubo próprio
        this.ambiente = ambiente;
    }

    //método de eliminar
    public boolean eliminarRobo(){ //Função para eliminar robôs quando ele se mover
        //pegar a lista de robos e percorrer procurando o nome do robo
        for (Robo robo : ambiente.getLista()) {
            if (this.getPosicao()[0] == robo.getPosicao()[0] && this.getPosicao()[1] == robo.getPosicao()[1] ){ //Se ele achou um robô na mesma posição que ele está, ele elimina esse robô 
                ambiente.getLista().remove(robo); //Remove o robô encontrado da lista de robôs ativos
                robosEliminados++; 
                return true; //Se ele eliminou o robô ele retorna true
            }
        } 
        return false; //Se ele percorreu a lista inteira e não existe robô com o nome dado ele retorna falso

    }
    
    public int getRobosEliminados(){ //Função que retorna o número de robôs que foram eliminados
        return robosEliminados;
    }

    //método de se mover
    public void mover(int deltaX, int deltaY){ //Função para mover o aspirador              
        //Analisa se o passo do aspirador é negativo, positivo ou nulo
        int posInicialX = this.getPosicao()[0], posInicialY = this.getPosicao()[1], deltaXpercorrida = 0, deltaYpercorrida = 0;
        int[] passos = getPasso(deltaX, deltaY);
        super.mover(deltaX, deltaY); //Move o robô de acordo com a função mover da classe mãe 
        //Salva o quanto já foi andado
        deltaXpercorrida = this.getPosicao()[0] - posInicialX; 
        deltaYpercorrida = this.getPosicao()[1] - posInicialY;
        if (identificarRobo(this.getPosicao()[0] + passos[0], this.getPosicao()[1] + passos[1], this.getPosicao()[2], this.getNome())){
            //eliminar o robô caso ele seja encontrado
            this.eliminarRobo();
            this.mover(deltaX - (deltaXpercorrida)*passos[0], deltaY - (deltaYpercorrida)*passos[1]); //Como o robô eliminou o obstáculo ele continua seguindo seu caminho
        }
    }   
}