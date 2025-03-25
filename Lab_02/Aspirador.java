public class Aspirador extends RoboTerrestre{
    //atributo numero de robos que eliminou
    private int robosEliminados = 0;
    //Construtor para inicializar os atributos
    public Aspirador(String nome, String direcao, int x, int y, int velocidadeMaxima){
        super(nome, direcao, x, y, velocidadeMaxima);
        this.robosEliminados = 0;   //inicializar o atritubo próprio
    }
}
    //método de eliminar
    public boolean eliminar() {
        //se identificar um robo no caminho
        if (super.identificarRobo(this.x , this.y)){ {
            this.robosEliminados++;
            //eliminar o robô
            return true;
        }
        else {
            return false;
        }
    }
}

    //método de se mover
    public void mover(int deltaX, int deltaY){      
        //se encontrar um obstaculo
        //matar o robo -> pegar a lista de robos e tirar o robo da lista

        //se não encontrar nada
        //continuar.
}