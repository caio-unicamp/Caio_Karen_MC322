# Simulador de Robôs Caio_Karen_MC322
Esse projeto é um "Simulador de Robôs", ou seja: é um ambiente computacional que permite modelar, testar e analisar o comportamento de robôs em diferentes cenarios sem a necessidade de hardware fısico.

# Instalação
Clone este repositório e instale as dependências:

1.  git clone https://github.com/caio-unicamp/Caio_Karen_MC322.git
2. cd Caio_Karen_MC322
3. sudo apt install openjdk-21-jre-headless  # version 21.0.6+7-1~24.04.1 

# Como usar
Para usar o Simulador como está agora em desenvolvimento no lab02, é necessário estar na pasta Lab_02 e executar os seguintes comandos no Terminal:

1. javac -d *.java
2. java -cp bin meu.pacote.Main

## instruções 
 Siga as instruções no Terminal, começa com as definições do seu Ambiente
 Após isso você poderá digitar um comando específico para povoar seu ambiente de robôs

### Para criar um Robô, digite:
 1. 1 
    Após isso, você escolher uma categoria para ele. As categorias são Áereo que possue movimento em 3 dimensões e Terrestre, que se movimenta em 2 direções (z=0).
    Para um Robô Áereo, digite
    2. 1
        Na categoria de Robô Áereo, existem 2 sub-categorias, os Drones que possuem um robô Rover dentro, porém caso você os mova na direção de um obstáculo ele derruba o Robô Rover e para de se mover, além do Drone tem os Pássaros que, não possuem Robôs dentro mas conseguem sempre chegar ao seu destino desviando dos obstáculos.
        Para um Drone, digite:
        3. 1
        Para um Pássaro, digite
        3. 2
    Para um Robô Terrestre, digite:
    2. 2
        Na categoria de Robô Terrestre, existem 2 sub-categorias, os Aspiradores que se movimentam e destroem todos os obstáculos em seu caminho e os Rover que se movimentam e caso encontrem algum objeto, o empurra até seu destino final.
        Para um um Aspirador, digite:
        3. 1
        Para um Rover, digite:
        3. 2
    Por fim, precisamos de um nome para o Robô, digite o nome escolhido no terminal, caso o nome seja igual a outro nome de um Robô no Ambiente, uma mensagem irá aparecer você precisará escolher um nome diferente:
    Para escolher um nome, digite:
    2. "nomeEscolhido"
    Parabéns, você criou um Robô!

### Para mover um Robô, digite:
 1. 2
 Agora que você quer mover um Robô, saibam que seus robôs movem ignorantes ao Teorema de Pitágoras, ou seja não se movem na diagonal mas sim pelos eixos x e depois y.
 No Terminal, seus Robôs devem ter aparecido conectados a um número como o seguinte exemplo: 
 1 - "nome1¨
 2 - "nome2"
 Para mover o Robô que você quer, digite o número n associado à ele:
 2. n
 Agora que você escolheu o seu Robô, aparecerá na tela os comandos associados a ele
 ###### Ainda não há como utilizar os métodos pelo terminal, faremos mais a frente.

### Para verificar lista de Robôs, digite:
 1. 3
 Deve aparecer a lista de Robôs, não há nada para o Usuário mexer, Digite outro comando dos apresentados previamente (para criar um Robô, mover um Robô e Encerrar a sessão)

### Para encerrar a sessão basta digitar o comando:
 1. 0
 Parabéns, você encerrou a sessão