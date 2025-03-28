public class Drone extends RoboAereo{
    //atributo próprio é um robô que será entregue pelo drone
    Robo pacote;
    Ambiente ambiente;
    int posicaoXdrone, posicaoYdrone, posicaoZdrone;
    //Construtor para inicializar os atributos
    public Drone(String nome, String direcao, int x, int y, int altitude, Ambiente ambiente){
        //inicializar os atributos do drone
        super(nome, direcao, x, y, altitude, ambiente);
        this.ambiente = ambiente;
        this.posicaoXdrone = x;
        this.posicaoYdrone = y;
        this.posicaoZdrone = altitude;
    }
    //método de se mover
    public boolean entregarPacote(int posicaoXdronefinal, int posicaoYdronefinal){ //Função para entregar um pacote. Essa função é um booleano pois caso o pacote seja entregue retorna true e caso não seja, retorna false
        //inicializar o robo pacote
        pacote = new Rover("Pacote1", "Sul", this.posicaoXdrone, this.posicaoYdrone, this.posicaoZdrone, ambiente);
        ambiente.adicionarRobo(pacote);
        mover(posicaoXdronefinal - posicaoXdrone, posicaoYdronefinal - posicaoYdrone); //Move o drone até a posição final da entrega e na função mover ele identifica se encontra outro robô no caminho, com isso ele esbarra no robô e derruba o pacote
        pacote.mover(posicaoXdronefinal - posicaoXdrone, posicaoYdronefinal - posicaoYdrone); //Move o pacote junto do drone
                
        pacote.setPosicao(posicaoXdronefinal, posicaoYdronefinal, 0); //Como o pacote não é um robô aéreo e não tem um método de descer, seta a coordenada z dele no chão independentemente se ele foi entregue ou foi derrubado
        if ((this.posicaoXdrone == posicaoXdronefinal) && (this.posicaoYdrone == posicaoYdronefinal)){ //se chegar na posição que ele quer, ele derruba o robo no local que parou            
            this.descer(this.getPosicao()[2]); //Função para mover o drone para baixo
            if (this.posicaoZdrone == 0){ //Nesse ponto depois da descida o pacote foi entregue com sucesso
                return true;
            }
            return false; //Caso chegue nessa parte é porque o drone encontrou um problema no caminho de descida e 
        }
        else {
            return false; //Caso chegue aqui significa que o drone encontrou obstáculos no plano XY e derrubou o Rover
        }   
    
    }
}