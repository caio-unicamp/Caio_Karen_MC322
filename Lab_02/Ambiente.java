import java.util.ArrayList;

public class Ambiente{
    private String ambiente; //nome do ambiente
    private int largura; //largura do ambiente
    private int altura; //altura do ambiente
    private ArrayList<Robo> listaRobosAtivos;

    public Ambiente(String ambiente, int largura, int altura){ //Construtor para inicializar os atributos
        this.ambiente = ambiente;
        this.largura = largura;
        this.altura = altura;
        listaRobosAtivos = new ArrayList<>();
    }

    public String getNomeAmbiente(){ //retorna o nome do ambiente 
        return this.ambiente;
    }
    
    public int[] getLimites(){ //Retorna os limites do ambiente
        int [] limites = {this.largura, this.altura};
        return limites;
    }

    public boolean dentroDosLimites(int x, int y){ //Retorna um booleano para analisar se o robô está dentro dos limites
        return (x >= 0 && x <= this.largura && y >= 0 && y <= this.altura);
    }

    public void adicionarRobo(){

    }
}