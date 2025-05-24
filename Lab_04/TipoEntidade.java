public enum TipoEntidade{
    VAZIO(false, ' '),
    ROBO(true, 'a'),
    OBSTACULO(false, '#'),
    DESCONHECIDO(false, '*');

    private final boolean movel; 
    private final char simbolo;

    TipoEntidade(boolean movel, char simbolo){
        this.movel = movel;
        this.simbolo = simbolo;
    }

    public boolean isMovel(){
        return this.movel;
    }

    public char getSimbolo(){
        return this.simbolo;
    }
}