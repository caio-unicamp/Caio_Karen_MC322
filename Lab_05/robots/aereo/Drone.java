package robots.aereo;
import interfaces.*;
import enums.*;
import excecoes.*;
import ambiente.*;
import sensores.*;
import robots.*;
import robots.terrestre.Rover;

public class Drone extends RoboAereo implements Comunicavel{
    Rover pacote; //Atributo próprio é um robô que será entregue pelo drone
    int tempoLocomocaoPacote;
    
    public Drone(String nome, String direcao, int x, int y, int altitude, int tempoLocomocaoTerrestre, int altitudeMaxima){ //Construtor para inicializar os atributos
        super(nome, direcao, x, y, altitude, altitudeMaxima);
        this.tempoLocomocaoPacote = tempoLocomocaoTerrestre;
    }
    /**
     * Envia uma mensagem para outro robô
     * @param destinatario o robô que receberá a mensagem
     * @param mensagem a mensagem a ser enviada
     * @throws RoboDesligadoException se o robô estiver desligado
     * @throws ErroComunicacaoException se houver um erro na comunicação, como destinatário nulo ou não comunicável
     */
    @Override
    public void enviarMensagem(Entidade destinatario, String mensagem) throws RoboDesligadoException, ErroComunicacaoException {
        if (this.getEstadoRobo() == EstadoRobo.DESLIGADO) { //Checa se o drone está ligado
            throw new RoboDesligadoException(this.getNome());
        }

        if (destinatario == null) { //Verifica se o destinatário existe
            throw new ErroComunicacaoException("Destinatário da mensagem não pode ser nulo.");
        }else if (!(destinatario instanceof Comunicavel)){ //Verifica se o destinatário é comunicável
            throw new ErroComunicacaoException("Você está tentando mandar uma mensagem para alguém que não quer falar com você... que situação hein");
        }

        if (destinatario instanceof Robo && ((Robo) destinatario).getEstadoRobo() == EstadoRobo.DESLIGADO) { //Checa se quem receberá a mensagem está ligado
            CentralComunicacao.getInstancia().registrarMensagem(this.getNome(), ((Robo) destinatario).getNome(), "[TENTATIVA FALHA - DESTINATÁRIO DESLIGADO] " + mensagem); //Registra na central que não foi possível enviar a mensagem pois o destinatário estava desligado
            throw new ErroComunicacaoException("O robô destinatário " + ((Robo) destinatario).getNome() + " está desligado.");
        }
        Comunicavel receptor = (Comunicavel) destinatario; //Faz o cast para comunicável
        //Faz o destinatário receber a mensagem (caso ele esteja ligado)
        receptor.receberMensagem(this.getNome(), mensagem); 
        // Se o destinatário puder ser instanciado como um robô, busca seu nome, se não, trata como desconhecido
        String nomeDestinatario = (receptor instanceof Robo) ? ((Robo) receptor).getNome() : "Desconhecido"; 
        // Registra que a mensagem foi enviada.
        CentralComunicacao.getInstancia().registrarMensagem(this.getNome(), nomeDestinatario, mensagem); 
    }
    /**
     * Recebe mensagens enviadas por outros robôs comunicáveis
     * @param remetente o nome do robô que enviou a mensagem
     * @param mensagem a mensagem recebida
     * @throws RoboDesligadoException se o robô estiver desligado
     */
    @Override
    public void receberMensagem(String remetente, String mensagem) throws RoboDesligadoException {
        if (this.getEstadoRobo() == EstadoRobo.DESLIGADO) { 
            //Mesmo que após a conferência de envio tiver passado, o robô destinatário não conseguir receber a mensagem, registra que a mensagem foi enviada porém não foi recebida 
            throw new RoboDesligadoException("O robô " + this.getNome() + " está desligado e não pode receber mensagens.");
        }
    }
    /**
     *  Método para mover o drone sem entregar nenhum pacote
     * @throws ErroComunicacaoException 
     */
    @Override
    public void mover(int deltaX, int deltaY, Ambiente ambiente) throws SensorDesligadoException, RoboDesligadoException, ColisaoException, ErroComunicacaoException{ // Função para mover o drone sem entregar nenhum pacote
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
     * @throws ErroComunicacaoException 
     */
    public void entregarPacote(int posicaoXdronefinal, int posicaoYdronefinal, String nomePacote, Ambiente ambiente) throws SensorDesligadoException, RoboDesligadoException, ColisaoException, ErroComunicacaoException{
        //inicializar o robo pacote
        if (this.getEstadoRobo().equals(EstadoRobo.DESLIGADO)){ //Lança o erro do caso do robô estar desligado
            throw new RoboDesligadoException("O pacote não pode ser entregue pois o " + this.getNome() + " está desligado");
        }
        for (Sensor<?> sensor : this.getSensores()){ //Verifica os sensores do drone para checagem de bateria
            if (sensor.getBateria() == 0){ //Se algum dos sensores estiver com a bateria descarregada impede de prosseguir
                throw new SensorDesligadoException(sensor, this.getNome());
            }
        }
        //Salva as posições iniciais do drone antes de movê-lo
        int posInicialX = this.getPosicao()[0];
        int posInicialY = this.getPosicao()[1];
        int posInicialZ = this.getPosicao()[2];
        
        this.mover(posicaoXdronefinal - posInicialX, posicaoYdronefinal - posInicialY, ambiente);
        
        if (!ambiente.getListaEntidades().contains(this)){ //Caso o próprio drone não esteja na lista de robôs ativos é porque ou ele explodiu ou ele caiu num buraco enquanto se movia, então não adiciona o pacote que ele carregava 
            throw new ColisaoException("Infelizmente o " + this.getNome() + " não está mais entre nós. Ele e o pacote foram destruídos");
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
                        if (obstaculo.getX() <= this.getPosicao()[0] && obstaculo.getPosX2() >= this.getPosicao()[0] && obstaculo.getY() <= this.getPosicao()[1] && obstaculo.getPosY2() >= this.getPosicao()[1] && obstaculo.getZ() >= this.getPosicao()[2]){ //Se já existe um obstáculo no lugar que o pacote seria derrubado, ele será destruído
                            this.interacaoRoboObstaculo(ambiente, obstaculo);
                            throw new ColisaoException("O pacote foi entregue onde havia um obstáculo e infelizmente foi destruído.");
                        }
                    }
                }
                if (jaExisteRobo(ambiente)){ //Caso exista robô onde seria feita a entrega, ele e o pacote serão destruídos
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
                    throw new ColisaoException("Como eu avisei, já existia um robô onde você queria entregar o pacote e você acabou destruindo ambos");
                }else { //Entregou o pacote com sucesso
                    adicionaPacote(nomePacote, ambiente);
                    return;
                }
            }else{ //Se o drone não conseguiu descer tudo ele encontrou um obstáculo na descida
                for (Entidade entidade : ambiente.getListaEntidades()){
                    if (!(entidade instanceof Robo)){ //Verifica se a entidade é um robô
                        continue; //Se não for, pula para a próxima iteração
                    }else{
                        Robo robo = (Robo) entidade; //Faz o cast para robô
                        if (robo.getPosicao()[0] == this.getPosicao()[0] && robo.getPosicao()[1] == this.getPosicao()[1] && robo.getPosicao()[2] == 0){ //Se já existe um robô no lugar que o pacote seria derrubado, ele será destruído e não adiciona o pacote
                            destroiRoboColidido(robo, ambiente);
                            throw new ColisaoException("Que desastre, além do seu drone ter tido um problema na descida e ter derrubado seu pacote, ja tinha um robô no lugar que o pacote caiu e ambos foram destruídos");
                        }
                    }
                }
                adicionaPacote(nomePacote, ambiente);
                throw new ColisaoException("Durante a descida para entregar o pacote o drone encontrou um problema e acabou derrubando seu pacote.");
            }
        }
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
        pacote = new Rover(nomePacote, this.getDirecao(), this.getPosicao()[0], this.getPosicao()[1], 0, this.tempoLocomocaoPacote);
        ambiente.adicionarEntidade((Entidade)pacote);
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