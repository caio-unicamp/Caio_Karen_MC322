public class Passaro extends RoboAereo{
    //atributo próprio da quantidade de desvios que ele fez
    private int qtdDesvios = 0;
    private int posicaoXPassaro;
    private int posicaoYPassaro;
    private int posicaoZPassaro;
    private Ambiente ambiente;
    //Construtor para inicializar os atributos
    public Passaro(String nome, String direcao, int x, int y, int altitude, Ambiente ambiente){
        super(nome, direcao, x, y, altitude, ambiente);
        this.ambiente = ambiente;
    }
    
    public void mover(int deltaX, int deltaY) {
        int posXinicial = this.getPosicao()[0];
        int posYinicial = this.getPosicao()[1];
        super.mover(deltaX, deltaY);
        if (identificarRobo(this.getPosicao()[0] + this.getPasso(deltaX, deltaY)[0], this.getPosicao()[1] + this.getPasso(deltaX, deltaY)[1], this.getPosicao()[2], this.getNome())){ //Caso o pássaro identifique um obstáculo no caminho ele começa a fazer uma busca para desviar
            if (desviar(deltaX, deltaY)) {
                qtdDesvios++;
                this.mover(deltaX, deltaY);
            }else {
                System.out.println("O pássaro não conseguiu se mover!");
            }
        }
    }

    //método próprio de desviar, se o Pássaro se esbarrar para alguma coisa ele desvia para: direita, esquerda, cima e baixo

    private boolean desviar(int deltaX, int deltaY) {

        // Testa várias direções até encontrar uma possível
        int[][] direcoes = {
            {1, 0, 0}, {-1, 0, 0}, {0, 1, 0}, {0, -1, 0},  
            {1, 1, 0}, {-1, -1, 0}, {1, -1, 0}, {-1, 1, 0}, 
            {1, 0, -1}, {-1, 0, -1}, {0, 1, -1}, {0, -1, -1},  
            {1, 1, -1}, {-1, -1, -1}, {1, -1, -1}, {-1, 1, -1},
            {1, 0, 1}, {-1, 0, 1}, {0, 1, 1}, {0, -1, 1},  
            {1, 1, 1}, {-1, -1, 1}, {1, -1, 1}, {-1, 1, 1}
        };

        for (int[] dir : direcoes) {
            int novoX = this.posicaoXPassaro + dir[0];
            int novoY = this.posicaoYPassaro + dir[1];
            int novoZ = this.posicaoZPassaro + dir[2];

            if (!identificarRobo(novoX, novoY, novoZ, this.getNome()) && ambiente.dentroDosLimites(novoX, novoY, novoZ)){ //Se nenhum robô for encontrado na direção escolhida e ele estiver dentro dos limites, o pássaro segue nessa direção
                super.mover(dir[0], dir[1]);
                super.mover(deltaX - dir[0], deltaY - dir[1]);  // Tenta continuar a trajetória original
                return true;
            }
        }
        return false;
    }
    public boolean desviar(){
        //se ele consegue ir para a direita, ele vai 1 posição paraa direita
        if (mover(this.posicaoXPassaro + 1, this.posicaoYPassaro)){
            mover(this.posicaoXPassaro + 1, this.posicaoYPassaro);
        }
        //se ele consegue ir para a esquerda, ele vai 1 posição paraa esquerda
        else if (mover(this.posicaoXPassaro - 1, this.posicaoYPassaro)){
            mover(this.posicaoXPassaro - 1, this.posicaoYPassaro);
        }
        //se ele consegue ir para cima, ele vai 1 posição paraa cima
        else if (mover(this.posicaoXPassaro, this.posicaoYPassaro + 1)){
            mover(this.posicaoXPassaro, this.posicaoYPassaro + 1);
        }
        //se ele consegue ir para baixo, ele vai 1 posição paraa baixo
        else if (mover(this.posicaoXPassaro, this.posicaoYPassaro - 1)){
            mover(this.posicaoXPassaro, this.posicaoYPassaro - 1);
        }
        //caso não consiga ir para nenhum dos lados, retorna falso
        return false;
    }
    
}