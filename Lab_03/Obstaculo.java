public class Obstaculo{
    int posX1;
    int posY1;
    int altura;
    int posX2;
    int posY2;
    TipoObstaculo tipoObstaculo;
    Ambiente ambiente;
    
    public Obstaculo(){
        
    }
    
    public enum TipoObstaculo{
        MINA_TERRESTRE(0, false),
        CAIXA(1, false),
        PASSARO(2, true),
        ;

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