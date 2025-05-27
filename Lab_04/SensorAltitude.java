public class SensorAltitude extends Sensor<Integer>{

    public SensorAltitude(double raio, String nomeSensor){
        super(raio, nomeSensor);
    }
    @Override
    /**
     * Para esse sensor o monitoramento funciona retornando a altura que o robô que usa esse sensor está.
     * @return altura do robô.
     * @implNote atributo[2] se refere à altitude do robô.
     */
    public Integer monitorar(Object... atributo){
        this.consumirBateria(5); // Consome 5% da bateria a cada monitoramento
        return (int) atributo[2];   
    }
    /**
     * Checa quão alto dentro do ambiente o robô está
     * @param altura
     * @param alturaMax
     * @return porcentagem da altura do robô em relação à altura máxima do ambiente
     */
    public double porcentoAltura(int altura, int alturaMax){
        return (double) ((altura * 100) / alturaMax); 
    }
    /**
     * Verifica se o robô está muito alto dentro do ambiente
     * @param taxaAltura
     * @return true se estiver pelo menos a 90% da altura máxima e false caso contrário
     */
    public boolean isMuitoAlto(double taxaAltura){
        return taxaAltura >= 90? true : false;
    }
}