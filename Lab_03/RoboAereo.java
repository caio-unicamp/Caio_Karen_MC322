public class RoboAereo extends Robo{
    private int altitudeMaxima; //Altitude máxima que o robô aéreo pode alcançar
    private int altitude; //Altitude atual do Robô
    public RoboAereo(String nome, String direcao, int posicaoX, int posicaoY, int altitude, Ambiente ambiente){ //Constructor para inicializar os atributos do robô aéreo
        super(nome, direcao, posicaoX, posicaoY, altitude); //Herança da classe robô
        this.altitude = altitude;
        this.altitudeMaxima = ambiente.getLimites()[2]; //A altitude máxima é o limite do ambiente
    }
    
    public void subir(int deltaZ, Ambiente ambiente){ //Função recursiva para mover o robô aéreo para cima respeitando os limites identificando obstáculos no caminho
        if (deltaZ == 0){ //Nesse ponto subiu tudo o que precisava
            return; 
        }
        
        if ((this.altitude + 1) <= altitudeMaxima && !this.getSensorProximidade().monitorar(this.getPosicao()[0], this.getPosicao()[1], this.getPosicao()[2] + 1, ambiente, this)){ //O robô sobe recursivamente
            this.altitude++;
            this.setPosicao(this.getPosicao()[0], this.getPosicao()[1],this.altitude);
            subir(deltaZ - 1, ambiente); //Chamada recursiva
            return; 
        }
    }

    public void descer(int deltaZ, Ambiente ambiente){ //Função recursiva para mover o robô aéreo para baixo respeitando os limites e identificando obstáculos no caminho
        if (deltaZ == 0){ //Nesse ponto desceu tudo o que precisava
            return; 
        }
        if (ambiente.dentroDosLimites(this.getPosicao()[0],this.getPosicao()[1], this.altitude - 1) && !this.getSensorProximidade().monitorar(this.getPosicao()[0], this.getPosicao()[1], this.getPosicao()[2] - 1, ambiente, this)){ //O robô desce recursivamente
            this.altitude--;
            this.setPosicao(this.getPosicao()[0], this.getPosicao()[1],this.altitude);
            descer(deltaZ - 1, ambiente); //Chamada recursiva
            return; //Retorna no caso dele poder descer
        }else{
            return; //Retorna no caso dele não poder descer
        }
    }

    public boolean moveuTudo(int altitudeOriginal, int deltaZ){ //Verifica se o robô chegou até a altura que era pra ir
        return (this.altitude + deltaZ == altitudeOriginal + deltaZ); 
    }

    public SensorProximidade getSensorProximidade(){ //Função para retornar o sensor de proximidade do robô
        SensorProximidade sensorProx = null;
        for (Sensor<?> sensor : this.getSensores()) { //Procura na lista de sensores do robo pelo sensor de proximidade
            if (sensor instanceof SensorProximidade){ //Verifica se o sensor é do tipo SensorProximidade
                sensorProx = (SensorProximidade) sensor;
            }
        }
        return sensorProx;
    }
}