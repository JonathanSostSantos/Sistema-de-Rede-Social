package gerenciador;

import modelo.Comentario;
import modelo.Post;
import modelo.Usuario;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GerenciadorPosts {
    private List<Post> posts;
    private Integer proximoId;
    private GerenciadorUsuarios gerenciadorUsuarios;

    public GerenciadorPosts() {
        this.posts = new ArrayList<>();
        this.proximoId = 1;
    }

    public void setGerenciadorUsuarios(GerenciadorUsuarios gerenciadorUsuarios) {
        this.gerenciadorUsuarios = gerenciadorUsuarios;
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

    public List<Post> ordenarPorData(List<Post> posts) {
        posts.sort(new Comparator<Post>() {
            public int compare(Post o1, Post o2) {
                if (o1.getDataPublicacao() == null || o2.getDataPublicacao() == null)
                    return 0;
                return o1.getDataPublicacao().compareTo(o2.getDataPublicacao());
            }
        });

        return posts;
    }

    public void curtir(int idPost, int idUsuario) {
        Usuario usuario = gerenciadorUsuarios.buscarPorId(idUsuario);

        for (Post p : posts) {
            if (p.getId() == idPost) {
                p.adicionarCurtida(usuario);
            }
        }
    }

    public void descurtir(int idPost, int idUsuario) {
        Usuario usuario = gerenciadorUsuarios.buscarPorId(idUsuario);

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
