import java.util.ArrayList;

public class Robo{
    private String nome;    //nome do robô
    private String direcao;   //direção do robô
    private int posicaoX;   //coordenada X no Ambiente
    private int posicaoY;   //coordenada Y no Ambiente
    private int posicaoZ;   //coordenada Z no Ambiente no caso de robôs aéreos
    private ArrayList<Sensor<?>> listaSensores; //Lista de sensores do robô
    
    public Robo (String nomeRobo, String direcaoRobo, int x, int y, int z) { //Construtor para inicializar os atributos do robô aéreo;
        this.nome = nomeRobo;
        this.direcao = direcaoRobo;
        this.posicaoX = x;
        this.posicaoY = y;
        this.posicaoZ = z;
        this.listaSensores = new ArrayList<>();
    }

    public void mover(int deltaX, int deltaY, Ambiente ambiente) { //Atualiza a posicão do robô de modo que ele anda primeiro no eixo x e depois no eixo y
        SensorProximidade sensorProx = null;
        for (Sensor<?> sensor : listaSensores) {
            if (sensor instanceof SensorProximidade){ //Verifica se o sensor é do tipo SensorProximidade
                sensorProx = (SensorProximidade) sensor;
            }
        }
        
        if (deltaX == 0 && deltaY == 0) { //Condição de parada da recursão
            return;
        }
        
        //Analisa se o passo do robô é positivo ou negativo ou nulo
        int[] passos = getPasso(deltaX, deltaY);
        boolean moveu = false;
        if (deltaX != 0 && ambiente.dentroDosLimites(deltaX + this.posicaoX , deltaY, deltaY) && !sensorProx.monitorar(this.posicaoX + passos[0], this.posicaoY, 0, ambiente, this)){ //Segue recursivamente no eixo x para analisar caso identifique algum obstáculo
            this.posicaoX += passos[0];
            moveu = true;
            mover(deltaX - passos[0], deltaY, ambiente);
            return;
        }else if (deltaY != 0 && ambiente.dentroDosLimites(deltaX, deltaY + this.posicaoY , deltaY) && !sensorProx.monitorar(this.posicaoX, this.posicaoY + passos[1], 0, ambiente, this)){ //Depois de ter andado tudo em x ele segue recursivamente no eixo y analisando caso identifique algum obstáculo
            this.posicaoY += passos[1];
            moveu = true;
        }

        if (!moveu){ //Se não conseguiu mover nem em X e nem em Y ele para a função
            return;
        }
    }

    public String getNome(){ //Retorna o nome do robô
        return nome;
    }

    public int[] getPosicao(){ //Retorna a posição do robô
        int[] posicao = {this.posicaoX, this.posicaoY, this.posicaoZ};
        return posicao;
    }

    public String getDirecao(){ //Retorna a direção que o robô está encarando
        return direcao;
    }
    
    public void setDirecao(String direcao){ //Função para alterar a direção que o robô está encarando
        this.direcao = direcao;
    }

    public void setPosicao(int x, int y, int z){ //Função para setar a posição dos robôs
        this.posicaoX = x;
        this.posicaoY = y;
        this.posicaoZ = z;
    }

    public int[] getPasso(int deltaX, int deltaY){ //Retorna o passo negativo ou positivo de x e de y
        int passoX = 0, passoY = 0;
        if (deltaX != 0){
            passoX = deltaX/Math.abs(deltaX);
        }
        if (deltaY != 0){
            passoY = deltaY/Math.abs(deltaY);
        }
        int[] listaPasso = {passoX, passoY};
        return listaPasso;
    }

    public void adicionarSensor(Sensor<?> sensor){ //Adiciona um sensor na lista de sensores do robô
        listaSensores.add(sensor);
    }

    public void removerSensor(Sensor<?> sensor){ //Remove um sensor na lista de sensores do robô
        listaSensores.remove(sensor);
    }

    public ArrayList<Sensor<?>> getSensores(){ //Acessa quais são os sensores que esse robô tem
        return listaSensores;
    }
    
    public SensorProximidade getSensorProximidade(){ //Função para retornar o sensor de proximidade do robô
        SensorProximidade sensorProx = null;
        for (Sensor<?> sensor : this.getSensores()) { //Procura na lista de sensores do robo pelo sensor de proximidade
            if (sensor instanceof SensorProximidade){ //Verifica se o sensor é do tipo SensorProximidade
                sensorProx = (SensorProximidade) sensor;
            }
        }
        return sensorProx;
    }

    public Obstaculo getObstaculoIdentificado(int x, int y, Ambiente ambiente){
        Obstaculo obstaculoIdentificado = null;
            for (Obstaculo obstaculo : ambiente.getListaObstaculos()) {
                if (obstaculo.getPosX1() <= x && obstaculo.getPosX2() >= x && obstaculo.getPosY1() <= y && obstaculo.getPosY2() >= y && obstaculo.getAltura() == this.getPosicao()[2]) {
                    obstaculoIdentificado = obstaculo;
                    break;
                }
            }
        return obstaculoIdentificado;
    }
    
    public boolean roboParouNoObstaculo(int x, int y, Ambiente ambiente){ //Função que verifica se o robô parou em um obstáculo
        Obstaculo obstaculoIdentificado = this.getObstaculoIdentificado(x, y, ambiente);
        if (obstaculoIdentificado != null){
            if (obstaculoIdentificado.getTipoObstaculo().equals(TipoObstaculo.ARVORE)){ //Se o obstáculo identificado for uma parede, o robô não pode passar por ele
                return true;
            }else if (obstaculoIdentificado.getTipoObstaculo().equals(TipoObstaculo.MINA_TERRESTRE)){ //Se ele identifica uma mina terrestre ele para
                if (this.getSensorProximidade().getBateria() == 0){ //Se a bateria dele tiver acabado, ele não consegue identificar a mina terrestre e irá explodir
                    ambiente.removerRobo(this); //Remove o robô explodido
                    ambiente.removerObstaculo(obstaculoIdentificado); //Remove a mina terrestre explodida
                }
                return true;
            }else if (obstaculoIdentificado.getTipoObstaculo().equals(TipoObstaculo.BURACO_SEM_FUNDO)){ //Se ele identifica uma mina aérea ele para
                if (this.getSensorProximidade().getBateria() == 0){ //Se a bateria dele tiver acabado, ele não consegue identificar o buraco e irá cair
                    ambiente.removerRobo(this); //Remove o robô que caiu no buraco
                }
                return true;
            }
        }
        return false; //Se não parou por conta de nenhum obstáculo, retorna false
    }
}