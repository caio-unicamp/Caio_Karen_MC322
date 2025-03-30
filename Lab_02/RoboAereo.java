public class RoboAereo extends Robo{
    private int altitudeMaxima; //Altitude máxima que o robô aéreo pode alcançar
    private int altitude; //Altitude atual do Robô
    
    public RoboAereo(String nome, String direcao, int posicaoX, int posicaoY, int altitude, Ambiente ambiente){ //Constructor para inicializar os atributos do robô aéreo
        super(nome, direcao, posicaoX, posicaoY, altitude,ambiente); //Herança da classe robô
        this.altitude = altitude;
    }
    
    public void subir(int deltaZ){ //Função recursiva para mover o robô aéreo para cima respeitando os limites e identificando obstáculos no caminho
        if (deltaZ == 0){ //Nesse ponto subiu tudo o que precisava
            return;
        }

        if ((this.altitude + 1 <= altitudeMaxima) && !identificarRobo(this.getPosicao()[0], this.getPosicao()[1], this.getPosicao()[2] + 1, this.getNome())){
            this.altitude += 1;
            this.setPosicao(getPosicao()[0], this.getPosicao()[1], this.altitude);
            subir(deltaZ - 1);
            return; 
        }
    }

    public void descer(int deltaZ){ //Função recursiva para mover o robô aéreo para baixo respeitando os limites e identificando obstáculos no caminho
        if (deltaZ == 0){ //Nesse ponto já desceu tudo o que precisava
            return;
        }
        
        if ((this.altitude - 1 >= 0) && !identificarRobo(this.getPosicao()[0], this.getPosicao()[1], this.getPosicao()[2] - 1, this.getNome())){
            this.altitude -= 1;
            this.setPosicao(getPosicao()[0], this.getPosicao()[1], this.altitude);
            descer(deltaZ - 1);
            return; 
        }
    }
}