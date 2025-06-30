package robot.autonomos;
import ambiente.*;
import robot.*;
import excecoes.*;
import sensores.*;
import enums.*;
import interfaces.Comunicavel;
import interfaces.Entidade;
import robot.subsistemas.*;

public class NannyMcphee extends Autonomo implements Comunicavel {
    private int numeroDeBebesCuidados; // Número de bebês que a Nanny já cuidou
    private Robo bebe; // Representa o bebê que está sendo cuidado pela Nanny
    private ModuloComunicacao moduloComunicacao; // Módulo de comunicação para enviar e receber mensagens
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
        this.moduloComunicacao = new ModuloComunicacao(this); // Inicializa o módulo de comunicação do NannyMcphee
    }
    /**
     * Envia uma mensagem para outro robô
     * @param destinatario o robô que receberá a mensagem
     * @param mensagem a mensagem a ser enviada
     * @throws RoboDesligadoException se o robô estiver desligado
     * @throws ErroComunicacaoException se houver um erro na comunicação, como destinatário nulo ou não comunicável
     */
    @Override
    public void enviarMensagem(Entidade destinatario, String mensagem) throws RoboDesligadoException, ErroComunicacaoException {
        moduloComunicacao.enviarMensagem(destinatario, mensagem); // Delegar a responsabilidade de enviar a mensagem para o módulo de comunicação 
    }
    /**
     * Recebe mensagens enviadas por outros robôs comunicáveis
     * @param remetente o nome do robô que enviou a mensagem
     * @param mensagem a mensagem recebida
     * @throws RoboDesligadoException se o robô estiver desligado
     */
    @Override
    public void receberMensagem(String remetente, String mensagem) throws RoboDesligadoException {
        moduloComunicacao.receberMensagem(remetente, mensagem);
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
     * Faz com que a Nanny não precise mais cuidar do bebê.
     */
    public void demitir() {
        this.bebe = null; // Define o bebê como nulo, indicando que não está mais cuidando de nenhum
        this.missao = null; // Remove a missão associada, se houver
    }
    /**
     * Executa a missão de cuidar de outros robôs no ambiente.
     * @param ambiente
     * @throws SensorDesligadoException se o sensor estiver desligado
     */
    @Override
    public void executarMissao(Ambiente ambiente) throws SensorDesligadoException, RoboDesligadoException {
        if (this.getEstadoRobo() == EstadoRobo.DESLIGADO) {
            throw new RoboDesligadoException("Nannys também ficam de saco cheio, é melhor dar um tempo para a " + this.getNome() + " descansar antes de voltar ao serviço.");
        }
        for (Sensor<?> sensor :  this.getSensores()) {
            if (sensor.getBateria() == 0) {
                throw new SensorDesligadoException(sensor, this.getNome());
            }
        }
        if (temMissao()) {
            missao.executar(this, ambiente); // Executa a missão passando o próprio robô (a Nanny)
        }
    }
    /**
     * Verifica o número de bebês que foram cuidados pelo babysitter.
     * @return número de bebês cuidados
     */ 
    public int getNumeroDeBebesCuidados() {
        return this.numeroDeBebesCuidados;
    }
}
