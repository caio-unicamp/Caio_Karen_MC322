import java.util.ArrayList;

public class Ambiente{
    private String ambiente; //nome do ambiente
    private int tamX; //tamanho em X do ambiente
    private int tamY; //tamanho em Y do ambiente
    private int tamZ; //Tamanho em Z do ambiente no caso do robô aéreo
    private int altura; //Altura máxima do ambiente
    private ArrayList<Robo> listaRobosAtivos;   //Lista de robôs ativos no ambiente

    public Ambiente(String ambiente, int tamX, int tamY){ //Construtor para inicializar os atributos
        this.ambiente = ambiente;
        this.tamX = tamX;
        this.tamY = tamY;
        listaRobosAtivos = new ArrayList<>();   //Adicionar uma lista de robôs ativos
    }

    public Ambiente(String ambiente, int tamX, int tamY, int tamZ){ //Construtor para inicializar os atributos
        this.ambiente = ambiente;
        this.tamX = tamX;
        this.tamY = tamY;
        this.tamZ = tamZ;
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

    public ArrayList<Robo> getLista(){ //Retorna a lista de Robôs ativos no ambiente
        return listaRobosAtivos;
    }
}