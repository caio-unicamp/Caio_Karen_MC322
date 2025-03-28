public class RoboTerrestre extends Robo{
    private int velocidadeMaxima; //Velocidade máxima permitida para o robô
    private int tempoLocomocaoTerrestre; //Tempo que o robô demora em uma locomoção
    
    public RoboTerrestre(String nome, String direcao, int posicaoX, int posicaoY, int velocidadeMaxima, Ambiente ambiente, int tempoLocomocaoTerrestre){
        super(nome, direcao, posicaoX, posicaoY, 0,ambiente); //Herança da classe robô
        this.velocidadeMaxima = velocidadeMaxima;
        this.tempoLocomocaoTerrestre = tempoLocomocaoTerrestre; 
    }

    public void mover(int deltaX, int deltaY){ //Função sobreposta para mover o robô terrestre
        super.mover(deltaX, deltaY); 
        //Essa função será chamada junto da velMaxAtingida na main para que ele só se mova no caso de não ter sido atingido o limite de velocidade
    }

    public boolean velMaxAtingida(int deltaX, int deltaY){
        if (Math.sqrt(deltaX*deltaX + deltaY*deltaY)/tempoLocomocaoTerrestre < velocidadeMaxima){   
            return false;
        }else{
            return true;
        }
    }

    public int getVelocidadeMaxima(){
        return velocidadeMaxima;
    }

    public void setVelMaxima(int velMax){
        this.velocidadeMaxima = velMax;
    }
} 