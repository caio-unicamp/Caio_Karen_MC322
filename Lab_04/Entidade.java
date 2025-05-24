public class Entidade {
    private final int x;
    private final int y;
    private final int z;
    private final TipoEntidade tipoEntidade; //Tipo da entidade de acordo com o enum referente a ele

    public Entidade(TipoEntidade tipoEntidade, int x, int y, int z){ //Constructor da Entidade
        this.tipoEntidade = tipoEntidade;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public TipoEntidade getTipoEntidade(){ //Retorna o tipo da entidade de acordo com o enum de entidades 
        return tipoEntidade;
    }

    public String getDescricao(){ //Retorna uma descrição referente à entidade em questão
        if (this.getTipoEntidade().equals(TipoEntidade.VAZIO)){ //Descrição do VAZIO
            return "VAZIO";
        }else if (this.getTipoEntidade().equals(TipoEntidade.ROBO)){ //Descrição do ROBO
            return "ROBO";
        }else if (this.getTipoEntidade().equals(TipoEntidade.OBSTACULO)){ //Descrição do OBSTACULO
            return "OBSTACULO";
        }else{ //Descrição do DESCONHECIDO
            return "Entidade desconhecida, tem certeza que você escreveu corretamente?";
        }
    }

    public char getSimbolo(){ //Retorna o símbolo de representação da entidade
        return this.getTipoEntidade().getSimbolo();
    }

    public int getX(){ //Retorna a posição x da entidade
        return this.x;
    }

    public int getY(){ //Retorna a posição y da entidade
        return this.y;
    }

    public int getZ(){ //Retorna a posição z da entidade
        return this.z;
    }
}