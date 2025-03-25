import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int comando;

        System.out.println("No começo de tudo, crie seu ambiente!\nNome: \n Largura: \nAltura: \nProfundidade: ");
        String nomeAmbiente = scanner.nextLine();
        int x = scanner.nextInt();
        int z = scanner.nextInt();
        int y = scanner.nextInt();
        Ambiente ambiente = new Ambiente(nomeAmbiente, x, y, z); //Cria seu novo ambiente
        while (comando != 0){ //Cria um looping para as ações possíveis
            System.out.print("\033[H\033[2J"); //Da clear no terminal depois de cada interação
            System.out.flush();

            System.out.print("Digite um comando: \n0 - Encerrar\n1 - Criar um Robô\n2 - Controlar um Robô\n3 - Verificar lista de Robôs");
            comando = scanner.nextInt();
            if (comando == 0){
                break;
            }else if (comando == 1){
                System.out.println("Maravilha! Que tipo de Robô você quer criar?\n1 - Áereo\n2 - Terrestre\n");
                comando = scanner.nextInt();
                if (comando == 1){
                    System.out.println("Bleh, odeio essas pestes infernizando nossos áres... Ok, como você quer que ele seja?\n1 - Drone\n2 - Pássaro\n");
                    comando = scanner.nextInt();
                    if (comando == 1){
                        System.out.println("É... pelo menos esse daí é útil para algo, tá, agora só preciso saber as informações finais do seu Robô\nNome: \nDireção: \nPosição X: \n");    
                        String nomeDrone = scanner.nextLine();
                        String direcao = scanner.nextLine();
                        int posicaoXdrone = scanner.nextInt();
                        int posicaoYdrone = scanner.nextInt();
                        int posicaoZdrone = scanner.nextInt();
                        Drone drone = new Drone(nomeDrone, direcao, posicaoXdrone, posicaoYdrone, posicaoZdrone);
                    }else if (comando == 2){
                        System.out.println("Eu desisto, faz o que você quiser aí...\n1 - Nome\n2 - ");

                        Passaro passaro = new Passaro(nomeAmbiente, nomeAmbiente, x, y, y)
                    }
                }else if (comando == 2){
                    System.out.println("Mais um rastejador, Ótimo! Como você quer que ele seja?\n1 - Aspirador\n2 - Rover\n");   
                    comando = scanner.nextInt();
                    if (comando == 1){

                    }else if (comando == 2){

                    }
                }
            }else if (comando == 2){

            }else if (comando == 3){

            }
        }
    }   
 }