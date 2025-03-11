public class Ambiente{
    
    public int largura_ambiente;
    public int altura_ambiente;

    public Ambiente(int largura, int altura){
        largura_ambiente = largura;
        altura_ambiente = altura;
    }

    public boolean dentroDosLimites(int x, int y){
        if (x > 0 && x < largura_ambiente && y > 0 && y < altura_ambiente) {
            return true;
        }else{
            return false;
        }
    }
}