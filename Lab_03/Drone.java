public class Drone extends RoboAereo{
    Robo pacote; //Atributo próprio é um robô que será entregue pelo drone
    int tempoLocomocaoPacote;
    
    public Drone(String nome, String direcao, int x, int y, int altitude, Ambiente ambiente, int tempoLocomocaoTerrestre){ //Construtor para inicializar os atributos
        super(nome, direcao, x, y, altitude, ambiente);
        this.tempoLocomocaoPacote = tempoLocomocaoTerrestre;
    }

    public boolean entregarPacote(int posicaoXdronefinal, int posicaoYdronefinal, String nomePacote, Ambiente ambiente){ //Função para entregar um pacote. Essa função é um booleano pois caso o pacote seja entregue retorna true e caso não seja, retorna false
        //inicializar o robo pacote
        pacote = new Rover(nomePacote, this.getDirecao(), this.getPosicao()[0], this.getPosicao()[1], this.getPosicao()[2], ambiente, tempoLocomocaoPacote);
        ambiente.adicionarRobo(pacote); //Adiciona o pacote na lista de Robôs ativos
        int posZinicial = this.getPosicao()[2];

        for (int i = 1; i <= Math.abs(posicaoXdronefinal - this.getPosicao()[0]); i++){ //Faz a identificação por todo o percurso em x
            if (identificarRobo(this.getPosicao()[0] + this.getPasso(posicaoXdronefinal - this.getPosicao()[0], posicaoYdronefinal - this.getPosicao()[1])[0], this.getPosicao()[1], this.getPosicao()[2], nomePacote, ambiente)){
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
            if (identificarRobo(posicaoXdronefinal, this.getPosicao()[1] + this.getPasso(posicaoXdronefinal - this.getPosicao()[0], posicaoYdronefinal - this.getPosicao()[1])[1], this.getPosicao()[2], nomePacote, ambiente)){
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
                this.setPosicao(this.getPosicao()[0], this.getPosicao()[1], posZinicial); //Após a entrega o drone sobe para a altura que estava no início, como a função descer já analisou se existiam obstáculos, basta apenas setar a posição z como a mesma do início sem necessidade de nenhuma conferência extra
                return true;
            }  
        }
        return false; //Caso chegue nessa parte é porque o drone encontrou um problema no caminho de descida ou o drone encontrou obstáculos no plano XY e derrubou o Rover
    }

    public boolean jaExisteRobo(Robo pacote, Ambiente ambiente){
        for (Robo robo : ambiente.getListaRobos()){
            if (robo.getNome() != pacote.getNome() && robo.getPosicao()[0] == this.getPosicao()[0] && robo.getPosicao()[1] == this.getPosicao()[1] && robo.getPosicao()[2] == 0){ //Se já existe um robô no lugar que o pacote seria derrubado, ambos são destruídos
                destroiPacote(robo, pacote, ambiente);
                return true;
            }
        }
        return false;
    }
    
    public void destroiPacote(Robo robo, Robo pacote, Ambiente ambiente){
        ambiente.removerRobo(robo);
        ambiente.removerRobo(pacote);
        System.out.println("Más notícias, já tinha outro robô no canto que seu pacote seria derrubado, então ambos serão destruídos... é uma pena, mas é assim que funciona a vida.");
        System.out.println("Os robôs " + robo.getNome() + " e " + pacote.getNome() + " foram destruídos.");
    }
}