package robot.terrestre;
import interfaces.*;
import excecoes.*;
import ambiente.*;
import enums.TipoEntidade;
import robot.Robo;

public class Rover extends RoboTerrestre{
    //atributo próprio
    int tempoLocomocaoTerrestre;
    int qtdRobosDerrubados;
    //Construtor
    public Rover(String nome, String direcao, int x, int y, int velocidadeMaxima, int tempoLocomocaoTerrestre){
        super(nome, direcao, x, y, velocidadeMaxima, tempoLocomocaoTerrestre);
        this.qtdRobosDerrubados = 0;
        this.tempoLocomocaoTerrestre = tempoLocomocaoTerrestre;
    }
    /**
     * Move recursivamente o rover empurrando quem estiver no caminho dele
     * @throws ErroComunicacaoException 
     */
    @Override
    public void mover(int deltaX, int deltaY, Ambiente ambiente) throws SensorDesligadoException, RoboDesligadoException, ColisaoException, ErroComunicacaoException {
        int posInicialX = this.getPosicao()[0];
        int posInicialY = this.getPosicao()[1];
        int[] passos = this.getPasso(deltaX, deltaY);
        if (passos[0] == 0 && passos[1] == 0) {
            return; // Evita chamadas infinitas
        }
        if (deltaX == 0 && deltaY == 0) { 
            return; // O rover já chegou ao destino, então para a recursão
        }
        
        super.mover(deltaX, deltaY, ambiente);
        int posAtualX = this.getPosicao()[0];
        int posAtualY = this.getPosicao()[1];
        if (posAtualX == posInicialX + deltaX && posAtualY == posInicialY + deltaY) {
            return; // Se ele andou tudo, não há necessidade de verificar colisões
        }

        if (passos[0] != 0 && ambiente.dentroDosLimites(posAtualX + passos[0], posAtualY, this.getPosicao()[2])){ // Movimeto em X
            if (this.getSensorProximidade().monitorar(posAtualX + passos[0], posAtualY, this.getPosicao()[2], ambiente)){
                if (this.getSensorProximidade().getUltimoTipoDetectado() == TipoEntidade.ROBO){ // O código abaixo é executado se o Rover identificar um robô
                    Robo roboEmpurrado = getRoboNaPosicao(posAtualX + passos[0], this.getPosicao()[1], ambiente);
                    if (roboEmpurrado != null){
                        this.empurrarRobo(roboEmpurrado, passos[0], 0, ambiente);
                    }
                    this.setPosicao(posAtualX + passos[0], posAtualY, this.getPosicao()[2]);
                    this.mover(deltaX - passos[0], 0, ambiente); //Continua o caminho em X decrementando o tanto que já foi andado
                } 
            }     
        }else if (passos[1] != 0 && ambiente.dentroDosLimites(posAtualX, posAtualY + passos[1], this.getPosicao()[2])){ // Movimeto em Y
            if (this.getSensorProximidade().monitorar(posAtualX, posAtualY + passos[1], this.getPosicao()[2], ambiente)){
                if (this.getSensorProximidade().getUltimoTipoDetectado() == TipoEntidade.ROBO){ // O código abaixo é executado se o Rover identificar um robô
                    Robo roboEmpurrado = getRoboNaPosicao(posAtualX, posAtualY + passos[1], ambiente);
                    if (roboEmpurrado != null){
                        this.empurrarRobo(roboEmpurrado, 0, passos[1], ambiente);
                    }
                    this.setPosicao(posAtualX, posAtualY + passos[1], this.getPosicao()[2]);
                    this.mover(0, deltaY - passos[1], ambiente); //Continua o caminho em Y decrementando o tanto que já foi andado
                }
            }
        }
    }
    /**
     * Empurra um robô que estiver no caminho
     * @param empurrado
     * @param deltaX
     * @param deltaY
     * @param ambiente
     * @throws ColisaoException
     * @implNote Caso existam vários robôs na linha que o rover tenta se mover, cada robô consegue empurrar o próximo na fila
     */
    public void empurrarRobo(Robo empurrado, int deltaX, int deltaY, Ambiente ambiente) throws ColisaoException{
        int[] novaPosicao = getPosicaoFinalRoboEmpurrado(deltaX, deltaY, empurrado, ambiente);
        if (this.getSensorProximidade().monitorar(novaPosicao[0], novaPosicao[1], 0, ambiente)){ //Caso encontre um robô enquanto está se mexendo, também irá empurrar este
            Robo novoRoboEmpurrado = getRoboNaPosicao(novaPosicao[0], novaPosicao[1], ambiente);
            if (novoRoboEmpurrado != null){
                ambiente.getListaEntidades().add(novoRoboEmpurrado);
                empurrarRobo(novoRoboEmpurrado, deltaX, deltaY, ambiente);
            }
        }
        empurrado.setPosicao(novaPosicao[0], novaPosicao[1], 0); //Como o robô está sendo empurrado pelo rover ele também passará a ignorar qualquer obstáculo, então basta setar a posição nova dele até a posição final dele até o fim da locomoção do rover
    }   
    /**
     * Retorna as posições finais dos robôs que serão empurrados pelo rover.
     * @param deltaX
     * @param deltaY
     * @param empurrado
     * @param ambiente
     * @return uma lista de tamanho 2 representando as posições finais do robô após ser empurrado, sendo o índice 0 a posição x e o índice 1 a posição y.
     * @throws ColisionException
     */
    public int[] getPosicaoFinalRoboEmpurrado(int deltaX, int deltaY, Robo empurrado, Ambiente ambiente) throws ColisaoException{ 
        int posicaoX = empurrado.getPosicao()[0] + deltaX;
        int posicaoY = empurrado.getPosicao()[1] + deltaY;
        
        if (!ambiente.dentroDosLimites(posicaoX, posicaoY, 0)){ //Se o robô que foi empurrado sai dos limites, ele é eliminado da lista de robôs ativos
            ambiente.getListaEntidades().remove(empurrado);
            qtdRobosDerrubados++;
        }

        return new int[] {posicaoX, posicaoY};
    }
    /**
     * Verifica quantos robôs foram empurrados pelo rover no total
     * @param ambiente
     * @return número de robôs que o rover já empurrou
     */
    public int getQtdRobosEmpurrados(Ambiente ambiente){
        return ambiente.getListaEntidades().size();
    }
    /**
     * Verifica quantos robôs foram derrubados do ambiente
     * @return número de robôs que o rover derrubou do mapa
     */
    public int getRobosDerrubados(){ 
        return qtdRobosDerrubados;
    }
    /**
     * 
     * @param x
     * @param y
     * @param ambiente
     * @return O robô que está nas coordenadas procuradas, caso não haja nenhum robô nessas coordenadas do ambiente, retorna NULL
     */
    private Robo getRoboNaPosicao(int x, int y, Ambiente ambiente) { //Função para não ter que percorrer a lista inteira de robôs até achar um específico
        for (Entidade entidade : ambiente.getListaEntidades()) {
            if (!(entidade instanceof Robo)){ //Verifica se a entidade é um robô
                continue; //Se não for, segue para a próxima interação
            }else{
                Robo robo = (Robo) entidade;
                if (robo.getPosicao()[0] == x && robo.getPosicao()[1] == y) { 
                    return robo;
                }
            }
        }
        return null; 
    }
}