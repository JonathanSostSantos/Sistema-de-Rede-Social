package modelo;

import ui.ConsoleColors;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Comentario {
    private Integer id;
    private Usuario autor;
    private String conteudo;
    private LocalDateTime dataComentario;
    private Post post;
    private Integer proximoId = 1;

    public Comentario(Usuario autor, String conteudo, Post post) {
        this.id = proximoId;
        this.autor = autor;
        this.conteudo = conteudo;
        this.dataComentario = LocalDateTime.now();
        this.post = post;
        proximoId++;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        return ConsoleColors.BLUE_BOLD_BRIGHT + autor.getNome() + ConsoleColors.RESET + " - " +
                ConsoleColors.WHITE_UNDERLINED + dataComentario.format(formatador) + ConsoleColors.RESET + "\n" +
                ConsoleColors.WHITE_BOLD_BRIGHT + conteudo + ConsoleColors.RESET + "\n";
    }
}
