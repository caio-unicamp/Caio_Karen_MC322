package robot;

import interfaces.*;
import robot.subsistemas.ControleMovimento;
import robot.subsistemas.GerenciadorSensores;
import sensores.*;
import enums.*;
import ambiente.*;
import excecoes.*;

import java.util.ArrayList;

public abstract class Robo implements Entidade, Sensoreavel{
    private String idRobo;    //id do robô que será o nome durante sua criação
    private String direcao;   //direção do robô
    private EstadoRobo estado; //Estado do robô, que pode ser LIGADO ou DESLIGADO 
    private TipoEntidade tipoEntidade; //Tipo da entidade, que nesse caso é um robô
    private int posicaoX;   //coordenada X no Ambiente
    private int posicaoY;   //coordenada Y no Ambiente
    private int posicaoZ;   //coordenada Z no Ambiente no caso de robôs aéreos
    private ArrayList<Sensor<?>> listaSensores; //Lista de sensores do robô
    protected GerenciadorSensores gerenciadorSensores; //Gerenciador de sensores do robô
    private ControleMovimento controleMovimento; //Controle de movimento do robô
    
    public Robo (String idRobo, String direcaoRobo, int x, int y, int z) { //Construtor para inicializar os atributos do robô aéreo;
        this.idRobo = idRobo;
        this.direcao = direcaoRobo;
        this.posicaoX = x;
        this.posicaoY = y;
        this.posicaoZ = z;
        this.listaSensores = new ArrayList<>();
        this.estado = EstadoRobo.LIGADO; //Por padrão o robô está ligado
        this.tipoEntidade = TipoEntidade.ROBO; //Define o tipo da entidade como robô
        gerenciadorSensores = new GerenciadorSensores(this); //Inicializa o gerenciador de sensores do robô
        controleMovimento = new ControleMovimento(this); //Inicializa o controle de movimento do robô
    }
    /**
     * Método para mover o robô no ambiente, de modo que ele anda primeiro no eixo X e depois no eixo Y.
     * @param deltaX
     * @param deltaY
     * @param ambiente
     * @throws SensorDesligadoException
     * @throws RoboDesligadoException
     * @throws ColisaoException
     * @throws ErroComunicacaoException 
     */
    public void mover(int deltaX, int deltaY, Ambiente ambiente) throws SensorDesligadoException, RoboDesligadoException, ColisaoException, ErroComunicacaoException {
        controleMovimento.mover(deltaX, deltaY, ambiente); 
    }
    /**
     * Método que aciona os sensores do robô, verificando se o robô está ligado e se os sensores estão com bateria.
     * @throws SensorDesligadoException
     * @throws RoboDesligadoException
     */
    public void acionarSensores() throws SensorDesligadoException, RoboDesligadoException {
        gerenciadorSensores.acionarSensores();
    }
    /**
     * Método para saber o nome do robô, o qual nesse caso irá funcionar como um id por ser único para cada robô no ambiente.
     * @return O nome do robô.
    */
    public String getNome(){ 
        return this.idRobo;
    }
    /** 
     * Método para saber a posição X do robô no ambiente.
     * @return A coordenada X do robô.
    */
    public int getX(){
        return this.posicaoX;
    }
    /**
     * Método para saber a posição Y do robô no ambiente.
     * @return A coordenada Y do robô.
    */
    public int getY(){
        return this.posicaoY;
    }
    /**
     * Método para saber a posição Z do robô no ambiente.
     * @return A coordenada Z do robô, que é a altura no caso de robôs aéreos.
     */
    public int getZ(){
        return this.posicaoZ;
    }
    /**
     * Método que da as coordenadas do robô no ambiente em forma de lista.
     * @return Uma lista de inteiros com tamanho 3, onde o índice 0 é a coordenada X, o índice 1 é a coordenada Y e o índice 2 é a coordenada Z.
     */
    public int[] getPosicao(){
        int[] posicao = {this.getX(), this.getY(), this.getZ()};
        return posicao;
    }
    /**
     * Método para saber a direção que o robô está encarando.
     * @return Norte, Sul, Leste ou Oeste.
     */
    public String getDirecao(){ 
        return direcao;
    }
    /**
     * Altera a direção que o robô está encarando
     * @param direcao
     */
    public void setDirecao(String direcao){ 
        this.direcao = direcao;
    }
    /**
     * Seta a posição do robô.
     * @param x
     * @param y
     * @param z
     */
    public void setPosicao(int x, int y, int z){ 
        this.posicaoX = x;
        this.posicaoY = y;
        this.posicaoZ = z;
    }
    /**
     * Analisa o passo do robô em X e Y podendo ser +1 ou -1 dependendo da direção que ele está indo.
     * @param deltaX
     * @param deltaY
     * @return Um array de inteiros com tamanho 2, onde o índice 0 é o passo em X e o índice 1 é o passo em Y.
     */
    public int[] getPasso(int deltaX, int deltaY){ 
        int passoX = 0, passoY = 0;
        if (deltaX != 0){
            passoX = deltaX/Math.abs(deltaX);
        }
        if (deltaY != 0){
            passoY = deltaY/Math.abs(deltaY);
        }
        int[] listaPasso = {passoX, passoY};
        return listaPasso;
    }

