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
                comando = lerInteiro("Maravilha! Que tipo de Robô você quer criar?\n1 - Áereo\n2 - Terrestre", scanner);

                //Lista de mensagens que vão ser passadas caso o nome escolhido já exista
                ArrayList<String> mensagensNomeJaExistente = new ArrayList<>();  
                mensagensNomeJaExistente.add("Oooof... que azar, parece que alguém teve a mesma ideia que você, por quê não tenta de novo?");
                mensagensNomeJaExistente.add("É... esse também não vai tá podendo :/");
                mensagensNomeJaExistente.add("Seu dia de sorte! você tem um sósia por aí e ele já escolheu esse nome! Infelizmente ele não está disponível para contato então você vai ter que escolher outro nome");
                mensagensNomeJaExistente.add("Amigo, já pensou em fazer uma aula de processo criativo? Você tá precisando, escolhe outro nome aí");
                mensagensNomeJaExistente.add("Hmm... é eu acho que já ouvi esse nome, não gosto muito dele, que tal escolher outro?");
                mensagensNomeJaExistente.add("naspu' duj ra'pu'bogh  eita, foi mal, escolhe outro nome aí... de preferência bem rápido");

                if (comando == 1){
                    comando = lerInteiro("Bleh, odeio essas pestes infernizando nossos áres... tá, como você quer que ele seja?\n1 - Drone\n2 - Pássaro", scanner);
                    if (comando == 1){
                        System.out.println("É... pelo menos esse daí é útil para algo, tá, agora só preciso saber as informações finais do seu Robô\nNome: ");    
                        criaRoboAereo(scanner, mensagensNomeJaExistente, ambiente, 0);
                    }else if (comando == 2){
                        System.out.println("Eu desisto, faz o que você quiser aí...\nNome: ");
                        criaRoboAereo(scanner, mensagensNomeJaExistente, ambiente, 1);
                    }
                }else if (comando == 2){
                    comando = lerInteiro("Mais um rastejador, Ótimo! Como você quer que ele seja?\n1 - Aspirador\n2 - Rover", scanner);
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
                Robo roboEscolhido;
                while(true){
                    comando = lerInteiro("\0", scanner);
                    if (comando >= 1 && comando <= ambiente.getLista().size()){
                        roboEscolhido = ambiente.getLista().get(comando - 1);
                        break;
                    }else{
                        System.out.print("Talvez os números antes dos nomes dos robôs não estejam claros o suficiente, mas tipo, você tem que escolher um dos números dessa lista que eu te mostrei pra depois você escolher o que quer fazer com ele. Vamo lá então, escolha um dos números que está do lado esquerdo");
                    }
                }
                if (roboEscolhido instanceof Aspirador){ //Mostra os métodos do robô aspirador
                    System.out.println("Vamos fazer uma limpa nesse lugar hehehe");    
                    comando = lerInteiro("Você deseja fazer o quê?\n1 - Andar por aí\n2 - MATAR!\n3 - Alterar a Velocidade Máxima", scanner);
                    Aspirador aspirador = ((Aspirador) roboEscolhido);
                    if (comando == 1){ //Método de mover normal do Aspirador
                        System.out.println("Quanto você deseja mover ele? Lembre-se que destruirá todos os robôs no caminho, faça o que quiser com essa informação...");
                        while (true){
                            int deltaX = lerInteiro("Passos em x: ", scanner); 
                            int deltaY = lerInteiro("Passos em y: ", scanner);
                            if (!aspirador.velMaxAtingida(deltaX, deltaY)){
                                aspirador.mover(deltaX, deltaY);
                                //pegar a qtd de robos eliminados
                                int qtdEliminados = aspirador.getRobosEliminados();
                                //imprimir a qtd de eliminados
                                System.out.println("Até agora você destruiu " + qtdEliminados + " robôs na sua vida");
                                if (qtdEliminados > 10){
                                    System.out.println("Um tremendo massacre eu diria, chega a me assustar, o próximo pode ser eu");
                                }
                                break;
                            }else{ //Se ele tiver ultrapassado o limite de velocidade ele irá pedir novamente o quanto ele quer que o robô ande
                                System.out.println("Só porque ele é um aspirador isso não significa que ele consegue armazenar ar pra usar como propulsão. Vai ter que tentar mover ele de novo");
                            }
                        }
                    }else if (comando == 2){ //Para ir atás de aspirar um robô específico
                        System.out.println("EXCELSIOR! Nesse modo o aspirador fica insano e vai especificamente caçar um robô. Qual deles vai ser o azarado da vez");
                        while (true) {
                            int numRoboMatar = 1;
                            for (Robo robo : ambiente.getLista()){
                                System.out.println(numRoboMatar++ + " - " + robo.getNome() + " - " + robo.getClass().getSimpleName());       
                            }
                            comando = lerInteiro("\0", scanner);
                            if (ambiente.getLista().get(comando - 1) instanceof Aspirador){
                                System.out.println("Uau, você realmente quer eliminar um de seus semelhantes? Sinistro... mas tudo bem, eu não fui programado pra me importar com isso");
                                break;
                            }else if (ambiente.getLista().get(comando - 1).getPosicao()[2] == 0){
                                System.out.println("De fato eu concordo que seria bem melhor se ele conseguisse eliminar essas pragas aéreas, mas infelizmente ele não tem essa capacidade. Então vamos tentar de novo");
                            }
                            else{
                                break;
                            }
                        }
                        Robo roboEliminado = ambiente.getLista().get(comando - 1);
                        ambiente.removerRobo(roboEscolhido);
                        aspirador.setPosicao(roboEliminado.getPosicao()[0], roboEliminado.getPosicao()[1], 0); //Seta a posição do aspirador na posição do robô que ele vai eliminar
                        System.out.println("O " + roboEliminado.getNome() + " foi eliminado com sucesso!");
                        int qtdEliminados = aspirador.getRobosEliminados();
                        System.out.println("Até agora você destruiu " + qtdEliminados + " robôs na sua vida");
                        if (qtdEliminados > 10){
                            System.out.println("Um tremendo massacre eu diria, chega a me assustar, o próximo pode ser eu");
                        }
                    }else if (comando == 3){
                        System.out.println("Ah Senninha, você quer correr então? Então vamo tunar esse motor pra ele conseguir correr mais rápido");
                        int velMax = leVelocidade(scanner);
                        aspirador.setVelMaxima(velMax);
                    }
                }else if (roboEscolhido instanceof Drone){ //Mostra os métodos do robô drone
                    System.out.println("A única coisa boa com esse daí é entregar novos rastejantes");
                    Drone drone = ((Drone) roboEscolhido);
                    comando = lerInteiro("Você deseja fazer o que?\n1 - Subir\n2 - Descer\n3 - Entregar um Pacote", scanner);
                    if (comando == 1 || comando == 2){ //Subir ou descer o robô
                        metodosRobosAereos(drone, comando, scanner);
                    }else if (comando == 3){ //Entregar um pacote
                        System.out.println("Que nome você quer dar para esse recém nascido que está por chegar?");
                        String nomePacote = scanner.nextLine();
                        System.out.println("Você deseja entregar seu pacote para quais coordenadas?");
                        int coordenadaX;
                        int coordenadaY;
                        while (true){ //Analisa se tem algum robô já ocupando a posição que o pacote seria entregue
                            int contaCoordenadasRobos = 0;
                            coordenadaX = lerInteiro("Coordenada em x: ", scanner);
                            coordenadaY = lerInteiro("Coordenada em Y: ", scanner);
                            for (Robo robo : ambiente.getLista()) {
                                contaCoordenadasRobos++;
                                if ((coordenadaX == robo.getPosicao()[0] && coordenadaY == robo.getPosicao()[1] && robo.getPosicao()[2] == 0)){
                                    break;
                                }
                            }
                            if (contaCoordenadasRobos == ambiente.getLista().size()){
                                break;
                            }else{
                                System.out.println("Puts, que chato, aparentemente já tem alguém ocupando essa posição. Mas claro que se você quiser mandar ele pra lá eu não vou poder impedir");
                                while (true) {
                                    int verifica = lerInteiro("1 - Sim, eu quero que ele vá pra lá\n2 - Não, eu quero que ele vá pra outro lugar", scanner);
                                    if (verifica == 1){
                                        System.out.println("Ok, você quem é o chefe aqui, só não diga que eu não avisei");
                                        break;
                                    }else if (verifica == 2){
                                        System.out.println("Ótimo, você não é tão mal caráter assim, vamos tentar de novo");
                                        break;
                                    }else{ //Continua pedindo até ser dada uma opção válida
                                        System.out.println("Cara, é uma coisa tão simples, sério, é literalmente só escolher um dos números que eu mostrei... Eu não sou pago o suficiente pra isso");
                                    }
                                }
                            }
                        }
                        //se o drone conseguiu entregar o pacote
                        if (drone.entregarPacote(coordenadaX, coordenadaY, nomePacote)){
                            System.out.println("O " + nomePacote +" foi entregue com sucesso!");
                        }
                        //se o drone não consegiu entregar o pacote
                        else{
                            System.out.println("Seu pacote foi derrubado no caminho...que decepção... Atualmente o " + nomePacote + " está nas coordenadas: (" + drone.getPosicao()[0] + ", " + drone.getPosicao()[1] + ", " + 0 + ") Tá esperando o que? VAI VER SE ELE TÁ BEM!");
                            int verifica = lerInteiro("1 - Estou indo ver ele agora\n2 - Sou mal caráter e vou ignorá-lo", scanner);
                            if (verifica == 1){
                                System.err.println("É bom mesmo...");
                            }else{
                                System.out.println("Estou gostando cada vez menos de você");
                            }
                        }
                    }
                }else if (roboEscolhido instanceof Passaro){ //Mostra os métodos do robô passaro
                    System.out.println("Sinceramente eu nem sei porque os criadores desenvolveram esses daí");
                    Passaro passaro = ((Passaro) roboEscolhido);
                    comando = lerInteiro("Você deseja fazer o que?\n1 - Subir\n2 - Descer\n3 - mover que nem alguém normal (ou quase)", scanner);
                    if (comando == 1 || comando == 2){ //Subir ou descer o robô
                        metodosRobosAereos(passaro, comando, scanner);
                    }else if (comando == 3){
                        //mover o pássaro
                        System.out.println("Você quer mover para onde?");
                        int deltaX = lerInteiro("Passos em x: ", scanner); 
                        int deltaY = lerInteiro("Passos em y: ", scanner);
                        passaro.mover(deltaX, deltaY);
                        //pegar a qtd de desvios
                        int qtdDesvios = passaro.getQtddesvios();
                        //imprimir a qtd de desvios
                        System.out.println("Você fez " + qtdDesvios + " desvios no caminho");
                    }
                }else if (roboEscolhido instanceof Rover){ //Mostra os métodos do robô rover
                    System.out.println("Ele me lembra um carinha de um filme antigo... não consigo lembrar qual é");
                    Rover rover = ((Rover) roboEscolhido);
                    comando = lerInteiro("Você deseja fazer o que?\n1 - Andar por aí\n2 - Empurrar\n3 - Alterar a Velocidade Máxima", scanner);
                    if (comando == 1){ //Método normal de mover o  Rover
                        //mover o Rover
                        System.out.println("Você quer mover para onde?");
                        while (true){
                            int deltaX = lerInteiro("Passos em x: ", scanner); 
                            int deltaY = lerInteiro("Passos em y: ", scanner);
                            if (!rover.velMaxAtingida(deltaX, deltaY)){
                                rover.mover(deltaX, deltaY);
                            }else{ //Se o limite de velocidade for ultrapassado ele irá pedir novamente o quanto o usuário quer que o robô ande
                                System.out.println("Calma lá Flash! você tá querendo ir rápido demais com esse carinha, no estágio atual ele pode acabar quebrando. Você vai ter que tentar mover ele de novo");
                            }
                        }
                        System.out.println("Você empurrou "+ rover.);
                    }else if (comando == 2){ //Método para empurrar um robô específico
                        System.out.println("Parece que você tem uma richa com alguém, não é mesmo? Me diz quem é e a gente esbarra nele");
                        while (true) {
                            int numRoboEmpurrar = 1;
                            for (Robo robo : ambiente.getLista()){
                                System.out.println(numRoboEmpurrar++ + " - " + robo.getNome() + " - " + robo.getClass().getSimpleName());       
                            }
                            comando = lerInteiro("\0", scanner);
                            if (ambiente.getLista().get(comando - 1) instanceof Rover){
                                System.out.println("Uhhhh, um caso de família será? Me sinto assistindo uma novela");
                                break;
                            }else if (ambiente.getLista().get(comando - 1).getPosicao()[2] == 0){
                                System.out.println("Eu também teria ranço desses tipinhos aí, se elas chegarem perto do chão eu te aviso pra você barruar nelas");
                            }
                            else{
                                break;
                            }
                        }
                        rover.mover(ambiente.getLista().get(comando - 1).getPosicao()[0], ambiente.getLista().get(comando - 1).getPosicao()[1]); 
                        System.out.println("O + " + ambiente.getLista().get(comando - 1).getNome() + " recebeu o recado... ele não gostou muito, se eu fosse você eu dormiria de olho aberto essa noite");
                    }else if (comando == 3){
                        System.out.println("Ah Senninha, você quer correr então? Então vamo tunar esse motor pra ele conseguir correr mais rápido");
                        int velMax = leVelocidade(scanner);
                        rover.setVelMaxima(velMax);
                    }
                }

            }else if (comando == 3){ //Bloco para mostrar a lista de robôs
                if (ambiente.getLista().size() == 0){
                    System.out.println("Oops, parece que você ainda não criou nenhum robô");
                }else{
                    System.out.println("Vamos dar uma olhada em quem você já criou até agora");
                    int contadorRobo = 1;
                    for (Robo robo : ambiente.getLista()) {
                        System.out.println(contadorRobo++ + " - " + robo.getNome() + " Posição: " + robo.getPosicao()[0] + ", " + robo.getPosicao()[1] + ", " + robo.getPosicao()[2]);
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
        String direcao = leDirecao(scanner);
        int[] coordenadas = lerCoordenadas(scanner, true, ambiente);
        if (tipoRobo == 0){
            int tempoLocomocaoTerrestre = lerInteiro("Tempo de locomoção do pacote: ", scanner);
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
        int[] coordenadas = lerCoordenadas(scanner, false, ambiente);
        int velMax = leVelocidade(scanner);
        int tempoLocomocaoTerrestre = leTempo(scanner);
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

    public static int[] lerCoordenadas(Scanner scanner, boolean roboVoador, Ambiente ambiente){ //Função para ler as coordenadas para não precisar repetir esse trecho do código várias vezes
        boolean lugarOcupado = false;
        while (true){
            int posicaoX = lerInteiro("Posição X: ", scanner);
            int posicaoY = lerInteiro("Posição Y: ", scanner);
            int posicaoZ = 0;
            if (roboVoador){ //Se o robô for voador inclui o eixo z
                posicaoZ = lerInteiro("Posição Z: ", scanner);
            }
            int qtdAnalisesPosicoes = 0;
            for (Robo robo : ambiente.getLista()) {
                qtdAnalisesPosicoes++;
                if (posicaoX == robo.getPosicao()[0] && posicaoY == robo.getPosicao()[1] && posicaoZ == robo.getPosicao()[2]){
                    System.out.println("Há muito tempo atrás Sir Isaac Newton provou que dois corpos não podem ocupar o mesmo lugar no espaço. Parece que você matou essa aula na escola ein, o " + robo.getNome() + " já tá nessa posição, colega. então vamos tentar colocar o novo robô em outro canto");
                    lugarOcupado = true;
                    qtdAnalisesPosicoes = -1;
                    break;
                }
            }
            if (qtdAnalisesPosicoes == ambiente.getLista().size()){
                lugarOcupado = false;
            }
            if (!lugarOcupado){ 
                if (!ambiente.dentroDosLimites(posicaoX, posicaoY, posicaoZ)){
                    System.out.println("Eu sei que é difícil manter a memória em dia, principalmente com o tanto de informação que existe hoje em dia, mas assim, você que criou o espaço e os limites dele, como você espera que eu consiga criar um robô fora dos limites que você mesmo estabeleceu? Então por favor tenta colocar o robô dentro do ambiente dessa vez ;)");
                }else{
                    return new int[]{posicaoX, posicaoY, posicaoZ};
                }
            }
            
        }
    }

    public static int lerInteiro(String mensagem, Scanner scanner) { //Função para excessões de inteiros lidos como string
        Random random = new Random();
        int valor;
        while (true) { // Continua pedindo até o usuário digitar corretamente
            System.out.println(mensagem);
            if (scanner.hasNextInt()) {
                valor = scanner.nextInt();
                return valor;
            }else {
                int numAleatorio = random.nextInt(10);
                if (numAleatorio == 5) {
                System.out.println("Olha, eu até podia fazer algo para que você conseguisse continuar mesmo ignorando completamente o que eu falei no começo sobre digitar nomes em vez de números, mas hoje eu estou de mal humor então só por isso você vai ter que recomeçar tudo de novo XP");
                System.exit(0);
                }else {
                    System.out.println("Engraçadinho você, vou deixar passar dessa vez... Digite um NÚMERO INTEIRO: ");
                    scanner.nextLine(); // Descarta a entrada inválida
                }
            }
        }
    }

    public static String leDirecao(Scanner scanner){ //Função para ficar pedindo a direção até o usuário digitar corretamente
        String nomeDirecao = scanner.nextLine();
        while (true){
            if (!nomeDirecao.equalsIgnoreCase("norte") && !nomeDirecao.equalsIgnoreCase("sul") && !nomeDirecao.equalsIgnoreCase("leste") && !nomeDirecao.equalsIgnoreCase("oeste")){
                System.out.println("Olha eu achei que não precisaria explicar isso, mas entendo que o cerébro inferior dos seres humanos às vezes não pega informações implícitas tão rapidamente, mas quando eu digo direção é tipo... norte, sul, leste e oeste. Então é, vamo tentar de novo?");
                nomeDirecao = scanner.nextLine();
            }else{
                break;
            }
        }
        return nomeDirecao;
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

    public static int leVelocidade(Scanner scanner){
        int velMax = 0;
        while (true){ //Loop para analisar a velocidade máxima
            velMax = lerInteiro("Velocidade Máxima: ", scanner);
            if (velMax == 0){
                System.out.println("Confesso que eu tenho um certo apreço por foras da Lei, mas infelizmente eu fui programado de modo que eu devo pedir que você me diga o limite de velocidade do seu robô... mas olhe pelo lado bom, você ainda pode colocar seu limite de velocidade MUITO alto e eu vou permitir!");
            }else if (velMax < 0){
                System.out.println("Hmmm... um robô que faz moonwalk é uma boa ideia, vou passar isso para os meus superiores, mas por enquanto vamos ficar apenas com velocidades positivas ok?");
            }else{
                break;
            }
        }
        return velMax;
    }
    
    public static int leTempo(Scanner scanner){
        int tempoLocomocaoTerrestre = 0;
        while (true){ //Loop para analisar o tempo de locomoção
            tempoLocomocaoTerrestre = lerInteiro("Tempo de Locomoção: ", scanner);   
            if (tempoLocomocaoTerrestre == 0){
                System.out.println("É sério? tempo de locomoção 0? você sabe o que pode acontecer comigo se eu tentar fazer uma divisão por 0? Tô achando que você tá querendo me matar... A função de teletransporte ainda não está disponível nessa versão do simulador, então por que não tentamos de novo?");
            }else if(tempoLocomocaoTerrestre < 0){
                System.out.println("Tempo negativo... Como eu não tenho certeza se uma criança está usando isso eu fui configurado para não xingar ninguém\n\nMAS caso você não seja uma criança... *&$ ¨$ &*%@#");
            }else{
                break;
            }
        }
        return tempoLocomocaoTerrestre;
    }

    public static void metodosRobosAereos(RoboAereo roboAereo, int subirOuDescer, Scanner scanner){ //Função para subir ou descer o robô aéreo
        int deltaZ;
        if (subirOuDescer == 1){
            deltaZ = lerInteiro("Quantos metros você deseja subir? ", scanner);
            roboAereo.subir(deltaZ);
        }else{
            deltaZ = lerInteiro("Quantos metros você deseja descer? ", scanner);
            roboAereo.descer(deltaZ);
        }

    }
}