public class Obstaculo{
    private int posX1;
    private int posY1;
    private int altura;
    private int posX2;
    private int posY2;
    private TipoObstaculo tipoObstaculo;
    
    public Obstaculo(int posX1, int posY1, int altura, int posX2, int posY2, TipoObstaculo tipoObstaculo, Ambiente ambiente){
        this.posX1 = posX1;
        this.posY1 = posY1;
        this.altura = altura;
        this.posX2 = posX2;
        this.posY2 = posY2;
        this.tipoObstaculo = tipoObstaculo;
    }
    
}