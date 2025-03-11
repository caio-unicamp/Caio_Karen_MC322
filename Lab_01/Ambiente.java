public class Ambiente{
    public String ambiente;
    public int largura;
    public int altura;

    public Ambiente(String ambiente, int largura, int altura){
        this.ambiente = ambiente;
        this.largura = largura;
        this.altura = altura;
    }
    
    public boolean dentroDosLimites(int x, int y){
        if (x > 0 && x < largura && y > 0 && y < altura) {
            return true;
        }else{
            return false;
        }
    }
}