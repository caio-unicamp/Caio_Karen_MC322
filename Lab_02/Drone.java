public class Drone extends RoboAereo{
    //atributo próprio é um robô que será entregue pelo drone
    Robo ada;
    //Construtor para inicializar os atributos
    public Drone(String nome, String direcao, int x, int y, int altitude){
        super(nome, direcao, x, y, altitude);
        this.ada = new Robo("Ada", "Sul", 0, 0);
    }
    //método próprio de jogar o robo dentro dele nas coordenadas finais
    //se esbarrar em algum objeto, ele derruba o robo no local que parou
    //se mover junto com o drone
    //descer na coordenada z
    
    
}