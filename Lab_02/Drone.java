public class Drone extends RoboAereo{
    //atributo próprio é um robô que será entregue pelo drone
    Robo pacote;
    Ambiente ambiente;
    //Construtor para inicializar os atributos
    public Drone(String nome, String direcao, int x, int y, int altitude, Ambiente ambiente){
        //inicializar os atributos do drone
        super(nome, direcao, x, y, altitude, ambiente);
        this.ambiente = ambiente;
    }
    //método de se mover
    public void entregarPacote(int posicaoXfinal, int posicaoYfinal){ //guardar a posição inicial
        //inicializar o robo pacote
        pacote = new Robo("Pacote1", "Sul", posicaoXfinal, posicaoYfinal, 0, ambiente);
        this.mover(posicaoXfinal, posicaoYfinal); //Move o drone até a posição final da entrega e na função mover ele identifica se encontra outro robô no caminho, com isso ele esbarra no robô e derruba o pacote

        if ((this.posicaoX == xVelho + deltaX) && (this.posicaoY == yVelho + deltaY)){ //se chegar na posição que ele quer, ele derruba o robo no local que parou
            //descer o pacote
            super.RoboAereo.descer(this.getPosicao()[2]); //Função para mover o drone para baixo
            //criar um novo robo para o pacote entrar no ambiente
        }
        else {
            //mover o robô
            super.mover(deltaX, deltaY);
        }   
    
    }
}