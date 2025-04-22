//fazer a classe
public class Aspirador extends RoboTerrestre{
    //atributo numero de robos que eliminou
    private int robosEliminados;
    
    //Construtor para inicializar os atributos
    public Aspirador(String nome, String direcao, int x, int y, int velocidadeMaxima, Ambiente ambiente, int tempoLocomocaoTerrestre){
        super(nome, direcao, x, y, velocidadeMaxima, ambiente, tempoLocomocaoTerrestre);
        this.robosEliminados = 0;   //inicializar o atritubo próprio
    }

    //método de eliminar
    public void aspirarRobo(Ambiente ambiente, int passosX, int passosY){ //Função para eliminar robôs quando ele se mover
        for (Robo robo : ambiente.getListaRobos()) {
            if (robo != this && robo.getPosicao()[0] == this.getPosicao()[0] + passosX && robo.getPosicao()[1] == this.getPosicao()[1] + passosY){ //Se ele achou um robô na mesma posição que ele está e não for ele próprio, ele elimina esse robô 
                ambiente.removerRobo(robo);
                robosEliminados++; 
                return; //Se ele eliminou o robô ele retorna true
            }
        }
    }
    
    public int getRobosEliminados(){ //Função que retorna o número de robôs que foram eliminados
        return robosEliminados;
    }

    @Override
    public void mover(int deltaX, int deltaY, Ambiente ambiente) {
        int posInicialX = this.getPosicao()[0];
        int posInicialY = this.getPosicao()[1];
        int[] passos = this.getPasso(deltaX, deltaY);
        if (passos[0] == 0 && passos[1] == 0) {
            return; // Evita chamadas infinitas
        }
        if (deltaX == 0 && deltaY == 0) { 
            return; // O aspirador já chegou ao destino, então para a recursão
        }
    
        // Move o robô de acordo com a função mover da classe mãe
        super.mover(deltaX, deltaY, ambiente);
        int posAtualX = this.getPosicao()[0];
        int posAtualY = this.getPosicao()[1];

        if (posAtualX == posInicialX + deltaX && posAtualY == posInicialY + deltaY) {
            return; // Se ele andou tudo, não há necessidade de verificar colisões
        }
        // Verifica se há um robô na nova posição
        if (identificarRobo(posAtualX + passos[0], posAtualY + passos[1], this.getPosicao()[2], ambiente)) {
            // Atualizar os valores restantes para deltaX e deltaY
            int novoDeltaX = deltaX - (posAtualX - posInicialX);
            int novoDeltaY = deltaY - (posAtualY - posInicialY);
            aspirarRobo(ambiente, passos[0], passos[1]); // Chama a função para eliminar o robô identificado

            // Condição de parada: verificar se ainda há movimento restante
            if (novoDeltaX != 0 || novoDeltaY != 0) {
                // Evitar loop infinito: verificar se a nova posição é válida e diferente da atual
                if (!ambiente.dentroDosLimites(this.getPosicao()[0] + passos[0], this.getPosicao()[1] + passos[1], this.getPosicao()[2])) {
                    return;
                }
                this.mover(novoDeltaX, novoDeltaY, ambiente);
            }
        }
    }
}
    