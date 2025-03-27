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
    public void entregarPacote(int posicaoXdronefinal, int posicaoYdronefinal){ //guardar a posição inicial
        //inicializar o robo pacote
        pacote = new Rover("Pacote1", "Sul", this.posicaoXdrone, this.posicaoYdrone, this.posicaoZdrone, ambiente);
        mover(posicaoXdronefinal, posicaoYdronefinal); //Move o drone até a posição final da entrega e na função mover ele identifica se encontra outro robô no caminho, com isso ele esbarra no robô e derruba o pacote
        pacote.mover(posicaoXdronefinal, posicaoYdronefinal); //Move o pacote junto do drone
        Rover pacote = ((Rover) pacote); //Transforma o pacote em rover
        
        if ((this.posicaoXdrone == posicaoXdronefinal) && (this.posicaoYdrone == posicaoYdronefinal)){ //se chegar na posição que ele quer, ele derruba o robo no local que parou
            //descer o pacote
            
            this.descer(this.getPosicao()[2]); //Função para mover o drone para baixo
            //criar um novo robo para o pacote entrar no ambiente
        }
        else {
            //mover o robô
            super.mover(deltaX, deltaY);
        }   
    
    }
}