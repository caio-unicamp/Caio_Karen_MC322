abstract class Sensor<T> {
    private double raio;
    private int bateria;
    
    Sensor(double raio){
        this.raio = raio;
        this.bateria = 100; // Bateria inicial em 100%
    }
    
    public abstract T monitorar(Object... atributo);

    public int getBateria() {
        return bateria;
    }

    public void setBateria(int bateria) {
        this.bateria = bateria;
    }

    public double getRaio() {
        return raio;
    }

    public void setRaio(double raio) {
        this.raio = raio;
    }

    public void recarregarBateria(int carga) {
        setBateria(this.bateria + carga);
        if (this.bateria > 100) {
            this.bateria = 100; // Bateria não pode exceder 100%
        }
    }

    public void consumirBateria(int consumo) {
        this.bateria -= consumo;
        if (this.bateria < 0) {
            this.bateria = 0; // Bateria não pode ser negativa
        }
    }

    public boolean isBateriaBaixa() {
        return this.bateria <= 20; // Considera bateria baixa se estiver abaixo de 20%
    }
}