    public void adicionarSensores(Sensor<?> sensor){ //Adiciona um sensor na lista de sensores do robô
        gerenciadorSensores.adicionarSensor(sensor);
    }

    public void removerSensor(Sensor<?> sensor){ //Remove um sensor na lista de sensores do robô
        gerenciadorSensores.removerSensores(sensor);
    }

    public ArrayList<Sensor<?>> getSensores(){ //Acessa quais são os sensores que esse robô tem
        return listaSensores;
    }
    
    public SensorProximidade getSensorProximidade(){ //Função para retornar o sensor de proximidade do robô
        return gerenciadorSensores.getSensorProximidade();
    }
    /**
     * Identificação de qual obstáculo o robô está detectando.
     * @param x
     * @param y
     * @param ambiente
     * @return O obstáculo identificado, caso exista, ou null caso não exista nenhum obstáculo na posição informada.
     */
    public Obstaculo getObstaculoIdentificado(int x, int y, Ambiente ambiente){
        Obstaculo obstaculoIdentificado = null;
            for (Entidade entidade : ambiente.getListaEntidades()) {
                if (!(entidade instanceof Obstaculo)) { //Verifica se a entidade é um obstáculo
                    continue; //Se não for, pula para a próxima iteração
                }else{
                    Obstaculo obstaculo = (Obstaculo) entidade; //Faz o cast para Obstaculo
                    if (obstaculo.getX() <= x && obstaculo.getPosX2() >= x && obstaculo.getY() <= y && obstaculo.getPosY2() >= y && obstaculo.getZ() >= this.getPosicao()[2]) {
                        obstaculoIdentificado = obstaculo;
                        break;
                    }
                }  
            }
        return obstaculoIdentificado;
    }
    /**
     * Verifica se o robô parou em um obstáculo.
     * @param obstaculoIdentificado
     * @return true se o robô parou em um obstáculo, false caso contrário.
     */
    public boolean roboParouNoObstaculo(Obstaculo obstaculoIdentificado){ 
        if (obstaculoIdentificado != null){
            if (!obstaculoIdentificado.getTipoObstaculo().equals(TipoObstaculo.PORTAO)){ //Se o obstáculo identificado for qualquer um deles, exceto o portao, ele para
                return true;
            }
        }
        return false; //Se não parou por conta de nenhum obstáculo, retorna false
    }
    /**
     * Método que define as interações padrões dos robôs com os obstáculos.
     * @param ambiente
     * @param obstaculoIdentificado
     */
    public void interacaoRoboObstaculo(Ambiente ambiente, Obstaculo obstaculoIdentificado){
        if (obstaculoIdentificado.getTipoObstaculo().equals(TipoObstaculo.MINA_TERRESTRE)){ //Se ele identifica uma mina terrestre ele para
            if (this.getSensorProximidade().getBateria() == 0){ //Se a bateria dele tiver acabado, ele não consegue identificar a mina terrestre e irá explodir
                ambiente.removerEntidade(this); //Remove o robô explodido
                ambiente.removerEntidade(obstaculoIdentificado); //Remove a mina terrestre explodida
            }
            return;
        }else if (obstaculoIdentificado.getTipoObstaculo().equals(TipoObstaculo.BURACO_SEM_FUNDO)){ //Se ele identifica um buraco sem fundo ele para
            if (this.getSensorProximidade().getBateria() == 0){ //Se a bateria dele tiver acabado, ele não consegue identificar o buraco e irá cair
                ambiente.removerEntidade(this); //Remove o robô que caiu no buraco
            }
            return;
        }
    }
    /**
     * Método para saber o tipo dessa entidade.
     * @return ROBO
     */
    public TipoEntidade getTipoEntidade(){
        return this.tipoEntidade;
    }
    /**
     * Método para saber a representação do Robô no mapa.
     * @return '*'
     */
    public char getSimbolo(){
        return this.tipoEntidade.getSimbolo();
    }
    /**
     * Explicação do que essa Entidade é no mapa.
     * @return Uma descrição da entidade robô.
     */
    public String getDescricao(){ 
        return "Os robôs são uma raça superior aos seres humanos que... o que? Eu não posso falar isso? Paia. Tá enfim, os robôs são usados para testar a simulação em que estamos. Além de testar a qualidade do nosso programa eles são bem divertidos vai. Dentre os robôs que temos no modelo atual do nosso simulador temos:\nAspiradores\nDrones\nPassáros\nRovers\nCada um tem seu jeitinho próprio e diferente de ser. Vamos nos divertir bastante com todos eles! HEHEHEHE!";
    }
    public void ligar(){ //Função que liga o robô
        this.estado = EstadoRobo.LIGADO;
    }
    public void desligar(){ //Função que desliga o robô
        this.estado = EstadoRobo.DESLIGADO;
    }
    /**
     * Método para saber se o robô está ligado ou desligado.
     * @return O estado do robô
     */
    public EstadoRobo getEstadoRobo(){ 
        return this.estado; 
    }
}