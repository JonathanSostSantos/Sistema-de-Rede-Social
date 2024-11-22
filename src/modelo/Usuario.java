package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private Integer id;
    private String nome;
    private String username;
    private String email;
    private String senha;
    private LocalDateTime dataCadastro;
    private List<Usuario> amigos;
    private List<Post> posts;

    public Usuario(String nome, String username, String email, String senha, LocalDateTime dataCadastro) {
        this.nome = nome;
        this.username = username;
        this.email = email;
        this.senha = senha;
        this.dataCadastro = dataCadastro;
        this.amigos = new ArrayList<>();
        this.posts = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public List<Usuario> getAmigos() {
        return amigos;
    }

    public List<Post> getPosts() {
        return posts;
    }

    @Override
    public String toString() {
        return "Usuario {" +
                " ID: " + id +
                ", Nome: '" + nome + '\'' +
                ", Username: '" + username + '\'' +
                ", Email: '" + email + '\'' +
                ", Senha: '" + senha + '\'' +
                ", Data de cadastro: " + dataCadastro +
                " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usuario usuario = (Usuario) o;

        return id.equals(usuario.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public void adicionarAmigo(Usuario amigo) {
        this.amigos.add(amigo);
    }

    public void removerAmigo(Usuario amigo) {
        this.amigos.remove(amigo);
    }

    public void adicionarPost(Post post) {
        this.posts.add(post);
    }
}
