public class Main {
    public static void main(String[] args){
        String dentro_ou_fora;
        Robo marcos = new Robo("Marcos", "Sul", 0, 0);   //Criar um objeto do tipo Robo;
        Ambiente caatinga = new Ambiente("Caatinga", 10, 10);    // Criar um objeto do tipo Ambiente;

        marcos.mover(5, 5);     //testar o método mover
        if (caatinga.dentroDosLimites(caatinga.getLimites()[0], caatinga.getLimites()[1])){
            dentro_ou_fora = "dentro";
        }else{
            dentro_ou_fora = "fora";
        }

        System.out.println("Posição do Robô: (" +  marcos.getPosicao()[0] + " , " + marcos.getPosicao()[1] + ")");   //imprimir
        System.out.println("O " + marcos.getNome() + " está " + dentro_ou_fora + " da " + caatinga.getNomeAmbiente());
    }   
 }