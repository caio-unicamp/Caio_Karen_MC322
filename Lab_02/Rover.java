public class Rover extends RoboTerrestre{
    //atributo próprio
    int qtdRobosEmpurrados;
    Ambiente ambiente;
    //Construtor
    public Rover(String nome, String direcao, int x, int y, int velocidadeMaxima, Ambiente ambiente, int tempoLocomocaoTerrestre){
        super(nome, direcao, x, y, velocidadeMaxima, ambiente, tempoLocomocaoTerrestre);
        this.ambiente = ambiente;
        this.qtdRobosEmpurrados = 0;
    }
    
    public void mover(int deltaX, int deltaY){ //Função para mover o rover e acessar a função empurrar robô
        super.mover(deltaX, deltaY);
        for (Robo robo : ambiente.getLista()){
            if 
        }
    }
    
    //metodo de mover com empurrar um robo junto
    public void empurrarRobo(Robo empurrado){
        
    }
}