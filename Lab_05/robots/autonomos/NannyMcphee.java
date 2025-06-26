package robots.autonomos;
import ambiente.*;
import robots.*;
import excecoes.*;
import sensores.*;
import enums.*;
import interfaces.Comunicavel;
import interfaces.Entidade;

public class NannyMcphee extends Autonomo implements Comunicavel {
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
     * Envia uma mensagem para outro robô
     * @param destinatario o robô que receberá a mensagem
     * @param mensagem a mensagem a ser enviada
     * @throws RoboDesligadoException se o robô estiver desligado
     * @throws ErroComunicacaoException se houver um erro na comunicação, como destinatário nulo ou não comunicável
     */
    @Override
    public void enviarMensagem(Entidade destinatario, String mensagem) throws RoboDesligadoException, ErroComunicacaoException {
        if (this.getEstadoRobo() == EstadoRobo.DESLIGADO) { //Checa se a nanny está ligada
            throw new RoboDesligadoException(this.getNome());
        }

        if (destinatario == null) { //Verifica se o destinatário existe
            throw new ErroComunicacaoException("Destinatário da mensagem não pode ser nulo.");
        }else if (!(destinatario instanceof Comunicavel)){ //Verifica se o destinatário é comunicável
            throw new ErroComunicacaoException("É muito difícil falar com crianças que não querem ouvir... não queria estar na sua situação"); //Se não for comunicável, lança uma exceção
        }

        if (destinatario instanceof Robo && ((Robo) destinatario).getEstadoRobo() == EstadoRobo.DESLIGADO) { //Checa se quem receberá a mensagem está ligado
            CentralComunicacao.getInstancia().registrarMensagem(this.getNome(), ((Robo) destinatario).getNome(), "[TENTATIVA FALHA - DESTINATÁRIO DESLIGADO] " + mensagem); //Registra na central que não foi possível enviar a mensagem pois o destinatário estava desligado
            throw new ErroComunicacaoException("O bebê " + ((Robo) destinatario).getNome() + " está dormindo no momento, melhor não acordar ele se não pode ficar de mal-humor.");
        }
        Comunicavel receptor = (Comunicavel) destinatario; //Faz o cast para comunicável
        // Faz o destinatário receber a mensagem (caso ele esteja ligado)
        receptor.receberMensagem(this.getNome(), mensagem); 
        // Se o destinatário puder ser instanciado como um robô, busca seu nome, se não, trata como desconhecido
        String nomeDestinatario = (receptor instanceof Robo) ? ((Robo) receptor).getNome() : "Desconhecido"; 
        // Registra que a mensagem foi enviada.
        CentralComunicacao.getInstancia().registrarMensagem(this.getNome(), nomeDestinatario, mensagem); 
    }
    /**
     * Recebe mensagens enviadas por outros robôs comunicáveis
     * @param remetente o nome do robô que enviou a mensagem
     * @param mensagem a mensagem recebida
     * @throws RoboDesligadoException se o robô estiver desligado
     */
    @Override
    public void receberMensagem(String remetente, String mensagem) throws RoboDesligadoException {
        if (this.getEstadoRobo() == EstadoRobo.DESLIGADO) { 
            //Mesmo que após a conferência de envio tiver passado, se o robô destinatário não conseguir receber a mensagem, registra que a mensagem foi enviada porém não foi recebida 
            throw new RoboDesligadoException("Nannys também ficam de saco cheio, é melhor dar um tempo para a " + this.getNome() + " descansar antes de voltar ao serviço.");
        }
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
     * Executa a tarefa de cuidar de outros robôs no ambiente.
     * @param ambiente
     * @throws SensorDesligadoException se o sensor estiver desligado
     */
    @Override
    public void executarMissao(Ambiente ambiente) throws SensorDesligadoException, RoboDesligadoException {
        if (this.getEstadoRobo() == EstadoRobo.DESLIGADO) {
            throw new RoboDesligadoException(this.getNome());
        }
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
