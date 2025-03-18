import java.util.ArrayList;

public class Ambiente{
    private String ambiente; //nome do ambiente
    private int tamX; //tamanho em X do ambiente
    private int tamY; //tamanho em Y do ambiente
    private int altura; //Altura máxima do ambiente
    private ArrayList<Robo> listaRobosAtivos;

    public Ambiente(String ambiente, int tamX, int tamY){ //Construtor para inicializar os atributos
        this.ambiente = ambiente;
        this.tamX = tamX;
        this.tamY = tamY;
        listaRobosAtivos = new ArrayList<>();
    }

    public String getNomeAmbiente(){ //retorna o nome do ambiente 
        return this.ambiente;
    }
    
    public int[] getLimites(){ //Retorna os limites do ambiente
        int [] limites = {this.tamX, this.tamY};
        return limites;
    }

    public boolean dentroDosLimites(int x, int y, int altura){ //Retorna um booleano para analisar se o robô está dentro dos limites
        return (x >= 0 && x <= this.tamX && y >= 0 && y <= this.tamY && altura >= 0 && altura <= this.altura);
    }

    public void adicionarRobo(Robo robo){ //Adiciona os robos ativos na lista 
        listaRobosAtivos.add(robo);
    }
}