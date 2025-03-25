public class RoboTerrestre extends Robo{
    private int velocidadeMaxima; //Velocidade máxima permitida para o robô
    private int tempoLocomocaoTerrestre; //Tempo que o robô demora em uma locomoção
    
    public RoboTerrestre(String nome, String direcao, int posicaoX, int posicaoY, int velocidadeMaxima, Ambiente ambiente){
        super(nome, direcao, posicaoX, posicaoY, 0,ambiente); //Herança da classe robô
        this.velocidadeMaxima = velocidadeMaxima;
        this.tempoLocomocaoTerrestre = 7;
    }

    public void mover(int deltaX, int deltaY){ //Função sobreposta para mover o robô terrestre
        if (Math.sqrt(deltaX*deltaX + deltaY*deltaY)/tempoLocomocaoTerrestre < velocidadeMaxima){   
            //se a velocidade for menor que a máxima
            super.mover(deltaX, deltaY);
        }else{
            //se a velocidade for maior que a máxima
            System.out.println("O " + getNome() + " quer ir rápido demais! tente desacelerar um pouco"); //Mudar esse print para a main!!!!!
        }
    }

    public int getVelocidadeMaxima(){
        return velocidadeMaxima;
    }
} 