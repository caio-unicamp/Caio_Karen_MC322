public class Aspirador extends RoboTerrestre{

    public Aspirador(String nome, String direcao, int x, int y, int velocidadeMaxima){
        super(nome, direcao, x, y, velocidadeMaxima);
    }

    public void eliminar(){
        if (super.identificarObstaculo()) {
            
        }
    }

}