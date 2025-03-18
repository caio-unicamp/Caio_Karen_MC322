public class RoboTerrestre extends Robo{
    private int velocidadeMaxima; //Velocidade máxima permitida para o robô
    private int tempoLocomocaoTerrestre; //Tempo que o robô demora em uma locomoção
    
    public RoboTerrestre(String nome, String direcao, int posicaoX, int posicaoY, int velocidadeMaxima){
        super(nome, direcao, posicaoX, posicaoY); //Herança da classe robô
        this.velocidadeMaxima = velocidadeMaxima;
        this.tempoLocomocaoTerrestre = 7;
    }

    public void mover(int deltaX, int deltaY){
        if (Math.sqrt(deltaX*deltaX + deltaY*deltaY)/tempoLocomocaoTerrestre < velocidadeMaxima){
            super.mover(deltaX, deltaY);
        }else{
            // System.out.println("O " + getNome() + " quer ir rápido demais! tente desacelerar um pouco");
        }
    }
} 