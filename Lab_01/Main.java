public class Main 
{
    public static void main(String[] args){

        Robo marcos = new Robo("Marcos");   //Criar um objeto do tipo Robo;
        Ambiente Caating = new Ambiente("Caatinga, 10, 10");    // Criar um objeto do tipo Ambiente;


        marcos.mover(5, 5);     //testar o método mover

        system.out.printf("Posição do Robô: %d", marcos.exibirPosicao());   //imprimir


    }   


 }