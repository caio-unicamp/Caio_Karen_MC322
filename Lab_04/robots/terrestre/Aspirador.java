package robots.terrestre;
import interfaces.*;
import enums.*;
import excecoes.*;
import ambiente.*;
import sensores.*;
import robots.Robo;

public class Aspirador extends RoboTerrestre implements Comunicavel {
    //atributo numero de robos que eliminou
    private int robosEliminados;
    
    //Construtor para inicializar os atributos
    public Aspirador(String nome, String direcao, int x, int y, int velocidadeMaxima, int tempoLocomocaoTerrestre){
        super(nome, direcao, x, y, velocidadeMaxima, tempoLocomocaoTerrestre); //Herança da classe robô
        this.robosEliminados = 0;   //inicializar o atritubo próprio
    }

    //método de aspirar robôs
    public void aspirarRobo(int passosX, int passosY, Ambiente ambiente){ //Função para eliminar robôs quando ele se mover
        for (Entidade entidade : ambiente.getListaEntidades()) {
            if (!(entidade instanceof Robo)){ //Verifica se a entidade é um robô
                continue; //Se não for, pula para a próxima iteração
            }else{
                Robo robo = (Robo) entidade;
                if (robo != this && robo.getPosicao()[0] == this.getPosicao()[0] + passosX && robo.getPosicao()[1] == this.getPosicao()[1] + passosY){ //Se ele achou um robô na mesma posição que ele está e não for ele próprio, ele elimina esse robô 
                    ambiente.removerEntidade(robo);
                    robosEliminados++; 
                    return; //Se ele eliminou o robô ele encerra a função
                }
            }
        }
    }
    
    public int getRobosEliminados(){ //Função que retorna o número de robôs que foram eliminados
        return robosEliminados;
    }
    
    @Override
    public void mover(int deltaX, int deltaY, Ambiente ambiente) throws SensorDesligadoException, RoboDesligadoException, ColisaoException { //Função para mover o aspirador e eliminar os robôs
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

        if (passos[0] != 0 && ambiente.dentroDosLimites(posAtualX + passos[0], posAtualY, this.getPosicao()[2])){ //Movimento em X
            if (this.getSensorProximidade().monitorar(posAtualX + passos[0], posAtualY, this.getPosicao()[2], ambiente, this)) { //Faz as verificações de proximidade caso ainda haja bateria no robô
                if (this.getSensorProximidade().identificarRobo(posAtualX + passos[0], posAtualY, this.getPosicao()[2], ambiente, this)){ // O código abaixo é executado se o robô não identificar um obstáculo mas sim um robô
                    // Atualizar os valores restantes para deltaX e deltaY
                    int novoDeltaX = deltaX - (posAtualX - posInicialX);
                    int novoDeltaY = deltaY - (posAtualY - posInicialY);
                    aspirarRobo(passos[0], 0,ambiente); // Chama a função para eliminar o robô identificado
        
                    // Condição de parada: verificar se ainda há movimento restante
                    if (novoDeltaX != 0 || novoDeltaY != 0) {
                        // Evitar loop infinito: verificar se a nova posição é válida e diferente da atual
                        if (!ambiente.dentroDosLimites(posAtualX + passos[0], posAtualY, this.getPosicao()[2])) {
                            return;
                        }
                        this.mover(novoDeltaX, novoDeltaY, ambiente);
                    }
                }
            }
        }else if (passos[1] != 0 && ambiente.dentroDosLimites(posAtualX, posAtualY + passos[1], this.getPosicao()[2])){ //Movimento em Y
            if (this.getSensorProximidade().monitorar(posAtualX, posAtualY + passos[1], this.getPosicao()[2], ambiente, this)) { //Faz as verificações de proximidade caso ainda haja bateria no robô
                if (this.getSensorProximidade().identificarRobo(posAtualX, posAtualY + passos[1], this.getPosicao()[2], ambiente, this)){ // O código abaixo é executado se o robô não identificar um obstáculo mas sim um robô
                    // Atualizar os valores restantes para deltaX e deltaY
                    int novoDeltaX = deltaX - (posAtualX - posInicialX);
                    int novoDeltaY = deltaY - (posAtualY - posInicialY);
                    aspirarRobo(0, passos[1],ambiente); // Chama a função para eliminar o robô identificado
        
                    // Condição de parada: verificar se ainda há movimento restante
                    if (novoDeltaX != 0 || novoDeltaY != 0) {
                        // Evitar loop infinito: verificar se a nova posição é válida e diferente da atual
                        if (!ambiente.dentroDosLimites(posAtualX, posAtualY + passos[1], this.getPosicao()[2])) {
                            return;
                        }
                        this.mover(novoDeltaX, novoDeltaY, ambiente);
                    }
                }
            }
        }
    }
}

    