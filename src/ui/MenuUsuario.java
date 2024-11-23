package ui;

import gerenciador.GerenciadorPosts;
import gerenciador.GerenciadorUsuarios;
import modelo.Comentario;
import modelo.Post;
import modelo.Usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MenuUsuario {
    private MenuPrincipal menu;
    private Scanner leitor;
    private Usuario usuarioLogado;
    private GerenciadorUsuarios gerenciadorUsuarios;
    private GerenciadorPosts gerenciadorPosts;

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public MenuUsuario() {
        this.leitor = new Scanner(System.in);
        menu = new MenuPrincipal();
        gerenciadorUsuarios = new GerenciadorUsuarios();
        gerenciadorPosts = new GerenciadorPosts();
    }

    public void exibirMenu() {
        Integer opcaoSelecionada;

        gerenciadorPosts.criar(new Post(usuarioLogado, "Se rir fosse exercício, já estaríamos todos marombeiros por aqui! #Funwitter", LocalDateTime.now()));
        gerenciadorPosts.criar(new Post(usuarioLogado, "Alguém aí sabe como fazer café sem se apaixonar pelo cheiro? Perguntando para um amigo... ☕❤ #FunwitterMoment", LocalDateTime.now()));
        gerenciadorPosts.criar(new Post(usuarioLogado, "Dizem que rir é o melhor remédio. Bem, aqui somos uma farmácia completa!", LocalDateTime.now()));
        gerenciadorPosts.criar(new Post(usuarioLogado, "Se o tédio fosse esporte olímpico, já estaria com várias medalhas de ouro. #BoraRir", LocalDateTime.now()));
        gerenciadorPosts.criar(new Post(usuarioLogado, "Errado é quem não erra. A gente erra, mas com estilo! ✌", LocalDateTime.now()));
        gerenciadorPosts.criar(new Post(usuarioLogado, "Será que a pizza pensa em mim tanto quanto eu penso nela?", LocalDateTime.now()));
        gerenciadorPosts.criar(new Post(usuarioLogado, "Dormir cedo é overrated. Vamos rir até o sol nascer! #NoiteDivertida", LocalDateTime.now().withHour(2)));
        gerenciadorPosts.criar(new Post(usuarioLogado, "Quem nunca colocou um lembrete e esqueceu do lembrete, não sabe o que é aventura. ⏰♂", LocalDateTime.now()));
        gerenciadorPosts.criar(new Post(usuarioLogado, "A meta é ser tão engraçado quanto meu reflexo quando acordo.", LocalDateTime.now()));
        gerenciadorPosts.criar(new Post(usuarioLogado, "Se o mundo te der limões, faça memes. \uD83C\uDF4B\uD83D\uDE02 #MemeLife", LocalDateTime.now().withHour(6)));
        gerenciadorPosts.criar(new Post(usuarioLogado, "Estudo revela: pessoas que seguem o Funwitter são 100% mais felizes. Ciência (talvez)!", LocalDateTime.now()));
        gerenciadorPosts.criar(new Post(usuarioLogado, "Hoje eu acordei com vontade de ser saudável... Aí lembrei que o chocolate também tem alma.", LocalDateTime.now()));
        gerenciadorPosts.criar(new Post(usuarioLogado, "Se escrever errado fosse arte, eu já seria um Picasso das palavras. #ArteDaGafe", LocalDateTime.now()));
        gerenciadorPosts.criar(new Post(usuarioLogado, "Qual é o plural de 'Internet caindo'? Apocalipse!", LocalDateTime.now()));
        gerenciadorPosts.criar(new Post(usuarioLogado, "Minha relação com a academia é como a do Wi-Fi: às vezes conecta, às vezes não.", LocalDateTime.now()));
        gerenciadorPosts.criar(new Post(usuarioLogado, "Acordar cedo é tipo spoiler do dia, não quero saber. #DorminhocosUnidos", LocalDateTime.now().withHour(4)));
        gerenciadorPosts.criar(new Post(usuarioLogado, "Quem inventou o trabalho antes das 10 da manhã claramente não tomava café. ☕⏳", LocalDateTime.now()));
        gerenciadorPosts.criar(new Post(usuarioLogado, "A vida é como um meme: às vezes você entende, às vezes só ri. #FunwitterFilosófico", LocalDateTime.now()));
        gerenciadorPosts.criar(new Post(usuarioLogado, "Sabe o que combina com sexta-feira? Tudo, menos trabalho. ✌ #VivaSexta", LocalDateTime.now()));
        gerenciadorPosts.criar(new Post(usuarioLogado, "Procurando motivo para sorrir? Olha para cima, tá escrito 'Funwitter'!", LocalDateTime.now()));
        gerenciadorPosts.criar(new Post(usuarioLogado, "Hoje descobri que meu talento especial é lembrar de algo importante só depois que já é tarde demais. ⏳ #SuperPoderInútil", LocalDateTime.now()));

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

        totalPaginas = totalPosts / 10;

        while (true) {
            postsNaPagina = pagina.equals(totalPaginas) ? totalPosts % 10 : 10;
            for (int i = pagina * 10; i < (pagina * 10) + postsNaPagina; i++) {
                System.out.println(ConsoleColors.WHITE + (1 + i % 10) + "- " + ConsoleColors.RESET + posts.get(i));
            }
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "\n" + (pagina > 0 ? pagina > 1 ? "<< <" : "<" : "") + " Página " + (pagina + 1) + " de " + (totalPaginas + 1) + (pagina < totalPaginas ? pagina < totalPaginas - 1 ? " > >>" : " >" : "" + ConsoleColors.RESET));
            System.out.println(ConsoleColors.WHITE_UNDERLINED + "(Selecione o post para interagir ou digite 0 para retornar)" + ConsoleColors.RESET);
            valorInserido = leitor.nextLine();

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

    private void verPosts() {
        Boolean postEstaSelecionado;
        Post postSelecionado;
        Integer opcaoSelecionada;

        while (true) {
            System.out.println("Seus posts:");
            postSelecionado = gerenciadorPosts.buscarPorId(listarPostsPaginados(gerenciadorPosts.ordenarPorData(usuarioLogado.getPosts())));

            postEstaSelecionado = postSelecionado != null;

            if (postEstaSelecionado) {
                interagirComPost(postSelecionado);
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
                                        gerenciadorUsuarios.removerAmizade(usuarioLogado.getId(), usuarioSelecionado.getId());
                                    } else {
                                        gerenciadorUsuarios.adicionarAmizade(usuarioLogado.getId(), usuarioSelecionado.getId());
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
        List<Post> postsDoFeed = new ArrayList<>(usuarioLogado.getPosts());
        Post postSelecionado;
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "==== Feed de notícias ====" + ConsoleColors.RESET);
        for (Usuario u : usuarioLogado.getAmigos()) {
            Collections.copy(postsDoFeed, u.getPosts());
        }

        while (true) {
            if (postsDoFeed.isEmpty()) {
                System.out.println("Ops, não tem nada para ver aqui 😕");
            } else {
                postSelecionado = gerenciadorPosts.buscarPorId(listarPostsPaginados(gerenciadorPosts.ordenarPorData(usuarioLogado.getPosts())));
                interagirComPost(postSelecionado);
            }
        }
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
            }
        }
    }

    private void interagirComPost(Post post) {
        Integer opcaoSelecionada;

        System.out.println(post);
        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "1- Mostrar curtidas");
        System.out.println("2- Mostrar comentários");
        System.out.println("3-" + (post.jaCurtiu(usuarioLogado) ? "Desc" : "C") + "urtir");
        System.out.println("4- Comentar");
        System.out.println("5- Deletar");
        System.out.println("6- Voltar" + ConsoleColors.RESET);

        while (true) {
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
                        deletarPost(post);
                        break;
                    case 6:
                        return;
                }
            }
        }
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
