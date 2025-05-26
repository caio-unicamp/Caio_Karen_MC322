/**
 * Interface que define o contrato para todas as entidades que podem existir no ambiente.
 * Garante que qualquer objeto no ambiente tenha uma posição, tipo, descrição e representação.
 */
public interface Entidade {
    
    public int getX(); //Retorna a posição x da entidade

    public int getY(); //Retorna a posição y da entidade

    public int getZ(); //Retorna a posição z da entidade

    TipoEntidade getTipoEntidade(); //Retorna o tipo da entidade de acordo com o enum de entidades 

    public String getDescricao(); //Retorna uma descrição referente à entidade em questão

    public char getSimbolo(); //Retorna o símbolo de representação da entidade

}