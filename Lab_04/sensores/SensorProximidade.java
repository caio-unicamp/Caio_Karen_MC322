package sensores;
public class SensorProximidade extends Sensor<Boolean>{
    public SensorProximidade(double raio){
        super(raio);
    }
    //Utilizamos uma lista de objetos diversos, aqui atributo[0] e atributo[1] são as coordenadas x e y do robô, atributo[2] é a altura do robô, atributo[3] é o ambiente e atributo[4] é o robô que está tentando se mover
    @Override
    //Método abstrato para monitorar a altura do robô
    public Boolean monitorar(Object... atributo){ //Método que verifica se o robô irá colidir com algum obstáculo ou robô
        this.consumirBateria(5); // Consome 5% da bateria a cada monitoramento

        if (((Robo) atributo[4]).getPasso((int) atributo[0], (int) atributo[1])[0] > 0){ //Se o passo for positivo ele anda para o leste
            ((Robo) atributo[4]).setDirecao("leste");
        }else if (((Robo) atributo[4]).getPasso((int) atributo[0], (int) atributo[1])[0] < 0){ //Se o passo for negativo ele anda para o oeste
            ((Robo) atributo[4]).setDirecao("oeste");
        }else if (((Robo) atributo[4]).getPasso((int) atributo[0], (int) atributo[1])[1] > 0){ //Se o passo for positivo ele anda para o leste
            ((Robo) atributo[4]).setDirecao("norte");
        }else if (((Robo) atributo[4]).getPasso((int) atributo[0], (int) atributo[1])[1] < 0){ //Se o passo for negativo ele anda para o oeste
            ((Robo) atributo[4]).setDirecao("sul");
        }
        return(identificarRobo((int) atributo[0],(int) atributo[1],(int) atributo[2],(Ambiente) atributo[3],(Robo) atributo[4]) || identificarObstaculo((int) atributo[0],(int) atributo[1],(int) atributo[2],(Ambiente) atributo[3])); //Caso o robô identifique um obstáculo ou um robô, ele retorna true caso contrário, retorna false
    }

    public boolean identificarRobo(int x, int y, int z, Ambiente ambiente, Robo roboProprio){
        for (Entidade entidade: ambiente.getListaEntidades()){ //Para cada Robô na lista de robôs (obviamente não sendo o robô que está tentando identificar um obstáculo), ele analisa se a posição que o robô em questão quer ir já está ocupada 
            if (!(entidade instanceof Robo)){ //Verifica se a entidade é um robô
                continue; //Se não for, pula para a próxima interação
            }else{
                Robo robo = (Robo) entidade; //Fazo cast para Robô
                if (robo.getPosicao()[0] == x && robo.getPosicao()[1] == y && robo.getPosicao()[2] == z && !robo.equals(roboProprio)){ //Se a posição já estiver ocupada por outro robô, ele retorna true
                    return true;
                }
            }
        }return false;
    }

    public boolean identificarObstaculo(int x, int y, int z, Ambiente ambiente){ //Identifica se o robô está colidindo com algum obstáculo
        for (Entidade entidade: ambiente.getListaEntidades()){ //Para cada obstáculo na lista de obstáculos, ele analisa se a posição que o robô em questão quer ir já está ocupada 
            if (!(entidade instanceof Obstaculo)){ //Verifica se a entidade é um obstáculo
                continue; //Se não for, pula para a próxima interação
            }else{
                Obstaculo obstaculo = (Obstaculo) entidade;
                if (obstaculo.getPosX1() >= x && obstaculo.getPosX2() <= x && obstaculo.getPosY1() >= y && obstaculo.getPosY2() <= y && obstaculo.getAltura() == z){ //Se a posição já estiver ocupada por um obstáculo, ele retorna true
                    return true;
                }
            }
        }return false;
    }
}