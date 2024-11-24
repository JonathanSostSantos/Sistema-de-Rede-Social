package modelo;

import ui.ConsoleColors;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um post na rede social.
 * Classe responsável por manter as informações de um post individual no sistema.
 *
 * @author Jonathan Sost Dos Santos
 */
public class Post {
    private Integer id;
    private Usuario autor;
    private String conteudo;
    private LocalDateTime dataPublicacao;
    private List<Usuario> curtidas;
    private List<Comentario> comentarios;
    private Integer proximoId = 1;

    /**
     * Construtor para criar um novo post sem passar ID como parâmetro.
     * O ID será atribuído utilizando o atributo local proximoId.
     * Utilizado também para inicializar as listas de curtidas e comentários.
     *
     * @param autor Autor do post
     * @param conteudo Conteúdo do post - não pode ser vazio
     * @param dataPublicacao A data em que o post foi publicado
     */
    public Post(Usuario autor, String conteudo, LocalDateTime dataPublicacao) {
        this.id = proximoId;
        this.autor = autor;
        this.conteudo = conteudo;
        this.dataPublicacao = dataPublicacao;
        this.curtidas = new ArrayList<>();
        this.comentarios = new ArrayList<>();
        autor.adicionarPost(this);
        proximoId++;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Usuario getAutor() {
        return autor;
    }

    public String getConteudo() {
        return conteudo;
    }

    public LocalDateTime getDataPublicacao() {
        return dataPublicacao;
    }

    public List<Usuario> getCurtidas() {
        return curtidas;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    /**
     * Retorna uma representação em String do post já formatado.
     *
     * @return String formatada com todos os dados do post que serão apresentados ao usuário
     */
    @Override
    public String toString() {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        return ConsoleColors.BLUE_BOLD_BRIGHT + autor.getNome() + ConsoleColors.WHITE_BOLD + " @" + autor.getUsername() + ConsoleColors.RESET + " - " +
                ConsoleColors.WHITE_UNDERLINED + dataPublicacao.format(formatador) + ConsoleColors.RESET + "\n" +
                ConsoleColors.WHITE_BOLD_BRIGHT + conteudo +
                ConsoleColors.BLACK_BOLD_BRIGHT + "\n👍 " + curtidas.size() + "  💬 " + comentarios.size() + "\n";
    }

    /**
     * Adiciona uma curtida ao post dada pelo usuário passado como parâmetro.
     *
     * @param usuario O usuário que está curtindo o post
     */
    public void adicionarCurtida(Usuario usuario) {
        for (Usuario curtidor : curtidas) {
            //Se o usuário já curtiu este post, avisar ao usuário e não curtir novamente.
            if (usuario == curtidor) {
                System.out.printf("O usuário %s já curtiu este post.%n", curtidor.getNome());
                return;
            }
        }

        curtidas.add(usuario);
        System.out.println("Curtida adicionada com sucesso!");
    }

    /**
     * Remove uma curtida ao post dada pelo usuário passado como parâmetro.
     *
     * @param usuario O usuário que está descurtindo o post
     */
    public void removerCurtida(Usuario usuario) {
        for (Usuario curtidor : curtidas) {
            //Se o usuário curtiu o post, remove a curtida. Caso contrário, avisa o usuário e não faz nada.
            if (usuario == curtidor) {
                curtidas.remove(curtidor);
                System.out.println("Curtida removida com sucesso!");
                return;
            }
        }

        System.out.println("Este usuário não curtiu o post, por isto, não foi possível removr a curtida.");
    }

    /**
     * Adiciona um comentário ao post.
     *
     * @param comentario O comentário a ser adicionado ao post.
     */
    public void adicionarComentario(Comentario comentario) {
        this.comentarios.add(comentario);
    }

    /**
     * Verifica se o usuário já curtiu este post.
     *
     * @param usuario O usuário que será verificado.
     */
    public boolean jaCurtiu(Usuario usuario) {
        for (Usuario u : curtidas) {
            if (u.equals(usuario)) return true;
        }

        return false;
    }
}
