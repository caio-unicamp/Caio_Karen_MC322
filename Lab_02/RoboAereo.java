public class RoboAereo extends Robo{
    private int altitudeMaxima; //Altitude máxima que o robô aéreo pode alcançar
    private int altitude; //Altitude atual do Robô
    
    public RoboAereo(String nome, String direcao, int posicaoX, int posicaoY, int altitude, Ambiente ambiente){ //Constructor para inicializar os atributos do robô aéreo
        super(nome, direcao, posicaoX, posicaoY, altitude, ambiente); //Herança da classe robô
        this.altitude = altitude;
        this.altitudeMaxima = 5;
    }
    
    public boolean subir(int metros){ //Função para mover o robô aéreo para cima respeitando os limites
        if (((altitude + metros) < altitudeMaxima) && !identificarRobo(this.getPosicao()[0], this.getPosicao()[1], this.getPosicao()[2] + metros, this.getNome())){     //o robo se teletransporta pra cima
            this.setPosicao(this.getPosicao()[0], this.getPosicao()[1],this.getPosicao()[2] + metros);
            return true; //Retorna no caso dele poder subir
        } else{
            return false; //Retorna no caso dele não poder subir
        }
    }

    public boolean descer(int metros, Ambiente ambiente){ //Função para mover o robô aéreo para baixo respeitando os limites
        if (ambiente.dentroDosLimites(ambiente.getLimites()[0],ambiente.getLimites()[1], ambiente.getLimites()[2]) && !identificarRobo(this.getPosicao()[0], this.getPosicao()[1], this.getPosicao()[2] - metros, this.getNome())){
            this.setPosicao(this.getPosicao()[0], this.getPosicao()[1],this.getPosicao()[2] - metros);
            return true; //Retorna no caso dele poder descer
        }else{
            return false; //Retorna no caso dele não poder descer
        }
    }
}