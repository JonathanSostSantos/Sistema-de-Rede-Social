package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Post {
    private Integer id;
    private Usuario autor;
    private String conteudo;
    private LocalDateTime dataPublicacao;
    private List<Usuario> curtidas;
    private List<Comentario> comentarios;

    public Post(Usuario autor, String conteudo, LocalDateTime dataPublicacao) {
        this.autor = autor;
        this.conteudo = conteudo;
        this.dataPublicacao = dataPublicacao;
        this.curtidas = new ArrayList<>();
        this.comentarios = new ArrayList<>();
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

    @Override
    public String toString() {
        return "Post:\n\n" +
                "ID: " + id +
                "\nAutor: " + autor.getNome() +
                "\nConteudo: '" + conteudo + '\'' +
                "\nData de publicação: " + dataPublicacao +
                "\nCurtidas: " + curtidas.size() + "\n";
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
