package gerenciador;

import modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Gerenciador responsável por todas as operações relacionadas a usuários na rede social.
 *
 * @author Jonathan Sost Dos Santos
 */
public class GerenciadorUsuarios {
    private List<Usuario> usuarios;
    private Integer proximoId;

    /**
     * Construtor do gerenciador de usuários.
     * Inicializa a lista de usuários e o contador de IDs.
     */
    public GerenciadorUsuarios() {
        this.usuarios = new ArrayList<>();
        this.proximoId = 1;
    }

    /**
     * Cadastra um novo usuário no sistema.
     * Adiciona o usuário na lista com um ID único.
     */
    public void cadastrar(Usuario usuario) {
        usuario.setId(proximoId);
        usuarios.add(usuario);
        proximoId++;
    }

    /**
     * Busca um usuário pelo seu ID.
     *
     * @param id O ID do usuário a ser buscado
     * @return O usuário encontrado ou null se não existir
     */
    public Usuario buscarPorId(int id) {
        for (Usuario u : usuarios) {
            if (u.getId() == id) {
                return u;
            }
        }

        return null;
    }

    /**
     * Busca um usuário pelo seu username.
     *
     * @param username O username do usuário a ser buscado
     * @return O usuário encontrado ou null se não existir
     */
    public Usuario buscarPorUsername(String username) {
        for (Usuario u : usuarios) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return u;
            }
        }

        return null;
    }

    /**
     * Busca um usuário pelo seu nome.
     *
     * @param nome O nome do usuário a ser buscado
     * @return O usuário encontrado ou null se não existir
     */
    public List<Usuario> buscarPorNome(String nome) {
        List<Usuario> usuariosARetornar = new ArrayList<>();

        for (Usuario u : usuarios) {
            if (u.getNome().toLowerCase().contains(nome.toLowerCase())) {
                usuariosARetornar.add(u);
            }
        }

        return usuariosARetornar;
    }

    /**
     * Busca um usuário pelo seu email.
     *
     * @param email O email do usuário a ser buscado
     * @return O usuário encontrado ou null se não existir
     */
    public Usuario buscarPorEmail(String email) {
        for (Usuario u : usuarios) {
            if (u.getNome().equalsIgnoreCase(email)) {
                return u;
            }
        }

        return null;
    }

    /**
     * Atualiza as informações do usuário conforme passadas pelo parâmetro.
     * Verifica se as informações são vazias ou não.
     * Caso estejam vazias, não haverá alteração. Caso não, haverá alteração.
     *
     * @param usuario As novas informações para a atualização do usuário
     * @return Retorna true se o usuário for atualizado e vice-versa
     */
    public boolean atualizar(Usuario usuario) {
        for (Usuario u : usuarios) {
            if (usuario.getId().equals(u.getId())) {
                if (!usuario.getNome().isBlank()) {
                    u.setNome(usuario.getNome());
                }
                if (!usuario.getUsername().isBlank()) {
                    u.setUsername(usuario.getUsername());
                }
                if (!usuario.getEmail().isBlank()) {
                    u.setEmail(usuario.getEmail());
                }
                if (!usuario.getSenha().isBlank()) {
                    u.setSenha(usuario.getSenha());
                }

                System.out.println("Usuário atualizado com sucesso! Resultado: " + u);
                return true;
            }
        }

        return false;
    }

    /**
     * Deleta um usuário do sistema.
     *
     * @param id O ID do usuário a ser deletado
     * @return Retorna true se o usuário for deletado e vice-versa
     */
    public boolean deletar(int id) {
        for (Usuario u : usuarios) {
            if (u.getId().equals(id)) {
                usuarios.remove(u);
                return true;
            }
        }

        return false;
    }

    /**
     * Faz com que os dois usuários cujos IDs foram passados por parâmetro se adicionem mutualmente
     *
     * @param idUsuario1 O ID do usuário que está adicionando
     * @param idUsuario2 O ID do usuário que está sendo adicionado
     */
    public void adicionarAmizade(int idUsuario1, int idUsuario2) {
        Usuario usuario1 = buscarPorId(idUsuario1);

        if (usuario1 == null) {
            System.out.println("Ocorreu um erro: não foi possível buscar o usuário logado.");
            return;
        }

        Usuario usuario2 = buscarPorId(idUsuario2);

        if (usuario2 == null) {
            System.out.println("Ocorreu um erro: não foi possível encontrado usuário a ser adicionado.");
            return;
        }

        usuario1.adicionarAmigo(usuario2);
        usuario2.adicionarAmigo(usuario1);
    }

    /**
     * Faz com que os dois usuários cujos IDs foram passados por parâmetro removam a amizade mutualmente
     *
     * @param idUsuario1 O ID do usuário que está removendo dos amigos
     * @param idUsuario2 O ID do usuário que está sendo removido dos amigos
     */
    public void removerAmizade(int idUsuario1, int idUsuario2) {
        Usuario usuario1 = buscarPorId(idUsuario1);

        if (usuario1 == null) {
            System.out.println("Ocorreu um erro: não foi possível buscar o usuário logado.");
            return;
        }

        Usuario usuario2 = buscarPorId(idUsuario2);

        if (usuario2 == null) {
            System.out.println("Ocorreu um erro: não foi possível encontrado usuário a ser removido.");
            return;
        }

        usuario1.removerAmigo(usuario2);
        usuario2.removerAmigo(usuario1);
    }
}
