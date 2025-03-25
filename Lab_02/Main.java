import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int comando = 1;

        System.out.println("No começo de tudo, crie seu ambiente!\nNome: ");
        String nomeAmbiente = scanner.nextLine();
        System.out.println("Largura: \n");
        int x = scanner.nextInt();
        System.out.println("Altura: \n");
        int z = scanner.nextInt();
        System.out.println("Profundidade: \n");
        int y = scanner.nextInt();
        Ambiente ambiente = new Ambiente(nomeAmbiente, x, y, z); //Cria seu novo ambiente
        while (comando != 0){ //Cria um looping para as ações possíveis
            System.out.print("\033[H\033[2J"); //Da clear no terminal depois de cada interação
            System.out.flush();

            System.out.print("Digite um comando: \n0 - Encerrar\n1 - Criar um Robô\n2 - Controlar um Robô\n3 - Verificar lista de Robôs\n");
            comando = scanner.nextInt();
            if (comando == 0){
                break;
            }else if (comando == 1){
                System.out.println("Maravilha! Que tipo de Robô você quer criar?\n1 - Áereo\n2 - Terrestre\n");
                comando = scanner.nextInt();
                if (comando == 1){
                    System.out.println("Bleh, odeio essas pestes infernizando nossos áres... tá, como você quer que ele seja?\n1 - Drone\n2 - Pássaro\n");
                    comando = scanner.nextInt();
                    if (comando == 1){
                        System.out.println("É... pelo menos esse daí é útil para algo, tá, agora só preciso saber as informações finais do seu Robô\nNome: \n");    
                        String nomeDrone = scanner.nextLine();
                        System.out.println("Direção: \n");
                        String direcaoDrone = scanner.nextLine();
                        System.out.println("Posição X: \n");
                        int posicaoXdrone = scanner.nextInt();
                        System.out.println("Posição Y: \n");
                        int posicaoYdrone = scanner.nextInt();
                        System.out.println("Posição Z: \n");
                        int posicaoZdrone = scanner.nextInt();
                        Drone drone = new Drone(nomeDrone, direcaoDrone, posicaoXdrone, posicaoYdrone, posicaoZdrone);
                    }else if (comando == 2){
                        System.out.println("Eu desisto, faz o que você quiser aí...\nNome: \n");
                        String nomePassaro = scanner.nextLine();
                        System.out.println("Direção: \n");
                        String direcaoPassaro = scanner.nextLine();
                        System.out.println("Posição X: \n");
                        int posicaoXpassaro = scanner.nextInt();
                        System.out.println("Posição Y: \n");
                        int posicaoYpassaro = scanner.nextInt();
                        System.out.println("Posição Z: \n");
                        int posicaoZpassaro = scanner.nextInt();
                        Passaro passaro = new Passaro(nomePassaro, direcaoPassaro, posicaoXpassaro, posicaoYpassaro, posicaoZpassaro);
                    }
                }else if (comando == 2){
                    System.out.println("Mais um rastejador, Ótimo! Como você quer que ele seja?\n1 - Aspirador\n2 - Rover\n");   
                    comando = scanner.nextInt();
                    if (comando == 1){
                        System.out.println("Adoro esses pestinhas! como você quer caracterizar sua criaturinha?\n Nome: \nDireção: \nPosição X: \nPosição Y:\nVelocidade Máxima: \n");
                        String nomeAspirador = scanner.nextLine();
                        String direcaoAspirador = scanner.nextLine();
                        int posicaoXaspirador = scanner.nextInt();
                        int posicaoYaspirador = scanner.nextInt();
                        int velMaxAspirador = scanner.nextInt();
                        Aspirador aspirador = new Aspirador(nomeAspirador, direcaoAspirador, posicaoXaspirador, posicaoYaspirador, velMaxAspirador, ambiente);
                    }else if (comando == 2){
                        System.out.println("Interessante... um amante de Rovers é raro hoje em dia, bom escolha como você quer que a gente crie ele\nNome: \nDireção: \nPosição X: \nPosição Y: \nVelocidade Máxima: \n");
                        String nomeRover = scanner.nextLine();
                        String direcaoRover = scanner.nextLine();
                        int posicaoXrover = scanner.nextInt();
                        int posicaoYrover = scanner.nextInt();
                        int velMaxRover = scanner.nextInt();
                        Rover rover = new Rover(nomeRover, direcaoRover, posicaoXrover, posicaoYrover, velMaxRover);
                    }
                }
            }else if (comando == 2){
                System.out.println("Vamos ver do que esses pequeninos são capazes, mas antes escolha com qual deles você quer se divertir agora\n");
                int contadorRobo = 1;
                for (Robo robo : ambiente.getLista()){
                    System.out.println(contadorRobo + " - " + robo.getNome());
                }
            }else if (comando == 3){

            }
        }
        scanner.close();
    }   

    
 }