package gerenciador;

import modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorUsuarios {
    private List<Usuario> usuarios;
    private Integer proximoId;

    public GerenciadorUsuarios() {
        this.usuarios = new ArrayList<>();
        this.proximoId = 1;
    }

    public void cadastrar(Usuario usuario) {
        usuario.setId(proximoId);
        usuarios.add(usuario);
        proximoId++;
    }

    public Usuario buscarPorId(int id) {
        for(Usuario u : usuarios) {
            if (u.getId() == id) {
                return u;
            }
        }

        return null;
    }

    public Usuario buscarPorUsername(String username) {
        for(Usuario u : usuarios) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return u;
            }
        }

        return null;
    }

    public List<Usuario> buscarPorNome(String nome) {
        List<Usuario> usuariosARetornar = new ArrayList<>();

        for(Usuario u : usuarios) {
            if (u.getNome().equalsIgnoreCase(nome)) {
                usuariosARetornar.add(u);
            }
        }

        return usuariosARetornar;
    }

    public Usuario buscarPorEmail(String email) {
        for(Usuario u : usuarios) {
            if (u.getNome().equalsIgnoreCase(email)) {
                return u;
            }
        }

        return null;
    }

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

    public boolean deletar(int id) {
        for(Usuario u : usuarios) {
            if (u.getId().equals(id)) {
                usuarios.remove(u);
                return true;
            }
        }

        return false;
    }

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
