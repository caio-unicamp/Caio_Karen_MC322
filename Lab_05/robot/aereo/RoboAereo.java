package robot.aereo;
import ambiente.*;
import excecoes.ColisaoException;
import excecoes.ErroComunicacaoException;
import excecoes.RoboDesligadoException;
import excecoes.SensorDesligadoException;
import interfaces.Manobravel;
import sensores.*;
import robot.Robo;
public class RoboAereo extends Robo implements Manobravel{
    private int altitudeMaxima; //Altitude máxima que o robô aéreo pode alcançar
    private int altitude; //Altitude atual do Robô

    public RoboAereo(String nome, String direcao, int posicaoX, int posicaoY, int altitude, int altitudeMaxima){ //Constructor para inicializar os atributos do robô aéreo
        super(nome, direcao, posicaoX, posicaoY, altitude); //Herança da classe robô
        this.altitude = altitude;
        this.altitudeMaxima = altitudeMaxima; //A altitude máxima é o limite do ambiente
    }
    /**
     * Sobe recursivamente o robô
     * @param deltaZ
     * @param ambiente
     * @throws ColisaoException
     * @throws ErroComunicacaoException 
     * @throws RoboDesligadoException 
     * @throws SensorDesligadoException 
     */
    public void subir(int deltaZ, Ambiente ambiente) throws ColisaoException, SensorDesligadoException, RoboDesligadoException, ErroComunicacaoException{ 
        if (deltaZ == 0){ //Nesse ponto subiu tudo o que precisava
            return; 
        }
        
        if (ambiente.dentroDosLimites(this.getPosicao()[0], this.getPosicao()[1], this.getPosicao()[2] + 1) && (this.altitude + 1) <= altitudeMaxima && !this.getSensorProximidade().monitorar(this.getPosicao()[0], this.getPosicao()[1], this.getPosicao()[2] + 1, ambiente, this)){ //O robô sobe recursivamente
            this.altitude++;
            this.setPosicao(this.getPosicao()[0], this.getPosicao()[1],this.altitude);
            this.subir(deltaZ - 1, ambiente);
            return; 
        }
    }
    /**
     * Desce recursivamente a posição do robô
     * @param deltaZ
     * @param ambiente
     * @throws ColisaoException
     * @throws ErroComunicacaoException 
     * @throws RoboDesligadoException 
     * @throws SensorDesligadoException 
     */
    public void descer(int deltaZ, Ambiente ambiente) throws ColisaoException, SensorDesligadoException, RoboDesligadoException, ErroComunicacaoException{
        if (deltaZ == 0){ //Nesse ponto desceu tudo o que precisava
            return; 
        }
        if (ambiente.dentroDosLimites(this.getPosicao()[0],this.getPosicao()[1], this.altitude - 1) && !this.getSensorProximidade().monitorar(this.getPosicao()[0], this.getPosicao()[1], this.getPosicao()[2] - 1, ambiente, this)){ //O robô desce recursivamente
            this.altitude--;
            this.setPosicao(this.getPosicao()[0], this.getPosicao()[1], this.altitude);
            this.descer(deltaZ - 1, ambiente);
            return; //Retorna no caso dele poder descer
        }else{
            return; //Retorna no caso dele não poder descer
        }
    }
    /**
     * Verifica se o robô chegou até a altura que deveria
     * @param altitudeOriginal
     * @param deltaZ
     * @return true se conseguiu e false caso contrário
     */
    public boolean moveuTudo(int altitudeOriginal, int deltaZ){ 
        return (this.altitude + deltaZ == altitudeOriginal + deltaZ); 
    }
    /**
     * Acessa o sensor de altitude do robô aéreo
     * @param robo
     * @return sensor de altitude
     */
    public SensorAltitude getSensorAltitude(Robo robo){ 
        SensorAltitude sensorAltitude = null;
        for (Sensor<?> sensor : robo.getSensores()){
            if (sensor instanceof SensorAltitude){
                sensorAltitude = (SensorAltitude) sensor;
            }
        }
        return sensorAltitude;
    }
}