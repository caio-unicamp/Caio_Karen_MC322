import java.util.Objects;

public final class Posicao { //A classe tem modificador final por se tratar das chaves do meu hash map do mapa 
    private final int x; //Posição x
    private final int y; //Posição y
    private final int z; //Posição z

    public Posicao(int x, int y, int z) { //Constructor das posições
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object object) { // Altera o método original de equals da superclasse original Object para conseguir perguntar direto as posições como (x,y,z)
        if (this == object){
            return true;
        } 

        if (object == null || getClass() != object.getClass()){
            return false;
        } 
        Posicao posicao = (Posicao) object;
        return x == posicao.x && y == posicao.y && z == posicao.z;
    }

    @Override
    public int hashCode() {
        // Combina os hash codes das três coordenadas, para a performance do HashMap ser melhor
        return Objects.hash(x, y, z);
    }
}