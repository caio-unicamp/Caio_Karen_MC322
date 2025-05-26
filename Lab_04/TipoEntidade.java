public enum TipoEntidade{
    VAZIO(false, ' '),
    ROBO(true, '*'),
    OBSTACULO(false, '#'),
    DESCONHECIDO(false, '?');

    private final boolean movel; 
    private final char simbolo;

    TipoEntidade(boolean movel, char simbolo){
        this.movel = movel;
        this.simbolo = simbolo;
    }
    /**
     * Método para saber se a entidade é móvel.
     * @return true se for móvel, false caso contrário
     */
    public boolean isMovel(){
        return this.movel;
    }
    /**
     * Método para saber a representação dessa entidade no mapa.
     */
    public char getSimbolo(){
        return this.simbolo;
    }
}