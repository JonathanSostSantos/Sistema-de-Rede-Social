package ui;

import gerenciador.GerenciadorPosts;
import gerenciador.GerenciadorUsuarios;
import modelo.Comentario;
import modelo.Post;
import modelo.Usuario;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class MenuUsuario {
    private MenuPrincipal menu;
    private Scanner leitor;
    private Usuario usuarioLogado;
    private GerenciadorUsuarios gerenciador;

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public MenuUsuario() {
        this.leitor = new Scanner(System.in);
        menu = new MenuPrincipal();
        gerenciador = new GerenciadorUsuarios();
    }

    public void exibirMenu() {
        Integer opcaoSelecionada;
        System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT + "Bem-vindo(a) de volta, " + ConsoleColors.GREEN_BOLD_BRIGHT + usuarioLogado.getNome() + ConsoleColors.CYAN_BOLD_BRIGHT + "!" + ConsoleColors.RESET);
        while (true) {
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "\n\nO que você deseja fazer?" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "\n1- Criar post");
            System.out.println("2- Ver meu perfil");
            System.out.println("3- Buscar usuários");
            System.out.println("4- Gerenciar amizades");
            System.out.println("5- Ver feed de notícias");
            System.out.println("6- Logout" + ConsoleColors.RESET);
            opcaoSelecionada = menu.validarEntradaInteira(leitor.nextLine().trim());

            if (opcaoSelecionada != null) {
                switch (opcaoSelecionada) {
                    case 1:
                        criarPost();
                        break;
                    case 2:
                        verPerfil();
                        break;
                    case 3:
                        buscarUsuarios();
                        break;
                    case 4:
                        gerenciarAmizades();
                        break;
                    case 5:
                        verFeedNoticias();
                        break;
                    case 6:
                        System.out.println(ConsoleColors.YELLOW_UNDERLINED + "Logout realizado!" + ConsoleColors.RESET);
                        return;
                    default:
                        System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Opção inexistente." + ConsoleColors.RESET);
                }
            }
        }
    }

    private void criarPost() {
        String valorInserido;
        GerenciadorPosts gerenciador = new GerenciadorPosts();
        while (true) {
            System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT + "Digite o que você está pensando (máximo 255 caracteres): " + ConsoleColors.RESET);
            valorInserido = leitor.nextLine();

            if (valorInserido.isBlank()) break;

            if (usuarioLogado == null) {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Houve um erro com a autenticação de login. Retornando ao menu." + ConsoleColors.RESET);
                return;
            } else if (valorInserido.length() > 255) {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "O limite é de 255 caracteres por post." + ConsoleColors.RESET);
            } else if (valorInserido.length() <= 0) {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "O post não pode estar vazio." + ConsoleColors.RESET);
            } else {
                gerenciador.criar(new Post(usuarioLogado, valorInserido, LocalDateTime.now()));
                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Post publicado com sucesso!" + ConsoleColors.RESET);
                break;
            }
        }
    }

    private void verPerfil() {
        Integer opcaoSelecionada;

        while (true) {
            System.out.println(ConsoleColors.BLUE_UNDERLINED + usuarioLogado.getNome() + ConsoleColors.RESET);
            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "1- Ver posts");
            System.out.println("2- Amigos");
            System.out.println("3- Editar perfil");
            System.out.println("4- Voltar" + ConsoleColors.RESET);
            opcaoSelecionada = menu.validarEntradaInteira(leitor.nextLine());

            if (opcaoSelecionada != null) {
                switch (opcaoSelecionada) {
                    case 1:
                        verPosts();
                        break;
                    case 2:
                        gerenciarAmizades();
                        break;
                    case 3:
                        editarPerfil();
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Opção inexistente." + ConsoleColors.RESET);
                }
            }
        }
    }

    private void verPosts() {
        Integer pagina = 0;
        List<Post> posts = usuarioLogado.getPosts();
        Integer totalPosts = posts.size();
        Integer totalPaginas;
        Integer postsNaPagina;
        String valorInserido;
        Integer opcaoSelecionada;
        Post postSelecionado = null;
        Boolean postEstaSelecionado;

        if (totalPosts <= 0) {
            System.out.println("Você não possui nenhum post, que tal fazer seu primeiro?");
        } else {
            totalPaginas = totalPosts / 10;
            System.out.println("Seus posts:");

            while (true) {
                postsNaPagina = pagina.equals(totalPaginas) ? totalPosts % 10 : 10;
                for (int i = pagina * 10; i < (pagina * 10) + postsNaPagina; i++) {
                    System.out.println(ConsoleColors.WHITE + (1 + i % 10) + "- " + ConsoleColors.RESET + posts.get(i));
                }
                System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "\n" + (pagina > 0 ? pagina > 1 ? "<< <" : "<" : "") + " Página " + (pagina + 1) + " de " + (totalPaginas + 1) + (pagina < totalPaginas ? pagina < totalPaginas - 1 ? " > >>" : " >" : "" + ConsoleColors.RESET));
                System.out.println(ConsoleColors.WHITE_UNDERLINED + "(Selecione o post para interagir ou digite 0 para retornar)" + ConsoleColors.RESET);
                valorInserido = leitor.nextLine();
                switch (valorInserido.trim()) {
                    case "<<":
                        if (pagina > 1) pagina = 0;
                        break;
                    case "<":
                        if (pagina > 0) pagina--;
                        break;
                    case ">":
                        if (pagina < totalPaginas) pagina++;
                        break;
                    case ">>":
                        if (pagina < (totalPaginas - 1)) pagina = totalPaginas;
                        break;
                    case "0":
                        return;
                    default:
                        if (valorInserido.matches("[0-9]+")) {
                            opcaoSelecionada = menu.validarEntradaInteira(valorInserido);
                            if (opcaoSelecionada <= 10 && opcaoSelecionada > 0) {
                                postSelecionado = posts.get(opcaoSelecionada - 1 + (pagina * 10));
                            }

                            postEstaSelecionado = postSelecionado != null;

                            if (!postEstaSelecionado) {
                                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "O post selecionado é inválido!" + ConsoleColors.RESET);
                            }

                            while (postEstaSelecionado) {
                                System.out.println(postSelecionado);
                                System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "1- Mostrar curtidas");
                                System.out.println("2- Mostrar comentários");
                                System.out.println("3- Deletar");
                                System.out.println("4- Voltar" + ConsoleColors.RESET);

                                opcaoSelecionada = menu.validarEntradaInteira(leitor.nextLine());
                                if (opcaoSelecionada != null) {
                                    switch (opcaoSelecionada) {
                                        case 1:
                                            mostrarCurtidas(postSelecionado);
                                            break;
                                        case 2:
                                            mostrarComentarios(postSelecionado);
                                            break;
                                        case 3:
                                            deletarPost(postSelecionado);
                                            break;
                                        case 4:
                                            postEstaSelecionado = false;
                                            break;
                                    }
                                }
                            }
                        }
                        break;
                }
            }
        }
    }

    private void editarPerfil() {

    }

    private void buscarUsuarios() {
        String valorInserido;
        Integer opcaoSelecionada;
        List<Usuario> usuarios;
        Usuario usuarioSelecionado;
        Boolean ehAmigo;

        while (true) {
            System.out.println("Digite o nome do usuário a ser buscado:");
            valorInserido = leitor.nextLine();

            usuarios = gerenciador.buscarPorNome(valorInserido);

            if (usuarios.isEmpty()) {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Não foram encontrados resultados com esta pesquisa." + ConsoleColors.RESET);
                return;
            }

            for (int i = 0; i < usuarios.size(); i++) {
                System.out.println(ConsoleColors.WHITE_BOLD + (i + 1) + ConsoleColors.RESET + "- " + usuarios.get(i));
            }
            System.out.println(ConsoleColors.WHITE_UNDERLINED + "(Selecione o usuário para interagir ou digite 0 para retornar)" + ConsoleColors.RESET);

            opcaoSelecionada = menu.validarEntradaInteira(leitor.nextLine());

            if (opcaoSelecionada != null) {
                switch (opcaoSelecionada) {
                    case 0:
                        return;
                    default:
                        if (opcaoSelecionada < usuarios.size() - 1) {
                            usuarioSelecionado = usuarios.get(opcaoSelecionada);
                            ehAmigo = ehAmigo(usuarioLogado, usuarioSelecionado);
                            System.out.println(usuarioSelecionado);
                            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "1- " + (ehAmigo ? "Remover" : "Adicionar") + " amigo");
                            System.out.println("2- Voltar" + ConsoleColors.RESET);
                            opcaoSelecionada = menu.validarEntradaInteira(leitor.nextLine());

                            switch (opcaoSelecionada) {
                                case 1:
                                    if (ehAmigo) {
                                        usuarioLogado.removerAmigo(usuarioSelecionado);
                                        usuarioSelecionado.removerAmigo(usuarioLogado);
                                    } else {
                                        usuarioLogado.adicionarAmigo(usuarioSelecionado);
                                        usuarioSelecionado.adicionarAmigo(usuarioLogado);
                                    }
                                    break;
                                case 2:
                                    break;
                            }
                        }
                        break;
                }
            }
        }
    }

    private void gerenciarAmizades() {
        List<Usuario> amigos = usuarioLogado.getAmigos();
        Usuario amigo;
        Integer opcaoSelecionada;

        if (amigos.isEmpty()) {
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Sua lista de amigos está vazia.");
            return;
        }

        while (true) {
            System.out.println(ConsoleColors.BLUE_BACKGROUND + "==== Lista de amigos ====" + ConsoleColors.RESET);
            for (int i = 0; i < amigos.size(); i++) {
                System.out.println(ConsoleColors.WHITE_BOLD + (i + 1) + "- " + ConsoleColors.RESET + amigos.get(i));
            }

            System.out.println(ConsoleColors.WHITE_UNDERLINED + "(Selecione um amigo para interagir ou digite 0 para retornar)" + ConsoleColors.RESET);
            opcaoSelecionada = menu.validarEntradaInteira(leitor.nextLine());

            if (opcaoSelecionada != null && opcaoSelecionada < amigos.size()) {
                amigo = amigos.get(opcaoSelecionada);
                System.out.println(amigo);
                System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "1- Remover amigo");
                System.out.println("2- Voltar" + ConsoleColors.RESET);

                switch (opcaoSelecionada) {
                    case 1:
                        usuarioLogado.removerAmigo(amigo);
                        amigo.removerAmigo(usuarioLogado);
                        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Amigo removido com sucesso!" + ConsoleColors.RESET);
                        break;
                    case 2:
                        return;
                }
            }
        }
    }

    private void verFeedNoticias() {

    }

    private void mostrarCurtidas(Post post) {
        List<Usuario> curtidas = post.getCurtidas();

        if (curtidas.isEmpty()) {
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Este post não possui curtidas." + ConsoleColors.RESET);
            return;
        }
        System.out.println(ConsoleColors.BLUE_BACKGROUND + "==== Curtidas ====" + ConsoleColors.BLUE_UNDERLINED);
        for (Usuario u : curtidas) {
            System.out.println(u.getNome());
        }
        System.out.println(ConsoleColors.RESET);
    }

    private void mostrarComentarios(Post post) {
        List<Comentario> comentarios = post.getComentarios();

        if (comentarios.isEmpty()) {
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Este post não possui comentários." + ConsoleColors.RESET);
            return;
        }
        System.out.println(ConsoleColors.BLUE_BACKGROUND + "==== Comentários ====" + ConsoleColors.RESET);
        for (Comentario c : comentarios) {
            System.out.println(c);
        }
        System.out.println(ConsoleColors.RESET);
    }

    private void deletarPost(Post post) {
        usuarioLogado.getPosts().remove(post);
    }

    private boolean ehAmigo(Usuario usuario1, Usuario usuario2) {
        for (Usuario u : usuario1.getAmigos()) {
            if (u == usuario2) return true;
        }

        return false;
    }
}
