package robots.autonomos;
import ambiente.*;
import excecoes.SensorDesligadoException;
import sensores.*;

public class NannyMcphee extends AgenteInteligente {
    private int numeroDeBebesCuidados;

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
        this.numeroDeBebesCuidados = 0; // Inicializa o número de bebês que foram cuidados
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
        // Lógica para cuidar de bebês no ambiente
        // Aqui você pode implementar a lógica específica para cuidar de bebês,
        // como verificar se há bebês no ambiente, interagir com eles, etc.
        // Por exemplo, incrementar o número de bebês cuidados:
        this.numeroDeBebesCuidados++;
        System.out.println(this.getNome() + " cuidou de um bebê. Total de bebês cuidados: " + this.numeroDeBebesCuidados);
    }
    /**
     * Verifica o número de bebês que foram cuidados pelo babysitter.
     * @return número de bebês cuidados
     */ 
    public int getNumeroDeBebesCuidados() {
        return this.numeroDeBebesCuidados;
    }
}
