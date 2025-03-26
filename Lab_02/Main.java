import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int comando = 1;

        System.out.println("Saudações! meu nome é Chappie e eu serei seu servidor hoje nesse magnífico sistema de simulação nem um pouco quebrado e feito por especialistas renomados! Antes de começarmos precisamos de um espaço para trabalhar, de preferência algo agradável para aproveitar num domingo à noite tomando uma bela dose de óleo de motor\nNome do Ambiente: ");
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
            }else if (comando == 1){ //Bloco para criação de robôs
                System.out.println("Maravilha! Que tipo de Robô você quer criar?\n1 - Áereo\n2 - Terrestre\n");
                comando = scanner.nextInt();

                //Lista de mensagens que vão ser passadas caso o nome escolhido já exista
                ArrayList<String> mensagensNomeJaExistente = new ArrayList<>();  
                mensagensNomeJaExistente.add("É... esse também não vai tá podendo :/\n");
                mensagensNomeJaExistente.add("Seu dia de sorte! você tem um sósia por aí e ele já escolheu esse nome! Infelizmente ele não está disponível para contato então você vai ter que escolher outro nome\n");
                mensagensNomeJaExistente.add("Amigo, já pensou em fazer uma aula de processo criativo? Você tá precisando, escolhe outro nome aí\n");
                mensagensNomeJaExistente.add("Hmm... é eu acho que já ouvi esse nome, não gosto muito dele, que tal escolher outro?\n");
                mensagensNomeJaExistente.add("yachtaHvIS wa' mIn, eita, foi mal, escolhe outro nome aí... de preferência bem rápido\n");
                mensagensNomeJaExistente.add("Oooof... que azar, parece que alguém teve a mesma ideia que você, por quê não tenta de novo?\n");

                if (comando == 1){
                    System.out.println("Bleh, odeio essas pestes infernizando nossos áres... tá, como você quer que ele seja?\n1 - Drone\n2 - Pássaro\n");
                    comando = scanner.nextInt();
                    if (comando == 1){
                        System.out.println("É... pelo menos esse daí é útil para algo, tá, agora só preciso saber as informações finais do seu Robô\nNome: \n");    
                        String nomeDrone = scanner.nextLine();
                        exibirMensagemAleatoria(mensagensNomeJaExistente, ambiente, nomeDrone); //Analisa se o nome escolhido já existe
                        System.out.println("Direção: \n");
                        String direcaoDrone = scanner.nextLine();
                        System.out.println("Posição X: \n");
                        int posicaoXdrone = scanner.nextInt();
                        System.out.println("Posição Y: \n");
                        int posicaoYdrone = scanner.nextInt();
                        System.out.println("Posição Z: \n");
                        int posicaoZdrone = scanner.nextInt();
                        Drone drone = new Drone(nomeDrone, direcaoDrone, posicaoXdrone, posicaoYdrone, posicaoZdrone);
                        ambiente.adicionarRobo(drone);
                    }else if (comando == 2){
                        System.out.println("Eu desisto, faz o que você quiser aí...\nNome: \n");
                        String nomePassaro = scanner.nextLine();
                        exibirMensagemAleatoria(mensagensNomeJaExistente, ambiente, nomePassaro); //Analisa se o nome escolhido já existe
                        System.out.println("Direção: \n");
                        String direcaoPassaro = scanner.nextLine();
                        System.out.println("Posição X: \n");
                        int posicaoXpassaro = scanner.nextInt();
                        System.out.println("Posição Y: \n");
                        int posicaoYpassaro = scanner.nextInt();
                        System.out.println("Posição Z: \n");
                        int posicaoZpassaro = scanner.nextInt();
                        Passaro passaro = new Passaro(nomePassaro, direcaoPassaro, posicaoXpassaro, posicaoYpassaro, posicaoZpassaro);
                        ambiente.adicionarRobo(passaro);
                    }
                }else if (comando == 2){
                    System.out.println("Mais um rastejador, Ótimo! Como você quer que ele seja?\n1 - Aspirador\n2 - Rover\n");   
                    comando = scanner.nextInt();
                    if (comando == 1){
                        System.out.println("Adoro esses pestinhas! como você quer caracterizar sua criaturinha?\n Nome: \n");
                        String nomeAspirador = scanner.nextLine();
                        exibirMensagemAleatoria(mensagensNomeJaExistente, ambiente, nomeAspirador); //Analisa se o nome escolhido já existe
                        System.out.print("Direção: \n");
                        String direcaoAspirador = scanner.nextLine();
                        System.out.print("Posição X: \n");
                        int posicaoXaspirador = scanner.nextInt();
                        System.out.print("Posição Y: \n");
                        int posicaoYaspirador = scanner.nextInt();
                        System.out.print("Velocidade Máxima: \n");
                        int velMaxAspirador = scanner.nextInt();
                        Aspirador aspirador = new Aspirador(nomeAspirador, direcaoAspirador, posicaoXaspirador, posicaoYaspirador, velMaxAspirador, ambiente);
                        ambiente.adicionarRobo(aspirador);
                    }else if (comando == 2){
                        System.out.println("Interessante... um amante de Rovers é raro hoje em dia, bom escolha como você quer que a gente crie ele\nNome: \n");
                        String nomeRover = scanner.nextLine();
                        exibirMensagemAleatoria(mensagensNomeJaExistente, ambiente, nomeRover); //Analisa se o nome escolhido já existe
                        System.out.print("Direção: \n");
                        String direcaoRover = scanner.nextLine();
                        System.out.print("Posição X: \n");
                        int posicaoXrover = scanner.nextInt();
                        System.out.print("Posição Y: \n");
                        int posicaoYrover = scanner.nextInt();
                        System.out.print("Velocidade Máxima: \n");
                        int velMaxRover = scanner.nextInt();
                        Rover rover = new Rover(nomeRover, direcaoRover, posicaoXrover, posicaoYrover, velMaxRover);
                        ambiente.adicionarRobo(rover);
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
    public static void exibirMensagemAleatoria(ArrayList<String> mensagensNomeJaExistente, Ambiente ambiente, String nome){ //Função que será chamada toda vez que o usuário tentar criar um robô cujo nome já existe
        Random random = new Random();
        while (true){
            int numDronesAnalisados = 0;
            for (Robo drone : ambiente.getLista()) {
                if (drone.getNome().equals(nome)){ //Printa uma das mensagens da lista de mensagens possíveis de erro de nome já existente
                    String mensagemMostrada = mensagensNomeJaExistente.get(random.nextInt(mensagensNomeJaExistente.size()));
                    System.out.println(mensagemMostrada);
                } 
                numDronesAnalisados++;
            }
            if (numDronesAnalisados == ambiente.getLista().size()){ //Quando tiver analisado todos os nomes na lista de robôs ativos, pode prosseguir na criação do robô
                break;
            }
        }
    }
 }