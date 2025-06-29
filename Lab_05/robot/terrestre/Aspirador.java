package robot.terrestre;

import interfaces.*;
import enums.*;
import excecoes.*;
import ambiente.*;
import robot.Robo;
import robot.subsistemas.*;;

public class Aspirador extends RoboTerrestre implements Comunicavel, Eliminador {
    //atributo numero de robos que eliminou
    private int robosEliminados;
    private ModuloComunicacao moduloComunicacao; // Módulo de comunicação do aspirador
    //Construtor para inicializar os atributos
    public Aspirador(String nome, String direcao, int x, int y, int velocidadeMaxima, int tempoLocomocaoTerrestre){
        super(nome, direcao, x, y, velocidadeMaxima, tempoLocomocaoTerrestre); //Herança da classe robô
        this.robosEliminados = 0;   //inicializar o atritubo próprio
        this.moduloComunicacao = new ModuloComunicacao(this); //Inicializa o módulo de comunicação do aspirador
    }
    /**
     * Envia uma mensagem para outro robô
     * @param destinatario o robô que receberá a mensagem
     * @param mensagem a mensagem a ser enviada
     * @throws RoboDesligadoException se o robô estiver desligado
     * @throws ErroComunicacaoException se houver um erro na comunicação, como destinatário nulo ou não comunicável
     */
    @Override
    public void enviarMensagem(Entidade destinatario, String mensagem) throws RoboDesligadoException, ErroComunicacaoException {
        moduloComunicacao.enviarMensagem(destinatario, mensagem); //Delegar a responsabilidade de enviar a mensagem para o módulo de comunicação
    }
    /**
     * Recebe mensagens enviadas por outros robôs comunicáveis
     * @param remetente o nome do robô que enviou a mensagem
     * @param mensagem a mensagem recebida
     * @throws RoboDesligadoException se o robô estiver desligado
     */
    @Override
    public void receberMensagem(String remetente, String mensagem) throws RoboDesligadoException {
        moduloComunicacao.receberMensagem(remetente, mensagem); //Delegar a responsabilidade de receber a mensagem para o módulo de comunicação
    }
    /**
     * Aspira os robôs encontrados no caminho quando ele se move
     * @param passosX
     * @param passosY
     * @param ambiente
     */
    public void eliminar(int passosX, int passosY, Ambiente ambiente){
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
    /**
     * Verifica quantos robôs foram eliminados
     * @return número de robôs eliminados
     */
    public int getRobosEliminados(){
        return robosEliminados;
    }
    /**
     * Move o aspirador aspirando os robôs encontrados no seu caminho
     * @throws ErroComunicacaoException 
     */
    @Override
    public void mover(int deltaX, int deltaY, Ambiente ambiente) throws SensorDesligadoException, RoboDesligadoException, ColisaoException, ErroComunicacaoException { //Função para mover o aspirador e eliminar os robôs
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
                if (this.getSensorProximidade().getUltimoTipoDetectado().equals(TipoEntidade.ROBO)){ // O código abaixo é executado se o Aspirador identificar um robô
                    // Atualizar os valores restantes para deltaX e deltaY
                    int novoDeltaX = deltaX - (posAtualX - posInicialX);
                    int novoDeltaY = deltaY - (posAtualY - posInicialY);
                    this.eliminar(passos[0], 0, ambiente); // Chama a função para eliminar o robô identificado
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
                if (this.getSensorProximidade().getUltimoTipoDetectado().equals(TipoEntidade.ROBO)){ // O código abaixo é executado se o Aspirador identificar um robô
                    // Atualizar os valores restantes para deltaX e deltaY
                    int novoDeltaX = deltaX - (posAtualX - posInicialX);
                    int novoDeltaY = deltaY - (posAtualY - posInicialY);
                    this.eliminar(0, passos[1], ambiente); // Chama a função para eliminar o robô identificado
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

    