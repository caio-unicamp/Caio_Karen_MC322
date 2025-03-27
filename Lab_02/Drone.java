public class Drone extends RoboAereo{
    //atributo próprio é um robô que será entregue pelo drone
    Robo pacote;
    //Construtor para inicializar os atributos
    public Drone(String nome, String direcao, int x, int y, int altitude, Ambiente ambiente){
        //inicializar os atributos do drone
        super(nome, direcao, x, y, altitude, ambiente);
        //inicializar o robo pacote
        this.pacote = new Robo("Pacote1", "Sul", x, y, 0, ambiente);
    }
  
    //método de se mover
    public void entregarPacote(int deltaX, int deltaY){             
        //guardar a posição inicial
        this.posicaoX = int xVelha
        this.posicaoY = int yVelho
        
        
        ////se esbarrar em algum objeto ou chegar na posição que ele quer, ele derruba o robo no local que parou
        if (super.identificarRobo(this.posicaoX + deltaX, this.posicaoY + deltaY)) || ((this.posicaoX == xVelho + deltaX) && (this.posicaoY == yVelho + deltaY)){
            //descer o pacote
            super.RoboAereo.descer(this.getPosicao()[2]) //Função para mover o drone para baixo
            //criar um novo robo para o pacote entrar no ambiente
        }
        else {
            //mover o robô
            super.mover(deltaX, deltaY);
        }   
    
    
}