package ambiente;
import java.util.ArrayList;
import interfaces.*;
import enums.TipoEntidade;
import excecoes.*;
/**
 * Representa o espaço 3D onde as entidades operam.
 * Gerencia uma lista polimórfica de entidades e um mapa de ocupação.
 */
public class Ambiente{
    private final String ambiente; //Nome do ambiente
    private final int tamX; //Tamanho em X do ambiente
    private final int tamY; //Tamanho em Y do ambiente
    private final int tamZ; //Tamanho em Z do ambiente no caso do robô aéreo
    private int numRobosAmbiente; //Número de robos que existem no ambiente
    private int numObstaculosAmbiente; //Número de obstáculos que existem no ambiente
    private ArrayList<Entidade> listaEntidades; //Lista das entidades ativas presentes no ambiente 
    private TipoEntidade[][][] mapa;
    
    public Ambiente(String ambiente, int tamX, int tamY, int tamZ){ //Construtor para inicializar os atributos
        this.ambiente = ambiente;
        this.tamX = tamX;
        this.tamY = tamY;
        this.tamZ = tamZ;
        this.numRobosAmbiente = 0; // Inicia com 0 robôs no ambiente
        this.numObstaculosAmbiente = 0; // Inicia com 0 obstáculos no ambiente
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
     * Verifica quantos robôs existem no ambiente
     * @return número de robôs no ambiente
     */
    public int getNumRobosAmbiente(){   
        return numRobosAmbiente;
    }
    /**
     * Verifica quantos obstáculos existem no ambiente
     * @return número de obstáculos no ambiente
     */
    public int getNumObstaculosAmbiente(){
        return numObstaculosAmbiente;
    }
    /**
     * Analisa se as coordenadas passadas estão dentro dos limites do ambiente.
     * @param x Posição x
     * @param y Posição y
     * @param altura Altura
     * @return Um booleano sendo true caso as coordenadas estejam dentro do ambiente e false caso contrário 
     * @throws ColisaoException
     */
    public boolean dentroDosLimites(int x, int y, int altura) throws ColisaoException{ 
        if (x >= 0 && x <= this.tamX && y >= 0 && y <= this.tamY && altura >= 0 && altura <= this.tamZ){
            return true;
        }else{
            throw new ColisaoException("Posição fora dos limites do ambiente");
        }
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
        if (entidade.getTipoEntidade().equals(TipoEntidade.ROBO)){ //Se for um robô, aumenta o número de robôs que existem no ambiente
            this.numRobosAmbiente++;
            this.mapa[entidade.getX()][entidade.getY()][entidade.getZ()] = TipoEntidade.ROBO;
        }else if (entidade.getTipoEntidade().equals(TipoEntidade.OBSTACULO)){ //Se for um obstáculo, aumenta o número de obstáculos que existem no ambiente
            this.numObstaculosAmbiente++;
            Obstaculo obstaculoRemovido = (Obstaculo) entidade;
            for (int i = obstaculoRemovido.getX(); i <= obstaculoRemovido.getPosX2(); i++){
                for (int j = obstaculoRemovido.getY(); j <= obstaculoRemovido.getPosY2(); j++){
                    for (int k = 0; k <= obstaculoRemovido.getZ(); k++){
                        this.mapa[i][j][k] = TipoEntidade.OBSTACULO;
                    }
                }
            }
        }
        listaEntidades.add(entidade);
    }
    /**
     * Método para remover uma entidade da lista de entidades ativas
     * @param entidade a ser removida da lista
     */
    public void removerEntidade(Entidade entidade){ 
        if (entidade.getTipoEntidade().equals(TipoEntidade.ROBO)){ //Se for um robô diminui o número de robôs que existem no ambiente
            this.numRobosAmbiente--;
            this.mapa[entidade.getX()][entidade.getY()][entidade.getZ()] = TipoEntidade.VAZIO;
        }else if (entidade.getTipoEntidade().equals(TipoEntidade.OBSTACULO)){ //Se for um obstáculo diminui o número de obstáculos que existem no ambiente
            this.numObstaculosAmbiente--;
            Obstaculo obstaculoRemovido = (Obstaculo) entidade;
            for (int i = obstaculoRemovido.getX(); i <= obstaculoRemovido.getPosX2(); i++){
                for (int j = obstaculoRemovido.getY(); j <= obstaculoRemovido.getPosY2(); j++){
                    for (int k = 0; k <= obstaculoRemovido.getZ(); k++){
                        this.mapa[i][j][k] = TipoEntidade.VAZIO;
                    }
                }
            }
        }
        listaEntidades.remove(entidade);
    }
    /**
     * Verifica se existe uma entidade ocupando uma posição específica no ambiente.
     * @param x
     * @param y
     * @param z
     * @return true se existe uma entidade nas coordenadas dadas e false caso contrário.
     */
    public boolean estaOcupado(int x, int y, int z){
        for (Entidade entidade : this.listaEntidades) {
            if (entidade.getX() == x && entidade.getY() == y && entidade.getZ() == z){
                return true;
            }
        }
        return false;
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
     * @return true se houve colisão com o ambiente e false caso contrário
     * @throws ColisaoException
     */
    public boolean verificarColisoes(Entidade entidade, int passoX, int passoY) throws ColisaoException{
        if (dentroDosLimites(entidade.getX() + passoX, entidade.getY() + passoY, entidade.getZ())){ //Verifica se a entidade está dentro dos limites do ambiente
            return false;
        }
        return true;
    }
    /**
     * Salva os símbolos correspondentes ao x e y em uma altura específica para printar na main
     * @param altura em que pede-se a vizualização
     * @return uma matriz referente aos símbolos das entidades em suas respectivas posições no ambiente
     */
    public char[][] vizualizarMapa(int altura){
        char [][] vizuMapa = new char[this.tamX - 1][this.tamY - 1];
        for (int j = this.tamY-1; j >= 0; j--){
            for (int i = 0; i < this.tamX; i++){ //Segue a lista do mapa e adciona o símbolo de cada entidade no nível de altura especificado
                vizuMapa[i][j] = (this.mapa[i][j][altura].getSimbolo());
            }
        }
        return vizuMapa;
    }
    /**
     * Muda a posição da entidade no mapa do ambiente
     * @param entidade
     * @param novoX
     * @param novoY
     * @param novoZ
     */
    public void moverEntidade(Entidade entidade, int novoX, int novoY, int novoZ){
        this.mapa[entidade.getX()][entidade.getY()][entidade.getZ()] = TipoEntidade.VAZIO;
        entidade.setPosicao(novoX, novoY, novoZ);
        this.mapa[novoX][novoY][novoZ] = entidade.getTipoEntidade();
    }
}