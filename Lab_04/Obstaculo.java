public class Obstaculo{
    private final int posX1;
    private final int posY1;
    private final int altura;
    private final int posX2;
    private final int posY2;
    private TipoObstaculo tipoObstaculo;
    //Imagine o nosso plano cartesiano da seguinte forma
    // y
    // ^
    // |                    # isso define x2,y2
    // |
    // |
    // |
    // |
    // |
    // |      # isso define x1,y1
    // ------------------------------------>x
    // Essa área está demarcada pelo obstáculo  de modo que tudo que estiver numa cordenada x1<= x <= x2 e y1<=y<=y2 é a área que o robô não pode andar
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
        return this.getTipoObstaculo().getAltura();
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
    public void setTipoObstaculo(TipoObstaculo tipoObstaculo) {
        this.tipoObstaculo = tipoObstaculo;
    }

}