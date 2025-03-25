import java.util.ArrayList;

public class RoboAereo extends Robo{
    private int altitudeMaxima; //Altitude máxima que o robô aéreo pode alcançar
    private int altitude;
    
    public RoboAereo(String nome, String direcao, int posicaoX, int posicaoY, int altitude, Ambiente ambiente){
        super(nome, direcao, posicaoX, posicaoY, ambiente); //Herança da classe robô
        this.altitude = altitude;
    }

    public boolean subir(int metros){
        if (metros > 0 && ((altitude + metros) < altitudeMaxima) && !identificarRobo()){
            this.altitude += metros;
            return true; //Retorna no caso dele poder subir
        }else{
            return false; //Retorna no caso dele não poder subir
        }
    }

    public boolean descer(int metros){
        if (metros < 0 && altitude > metros && !identificarRobo()){
            this.altitude -= metros;
            return true; //Retorna no caso dele não poder descer
        }else{
            return false; //Retorna no caso dele não poder descer
        }
    }
}