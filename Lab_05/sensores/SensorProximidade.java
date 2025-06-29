package sensores;
import robot.*;
import ambiente.*;
import interfaces.Entidade;
import enums.TipoEntidade;

public class SensorProximidade extends Sensor<Boolean>{
    private TipoEntidade ultimoTipoDetectado; //Atributo para tornar mais fácil que tipo de entidade o sensor detecta
    
    public SensorProximidade(double raio, String nomeSensor){
        super(raio, nomeSensor);
        this.ultimoTipoDetectado = TipoEntidade.VAZIO;
    }
    /**
     * O monitoramento do sensor de proximidade funciona verificando por robôs e obstáculos próximos.
     * @param atributo lista de objetos diversos
     * @implNote atributo[0] e atributo[1] são as coordenadas x e y que o robô tentará ir
     * @implNote atributo[2] é a altura do robô 
     * @implNote atributo[3] é o ambiente
     * @implNote atributo[4] é o robô que está usando esse sensor
     * @return true se o sensor identificar um obstáculo ou um robô e false caso contrário
     */
    @Override
    public Boolean monitorar(Object... atributo){ //Método que verifica se o robô irá colidir com algum obstáculo ou robô
        this.consumirBateria(5); // Consome 5% da bateria a cada monitoramento
        this.ultimoTipoDetectado = TipoEntidade.VAZIO; //Reseta a detecção
        //Lógica para definir a direção
        if (((Robo) atributo[4]).getPasso((int) atributo[0], (int) atributo[1])[0] > 0){ //Se o passo for positivo ele anda para o leste
            ((Robo) atributo[4]).setDirecao("leste");
        }else if (((Robo) atributo[4]).getPasso((int) atributo[0], (int) atributo[1])[0] < 0){ //Se o passo for negativo ele anda para o oeste
            ((Robo) atributo[4]).setDirecao("oeste");
        }else if (((Robo) atributo[4]).getPasso((int) atributo[0], (int) atributo[1])[1] > 0){ //Se o passo for positivo ele anda para o leste
            ((Robo) atributo[4]).setDirecao("norte");
        }else if (((Robo) atributo[4]).getPasso((int) atributo[0], (int) atributo[1])[1] < 0){ //Se o passo for negativo ele anda para o oeste
            ((Robo) atributo[4]).setDirecao("sul");
        }
        //Deixando claro como funcionam os atributos
        int x = (int) atributo[0];
        int y = (int) atributo[1];
        int z = (int) atributo[2];
        Ambiente ambiente = (Ambiente) atributo[3];
        Robo roboProprio = (Robo) atributo[4];
        
        // Identifica se achou um robô e altera a variável do tipo da entidade
        if (identificarRobo(x, y, z, ambiente, roboProprio)) {
            this.ultimoTipoDetectado = TipoEntidade.ROBO;
            return true;
        }
        // Identifica se achou um obstáculo e altera a variável do tipo da entidade
        if (identificarObstaculo(x, y, z, ambiente)) {
            this.ultimoTipoDetectado = TipoEntidade.OBSTACULO;
            return true;
        }
        return false; // Retorna false se nada foi encontrado
    }
    
    public boolean identificarRobo(int x, int y, int z, Ambiente ambiente, Robo roboProprio){
        for (Entidade entidade: ambiente.getListaEntidades()){ //Para cada Robô na lista de robôs (obviamente não sendo o robô que está tentando identificar um obstáculo), ele analisa se a posição que o robô em questão quer ir já está ocupada 
            if (!(entidade instanceof Robo)){ //Verifica se a entidade é um robô
                continue; //Se não for, pula para a próxima interação
            }else{
                Robo robo = (Robo) entidade; //Fazo cast para Robô
                if (robo.getPosicao()[0] == x && robo.getPosicao()[1] == y && robo.getPosicao()[2] == z && !robo.equals(roboProprio)){ //Se a posição já estiver ocupada por outro robô, ele retorna true
                    return true;
                }
            }
        }return false;
    }
    
    public boolean identificarObstaculo(int x, int y, int z, Ambiente ambiente){ //Identifica se o robô está colidindo com algum obstáculo
        for (Entidade entidade: ambiente.getListaEntidades()){ //Para cada obstáculo na lista de obstáculos, ele analisa se a posição que o robô em questão quer ir já está ocupada 
            if (!(entidade instanceof Obstaculo)){ //Verifica se a entidade é um obstáculo
                continue; //Se não for, pula para a próxima interação
            }else{
                Obstaculo obstaculo = (Obstaculo) entidade;
                if (obstaculo.getX() >= x && obstaculo.getPosX2() <= x && obstaculo.getY() >= y && obstaculo.getPosY2() <= y && obstaculo.getZ() == z){ //Se a posição já estiver ocupada por um obstáculo, ele retorna true
                    return true;
                }
            }
        }return false;
    }
    /**
     * Verifica qual entidade esse sensor detectou
     * @return tipo da entidade detectada (Robo ou Obstáculo)
     */
    public TipoEntidade getUltimoTipoDetectado(){
        return this.ultimoTipoDetectado;
    }
}