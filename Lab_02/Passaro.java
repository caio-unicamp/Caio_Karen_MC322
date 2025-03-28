public class Passaro extends RoboAereo{
    //atributo próprio da quantidade de desvios que ele fez
    private int qtddesvios;

    //Construtor para inicializar os atributos
    public Passaro(String nome, String direcao, int x, int y, int altitude, Ambiente ambiente){
        super(nome, direcao, x, y, altitude, ambiente);
    }
    //método próprio de mover
    public boolean moverPassaro(int posicaoXpassarofinal, int posicaoYpassarofinal){ 
        mover(posicaoXpassarofinal - posicaoXpassaro, posicaoYpassarofinal - posicaoYpassaro); //Move o Passaro até a posição final, na função mover ele identifica se encontra outro robô no caminho, com isso para antes e tenta desviar para a direita, depois esquerda, cima e baixo
        //se chegar na posição final
        if ((this.posicaoXPassaro == posicaoXpassarofinal) && (this.posicaoYPassaro == posicaoYpassarofinal)){ 
            //retornar verdadeiro
            return true;
        }
        //se não chegar na posição final, ele tenta desviar
        else {
            desviar();    //chama a função desviar
            qtddesvios++; //incrementa a quantidade de desvios
        }    
    }    

    //método próprio de desviar, se o Pássaro se esbarrar para alguma coisa ele desvia para: direita, esquerda, cima e baixo
    public boolean desviar(){
        //se ele consegue ir para a direita, ele vai 1 posição paraa direita
        if (mover(this.posicaoXPassaro + 1, this.posicaoYPassaro)){
            mover(this.posicaoXPassaro + 1, this.posicaoYPassaro)
        }
        //se ele consegue ir para a esquerda, ele vai 1 posição paraa esquerda
        else if (mover(this.posicaoXPassaro - 1, this.posicaoYPassaro)){
            mover(this.posicaoXPassaro - 1, this.posicaoYPassaro)
        }
        //se ele consegue ir para cima, ele vai 1 posição paraa cima
        else if (mover(this.posicaoXPassaro, this.posicaoYPassaro + 1)){
            mover(this.posicaoXPassaro, this.posicaoYPassaro + 1)
        }
        //se ele consegue ir para baixo, ele vai 1 posição paraa baixo
        else if (mover(this.posicaoXPassaro, this.posicaoYPassaro - 1)){
            mover(this.posicaoXPassaro, this.posicaoYPassaro - 1)
        }
        //caso não consiga ir para nenhum dos lados, retorna falso
        return false;
    }
}