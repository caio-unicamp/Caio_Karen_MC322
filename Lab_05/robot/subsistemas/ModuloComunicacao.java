package robot.subsistemas;

import enums.EstadoRobo;
import excecoes.ErroComunicacaoException;
import excecoes.RoboDesligadoException;
import interfaces.Comunicavel;
import interfaces.Entidade;
import robot.Robo;
import utils.CentralComunicacao;

public class ModuloComunicacao implements Comunicavel {
    private final Robo roboPai; // Atributo para armazenar a interface comunicável, se necessário
    public ModuloComunicacao(Robo roboPai) {
        this.roboPai = roboPai;
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
        if (roboPai.getEstadoRobo() == EstadoRobo.DESLIGADO) { //Checa se o drone está ligado
            throw new RoboDesligadoException(roboPai.getNome() + " está desligado e não pode enviar mensagens.");
        }

        if (destinatario == null) { //Verifica se o destinatário existe
            throw new ErroComunicacaoException("Destinatário da mensagem não pode ser nulo.");
        }else if (!(destinatario instanceof Comunicavel)){ //Verifica se o destinatário é comunicável
            throw new ErroComunicacaoException("Você está tentando mandar uma mensagem para alguém que não quer falar com você... que situação hein");
        }

        if (destinatario instanceof Robo && ((Robo) destinatario).getEstadoRobo() == EstadoRobo.DESLIGADO) { //Checa se quem receberá a mensagem está ligado
            CentralComunicacao.getInstancia().registrarMensagem(roboPai.getNome(), ((Robo) destinatario).getNome(), "[TENTATIVA FALHA - DESTINATÁRIO DESLIGADO] " + mensagem); //Registra na central que não foi possível enviar a mensagem pois o destinatário estava desligado
            throw new ErroComunicacaoException("O robô destinatário " + ((Robo) destinatario).getNome() + " está desligado.");
        }
        Comunicavel receptor = (Comunicavel) destinatario; //Faz o cast para comunicável
        //Faz o destinatário receber a mensagem (caso ele esteja ligado)
        receptor.receberMensagem(roboPai.getNome(), mensagem); 
        // Se o destinatário puder ser instanciado como um robô, busca seu nome, se não, trata como desconhecido
        String nomeDestinatario = (receptor instanceof Robo) ? ((Robo) receptor).getNome() : "Desconhecido"; 
        // Registra que a mensagem foi enviada.
        CentralComunicacao.getInstancia().registrarMensagem(roboPai.getNome(), nomeDestinatario, mensagem); 
    }
    /**
     * Recebe mensagens enviadas por outros robôs comunicáveis
     * @param remetente o nome do robô que enviou a mensagem
     * @param mensagem a mensagem recebida
     * @throws RoboDesligadoException se o robô estiver desligado
     */
    @Override
    public void receberMensagem(String remetente, String mensagem) throws RoboDesligadoException {
        if (roboPai.getEstadoRobo() == EstadoRobo.DESLIGADO) { 
            //Mesmo que após a conferência de envio tiver passado, o robô destinatário não conseguir receber a mensagem, registra que a mensagem foi enviada porém não foi recebida 
            throw new RoboDesligadoException("O robô " + roboPai.getNome() + " está desligado e não pode receber mensagens.");
        }
    }
}