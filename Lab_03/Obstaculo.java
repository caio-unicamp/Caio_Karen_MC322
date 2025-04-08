public class Obstaculo{
    int posX1;
    int posY1;
    int altura;
    int posX2;
    int posY2;

    public Obstaculo(){
        
    }
    
    public enum TipoObstaculo{
        MINA_TERRESTRE(0, false);

        TipoObstaculo(int altura, boolean obstaculoAereo){

        }
    }
    
}