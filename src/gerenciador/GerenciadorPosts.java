package gerenciador;

import modelo.Comentario;
import modelo.Post;
import modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorPosts {
    private List<Post> posts;
    private Integer proximoId;

    public GerenciadorPosts() {
        this.posts = new ArrayList<>();
        this.proximoId = 1;
    }

    public void criar(Post post) {
        post.setId(proximoId);
        posts.add(post);
        proximoId++;
    }

    public Post buscarPorId(int id) {
        for(Post p : posts) {
            if (p.getId() == id) {
                return p;
            }
        }

        return null;
    }

    public List<Post> listarPorUsuario(int idUsuario) {
        List<Post> postsDoUsuario = new ArrayList<>();

        for (Post p : posts) {
            if (p.getAutor().getId() == idUsuario) {
                postsDoUsuario.add(p);
            }
        }

        return postsDoUsuario;
    }

    public void curtir(int idPost, int idUsuario) {
        GerenciadorUsuarios gerenciador = new GerenciadorUsuarios();
        Usuario usuario = gerenciador.buscarPorId(idUsuario);

        for (Post p : posts) {
            if (p.getId() == idPost) {
                p.adicionarCurtida(usuario);
            }
        }
    }

    public void descurtir(int idPost, int idUsuario) {
        GerenciadorUsuarios gerenciador = new GerenciadorUsuarios();
        Usuario usuario = gerenciador.buscarPorId(idUsuario);

        for (Post p : posts) {
            if (p.getId() == idPost) {
                p.removerCurtida(usuario);
            }
        }
    }

    public void comentar(int idPost, Comentario comentario) {
        buscarPorId(idPost).adicionarComentario(comentario);
    }

    public boolean deletar(int id) {
        return posts.removeIf(p -> p.getId() == id);
    }
}
