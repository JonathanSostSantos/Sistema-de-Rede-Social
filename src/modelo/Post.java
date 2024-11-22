package modelo;

import ui.ConsoleColors;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Post {
    private Integer id;
    private Usuario autor;
    private String conteudo;
    private LocalDateTime dataPublicacao;
    private List<Usuario> curtidas;
    private List<Comentario> comentarios;
    private Integer proximoId = 1;

    public Post(Usuario autor, String conteudo, LocalDateTime dataPublicacao) {
        this.id = proximoId;
        this.autor = autor;
        this.conteudo = conteudo;
        this.dataPublicacao = dataPublicacao;
        this.curtidas = new ArrayList<>();
        this.comentarios = new ArrayList<>();
        proximoId++;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDateTime getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(LocalDateTime dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public List<Usuario> getCurtidas() {
        return curtidas;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        return ConsoleColors.BLUE_BOLD_BRIGHT + autor.getNome() + ConsoleColors.WHITE_BOLD + " @" + autor.getUsername() + ConsoleColors.RESET + " - " +
                ConsoleColors.WHITE_UNDERLINED + dataPublicacao.format(formatador) + ConsoleColors.RESET + "\n" +
                ConsoleColors.WHITE_BOLD_BRIGHT + conteudo +
                ConsoleColors.BLACK_BOLD_BRIGHT + "\n👍 " + curtidas.size() + "  💬 " + comentarios.size() + "\n";
    }

    public void adicionarCurtida(Usuario usuario) {
        for (Usuario curtidor : curtidas) {
            if (usuario == curtidor) {
                System.out.printf("O usuário %s já curtiu este post.%n", curtidor.getNome());
                return;
            }
        }

        curtidas.add(usuario);
        System.out.println("Curtida adicionada com sucesso!");
    }

    public void removerCurtida(Usuario usuario) {
        for (Usuario curtidor : curtidas) {
            if (usuario == curtidor) {
                System.out.println("Curtida removida com sucesso!");
                return;
            }
        }

        System.out.println("Este usuário não curtiu o post, por isto, não foi possível removê-la.");
    }

    public void adicionarComentario(Comentario comentario) {
        this.comentarios.add(comentario);
    }
}
