public class Drone extends RoboAereo{
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
        for (Robo robo : ambiente.getListaRobos()){
            if (!robo.equals(this)){
                numRobosAnalisados++;
            }else{
                break;
            }
        }if (numRobosAnalisados == ambiente.getListaRobos().size()){ //Caso o próprio drone não esteja na lista de robôs ativos é porque ou ele explodiu ou ele caiu num buraco enquanto se movia, então não adiciona o pacote que ele carregava 
            return false;
        }
        
        if (this.getPosicao()[0] == posicaoXdronefinal && this.getPosicao()[1] == posicaoYdronefinal){ //Se o drone já chegou na posição que ele quer, ele desce com o robo
            this.descer(this.getPosicao()[2] - 1, ambiente); //Desce o drone até uma unidade antes do chão para conseguir entregar o pacote
            if (this.getPosicao()[2] == 1){ //Nesse ponto depois da descida o pacote foi entregue com sucesso
                this.setPosicao(this.getPosicao()[0], this.getPosicao()[1], posInicialZ); //Após a entrega o drone sobe para a altura que estava no início, como a função descer já analisou se existiam obstáculos, basta apenas setar a posição z como a mesma do início sem necessidade de nenhuma conferência extra
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

        for (int i = 1; i <= Math.abs(posicaoXdronefinal - this.getPosicao()[0]); i++){ //Faz a identificação por todo o percurso em x
            if (identificarRobo(this.getPosicao()[0] + this.getPasso(posicaoXdronefinal - this.getPosicao()[0], posicaoYdronefinal - this.getPosicao()[1])[0], this.getPosicao()[1], this.getPosicao()[2], ambiente)){
                if (jaExisteRobo(pacote, ambiente)){ //Caso exista robô onde seria derrubado não precisa fazer mais nada pois ele e o pacote serão destruídos
                    return false;
                }
                pacote.setPosicao(this.getPosicao()[0], this.getPosicao()[1], 0); //Derruba o pacote
                return false; //Se identificou um obstáculo a entrega falhou
            }else{
                super.mover(this.getPasso(posicaoXdronefinal - this.getPosicao()[0], 0)[0], 0, ambiente);
            }
        }
        for (int i = 1; i <= Math.abs(posicaoYdronefinal - this.getPosicao()[1]); i++){ //Faz a identificação por todo o percurso em y
            if (identificarRobo(posicaoXdronefinal, this.getPosicao()[1] + this.getPasso(posicaoXdronefinal - this.getPosicao()[0], posicaoYdronefinal - this.getPosicao()[1])[1], this.getPosicao()[2], ambiente)){
                if (jaExisteRobo(pacote, ambiente)){ //Caso exista robô onde seria derrubado não precisa fazer mais nada pois ele e o pacote serão destruídos
                    return false;
                }
                pacote.setPosicao(this.getPosicao()[0], this.getPosicao()[1], 0); //Derruba o pacote
                return false; //Se identificou um obstáculo a entrega falhou
            }else{
                super.mover(0, this.getPasso(0 , posicaoYdronefinal - this.getPosicao()[1])[1], ambiente);
            }
        }
        
        //Atualiza a posição real do drone
        this.getPosicao()[0] = this.getPosicao()[0];
        this.getPosicao()[1] = this.getPosicao()[1];
        
        pacote.setPosicao(posicaoXdronefinal, posicaoYdronefinal, 0); //Como o pacote não é um robô aéreo e não tem um método de descer, seta a coordenada z dele no chão independentemente se ele foi entregue ou foi derrubado

        if ((this.getPosicao()[0] == posicaoXdronefinal) && (this.getPosicao()[1] == posicaoYdronefinal)){ //se chegar na posição que ele quer, ele desce com o robo            
            this.descer(this.getPosicao()[2] - 1, ambiente); //Desce o drone até uma unidade antes do chão para conseguir entregar o pacote
            if (this.getPosicao()[2] == 1){ //Nesse ponto depois da descida o pacote foi entregue com sucesso
                this.setPosicao(this.getPosicao()[0], this.getPosicao()[1], posInicialZ); //Após a entrega o drone sobe para a altura que estava no início, como a função descer já analisou se existiam obstáculos, basta apenas setar a posição z como a mesma do início sem necessidade de nenhuma conferência extra
                return true;
            }  
        }
        return false; //Caso chegue nessa parte é porque o drone encontrou um problema no caminho de descida ou o drone encontrou obstáculos no plano XY e derrubou o Rover
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
    
    public void destroiPacote(Robo pacote, Ambiente ambiente){
        ambiente.removerRobo(pacote);
    }

    public void destroiRoboColidido(Robo roboColidido, Ambiente ambiente){ //Função para destruir o robô colidido
        ambiente.removerRobo(roboColidido);
    }
}