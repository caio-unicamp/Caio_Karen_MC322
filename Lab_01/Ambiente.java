public class Ambiente{
    private String ambiente;
    private int largura;
    private int altura;

    public Ambiente(String ambiente, int largura, int altura){
        this.ambiente = ambiente;
        this.largura = largura;
        this.altura = altura;
    }

    public String nomeAmbiente(){
        return this.ambiente;
    }
    
    public int[] getLimites(){
        int [] limites = {this.largura, this.altura};
        return limites;
    }

    public boolean dentroDosLimites(int x, int y){
        return (x >= 0 && x <= this.largura && y >= 0 && y <= this.altura);
    }
}