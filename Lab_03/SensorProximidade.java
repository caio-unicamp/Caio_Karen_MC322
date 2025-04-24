public class SensorProximidade extends Sensor<Boolean>{
    int raio;
    public SensorProximidade(double raio){
        super(raio);
        this.raio = 1; //Como o sensor é de proximidade ele só consegue identificar obstáculos a 1 unidade de distância 
    }
    //Utilizamos uma lista de objetos diversos, aqui atributo[0] e atributo[1] são as coordenadas x e y do robô, atributo[2] é a altura do robô, atributo[3] é o ambiente e atributo[4] é o robô que está tentando se mover
    @Override
    //Método abstrato para monitorar a altura do robô
    public Boolean monitorar(Object... atributo){ //Método que verifica se o robô irá colidir com algum obstáculo ou robô
        if (((Robo) atributo[4]).getPasso((int) atributo[0], (int) atributo[1])[0] > 0){ //Se o passo for positivo ele anda para o leste
            ((Robo) atributo[4]).setDirecao("Leste");
        }else if (((Robo) atributo[4]).getPasso((int) atributo[0], (int) atributo[1])[0] < 0){ //Se o passo for negativo ele anda para o oeste
            ((Robo) atributo[4]).setDirecao("Oeste");
        }else if (((Robo) atributo[4]).getPasso((int) atributo[0], (int) atributo[1])[1] > 0){ //Se o passo for positivo ele anda para o leste
            ((Robo) atributo[4]).setDirecao("Norte");
        }else if (((Robo) atributo[4]).getPasso((int) atributo[0], (int) atributo[1])[1] < 0){ //Se o passo for negativo ele anda para o oeste
            ((Robo) atributo[4]).setDirecao("Sul");
        }
        return(identificarRobo((int) atributo[0],(int) atributo[1],(int) atributo[2],(Ambiente) atributo[3],(Robo) atributo[4]) || identificarObstaculo((int) atributo[0],(int) atributo[1],(int) atributo[2],(Ambiente) atributo[3])); //Caso o robô identifique um obstáculo ou um robô, ele retorna true caso contrário, retorna false
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