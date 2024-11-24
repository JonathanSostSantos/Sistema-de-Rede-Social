package modelo;

import ui.ConsoleColors;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um usuário na rede social.
 * Classe responsável por manter as informações de um usuário individual no sistema.
 *
 * @author Jonathan Sost Dos Santos
 */
public class Usuario {
    private Integer id;
    private String nome;
    private String username;
    private String email;
    private String senha;
    private LocalDateTime dataCadastro;
    private List<Usuario> amigos;
    private List<Post> posts;

    /**
     * Construtor para criar um novo usuário sem passar ID como parâmetro.
     * Utilizado também para inicializar as listas de amigos e posts.
     *
     * @param nome     O nome do usuário
     * @param username O apelido do usuário que será utilizado para login
     * @param email    O email do usuário
     * @param senha    A senha do usuário de login
     */
    public Usuario(String nome, String username, String email, String senha) {
        this.nome = nome;
        this.username = username;
        this.email = email;
        this.senha = senha;
        this.dataCadastro = LocalDateTime.now();
        this.amigos = new ArrayList<>();
        this.posts = new ArrayList<>();
    }

    /**
     * Construtor para criar um novo usuário passando ID como parâmetro.
     *
     * @param id       O ID do usuário
     * @param nome     O nome do usuário
     * @param username O apelido do usuário que será utilizado para login
     * @param email    O email do usuário
     * @param senha    A senha do usuário de login
     */
    public Usuario(Integer id, String nome, String username, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.username = username;
        this.email = email;
        this.senha = senha;
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

    /**
     * Retorna uma representação em String do usuário já formatado.
     *
     * @return String formatada com todos os dados do usuário que serão apresentados ao usuário
     */
    @Override
    public String toString() {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        return ConsoleColors.BLUE_BOLD_BRIGHT + nome +
                ConsoleColors.WHITE_BOLD + " @" + username +
                "\nData de cadastro: " + dataCadastro.format(formatador) + "\n";
    }

    /**
     * Verifica se dois usuários são iguais com base no ID.
     * Dois usuários são considerados iguais se possuem o mesmo ID.
     *
     * @param o Objeto a ser comparado
     * @return true se os usuários têm o mesmo ID, false caso contrário
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usuario usuario = (Usuario) o;

        return id.equals(usuario.id);
    }

    /**
     * Gera um código hash para o usuário com base no ID.
     *
     * @return O código hash gerado
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * Adiciona um amigo na lista de amizades do usuário.
     *
     * @param amigo O usuário a ser adicionado
     */
    public void adicionarAmigo(Usuario amigo) {
        this.amigos.add(amigo);
    }

    /**
     * Remove um amigo da lista de amizades do usuário.
     *
     * @param amigo O usuário a ser removido
     */
    public void removerAmigo(Usuario amigo) {
        this.amigos.remove(amigo);
    }

    /**
     * Adiciona um post na lista de posts do usuário.
     *
     * @param post O post a ser adicionado
     */
    public void adicionarPost(Post post) {
        this.posts.add(post);
    }
}
