//importar a biblioteca para mexer com a lista
import java.util.ArrayList;
//fazer a classe
public class Aspirador extends RoboTerrestre{
    //atributo numero de robos que eliminou
    private int robosEliminados = 0;
    private Ambiente ambiente;
   
    //Construtor para inicializar os atributos
    public Aspirador(String nome, String direcao, int x, int y, int velocidadeMaxima, Ambiente ambiente){
        super(nome, direcao, x, y, velocidadeMaxima, ambiente);
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
    public void mover(int deltaX, int deltaY){ //A classe aspirador              
        //se encontrar um obstáculo
        if (super.identificarRobo(this.x + deltaX, this.y + deltaY)){
            //eliminar o robô
            this.eliminar();
        }
        else {
            //mover o robô
            super.mover(deltaX, deltaY);
        }
    }   
}