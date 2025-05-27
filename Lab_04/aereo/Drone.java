public class Drone extends RoboAereo implements Comunicavel{
    Robo pacote; //Atributo próprio é um robô que será entregue pelo drone
    int tempoLocomocaoPacote;
    
    public Drone(String nome, String direcao, int x, int y, int altitude, int tempoLocomocaoTerrestre, int altitudeMaxima){ //Construtor para inicializar os atributos
        super(nome, direcao, x, y, altitude, altitudeMaxima);
        this.tempoLocomocaoPacote = tempoLocomocaoTerrestre;
    }

    @Override
    public void mover(int deltaX, int deltaY, Ambiente ambiente){ // Função para mover o drone sem entregar nenhum pacote
        int posInicialX = this.getPosicao()[0];
        int posInicialY = this.getPosicao()[1];
        int[] passos = this.getPasso(deltaX, deltaY);
        if (passos[0] == 0 && passos[1] == 0) {
            return; // Evita chamadas infinitas
        }
        if (deltaX == 0 && deltaY == 0) { 
            return; // O drone já chegou ao destino, então para a recursão
        }
        
        super.mover(deltaX, deltaY, ambiente);
        int posAtualX = this.getPosicao()[0];
        int posAtualY = this.getPosicao()[1];
        if (posAtualX == posInicialX + deltaX && posAtualY == posInicialY + deltaY) {
            return; // Se ele andou tudo, não há necessidade de verificar colisões
        }
    }
    
    public boolean entregouPacote(int posicaoXdronefinal, int posicaoYdronefinal, String nomePacote, Ambiente ambiente){ //Função para entregar um pacote. Essa função é um booleano pois caso o pacote seja entregue retorna true e caso não seja, retorna false
        //inicializar o robo pacote
        int posInicialX = this.getPosicao()[0];
        int posInicialY = this.getPosicao()[1];
        int posInicialZ = this.getPosicao()[2];
        
        this.mover(posicaoXdronefinal - posInicialX, posicaoYdronefinal - posInicialY, ambiente);
        
        int numRobosAnalisados = 0;
        for (Entidade entidade : ambiente.getListaEntidades()){
            if (!(entidade instanceof Robo)){ //Verifica se a entidade é um robô
                continue; //Caso não seja, passa para a próxima interação
            }else{
                Robo robo = (Robo) entidade; //Faz o cast para robô
                if (!robo.equals(this)){
                    numRobosAnalisados++;
                }else{
                    break;
                }
            }
        }if (numRobosAnalisados == ambiente.getListaRobos().size()){ //Caso o próprio drone não esteja na lista de robôs ativos é porque ou ele explodiu ou ele caiu num buraco enquanto se movia, então não adiciona o pacote que ele carregava 
            return false;
        }
        
        if (this.getPosicao()[0] == posicaoXdronefinal && this.getPosicao()[1] == posicaoYdronefinal){ //Se o drone já chegou na posição que ele quer, ele desce com o robo
            this.descer(this.getPosicao()[2] - 1, ambiente); //Desce o drone até uma unidade antes do chão para conseguir entregar o pacote
            if (this.getPosicao()[2] == 1){ //Nesse ponto depois da descida o pacote foi entregue com sucesso
                this.setPosicao(this.getPosicao()[0], this.getPosicao()[1], posInicialZ); //Após a entrega o drone sobe para a altura que estava no início, como a função descer já analisou se existiam obstáculos, basta apenas setar a posição z como a mesma do início sem necessidade de nenhuma conferência extra
                for (Obstaculo obstaculo: ambiente.getListaObstaculos()){
                    if (obstaculo.getPosX1() <= this.getPosicao()[0] && obstaculo.getPosX2() >= this.getPosicao()[0] && obstaculo.getPosY1() <= this.getPosicao()[1] && obstaculo.getPosY2() >= this.getPosicao()[1] && obstaculo.getAltura() >= this.getPosicao()[2]){ //Se já existe um obstáculo no lugar que o pacote seria derrubado, ele será destruído
                        if (obstaculo.getTipoObstaculo().equals(TipoObstaculo.MINA_TERRESTRE)){ // No caso do pacote cair em uma mina, ela é destruída
                            ambiente.removerObstaculo(obstaculo);
                        }
                        return false;
                    }
                }
                if (jaExisteRobo(ambiente)){ //Caso exista robô onde seria entregue, ele e o pacote serão destruídos
                    for (Robo robo : ambiente.getListaRobos()){
                        if (robo.getPosicao()[0] == this.getPosicao()[0] && robo.getPosicao()[1] == this.getPosicao()[1] && robo.getPosicao()[2] == 0){ //Se já existe um robô no lugar que o pacote seria derrubado, ele será destruído
                            destroiRoboColidido(robo, ambiente);
                        }
                    }
                    return false;
                }else {
                    adicionaPacote(nomePacote, ambiente);
                    return true;
                }
            }else{
                for (Robo robo : ambiente.getListaRobos()){
                    if (robo.getPosicao()[0] == this.getPosicao()[0] && robo.getPosicao()[1] == this.getPosicao()[1] && robo.getPosicao()[2] == 0){ //Se já existe um robô no lugar que o pacote seria derrubado, ele será destruído
                        destroiRoboColidido(robo, ambiente);
                    }
                }
            }
        }
        return false; // Caso o robô não tenha conseguido entregar o pacote, retorna false
    }

    public boolean jaExisteRobo(Ambiente ambiente){
        for (Robo robo : ambiente.getListaRobos()){
            if (robo.getPosicao()[0] == this.getPosicao()[0] && robo.getPosicao()[1] == this.getPosicao()[1] && robo.getPosicao()[2] == 0){ //Se já existe um robô no lugar que o pacote seria derrubado, ambos são destruídos
                return true;
            }
        }
        return false;
    }
    
    public void adicionaPacote(String nomePacote, Ambiente ambiente){ //Função para adicionar o pacote na lista de robôs ativos
        pacote = new Rover(nomePacote, this.getDirecao(), this.getPosicao()[0], this.getPosicao()[1], this.getPosicao()[2], tempoLocomocaoPacote);
        ambiente.adicionarRobo(pacote); //Adiciona o pacote na lista de Robôs ativos
    }

    public void destroiRoboColidido(Robo roboColidido, Ambiente ambiente){ //Função para destruir o robô colidido
        ambiente.removerRobo(roboColidido);
    }
}