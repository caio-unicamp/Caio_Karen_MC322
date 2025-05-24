public enum TipoEntidade{
    VAZIO(false, ' '),
    ROBO(true, 'a'),
    OBSTACULO(false, '#');

    private final boolean movel; 
    private final char simbolo;

    TipoEntidade(boolean movel, char simbolo){
        this.movel = movel;
        this.simbolo = simbolo;
    }

    public boolean isMovel(){
        return this.movel;
    }

    public char simbolo(){
        return this.simbolo;
    }
}