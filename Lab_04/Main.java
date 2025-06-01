import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import ambiente.*;
import enums.*;
import excecoes.*;
import interfaces.*;
import robots.*;
import robots.aereo.*;
import robots.terrestre.*;
import sensores.*;

public class Main {
    public static void main(String[] args) throws ColisaoException, AlturaMaximaAtingidaException, SensorDesligadoException, ErroComunicacaoException, RoboDesligadoException{
        Scanner scanner = new Scanner(System.in);
        String sistemaOperacional = System.getProperty("os.name").toLowerCase();
        int comando = -1;
        System.out.println("Saudações! meu nome é ClapTrap e eu serei seu servidor hoje nesse magnífico sistema de simulação nem um pouco quebrado e feito por especialistas renomados! Antes de começarmos precisamos de um espaço para trabalhar, de preferência algo agradável para aproveitar num domingo à noite tomando uma bela dose de óleo de motor. Ah e só mais uma coisinha, eu imploro que quando pedirmos por números você não digite um nome, e se mesmo assim você teimar com isso, eu sei onde você mora...\nNome do Ambiente: ");
        String nomeAmbiente = scanner.nextLine();

        int x = lerInteiro("Largura: ", scanner);
        int z = lerInteiro("Altura: ", scanner);
        int y = lerInteiro("Profundidade: ", scanner);

        Ambiente ambiente = new Ambiente(nomeAmbiente, x, y, z); //Cria seu novo ambiente
        ambiente.inicializarMapa(); //Inicializa o mapa

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
                    System.out.println("Para (x1,y1)");
                    int [] mina1 = lerCoordenadas(scanner, false, ambiente);
                    int x1Mina = mina1[0];
                    int y1Mina = mina1[1];
                    System.out.println("Para (x2,y2)");
                    int [] mina2 = lerCoordenadas(scanner, false, ambiente);
                    int x2Mina = mina2[0];
                    int y2Mina = mina2[1];
                    ambiente.adicionarEntidade(new Obstaculo(x1Mina, y1Mina, x2Mina, y2Mina, TipoObstaculo.MINA_TERRESTRE, ambiente));
                    break;
                }else if (tipoObstaculo == 2){
                    System.out.println("Ótimo, agora me diga onde você quer colocar o buraco");
                    System.out.println("Para (x1,y1)");
                    int [] buraco1 = lerCoordenadas(scanner, false, ambiente);
                    int x1Buraco = buraco1[0];
                    int y1Buraco = buraco1[1];
                    System.out.println("Para (x2,y2)");
                    int [] buraco2 = lerCoordenadas(scanner, false, ambiente);
                    int x2Buraco = buraco2[0];
                    int y2Buraco = buraco2[1];
                    ambiente.adicionarEntidade(new Obstaculo(x1Buraco, y1Buraco, x2Buraco, y2Buraco, TipoObstaculo.BURACO_SEM_FUNDO, ambiente));
                    break;
                }else if (tipoObstaculo == 3){
                    System.out.println("Ótimo, agora me diga onde você quer colocar a Árvore");
                    System.out.println("Para (x1,y1)");
                    int [] arvore1 = lerCoordenadas(scanner, false, ambiente);
                    int x1Arvore = arvore1[0];
                    int y1Arvore = arvore1[1];
                    System.out.println("Para (x2,y2)");
                    int [] arvore2 = lerCoordenadas(scanner, false, ambiente);
                    int x2Arvore = arvore2[0];
                    int y2Arvore = arvore2[1];
                    ambiente.adicionarEntidade(new Obstaculo(x1Arvore, y1Arvore, x2Arvore, y2Arvore, TipoObstaculo.ARVORE, ambiente));
                    break;
                }else if (tipoObstaculo == 4){
                    System.out.println("Ótimo, agora me diga onde você quer colocar o Portão");
                    System.out.println("Para (x1,y1)");
                    int [] portao1 = lerCoordenadas(scanner, false, ambiente);
                    int x1Portao = portao1[0];
                    int y1Portao = portao1[1];
                    System.out.println("Para (x2,y2)");
                    int [] portao = lerCoordenadas(scanner, false, ambiente);
                    int x2Portao = portao[0];
                    int y2Portao = portao[1];
                    ambiente.adicionarEntidade(new Obstaculo(x1Portao, y1Portao, x2Portao, y2Portao, TipoObstaculo.PORTAO, ambiente));
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

            comando = lerInteiro("Digite um comando: \n0 - Encerrar\n1 - Criar um Robô\n2 - Controlar um Robô\n3 - Ligar ou Desligar um Robô\n4 - Verificar lista de Robôs\n5 - Verificar lista de Obstáculos\n6 - Verificar Central de Comunicação\n7 - Vizualisar o ambiente\n8 - Ver descrição das Entidades", scanner);
            
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
                System.out.println("Vamos ver do que esses pequeninos são capazes. Mas antes escolha com qual deles você quer se divertir agora. Digite o nome do robô que você deseja testar.");
                int numEscolhaAcaoRobo = 1;
                for (Entidade entidade : ambiente.getListaEntidades()){
                    if (!(entidade instanceof Robo)){ //Verifica se a entidade é um robô
                        continue; //Se não for, segue para a próxima iteração
                    }else{
                        Robo robo = (Robo) entidade; //Faz o cast para robô
                        System.out.println(numEscolhaAcaoRobo++ + " - " + robo.getNome() + " - " + robo.getClass().getSimpleName());
                    }
                }
                Robo roboEscolhido = null;
                while(true){
                    String nomeRoboEscolhido = scanner.nextLine();
                    for (Entidade entidade : ambiente.getListaEntidades()) { //Procura pelo robô pelo nome digitado
                        if (entidade.getNome().equals(nomeRoboEscolhido)){
                            roboEscolhido = (Robo) entidade;
                            break;
                        }
                    }
                    if ((roboEscolhido != null)){ //Se o robô existe na lista quebra o laço
                        break;
                    }else{
                        System.out.println("Não sei como, mas acho que não me fiz claro o suficiente, mas tipo, você tem que escolher um dos robôs dessa lista que eu te mostrei pra depois você escolher o que quer fazer com ele. Vamo lá então, escolha um dos nomes que está do lado direito");
                    }
                }
                if (roboEscolhido instanceof Aspirador){ //Mostra os métodos do robô aspirador
                    System.out.println("Vamos fazer uma limpa nesse lugar hehehe");    
                    comando = lerInteiro("Você deseja fazer o quê?\n1 - Andar por aí\n2 - MATAR!\n3 - Alterar a Velocidade Máxima\n4 - Analisar sensores\n5 - Enviar uma mensagem", scanner);
                    Aspirador aspirador = ((Aspirador) roboEscolhido);
                    if (comando == 1){ //Método de mover normal do Aspirador
                        System.out.println("Quanto você deseja mover ele? Lembre-se que destruirá todos os robôs no caminho, faça o que quiser com essa informação...");
                        while (true){
                            int deltaX = lerInteiro("Passos em x: ", scanner); 
                            int deltaY = lerInteiro("Passos em y: ", scanner);
                            try{
                                double taxaAspirador = aspirador.getSensorVelocidade().porcentoVelocidade(aspirador.getSensorVelocidade().monitorar(deltaX, deltaY, aspirador), aspirador.getVelocidadeMaxima());
                                String velAspirador = String.format("%.2f", taxaAspirador);
                                try{
                                    //Tratamento dos erros de sensor desligado e robô desligado ao tentar acionar os sensores 
                                    try{
                                        aspirador.acionarSensores();
                                    }catch (RoboDesligadoException e) { //Se o robô estiver desligado, não consegue monitorar o ambiente
                                        System.err.println(e.getMessage());
                                    }catch (SensorDesligadoException e) { //Se algum sensor estiver desligado, não consegue monitorar o ambiente
                                        System.err.println(e.getMessage());
                                    }
                                    aspirador.executarTarefa("mover", deltaX, deltaY, ambiente);
                                    obstaculoAchado(aspirador, ambiente);
                                    System.out.println("Você andou a "+ velAspirador + "% da velocidade máxima");
                                    if (aspirador.getSensorVelocidade().isMuito(taxaAspirador)){
                                        System.out.println("Quase um SpeedRacer! Impressionante!");
                                    }
                                    //pegar a qtd de robos eliminados
                                    int qtdEliminados = aspirador.getRobosEliminados();
                                    //imprimir a qtd de eliminados
                                    System.out.println("Até agora você destruiu " + qtdEliminados + " robôs na sua vida");
                                    if (qtdEliminados > 10){
                                        System.out.println("Um tremendo massacre eu diria, chega a me assustar, o próximo pode ser eu");
                                    }
                                    break;    
                                }catch(RoboDesligadoException e){ //O robô só pode se mover se estiver ligado
                                    System.err.println(e.getMessage());
                                }catch(SensorDesligadoException e){ //Se ele tentar se mover com um sensor descarregado ele indica que isso não é possível
                                    System.err.println(e.getMessage());
                                }catch(ColisaoException e){ //Se ele se moveu e acabou a bateria do sensor de proximidade no meio da locomoção ele poderá colidir com algum obstáculo
                                    System.err.println(e.getMessage());
                                }
                            }catch(VelocidadeMaximaAtingidaException e){ //Se ele tiver ultrapassado o limite de velocidade ele irá pedir novamente o quanto ele quer que o robô ande e indicará qual foi o erro
                                System.err.println(e.getMessage());
                            }
                        }
                    }else if (comando == 2){ //Para ir atás de aspirar um robô específico
                        System.out.println("EXCELSIOR! Nesse modo o aspirador fica insano e vai caçar um robô em específico. Qual deles vai ser o azarado da vez? Digite o nome do robô a ser aspirado");
                        Robo roboAspirado = null;
                        while (true) {
                            int numRoboMatar = 1;
                            boolean podeMatar = false;
                            for (Entidade entidade : ambiente.getListaEntidades()){ //Procura a entidade pelo nome
                                if (!(entidade instanceof Robo)){ //Verifica se a entidade é um robô
                                    continue; //Se não for segue para a próxima iteração
                                }else{
                                    Robo robo = (Robo) entidade; //Faz o cast para robô
                                    System.out.println(numRoboMatar++ + " - " + robo.getNome() + " - " + robo.getClass().getSimpleName());       
                                    if (!robo.equals(roboEscolhido) && robo.getPosicao()[2] == 0){ //Só pode matar robôs que não sejam o próprio aspirador e que estejam no chão
                                        podeMatar = true;
                                    }
                                }
                            }
                            if (!podeMatar){ //Se não tiver nenhum robô que possa ser eliminado, ele não deixa o usuário continuar
                                System.out.println("Parece que não tem ninguém pra você eliminar, um triste dia para os amantes de ação, vamos seguir em frente");
                                break;
                            }
                            String nomeRoboAspirado = scanner.nextLine();
                            for (Entidade entidade : ambiente.getListaEntidades()){
                                if (!(entidade instanceof Robo)){ //Verifica se a entidade é um robô
                                    continue; //Se não for, segue para a próxima iteração
                                }else if (entidade.getNome().equals(nomeRoboAspirado)){
                                    roboAspirado = (Robo) entidade;
                                }
                            }
                            if (roboAspirado == null){ //Se for escolhido um robô inválido volta a pedir o número
                                System.out.println("Blá blá blá, você sabe o que fez, escolha um dos nomes mostrados");
                                continue;
                            }else if(roboAspirado.equals(aspirador)){ //Não deixa aspirar a si próprio
                                System.out.println("Como? Só isso mesmo, tipo, como você pretende aspirar a si próprio? Você não tem um espelho? Ou você só quer me fazer de palhaço mesmo?");
                                continue;
                            }else if (roboAspirado instanceof Aspirador){ //Apenas um print
                                System.out.println("Uau, você realmente quer eliminar um de seus semelhantes? Sinistro... mas tudo bem, eu não fui programado pra me importar com isso");
                                break;
                            }else if (roboAspirado.getPosicao()[2] == 0){ //Não deixa aspirar um robô que não está no chão
                                System.out.println("De fato eu concordo que seria bem melhor se ele conseguisse eliminar essas pragas aéreas, mas infelizmente ele não tem essa capacidade. Então vamos tentar de novo");
                            }else{
                                break;
                            }
                        }
                        try{
                            ambiente.removerEntidade(roboAspirado);
                            aspirador.setPosicao(roboAspirado.getPosicao()[0], roboAspirado.getPosicao()[1], 0); //Seta a posição do aspirador na posição do robô que ele vai eliminar
                            System.out.println("O " + roboAspirado.getNome() + " foi eliminado com sucesso!");
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
                    }else if (comando == 4){ //Checa as baterias dos sensores
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
                            }else if (comando == 1 || comando == 2){ //Checar atributos do sensor escolhido
                                metodosSensores(scanner, aspirador, aspirador.getSensores().get(comando));
                                break;
                            }
                        }
                    }else if (comando == 5){ //Enviar uma mensagem para outro robô
                        metodosRobosComunicaveis(ambiente, scanner, aspirador);
                    }

                }else if (roboEscolhido instanceof Drone){ //Mostra os métodos do robô drone
                    System.out.println("A única coisa boa com esse daí é entregar novos rastejantes");
                    Drone drone = ((Drone) roboEscolhido);
                    comando = lerInteiro("Você deseja fazer o que?\n1 - Subir\n2 - Descer\n3 - Entregar um Pacote\n4 - Analisar sensores\n5 - Enviar uma mensagem", scanner);
                    if (comando == 1 || comando == 2){ //Subir ou descer o drone
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
                            for (Entidade entidade : ambiente.getListaEntidades()) {
                                if (!(entidade instanceof Robo)){ //Verifica se a entidade é um Robô
                                    continue; //Se não for segue para a próxima iteração
                                }else{
                                    Robo robo = (Robo) entidade; //Faz o cast para robô
                                    contaCoordenadasRobos++;
                                    if ((coordenadaX == robo.getPosicao()[0] && coordenadaY == robo.getPosicao()[1] && robo.getPosicao()[2] == 0)){
                                        break;
                                    }
                                }
                            }
                            if (contaCoordenadasRobos == ambiente.getNumRobosAmbiente()){
                                break;
                            }else{
                                System.out.println("Puts, que chato, aparentemente já tem alguém ocupando essa posição. Mas claro que se você quiser mandar ele pra lá eu não vou poder impedir");
                                while (true) {
                                    int verifica = lerInteiro("1 - Sim, eu quero que ele vá pra lá\n2 - Não, eu quero que ele vá pra outro lugar", scanner);
                                    if (verifica == 1){
                                        System.out.println("Ok, você quem é o chefe aqui, só não diga que eu não avisei");
                                    }else if (verifica == 2){
                                        System.out.println("Ótimo, você não é tão mal caráter assim, vamos tentar de novo");
                                    }else{ //Continua pedindo até ser dada uma opção válida
                                        System.out.println("Cara, é uma coisa tão simples, sério, é literalmente só escolher um dos números que eu mostrei... Eu não sou pago o suficiente pra isso");
                                        continue;
                                    }
                                    break;
                                }
                            }
                        }
                        try{
                            drone.executarTarefa("entregar pacote", coordenadaX, coordenadaY, nomePacote, ambiente);
                            //Indica se o drone conseguiu entregar o pacote
                            System.out.println("O " + nomePacote +" foi entregue com sucesso!");
                        }catch (RoboDesligadoException e){ //Indica que drone está desligado
                            System.err.println(e.getMessage());
                        }catch (SensorDesligadoException e){ //Indica que algum sensor do drone está desligado
                            System.err.println(e.getMessage());
                        }catch (ColisaoException e){
                            obstaculoAchado(drone, ambiente); //Printa interações com obstáculos
                            for (Entidade entidade : ambiente.getListaEntidades()) {
                                if (!(entidade instanceof Robo)){ //Verifica se a entidade é um robô
                                    continue; //Se não for segue para a próxima iteração
                                }else{
                                    if (entidade.getNome().equals(nomePacote)){ //Caso o pacote tenha sido derrubado mas não destruído
                                        System.out.println("Seu pacote foi derrubado no caminho...que decepção... Atualmente o " + nomePacote + " está nas coordenadas: (" + drone.getPosicao()[0] + ", " + drone.getPosicao()[1] + ", " + 0 + ") Tá esperando o que? VAI VER SE ELE TÁ BEM!");
                                        int verifica = lerInteiro("1 - Estou indo ver ele agora\n2 - Sou mal caráter e vou ignorá-lo", scanner);
                                        if (verifica == 1){
                                            System.err.println("É bom mesmo...");
                                        }else{
                                            System.out.println("Estou gostando cada vez menos de você");
                                        }
                                        break;
                                    }
                                }
                            }
                            System.err.println(e.getMessage());
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
                            }else if (comando == 1 || comando == 2){ //Checar atributos do sensor escolhido
                                metodosSensores(scanner, drone, drone.getSensores().get(comando - 1));
                                break;
                            }
                        }
                    }else if (comando == 5){ //Enviar uma mensagem para outro robô
                        metodosRobosComunicaveis(ambiente, scanner, drone);
                    }
                }else if (roboEscolhido instanceof Passaro){ //Mostra os métodos do robô passaro
                    System.out.println("Sinceramente eu nem sei porque os criadores desenvolveram esses daí");
                    Passaro passaro = ((Passaro) roboEscolhido);
                    comando = lerInteiro("Você deseja fazer o que?\n1 - Subir\n2 - Descer\n3 - Mover que nem alguém normal (ou quase)\n4 - Analisar sensores", scanner);
                    if (comando == 1 || comando == 2){ //Subir ou descer o robô
                        metodosRobosAereos(passaro, comando, scanner, ambiente);
                    }else if (comando == 3){
                        //mover o pássaro
                        System.out.println("Quanto você quer mover ele?");
                        int deltaX = lerInteiro("Passos em x: ", scanner); 
                        int deltaY = lerInteiro("Passos em y: ", scanner);
                        try{
                            //Tratamento dos erros de sensor desligado e robô desligado ao tentar acionar os sensores 
                            try{
                                passaro.acionarSensores();
                            }catch (RoboDesligadoException e) { //Se o robô estiver desligado, não consegue monitorar o ambiente
                                System.err.println(e.getMessage());
                            }catch (SensorDesligadoException e) { //Se algum sensor estiver desligado, não consegue monitorar o ambiente
                                System.err.println(e.getMessage());
                            }
                            passaro.executarTarefa("mover", deltaX, deltaY, ambiente);
                            //pegar a qtd de desvios
                            int qtdDesvios = passaro.getQtddesvios();
                            //imprimir a qtd de desvios
                            System.out.println("Você fez " + qtdDesvios + " desvios no caminho");
                        }catch(RoboDesligadoException e){
                            System.err.println(e.getMessage());
                        }catch(SensorDesligadoException e){
                            System.err.println(e.getMessage());
                        }catch(ColisaoException e){
                            obstaculoAchado(passaro, ambiente);
                            System.err.println(e.getMessage());
                        }
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
                            }else if (comando == 1 || comando == 2){ //Checar atributos do sensor escolhido
                                metodosSensores(scanner, passaro, passaro.getSensores().get(comando - 1));
                                break;
                            }
                        }
                    }
                }else if (roboEscolhido instanceof Rover){ //Mostra os métodos do robô rover
                    System.out.println("Ele me lembra um carinha de um filme antigo... não consigo lembrar qual é");
                    Rover rover = ((Rover) roboEscolhido);
                    comando = lerInteiro("Você deseja fazer o que?\n1 - Andar por aí\n2 - Empurrar\n3 - Alterar a Velocidade Máxima\n4 - Analisar sensores", scanner);
                    if (comando == 1){ //Método normal de mover o  Rover
                        System.out.println("Você quer mover para onde?");
                        while (true){
                            int deltaX = lerInteiro("Passos em x: ", scanner); 
                            int deltaY = lerInteiro("Passos em y: ", scanner);
                            try{
                                double taxaRover = rover.getSensorVelocidade().porcentoVelocidade(rover.getSensorVelocidade().monitorar(deltaX, deltaY, rover), rover.getVelocidadeMaxima());
                                String velRover = String.format("%.2f", taxaRover);
                                try{
                                    //Tratamento dos erros de sensor desligado e robô desligado ao tentar acionar os sensores 
                                    try{
                                        rover.acionarSensores();
                                    }catch (RoboDesligadoException e) { //Se o robô estiver desligado, não consegue monitorar o ambiente
                                        System.err.println(e.getMessage());
                                        return; 
                                    }catch (SensorDesligadoException e) { //Se algum sensor estiver desligado, não consegue monitorar o ambiente
                                        System.err.println(e.getMessage());
                                        return; 
                                    }
                                    rover.executarTarefa("mover", deltaX, deltaY, ambiente);
                                    obstaculoAchado(rover, ambiente);
                                    System.out.println("Você andou a "+ velRover + "% da velocidade máxima");
                                    if (rover.getSensorVelocidade().isMuito(taxaRover)){
                                        System.out.println("Quase um SpeedRacer! Impressionante!");
                                    }
                                    System.out.println("Você empurrou "+ rover.getQtdRobosEmpurrados(ambiente) + " robôs e derrubou " + rover.getRobosDerrubados() + "robôs durante sua caminhada tranquila em " + ambiente.getNomeAmbiente());
                                    break;
                                }catch(RoboDesligadoException e){ //O robô só pode se mover se estiver ligado
                                    System.err.println(e.getMessage());
                                }catch(SensorDesligadoException e){ //Se ele tentar se mover com um sensor descarregado ele indica que isso não é possível
                                    System.err.println(e.getMessage());
                                }catch(ColisaoException e){ //Se ele se moveu e acabou a bateria do sensor de proximidade no meio da locomoção ele poderá colidir com algum obstáculo
                                    System.err.println(e.getMessage());
                                }
                            }catch(VelocidadeMaximaAtingidaException e){ //Se ele tiver ultrapassado o limite de velocidade ele irá pedir novamente o quanto ele quer que o robô ande e indicará qual foi o erro
                                System.err.println(e.getMessage());
                            }
                        }
                    }else if (comando == 2){ //Método para empurrar um robô específico
                        System.out.println("Parece que você tem uma richa com alguém, não é mesmo? Me diz quem é e a gente esbarra nele");
                        Robo inimigo = null;
                        while (true) {
                            int numRoboEmpurrar = 1;
                            boolean podeEmpurrar = false;
                            for (Entidade entidade : ambiente.getListaEntidades()){
                                if (!(entidade instanceof Robo)){ //Verifica se a entidade é um robô
                                    continue; //Se não for, segue para a próxima iteração
                                }else{                                 
                                    Robo robo = (Robo) entidade; //Faz o cast para robô
                                    System.out.println(numRoboEmpurrar++ + " - " + robo.getNome() + " - " + robo.getClass().getSimpleName());       
                                    if (!robo.equals(roboEscolhido) && robo.getPosicao()[2] == 0){ //Só pode empurrar robôs que não sejam o próprio rover e que estejam no chão
                                        podeEmpurrar = true;
                                    }
                                }
                            }
                            if (!podeEmpurrar){ //Se não tiver nenhum robô que possa ser empurrado, ele não deixa o usuário continuar
                                System.out.println("Que fim sem graça dessa novela, não tem ninguém pra você empurrar");
                                break;
                            }
                            String nomeRoboInimigo = scanner.nextLine();
                            for (Entidade entidade : ambiente.getListaEntidades()) {
                                if (!(entidade instanceof Robo)){ //Verifica se a entidade é um robô
                                    continue; //Se não for segue para a próxima iteração
                                }else if (entidade.getNome().equals(nomeRoboInimigo)){
                                    inimigo = (Robo) entidade;
                                }
                            }
                            if (inimigo == null){ //Se for escolhido um número inválido volta a pedir o número
                                System.out.println("Ah sim, é claro, você quer que eu adivinhe um robô que você escolheu e que não existe? Que legal, mas não sou tão bom assim, então por favor escolha um dos nomes mostrados");
                                continue;
                            }else if (inimigo.equals(roboEscolhido)){ //Não deixa empurrar a si próprio
                                System.out.println("Não seja tão duro consigo mesmo, vamos lá, deve haver outra pessoa que você odeia mais do que a si próprio");
                                continue;
                            }else if (inimigo instanceof Rover){
                                System.out.println("Uhhhh, um caso de família será? Me sinto assistindo uma novela mexicana");
                                break;
                            }else if (inimigo.getPosicao()[2] == 0){
                                System.out.println("Eu também teria ranço desses tipinhos aí, se eles chegarem perto do chão eu te aviso pra você barruar neles");
                                continue;
                            }
                            else{
                                break;
                            }
                        }
                        // Empurrar algum robô
                        try{
                            int[] posInicialInimigo = {inimigo.getPosicao()[0], inimigo.getPosicao()[1]};
                            //Tratamento dos erros de sensor desligado e robô desligado ao tentar acionar os sensores 
                            try{
                                rover.acionarSensores();
                            }catch (RoboDesligadoException e) { //Se o robô estiver desligado, não consegue monitorar o ambiente
                                System.err.println(e.getMessage());
                                return; 
                            }catch (SensorDesligadoException e) { //Se algum sensor estiver desligado, não consegue monitorar o ambiente
                                System.err.println(e.getMessage());
                                return; 
                            }
                            rover.executarTarefa("mover", inimigo.getPosicao()[0] - rover.getPosicao()[0], inimigo.getPosicao()[1] - rover.getPosicao()[1], ambiente);
                            obstaculoAchado(rover, ambiente);
                            if (rover.getPosicao()[0] == posInicialInimigo[0]){ // Só consegue empurrar no caso de ter conseguido andar até a posição que ele estava antes, ou seja, se não houvesse nenhum obstáculo entre eles
                                int numAnaliseRobosEmpurrados = 0;
                                for (Entidade entidade : ambiente.getListaEntidades()) {
                                    if (!(entidade instanceof Robo)){ //Verifica se a entidade é um robô
                                        continue; //Se não for, segue para a próxima iteração
                                    }else{
                                        Robo robo = (Robo) entidade;
                                        if (robo.getNome().equals(inimigo.getNome())){ 
                                            System.out.println("O + " + inimigo + " recebeu o recado... ele não gostou muito, se eu fosse você eu dormiria de olho aberto essa noite");
                                            break;
                                        }
                                        numAnaliseRobosEmpurrados++;
                                    }
                                }
                                if (numAnaliseRobosEmpurrados != ambiente.getNumRobosAmbiente()){ // Se o robô caiu do mapa, avisa que você empurrou ele para fora
                                    System.out.println("Acho que você foi um pouco longe demais, o " + inimigo.getNome() + " acabou caindo pra fora de " + ambiente.getNomeAmbiente() + " e não vai voltar mais. Se esse era o recado que você queria, estou ficando mais intrigado com essa novela");
                                    int qtdEliminados = rover.getRobosDerrubados();
                                    if (qtdEliminados > 1){
                                        System.out.println("Eita, e aparentemente não foi só ele, você acabou derrubando " + qtdEliminados + " robôs no processo... oops");
                                    }
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
                            }else if (comando == 1 || comando == 2){ //Checar atributos do sensor escolhido
                                metodosSensores(scanner, rover, rover.getSensores().get(comando - 1));
                                break;
                            }
                        }
                    }
                }

            }else if(comando == 3){ //Ligar ou desligar um robô
                System.out.println("Qual robô você deseja ligar ou desligar? Digite o nome do robô escolhido");
                String nomeRoboMudaEstado;
                Robo roboMudaEstado = null;
                while (true) { //Pede o nome até ser digitado um nome de robô existente no ambiente
                    int contadorRoboEstado = 1;
                    for (Entidade entidade : ambiente.getListaEntidades()) {
                        if (!(entidade instanceof Robo)){ //Verifica se a entidade é um robô
                            continue; //Se não for, segue para a próxima iteração
                        }else{
                            System.out.println(contadorRoboEstado++ + " - " + entidade.getNome());
                        }
                    }
                    nomeRoboMudaEstado = scanner.nextLine();
                    for (Entidade entidade : ambiente.getListaEntidades()) {
                        if (!(entidade instanceof Robo)){ //Verifica se a entidade é um robô
                            continue; //Se não for, segue para a próxima iteração
                        }else{
                            if (entidade.getNome().equals(nomeRoboMudaEstado)){
                                roboMudaEstado = (Robo) entidade; //Faz o cast para robô
                            }
                        }
                    }
                    if (roboMudaEstado == null){
                        System.out.println("Não sou capaz de mudar o estado de robôs que não estão no ambiente. Digite um nome de robô válido");
                    }else{
                        break;
                    }  
                }
                int escolhaEstado = lerInteiro("Perfeito, o que deseja fazer com o "+ nomeRoboMudaEstado + "?\n1 - Ligar\n2 - Desligar" , scanner);
                if (escolhaEstado == 1){
                    if (roboMudaEstado.getEstadoRobo().equals(EstadoRobo.LIGADO)){
                        System.out.println("O "+ nomeRoboMudaEstado + " já se encontra ligado.");
                    }else if(roboMudaEstado.getEstadoRobo().equals(EstadoRobo.DESLIGADO)){
                        roboMudaEstado.desligar(); //Desliga o robô
                        System.out.println("O " + nomeRoboMudaEstado + " foi desligado com sucesso!");
                        if (roboMudaEstado instanceof RoboAereo){
                            System.out.println("Que bom, não aguentava mais ele");
                        }
                    }
                }else if (escolhaEstado == 2){
                    if (roboMudaEstado.getEstadoRobo().equals(EstadoRobo.DESLIGADO)){
                        System.out.println("O "+ nomeRoboMudaEstado + " já se encontra desligado.");
                    }else if (roboMudaEstado.getEstadoRobo().equals(EstadoRobo.LIGADO)){
                        roboMudaEstado.ligar(); //Liga o robô
                        System.out.println("O " + nomeRoboMudaEstado + " foi ligado com sucesso!");
                        if (roboMudaEstado instanceof RoboAereo){
                            System.out.println("Que droga... digo, yay.");
                        }
                    }
                }else{ //Caso seja digitado um número inválido
                    System.out.println("Comando inválido, recomece o processo.");
                }
                
            }else if (comando == 4){ //Bloco para mostrar a lista de robôs
                if (ambiente.getNumRobosAmbiente() == 0){
                    System.out.println("Oops, parece que você ainda não criou nenhum robô.");
                }else{
                    System.out.println("Vamos dar uma olhada em quem você já criou até agora!");
                    int contadorRobo = 1;
                    for (Entidade entidade : ambiente.getListaEntidades()) {
                        if (!(entidade instanceof Robo)){ //Verifica se a entidade é um robô
                            continue; //Se não for segue para a próxima iteração
                        }else{
                            Robo robo = (Robo) entidade;
                            System.out.println(contadorRobo++ + " - " + robo.getNome() + " - " + robo.getClass().getSimpleName() +  " - Posição: " + robo.getPosicao()[0] + ", " + robo.getPosicao()[1] + ", " + robo.getPosicao()[2] + " - " + robo.getEstadoRobo());
                        }
                    }
                    System.out.println("Mas calma lá marujo isso daqui é so pra visualização, se quiser fazer algo com eles você vai precisar ver as ações possíveis com cada um deles na simulação");
                }
            }else if(comando == 5){ // Bloco para mostrar a lista de obstáculos
                if (ambiente.getNumObstaculosAmbiente() == 0){
                    System.out.println("Oops, parece que " + ambiente.getNomeAmbiente() + " não possui nenhum defeito, impressionante! Mesmo que irrealistíco");
                }else{
                    System.out.println("Vamos dar uma olhada nos obstáculos existentes em "+ ambiente.getNomeAmbiente());
                    int contadorObstaculo = 1;
                    for (Entidade entidade : ambiente.getListaEntidades()) {
                        if (!(entidade instanceof Obstaculo)){ //Verifica se a entidade é um obstáculo
                            continue; //Se não for, segue para a próxima iteração
                        }else{
                            Obstaculo obstaculo = (Obstaculo) entidade;
                            System.out.println(contadorObstaculo++ + " - " + obstaculo.getTipoObstaculo() + " - Posição (X1,Y1): (" + obstaculo.getX() + "," + obstaculo.getY() + "), " + "Posição (X2,Y2): (" + obstaculo.getPosX2() + "," + obstaculo.getPosY2() + ")");
                        }
                    }
                }
            }else if (comando == 6) {  // Bloco para exibir as mensagens do log
                System.out.println("Exibindo mensagens do log:");
                List<String> logs = CentralComunicacao.getInstancia().getMensagensFormatadas(); // Obtém as mensagens formatadas do log na CentralComunicacao
                exibirMensagensCentral(logs);
                for (String logEntry : logs) {
                    System.out.println(logEntry); // Exibe cada mensagem formatada
                }
            }else if(comando == 7){
                int alturaVizualizacao = lerInteiro("Perfeito! Infelizmente no momento não posso te dar uma vizualização completa do ambiente por limitações gráficas, então preciso que você me diga uma altura que esteja dentro dos limites do ambiente para eu te mostrar um corte no plano XY nessa altura", scanner);
                while (true) {
                    if (alturaVizualizacao < 0){ //Se a altura pedida for menor que 0
                        alturaVizualizacao = lerInteiro("Seria bem legal poder ir para baixo da terra, mas meus sensores tem medo do escuro, então vou ter que pedir para você escolher uma altura acima da terra", scanner);
                    }else if(alturaVizualizacao > ambiente.getLimites()[2]){
                        alturaVizualizacao = lerInteiro("Ao infinito e além! É o que eu diria se eu pudesse estar acima dos céus, que inveja daqueles que podem voar. Escolha uma altura dentro dos limites de " + ambiente.getNomeAmbiente(), scanner);
                    }else{
                        break;
                    }
                }
                for (int j = ambiente.getLimites()[1] - 1; j >= 0; j--){
                    for (int i = 0; i < ambiente.getLimites()[0]; i++){
                        System.out.println(ambiente.vizualizarMapa(alturaVizualizacao)[i][j]);
                    }
                }
            }else if (comando == 8){ //Ver descrição de entidade
                int escolheEntidadeDescricao = lerInteiro("É sempre bom conhecer um pouco mais sobre suas entidades antes de fazer algo com elas. De qual entidade você quer saber?\n1 - Robô\n2 - Obstáculo", scanner);
                if (escolheEntidadeDescricao == 1){
                    Robo exemploRobo = new Aspirador("teste", "sul", 0, 0, 10, 100); 
                    System.out.println(exemploRobo.getDescricao());
                }else if(escolheEntidadeDescricao == 2){
                    Obstaculo exemploObstaculo = new Obstaculo(0, 0, 0, 0, TipoObstaculo.PORTAO, ambiente);
                    System.out.println(exemploObstaculo.getDescricao());
                }else{
                    System.out.println("Entidade desconhecida, não é possível acessar uma descrição.");
                }
            }else{ //Caso seja digitado um número inválido
                System.out.println("Foi mal, por enquanto eu só sei contar até 9... entendeu? entendeu? porque é 0-indexado hahahahaha... Aff que usuário chato");
            }
        }
        System.out.println("Obrigado por usar o sistema de simulação, não esqueça de avaliar nossos serviços, espero que tenha gostado! Se não gostou: Eu não ligo! Eu sou só um robô. Até mais!");
        scanner.close();
    }  
    /**
     * Exibe todas as mensagens registradas na central de comunicações dos robôs
     */
    public static void exibirMensagensCentral(List<String> mensagens) { //CONFERIR E CONSERTAR
        System.out.println("\n--- Log de Comunicações ---");
        if (mensagens.isEmpty()) {
            System.out.println("Nenhuma mensagem trocada.");
        } else {
            for (String msg : mensagens) {
                System.out.println(msg);
            }
        }
        System.out.println("---------------------------\n");
    }
    //Quando já tem o nome que o usuário quer colocar no Robô que está criando, na lista de Robôs
    /**
     * Quando já existe no ambiente um robô com o nome que o usuário quer colocar no ambiente exibe aleatoriamente mensagens pedindo para ele tentar inserir outro nome
     * @param scanner
     * @param mensagensNomeJaExistente
     * @param ambiente
     * @return nome do robô criado após conferência
     */
    public static String exibirMensagemAleatoria(Scanner scanner, ArrayList<String> mensagensNomeJaExistente, Ambiente ambiente){ 
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
            for (Entidade entidade : ambiente.getListaEntidades()){
                if (!(entidade instanceof Robo)){ //Verifica se a entidade é um robô
                    continue; // Se não for, segue para a próxima iteração
                }else{
                    Robo robo = (Robo) entidade;
                    if (robo.getNome().equals(nome)){ //Printa uma das mensagens da lista de mensagens possíveis de erro de nome já existente
                        String mensagemMostrada = mensagensNomeJaExistente.get(random.nextInt(mensagensNomeJaExistente.size()));
                        System.out.println(mensagemMostrada);
                        nome = scanner.nextLine();
                        nomeExiste = true;
                        break;
                    } 
                }
            }
            if (!nomeExiste){ //Quando tiver analisado todos os nomes na lista de robôs ativos, pode prosseguir na criação do robô
                break;
            }
        }
        return nome;
    }
    /**
     * Cria os robôs aéreos no ambiente
     * @param scanner
     * @param mensagensNomeJaExistente
     * @param ambiente
     * @param tipoRobo
     * @throws ColisaoException
     */
    public static void criaRoboAereo(Scanner scanner,ArrayList<String> mensagensNomeJaExistente, Ambiente ambiente, int tipoRobo) throws ColisaoException{ 
        String nomeRoboAereo = exibirMensagemAleatoria(scanner, mensagensNomeJaExistente, ambiente); //Analisa se o nome escolhido já existe
        System.out.println("Direção: ");
        String direcao = leDirecao(scanner);
        int[] coordenadas = lerCoordenadas(scanner, true, ambiente);
        if (tipoRobo == 0){
            System.out.println("Para o pacote a ser entregue");
            int tempoLocomocaoTerrestre = leTempo(scanner);
            Drone drone = new Drone(nomeRoboAereo, direcao, coordenadas[0], coordenadas[1], coordenadas[2], tempoLocomocaoTerrestre, ambiente.getLimites()[2]);
            ambiente.adicionarEntidade(drone);   

            SensorAltitude sensorAltitude = new SensorAltitude(ambiente.getLimites()[2], "Sensor de Altitude");
            SensorProximidade sensorProximidade = new SensorProximidade(1, "Sensor de Proximidade");
            // Adiciona os sensores ao drone
            drone.adicionarSensores(sensorAltitude);
            drone.adicionarSensores(sensorProximidade);
            System.out.println("É... você criou um rover pelo menos! E tem esse " + drone.getNome() + " também... yay");
        }else if (tipoRobo == 1){
            Passaro passaro = new Passaro(nomeRoboAereo, direcao, coordenadas[0], coordenadas[1], coordenadas[2], ambiente.getLimites()[2]);
            ambiente.adicionarEntidade(passaro);

            SensorAltitude sensorAltitude = new SensorAltitude(ambiente.getLimites()[2], "Sensor de Altitude");
            SensorProximidade sensorProximidade = new SensorProximidade(1, "Sensor de Proximidade");
            // Adiciona os sensores ao drone
            passaro.adicionarSensores(sensorAltitude);
            passaro.adicionarSensores(sensorProximidade);
            System.out.println("Meus superiores dizem que eu deveria dizer parabéns por ter criado o " + passaro.getNome() +" nesse ponto, mas eu me recuso.");
        }
    }
    /**
     * Cria os robôs terrestres no ambiente
     * @param scanner
     * @param mensagensNomeJaExistente
     * @param ambiente
     * @param tipoRobo
     * @throws ColisaoException
     */
    public static void criaRoboTerrestre(Scanner scanner, ArrayList<String> mensagensNomeJaExistente, Ambiente ambiente, int tipoRobo) throws ColisaoException{
        String nomeRoboTerrestre = exibirMensagemAleatoria(scanner, mensagensNomeJaExistente, ambiente); //Analisa se o nome escolhido já existe
        System.out.println("Direção: ");
        String direcao = leDirecao(scanner);
        int[] coordenadas = lerCoordenadas(scanner, false, ambiente);
        int velMax = leVelocidade(scanner);
        int tempoLocomocaoTerrestre = leTempo(scanner);
        if (tipoRobo == 0){
            Aspirador aspirador = new Aspirador(nomeRoboTerrestre, direcao, coordenadas[0], coordenadas[1], velMax, tempoLocomocaoTerrestre);
            ambiente.adicionarEntidade(aspirador);

            SensorVelocidade sensorVelocidade = new SensorVelocidade(ambiente.getLimites()[0] + ambiente.getLimites()[1], "Sensor de Velocidade");
            SensorProximidade sensorProximidade = new SensorProximidade(1, "Sensor de Proximidade");
            // Adiciona os sensores ao drone
            aspirador.adicionarSensores(sensorVelocidade);
            aspirador.adicionarSensores(sensorProximidade);
            System.out.println("VAMOOOOOOOOOOOOOO!!! Você é incrível por ter criado o " + aspirador.getNome());
        }else if (tipoRobo == 1){
            Rover rover = new Rover(nomeRoboTerrestre, direcao, coordenadas[0], coordenadas[1], velMax, tempoLocomocaoTerrestre);
            ambiente.adicionarEntidade(rover);

            SensorVelocidade sensorVelocidade = new SensorVelocidade(ambiente.getLimites()[0] + ambiente.getLimites()[1], "Sensor de Velocidade");
            SensorProximidade sensorProximidade = new SensorProximidade(1, "Sensor de Proximidade");
            // Adiciona os sensores ao drone
            rover.adicionarSensores(sensorVelocidade);
            rover.adicionarSensores(sensorProximidade);
            System.out.println("Meus olhos se enchem de óleo toda vez, o " + rover.getNome() + " é tão lindo");
        }
    }
    /**
     * Lê coordenadas identificando erros de inserção de não inteiros e se já existe alguma entidade já existente nelas
     * @param scanner
     * @param roboVoador
     * @param ambiente
     * @return Um array de tamanho 3 com as coordenadas X, Y e Z respectivamente
     * @throws ColisaoException
     */
    public static int[] lerCoordenadas(Scanner scanner, boolean roboVoador, Ambiente ambiente) throws ColisaoException{ 
        boolean lugarOcupado = false;
        while (true){
            int posicaoX = lerInteiro("Posição X: ", scanner);
            int posicaoY = lerInteiro("Posição Y: ", scanner);
            int posicaoZ = 0;
            if (roboVoador){ //Se o robô for voador inclui o eixo z
                posicaoZ = lerInteiro("Posição Z: ", scanner);
            }
            if (ambiente.estaOcupado(posicaoX, posicaoY, posicaoZ)){ //Verifica se já existe alguma entidade ocupando o local pedido
                lugarOcupado = true;
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
    /**
     * Tratamento de excessões para leituras de inteiros
     * @param mensagem a ser mostrada para que o usuário saiba a que inteiro está se referindo quando for pedido no buffer
     * @param scanner
     * @return inteiro lido após conferências
     * @implNote Existe uma chance de 10% em que o programa irá encerrar caso o usuário digite algo que não é um inteiro.
     * @implNote Sim isso é uma feature :)
     */
    public static int lerInteiro(String mensagem, Scanner scanner) { 
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
    /**
     * Tratamento de erros ao digitar a direção para que sejam aceitas apenas norte, sul, leste e oeste
     * @implNote Não importa se a direção será escrita com letras maiúsculas ou minúsculas, apenas se foi escrito corretamente
     */
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
    /**
     * Limpa o terminal após um ciclo de iterações para ficar mais agradável para o usuário no terminal
     * @param sistemaOperacional
     */
    public static void limparTela(String sistemaOperacional){ 
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
    /**
     * Lê a velocidade para os robôs terrestres que serão criados, não permitindo velocidades negativas nem nulas
     */
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
    /**
     * Lê o tempo de locomoção para os robôs terrestres que serão criados, não permitindo tempos negativos nem nulos
     */
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
    /**
     * Métodos dos robôs aéreos básicos (Subir ou descer)
     * @throws ColisaoException
     * @throws AlturaMaximaAtingidaException
     * @throws ErroComunicacaoException 
     * @throws RoboDesligadoException 
     * @throws SensorDesligadoException 
     */
    public static void metodosRobosAereos(RoboAereo roboAereo, int subirOuDescer, Scanner scanner, Ambiente ambiente) throws ColisaoException, AlturaMaximaAtingidaException, SensorDesligadoException, RoboDesligadoException, ErroComunicacaoException{ 
        if (roboAereo.getSensorAltitude(roboAereo).getBateria() == 0){
            System.out.println("O sensor de altitude do seu robô está sem bateria, você não vai conseguir subir ou descer mais até recarregá-lo");
        }else{
            int deltaZ;
            if (subirOuDescer == 1){
                deltaZ = lerInteiro("Quantos metros você deseja subir? ", scanner);
                roboAereo.executarTarefa("subir", deltaZ, ambiente);
            }else{
                deltaZ = lerInteiro("Quantos metros você deseja descer? ", scanner);
                roboAereo.executarTarefa("descer", deltaZ, ambiente);
            }
        }
        if (roboAereo.getSensorAltitude(roboAereo).isBateriaBaixa()){
            System.out.println("A bateria do seu sensor de altitude está baixa, se você não recarregá-lo ele pode parar de funcionar e você pode acabar caindo");
        }
        obstaculoAchado(roboAereo, ambiente);
        try{ //Trata AlturaMaximaAtingidaException
            System.out.println("Você está atualmente a " + roboAereo.getSensorAltitude(roboAereo).porcentoAltura(roboAereo.getPosicao()[2], ambiente.getLimites()[2]) + "% da altura máxima do seu ambiente");
            if (roboAereo.getSensorAltitude(roboAereo).isMuito(roboAereo.getSensorAltitude(roboAereo).porcentoAltura(roboAereo.getPosicao()[2], ambiente.getLimites()[2]))){
                System.out.println("O mito de Ícarus narra a história de um anjo que tentou voar muito perto do sol e acabou morrendo, cuidado para acabar não virando uma lenda da pior maneira");
            }
        }catch(AlturaMaximaAtingidaException e){
            System.err.println(e.getMessage());
        }
    }
    /**
     * Métodos dos sensores básicos (Checagem de baterias)
     */
    public static void metodosSensores(Scanner scanner, Robo robo, Sensor<?> sensor){
        int comando;
        while (true){
            comando = lerInteiro("O que deseja fazer?\n1 - Checar Bateria\n2 - Recarregar Bateria\n3 - Checar o raio", scanner);
            if (comando < 1 || comando > 3){ //Se for escolhido um número inválido volta a pedir o número
                System.out.println("Qual a dificuldade de fazer o que eu mando às vezes? Escolha um dos números que eu mostrei");
                continue;
            }
            if (comando == 1){
                System.out.println("A bateria do "+ sensor.getNomeSensor() +" está em " + robo.getSensores().get(comando - 1).getBateria() + "%");
                break;
            }else if (comando == 2){
                int carga = lerInteiro("Qual a quantidade de carga que você deseja colocar?", scanner);
                robo.getSensores().get(comando - 1).recarregarBateria(carga);
                System.out.println("A bateria do "+ sensor.getNomeSensor() +" agora está em " + sensor.getBateria() + "%");
                break;
            }else if (comando == 3){
                System.out.println("O " + sensor.getNomeSensor() + " possui raio de funcionamento de " + sensor.getRaio());
            }
        }
    }
    /**
     * Printa as interações que os robôs possuem ao encontrarem obstáculos no caminho
     */
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
            if (sensorProx.getUltimoTipoDetectado().equals(TipoEntidade.OBSTACULO)){ //Se o aspirador identificar um obstáculo em vez de um robô ele age diferente
                Obstaculo obstaculoIdentificado = null;
                for (Entidade entidade : ambiente.getListaEntidades()) {
                    if (!(entidade instanceof Obstaculo)){ //Verifica se a entidade é um obstáculo                        
                        continue; //Se não for, segue para a próxima iteração
                    }else{
                        Obstaculo obstaculo = (Obstaculo) entidade; //Faz o cast para entidade
                        if (obstaculo.getX() <= robo.getPosicao()[0] + passos[0] && obstaculo.getPosX2() >= robo.getPosicao()[0] + passos[0] && obstaculo.getY() <= robo.getPosicao()[1] + passos[1] && obstaculo.getPosY2() >= robo.getPosicao()[1] + passos[1] && obstaculo.getZ() == robo.getPosicao()[2]) {
                            obstaculoIdentificado = obstaculo;
                            break;
                        }
                    }
                }
                if (obstaculoIdentificado != null){
                    if (sensorProx.getBateria() != 0 && obstaculoIdentificado.getTipoObstaculo().equals(TipoObstaculo.PORTAO)){ //Se o obstáculo identificado for um portão, o robô pode passar por ele, mas ele só saberá que passou por ele caso a bateria não tenha acabado
                        System.out.println("O " + robo.getNome() + " Adentrou majestosamente por um portão de " + ambiente.getNomeAmbiente() + "!");
                        if (robo instanceof RoboAereo){
                            System.out.println("Tá, devo adimitir que isso foi lindo de se ver... mas se te perguntarem EU NUNCA FALEI ISSO");
                        }
                    }else if (obstaculoIdentificado.getTipoObstaculo().equals(TipoObstaculo.ARVORE)){ //Se o obstáculo identificado for uma árvore, o robô não pode passar por ele
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
    /**
     * Printa o mapa 2D para que o usuário entenda como posicionar um obstáculo durante a sua criação
     */
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
    /**
     * Mostra o que os robôs comunicáveis podem fazer
     * @param ambiente
     * @param scanner
     * @param remetente
     * @throws SensorDesligadoException
     * @throws ColisaoException
     */
    public static void metodosRobosComunicaveis(Ambiente ambiente, Scanner scanner, Comunicavel remetente) throws SensorDesligadoException, ColisaoException{
        //Faz o cast para qual dos robôs comunicáveis o remetente é
        Robo remetente1;
        if (remetente instanceof Drone){ 
            remetente1 = (Drone) remetente;
        }else{
            remetente1 = (Aspirador) remetente;
        }
        System.out.println("Para quem você deseja enviar sua mensagem? (Digite o nome ao lado do número)");
        while (true) {
            int numAnaliseRobosMensagem = 1;
            for (Entidade entidade : ambiente.getListaEntidades()){
                if (!(entidade instanceof Robo)){ //Verifica se a entidade é um robô
                    continue; //Se não for, segue para a próxima iteração
                }else{
                    System.out.println(numAnaliseRobosMensagem++ + " - " + entidade.getNome());
                }
            }
            String receptorMensagem = scanner.nextLine();
            Robo receptor = null;
            for (Entidade entidade : ambiente.getListaEntidades()) { //Procura a entidade que receberá a mensagem a partir do nome
                if (entidade.getNome().equals(receptorMensagem)){
                    receptor = (Robo) entidade;
                    break;
                }
            }
            if (receptor == null){ //Se não for digitado um nome de um robô existente na lista ele pede novamente 
                System.out.println("Amigos imaginários são divertidos na teoria, mas eu não posso te ajudar a enviar mensagens pra eles, tente escolher um robô que exista na nossa simulação");
            }else{
                try{ //Tenta enviar a mensagem
                    System.out.print("Que mensagem você deseja enviar?");
                    String mensagem = scanner.nextLine();
                    remetente1.executarTarefa("enviar mensagem", receptor, mensagem);
                    System.out.println("[" + ((Robo) remetente).getNome() + " para " + receptorMensagem + "]: " + mensagem + " (Mensagem enviada)");
                    break;
                }catch(ErroComunicacaoException e){ //Indica que o remetente não é comunicável 
                    System.err.println(e.getMessage());
                }catch(RoboDesligadoException e){ //Indica que o remetente estava desligado
                    System.err.println(e.getMessage());
                }
            }
        }
        
    }
}