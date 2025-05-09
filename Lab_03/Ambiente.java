import java.util.ArrayList;

public class Ambiente{
    private final String ambiente; //nome do ambiente
    private final int tamX; //tamanho em X do ambiente
    private final int tamY; //tamanho em Y do ambiente
    private final int tamZ; //Tamanho em Z do ambiente no caso do robô aéreo
    private ArrayList<Robo> listaRobosAtivos;   //Lista de robôs ativos no ambiente
    private ArrayList<Obstaculo> listaObstaculos; //Lista de obstáculos ativos no ambiente

    public Ambiente(String ambiente, int tamX, int tamY, int tamZ){ //Construtor para inicializar os atributos
        this.ambiente = ambiente;
        this.tamX = tamX;
        this.tamY = tamY;
        this.tamZ = tamZ;
        listaRobosAtivos = new ArrayList<>();
        listaObstaculos = new ArrayList<>();
    }
    public String getNomeAmbiente(){ //retorna o nome do ambiente 
        return this.ambiente;
    }
    
    public int[] getLimites(){ //Retorna os limites do ambiente
        int [] limites = {this.tamX, this.tamY, this.tamZ};
        return limites;
    }

    public boolean dentroDosLimites(int x, int y, int altura){ //Retorna um booleano para analisar se o robô está dentro dos limites
        return (x >= 0 && x <= this.tamX && y >= 0 && y <= this.tamY && altura >= 0 && altura <= this.tamZ);
    }

    public void adicionarRobo(Robo robo){ //Adiciona um robô na lista de robôs ativos 
        listaRobosAtivos.add(robo);
    }

    public void removerRobo(Robo robo){ //Remove um robo na lista de robos ativos
        listaRobosAtivos.remove(robo);
    }

    public ArrayList<Robo> getListaRobos(){ //Retorna a lista de Robôs ativos no ambiente
        return listaRobosAtivos;
    }

    public void adicionarObstaculo(Obstaculo obstaculo){ //Adiciona um obstáculo no ambiente
        listaObstaculos.add(obstaculo);
    }

    public void removerObstaculo(Obstaculo obstaculo){ //Remove um obstáculo no ambiente
        listaObstaculos.remove(obstaculo);
    }
    
    public ArrayList<Obstaculo> getListaObstaculos(){ //Retorna a lista de obstáculos ativos no ambiente
        return listaObstaculos;
    }
    
}