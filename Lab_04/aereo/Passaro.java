package aerero;
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
        int posInicialX = this.getPosicao()[0];
        int posInicialY = this.getPosicao()[1];
        int[] passos = this.getPasso(deltaX, deltaY);
        // Condição de parada: verificar se ainda há movimento restante
        if (passos[0] == 0 && passos[1] == 0) {
            return; 
        }
        if (deltaX == 0 && deltaY == 0) { 
            return; 
        }

        super.mover(deltaX, deltaY, ambiente);
        int posAtualX = this.getPosicao()[0];
        int posAtualY = this.getPosicao()[1];
        if (posAtualX == posInicialX + deltaX && posAtualY == posInicialY + deltaY) {
            return; // Se ele andou tudo, não há necessidade de verificar colisões
        }

        if (passos[0] != 0 && ambiente.dentroDosLimites(posAtualX + passos[0], posInicialY, this.getPosicao()[2])){ // Movimento em X
            if (this.getSensorProximidade().monitorar(posAtualX + passos[0], posAtualY, this.getPosicao()[2], ambiente, this)){ //Caso o pássaro identifique um obstáculo ou um robô no caminho ele começa a fazer uma busca para desviar
                //Só desvia se a bateria do sensor de proximidade não acabar
                int novoDeltaX = deltaX - (posAtualX - posInicialX);
                int novoDeltaY = deltaY - (posAtualY - posInicialY);

                // Condição de parada: verificar se ainda há movimento restante
                if (novoDeltaX != 0 || novoDeltaY != 0) {
                    if (desviouXY(ambiente)  || desviouZ(ambiente)){ // Verifica se ele conseguiu desviar ou no plano X-Y ou no plano Z
                        this.desviar(ambiente); // Aplica o desvio
                        this.mover(novoDeltaX - (this.posicao[0] - posAtualX), novoDeltaY - (this.posicao[1] - posAtualY), ambiente); //Chama recursivamente a função mover para continuar o movimento após o desvio
                        qtdDesvios++;
                    }
                    return; // De qualquer forma, se ele não conseguir desviar ele para e se ele conseguir desviar ele continua o movimento com a chamada recursiva
                }
            }
        }else if (passos[1] != 0 && ambiente.dentroDosLimites(posAtualX, posInicialY + passos[1], this.getPosicao()[2])){ // Movimento em X
            if (this.getSensorProximidade().monitorar(posAtualX, posAtualY + passos[1], this.getPosicao()[2], ambiente, this)){ //Caso o pássaro identifique um obstáculo ou um robô no caminho ele começa a fazer uma busca para desviar
                //Só desvia se a bateria do sensor de proximidade não acabar    
                int novoDeltaX = deltaX - (posAtualX - posInicialX);
                int novoDeltaY = deltaY - (posAtualY - posInicialY);
                // Condição de parada: verificar se ainda há movimento restante
                if (novoDeltaX != 0 || novoDeltaY != 0) {
                    if (desviouXY(ambiente)  || desviouZ(ambiente)){ // Verifica se ele conseguiu desviar ou no plano X-Y ou no plano Z
                        this.desviar(ambiente); // Aplica o desvio
                        this.mover(novoDeltaX - (this.posicao[0] - posAtualX), novoDeltaY - (this.posicao[1] - posAtualY), ambiente); //Chama recursivamente a função mover para continuar o movimento após o desvio
                        qtdDesvios++;
                    }
                    return; // De qualquer forma, se ele não conseguir desviar ele para e se ele conseguir desviar ele continua o movimento com a chamada recursiva
                }
            }
        }
    }

    public boolean desviouXY(Ambiente ambiente){
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

    public boolean desviouZ(Ambiente ambiente){
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
    private void desviar(Ambiente ambiente){
        if (desviouXY(ambiente)){
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
                        return;
                    }
                }
            }
        }else if (desviouZ(ambiente)){
            // Se não conseguiu desviar no plano X-Y, tenta desviar para cima ou para baixo (Z)
            int[] desviosZ = {1, -1}; // Primeiro tenta subir, depois descer
            for (int dz : desviosZ) {
                int novoZ = this.getPosicao()[2] + dz;
    
                if (!this.getSensorProximidade().monitorar(this.getPosicao()[0], this.getPosicao()[1], novoZ, ambiente, this) &&
                    ambiente.dentroDosLimites(this.getPosicao()[0], this.getPosicao()[1], novoZ)) {
    
                    // Move para cima ou para baixo
                    this.setPosicao(this.getPosicao()[0], this.getPosicao()[1], novoZ);
                    return;
                }
            }
        }
    }            

    public int getQtddesvios(){
        return qtdDesvios;
    }
}