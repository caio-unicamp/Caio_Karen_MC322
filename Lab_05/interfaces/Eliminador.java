package interfaces;
import ambiente.Ambiente;
/**
 * Referente a robôs que conseguem eliminar outros robôs no ambiente
 */
public interface Eliminador {
    /**
     * Elimina um robô a uma distância de 1 unidade do robô
     * @param passosX
     * @param passosY
     * @param ambiente
     */
    void eliminar(int passosX, int passosY, Ambiente ambiente);
}
