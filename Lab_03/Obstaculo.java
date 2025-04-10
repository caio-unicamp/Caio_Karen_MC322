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
    
    public int getPosX1() {
        return posX1;
    }
    public int getPosY1() {
        return posY1;
    }
    public int getAltura() {
        return altura;
    }
    public int getPosX2() {
        return posX2;
    }
    public int getPosY2() {
        return posY2;
    }
    public TipoObstaculo getTipoObstaculo() {
        return tipoObstaculo;
    }
    public void setPosX1(int posX1) {
        this.posX1 = posX1;
    }
    public void setPosY1(int posY1) {
        this.posY1 = posY1;
    }
    public void setAltura(int altura) {
        this.altura = altura;
    }
    public void setPosX2(int posX2) {
        this.posX2 = posX2;
    }
    public void setPosY2(int posY2) {
        this.posY2 = posY2;
    }
    public void setTipoObstaculo(TipoObstaculo tipoObstaculo) {
        this.tipoObstaculo = tipoObstaculo;
    }
}