package modelo;

import ui.ConsoleColors;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Representa um comentário na rede social.
 * Classe responsável por manter as informações de um comentário individual no sistema.
 *
 * @author Jonathan Sost Dos Santos
 */
public class Comentario {
    private Integer id;
    private Usuario autor;
    private String conteudo;
    private LocalDateTime dataComentario;
    private Post post;
    private Integer proximoId = 1;

    /**
     * Construtor para criar um novo comentário sem passar ID como parâmetro.
     * O ID será atribuído utilizando o atributo local proximoId.
     *
     * @param autor Autor do comentário
     * @param conteudo Conteúdo do comentário - não pode ser vazio
     * @param post O post onde o comentário está sendo publicado
     */
    public Comentario(Usuario autor, String conteudo, Post post) {
        this.id = proximoId;
        this.autor = autor;
        this.conteudo = conteudo;
        this.dataComentario = LocalDateTime.now();
        this.post = post;
        proximoId++;
    }

    /**
     * Retorna uma representação em String do comentário já formatado.
     *
     * @return String formatada com todos os dados do comentário que serão apresentados ao usuário
     */
    @Override
    public String toString() {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        return ConsoleColors.BLUE_BOLD_BRIGHT + autor.getNome() + ConsoleColors.WHITE_BOLD + " @" + autor.getUsername() + ConsoleColors.RESET + " - " +
                ConsoleColors.WHITE_UNDERLINED + dataComentario.format(formatador) + ConsoleColors.RESET + "\n" +
                ConsoleColors.WHITE_BOLD_BRIGHT + conteudo + ConsoleColors.RESET + "\n";
    }
}
