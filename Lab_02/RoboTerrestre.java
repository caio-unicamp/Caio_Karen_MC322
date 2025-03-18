public class RoboTerrestre extends Robo{
    private int velocidadeMaxima; //Velocidade máxima permitida para o robô
    private int tempoLocomocaoTerrestre; //Tempo que o robô demora em uma locomoção
    
    public RoboTerrestre(String nome, String direcao, int posicaoX, int posicaoY, int velocidadeMaxima){
        super(nome, direcao, posicaoX, posicaoY);
        this.velocidadeMaxima = velocidadeMaxima;
        this.tempoLocomocaoTerrestre = 7;
    }

    public void mover(int deltaX){
        if (Math.sqrt(deltaX*deltaX) < velocidadeMaxima){
            super.mover(deltaX, 0); //Como o robô é terrestre não faz sentido ele se mover na vertical
        }else{
            System.out.println("O " + getNome() + " quer ir rápido demais! tente desacelerar um pouco");
        }
    }
} 