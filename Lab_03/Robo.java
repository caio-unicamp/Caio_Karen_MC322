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
        SensorProximidade sensorProx = this.getSensorProximidade(); //Acessa o sensor de proximidade do robô

        int posInicialX = this.getPosicao()[0];
        int posInicialY = this.getPosicao()[1];
        int[] passos = this.getPasso(deltaX, deltaY);
        if (passos[0] == 0 && passos[1] == 0) {
            return; // Evita chamadas infinitas
        }
        if (deltaX == 0 && deltaY == 0) { 
            return; // O robô já chegou ao destino, então para a recursão
        }

        boolean moveu = false;
        if (deltaX != 0 && ambiente.dentroDosLimites(deltaX + this.getPosicao()[1] , deltaY, deltaY) && !this.roboParouNoObstaculo(this.getObstaculoIdentificado(this.getPosicao()[0] + passos[0], this.getPosicao()[1], ambiente))){ //Segue recursivamente no eixo x para analisar caso pare em algum obstáculo
            this.setPosicao(this.getPosicao()[0] + passos[0], this.getPosicao()[1], this.getPosicao()[2]);
            moveu = true;
            mover(deltaX - passos[0], deltaY, ambiente);
            return;
        }else if (deltaY != 0 && ambiente.dentroDosLimites(deltaX, deltaY + this.getPosicao()[1] , deltaY) && !this.roboParouNoObstaculo(this.getObstaculoIdentificado(this.getPosicao()[0], this.getPosicao()[1] + passos[1], ambiente))){ //Depois de ter andado tudo em x ele segue recursivamente no eixo y analisando caso identifique algum obstáculo
            this.setPosicao(this.getPosicao()[0], this.getPosicao()[1] + passos[1], this.getPosicao()[2]);
            moveu = true;
            this.mover(deltaX, deltaY - passos[1], ambiente);
            return;
        }
        
        int posAtualX = this.getPosicao()[0];
        int posAtualY = this.getPosicao()[1];
        if (posAtualX == posInicialX + deltaX && posAtualY == posInicialY + deltaY) {
            return; // Se ele andou tudo, não há necessidade de verificar colisões
        }

        if (passos[0] != 0 && ambiente.dentroDosLimites(posAtualX + passos[0], posInicialY, this.getPosicao()[2])){ // Analisa se ele consegue andar em X
            if (sensorProx.monitorar(posAtualX + passos[0], posAtualY, this.getPosicao()[2], ambiente, this)){
                Obstaculo obstaculoIdenObstaculo = this.getObstaculoIdentificado(posAtualX + passos[0], posAtualY, ambiente);
                if (this.roboParouNoObstaculo(obstaculoIdenObstaculo)){ // Se o robô identificar um obstáculo e for parado por ele, encerra o movimento
                    if (sensorProx.getBateria() == 0){ // Se a bateria do sensor de proximidade acabar, aplica as interações de colisão com obstáculos
                        this.interacaoRoboObstaculo(ambiente, obstaculoIdenObstaculo);
                    }
                }
            }
        }else if (passos[1] != 0 && ambiente.dentroDosLimites(posAtualX, posInicialY + passos[1], this.getPosicao()[2])){ // Analisa se ele consegue andar em Y
            if (sensorProx.monitorar(posAtualX, posAtualY + passos[1], this.getPosicao()[2], ambiente, this)){
                Obstaculo obstaculoIdenObstaculo = this.getObstaculoIdentificado(posAtualX, posAtualY + passos[1], ambiente);
                if (this.roboParouNoObstaculo(obstaculoIdenObstaculo)){ // Se o robô identificar um obstáculo e for parado por ele, encerra o movimento
                    if (sensorProx.getBateria() == 0){ // Se a bateria do sensor de proximidade acabar, aplica as interações de colisão com obstáculos
                        this.interacaoRoboObstaculo(ambiente, obstaculoIdenObstaculo);
                    }
                }
            }
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
                if (obstaculo.getPosX1() <= x && obstaculo.getPosX2() >= x && obstaculo.getPosY1() <= y && obstaculo.getPosY2() >= y && obstaculo.getAltura() >= this.getPosicao()[2]) {
                    obstaculoIdentificado = obstaculo;
                    break;
                }
            }
        return obstaculoIdentificado;
    }
    
    public boolean roboParouNoObstaculo(Obstaculo obstaculoIdentificado){ //Função que verifica se o robô parou em um obstáculo
        if (obstaculoIdentificado != null){
            if (!obstaculoIdentificado.getTipoObstaculo().equals(TipoObstaculo.PORTAO)){ //Se o obstáculo identificado for qualquer um deles, exceto o portao, ele para
                return true;
            }
        }
        return false; //Se não parou por conta de nenhum obstáculo, retorna false
    }

    public void interacaoRoboObstaculo(Ambiente ambiente, Obstaculo obstaculoIdentificado){
        if (obstaculoIdentificado.getTipoObstaculo().equals(TipoObstaculo.MINA_TERRESTRE)){ //Se ele identifica uma mina terrestre ele para
            if (this.getSensorProximidade().getBateria() == 0){ //Se a bateria dele tiver acabado, ele não consegue identificar a mina terrestre e irá explodir
                ambiente.removerRobo(this); //Remove o robô explodido
                ambiente.removerObstaculo(obstaculoIdentificado); //Remove a mina terrestre explodida
            }
            return;
        }else if (obstaculoIdentificado.getTipoObstaculo().equals(TipoObstaculo.BURACO_SEM_FUNDO)){ //Se ele identifica um buraco sem fundo ele para
            if (this.getSensorProximidade().getBateria() == 0){ //Se a bateria dele tiver acabado, ele não consegue identificar o buraco e irá cair
                ambiente.removerRobo(this); //Remove o robô que caiu no buraco
            }
            return;
        }
    }
}