public class Obstaculo{
    int posX1;
    int posY1;
    int altura;
    int posX2;
    int posY2;
    TipoObstaculo tipoObstaculo;
    Ambiente ambiente;
    
    public Obstaculo(int posX1, int posY1, int altura, int posX2, int posY2, TipoObstaculo tipoObstaculo, Ambiente ambiente){
        this.posX1 = posX1;
        this.posY1 = posY1;
        this.altura = altura;
        this.posX2 = posX2;
        this.posY2 = posY2;
        this.tipoObstaculo = tipoObstaculo;
        this.ambiente = ambiente;
    }
    
    public enum TipoObstaculo{
        MINA_TERRESTRE(0, false),
        BURACO_SEM_FUNDO(0, false),
        CAIXA(1, false),
        PASSARO(2, true),
        MURALHA(3, false);

        private final int altura;
        private final boolean obstaculoAereo;
        
        TipoObstaculo(int altura, boolean obstaculoAereo){
            this.altura = altura;
            this.obstaculoAereo = obstaculoAereo;
        }

        public int getAltura(){
            return altura;
        }

        public boolean isObstaculoAereo(){
            return obstaculoAereo;
        }
    }
    
}