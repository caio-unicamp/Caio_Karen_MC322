public class SensorProximidade extends Sensor<Boolean>{

    public SensorProximidade(double raio){
        super(raio);
    }
    
    @Override
    //Método abstrato para monitorar a altura do robô
    Boolean monitorar(int x, int y, int z, Ambiente ambiente){ //Método que verifica se o robô irá colidir com algum obstáculo ou robô
        if (identificarRobo(getBateria(), getBateria(), getBateria(), null) || identificarObstaculo(getBateria(), getBateria(), getBateria(), null)){
            return true; //Caso encontre um robô ou obstáculo, retorna true
        }
        return false; //Caso contrário, retorna false
    }

    public boolean identificarRobo(int x, int y, int z, Ambiente ambiente, Robo roboProprio){
        for (Robo robo: ambiente.getListaRobos()){ //Para cada Robô na lista de robôs (obviamente não sendo o robô que está tentando identificar um obstáculo), ele analisa se a posição que o robô em questão quer ir já está ocupada 
            if (robo.getPosicao()[0] == x && robo.getPosicao()[1] == y && robo.getPosicao()[2] == z && !robo.equals(roboProprio)){ //Se a posição já estiver ocupada por outro robô, ele retorna true
                return true;
            }
        }return false;
    }

    public boolean identificarObstaculo(int x, int y, int z, Ambiente ambiente){ //Identifica se o robô está colidindo com algum obstáculo
        for (Obstaculo obstaculo: ambiente.getListaObstaculos()){ //Para cada obstáculo na lista de obstáculos, ele analisa se a posição que o robô em questão quer ir já está ocupada 
            if (obstaculo.getPosX1() >= x && obstaculo.getPosX2() <= x && obstaculo.getPosY1() >= y && obstaculo.getPosY2() <= y && obstaculo.getAltura() == z){ //Se a posição já estiver ocupada por um obstáculo, ele retorna true
                return true;
            }
        }return false;
    }
}