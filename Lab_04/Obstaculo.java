public class Obstaculo implements Entidade{
    private final int posX1;
    private final int posY1;
    private final int posX2;
    private final int posY2;
    private final TipoObstaculo tipoObstaculo; //Tipo do obstáculo de acordo com o enum referente a ele
    /**
    <pre>
     *Imagine o nosso plano cartesiano da seguinte forma <p>
      y<p>
      ^<p>
      |               # isso define x2,y2 <p>  
      |<p>
      |<p>
      |<p>
      |<p>
      |<p>
      |<p>
      |      # isso define x1,y1 <p>
      ------------------------------------>x <p>
      </pre>
      Essa área está demarcada pelo obstáculo  de modo que tudo que estiver numa cordenada x1<= x <= x2 e y1<=y<=y2 é a área que o robô não pode andar
      @param posX1 
      @param posY1 
      @param posX2 
      @param posY2 
      @param tipoObstaculo
      @param ambiente 
     */
    public Obstaculo(int posX1, int posY1, int posX2, int posY2, TipoObstaculo tipoObstaculo, Ambiente ambiente){ //Constructor da classe Obstáculo
        this.posX1 = posX1;
        this.posY1 = posY1;
        this.posX2 = posX2;
        this.posY2 = posY2;
        this.tipoObstaculo = tipoObstaculo;
    }
    /**
     * Método para saber a posição X1 do obstáculo
     * @return posX1
     */
    public int getPosX1() { 
        return posX1;
    }
    /**
     * Método para saber a posição Y1 do obstáculo
     * @return posY1
     */
    public int getPosY1() { 
        return posY1;
    }
    /**
     * Método para saber a altura do obstáculo de acordo com o método do enum de TipoObstaculo
     * @return altura do obstáculo
     */
    public int getAltura() { 
        return this.getTipoObstaculo().getAltura();
    }
    /**
     * Método para saber a posição X2 do obstáculo
     * @return posX2
     */
    public int getPosX2() { 
        return posX2;
    }
    /**
     * Método para saber a posição Y2 do obstáculo
     * @return posY2
     */
    public int getPosY2() { 
        return posY2;
    }
    /**
     * Método para saber o tipo do obstáculo
     * @return tipoObstaculo
     */
    public TipoObstaculo getTipoObstaculo() { 
        return tipoObstaculo;
    }
}