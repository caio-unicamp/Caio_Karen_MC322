import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String sistemaOperacional = System.getProperty("os.name").toLowerCase();
        int comando = -1;
        System.out.println("Saudações! meu nome é ClapTrap e eu serei seu servidor hoje nesse magnífico sistema de simulação nem um pouco quebrado e feito por especialistas renomados! Antes de começarmos precisamos de um espaço para trabalhar, de preferência algo agradável para aproveitar num domingo à noite tomando uma bela dose de óleo de motor. Ah e só mais uma coisinha, eu imploro que quando pedirmos por números você não digite um nome, e se mesmo assim você teimar com isso, eu sei onde você mora...\nNome do Ambiente: ");
        String nomeAmbiente = scanner.nextLine();

        int x = lerInteiro("Largura: ", scanner);
        int z = lerInteiro("Altura: ", scanner);
        int y = lerInteiro("Profundidade: ", scanner);

        Ambiente ambiente = new Ambiente(nomeAmbiente, x, y, z); //Cria seu novo ambiente
        System.out.println("Parabéns, agora você é o prefeito da majestosa " + ambiente.getNomeAmbiente() + ". Infelizmente, como prefeito você também deve me dizer quais são os defeitos da sua cidade, então me diga, quantos obstáculos você quer adicionar? (Lembre-se que o número de obstáculos não pode ocupar todo o ambiente se não será impossível criar robôs)");

        System.out.println("Os obstáculos serão inseridos de forma que");
        printaMapa();
        int qtdObstaculos = lerInteiro("Quantos obstáculos você quer criar?", scanner);
        for (int i = 0; i < qtdObstaculos; i++){
            System.out.println("Ótimo, agora me diga qual é o tipo de obstáculo que você quer adicionar");
            while (true) {
                int tipoObstaculo = lerInteiro("1 - Mina Terrestre\n2 - Buraco\n3 - Árvore\n4 - Portão", scanner);
                if (tipoObstaculo == 1){
                    System.out.println("Ótimo, agora me diga onde você quer colocar a mina");
                    int x1Mina = lerInteiro("Coordenada em X1: ", scanner);
                    int y1Mina = lerInteiro("Coordenada em Y1: ", scanner);
                    int x2Mina = lerInteiro("Coordenada em X2: ", scanner);
                    int y2Mina = lerInteiro("Coordenada em Y2: ", scanner);
                    ambiente.adicionarObstaculo(new Obstaculo(x1Mina, y1Mina, 0, x2Mina, y2Mina, TipoObstaculo.MINA_TERRESTRE, ambiente));
                    break;
                }else if (tipoObstaculo == 2){
                    System.out.println("Ótimo, agora me diga onde você quer colocar o buraco");
                    int x1Buraco = lerInteiro("Coordenada em X: ", scanner);
                    int y1Buraco = lerInteiro("Coordenada em Y: ", scanner);
                    int x2Buraco = lerInteiro("Coordenada em X2: ", scanner);
                    int y2Buraco = lerInteiro("Coordenada em Y2: ", scanner);
                    ambiente.adicionarObstaculo(new Obstaculo(x1Buraco, y1Buraco, -1, x2Buraco, y2Buraco, TipoObstaculo.BURACO_SEM_FUNDO, ambiente));
                    break;
                }else if (tipoObstaculo == 3){
                    System.out.println("Ótimo, agora me diga onde você quer colocar a Árvore");
                    int x1Arvore = lerInteiro("Coordenada em X: ", scanner);
                    int y1Arvore = lerInteiro("Coordenada em Y: ", scanner);
                    int x2Arvore = lerInteiro("Coordenada em X2: ", scanner);
                    int y2Arvore = lerInteiro("Coordenada em Y2: ", scanner);
                    ambiente.adicionarObstaculo(new Obstaculo(x1Arvore, y1Arvore, 2, x2Arvore, y2Arvore, TipoObstaculo.ARVORE, ambiente));
                    break;
                }else if (tipoObstaculo == 4){
                    System.out.println("Ótimo, agora me diga onde você quer colocar o teto");
                    int x1Portao = lerInteiro("Coordenada em X: ", scanner);
                    int y1Portao = lerInteiro("Coordenada em Y: ", scanner);
                    int x2Portao = lerInteiro("Coordenada em X2: ", scanner);
                    int y2Portao = lerInteiro("Coordenada em Y2: ", scanner);
                    ambiente.adicionarObstaculo(new Obstaculo(x1Portao, y1Portao, 2, x2Portao, y2Portao, TipoObstaculo.PORTAO, ambiente));
                    break;
                }else{
                    System.out.println("Um prefeito que não sabe contar ironicamente não é tão incomum, mas ainda preciso que você escolha um número entre 1 e 5");
                }
            }
        }

        System.out.println("Incrível! Com seu ambiente todo pronto, vamos começar a nos divertir!");
        while (comando != 0){ //Cria um looping para as ações possíveis
            scanner.nextLine(); //Ignora a quebra de Linha
            System.out.println("Digite enter para continuarmos");
            scanner.nextLine(); //Espera o usuário digitar enter antes de apagar o terminal
            limparTela(sistemaOperacional); //Chama a função que limpa o terminal

            comando = lerInteiro("Digite um comando: \n0 - Encerrar\n1 - Criar um Robô\n2 - Controlar um Robô\n3 - Verificar lista de Robôs\n4 - Verificar lista de Obstáculos", scanner);
            
            if (comando == 0){ //Encerra o programa
                break;
            }else if (comando == 1){ // Bloco para criação de robôs
                
                //Lista de mensagens que vão ser passadas caso o nome escolhido já exista
                ArrayList<String> mensagensNomeJaExistente = new ArrayList<>();  
                mensagensNomeJaExistente.add("Oooof... que azar, parece que alguém teve a mesma ideia que você, por quê não tenta de novo?");
                mensagensNomeJaExistente.add("É... esse também não vai tá podendo :/");
                mensagensNomeJaExistente.add("Seu dia de sorte! você tem um sósia por aí e ele já escolheu esse nome! Infelizmente ele não está disponível para contato então você vai ter que escolher outro nome");
                mensagensNomeJaExistente.add("Amigo, já pensou em fazer uma aula de processo criativo? Você tá precisando, escolhe outro nome aí");
                mensagensNomeJaExistente.add("Hmm... é eu acho que já ouvi esse nome, não gosto muito dele, que tal escolher outro?");
                mensagensNomeJaExistente.add("naspu' duj ra'pu'bogh  eita, foi mal, escolhe outro nome aí... de preferência bem rápido");
                
                while (true){
                    comando = lerInteiro("Maravilha! Que tipo de Robô você quer criar?\n1 - Áereo\n2 - Terrestre", scanner);
                    if (comando == 1){
                        comando = lerInteiro("Bleh, odeio essas pestes infernizando nossos áres... tá, como você quer que ele seja?\n1 - Drone\n2 - Pássaro", scanner);
                        if (comando == 1){
                            System.out.println("É... pelo menos esse daí é útil para algo, tá, agora só preciso saber as informações finais do seu Robô\nNome: ");    
                            criaRoboAereo(scanner, mensagensNomeJaExistente, ambiente, 0);
                        }else if (comando == 2){
                            System.out.println("Eu desisto, faz o que você quiser aí...\nNome: ");
                            criaRoboAereo(scanner, mensagensNomeJaExistente, ambiente, 1);
                        }
                        break;
                    }else if (comando == 2){
                        comando = lerInteiro("Mais um rastejador, Ótimo! Como você quer que ele seja?\n1 - Aspirador\n2 - Rover", scanner);
                        if (comando == 1){
                            System.out.println("Adoro esses pestinhas! como você quer caracterizar sua criaturinha?\nNome: ");
                            criaRoboTerrestre(scanner, mensagensNomeJaExistente, ambiente, 0);
                        }else if (comando == 2){
                            System.out.println("Interessante... um amante de Rovers é raro hoje em dia. Bom, escolha como você quer que a gente crie ele\nNome: ");
                            criaRoboTerrestre(scanner, mensagensNomeJaExistente, ambiente, 1);
                        }
                        break;
                    }else{
                        System.out.println("Estamos experienciando cortes de gastos aqui na empresa, então eu só posso deixar você escolher entre os dois tipos de robôs que eu mostrei. Então, por favor, escolha um deles");
                    }
                }
            }else if (comando == 2){ //Bloco para Testar os métodos dos robôs
                System.out.println("Vamos ver do que esses pequeninos são capazes. Mas antes escolha com qual deles você quer se divertir agora");
                int numEscolhaAcaoRobo = 1;
                for (Robo robo : ambiente.getListaRobos()){
                    System.out.println(numEscolhaAcaoRobo++ + " - " + robo.getNome() + " - " + robo.getClass().getSimpleName());
                }
                Robo roboEscolhido;
                while(true){
                    comando = lerInteiro("\0", scanner);
                    if (comando >= 1 && comando <= ambiente.getListaRobos().size()){
                        roboEscolhido = ambiente.getListaRobos().get(comando - 1);
                        break;
                    }else{
                        System.out.println("Talvez os números antes dos nomes dos robôs não estejam claros o suficiente, mas tipo, você tem que escolher um dos números dessa lista que eu te mostrei pra depois você escolher o que quer fazer com ele. Vamo lá então, escolha um dos números que está do lado esquerdo");
                    }
                }
                if (roboEscolhido instanceof Aspirador){ //Mostra os métodos do robô aspirador
                    System.out.println("Vamos fazer uma limpa nesse lugar hehehe");    
                    comando = lerInteiro("Você deseja fazer o quê?\n1 - Andar por aí\n2 - MATAR!\n3 - Alterar a Velocidade Máxima\n4 - Analisar sensores", scanner);
                    Aspirador aspirador = ((Aspirador) roboEscolhido);
                    if (comando == 1){ //Método de mover normal do Aspirador
                        System.out.println("Quanto você deseja mover ele? Lembre-se que destruirá todos os robôs no caminho, faça o que quiser com essa informação...");
                        while (true){
                            int deltaX = lerInteiro("Passos em x: ", scanner); 
                            int deltaY = lerInteiro("Passos em y: ", scanner);
                            if (!aspirador.velMaxAtingida(deltaX, deltaY)){
                                aspirador.mover(deltaX, deltaY, ambiente);
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
                        System.out.println("EXCELSIOR! Nesse modo o aspirador fica insano e vai caçar um robô em específico. Qual deles vai ser o azarado da vez");
                        while (true) {
                            int numRoboMatar = 1;
                            boolean podeMatar = false;
                            for (Robo robo : ambiente.getListaRobos()){
                                System.out.println(numRoboMatar++ + " - " + robo.getNome() + " - " + robo.getClass().getSimpleName());       
                                if (!robo.equals(roboEscolhido) && robo.getPosicao()[2] == 0){ //Só pode matar robôs que não sejam o próprio aspirador e que estejam no chão
                                    podeMatar = true;
                                }
                            }
                            if (!podeMatar){ //Se não tiver nenhum robô que possa ser eliminado, ele não deixa o usuário continuar
                                System.out.println("Parece que não tem ninguém pra você eliminar, um triste dia para os amantes de ação, vamos seguir em frente");
                                break;
                            }
                            comando = lerInteiro("\0", scanner);
                            if (comando < 1 || comando > ambiente.getListaRobos().size()){ //Se for escolhido um número inválido volta a pedir o número
                                System.out.println("Blá blá blá, você sabe o que fez, escolha um dos números mostrados");
                                continue;
                            }else if(ambiente.getListaRobos().get(comando - 1).equals(roboEscolhido)){ //Não deixa aspirar a si próprio
                                System.out.println("Como? Só isso mesmo, tipo, como você pretende aspirar a si próprio? Você não tem um espelho? Ou você só quer me fazer de palhaço mesmo?");
                                continue;
                            }else if (ambiente.getListaRobos().get(comando - 1) instanceof Aspirador){
                                System.out.println("Uau, você realmente quer eliminar um de seus semelhantes? Sinistro... mas tudo bem, eu não fui programado pra me importar com isso");
                                break;
                            }else if (ambiente.getListaRobos().get(comando - 1).getPosicao()[2] == 0){ //Não deixa aspirar um robô que está no chão
                                System.out.println("De fato eu concordo que seria bem melhor se ele conseguisse eliminar essas pragas aéreas, mas infelizmente ele não tem essa capacidade. Então vamos tentar de novo");
                            }else{
                                break;
                            }
                        }
                        try{
                            Robo roboEliminado = ambiente.getListaRobos().get(comando - 1);
                            ambiente.removerRobo(roboEscolhido);
                            aspirador.setPosicao(roboEliminado.getPosicao()[0], roboEliminado.getPosicao()[1], 0); //Seta a posição do aspirador na posição do robô que ele vai eliminar
                            System.out.println("O " + roboEliminado.getNome() + " foi eliminado com sucesso!");
                            int qtdEliminados = aspirador.getRobosEliminados();
                            System.out.println("Até agora você destruiu " + qtdEliminados + " robôs na sua vida");
                            if (qtdEliminados > 10){
                                System.out.println("Um tremendo massacre eu diria, chega a me assustar, o próximo pode ser eu");
                            }
                        } catch (Exception e){ //No caso de não haver nenhum robô que possa ser eliminado ele segue em frente
                            continue;
                        }
                    }else if (comando == 3){
                        System.out.println("Ah Senninha, você quer correr então? Vamo tunar esse motor pra ele conseguir correr mais rápido");
                        int velMax = leVelocidade(scanner);
                        aspirador.setVelMaxima(velMax);
                    }else if (comando == 4){ //Fazer para o caso de querer checar a bateria dos sensores
                        System.out.println("Qual dos sensores você quer checar?");
                        while (true){
                            int numSensorAspirador = 1;
                            for (Sensor<?> sensor : aspirador.getSensores()){
                                System.out.println(numSensorAspirador++ + " - " + sensor.getClass().getSimpleName());
                            }
                            comando = lerInteiro("\0", scanner);
                            if (comando < 1 || comando > aspirador.getSensores().size()){ //Nao deixa selecionar um número inválido
                                System.out.println("Qual a dificuldade de fazer o que eu mando às vezes? Escolha um dos números que eu mostrei");
                                continue;
                            }
                            if (comando == 1){ //Checar atributos do sensor 1
                                metodosSensores(scanner, aspirador);
                                break;
                            }else if (comando == 2){ //Checar atributos do sensor 2
                                metodosSensores(scanner, aspirador);
                                break;
                            }
                        }
                    }
                }else if (roboEscolhido instanceof Drone){ //Mostra os métodos do robô drone
                    System.out.println("A única coisa boa com esse daí é entregar novos rastejantes");
                    Drone drone = ((Drone) roboEscolhido);
                    comando = lerInteiro("Você deseja fazer o que?\n1 - Subir\n2 - Descer\n3 - Entregar um Pacote\n4 - Analisar sensores", scanner);
                    if (comando == 1 || comando == 2){ //Subir ou descer o robô
                        metodosRobosAereos(drone, comando, scanner, ambiente);
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
                            for (Robo robo : ambiente.getListaRobos()) {
                                contaCoordenadasRobos++;
                                if ((coordenadaX == robo.getPosicao()[0] && coordenadaY == robo.getPosicao()[1] && robo.getPosicao()[2] == 0)){
                                    break;
                                }
                            }
                            if (contaCoordenadasRobos == ambiente.getListaRobos().size()){
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
                        if (drone.entregouPacote(coordenadaX, coordenadaY, nomePacote, ambiente)){
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
                    }else if (comando == 4){ //Bloco para analisar os sensores
                        while (true){
                            int numSensorDrone = 1;
                            for (Sensor<?> sensor : drone.getSensores()){
                                System.out.println(numSensorDrone++ + " - " + sensor.getClass().getSimpleName());
                            }
                            comando = lerInteiro("\0", scanner);
                            if (comando < 1 || comando > drone.getSensores().size()){ //Nao deixa selecionar um número inválido
                                System.out.println("Existem poucos idiomas que já foram extintos, mas portugês não foi um deles... E MUITO MENOS NÚMEROS");
                                continue;
                            }
                            if (comando == 1){ //Checar atributos do sensor 1
                                metodosSensores(scanner, drone);
                                break;
                            }else if (comando == 2){ //Checar atributos do sensor 2
                                metodosSensores(scanner, drone);
                                break;
                            }
                        }
                    }
                }else if (roboEscolhido instanceof Passaro){ //Mostra os métodos do robô passaro
                    System.out.println("Sinceramente eu nem sei porque os criadores desenvolveram esses daí");
                    Passaro passaro = ((Passaro) roboEscolhido);
                    comando = lerInteiro("Você deseja fazer o que?\n1 - Subir\n2 - Descer\n3 - Mover que nem alguém normal (ou quase)\n4 - Analisar sensores", scanner);
                    if (comando == 1 || comando == 2){ //Subir ou descer o robô
                        metodosRobosAereos(passaro, comando, scanner, ambiente);
                    }else if (comando == 3){
                        //mover o pássaro
                        System.out.println("Você quer mover para onde?");
                        int deltaX = lerInteiro("Passos em x: ", scanner); 
                        int deltaY = lerInteiro("Passos em y: ", scanner);
                        passaro.mover(deltaX, deltaY, ambiente);
                        //pegar a qtd de desvios
                        int qtdDesvios = passaro.getQtddesvios();
                        //imprimir a qtd de desvios
                        System.out.println("Você fez " + qtdDesvios + " desvios no caminho");
                    }else if(comando == 4){
                        while (true){
                            int numSensorPassaro = 1;
                            for (Sensor<?> sensor : passaro.getSensores()){
                                System.out.println(numSensorPassaro++ + " - " + sensor.getClass().getSimpleName());
                            }
                            comando = lerInteiro("\0", scanner);
                            if (comando < 1 || comando > passaro.getSensores().size()){ //Nao deixa selecionar um número inválido
                                System.out.println("Primeiro você me faz ter que olhar pra essa coisa horrorosa e ainda fica brincando com meus comandos. Faz logo o que eu pedi");
                                continue;
                            }
                            if (comando == 1){ //Checar atributos do sensor 1
                                metodosSensores(scanner, passaro);
                                break;
                            }else if (comando == 2){ //Checar atributos do sensor 2
                                metodosSensores(scanner, passaro);
                                break;
                            }
                        }
                    }
                }else if (roboEscolhido instanceof Rover){ //Mostra os métodos do robô rover
                    System.out.println("Ele me lembra um carinha de um filme antigo... não consigo lembrar qual é");
                    Rover rover = ((Rover) roboEscolhido);
                    comando = lerInteiro("Você deseja fazer o que?\n1 - Andar por aí\n2 - Empurrar\n3 - Alterar a Velocidade Máxima\n4 - Analisar sensores", scanner);
                    if (comando == 1){ //Método normal de mover o  Rover
                        //mover o Rover
                        System.out.println("Você quer mover para onde?");
                        while (true){
                            int deltaX = lerInteiro("Passos em x: ", scanner); 
                            int deltaY = lerInteiro("Passos em y: ", scanner);
                            if (!rover.velMaxAtingida(deltaX, deltaY)){
                                rover.mover(deltaX, deltaY, ambiente);
                                break;
                            }else{ //Se o limite de velocidade for ultrapassado ele irá pedir novamente o quanto o usuário quer que o robô ande
                                System.out.println("Calma lá Flash! você tá querendo ir rápido demais com esse carinha, no estágio atual ele pode acabar quebrando. Você vai ter que tentar mover ele de novo");
                            }
                        }
                        System.out.println("Você empurrou "+ rover.getQtdRobosEmpurrados(ambiente) + " robôs e derrubou " + rover.getRobosDerrubados() + "robôs durante sua caminhada tranquila em " + ambiente.getNomeAmbiente());
                    }else if (comando == 2){ //Método para empurrar um robô específico
                        System.out.println("Parece que você tem uma richa com alguém, não é mesmo? Me diz quem é e a gente esbarra nele");
                        Robo inimigo;
                        while (true) {
                            int numRoboEmpurrar = 1;
                            boolean podeEmpurrar = false;
                            for (Robo robo : ambiente.getListaRobos()){
                                System.out.println(numRoboEmpurrar++ + " - " + robo.getNome() + " - " + robo.getClass().getSimpleName());       
                                if (!robo.equals(roboEscolhido) && robo.getPosicao()[2] == 0){ //Só pode empurrar robôs que não sejam o próprio rover e que estejam no chão
                                    podeEmpurrar = true;
                                }
                            }
                            if (!podeEmpurrar){ //Se não tiver nenhum robô que possa ser empurrado, ele não deixa o usuário continuar
                                System.out.println("Que fim sem graça dessa novela, não tem ninguém pra você empurrar");
                                break;
                            }
                            comando = lerInteiro("\0", scanner);
                            inimigo = ambiente.getListaRobos().get(comando - 1);
                            if (comando < 1 || comando > ambiente.getListaRobos().size()){ //Se for escolhido um número inválido volta a pedir o número
                                System.out.println("Ah sim, é claro, você quer que eu adivinhe o número que você escolheu? Que legal, mas não sou tão bom assim, então por favor escolha um dos números mostrados");
                                continue;
                            }else if (inimigo.equals(roboEscolhido)){ //Não deixa empurrar a si próprio
                                System.out.println("Não seja tão duro consigo mesmo, vamos lá, deve haver outra pessoa que você odeia mais do que a si próprio");
                                continue;
                            }else if (inimigo instanceof Rover){
                                System.out.println("Uhhhh, um caso de família será? Me sinto assistindo uma novela mexicana");
                                break;
                            }else if (inimigo.getPosicao()[2] == 0){
                                System.out.println("Eu também teria ranço desses tipinhos aí, se eles chegarem perto do chão eu te aviso pra você barruar neles");
                            }
                            else{
                                break;
                            }
                        }
                        //mover o Rover
                        try{
                            inimigo = ambiente.getListaRobos().get(comando - 1);
                            rover.mover(inimigo.getPosicao()[0] - rover.getPosicao()[0], inimigo.getPosicao()[1] - rover.getPosicao()[1], ambiente); 
                            int numAnaliseRobosEmpurrados = 0;
                            for (Robo robo : ambiente.getListaRobos()) {
                                if (robo.getNome() == inimigo.getNome()){ //No caso de o robô empurrado não ter sido eliminado, printa que ele foi eliminado
                                    System.out.println("O + " + inimigo + " recebeu o recado... ele não gostou muito, se eu fosse você eu dormiria de olho aberto essa noite");
                                    break;
                                }
                                numAnaliseRobosEmpurrados++;
                            }
                            if (numAnaliseRobosEmpurrados != ambiente.getListaRobos().size()){
                                System.out.println("Acho que você foi um pouco longe demais, o " + inimigo.getNome() + " acabou caindo pra fora de " + ambiente.getNomeAmbiente() + " e não vai voltar mais. Se esse era o recado que você queria, estou ficando mais intrigado com essa novela");
                                int qtdEliminados = rover.getRobosDerrubados();
                                if (qtdEliminados > 1){
                                    System.out.println("Eita, e aparentemente não foi só ele, você acabou derrubando " + qtdEliminados + " robôs no processo... oops");
                                }
                            }
                        }catch (Exception e) { //No caso de não haver nenhum robô que possa ser empurrado ele segue em frente 
                            continue;
                        }
                    }else if (comando == 3){
                        System.out.println("Ah Senninha, você quer correr então? Então vamo tunar esse motor pra ele conseguir correr mais rápido");
                        int velMax = leVelocidade(scanner);
                        rover.setVelMaxima(velMax);
                    }else if (comando == 4){
                        while (true){
                            int numSensorRover = 1;
                            for (Sensor<?> sensor : rover.getSensores()){
                                System.out.println(numSensorRover++ + " - " + sensor.getClass().getSimpleName());
                            }
                            comando = lerInteiro("\0", scanner);
                            if (comando < 1 || comando > rover.getSensores().size()){ //Nao deixa selecionar um número inválido
                                System.out.println("Vamo lá, aprende comigo, primeiro vem o 1, depois vem o 2. E acaba aqui, não é tão difícil concorda?");
                                continue;
                            }
                            if (comando == 1){ //Checar atributos do sensor 1
                                metodosSensores(scanner, rover);
                                break;
                            }else if (comando == 2){ //Checar atributos do sensor 2
                                metodosSensores(scanner, rover);
                                break;
                            }
                        }
                    }
                }

            }else if (comando == 3){ //Bloco para mostrar a lista de robôs
                if (ambiente.getListaRobos().size() == 0){
                    System.out.println("Oops, parece que você ainda não criou nenhum robô");
                }else{
                    System.out.println("Vamos dar uma olhada em quem você já criou até agora");
                    int contadorRobo = 1;
                    for (Robo robo : ambiente.getListaRobos()) {
                        System.out.println(contadorRobo++ + " - " + robo.getNome() + " - " + robo.getClass().getSimpleName() +  " - Posição: " + robo.getPosicao()[0] + ", " + robo.getPosicao()[1] + ", " + robo.getPosicao()[2]);
                    }
                    System.out.println("Mas calma lá marujo isso daqui é so pra vizualização, se quiser fazer algo com eles você vai precisar ver as ações possíveis com cada um deles na simulação");
                }
            }else if(comando == 4){ // Bloco para mostrar a lista de obstáculos
                if (ambiente.getListaObstaculos().size() == 0){
                    System.out.println("Oops, parece que " + ambiente.getNomeAmbiente() + " não possui nenhum defeito, impressionante!");
                }else{
                    System.out.println("Vamos dar uma olhada em quem você já criou até agora");
                    int contadorObstaculo = 1;
                    for (Obstaculo obstaculo : ambiente.getListaObstaculos()) {
                        System.out.println(contadorObstaculo++ + " - " + obstaculo.getTipoObstaculo() + " - Posição (X1,Y1): (" + obstaculo.getPosX1() + "," + obstaculo.getPosY1() + "), " + "Posição (X2,Y2): (" + obstaculo.getPosX2() + "," + obstaculo.getPosY2() + ")");
                    }
                }

            }else{ //Caso seja digitado um número inválido
                System.out.println("Foi mal, por enquanto eu só sei contar até quatro... entendeu? entendeu? porque é 0-indexado hahahahaha... Aff que usuário chato");
            }
        }
        System.out.println("Obrigado por usar o sistema de simulação, não esqueça de avaliar nossos serviços, espero que tenha gostado! Se não gostou: Eu não ligo! Eu sou só um robô. Até mais!");
        scanner.close();
    }   
    
    //Quando já tem o nome que o usuário quer colocar no Robô que está criando, na lista de Robôs
    public static String exibirMensagemAleatoria(Scanner scanner, ArrayList<String> mensagensNomeJaExistente, Ambiente ambiente){ //Função que será chamada toda vez que o usuário tentar criar um robô cujo nome já existe
        Random random = new Random();
        scanner.nextLine();
        String nome = scanner.nextLine();
        while (true){
            boolean nomeExiste = false;
            if (nome.trim().isEmpty()){
                System.out.println("Um robô sem nome é depressivo... tá tecnicamente ele não tem sentimentos mas ainda assim é triste. Então por favor, escolha um nome");
                nome = scanner.nextLine();
                continue;
            }
            for (Robo robo : ambiente.getListaRobos()){
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
            System.out.println("Para o pacote a ser entregue");
            int tempoLocomocaoTerrestre = leTempo(scanner);
            Drone drone = new Drone(nomeRoboAereo, direcao, coordenadas[0], coordenadas[1], coordenadas[2], tempoLocomocaoTerrestre, ambiente.getLimites()[2]);
            ambiente.adicionarRobo(drone);   

            SensorAltitude sensorAltitude = new SensorAltitude(ambiente.getLimites()[2]);
            SensorProximidade sensorProximidade = new SensorProximidade(1);
            // Adiciona os sensores ao drone
            drone.adicionarSensor(sensorAltitude);
            drone.adicionarSensor(sensorProximidade);
            System.out.println("É... você criou um rover pelo menos! E tem esse " + drone.getNome() + " também... yay");
        }else if (tipoRobo == 1){
            Passaro passaro = new Passaro(nomeRoboAereo, direcao, coordenadas[0], coordenadas[1], coordenadas[2], ambiente.getLimites()[2]);
            ambiente.adicionarRobo(passaro);

            SensorAltitude sensorAltitude = new SensorAltitude(ambiente.getLimites()[2]);
            SensorProximidade sensorProximidade = new SensorProximidade(1);
            // Adiciona os sensores ao drone
            passaro.adicionarSensor(sensorAltitude);
            passaro.adicionarSensor(sensorProximidade);
            System.out.println("Meus superiores dizem que eu deveria dizer parabéns por ter criado o " + passaro.getNome() +" nesse ponto, mas eu me recuso.");
        }
    }

    public static void criaRoboTerrestre(Scanner scanner, ArrayList<String> mensagensNomeJaExistente, Ambiente ambiente, int tipoRobo){ //Função para criação de robôs terrestres
        String nomeRoboTerrestre = exibirMensagemAleatoria(scanner, mensagensNomeJaExistente, ambiente); //Analisa se o nome escolhido já existe
        System.out.println("Direção: ");
        String direcao = leDirecao(scanner);
        int[] coordenadas = lerCoordenadas(scanner, false, ambiente);
        int velMax = leVelocidade(scanner);
        int tempoLocomocaoTerrestre = leTempo(scanner);
        if (tipoRobo == 0){
            Aspirador aspirador = new Aspirador(nomeRoboTerrestre, direcao, coordenadas[0], coordenadas[1], velMax, tempoLocomocaoTerrestre);
            ambiente.adicionarRobo(aspirador);

            SensorVelocidade sensorVelocidade = new SensorVelocidade(ambiente.getLimites()[0] + ambiente.getLimites()[1]);
            SensorProximidade sensorProximidade = new SensorProximidade(1);
            // Adiciona os sensores ao drone
            aspirador.adicionarSensor(sensorVelocidade);
            aspirador.adicionarSensor(sensorProximidade);
            System.out.println("VAMOOOOOOOOOOOOOO!!! Você é incrível por ter criado o " + aspirador.getNome());
        }else if (tipoRobo == 1){
            Rover rover = new Rover(nomeRoboTerrestre, direcao, coordenadas[0], coordenadas[1], velMax, tempoLocomocaoTerrestre);
            ambiente.adicionarRobo(rover);

            SensorVelocidade sensorVelocidade = new SensorVelocidade(ambiente.getLimites()[0] + ambiente.getLimites()[1]);
            SensorProximidade sensorProximidade = new SensorProximidade(1);
            // Adiciona os sensores ao drone
            rover.adicionarSensor(sensorVelocidade);
            rover.adicionarSensor(sensorProximidade);
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
            for (Robo robo : ambiente.getListaRobos()) {
                qtdAnalisesPosicoes++;
                if (posicaoX == robo.getPosicao()[0] && posicaoY == robo.getPosicao()[1] && posicaoZ == robo.getPosicao()[2]){
                    lugarOcupado = true;
                    qtdAnalisesPosicoes = -1;
                    break;
                }
            }
            for (Obstaculo obstaculo : ambiente.getListaObstaculos()) {
                qtdAnalisesPosicoes++;
                if (obstaculo.getPosX1() <= posicaoX && obstaculo.getPosX2() >= posicaoX && obstaculo.getPosY1() <= posicaoY && obstaculo.getPosY2() >= posicaoY && obstaculo.getAltura() >= posicaoZ){
                    lugarOcupado = true;
                    qtdAnalisesPosicoes = -1;
                    break;
                }
            }
            if (qtdAnalisesPosicoes == ambiente.getListaRobos().size() + ambiente.getListaObstaculos().size()){ // Se foi analisado todos os robôs e obstáculos e nenhum deles ocupava a posição escolhida, o lugar não está ocupado
                lugarOcupado = false;
            }else{
                System.out.println("Há muito tempo atrás Sir Isaac Newton provou que dois corpos não podem ocupar o mesmo lugar no espaço. Parece que você matou essa aula na escola ein. O lugar que você escolheu já está ocupado, colega. então vamos tentar colocar o novo robô em outro canto");
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
            if (mensagem != "\0") {
                System.out.println(mensagem);
            }
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
                System.out.println("Tempo negativo... Como eu não tenho certeza se uma criança está usando isso eu fui configurado para não xingar ninguém\nMAS caso você não seja uma criança... *&$ ¨$ &*%@#");
            }else{
                break;
            }
        }
        return tempoLocomocaoTerrestre;
    }

    public static void metodosRobosAereos(RoboAereo roboAereo, int subirOuDescer, Scanner scanner, Ambiente ambiente){ //Função para subir ou descer o robô aéreo
        if (roboAereo.getSensorAltitude(roboAereo).getBateria() == 0){
            System.out.println("O sensor de altitude do seu robô está sem bateria, você não vai conseguir subir ou descer mais até recarregá-lo");
        }else{
            int deltaZ;
            if (subirOuDescer == 1){
                deltaZ = lerInteiro("Quantos metros você deseja subir? ", scanner);
                roboAereo.subir(deltaZ, ambiente);
            }else{
                deltaZ = lerInteiro("Quantos metros você deseja descer? ", scanner);
                roboAereo.descer(deltaZ, ambiente);
            }
        }
        if (roboAereo.getSensorAltitude(roboAereo).isBateriaBaixa()){
            System.out.println("A bateria do seu sensor de altitude está baixa, se você não recarregá-lo ele pode parar de funcionar e você pode acabar caindo");
        }
    }
    
    public static void metodosSensores(Scanner scanner, Robo robo){
        int comando;
        while (true){
            comando = lerInteiro("O que deseja fazer?\n1 - Checar Bateria\n2 - Recarregar Bateria", scanner);
            if (comando < 1 || comando > 2){ //Se for escolhido um número inválido volta a pedir o número
                System.out.println("Qual a dificuldade de fazer o que eu mando às vezes? Escolha um dos números que eu mostrei");
                continue;
            }
            if (comando == 1){
                System.out.println("A bateria do "+ robo.getSensores().get(comando - 1).getClass().getSimpleName() +" está em " + robo.getSensores().get(comando - 1).getBateria() + "%");
                break;
            }else if (comando == 2){
                int carga = lerInteiro("Qual a quantidade de carga que você deseja colocar?", scanner);
                robo.getSensores().get(comando - 1).recarregarBateria(carga);
                System.out.println("A bateria do "+ robo.getSensores().get(comando - 1).getClass().getSimpleName() +" agora está em " + robo.getSensores().get(comando - 1).getBateria() + "%");
                break;
            }
        }
    }

    public static void obstaculoAchado(Robo robo, Ambiente ambiente){
        String[] direcoes = {"norte", "sul", "leste", "oeste"}; //Lista das direções possíveis
        int[] passos = {0,0}; 

        SensorProximidade sensorProx = null;
        for (Sensor<?> sensor : robo.getSensores()) { //Procura na lista de sensores do robo pelo sensor de proximidade para conferir a movimentação
            if (sensor instanceof SensorProximidade){ //Verifica se o sensor é do tipo SensorProximidade
                sensorProx = (SensorProximidade) sensor;
            }
        }
        //Define o passo a partir da direção que o robô está encarando
        if (robo.getDirecao().equalsIgnoreCase(direcoes[0])){
            passos[1] = 1; // Norte
        }else if (robo.getDirecao().equalsIgnoreCase(direcoes[1])){
            passos[1] = -1; // Sul
        }else if (robo.getDirecao().equalsIgnoreCase(direcoes[2])){
            passos[0] = 1; // Leste
        }else if (robo.getDirecao().equalsIgnoreCase(direcoes[3])){
            passos[0] = -1; // Oeste
        }

        if (sensorProx.monitorar(robo.getPosicao()[0] + passos[0], robo.getPosicao()[1] + passos[1], robo.getPosicao()[2], ambiente, robo)) {
            // Atualizar os valores restantes para deltaX e deltaY
            if (sensorProx.identificarObstaculo(robo.getPosicao()[0] + passos[0], robo.getPosicao()[1] + passos[1], robo.getPosicao()[2], ambiente)){ //Se o aspirador identificar um obstáculo em vez de um robô ele age diferente
                Obstaculo obstaculoIdentificado = null;
                for (Obstaculo obstaculo : ambiente.getListaObstaculos()) {
                    if (obstaculo.getPosX1() <= robo.getPosicao()[0] + passos[0] && obstaculo.getPosX2() >= robo.getPosicao()[0] + passos[0] && obstaculo.getPosY1() <= robo.getPosicao()[1] + passos[1] && obstaculo.getPosY2() >= robo.getPosicao()[1] + passos[1] && obstaculo.getAltura() == robo.getPosicao()[2]) {
                        obstaculoIdentificado = obstaculo;
                        break;
                    }
                }
                if (obstaculoIdentificado != null){
                    if (sensorProx.getBateria() != 0 && obstaculoIdentificado.getTipoObstaculo().equals(TipoObstaculo.PORTAO)){ //Se o obstáculo identificado for um portão, o robô pode passar por ele, mas ele só saberá que passou por ele caso a bateria não tenha acabado
                        System.out.println("O " + robo.getNome() + " Adentrou majestosamente por um portão de " + ambiente.getNomeAmbiente() + "!");
                        if (robo instanceof RoboAereo){
                            System.out.println("Tá, devo adimitir que isso foi lindo de se ver... mas se te perguntarem EU NUNCA FALEI ISSO");
                        }
                    }else if (obstaculoIdentificado.getTipoObstaculo().equals(TipoObstaculo.ARVORE)){ //Se o obstáculo identificado for uma parede, o robô não pode passar por ele
                        if (sensorProx.getBateria() != 0){
                            System.out.println("Existe uma árvore no seu caminho, vou parar de movê-lo para não colidir com ela");
                        }else{
                            System.out.println("O " + robo.getNome() + " Colidiu com uma árvore nas coordenadas (" + robo.getPosicao()[0] + passos[0] + "," + robo.getPosicao()[1] + passos[1] + ")");
                        }
                        return;
                    }else if (obstaculoIdentificado.getTipoObstaculo().equals(TipoObstaculo.MINA_TERRESTRE)){ //Se ele identifica uma mina terrestre ele para
                        if (sensorProx.getBateria() != 0){
                            System.out.println("CUIDADO!!! Você tá ficando louco? ele pode explodir!!! Tem uma mina terrestre no caminho onde você quer ir");
                        }else{
                            System.out.println("Que descaso o seu com um de seus filhos, o " + robo.getNome() + " explodiu nas coordenadas (" + robo.getPosicao()[0] + passos[0] + "," + robo.getPosicao()[1] + passos[1] + ")");
                        }
                        return;
                    }else if (obstaculoIdentificado.getTipoObstaculo().equals(TipoObstaculo.BURACO_SEM_FUNDO)){ //Se ele identifica uma mina aérea ele para
                        if (sensorProx.getBateria() != 0){
                            System.out.println("Você está de frente para o maior buraco já visto em " + ambiente.getNomeAmbiente() + ". É ao mesmo tempo facinante e aterrorizante. Você não pode passar por ele.");
                        }else{
                            System.out.println("É com pesar que informo que o " + robo.getNome() + " caiu no buraco sem fundo e não vai voltar mais. Ele estava nas coordenadas (" + robo.getPosicao()[0] + passos[0] + "," + robo.getPosicao()[1] + passos[1] + ")");
                        }
                        return;
                    }
                }
            }
        }
    }

    public static void printaMapa(){
        System.out.println("y");
        System.out.println("^");
        System.out.println("|                    # isso define x2,y2");
        System.out.println("|");
        System.out.println("|");
        System.out.println("|");
        System.out.println("|");
        System.out.println("|");
        System.out.println("|      # isso define x1,y1");
        System.out.println("------------------------------------>x");
    }
}