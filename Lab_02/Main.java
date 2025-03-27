import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String sistenaOperacional = System.getProperty("os.name").toLowerCase();
        int comando = 1;

        System.out.println("Saudações! meu nome é ClapTrap e eu serei seu servidor hoje nesse magnífico sistema de simulação nem um pouco quebrado e feito por especialistas renomados! Antes de começarmos precisamos de um espaço para trabalhar, de preferência algo agradável para aproveitar num domingo à noite tomando uma bela dose de óleo de motor\nNome do Ambiente: ");
        String nomeAmbiente = scanner.nextLine();
        System.out.println("Largura: ");
        int x = scanner.nextInt();
        System.out.println("Altura: ");
        int z = scanner.nextInt();
        System.out.println("Profundidade: ");
        int y = scanner.nextInt();
        Ambiente ambiente = new Ambiente(nomeAmbiente, x, y, z); //Cria seu novo ambiente
        while (comando != 0){ //Cria um looping para as ações possíveis
            try {
                if (sistenaOperacional.contains("win")){
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); //Da clear no cmd
                }else{
                    System.out.print("\033[H\033[2J"); //Da clear no terminal no caso de Linux e MacOS depois de cada interação
                    System.out.flush();
                }
            } catch (Exception e) {
                System.out.println("\n".repeat(50)); //No caso de erro ele apenas "limpa" o terminal printando diversas vezes uma quebra de linha
            }

            System.out.print("Digite um comando: \n0 - Encerrar\n1 - Criar um Robô\n2 - Controlar um Robô\n3 - Verificar lista de Robôs\n");
            comando = scanner.nextInt();
            if (comando == 0){ //Encerra o programa
                break;
            }else if (comando == 1){ //Bloco para criação de robôs
                System.out.println("Maravilha! Que tipo de Robô você quer criar?\n1 - Áereo\n2 - Terrestre\n");
                comando = scanner.nextInt();

                //Lista de mensagens que vão ser passadas caso o nome escolhido já exista
                ArrayList<String> mensagensNomeJaExistente = new ArrayList<>();  
                mensagensNomeJaExistente.add("Oooof... que azar, parece que alguém teve a mesma ideia que você, por quê não tenta de novo?\n");
                mensagensNomeJaExistente.add("É... esse também não vai tá podendo :/\n");
                mensagensNomeJaExistente.add("Seu dia de sorte! você tem um sósia por aí e ele já escolheu esse nome! Infelizmente ele não está disponível para contato então você vai ter que escolher outro nome\n");
                mensagensNomeJaExistente.add("Amigo, já pensou em fazer uma aula de processo criativo? Você tá precisando, escolhe outro nome aí\n");
                mensagensNomeJaExistente.add("Hmm... é eu acho que já ouvi esse nome, não gosto muito dele, que tal escolher outro?\n");
                mensagensNomeJaExistente.add("naspu' duj ra'pu'bogh  eita, foi mal, escolhe outro nome aí... de preferência bem rápido\n");

                if (comando == 1){
                    System.out.println("Bleh, odeio essas pestes infernizando nossos áres... tá, como você quer que ele seja?\n1 - Drone\n2 - Pássaro");
                    comando = scanner.nextInt();
                    if (comando == 1){
                        System.out.println("É... pelo menos esse daí é útil para algo, tá, agora só preciso saber as informações finais do seu Robô\nNome: ");    
                        criaRoboAereo(scanner, mensagensNomeJaExistente, ambiente, 0);
                    }else if (comando == 2){
                        System.out.println("Eu desisto, faz o que você quiser aí...\nNome: ");
                        criaRoboAereo(scanner, mensagensNomeJaExistente, ambiente, 1);
                    }
                }else if (comando == 2){
                    System.out.println("Mais um rastejador, Ótimo! Como você quer que ele seja?\n1 - Aspirador\n2 - Rover");   
                    comando = scanner.nextInt();
                    if (comando == 1){
                        System.out.println("Adoro esses pestinhas! como você quer caracterizar sua criaturinha?\n Nome: ");
                        criaRoboTerrestre(scanner, mensagensNomeJaExistente, ambiente, 0);
                    }else if (comando == 2){
                        System.out.println("Interessante... um amante de Rovers é raro hoje em dia. Bom, escolha como você quer que a gente crie ele\nNome: ");
                        criaRoboTerrestre(scanner, mensagensNomeJaExistente, ambiente, 1);
                    }
                }
            }else if (comando == 2){ //Bloco para Testar os métodos dos robôs
                System.out.println("Vamos ver do que esses pequeninos são capazes. Mas antes escolha com qual deles você quer se divertir agora");
                int contadorRobo = 1;
                for (Robo robo : ambiente.getLista()){
                    System.out.println(contadorRobo + " - " + robo.getNome());
                    contadorRobo++;
                }
                comando = scanner.nextInt();
                if (ambiente.getLista().get(comando - 1) instanceof Aspirador){ //Mostra os métodos do robô aspirador
                    System.out.println("Vamos fazer uma limpa nesse lugar hehehe");
                    
                }else if (ambiente.getLista().get(comando - 1) instanceof Drone){ //Mostra os métodos do robô drone
                    System.out.println("A única coisa boa com esse daí é entregar novos rastejantes");
                }else if (ambiente.getLista().get(comando - 1) instanceof Passaro){ //Mostra os métodos do robô passaro
                    System.out.println("Sinceramente eu nem sei porque os criadores desenvolveram esses daí");
                }else if (ambiente.getLista().get(comando - 1) instanceof Rover){ //Mostra os métodos do robô rover
                    System.out.println("Ele me lembra um carinha de um filme antigo... não consigo lembrar qual é");
                }

            }else if (comando == 3){ //Bloco para mostrar a lista de robôs
                if (ambiente.getLista().size() == 0){
                    
                }else{
                    System.out.println("Vamos dar uma olhada em quem você já criou até agora");
                    int contadorRobo = 1;
                    for (Robo robo : ambiente.getLista()) {
                        System.out.println(contadorRobo + " - " + robo.getNome());
                    }
                    System.out.println("Mas calma lá marujo isso daqui é so pra vizualização, se quiser fazer algo com eles você vai precisar ver as ações possíveis com cada um deles na simulação");
                }
            }
        }
        scanner.close();
    }   

    public static String exibirMensagemAleatoria(Scanner scanner, ArrayList<String> mensagensNomeJaExistente, Ambiente ambiente){ //Função que será chamada toda vez que o usuário tentar criar um robô cujo nome já existe
        Random random = new Random();
        String nome = scanner.nextLine();
        while (true){
            boolean nomeExiste = false;
            for (Robo robo : ambiente.getLista()) {
                if (robo.getNome().equals(nome)){ //Printa uma das mensagens da lista de mensagens possíveis de erro de nome já existente
                    String mensagemMostrada = mensagensNomeJaExistente.get(random.nextInt(mensagensNomeJaExistente.size()));
                    System.out.println(mensagemMostrada);
                    nome = scanner.nextLine();
                    nomeExiste = true;
                    break;
                } 
            }
            if (!nomeExiste){ //Quando tiver analisado todos os nomes na lista de robôs ativos, pode prosseguir na criação do robô
                break;
            }
        }
        return nome;
    }

    public static void criaRoboAereo(Scanner scanner,ArrayList<String> mensagensNomeJaExistente, Ambiente ambiente, int tipoRobo){ //Função para criação de robôs aéreos
        String nomeRoboAereo = exibirMensagemAleatoria(scanner, mensagensNomeJaExistente, ambiente); //Analisa se o nome escolhido já existe
        System.out.println("Direção: \n");
        String direcao = scanner.nextLine();
        int[] coordenadas = lerCoordenadas(scanner, true);
        if (tipoRobo == 0){
            Drone drone = new Drone(nomeRoboAereo, direcao, coordenadas[0], coordenadas[1], coordenadas[2]);
            ambiente.adicionarRobo(drone);   
        }else if (tipoRobo == 1){
            Passaro passaro = new Passaro(nomeRoboAereo, direcao, coordenadas[0], coordenadas[1], coordenadas[2]);
            ambiente.adicionarRobo(passaro);
        }
    }

    public static void criaRoboTerrestre(Scanner scanner, ArrayList<String> mensagensNomeJaExistente, Ambiente ambiente, int tipoRobo){ //Função para criação de robôs terrestres
        String nomeRoboTerrestre = exibirMensagemAleatoria(scanner, mensagensNomeJaExistente, ambiente); //Analisa se o nome escolhido já existe
        System.out.print("Direção: \n");
        String direcao = scanner.nextLine();
        int[] coordenadas = lerCoordenadas(scanner, false);
        System.out.print("Velocidade Máxima: \n");
        int velMax = scanner.nextInt();
        if (tipoRobo == 0){
            Aspirador aspirador = new Aspirador(nomeRoboTerrestre, direcao, coordenadas[0], coordenadas[1], velMax, ambiente);
            ambiente.adicionarRobo(aspirador);
        }else if (tipoRobo == 1){
            Rover rover = new Rover(nomeRoboTerrestre, direcao, coordenadas[0], coordenadas[1], velMax);
            ambiente.adicionarRobo(rover);
        }
    }

    public static int[] lerCoordenadas(Scanner scanner, boolean roboVoador){ //Função para ler as coordenadas para não precisar repetir esse trecho do código várias vezes
        System.out.print("Posição X: \n");
        int posicaoX = scanner.nextInt();
        System.out.print("Posição Y: \n");
        int posicaoY = scanner.nextInt();
        int posicaoZ = 0;
        if (roboVoador){ //Se o robô for voador inclui o eixo z
            System.out.println("Posição Z: \n");
            posicaoZ = scanner.nextInt();
        }
        return new int[]{posicaoX, posicaoY, posicaoZ};
    }
 }