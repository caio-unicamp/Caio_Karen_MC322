import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String sistemaOperacional = System.getProperty("os.name").toLowerCase();
        int comando = 1;

        System.out.println("Saudações! meu nome é ClapTrap e eu serei seu servidor hoje nesse magnífico sistema de simulação nem um pouco quebrado e feito por especialistas renomados! Antes de começarmos precisamos de um espaço para trabalhar, de preferência algo agradável para aproveitar num domingo à noite tomando uma bela dose de óleo de motor. Ah e só mais uma coisinha, eu imploro que quando pedirmos por números você não digite um nome, e se mesmo assim você teimar com isso, eu sei onde você mora...\nNome do Ambiente: ");
        String nomeAmbiente = scanner.nextLine();

        int x = lerInteiro("Largura: ", scanner);
        int z = lerInteiro("Altura: ", scanner);
        int y = lerInteiro("Profundidade: ", scanner);

        Ambiente ambiente = new Ambiente(nomeAmbiente, x, y, z); //Cria seu novo ambiente
        System.out.println("Parabéns, agora você é o prefeito da majestosa " + ambiente.getNomeAmbiente());
        while (comando != 0){ //Cria um looping para as ações possíveis
            scanner.nextLine(); //Ignora a quebra de Linha
            System.out.println("Digite enter para continuarmos");
            scanner.nextLine(); //Espera o usuário digitar enter antes de apagar o terminal
            limparTela(sistemaOperacional); //Chama a função que limpa o terminal

            comando = lerInteiro("Digite um comando: \n0 - Encerrar\n1 - Criar um Robô\n2 - Controlar um Robô\n3 - Verificar lista de Robôs", scanner);
            if (comando == 0){ //Encerra o programa
                break;
            }else if (comando == 1){ //Bloco para criação de robôs
                System.out.println("Maravilha! Que tipo de Robô você quer criar?\n1 - Áereo\n2 - Terrestre");
                comando = scanner.nextInt();

                //Lista de mensagens que vão ser passadas caso o nome escolhido já exista
                ArrayList<String> mensagensNomeJaExistente = new ArrayList<>();  
                mensagensNomeJaExistente.add("Oooof... que azar, parece que alguém teve a mesma ideia que você, por quê não tenta de novo?");
                mensagensNomeJaExistente.add("É... esse também não vai tá podendo :/");
                mensagensNomeJaExistente.add("Seu dia de sorte! você tem um sósia por aí e ele já escolheu esse nome! Infelizmente ele não está disponível para contato então você vai ter que escolher outro nome");
                mensagensNomeJaExistente.add("Amigo, já pensou em fazer uma aula de processo criativo? Você tá precisando, escolhe outro nome aí");
                mensagensNomeJaExistente.add("Hmm... é eu acho que já ouvi esse nome, não gosto muito dele, que tal escolher outro?");
                mensagensNomeJaExistente.add("naspu' duj ra'pu'bogh  eita, foi mal, escolhe outro nome aí... de preferência bem rápido");

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
                        System.out.println("Adoro esses pestinhas! como você quer caracterizar sua criaturinha?\nNome: ");
                        criaRoboTerrestre(scanner, mensagensNomeJaExistente, ambiente, 0);
                    }else if (comando == 2){
                        System.out.println("Interessante... um amante de Rovers é raro hoje em dia. Bom, escolha como você quer que a gente crie ele\nNome: ");
                        criaRoboTerrestre(scanner, mensagensNomeJaExistente, ambiente, 1);
                    }
                }
            }else if (comando == 2){ //Bloco para Testar os métodos dos robôs
                System.out.println("Vamos ver do que esses pequeninos são capazes. Mas antes escolha com qual deles você quer se divertir agora");
                int i = 1;
                for (Robo robo : ambiente.getLista()){
                    System.out.println(i++ + " - " + robo.getNome());
                }
                comando = scanner.nextInt();
                Robo roboEscolhido = ambiente.getLista().get(comando - 1);
                if (roboEscolhido instanceof Aspirador){ //Mostra os métodos do robô aspirador
                    System.out.println("Vamos fazer uma limpa nesse lugar hehehe");    
                    Aspirador aspirador = ((Aspirador) roboEscolhido);
                    System.out.println("Você deseja mover para quais coordenadas? Lembre-se que destruirá todos os robôs no caminho\nPassos em x:");
                    int deltaX = scanner.nextInt(); 
                    System.out.println("Passos em y:");
                    int deltaY = scanner.nextInt();
                    aspirador.mover(deltaX, deltaY);
                    //pegar a qtd de robos eliminados
                    int qtdEliminados = aspirador.getRobosEliminados();
                    //imprimir a qtd de eliminados
                    System.out.println("Você destruiu " + qtdEliminados + " robôs no caminho");

                }else if (roboEscolhido instanceof Drone){ //Mostra os métodos do robô drone
                    System.out.println("A única coisa boa com esse daí é entregar novos rastejantes");
                    Drone drone = ((Drone) roboEscolhido);
                    scanner.nextLine();
                    System.out.println("Que nome você quer dar para esse recém nascido que está por chegar?");
                    String nomePacote = scanner.nextLine();
                    int coordenadaX = lerInteiro("Você deseja entregar seu pacote para quais coordenadas?\nCoordenada em x: ", scanner);
                    int coordenadaY = lerInteiro("Coordenada em Y: ", scanner);
                    scanner.nextLine(); //Ignora a quebra de linha
                    //se o drone conseguiu entregar o pacote
                    if (drone.entregarPacote(coordenadaX, coordenadaY, nomePacote)){
                        System.out.println("O " + nomePacote +" pacote foi entregue com sucesso!");
                    }
                    //se o drone não consegiu entregar o pacote
                    else{
                        System.out.println("Seu pacote foi derrubado no caminho...que decepção... Atualmente o " + nomePacote + " está nas coordenadas: " + drone.getPosicao()[0] + ", " + drone.getPosicao()[1] + ", " + 0 + " Tá esperando o que? VAI VER SE ELE TÁ BEM!");
                        int verifica = lerInteiro("1 - Estou indo ver ele agora\n2 - Sou mal caráter e vou ignorá-lo", scanner);
                        if (verifica == 1){
                            System.err.println("É bom mesmo...");
                        }else{
                            System.out.println("Estou gostando cada vez menos de você");
                        }
                    }
                } else if (roboEscolhido instanceof Passaro){ //Mostra os métodos do robô passaro
                    System.out.println("Sinceramente eu nem sei porque os criadores desenvolveram esses daí");
                    Passaro passaro = ((Passaro) roboEscolhido);
                    //mover o pássaro
                    System.out.println("você quer mover para onde?\nPassos em x:");
                    int deltaX = scanner.nextInt(); 
                    System.out.println("Passos em y:");
                    int deltaY = scanner.nextInt();
                    passaro.mover(deltaX, deltaY);
                    //pegar a qtd de desvios
                    int qtdDesvios = passaro.getQtddesvios();
                    //imprimir a qtd de desvios
                    System.out.println("Você fez " + qtdDesvios + " desvios no caminho");

                }else if (roboEscolhido instanceof Rover){ //Mostra os métodos do robô rover
                    System.out.println("Ele me lembra um carinha de um filme antigo... não consigo lembrar qual é");
                    Rover rover = ((Rover) roboEscolhido);
                    //mover o Rover
                    System.out.println("Você quer mover para onde?\nPassos  em x: ");
                    int deltaX = scanner.nextInt(); 
                    System.out.println("Passos em y:");
                    int deltaY = scanner.nextInt();
                    rover.mover(deltaX, deltaY);
}

            } else if (comando == 3){ //Bloco para mostrar a lista de robôs
                if (ambiente.getLista().size() == 0){
                    System.out.println("Oops, parece que você ainda não criou nenhum robô");
                } else{
                    System.out.println("Vamos dar uma olhada em quem você já criou até agora");
                    int contadorRobo = 1;
                    for (Robo robo : ambiente.getLista()) {
                        System.out.println(contadorRobo++ + " - " + robo.getNome());
                    }
                    System.out.println("Mas calma lá marujo isso daqui é so pra vizualização, se quiser fazer algo com eles você vai precisar ver as ações possíveis com cada um deles na simulação");
                }
            }
        }
        scanner.close();
    }   
    //quando já tem o nome que o usuário quer colocar no Robô que está criando, na lista de Robôs
    public static String exibirMensagemAleatoria(Scanner scanner, ArrayList<String> mensagensNomeJaExistente, Ambiente ambiente){ //Função que será chamada toda vez que o usuário tentar criar um robô cujo nome já existe
        Random random = new Random();
        scanner.nextLine();
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
        System.out.println("Direção: ");
        String direcao = scanner.nextLine();
        int[] coordenadas = lerCoordenadas(scanner, true);
        if (tipoRobo == 0){
            System.out.print("Tempo de locomoção do pacote: ");
            int tempoLocomocaoTerrestre = scanner.nextInt();
            Drone drone = new Drone(nomeRoboAereo, direcao, coordenadas[0], coordenadas[1], coordenadas[2], ambiente, tempoLocomocaoTerrestre);
            ambiente.adicionarRobo(drone);   
            System.out.println("É... você criou um rover pelo menos! E tem esse " + drone.getNome() + " também... yay");
        }else if (tipoRobo == 1){
            Passaro passaro = new Passaro(nomeRoboAereo, direcao, coordenadas[0], coordenadas[1], coordenadas[2], ambiente);
            ambiente.adicionarRobo(passaro);
            System.out.println("Meus superiores dizem que eu deveria dizer parabéns por ter criado o " + passaro.getNome() +" nesse ponto, mas eu me recuso.");
        }
    }

    public static void criaRoboTerrestre(Scanner scanner, ArrayList<String> mensagensNomeJaExistente, Ambiente ambiente, int tipoRobo){ //Função para criação de robôs terrestres
        String nomeRoboTerrestre = exibirMensagemAleatoria(scanner, mensagensNomeJaExistente, ambiente); //Analisa se o nome escolhido já existe
        System.out.print("Direção: ");
        String direcao = leDirecao(scanner);
        int[] coordenadas = lerCoordenadas(scanner, false);
        System.out.print("Velocidade Máxima: ");
        int velMax = scanner.nextInt();
        System.out.println("Tempo de Locomoção: ");
        int tempoLocomocaoTerrestre = scanner.nextInt();
        if (tipoRobo == 0){
            Aspirador aspirador = new Aspirador(nomeRoboTerrestre, direcao, coordenadas[0], coordenadas[1], velMax, ambiente, tempoLocomocaoTerrestre);
            ambiente.adicionarRobo(aspirador);
            System.out.println("VAMOOOOOOOOOOOOOO!!! Você é incrível por ter criado o " + aspirador.getNome());
        }else if (tipoRobo == 1){
            Rover rover = new Rover(nomeRoboTerrestre, direcao, coordenadas[0], coordenadas[1], velMax, ambiente, tempoLocomocaoTerrestre);
            ambiente.adicionarRobo(rover);
            System.out.println("Meus olhos se enchem de óleo toda vez, o " + rover.getNome() + " é tão lindo");
        }
    }

    public static int[] lerCoordenadas(Scanner scanner, boolean roboVoador){ //Função para ler as coordenadas para não precisar repetir esse trecho do código várias vezes
        int posicaoX = lerInteiro("Posição X: ", scanner);
        int posicaoY = lerInteiro("Posição Y: ", scanner);
        int posicaoZ = 0;
        if (roboVoador){ //Se o robô for voador inclui o eixo z
            posicaoZ = lerInteiro("Posição Z: ", scanner);
        }
        return new int[]{posicaoX, posicaoY, posicaoZ};
    }

    public static int lerInteiro(String mensagem, Scanner scanner) { //Função para excessões de inteiros lidos como string
        Random random = new Random();
        int numAleatorio = random.nextInt(10);
        int valor;
        while (true) { // Continua pedindo até o usuário digitar corretamente
            System.out.println(mensagem);
            if (scanner.hasNextInt()) {
                valor = scanner.nextInt();
                return valor;
            }else if (numAleatorio == 5) {
                System.out.println("Olha, eu até podia fazer algo para que você conseguisse continuar mesmo ignorando completamente o que eu falei no começo sobre digitar nomes em vez de números, mas hoje eu estou de mal humor então só por isso você vai ter que recomeçar tudo de novo XP");
                System.exit(0);
            }else {
                System.out.println("Engraçadinho você, vou deixar passar dessa vez... Digite um NÚMERO INTEIRO: ");
                scanner.nextLine(); // Descarta a entrada inválida
            }
        }
    }

    public static String leDirecao(Scanner scanner){ //Função para ficar pedindo a direção até o usuário digitar corretamente
        String nomeDirecao = scanner.nextLine();
        while (true){
            if (!nomeDirecao.equalsIgnoreCase("norte") || !nomeDirecao.equalsIgnoreCase("sul") || !nomeDirecao.equalsIgnoreCase("leste") || !nomeDirecao.equalsIgnoreCase("oeste")){
                System.out.println("Olha eu achei que não precisaria explicar isso, mas entendo que o cerébro inferior dos seres humanos às vezes não pega informações implícitas tão rapidamente, mas quando eu digo direção é tipo... norte, sul, leste e oeste. Então é, vamo tentar de novo?");
                nomeDirecao = scanner.nextLine();
            }
        }
    }

    public static void limparTela(String sistemaOperacional){ //Função para limpar o terminal após algumas interações
        try {
            if (sistemaOperacional.contains("win")){
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); //Da clear no cmd usando cls
            }else{
                System.out.print("\033[H\033[2J"); //Da clear no terminal no caso de Linux e MacOS depois de cada interação
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("\n".repeat(50)); //No caso de erro ele apenas "limpa" o terminal printando diversas vezes uma quebra de linha
        }
    }
 }