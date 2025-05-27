package aereo;
public class Drone extends RoboAereo implements Comunicavel{
    Robo pacote; //Atributo próprio é um robô que será entregue pelo drone
    int tempoLocomocaoPacote;
    
    public Drone(String nome, String direcao, int x, int y, int altitude, int tempoLocomocaoTerrestre, int altitudeMaxima){ //Construtor para inicializar os atributos
        super(nome, direcao, x, y, altitude, altitudeMaxima);
        this.tempoLocomocaoPacote = tempoLocomocaoTerrestre;
    }
    /**
     * Envia uma mensagem para outro robô
     */
    @Override
    public void enviarMensagem(Comunicavel destinatario, String mensagem) throws RoboDesligadoException, ErroComunicacaoException {
        if (this.getEstadoRobo() == EstadoRobo.DESLIGADO) { //Checa se o drone está ligado
            throw new RoboDesligadoException( this.getNome());
        }

        if (destinatario == null) {
            throw new ErroComunicacaoException("Destinatário da mensagem não pode ser nulo.");
        }

        if (destinatario instanceof Robo && ((Robo) destinatario).getEstadoRobo() == EstadoRobo.DESLIGADO) { //Checa se quem receberá a mensagem não está ligado
            CentralComunicacao.getInstancia().registrarMensagem(this.getNome(), ((Robo) destinatario).getNome(), "[TENTATIVA FALHA - DESTINATÁRIO DESLIGADO] " + mensagem); //Registra na central que não foi possível enviar a mensagem pois o destinatário estava desligado
            throw new ErroComunicacaoException("O robô destinatário " + ((Robo) destinatario).getNome() + " está desligado.");
        }

        // Call destinatario's receberMensagem method
        // The 'remetente' parameter in receberMensagem will be the name of this robot
        destinatario.receberMensagem(this.getNome(), mensagem); //

        // Register the message with CentralComunicacao
        // Assuming the destinatario can be cast to Robo to get its name for logging.
        // If not all Comunicavel are Robo, you might need a getNome() in Comunicavel or handle this differently.
        String nomeDestinatario = "Desconhecido";
        if (destinatario instanceof Robo) {
            nomeDestinatario = ((Robo) destinatario).getNome();
        } else if (destinatario instanceof Entidade) { // Fallback if it's some other Entidade that's Comunicavel
             nomeDestinatario = ((Entidade) destinatario).getNome();
        }
        // Register the message in the communication central
        CentralComunicacao.getInstancia().registrarMensagem(this.getNome(), nomeDestinatario, mensagem); //
        //COLOCAR PRINT NA MAIN DEPOIS DE REGISTRAR A MENSAGEM
        System.out.println("[" + this.getNome() + " para " + nomeDestinatario + "]: " + mensagem + " (Mensagem enviada)");
    }

