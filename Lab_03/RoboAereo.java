public class RoboAereo extends Robo{
    private int altitudeMaxima; //Altitude máxima que o robô aéreo pode alcançar
    private int altitude; //Altitude atual do Robô
    private Ambiente ambiente; //Ambiente onde o robô está inserido
    
    public RoboAereo(String nome, String direcao, int posicaoX, int posicaoY, int altitude, Ambiente ambiente){ //Constructor para inicializar os atributos do robô aéreo
        super(nome, direcao, posicaoX, posicaoY, altitude, ambiente); //Herança da classe robô
        this.ambiente = ambiente; //Atributo do ambiente
        this.altitude = altitude;
        this.altitudeMaxima = ambiente.getLimites()[2]; //A altitude máxima é o limite do ambiente
    }
    
    public void subir(int deltaZ){ //Função recursiva para mover o robô aéreo para cima respeitando os limites identificando obstáculos no caminho
        if (deltaZ == 0){ //Nesse ponto subiu tudo o que precisava
            return; 
        }
        if ((this.altitude + 1) <= altitudeMaxima && !identificarRobo(this.getPosicao()[0], this.getPosicao()[1], this.getPosicao()[2] + 1, this.getNome())){ //O robô sobe recursivamente
            this.altitude++;
            this.setPosicao(this.getPosicao()[0], this.getPosicao()[1],this.altitude);
            subir(deltaZ - 1); //Chamada recursiva
            return; 
        }
    }

    public void descer(int deltaZ){ //Função recursiva para mover o robô aéreo para baixo respeitando os limites e identificando obstáculos no caminho
        if (deltaZ == 0){ //Nesse ponto desceu tudo o que precisava
            return; 
        }
        if (ambiente.dentroDosLimites(this.getPosicao()[0],this.getPosicao()[1], this.altitude - 1) && !identificarRobo(this.getPosicao()[0], this.getPosicao()[1], this.getPosicao()[2] - 1, this.getNome())){ //O robô desce recursivamente
            this.altitude--;
            this.setPosicao(this.getPosicao()[0], this.getPosicao()[1],this.altitude);
            descer(deltaZ - 1); //Chamada recursiva
            return; //Retorna no caso dele poder descer
        }else{
            return; //Retorna no caso dele não poder descer
        }
    }

    public boolean moveuTudo(int altitudeOriginal, int deltaZ){
        if (this.altitude + deltaZ == altitudeOriginal + deltaZ){ //Verifica se o robô chegou até a altura que era pra ir
            return true; 
        }else{
            return false;
        }
    }
}