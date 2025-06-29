package missions;
import ambiente.Ambiente;
import robot.Robo;

public interface Missao {
    void executar(Robo r, Ambiente a);
}