package robots.autonomos;
import ambiente.*;
import robots.*;
import excecoes.*;
import sensores.*;

public class NannyMcphee extends AgenteInteligente {
    private int numeroDeBebesCuidados; // Número de bebês que a Nanny já cuidou
    private Robo bebe; // Representa o bebê que está sendo cuidado pela Nanny

    /**
     * Construtor da classe Babysitter.
     * Inicializa o babysitter com um nome, direção, coordenadas e número de bebês que está cuidando.
     * @param nome representando seu nome
     * @param direcao representando a direção do robô
     * @param x coordenada x no ambiente
     * @param y coordenada y no ambiente
     */
    public NannyMcphee(String nome, String direcao, int x, int y, int z) {
        super(nome, direcao, x, y, z);
        this.bebe = null; // Inicializa o bebê como nulo, pois ainda não foi atribuído
        this.numeroDeBebesCuidados = 0; // Inicializa o número de bebês que foram cuidados
    }
    /**
     * Define o bebê que a Nanny está cuidando.
     * @param bebe o robô bebê que está sendo cuidado
     */
    public void setBebe(Robo bebe) {
        this.bebe = bebe;
    }
    /**
     * Retorna o bebê que a Nanny está cuidando.
     * @return o robô bebê que está sendo cuidado
     */
    public Robo getBebe() {
        return this.bebe;
    }
    /**
     * Verifica se a Nanny está cuidando de um bebê.
     * @return true se está cuidando de um bebê, false caso contrário
     */
    public boolean estaCuidandoDeBebe() {
        return this.bebe != null;
    }
    /**
     * Faz com que a Nanny não precise mais cuidar do bebê.
     */
    public void demitir() {
        this.bebe = null; // Define o bebê como nulo, indicando que não está mais cuidando de nenhum
    }
    /**
     * Executa a tarefa de cuidar de outros robôs no ambiente.
     * @param ambiente
     * @throws SensorDesligadoException se o sensor estiver desligado
     */
    @Override
    public void executarMissao(Ambiente ambiente) throws SensorDesligadoException {
        for (Sensor<?> sensor :  this.getSensores()) {
            if (sensor.getBateria() == 0) {
                throw new SensorDesligadoException(sensor, this.getNome());
            }
        }
        missao.executar(bebe, ambiente);
        this.numeroDeBebesCuidados++;
    }
    /**
     * Verifica o número de bebês que foram cuidados pelo babysitter.
     * @return número de bebês cuidados
     */ 
    public int getNumeroDeBebesCuidados() {
        return this.numeroDeBebesCuidados;
    }
}
