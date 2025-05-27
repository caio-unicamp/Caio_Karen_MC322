import java.util.ArrayList;

/**
 * Representa o espaço 3D onde as entidades operam.
 * Gerencia uma lista polimórfica de entidades e um mapa de ocupação.
 */
public class Ambiente{
    private final String ambiente; //Nome do ambiente
    private final int tamX; //Tamanho em X do ambiente
    private final int tamY; //Tamanho em Y do ambiente
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
    /**
     * Método para saber o nome do ambiente que a simulação está sendo rodada
     * @return Nome do ambiente 
     */
    public String getNomeAmbiente(){  
        return this.ambiente;
    }
    /**
     * Método para saber os limites do ambiente
     * @return Uma lista de inteiros com tamanho 3, tendo no índice 0 a largura máxima (X), no índice 1 a profundidade máxima (Y) e no índice 2 a altura máxima (Z)
     */
    public int[] getLimites(){ 
        int [] limites = {this.tamX, this.tamY, this.tamZ};
        return limites;
    }
    /**
     * Analisa se as coordenadas passadas estão dentro dos limites do ambiente.
     * @param x Posição x
     * @param y Posição y
     * @param altura Altura
     * @return Um booleano sendo true caso as coordenadas estejam dentro do ambiente e false caso contrário 
     */
    public boolean dentroDosLimites(int x, int y, int altura){ 
        return (x >= 0 && x <= this.tamX && y >= 0 && y <= this.tamY && altura >= 0 && altura <= this.tamZ);
    }
    /**
     * Método para inicializar o mapa 3D do ambiente com todas as posições vazias
     */
    public void inicializarMapa(){
        for (int x = 0; x < this.tamX; x++){
            for (int y = 0; y < this.tamY; y++){
                for (int z  = 0; z < this.tamZ; z++){
                    mapa[x][y][z] = TipoEntidade.VAZIO;
                }
            }
        }
    }
    /**
     * Método para adicionar uma entidade na lista de entidades ativas
     * @param entidade a ser adicionada na lista
     */
    public void adicionarEntidade(Entidade entidade){ 
        listaEntidades.add(entidade);
    }
    /**
     * Método para remover uma entidade da lista de entidades ativas
     * @param entidade a ser removida da lista
     */
    public void removerEntidade(Entidade entidade){ 
        listaEntidades.remove(entidade);
    }
    /**
     * Confere a lista de entidades ativas no ambiente.
     * @return Uma lista de entidades ativas no ambiente, podendo ser robôs, obstáculos ou outros objetos que implementem a interface Entidade.
     */
    public ArrayList<Entidade> getListaEntidades(){ 
        return listaEntidades;
    }
    /**
     * Verifica se houve uma colisão entre uma entidade e o ambiente.
     * @param entidade a ser verificada
     * @param passoX
     * @param passoY
     * @throws ColisaoException
     */
    public void verificarColisoes(Entidade entidade, int passoX, int passoY) throws ColisaoException{
        if (!dentroDosLimites(entidade.getX() + passoX, entidade.getY() + passoY, entidade.getZ())){ //Verifica se a entidade está dentro dos limites do ambiente
            throw new ColisaoException("Houve uma colisão com os limites do ambiente ao tentar ");
        }
    }
    /**
     * Printa uma vizualização do mapa no plano XY dado um nível de altura.
     * @param altura do mapa a ser vizualizado
     */
    public void vizualizarMapa(int altura){
        for (int j = this.tamY-1; j >= 0; j--){
            for (int i = 0; i < this.tamX; i++){ //Segue a lista do mapa e printa o símbolo de cada entidade no nível de altura especificado
                System.out.print(mapa[i][j][altura].getSimbolo() + " ");
            }
            System.out.println();
        }
    }
}