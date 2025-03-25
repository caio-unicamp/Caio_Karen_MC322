//importar a biblioteca para mexer com a lista
import java.util.ArrayList;
//fazer a classe
public class Aspirador extends RoboTerrestre{
    //atributo numero de robos que eliminou
    private int robosEliminados = 0;
   
    //Construtor para inicializar os atributos
    public Aspirador(String nome, String direcao, int x, int y, int velocidadeMaxima, Ambiente ambiente){
        super(nome, direcao, x, y, velocidadeMaxima, ambiente);
        this.robosEliminados = 0;   //inicializar o atritubo próprio
    }

    //método de eliminar
    public void eliminar(int x, int y) {
        //pegar a lista de robos e percorrer procurano o nome do robo
        private ArrayList<Robo> listaRobosEliminar; 
        listaRobosEliminar = ambiente.getLista();   //pegar a lista de robos do ambiente
        //percorrer a lista
        for (int i = 0; i < Robo.listaRobosAtivos size(); i++) {
            //se a posição do robo na índice i for igual a posição do robo que queremos eliminar
            if (Robo.listaRobosAtivos.get(i).posicaoX.equals(x)) && (Robo.listaRobosAtivos.get(i).posicaoY.equals(y))  {
                //remover o robo da lista
                Robo.listaRobosAtivos.remove(i);
                return true; // Removido com sucesso
                this.robosEliminados++; //incrementar o atributo próprio
            }
        }
        
        return false; // Robo não encontrado se ele não retornou True previamente
    }
        

    //método de se mover
    public void mover(int deltaX, int deltaY){             
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