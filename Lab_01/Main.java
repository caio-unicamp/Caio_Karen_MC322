public class Main {
    public static void main(String[] args){

        Robo marcos = new Robo("Marcos");   //Criar um objeto do tipo Robo;
        Ambiente caatinga = new Ambiente("Caatinga", 10, 10);    // Criar um objeto do tipo Ambiente;

        marcos.mover(5, 5);     //testar o método mover
        caatinga.dentroDosLimites(caatinga.largura, caatinga.altura);
        System.out.println("Posição do Robô: " +  marcos.getPosicao() + " e " + marcos.getPosicao());   //imprimir

    }   
 }