package robots.terrestre;
import interfaces.*;
import enums.*;
import excecoes.*;
import ambiente.*;
import robots.Robo;

public class Aspirador extends RoboTerrestre implements Comunicavel {
    //atributo numero de robos que eliminou
    private int robosEliminados;
    
    //Construtor para inicializar os atributos
    public Aspirador(String nome, String direcao, int x, int y, int velocidadeMaxima, int tempoLocomocaoTerrestre){
        super(nome, direcao, x, y, velocidadeMaxima, tempoLocomocaoTerrestre); //Herança da classe robô
        this.robosEliminados = 0;   //inicializar o atritubo próprio
    }
    /**
     * Envia uma mensagem para outro robô
     */
    @Override
    public void enviarMensagem(Comunicavel destinatario, String mensagem) throws RoboDesligadoException, ErroComunicacaoException {
        if (this.getEstadoRobo() == EstadoRobo.DESLIGADO) { //Checa se o aspirador está ligado
            throw new RoboDesligadoException(this.getNome()); //Se não estiver lança o respectivo erro
        }

        if (destinatario == null) {
            throw new ErroComunicacaoException("Destinatário da mensagem não pode ser nulo.");
        }

        if (destinatario instanceof Robo && ((Robo) destinatario).getEstadoRobo() == EstadoRobo.DESLIGADO) { //Checa se quem receberá a mensagem não está desligado
            CentralComunicacao.getInstancia().registrarMensagem(this.getNome(), ((Robo) destinatario).getNome(), "[TENTATIVA FALHA - DESTINATÁRIO DESLIGADO] " + mensagem);
            throw new ErroComunicacaoException("O robô destinatário " + ((Robo) destinatario).getNome() + " está desligado.");
        }
        //Faz o destinatário receber a mensagem (caso ele não esteja desligado)
        destinatario.receberMensagem(this.getNome(), mensagem);
        // Se o destinatário puder ser instanciado como um robô, busca seu nome, se não, trata como desconhecido
        String nomeDestinatario = (destinatario instanceof Robo) ? ((Robo) destinatario).getNome() : "Desconhecido";
        
        CentralComunicacao.getInstancia().registrarMensagem(this.getNome(), nomeDestinatario, mensagem);
        //COLOCAR O PRINT NA MAIN DEPOIS DE TERMINAR
        System.out.println("[" + this.getNome() + " para " + nomeDestinatario + "]: " + mensagem + " (Mensagem enviada)");
    }
    /**
     * Recebe mensagens enviadas por outros robôs comunicáveis
     */
    @Override
    public void receberMensagem(String remetente, String mensagem) throws RoboDesligadoException {
        if (this.getEstadoRobo() == EstadoRobo.DESLIGADO) { //Confere se o Aspirador está ligado
            throw new RoboDesligadoException("O robô " + this.getNome() + " está desligado e não pode receber mensagens."); //Se não estiver lança o respectivo erro
        }
        //BOTAR O PRINT NA MAIN DEPOIS
        System.out.println("[" + this.getNome() + " recebeu de " + remetente + "]: \"" + mensagem + "\"");
    }
    /**
     * Aspira os robôs encontrados no caminho quando ele se move
     * @param passosX
     * @param passosY
     * @param ambiente
     */
    public void aspirarRobo(int passosX, int passosY, Ambiente ambiente){
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
     */
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
                if (this.getSensorProximidade().getUltimoTipoDetectado() == TipoEntidade.ROBO){ // O código abaixo é executado se o Aspirador identificar um robô
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
                if (this.getSensorProximidade().getUltimoTipoDetectado() == TipoEntidade.ROBO){ // O código abaixo é executado se o Aspirador identificar um robô
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

    