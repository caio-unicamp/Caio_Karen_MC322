package robot.subsistemas;

import ambiente.Ambiente;
import ambiente.Obstaculo;
import excecoes.ColisaoException;
import excecoes.ErroComunicacaoException;
import excecoes.RoboDesligadoException;
import excecoes.SensorDesligadoException;
import robot.Robo;
import sensores.SensorProximidade;

public class ControleMovimento {
    private Robo roboPai;

    public ControleMovimento(Robo roboPai) {
        this.roboPai = roboPai;
    }
    /**
     * Método para mover o robô no ambiente, de modo que ele anda primeiro no eixo X e depois no eixo Y.
     * @param deltaX
     * @param deltaY
     * @param ambiente
     * @throws SensorDesligadoException
     * @throws RoboDesligadoException
     * @throws ColisaoException
     * @throws ErroComunicacaoException 
     */
    public void mover(int deltaX, int deltaY, Ambiente ambiente) throws SensorDesligadoException, RoboDesligadoException, ColisaoException, ErroComunicacaoException{
        roboPai.acionarSensores(); //Aciona os sensores do robô antes de iniciar o movimento
        SensorProximidade sensorProx = roboPai.getSensorProximidade(); //Acessa o sensor de proximidade do robô

        int posInicialX = roboPai.getPosicao()[0];
        int posInicialY = roboPai.getPosicao()[1];
        int[] passos = roboPai.getPasso(deltaX, deltaY);
        if (passos[0] == 0 && passos[1] == 0) {
            return; // Evita chamadas infinitas
        }
        if (deltaX == 0 && deltaY == 0) { 
            return; // O robô já chegou ao destino, então para a recursão
        }

        boolean moveu = false;
        if (deltaX != 0 && ambiente.dentroDosLimites(deltaX + roboPai.getPosicao()[1] , deltaY, deltaY) && !roboPai.roboParouNoObstaculo(roboPai.getObstaculoIdentificado(roboPai.getPosicao()[0] + passos[0], roboPai.getPosicao()[1], ambiente))){ //Segue recursivamente no eixo x para analisar caso pare em algum obstáculo
            roboPai.setPosicao(roboPai.getPosicao()[0] + passos[0], roboPai.getPosicao()[1], roboPai.getPosicao()[2]);
            moveu = true;
            mover(deltaX - passos[0], deltaY, ambiente);
            return;
        }else if (deltaY != 0 && ambiente.dentroDosLimites(deltaX, deltaY + roboPai.getPosicao()[1] , deltaY) && !roboPai.roboParouNoObstaculo(roboPai.getObstaculoIdentificado(roboPai.getPosicao()[0], roboPai.getPosicao()[1] + passos[1], ambiente))){ //Depois de ter andado tudo em x ele segue recursivamente no eixo y analisando caso identifique algum obstáculo
            roboPai.setPosicao(roboPai.getPosicao()[0], roboPai.getPosicao()[1] + passos[1], roboPai.getPosicao()[2]);
            moveu = true;
            roboPai.mover(deltaX, deltaY - passos[1], ambiente);
            return;
        }
        
        int posAtualX = roboPai.getPosicao()[0];
        int posAtualY = roboPai.getPosicao()[1];
        if (posAtualX == posInicialX + deltaX && posAtualY == posInicialY + deltaY) {
            return; // Se ele andou tudo, não há necessidade de verificar colisões
        }

        if (passos[0] != 0 && ambiente.dentroDosLimites(posAtualX + passos[0], posInicialY, roboPai.getPosicao()[2])){ // Analisa se ele consegue andar em X
            if (sensorProx.monitorar(posAtualX + passos[0], posAtualY, roboPai.getPosicao()[2], ambiente, roboPai)){
                Obstaculo obstaculoIdenObstaculo = roboPai.getObstaculoIdentificado(posAtualX + passos[0], posAtualY, ambiente);
                if (roboPai.roboParouNoObstaculo(obstaculoIdenObstaculo)){ // Se o robô identificar um obstáculo e for parado por ele, encerra o movimento
                    if (sensorProx.getBateria() == 0){ // Se a bateria do sensor de proximidade acabar, aplica as interações de colisão com obstáculos
                        roboPai.interacaoRoboObstaculo(ambiente, obstaculoIdenObstaculo);
                    }
                }
            }
        }else if (passos[1] != 0 && ambiente.dentroDosLimites(posAtualX, posInicialY + passos[1], roboPai.getPosicao()[2])){ // Analisa se ele consegue andar em Y
            if (sensorProx.monitorar(posAtualX, posAtualY + passos[1], roboPai.getPosicao()[2], ambiente, roboPai)){
                Obstaculo obstaculoIdenObstaculo = roboPai.getObstaculoIdentificado(posAtualX, posAtualY + passos[1], ambiente);
                if (roboPai.roboParouNoObstaculo(obstaculoIdenObstaculo)){ // Se o robô identificar um obstáculo e for parado por ele, encerra o movimento
                    if (sensorProx.getBateria() == 0){ // Se a bateria do sensor de proximidade acabar, aplica as interações de colisão com obstáculos
                        roboPai.interacaoRoboObstaculo(ambiente, obstaculoIdenObstaculo);
                    }
                }
            }
        }
        if (!moveu){ //Se não conseguiu mover nem em X e nem em Y ele para a função
            return;
        }
    }

}