class Robo {
    private String nome;    //nome do robô
    private int posicaoX;   //coordenada X no Ambiente
    private int posicaoY;   //coordenada Y no Ambiente


    public Robo (nomeRobo) {    //Construtor para inicializar os atributos;
    
    nome = nomeRobo;
    posicaoX = 0;
    posicaoY = 0;
    
    }

    public void setPosicao(int deltaX, int deltaY) { //Atualiza a posi ̧c ̃ao do robˆo;
    
    posicaoX = deltaX;
    posicaoY = deltaY;
    
    }

    public int obtemPosicao()
    
    //exibirPosicao() - Imprime a posi ̧c ̃ao atual do robˆo.




}