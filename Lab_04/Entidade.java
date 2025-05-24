public class Entidade {
    private TipoEntidade tipoEntidade; //Tipo da entidade de acordo com o enum 

    public Entidade(TipoEntidade tipoEntidade){ //Constructor da Entidade
        this.tipoEntidade = tipoEntidade;
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
            return "AAAAAAAAAAAA";
        }
    }

    public char getSimbolo(){ //Retorna o símbolo de representação da entidade
        return this.getTipoEntidade().getSimbolo();
    }
}