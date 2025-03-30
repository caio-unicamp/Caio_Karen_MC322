public class Drone extends RoboAereo{
    //atributo próprio é um robô que será entregue pelo drone
    Robo pacote;
    Ambiente ambiente;
    int posicaoXdrone, posicaoYdrone, posicaoZdrone, tempoLocomocaoPacote;
    //Construtor para inicializar os atributos
    public Drone(String nome, String direcao, int x, int y, int altitude, Ambiente ambiente, int tempoLocomocaoTerrestre){
        //inicializar os atributos do drone
        super(nome, direcao, x, y, altitude, ambiente);
        this.ambiente = ambiente;
        this.posicaoXdrone = x;
        this.posicaoYdrone = y;
        this.posicaoZdrone = altitude;
        this.tempoLocomocaoPacote = tempoLocomocaoTerrestre;
    }

    public boolean entregarPacote(int posicaoXdronefinal, int posicaoYdronefinal, String nomePacote){ //Função para entregar um pacote. Essa função é um booleano pois caso o pacote seja entregue retorna true e caso não seja, retorna false
        //inicializar o robo pacote
        pacote = new Rover(nomePacote, this.getDirecao(), this.posicaoXdrone, this.posicaoYdrone, this.posicaoZdrone, ambiente, tempoLocomocaoPacote);
        ambiente.adicionarRobo(pacote); //Adiciona o pacote na lista de Robôs ativos
        int posZinicial = this.posicaoZdrone;

        for (int i = 1; i <= Math.abs(posicaoXdronefinal - posicaoXdrone); i++){ //Faz a identificação por todo o percurso em x
            if (identificarRobo(this.getPosicao()[0] + this.getPasso(posicaoXdronefinal - this.getPosicao()[0], posicaoYdronefinal - this.getPosicao()[1])[0], this.getPosicao()[1], this.getPosicao()[2], nomePacote)){
                pacote.setPosicao(this.getPosicao()[0], this.getPosicao()[1], 0); //Derruba o pacote
                return false; //Se identificou um obstáculo a entrega falhou
            }else{
                super.mover(this.getPasso(posicaoXdronefinal - this.getPosicao()[0], 0)[0], 0);
            }
        }
        for (int i = 1; i <= Math.abs(posicaoYdronefinal - posicaoYdrone); i++){ //Faz a identificação por todo o percurso em y
            if (identificarRobo(posicaoXdronefinal, this.getPosicao()[1] + this.getPasso(posicaoXdronefinal - this.getPosicao()[0], posicaoYdronefinal - this.getPosicao()[1])[1], this.getPosicao()[2], nomePacote)){
                pacote.setPosicao(this.getPosicao()[0], this.getPosicao()[1], 0); //Derruba o pacote
                return false; //Se identificou um obstáculo a entrega falhou
            }else{
                super.mover(0, this.getPasso(0 , posicaoYdronefinal - this.getPosicao()[1])[1]);
            }
        }
        
        //Atualiza a posição real do drone
        this.posicaoXdrone = this.getPosicao()[0];
        this.posicaoYdrone = this.getPosicao()[1];
        
        pacote.setPosicao(posicaoXdronefinal, posicaoYdronefinal, 0); //Como o pacote não é um robô aéreo e não tem um método de descer, seta a coordenada z dele no chão independentemente se ele foi entregue ou foi derrubado

        if ((this.posicaoXdrone == posicaoXdronefinal) && (this.posicaoYdrone == posicaoYdronefinal)){ //se chegar na posição que ele quer, ele desce com o robo            
            this.descer(this.getPosicao()[2] - 1); //Desce o drone até uma unidade antes do chão para conseguir entregar o pacote
            if (this.getPosicao()[2] == 1){ //Nesse ponto depois da descida o pacote foi entregue com sucesso
                this.setPosicao(this.getPosicao()[0], this.getPosicao()[1], posZinicial); //Após a entrega o drone sobe para a altura que estava no início, como a função descer já analisou se existiam obstáculos, basta apenas setar a posição z como a mesma do início sem necessidade de nenhuma conferência extra
                return true;
            }  
        }
        return false; //Caso chegue nessa parte é porque o drone encontrou um problema no caminho de descida ou o drone encontrou obstáculos no plano XY e derrubou o Rover
    }
}