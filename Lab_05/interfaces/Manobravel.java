package interfaces;

import ambiente.Ambiente;
import excecoes.ColisaoException;
import excecoes.ErroComunicacaoException;
import excecoes.RoboDesligadoException;
import excecoes.SensorDesligadoException;

/**
 * Interface para robôs que possuem capacidades de manobras especiais,
 * como subir ou descer
 */
public interface Manobravel {

    /**
     * Move o robô para cima no ambiente.
     *
     * @param deltaZ A quantidade de unidades a subir.
     * @param ambiente O ambiente no qual o robô está se movendo.
     * @throws ColisaoException Se ocorrer uma colisão durante a subida.
     * @throws SensorDesligadoException Se um sensor necessário estiver desligado.
     * @throws RoboDesligadoException Se o robô estiver desligado.
     * @throws ErroComunicacaoException Se ocorrer um erro de comunicação.
     */
    void subir(int deltaZ, Ambiente ambiente) throws ColisaoException, SensorDesligadoException, RoboDesligadoException, ErroComunicacaoException;

    /**
     * Move o robô para baixo no ambiente.
     *
     * @param deltaZ A quantidade de unidades a descer.
     * @param ambiente O ambiente no qual o robô está se movendo.
     * @throws ColisaoException Se ocorrer uma colisão durante a descida.
     * @throws SensorDesligadoException Se um sensor necessário estiver desligado.
     * @throws RoboDesligadoException Se o robô estiver desligado.
     * @throws ErroComunicacaoException Se ocorrer um erro de comunicação.
     */
    void descer(int deltaZ, Ambiente ambiente) throws ColisaoException, SensorDesligadoException, RoboDesligadoException, ErroComunicacaoException;

}