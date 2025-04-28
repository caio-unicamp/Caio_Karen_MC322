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
    
    @Override
    public void mover(int deltaX, int deltaY, Ambiente ambiente){ //Função para mover recursivamente o rover e acessar a função empurrar robô
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
                if (this.getSensorProximidade().identificarRobo(posAtualX + passos[0], posAtualY, this.getPosicao()[2], ambiente, this)){ // O código abaixo é executado se o robô não identificar um obstáculo mas sim um robô
                    Robo roboEmpurrado = getRoboNaPosicao(posAtualX + passos[0], this.getPosicao()[1], ambiente);
                    if (roboEmpurrado != null){
                        empurrarRobo(roboEmpurrado, passos[0], 0, ambiente);
                    }
                    this.setPosicao(posAtualX + passos[0], posAtualY, this.getPosicao()[2]);
                    this.mover(deltaX - passos[0], 0, ambiente); //Continua o caminho em X decrementando o tanto que já foi andado
                } 
            }     
        }else if (passos[1] != 0 && ambiente.dentroDosLimites(posAtualX, posAtualY + passos[1], this.getPosicao()[2])){ // Movimeto em Y
            if (this.getSensorProximidade().monitorar(posAtualX, posAtualY + passos[1], this.getPosicao()[2], ambiente)){
                if (this.getSensorProximidade().identificarRobo(posAtualX, posAtualY + passos[1], this.getPosicao()[2], ambiente, this)){
                    Robo roboEmpurrado = getRoboNaPosicao(posAtualX, posAtualY + passos[1], ambiente);
                    if (roboEmpurrado != null){
                        empurrarRobo(roboEmpurrado, 0, passos[1], ambiente);
                    }
                    this.setPosicao(posAtualX, posAtualY + passos[1], this.getPosicao()[2]);
                    this.mover(0, deltaY - passos[1], ambiente); //Continua o caminho em Y decrementando o tanto que já foi andado
                }
            }
        }
    }
    
    //metodo de mover com empurrar um robo junto
    public void empurrarRobo(Robo empurrado, int deltaX, int deltaY, Ambiente ambiente){
        int[] novaPosicao = getPosicaoFinalRoboEmpurrado(deltaX, deltaY, empurrado, ambiente);
        if (this.getSensorProximidade().monitorar(novaPosicao[0], novaPosicao[1], 0, ambiente)){ //Caso encontre um robô enquanto está se mexendo, também irá empurrar este
            Robo novoRoboEmpurrado = getRoboNaPosicao(novaPosicao[0], novaPosicao[1], ambiente);
            if (novoRoboEmpurrado != null){
                ambiente.getListaRobos().add(novoRoboEmpurrado);
                empurrarRobo(novoRoboEmpurrado, deltaX, deltaY, ambiente);
            }
        }
        empurrado.setPosicao(novaPosicao[0], novaPosicao[1], 0); //Como o robô está sendo empurrado pelo rover ele também passará a ignorar qualquer obstáculo, então basta setar a posição nova dele até a posição final dele até o fim da locomoção do rover
    }   

    public int[] getPosicaoFinalRoboEmpurrado(int deltaX, int deltaY, Robo empurrado, Ambiente ambiente){ //Função que retorna as posições finais dos robôs empurrados pelo rover
        int posicaoX = empurrado.getPosicao()[0] + deltaX;
        int posicaoY = empurrado.getPosicao()[1] + deltaY;
        
        if (!ambiente.dentroDosLimites(posicaoX, posicaoY, 0)){ //Se o robô que foi empurrado sai dos limites, ele é eliminado da lista de robôs ativos
            ambiente.getListaRobos().remove(empurrado);
            qtdRobosDerrubados++;
        }

        return new int[] {posicaoX, posicaoY};
    }

    public int getQtdRobosEmpurrados(Ambiente ambiente){ //Função que mostra quantos robôs foram empurrados
        return ambiente.getListaRobos().size();
    }
    public int getRobosDerrubados(){ //Função que mostra quantos robôs foram derrubados
        return qtdRobosDerrubados;
    }
    
    private Robo getRoboNaPosicao(int x, int y, Ambiente ambiente) { //Função para não ter que percorrer a lista inteira de robôs até achar um específico
        for (Robo robo : ambiente.getListaRobos()) {
            if (robo.getPosicao()[0] == x && robo.getPosicao()[1] == y) {
                return robo;
            }
        }
        return null;
    }
}