package gerenciador;

import modelo.Comentario;
import modelo.Post;
import modelo.Usuario;
import ui.ConsoleColors;

import java.util.ArrayList;
import java.util.List;

/**
 * Gerenciador responsável por todas as operações relacionadas a posts na rede social.
 *
 * @author Jonathan Sost Dos Santos
 */
public class GerenciadorPosts {
    private List<Post> posts;
    private Integer proximoId;
    private GerenciadorUsuarios gerenciadorUsuarios;

    /**
     * Construtor do gerenciador de posts.
     * Inicializa a lista de posts e o contador de IDs.
     */
    public GerenciadorPosts() {
        this.posts = new ArrayList<>();
        this.proximoId = 1;
    }

    public void setGerenciadorUsuarios(GerenciadorUsuarios gerenciadorUsuarios) {
        this.gerenciadorUsuarios = gerenciadorUsuarios;
    }

    /**
     * Cria um novo post no sistema.
     * Adiciona o post na lista com um ID único.
     */
    public void criar(Post post) {
        post.setId(proximoId);
        posts.add(post);
        proximoId++;
    }

    /**
     * Busca um post pelo seu ID.
     *
     * @param id O ID do post a ser buscado
     * @return O post encontrado ou null se não existir
     */
    public Post buscarPorId(int id) {
        for (Post p : posts) {
            if (p.getId() == id) {
                return p;
            }
        }

        return null;
    }

    /**
     * Busca posts pelo ID de um usuário.
     *
     * @param idUsuario O ID do usuário do qual serão buscados os posts
     * @return Os posts encontrados
     */
    public List<Post> listarPorUsuario(int idUsuario) {
        List<Post> postsDoUsuario = new ArrayList<>();

        for (Post p : posts) {
            if (p.getAutor().getId() == idUsuario) {
                postsDoUsuario.add(p);
            }
        }

        return postsDoUsuario;
    }

    /**
     * Ordena a lista de posts por data de forma crescente.
     *
     * @param posts A lista de posts a ser ordenada
     * @return Os posts ordenados
     */
    public List<Post> ordenarPorData(List<Post> posts) {
        posts.sort((post1, post2) -> {
            if (post1.getDataPublicacao() == null || post2.getDataPublicacao() == null)
                return 0;
            return post2.getDataPublicacao().compareTo(post1.getDataPublicacao());
        });

        return posts;
    }

    /**
     * Adiciona o usuário à lista de curtidas do post.
     *
     * @param idPost O ID do post a ser curtido
     * @param idUsuario O ID do usuário que está curtindo o post
     */
    public void curtir(int idPost, int idUsuario) {
        Usuario usuario = gerenciadorUsuarios.buscarPorId(idUsuario);

        for (Post p : posts) {
            if (p.getId() == idPost) {
                p.adicionarCurtida(usuario);
            }
        }
    }

    /**
     * Remove o usuário da lista de curtidas do post.
     *
     * @param idPost O ID do post a ser descurtido
     * @param idUsuario O ID do usuário que está descurtindo o post
     */
    public void descurtir(int idPost, int idUsuario) {
        Usuario usuario = gerenciadorUsuarios.buscarPorId(idUsuario);

        for (Post p : posts) {
            if (p.getId() == idPost) {
                p.removerCurtida(usuario);
            }
        }
    }

    /**
     * Adiciona um comentário ao post.
     *
     * @param idPost O ID do post a ser comentado
     * @param comentario O comentário que será publicado
     */
    public void comentar(int idPost, Comentario comentario) {
        buscarPorId(idPost).adicionarComentario(comentario);
    }

    /**
     * Deleta o post selecionado.
     *
     * @param id O ID do post a ser deletado
     */
    public boolean deletar(int id) {
        return posts.removeIf(p -> p.getId() == id);
    }

    public boolean editar(int id, String conteudo) {
        Post post = buscarPorId(id);

        if (post != null) {
            for (Post p : posts) {
                if (post.getId().equals(p.getId())) {
                    if (!conteudo.isBlank()) {
                        p.setConteudo(conteudo);
                    }

                    System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Post atualizado com sucesso!");
                    return true;
                }
            }
        }

        return false;
    }
}
