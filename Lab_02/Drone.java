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

    public boolean entregarPacote(int posicaoXdronefinal, int posicaoYdronefinal){ //Função para entregar um pacote. Essa função é um booleano pois caso o pacote seja entregue retorna true e caso não seja, retorna false
        //inicializar o robo pacote
        pacote = new Rover("Pacote1", "Sul", this.posicaoXdrone, this.posicaoYdrone, this.posicaoZdrone, ambiente, tempoLocomocaoPacote);
        ambiente.adicionarRobo(pacote); //Adiciona o pacote na lista de Robôs ativos

        int posXinicial = this.posicaoXdrone;
        int posYinicial = this.posicaoYdrone;
        
        super.mover(posicaoXdronefinal - posicaoXdrone, posicaoYdronefinal - posicaoYdrone); //Move o drone até a posição final da entrega e na função mover ele identifica se encontra outro robô no caminho, com isso ele esbarra no robô e derruba o pacote

        if (this.posicaoXdrone == posXinicial && this.posicaoYdrone == posYinicial){ //Se o drone não se moveu ele não deve mover o pacote
            return false;
        }
        
        pacote.mover(posicaoXdronefinal - this.posicaoXdrone, posicaoYdronefinal - this.posicaoYdrone); //Move o pacote junto do drone
        pacote.setPosicao(posicaoXdronefinal, posicaoYdronefinal, 0); //Como o pacote não é um robô aéreo e não tem um método de descer, seta a coordenada z dele no chão independentemente se ele foi entregue ou foi derrubado
                
        if ((this.posicaoXdrone == posicaoXdronefinal) && (this.posicaoYdrone == posicaoYdronefinal)){ //se chegar na posição que ele quer, ele derruba o robo no local que parou            
            this.descer(this.getPosicao()[2]); //Função para mover o drone para baixo
            if (this.posicaoZdrone == 0){ //Nesse ponto depois da descida o pacote foi entregue com sucesso
                return true;
            }  
        }
        return false; //Caso chegue nessa parte é porque o drone encontrou um problema no caminho de descida ou o drone encontrou obstáculos no plano XY e derrubou o Rover
    }
}