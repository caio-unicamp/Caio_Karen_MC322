import java.util.ArrayList;

public class Ambiente{
    private final String ambiente; //nome do ambiente
    private final int tamX; //tamanho em X do ambiente
    private final int tamY; //tamanho em Y do ambiente
    private final int tamZ; //Tamanho em Z do ambiente no caso do robô aéreo
    private ArrayList<Entidade> listaEntidades; //Lista das entidades ativas presentes no ambiente 
    private TipoEntidade[][][] mapa;
    
    public Ambiente(String ambiente, int tamX, int tamY, int tamZ){ //Construtor para inicializar os atributos
        this.ambiente = ambiente;
        this.tamX = tamX;
        this.tamY = tamY;
        this.tamZ = tamZ;
        this.mapa = new TipoEntidade[this.tamX - 1][this.tamY - 1][this.tamZ - 1];
        listaEntidades = new ArrayList<>();
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

    private void inicializarMapa(){
        for (int x = 0; x < this.tamX; x++){
            for (int y = 0; y < this.tamY; y++){
                for (int z  = 0; z < this.tamZ; z++){
                    mapa[x][y][z] = TipoEntidade.VAZIO;
                }
            }
        }
    }
    
}