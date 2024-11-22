package modelo;

import java.time.LocalDateTime;

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
}
