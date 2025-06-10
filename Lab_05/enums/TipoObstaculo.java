package enums;
public enum TipoObstaculo{
    BURACO_SEM_FUNDO(0, false, "Cratera"),
    MINA_TERRESTRE(0, true, "KABUM!"),
    ARVORE(2, true, "Pau Brasil"),
    PORTAO(2, false, "Garagem");

    private final int altura;
    private final boolean bloqueiaPassagem;
    private final String nomeObstaculo;
    
    TipoObstaculo(int altura, boolean bloqueiaPassagem, String nomeObstaculo){
        this.altura = altura;
        this.bloqueiaPassagem = bloqueiaPassagem;
        this.nomeObstaculo = nomeObstaculo; 
    }
    /**
     * Confere a altura de um obstáculo.
     * @return altura do Obstáculo
     */
    public int getAltura(){
        return altura;
    }
    /**
     * Confere se o Obstáculo bloqueia a passagem de robôs.
     * @return true caso o Obstáculo bloqueie a passagem e false caso contrário
     */
    public boolean isBloqueiaPassagem(){
        return bloqueiaPassagem;
    }
    /**
     * Verifica o nome do Obstáculo
     * @return nome do Obstáculo.
     */
    public String getNome(){
        return nomeObstaculo;
    }
}


