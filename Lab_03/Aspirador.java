//fazer a classe

import java.util.ArrayList;

public class Aspirador extends RoboTerrestre{
    //atributo numero de robos que eliminou
    private int robosEliminados;
    
    //Construtor para inicializar os atributos
    public Aspirador(String nome, String direcao, int x, int y, int velocidadeMaxima, int tempoLocomocaoTerrestre, ArrayList<Sensor<?>> listaSensores){
        super(nome, direcao, x, y, velocidadeMaxima, tempoLocomocaoTerrestre, listaSensores); //Herança da classe robô
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
    public void mover(int deltaX, int deltaY, Ambiente ambiente) { //Função para mover o aspirador e eliminar os robôs
        SensorProximidade sensorProx = null;
        for (Sensor<?> sensor : listaSensores) { //Procura na lista de sensores do robo pelo sensor de proximidade para ajustar a movimentação
            if (sensor instanceof SensorProximidade){ //Verifica se o sensor é do tipo SensorProximidade
                sensorProx = (SensorProximidade) sensor;
            }
        }
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
        if (sensorProx.monitorar(posAtualX + passos[0], posAtualY + passos[1], this.getPosicao()[2], ambiente, this)) { //Faz as verificações de proximidade caso ainda haja bateria no robô
            // Atualizar os valores restantes para deltaX e deltaY
            if (sensorProx.identificarObstaculo(posAtualX + passos[0], posAtualY + passos[1], this.getPosicao()[2], ambiente)){ //Se o aspirador identificar um obstáculo em vez de um robô ele age diferente
                Obstaculo obstaculoIdentificado = null;
                for (Obstaculo obstaculo : ambiente.getListaObstaculos()) {
                    if (obstaculo.getPosX1() <= posAtualX + passos[0] && obstaculo.getPosX2() >= posAtualX + passos[0] && obstaculo.getPosY1() <= posAtualY + passos[1] && obstaculo.getPosY2() >= posAtualY + passos[1] && obstaculo.getAltura() == this.getPosicao()[2]) {
                        obstaculoIdentificado = obstaculo;
                        break;
                    }
                }
                if (obstaculoIdentificado != null){
                    if (obstaculoIdentificado.getTipoObstaculo().equals(TipoObstaculo.ARVORE)){ //Se o obstáculo identificado for uma parede, o robô não pode passar por ele
                        return;
                    }else if (obstaculoIdentificado.getTipoObstaculo().equals(TipoObstaculo.MINA_TERRESTRE)){ //Se ele identifica uma mina terrestre ele para
                        if (sensorProx.getBateria() == 0){ //Se a bateria dele tiver acabado, ele não consegue identificar a mina terrestre e irá explodir
                            ambiente.removerRobo(this); //Remove o robô explodido
                            ambiente.removerObstaculo(obstaculoIdentificado); //Remove a mina terrestre explodida
                        }
                        return;
                    }else if (obstaculoIdentificado.getTipoObstaculo().equals(TipoObstaculo.BURACO_SEM_FUNDO)){ //Se ele identifica uma mina aérea ele para
                        if (sensorProx.getBateria() == 0){ //Se a bateria dele tiver acabado, ele não consegue identificar o buraco e irá cair
                            ambiente.removerRobo(this); //Remove o robô que caiu no buraco
                        }
                        return;
                    }
                }
            }
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

    