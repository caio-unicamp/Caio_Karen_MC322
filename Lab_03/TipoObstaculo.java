public class TipoObstaculo {

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