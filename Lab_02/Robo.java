public class Robo {
    private String nome;    //nome do robô
    private String direcao;   //direção do robô
    private int posicaoX;   //coordenada X no Ambiente
    private int posicaoY;   //coordenada Y no Ambiente

    public Robo (String nomeRobo) {    //Construtor para inicializar os atributos;
        this.nome = nomeRobo;
        this.posicaoX = 0;
        this.posicaoY = 0;
    }

    public void mover(int deltaX, int deltaY) { //Atualiza a posicão do robô;
        this.posicaoX += deltaX;
        this.posicaoY += deltaY;
    }

    public String getNome(){ //Retorna o nome do robô
        return nome;
    }

    public int[] getPosicao(){ //Retorna a posição do robô
        int[] posicao = {this.posicaoX, this.posicaoY};
        return posicao;
    }
}