public class Passaro extends RoboAereo{
    //atributo próprio da quantidade de desvios que ele fez
    private int qtdDesvios = 0;
    private Ambiente ambiente;
    //Construtor para inicializar os atributos
    public Passaro(String nome, String direcao, int x, int y, int altitude, Ambiente ambiente){
        super(nome, direcao, x, y, altitude, ambiente);
        this.ambiente = ambiente;
    }
    
    public void mover(int deltaX, int deltaY) {
        super.mover(deltaX, deltaY);
        if (identificarRobo(this.getPosicao()[0] + this.getPasso(deltaX, deltaY)[0], this.getPosicao()[1] + this.getPasso(deltaX, deltaY)[1], this.getPosicao()[2], this.getNome())){ //Caso o pássaro identifique um obstáculo no caminho ele começa a fazer uma busca para desviar
            if (desviar(deltaX, deltaY)) {
                qtdDesvios++;
            }
        }
    }

    //método próprio de desviar, se o Pássaro se esbarrar em alguma coisa ele tenta desviar para todas as possibilidades de direção
    private boolean desviar(int deltaX, int deltaY) {
        String direcao = this.getDirecao();
        int dx = 0, dy = 0;

        switch (direcao.toLowerCase()){ //Define o deslocamento com base na direção
            case "norte": dy = 1;
                break;
            case "sul": dy = -1;
                break;
            case "leste": dx = 1;
                break;
            case "oeste": dx = -1;
                break;
        }  
        // Primeiro tenta desviar no plano X-Y (3x3 à frente)
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int novoX = this.getPosicao()[0] + dx + i;
                int novoY = this.getPosicao()[1] + dy + j;
                int novoZ = this.getPosicao()[2]; //Mantém a altura inicial

                if (!identificarRobo(novoX, novoY, novoZ, this.getNome()) &&
                    ambiente.dentroDosLimites(novoX, novoY, novoZ)) {

                    // Move para a posição desviada no plano 2D
                    super.mover(novoX - this.getPosicao()[0], novoY - this.getPosicao()[1]);

                    // Tenta continuar na trajetória original
                    super.mover(deltaX - dx, deltaY - dy);
                    return true;
                }
            }
        }

        // Se não conseguiu desviar no plano X-Y, tenta desviar para cima ou para baixo (Z)
        int[] desviosZ = {1, -1}; // Primeiro tenta subir, depois descer
        for (int dz : desviosZ) {
            int novoX = this.getPosicao()[0] + dx;
            int novoY = this.getPosicao()[1] + dy;
            int novoZ = this.getPosicao()[2] + dz;

            if (!identificarRobo(novoX, novoY, novoZ, this.getNome()) &&
                ambiente.dentroDosLimites(novoX, novoY, novoZ)) {

                // Move para cima ou para baixo
                this.getPosicao()[2] = novoZ;

                // Continua a trajetória original
                super.mover(deltaX - dx, deltaY - dy);
                return true;
            }
        }
        return false; // Se não encontrou nenhuma rota alternativa
    }            

    public int getQtddesvios(){
        return qtdDesvios;
    }
}