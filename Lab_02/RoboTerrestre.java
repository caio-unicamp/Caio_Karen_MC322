public class RoboTerrestre extends Robo{
    private int velocidadeMaxima;
    private int tempoLocomocao;
    
    public RoboTerrestre(String nome, String direcao, int posicaoX, int posicaoY, int velocidadeMaxima){
        super(nome, direcao, posicaoX, posicaoY);
        this.velocidadeMaxima = velocidadeMaxima;
        this.tempoLocomocao = 7;
    }

    public void mover(int deltaX, int deltaY){
        if (Math.sqrt(deltaY*deltaY + deltaX*deltaX) < velocidadeMaxima){
            super.mover(deltaX, deltaY);
        }else{
            System.out.println("O " + getNome() + " quer ir rápido demais! tente desacelerar um pouco");
        }
    }


} 