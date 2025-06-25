package missions;
import ambiente.Ambiente;
import robots.Robo;

public interface Missao {
    void executar(Robo r, Ambiente a);
}