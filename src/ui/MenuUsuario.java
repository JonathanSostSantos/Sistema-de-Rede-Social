package ui;

import gerenciador.GerenciadorPosts;
import gerenciador.GerenciadorUsuarios;
import modelo.Comentario;
import modelo.Post;
import modelo.Usuario;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Classe responsável pela interface do menu logado com o usuário via console.
 * Gerencia todas as interações do usuário com o menu logado do sistema.
 *
 * @author Jonathan Sost Dos Santos
 */
public class MenuUsuario {
    private MenuPrincipal menu;
    private Scanner leitor;
    private Usuario usuarioLogado;
    private GerenciadorUsuarios gerenciadorUsuarios;
    private GerenciadorPosts gerenciadorPosts;
    private SecretKey chave;

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public void setChave(SecretKey chave) {
        this.chave = chave;
    }

    public GerenciadorPosts getGerenciadorPosts() {
        return gerenciadorPosts;
    }

    /**
     * Construtor da classe MenuUsuario.
     * Inicializa o scanner, o menu principal, o gerenciador de usuários e o gerenciador de posts.
     */
    public MenuUsuario() {
        this.leitor = new Scanner(System.in);
        this.menu = new MenuPrincipal();
        this.gerenciadorUsuarios = new GerenciadorUsuarios();
        this.gerenciadorPosts = new GerenciadorPosts();
    }

    public void setGerenciadorUsuarios(GerenciadorUsuarios gerenciadorUsuarios) {
        this.gerenciadorUsuarios = gerenciadorUsuarios;
    }

    public void setGerenciadorPosts(GerenciadorPosts gerenciadorPosts) {
        this.gerenciadorPosts = gerenciadorPosts;
    }

    /**
     * Exibe o menu logado e processa as escolhas do usuário.
     * Loop principal do pós-login.
     */
    public void exibirMenu() {
        Integer opcaoSelecionada;

        System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT + "Bem-vindo(a) de volta, " + ConsoleColors.GREEN_BOLD_BRIGHT + usuarioLogado.getNome() + ConsoleColors.CYAN_BOLD_BRIGHT + "!" + ConsoleColors.RESET);
        while (true) {
            //Apresenta as principais opções do menu logado e solicita que o usuário informe o que deseja fazer.
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "\n\nO que você deseja fazer?" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "\n1- Criar post");
            System.out.println("2- Ver meu perfil");
            System.out.println("3- Buscar usuários");
            System.out.println("4- Gerenciar amizades");
            System.out.println("5- Ver feed de notícias");
            System.out.println("6- Logout" + ConsoleColors.RESET);

            //Faz a validação do input gerado pelo usuário para garantir que não ocorra uma Exception ao tentar converter a String para int
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

