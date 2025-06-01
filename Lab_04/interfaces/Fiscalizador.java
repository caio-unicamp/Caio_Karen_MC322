package interfaces;
/**
 * Interface que define sensores que avisam quando algum parâmetro do robô está muito elevado
 */
public interface Fiscalizador {
    /**
     * Verifica se o parâmetro referente ao sensor está muito elevado
     * @param taxa
     * @return true se o parâmetro estiver muito alto e false caso contrário
     */
    public boolean isMuito(double taxa);
}