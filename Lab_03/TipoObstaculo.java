public enum TipoObstaculo{
    BURACO_SEM_FUNDO(-1, false),
    MINA_TERRESTRE(0, true),
    ARVORE(2, true),
    PORTAO(2, false);

    private final int altura;
    private final boolean bloqueiaPassagem;
    
    TipoObstaculo(int altura, boolean bloqueiaPassagem){
        this.altura = altura;
        this.bloqueiaPassagem = bloqueiaPassagem;
    }

    public int getAltura(){
        return altura;
    }

    public boolean isBloqueiaPassagem(){
        return bloqueiaPassagem;
    }
}

