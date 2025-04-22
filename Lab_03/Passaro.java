public class Passaro extends RoboAereo{
    //atributo próprio da quantidade de desvios que ele fez
    private int qtdDesvios;
    //Construtor para inicializar os atributos
    public Passaro(String nome, String direcao, int x, int y, int altitude, Ambiente ambiente){
        super(nome, direcao, x, y, altitude, ambiente);
        this.qtdDesvios = 0;
    }
    
    @Override
    public void mover(int deltaX, int deltaY, Ambiente ambiente){
        if (identificarRobo(this.getPosicao()[0] + this.getPasso(deltaX, deltaY)[0], this.getPosicao()[1] + this.getPasso(deltaX, deltaY)[1], this.getPosicao()[2], ambiente)){ //Caso o pássaro identifique um obstáculo no caminho ele começa a fazer uma busca para desviar
            if (desviar(deltaX, deltaY, ambiente)){
                qtdDesvios++;
                return;
            }
        }
        super.mover(deltaX, deltaY, ambiente);
    }

    //método próprio de desviar, se o Pássaro se esbarrar em alguma coisa ele tenta desviar para todas as possibilidades de direção
    private boolean desviar(int deltaX, int deltaY, Ambiente ambiente){
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0){ // A posição original não deve ser testada
                    continue;
                }
                
                int novoX = this.getPosicao()[0] + i;
                int novoY = this.getPosicao()[1] + j;
                int novoZ = this.getPosicao()[2]; //Mantém a altura inicial

                if (!identificarRobo(novoX, novoY, novoZ, ambiente) &&
                    ambiente.dentroDosLimites(novoX, novoY, novoZ)) {

                    // Move para a posição desviada no plano 2D
                    super.mover(novoX - this.getPosicao()[0], novoY - this.getPosicao()[1], ambiente);

                    return true;
                }
            }
        }

        // Se não conseguiu desviar no plano X-Y, tenta desviar para cima ou para baixo (Z)
        int[] desviosZ = {1, -1}; // Primeiro tenta subir, depois descer
        for (int dz : desviosZ) {
            int novoZ = this.getPosicao()[2] + dz;

            if (!identificarRobo(this.getPosicao()[0], this.getPosicao()[1], novoZ, ambiente) &&
                ambiente.dentroDosLimites(this.getPosicao()[0], this.getPosicao()[1], novoZ)) {

                // Move para cima ou para baixo
                this.getPosicao()[2] = novoZ;

                return true;
            }
        }
        return false; // Se não encontrou nenhuma rota alternativa
    }            

    public int getQtddesvios(){
        return qtdDesvios;
    }
}