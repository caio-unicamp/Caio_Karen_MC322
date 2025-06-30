package missions;
import ambiente.Ambiente;
import enums.EstadoRobo;
import excecoes.ColisaoException;
import excecoes.RoboDesligadoException;
import excecoes.SensorDesligadoException;
import robot.Robo;
import robot.autonomos.NannyMcphee;
import ambiente.Obstaculo;
import utils.Logger;

public class MissaoProteger implements Missao {

    public MissaoProteger() {
    }
    /**
     * Executa a missão de proteger um robô no ambiente.
     * A Nanny McPhee seguirá seu bebê e tentará evitar que ele encontre perigos.
     * @param robo autônomo que executa a missão.
     * @param ambiente O ambiente onde a missão ocorre.
     */
    @Override
    public void executar(Robo robo, Ambiente ambiente) {
        if (!(robo instanceof NannyMcphee)) {
            return; // A missão só é aplicável a robôs do tipo NannyMcphee.
        }

        NannyMcphee nanny = (NannyMcphee) robo;
        Robo bebe = nanny.getBebe();

        if (bebe == null || nanny.getEstadoRobo() == EstadoRobo.DESLIGADO) {
            return; // Se não há bebê para proteger ou a Nanny está desligada, a missão não é executada.
        }

        try {
            // Lógica para seguir o bebê
            int nannyX = nanny.getX();
            int nannyY = nanny.getY();
            int bebeX = bebe.getX();
            int bebeY = bebe.getY();

            int deltaX = bebeX - nannyX;
            int deltaY = bebeY - nannyY;

            // Move-se para ficar adjacente ao bebê, e não na mesma célula
            if (Math.abs(deltaX) > 1 || Math.abs(deltaY) > 1) {
                int targetX = deltaX - Integer.signum(deltaX);
                int targetY = deltaY - Integer.signum(deltaY);
                nanny.mover(targetX, targetY, ambiente);
                Logger.log("Operação babá: "+ nanny.getNome() + " se moveu para proteger " + bebe.getNome() + " de perigos.");
            }

            // Lógica para evitar que o bebê seja destruído
            // A Nanny verifica a área ao redor do bebê em busca de obstáculos perigosos.
            int[] bebePasso = bebe.getPasso(1, 1);
            int proximoXBebe = bebe.getX() + bebePasso[0];
            int proximoYBebe = bebe.getY() + bebePasso[1];

            if (ambiente.dentroDosLimites(proximoXBebe, proximoYBebe, bebe.getZ())) {
                Obstaculo obstaculoPerigoso = nanny.getObstaculoIdentificado(proximoXBebe, proximoYBebe, ambiente);
                if (obstaculoPerigoso != null) {
                    switch (obstaculoPerigoso.getTipoObstaculo()) {
                        case MINA_TERRESTRE:
                        case BURACO_SEM_FUNDO:
                            Logger.log("ALERTA! " + nanny.getNome() + " detectou perigo para " + bebe.getNome() + "!");
                            // Move a Nanny para a posição do obstáculo para "bloquear" o caminho.
                            nanny.mover(obstaculoPerigoso.getX() - nanny.getX(), obstaculoPerigoso.getY() - nanny.getY(), ambiente);
                            break;
                        default:
                            // Não faz nada para outros tipos de obstáculos.
                            break;
                    }
                }
            }

        } catch (SensorDesligadoException | RoboDesligadoException | ColisaoException e) {
            Logger.log("Nanny McPhee encontrou um problema ao proteger: " + e.getMessage());
        } catch (Exception e) {
            Logger.log("Ocorreu um erro inesperado na MissaoProteger: " + e.getMessage());
        }
    }
}