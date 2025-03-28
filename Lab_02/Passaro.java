public class Passaro extends RoboAereo{
    //atributo próprio da quantidade de desvios que ele fez
    private int qtddesvios;

    //Construtor para inicializar os atributos
    public Passaro(String nome, String direcao, int x, int y, int altitude, Ambiente ambiente){
        super(nome, direcao, x, y, altitude, ambiente);
    }
    //método próprio de mover
    //se esbarrar para alguma coisa ele desvia para: direita, esquerda, cima e baixo
}