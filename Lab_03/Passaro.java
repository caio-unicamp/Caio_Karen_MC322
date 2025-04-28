public class Passaro extends RoboAereo{
    //Atributo próprio da quantidade de desvios que ele fez
    private int qtdDesvios;
    private int[] posicao = new int[3]; // Atributo para armazenar a posição do pássaro após o desvio
    //Construtor para inicializar os atributos
    public Passaro(String nome, String direcao, int x, int y, int altitude, int altitudeMaxima){
        super(nome, direcao, x, y, altitude, altitudeMaxima); //Herança da classe robô aéreo
        this.qtdDesvios = 0;
        this.posicao[0] = x;
        this.posicao[1] = y;
        this.posicao[2] = altitude;
    }
    
    @Override
    public void mover(int deltaX, int deltaY, Ambiente ambiente){
        if ( this.getSensorProximidade().monitorar(this.getPosicao()[0] + this.getPasso(deltaX, deltaY)[0], this.getPosicao()[1] + this.getPasso(deltaX, deltaY)[1], this.getPosicao()[2], ambiente, this)){ //Caso o pássaro identifique um obstáculo ou um robô no caminho ele começa a fazer uma busca para desviar
            if (this.getSensorProximidade().getBateria() != 0){ //Só desvia se a bateria do sensor de proximidade não acabar
                if (desviouXY(deltaX, deltaY, ambiente)){ //Desvia no plano X-Y
                    this.desviar(deltaX, deltaY, ambiente);
                    this.mover(this.posicao[0] - this.getPosicao()[0], this.posicao[1] - this.getPosicao()[1], ambiente);
                    qtdDesvios++;
                    return;
                }else if (desviouZ(deltaX, deltaY, ambiente)){ //Desvia verticalmente
                    this.desviar(deltaX, deltaY, ambiente);
                    this.mover(this.posicao[0] - this.getPosicao()[0], this.posicao[1] -  this.getPosicao()[1], ambiente);
                    qtdDesvios++;
                    return;
            }
            }
        }else if (this.roboParouNoObstaculo(this.getObstaculoIdentificado(this.getPosicao()[0] + this.getPasso(deltaX, deltaY)[0], this.getPosicao()[1] + this.getPasso(deltaX, deltaY)[1], ambiente))){ //Caso a bateria do sensor de proximidade acabe ele não consegue mais desviar, daí realiza-se as colisões com os obstáculos
            this.interacaoRoboObstaculo(ambiente, this.getObstaculoIdentificado(this.getPosicao()[0] + this.getPasso(deltaX, deltaY)[0], this.getPosicao()[1] + this.getPasso(deltaX, deltaY)[1], ambiente));
            return;
        }
        super.mover(deltaX, deltaY, ambiente);
    }

    public boolean desviouXY(int deltaX, int deltaY, Ambiente ambiente){
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0){ // A posição original não deve ser testada
                    continue;
                }
                
                posicao[0] = this.getPosicao()[0] + i;
                posicao[1] = this.getPosicao()[1] + j;
                posicao[2] = this.getPosicao()[2]; //Mantém a altura inicial

                if (!this.getSensorProximidade().monitorar(this.posicao[0], this.posicao[1], this.posicao[2], ambiente, this) &&
                    ambiente.dentroDosLimites(this.posicao[0], this.posicao[1], this.posicao[2])) {
                    // Move para a posição desviada no plano 2D
                    return true;
                }
            }
        }
        return false; // Se não conseguiu desviar no plano X-Y
    }

    public boolean desviouZ(int deltaX, int deltaY, Ambiente ambiente){

        // Se não conseguiu desviar no plano X-Y, tenta desviar para cima ou para baixo (Z)
        int[] desviosZ = {1, -1}; // Primeiro tenta subir, depois descer
        for (int dz : desviosZ) {
            int novoZ = this.getPosicao()[2] + dz;

            if (!this.getSensorProximidade().monitorar(this.getPosicao()[0], this.getPosicao()[1], novoZ, ambiente, this) &&
                ambiente.dentroDosLimites(this.getPosicao()[0], this.getPosicao()[1], novoZ)) {

                // Move para cima ou para baixo
                this.posicao[2] = novoZ;
                return true;
            }
        }
        return false; // Se não encontrou nenhuma rota alternativa
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

                if (!this.getSensorProximidade().monitorar(novoX, novoY, novoZ, ambiente, this) &&
                    ambiente.dentroDosLimites(novoX, novoY, novoZ)) {
                    // Move para a posição desviada no plano 2D
                    this.mover(novoX - this.getPosicao()[0], novoY - this.getPosicao()[1], ambiente);
                    return true;
                }
            }
        }

        // Se não conseguiu desviar no plano X-Y, tenta desviar para cima ou para baixo (Z)
        int[] desviosZ = {1, -1}; // Primeiro tenta subir, depois descer
        for (int dz : desviosZ) {
            int novoZ = this.getPosicao()[2] + dz;

            if (!this.getSensorProximidade().monitorar(this.getPosicao()[0], this.getPosicao()[1], novoZ, ambiente, this) &&
                ambiente.dentroDosLimites(this.getPosicao()[0], this.getPosicao()[1], novoZ)) {

                // Move para cima ou para baixo
                this.getPosicao()[2] = novoZ;
                this.mover(this.getPosicao()[0], this.getPosicao()[1], ambiente);
                return true;
            }
        }
        return false; // Se não encontrou nenhuma rota alternativa
    }            

    public int getQtddesvios(){
        return qtdDesvios;
    }
}