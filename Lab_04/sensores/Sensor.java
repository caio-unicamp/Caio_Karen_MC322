package sensores;
abstract class Sensor<T> {
    private double raio;
    private int bateria;
    private final String nomeSensor;
    
    Sensor(double raio, String nomeSensor){
        this.raio = raio;
        this.bateria = 100; // Bateria inicial em 100%
        this.nomeSensor = nomeSensor;
    }
    /**
     * Método abstrato para monitoramento feito pelos sensores que depende de cada um deles
     * @param atributo
     */
    public abstract T monitorar(Object... atributo);
    /**
     * Verifica a bateria do sensor.
     * @return bateria
     */
    public int getBateria() {
        return bateria;
    }
    /**
     * Seta a bateria para um valor específico.
     * @param bateria
     */
    public void setBateria(int bateria) {
        this.bateria = bateria;
    }
    /**
     * Método para saber qual o raio de alcance do sensor
     * @return raio de alcance do sensor.
     */
    public double getRaio() {
        return raio;
    }
    /**
     * Seta o raio de alcance do sensor.
     * @param raio
     */
    public void setRaio(double raio) {
        this.raio = raio;
    }
    /**
     * Recarrega a bateria do sensor.
     * @param carga a ser recarregada
     */
    public void recarregarBateria(int carga) {
        this.bateria += carga;
        if (this.bateria > 100) {
            this.bateria = 100; // Bateria não pode exceder 100%
        }
        setBateria(this.bateria);
    }
    /**
     * Consome a bateria do sensor.
     * @param consumo da bateria.
     */
    public void consumirBateria(int consumo) {
        this.bateria -= consumo;
        if (this.bateria < 0) {
            this.bateria = 0; // Bateria não pode ser negativa
        }
        this.setBateria(this.bateria);
    }
    /**
     * Confere se a bateria do sensor está acabando.
     * @return true se estiver abaixo de 20% e false caso contrário.
     */
    public boolean isBateriaBaixa() {
        return this.bateria <= 20; 
    }
    /**
     * Método para saber o nome do sensor usado
     * @return nome do sensor
     */
    public String getNomeSensor(){
        return this.nomeSensor;
    }
}