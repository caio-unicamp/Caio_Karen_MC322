public class Rover extends RoboTerrestre{
    //atributo próprio
    int qtdRobosEmpurrados;
    //Construtor
    public Rover(String nome, String direcao, int x, int y, int velocidadeMaxima, Ambiente ambiente){
        super(nome, direcao, x, y, velocidadeMaxima, ambiente);
        this.qtdRobosEmpurrados = 0;
    }
    //metodo de mover com empurrar um robo junto



    
}