    @Override
    public void receberMensagem(String remetente, String mensagem) throws RoboDesligadoException {
        if (this.getEstadoRobo() == EstadoRobo.DESLIGADO) { //
            // Even if off, it might be argued a message "arrives" but isn't processed.
            // However, per PDF for Comunicavel.receberMensagem, it can throw RoboDesligadoException.
            // Logging it might still be useful.
            // CentralComunicacao.getInstancia().registrarMensagem(remetente, this.getNome(), "[TENTATIVA FALHA DE ENTREGA - RECEPTOR DESLIGADO] " + mensagem);
            throw new RoboDesligadoException("O robô " + this.getNome() + " está desligado e não pode receber mensagens.");
        }

        // Logic for what the robot does with the message and nothing more happens
        System.out.println("[" + this.getNome() + " recebeu de " + remetente + "]: \"" + mensagem + "\"");
    }
    /**
     *  Método para mover o drone sem entregar nenhum pacote
     */
    @Override
    public void mover(int deltaX, int deltaY, Ambiente ambiente) throws SensorDesligadoException, RoboDesligadoException, ColisaoException{ // Função para mover o drone sem entregar nenhum pacote
        int posInicialX = this.getPosicao()[0];
        int posInicialY = this.getPosicao()[1];
        int[] passos = this.getPasso(deltaX, deltaY);
        if (passos[0] == 0 && passos[1] == 0) {
            return; // Evita chamadas infinitas
        }
        if (deltaX == 0 && deltaY == 0) { 
            return; // O drone já chegou ao destino, então para a recursão
        }
        
        super.mover(deltaX, deltaY, ambiente);
        int posAtualX = this.getPosicao()[0];
        int posAtualY = this.getPosicao()[1];
        if (posAtualX == posInicialX + deltaX && posAtualY == posInicialY + deltaY) {
            return; // Se ele andou tudo, não há necessidade de verificar colisões
        }
    }
    /**
     * Verifica se o pacote conseguiu ser entregue
     * @param posicaoXdronefinal
     * @param posicaoYdronefinal
     * @param nomePacote
     * @param ambiente
     * @return true se o pacote foi entregue na posição que foi indicada e false se houve algum problema no caminho
     * @throws ColisaoException 
     * @throws RoboDesligadoException 
     * @throws SensorDesligadoException 
     */
    public boolean entregouPacote(int posicaoXdronefinal, int posicaoYdronefinal, String nomePacote, Ambiente ambiente) throws SensorDesligadoException, RoboDesligadoException, ColisaoException{
        //inicializar o robo pacote
        int posInicialX = this.getPosicao()[0];
        int posInicialY = this.getPosicao()[1];
        int posInicialZ = this.getPosicao()[2];
        
        this.mover(posicaoXdronefinal - posInicialX, posicaoYdronefinal - posInicialY, ambiente);
        
        int numRobosAnalisados = 0;
        for (Entidade entidade : ambiente.getListaEntidades()){
            if (!(entidade instanceof Robo)){ //Verifica se a entidade é um robô
                continue; //Caso não seja, passa para a próxima interação
            }else{
                Robo robo = (Robo) entidade; //Faz o cast para robô
                if (!robo.equals(this)){
                    numRobosAnalisados++;
                }else{
                    break;
                }
            }
        }if (numRobosAnalisados == ambiente.getNumRobosAmbiente()){ //Caso o próprio drone não esteja na lista de robôs ativos é porque ou ele explodiu ou ele caiu num buraco enquanto se movia, então não adiciona o pacote que ele carregava 
            return false;
        }
        
        if (this.getPosicao()[0] == posicaoXdronefinal && this.getPosicao()[1] == posicaoYdronefinal){ //Se o drone já chegou na posição que ele quer, ele desce com o robo
            this.descer(this.getPosicao()[2] - 1, ambiente); //Desce o drone até uma unidade antes do chão para conseguir entregar o pacote
            if (this.getPosicao()[2] == 1){ //Nesse ponto depois da descida o pacote foi entregue com sucesso
                this.setPosicao(this.getPosicao()[0], this.getPosicao()[1], posInicialZ); //Após a entrega o drone sobe para a altura que estava no início, como a função descer já analisou se existiam obstáculos, basta apenas setar a posição z como a mesma do início sem necessidade de nenhuma conferência extra
                for (Entidade entidade: ambiente.getListaEntidades()){
                    if (!(entidade instanceof Obstaculo)){ //Verifica se a entidade é um obstáculo
                        continue; //Se não for, segue para a próxima iteração
                    }else{
                        Obstaculo obstaculo = (Obstaculo) entidade; //Faz o cast para obstáculo
                        if (obstaculo.getPosX1() <= this.getPosicao()[0] && obstaculo.getPosX2() >= this.getPosicao()[0] && obstaculo.getPosY1() <= this.getPosicao()[1] && obstaculo.getPosY2() >= this.getPosicao()[1] && obstaculo.getAltura() >= this.getPosicao()[2]){ //Se já existe um obstáculo no lugar que o pacote seria derrubado, ele será destruído
                            if (obstaculo.getTipoObstaculo().equals(TipoObstaculo.MINA_TERRESTRE)){ // No caso do pacote cair em uma mina, ela é destruída
                                ambiente.removerEntidade(obstaculo);
                            }
                            return false;
                        }
                    }
                }
                if (jaExisteRobo(ambiente)){ //Caso exista robô onde seria entregue, ele e o pacote serão destruídos
                    for (Entidade entidade : ambiente.getListaEntidades()){
                        if (!(entidade instanceof Robo)){ //Verifica se a entidade é um robô
                            continue; //Se não for, pula para a próxima iteração
                        }else{
                            Robo robo = (Robo) entidade; //Faz o cast para robô
                            if (robo.getPosicao()[0] == this.getPosicao()[0] && robo.getPosicao()[1] == this.getPosicao()[1] && robo.getPosicao()[2] == 0){ //Se já existe um robô no lugar que o pacote seria derrubado, ele será destruído
                                destroiRoboColidido(robo, ambiente);
                            }
                        }
                    }
                    return false;
                }else {
                    adicionaPacote(nomePacote, ambiente);
                    return true;
                }
            }else{
                for (Entidade entidade : ambiente.getListaEntidades()){
                    if (!(entidade instanceof Robo)){ //Verifica se a entidade é um robô
                        continue; //Se não for, pula para a próxima iteração
                    }else{
                        Robo robo = (Robo) entidade; //Faz o cast para robô
                        if (robo.getPosicao()[0] == this.getPosicao()[0] && robo.getPosicao()[1] == this.getPosicao()[1] && robo.getPosicao()[2] == 0){ //Se já existe um robô no lugar que o pacote seria derrubado, ele será destruído
                            destroiRoboColidido(robo, ambiente);
                        }
                    }
                }
            }
        }
        return false; // Caso o robô não tenha conseguido entregar o pacote, retorna false
    }
    /**
     * Verifica se já existe um robô no local que o pacote seria derrubado
     * @param ambiente
     * @return true caso exista e false caso contrário
     */
    public boolean jaExisteRobo(Ambiente ambiente){
        for (Entidade entidade : ambiente.getListaEntidades()){
            if (!(entidade instanceof Robo)){ //Verifica se a entidade é um robô
                continue; //Se não for, segue para a próxima iteração
            }else{
                Robo robo = (Robo) entidade; //Faz o cast para robô
                if (robo.getPosicao()[0] == this.getPosicao()[0] && robo.getPosicao()[1] == this.getPosicao()[1] && robo.getPosicao()[2] == 0){
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Adiciona o pacote na lista de entidades no ambiente
     * @param nomePacote
     * @param ambiente
     */
    public void adicionaPacote(String nomePacote, Ambiente ambiente){
        pacote = new Rover(nomePacote, this.getDirecao(), this.getPosicao()[0], this.getPosicao()[1], this.getPosicao()[2], tempoLocomocaoPacote);
        ambiente.adicionarEntidade(pacote);
    }
    /**
     * Destrói o robô que colidiu
     * @param roboColidido
     * @param ambiente
     */
    public void destroiRoboColidido(Robo roboColidido, Ambiente ambiente){
        ambiente.removerEntidade(roboColidido);
    }
}