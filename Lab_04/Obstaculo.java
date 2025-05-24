public class Obstaculo{
    private final int posX1;
    private final int posY1;
    private final int posX2;
    private final int posY2;
    private final TipoObstaculo tipoObstaculo; //Tipo do obstáculo de acordo com o enum referente a ele
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
    public Obstaculo(int posX1, int posY1, int posX2, int posY2, TipoObstaculo tipoObstaculo, Ambiente ambiente){ //Constructor da classe Obstáculo
        this.posX1 = posX1;
        this.posY1 = posY1;
        this.posX2 = posX2;
        this.posY2 = posY2;
        this.tipoObstaculo = tipoObstaculo;
    }
    
    public int getPosX1() { //Retorna a posição X1 do obstáculo
        return posX1;
    }
    public int getPosY1() { //Retorna a posição Y1 do obstáculo
        return posY1;
    }
    public int getAltura() { //Retorna a altura do obstáculo de acordo com o método do enum de TipoObstaculo
        return this.getTipoObstaculo().getAltura();
    }
    public int getPosX2() { //Retorna a posição X2 do obstáculo
        return posX2;
    }
    public int getPosY2() { //Retorna a posição Y2 do obstáculo
        return posY2;
    }
    public TipoObstaculo getTipoObstaculo() { //Retorna o tipo do obstáculo de acordo com o enum referente a ele
        return tipoObstaculo;
    }
}