    /**
     * Valida as informações do post e realiza a publicação.
     */
    private void criarPost() {
        String valorInserido;
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
                gerenciadorPosts.criar(new Post(usuarioLogado, valorInserido, LocalDateTime.now()));
                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Post publicado com sucesso!" + ConsoleColors.RESET);
                break;
            }
        }
    }

    /**
     * Apresenta o perfil do usuário que está logado, além de dar as opções para ver posts, amigos e editar perfil.
     */
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

    /**
     * Apresenta os posts com um limite de 10 por página, assim como a opção de avançar e retornar as páginas.
     *
     * @param posts Os posts que serão apresentados.
     * @return O post que foi selecionado pelo usuário para interagir
     */
    private Integer listarPostsPaginados(List<Post> posts) {
        Integer pagina = 0;
        Integer totalPosts = posts.size();
        Integer totalPaginas;
        Integer postsNaPagina;
        String valorInserido;
        Integer opcaoSelecionada;
        Post postSelecionado;

        if (totalPosts <= 0) {
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Nenhum post a ser mostrado aqui.");
            return -1;
        }

        //Cada página possui 10 posts
        totalPaginas = totalPosts / 10;

        while (true) {
            //Se for a última página, o número de posts apresentados será o resto da divisão do total de posts por 10
            postsNaPagina = pagina.equals(totalPaginas) ? totalPosts % 10 : 10;
            for (int i = pagina * 10; i < (pagina * 10) + postsNaPagina; i++) {
                System.out.println(ConsoleColors.WHITE + (1 + i % 10) + "- " + ConsoleColors.RESET + posts.get(i));
            }
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "\n" + (pagina > 0 ? pagina > 1 ? "<< <" : "<" : "") + " Página " + (pagina + 1) + " de " + (totalPaginas + 1) + (pagina < totalPaginas ? pagina < totalPaginas - 1 ? " > >>" : " >" : "" + ConsoleColors.RESET));
            System.out.println(ConsoleColors.WHITE_UNDERLINED + "(Selecione o post para interagir ou digite 0 para retornar)" + ConsoleColors.RESET);
            valorInserido = leitor.nextLine();

            //Realiza a navegação pelas páginas de posts ou retorna o post com o qual o usuário deseja interagir.
            switch (valorInserido) {
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
                    return -1;
                default:
                    if (valorInserido.matches("[0-9]+")) {
                        opcaoSelecionada = menu.validarEntradaInteira(valorInserido);

                        if ((pagina.equals(totalPaginas) && opcaoSelecionada > totalPosts % 10) || (opcaoSelecionada > 10 || opcaoSelecionada < 0)) {
                            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "O post selecionado não existe." + ConsoleColors.RESET);
                            break;
                        }

                        postSelecionado = posts.get(opcaoSelecionada - 1 + (pagina * 10));

                        if (postSelecionado != null) {
                            return postSelecionado.getId();
                        }
                    }
                    break;
            }
        }
    }

    /**
     * Apresenta os posts do usuário que está logado no seu perfil.
     */
    private void verPosts() {
        Post postSelecionado;

        while (true) {
            System.out.println("Seus posts:");
            postSelecionado = gerenciadorPosts.buscarPorId(listarPostsPaginados(gerenciadorPosts.ordenarPorData(usuarioLogado.getPosts())));

            if (postSelecionado != null) {
                interagirComPost(postSelecionado);
            }
            break;
        }
    }

    /**
     * Edita o usuário logado com os dados informados pelo próprio.
     */
    private void editarPerfil() {
        usuarioLogado = menu.validarUsuario(2, usuarioLogado, chave);

        if (usuarioLogado != null) {
            gerenciadorUsuarios.atualizar(usuarioLogado);
        }
    }

    /**
     * Busca usuários com base no nome fornecido pelo usuário.
     */
    private void buscarUsuarios() {
        String valorInserido;
        Integer opcaoSelecionada;
        List<Usuario> usuarios;
        Usuario usuarioSelecionado;
        Boolean ehAmigo;

        while (true) {
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "Digite o nome do usuário a ser buscado:" + ConsoleColors.RESET);
            valorInserido = leitor.nextLine();

            //Traz os usuários que contêm o valor digitado no nome.
            usuarios = gerenciadorUsuarios.buscarPorNome(valorInserido);

            if (usuarios.isEmpty()) {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Não foram encontrados resultados com esta pesquisa." + ConsoleColors.RESET);
                return;
            }

            for (int i = 0; i < usuarios.size(); i++) {
                System.out.println(ConsoleColors.WHITE_BOLD + (i + 1) + ConsoleColors.RESET + "- " + usuarios.get(i));
            }
            System.out.println(ConsoleColors.WHITE_UNDERLINED + "(Selecione o usuário para interagir ou digite 0 para retornar)" + ConsoleColors.RESET);

            opcaoSelecionada = menu.validarEntradaInteira(leitor.nextLine());

            //Apresenta as opções para interagir com o usuário selecionado.
            if (opcaoSelecionada != null) {
                switch (opcaoSelecionada) {
                    case 0:
                        return;
                    default:
                        if (opcaoSelecionada <= usuarios.size()) {
                            usuarioSelecionado = usuarios.get(opcaoSelecionada-1);
                            ehAmigo = ehAmigo(usuarioLogado, usuarioSelecionado);
                            System.out.println(usuarioSelecionado);
                            if (usuarioSelecionado != usuarioLogado) {
                                System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "1- " + (ehAmigo ? "Remover" : "Adicionar") + " amigo");
                            }
                            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "2- Voltar" + ConsoleColors.RESET);
                            opcaoSelecionada = menu.validarEntradaInteira(leitor.nextLine());

                            switch (opcaoSelecionada) {
                                case 1:
                                    if (usuarioSelecionado != usuarioLogado) {
                                        if (ehAmigo) {
                                            gerenciadorUsuarios.removerAmizade(usuarioLogado.getId(), usuarioSelecionado.getId());
                                        } else {
                                            gerenciadorUsuarios.adicionarAmizade(usuarioLogado.getId(), usuarioSelecionado.getId());
                                        }
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

    /**
     * Apresenta as amizades do usuário logado.
     * É possível ver as informações dos amigos e removê-los através deste menu.
     */
    private void gerenciarAmizades() {
        List<Usuario> amigos = usuarioLogado.getAmigos();
        Usuario amigo;
        Integer opcaoSelecionada;

        if (amigos.isEmpty()) {
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Sua lista de amigos está vazia.");
            return;
        }

        while (true) {
            System.out.println(ConsoleColors.BLUE_BACKGROUND + ConsoleColors.BLACK_BOLD + "==== Lista de amigos ====" + ConsoleColors.RESET);
            for (int i = 0; i < amigos.size(); i++) {
                System.out.println(ConsoleColors.WHITE_BOLD + (i + 1) + "- " + ConsoleColors.RESET + amigos.get(i));
            }

            if (amigos.isEmpty()) {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Você não possui amigos em sua lista de amizades." + ConsoleColors.RESET);
            }

            System.out.println(ConsoleColors.WHITE_UNDERLINED + "(Selecione um amigo para interagir ou digite 0 para retornar)" + ConsoleColors.RESET);
            opcaoSelecionada = menu.validarEntradaInteira(leitor.nextLine());

            if (opcaoSelecionada != null) {
                if (opcaoSelecionada == 0) {
                    return;
                } else if (opcaoSelecionada <= amigos.size() && opcaoSelecionada > 0) {
                    amigo = amigos.get(opcaoSelecionada-1);
                    System.out.println(amigo);
                    if (amigo != usuarioLogado) {
                        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "1- Remover amigo");
                    }
                    System.out.println("2- Voltar" + ConsoleColors.RESET);
                    opcaoSelecionada = menu.validarEntradaInteira(leitor.nextLine());

                    switch (opcaoSelecionada) {
                        case 1:
                            if (amigo != usuarioLogado) {
                                gerenciadorUsuarios.removerAmizade(usuarioLogado.getId(), amigo.getId());
                                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Amigo removido com sucesso!" + ConsoleColors.RESET);
                            }
                            break;
                        case 2:
                            return;
                    }
                }
            }
        }
    }

    /**
     * Apresenta os posts do usuário logado e seus amigos em ordem crescente por data.
     */
    private void verFeedNoticias() {
        List<Post> postsDoFeed = new ArrayList<>(usuarioLogado.getPosts());
        Post postSelecionado;

        while (true) {
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "==== Feed de notícias ====" + ConsoleColors.RESET);
            for (Usuario u : usuarioLogado.getAmigos()) {
                postsDoFeed.addAll(u.getPosts());
            }
            if (postsDoFeed.isEmpty()) {
                System.out.println("Ops, não tem nada para ver aqui 😕");
            } else {
                postSelecionado = gerenciadorPosts.buscarPorId(listarPostsPaginados(gerenciadorPosts.ordenarPorData(postsDoFeed)));
                if(postSelecionado != null) {
                    interagirComPost(postSelecionado);
                }
                break;
            }
        }
    }

    /**
     * Apresenta os usuários que curtiram o post selecionado.
     *
     * @param post O post selecionado.
     */
    private void mostrarCurtidas(Post post) {
        List<Usuario> curtidas = post.getCurtidas();

        if (curtidas.isEmpty()) {
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Este post não possui curtidas." + ConsoleColors.RESET);
            return;
        }
        System.out.println(ConsoleColors.BLUE_BACKGROUND + ConsoleColors.BLACK_BOLD + "==== Curtidas ====" + ConsoleColors.RESET + "\n" + ConsoleColors.BLUE_UNDERLINED);
        for (Usuario u : curtidas) {
            System.out.println("👍 " + u.getNome());
        }
        System.out.println(ConsoleColors.RESET);
    }

    /**
     * Apresenta os comentários do post selecionado.
     *
     * @param post O post selecionado.
     */
    private void mostrarComentarios(Post post) {
        List<Comentario> comentarios = post.getComentarios();

        if (comentarios.isEmpty()) {
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Este post não possui comentários." + ConsoleColors.RESET);
            return;
        }
        System.out.println(ConsoleColors.BLUE_BACKGROUND + ConsoleColors.BLACK_BOLD + "==== Comentários ====" + ConsoleColors.RESET + "\n");
        for (Comentario c : comentarios) {
            System.out.println(c);
        }
        System.out.println(ConsoleColors.RESET);
    }

    /**
     * Solicita que o usuário informe o que deseja comentar no post e adiciona o comentário ao mesmo.
     *
     * @param post O post ao qual será comentado.
     */
    private void comentar(Post post) {
        String valorInserido;
        while (true) {
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "Digite seu comentário:" + ConsoleColors.RESET);
            valorInserido = leitor.nextLine();

            if (valorInserido.isEmpty()) {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "O comentário não pode ser vazio." + ConsoleColors.RESET);
            } else if (valorInserido.length() > 255) {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "O comentário pode conter até 255 caracteres. No momento ele contém: " + valorInserido.length() + ConsoleColors.RESET);
            } else {
                gerenciadorPosts.comentar(post.getId(), new Comentario(usuarioLogado, valorInserido, post));
                break;
            }
        }
    }

    /**
     * Apresenta as opções de interação com o post selecinado.
     *
     * @param post O post selecionado.
     */
    private void interagirComPost(Post post) {
        Integer opcaoSelecionada;

        while (true) {
            System.out.println(post);
            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "1- Mostrar curtidas");
            System.out.println("2- Mostrar comentários");
            System.out.println("3-" + (post.jaCurtiu(usuarioLogado) ? " Desc" : " C") + "urtir");
            System.out.println("4- Comentar");
            if (post.getAutor() == usuarioLogado) {
                System.out.println("5- Deletar");
            }
            System.out.println("6- Voltar" + ConsoleColors.RESET);

            opcaoSelecionada = menu.validarEntradaInteira(leitor.nextLine());
            if (opcaoSelecionada != null) {
                switch (opcaoSelecionada) {
                    case 1:
                        mostrarCurtidas(post);
                        break;
                    case 2:
                        mostrarComentarios(post);
                        break;
                    case 3:
                        if (post.jaCurtiu(usuarioLogado)) {
                            gerenciadorPosts.descurtir(post.getId(), usuarioLogado.getId());
                        } else {
                            gerenciadorPosts.curtir(post.getId(), usuarioLogado.getId());
                        }
                        break;
                    case 4:
                        comentar(post);
                        break;
                    case 5:
                        if (post.getAutor() == usuarioLogado) {
                            deletarPost(post);
                            return;
                        }
                        break;
                    case 6:
                        return;
                }
            }
        }
    }

    /**
     * Deleta o post selecionado.
     *
     * @param post O post selecionado.
     */
    private void deletarPost(Post post) {
        usuarioLogado.getPosts().remove(post);
        gerenciadorPosts.deletar(post.getId());
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Post deletado com sucesso!");
    }

    /**
     * Verifica se os usuários são amigos.
     *
     * @param usuario1 O primeiro usuário a ser verificado.
     * @param usuario2 O segundo usuário a ser verificado.
     * @return
     */
    private boolean ehAmigo(Usuario usuario1, Usuario usuario2) {
        for (Usuario u : usuario1.getAmigos()) {
            if (u == usuario2) return true;
        }

        return false;
    }
}
