package ui;

import gerenciador.GerenciadorPosts;
import modelo.Post;
import modelo.Usuario;

import java.time.LocalDateTime;
import java.util.Scanner;

public class MenuUsuario {
    private MenuPrincipal menu;
    private Scanner leitor;
    private Usuario usuarioLogado;

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public MenuUsuario() {
        this.leitor = new Scanner(System.in);
        menu = new MenuPrincipal();
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
        Integer totalPosts = usuarioLogado.getPosts().size();
        Integer totalPaginas;
        String valorInserido;

        if (totalPosts <= 0) {
            System.out.println("Você não possui nenhum post, que tal fazer seu primeiro?");
        } else {
            totalPaginas = totalPosts / 10;
            System.out.println("Seus posts:");

            while (true) {
                for (int i = pagina * 10; i < (pagina * 10) + 10; i++) {
                    System.out.println(usuarioLogado.getPosts().get(i));
                }
                System.out.println("\n" + (pagina > 0 ? pagina > 1 ? "<< <" : "<" : "") + " Página " + (pagina + 1) + " de " + (totalPaginas + 1) + (pagina < totalPaginas ? pagina < totalPaginas - 1 ? " >> >" : " >" : ""));
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
                    default:
                        if (valorInserido.matches(".*[0-9].*")) {

                        }
                        break;
                }
            }
        }
    }

    private void editarPerfil() {

    }

    private void buscarUsuarios() {

    }

    private void gerenciarAmizades() {

    }

    private void verFeedNoticias() {

    }
}
