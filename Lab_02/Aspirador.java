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
            if (robo != this && this.getPosicao()[0] +1 == robo.getPosicao()[0] && this.getPosicao()[1] == robo.getPosicao()[1] ){ //Se ele achou um robô na mesma posição que ele está, ele elimina esse robô 
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

    public void mover(int deltaX, int deltaY) {
        int posInicialX = this.getPosicao()[0];
        int posInicialY = this.getPosicao()[1];
        int[] passos = getPasso(deltaX, deltaY);
    
        // Move o robô de acordo com a função mover da classe mãe
        super.mover(deltaX, deltaY);
    
        // Verifica se há um robô na nova posição
        if (identificarRobo(this.getPosicao()[0] + passos[0], this.getPosicao()[1] + passos[1], this.getPosicao()[2], this.getNome())) {
            // Eliminar o robô caso ele seja encontrado
            boolean eliminado = this.eliminarRobo();
    
            if (eliminado) {
                System.out.println("Robô eliminado na posição (" + this.getPosicao()[0] + ", " + this.getPosicao()[1] + ")");
            }
    
            // Atualizar os valores restantes para deltaX e deltaY
            int novoDeltaX = deltaX - (this.getPosicao()[0] - posInicialX);
            int novoDeltaY = deltaY - (this.getPosicao()[1] - posInicialY);
    
            // Condição de parada: verificar se ainda há movimento restante
            if (novoDeltaX != 0 || novoDeltaY != 0) {
                // Evitar loop infinito: verificar se a nova posição é válida e diferente da atual
                if (!ambiente.dentroDosLimites(this.getPosicao()[0] + passos[0], this.getPosicao()[1] + passos[1], this.getPosicao()[2])) {
                    System.out.println("Movimento interrompido: fora dos limites do ambiente.");
                    return;
                }
                this.mover(novoDeltaX, novoDeltaY);
            }
            else {
                System.out.println("Movimento interrompido: sem movimento restante.");
            }
        }
    }
}
    