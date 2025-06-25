package robots.autonomos;

public class Babysitter extends AgenteInteligente {
    private int numeroDeBebesCuidando;

    /**
     * Construtor da classe Babysitter.
     * Inicializa o babysitter com um nome, direção, coordenadas e número de bebês que está cuidando.
     * @param nome representando seu nome
     * @param direcao representando a direção do robô
     * @param x coordenada x no ambiente
     * @param y coordenada y no ambiente
     */
    public Babysitter(String nome, String direcao, int x, int y) {
        super(nome, direcao, x, y);
        this.numeroDeBebesCuidando = 0; // Inicializa o número de bebês que está cuidando
    }
    
}
