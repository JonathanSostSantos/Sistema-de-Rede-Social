package ui;

import gerenciador.GerenciadorPosts;
import gerenciador.GerenciadorUsuarios;
import modelo.Post;
import modelo.Usuario;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MenuPrincipal {
    private Scanner leitor;
    private GerenciadorUsuarios gerenciadorUsuarios;
    private GerenciadorPosts gerenciadorPosts;
    private static final String algoritmo = "AES";
    private SecretKey chave;

    public MenuPrincipal() {
        this.leitor = new Scanner(System.in);
        this.gerenciadorPosts = new GerenciadorPosts();
        this.gerenciadorUsuarios = new GerenciadorUsuarios();
    }

    public void exibirMenu() {
        Integer opcaoSelecionada;
        Usuario usuario1 = null;
        Usuario usuario2 = null;
        try {
            chave = gerarChave();
        } catch (Exception e) {
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Ocorreu um erro inesperado ao gerar a chave de encriptação.\nMensagem de erro: " + e.getMessage() + ConsoleColors.RESET);
        }

        try {
            usuario1 = new Usuario("teste da silva", "teste", "teste@gmail.com", encriptarSenha("Teste1200", chave), LocalDateTime.now());
            gerenciadorUsuarios.cadastrar(usuario1);
            usuario2 = new Usuario("abcd", "abcd", "abcd@gmail.com", encriptarSenha("Abcd1234", chave), LocalDateTime.now());
            gerenciadorUsuarios.cadastrar(usuario2);

            gerenciadorPosts.criar(new Post(usuario1, "Se rir fosse exercício, já estaríamos todos marombeiros por aqui! #Funwitter", LocalDateTime.now()));
            gerenciadorPosts.criar(new Post(usuario1, "Alguém aí sabe como fazer café sem se apaixonar pelo cheiro? Perguntando para um amigo... ☕❤ #FunwitterMoment", LocalDateTime.now()));
            gerenciadorPosts.criar(new Post(usuario1, "Dizem que rir é o melhor remédio. Bem, aqui somos uma farmácia completa!", LocalDateTime.now()));
            gerenciadorPosts.criar(new Post(usuario1, "Se o tédio fosse esporte olímpico, já estaria com várias medalhas de ouro. #BoraRir", LocalDateTime.now()));
            gerenciadorPosts.criar(new Post(usuario1, "Errado é quem não erra. A gente erra, mas com estilo! ✌", LocalDateTime.now()));
            gerenciadorPosts.criar(new Post(usuario1, "Será que a pizza pensa em mim tanto quanto eu penso nela?", LocalDateTime.now()));
            gerenciadorPosts.criar(new Post(usuario1, "Dormir cedo é overrated. Vamos rir até o sol nascer! #NoiteDivertida", LocalDateTime.now().withHour(2)));
            gerenciadorPosts.criar(new Post(usuario1, "Quem nunca colocou um lembrete e esqueceu do lembrete, não sabe o que é aventura. ⏰♂", LocalDateTime.now()));
            gerenciadorPosts.criar(new Post(usuario1, "A meta é ser tão engraçado quanto meu reflexo quando acordo.", LocalDateTime.now()));
            gerenciadorPosts.criar(new Post(usuario1, "Se o mundo te der limões, faça memes. \uD83C\uDF4B\uD83D\uDE02 #MemeLife", LocalDateTime.now().withHour(6)));
            gerenciadorPosts.criar(new Post(usuario1, "Estudo revela: pessoas que seguem o Funwitter são 100% mais felizes. Ciência (talvez)!", LocalDateTime.now()));
            gerenciadorPosts.criar(new Post(usuario1, "Hoje eu acordei com vontade de ser saudável... Aí lembrei que o chocolate também tem alma.", LocalDateTime.now()));
            gerenciadorPosts.criar(new Post(usuario1, "Se escrever errado fosse arte, eu já seria um Picasso das palavras. #ArteDaGafe", LocalDateTime.now()));
            gerenciadorPosts.criar(new Post(usuario1, "Qual é o plural de 'Internet caindo'? Apocalipse!", LocalDateTime.now()));
            gerenciadorPosts.criar(new Post(usuario1, "Minha relação com a academia é como a do Wi-Fi: às vezes conecta, às vezes não.", LocalDateTime.now()));
            gerenciadorPosts.criar(new Post(usuario1, "Acordar cedo é tipo spoiler do dia, não quero saber. #DorminhocosUnidos", LocalDateTime.now().withHour(4)));
            gerenciadorPosts.criar(new Post(usuario1, "Quem inventou o trabalho antes das 10 da manhã claramente não tomava café. ☕⏳", LocalDateTime.now()));
            gerenciadorPosts.criar(new Post(usuario1, "A vida é como um meme: às vezes você entende, às vezes só ri. #FunwitterFilosófico", LocalDateTime.now()));
            gerenciadorPosts.criar(new Post(usuario1, "Sabe o que combina com sexta-feira? Tudo, menos trabalho. ✌ #VivaSexta", LocalDateTime.now()));
            gerenciadorPosts.criar(new Post(usuario1, "Procurando motivo para sorrir? Olha para cima, tá escrito 'Funwitter'!", LocalDateTime.now()));
            gerenciadorPosts.criar(new Post(usuario1, "Hoje descobri que meu talento especial é lembrar de algo importante só depois que já é tarde demais. ⏳ #SuperPoderInútil", LocalDateTime.now()));

            gerenciadorPosts.criar(new Post(usuario2, "Nunca subestime o poder de um meme para salvar um dia ruim. É basicamente terapia, só que grátis. \uD83D\uDE02\uD83D\uDCF1 #FunwitterSalva", LocalDateTime.now().withHour(3)));
            gerenciadorPosts.criar(new Post(usuario2, "Alguém já tentou desligar e ligar de novo o ano? Acho que tá com bug. \uD83D\uDDA5\uFE0F\uD83E\uDD14 #Reset2024", LocalDateTime.now().withHour(5)));
            gerenciadorPosts.criar(new Post(usuario2, "Segunda-feira é tipo o chefe chato das semanas, mas estamos aqui para rir na cara dela. \uD83D\uDE02\uD83D\uDCBC #Resistindo", LocalDateTime.now().withHour(18)));
            gerenciadorPosts.criar(new Post(usuario2, "Se comer fosse esporte olímpico, eu seria ouro no revezamento pizza-batata frita-sorvete. \uD83E\uDD47\uD83C\uDF55\uD83C\uDF5F\uD83C\uDF66", LocalDateTime.now().withHour(7)));
            gerenciadorPosts.criar(new Post(usuario2, "Adoro como os pássaros cantam de manhã, mas eles poderiam baixar o volume, né? Ainda tô no modo soneca. \uD83D\uDC26\uD83D\uDE34", LocalDateTime.now().withHour(23)));
            gerenciadorPosts.criar(new Post(usuario2, "A vida é como o Wi-Fi público: você nunca sabe se vai conectar ou travar de vez. \uD83D\uDCF6\uD83D\uDE02 #FilosofiaDoDia", LocalDateTime.now().withHour(1)));
            gerenciadorPosts.criar(new Post(usuario2, "Sou especialista em prometer acordar cedo e fazer cara de surpresa quando o despertador toca. ⏰\uD83E\uDD2F #Clássico", LocalDateTime.now().withHour(2)));
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao gerar os testes unitários.\nMensagem de erro: " + e.getMessage());
        }

        while (true) {
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "===███████ ██    ██ ███    ██ ██     ██ ██ ████████ ████████ ███████ ██████===  \n" +
                    "===██      ██    ██ ████   ██ ██     ██ ██    ██       ██    ██      ██   ██=== \n" +
                    "===█████   ██    ██ ██ ██  ██ ██  █  ██ ██    ██       ██    █████   ██████===  \n" +
                    "===██      ██    ██ ██  ██ ██ ██ ███ ██ ██    ██       ██    ██      ██   ██=== \n" +
                    "===██       ██████  ██   ████  ███ ███  ██    ██       ██    ███████ ██   ██=== " + ConsoleColors.RESET);
            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "\n\n1 -> Fazer login");
            System.out.println("\n2 -> Cadastrar usuário" + ConsoleColors.RESET);
            opcaoSelecionada = validarEntradaInteira(leitor.nextLine().trim());

            if (opcaoSelecionada != null) {
                switch (opcaoSelecionada) {
                    case 1:
                        fazerLogin();
                        break;
                    case 2:
                        cadastrarUsuario();
                        break;
                    default:
                        System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Opção inexistente." + ConsoleColors.RESET);
                }
            }
        }
    }

    private void fazerLogin() {
        String username;
        Usuario usuario;

        while (true) {
            System.out.print(ConsoleColors.WHITE_UNDERLINED + "Informe seu nome de usuário:" + ConsoleColors.RESET);
            username = leitor.nextLine();
            usuario = gerenciadorUsuarios.buscarPorUsername(username);

            if (usuario == null) {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Não há um usuário cadastrado com este nome. Tente novamente." + ConsoleColors.RESET);
            } else {
                while (true) {
                    System.out.print(ConsoleColors.WHITE_UNDERLINED + "Informe sua senha:" + ConsoleColors.RESET);
                    try {
                        if (usuario.getSenha().equals(encriptarSenha(leitor.nextLine().trim(), chave))) {
                            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Login efetuado!" + ConsoleColors.RESET);
                            exibirMenuLogado(usuario);
                            return;
                        }
                    } catch (Exception e) {
                        System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Houve um erro inesperado ao tentar acessar sua senha.\nMensagem de erro: " + e.getMessage() + ConsoleColors.RESET);
                    }
                    System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Senha incorreta. Tente novamente." + ConsoleColors.RESET);
                }
            }
        }
    }

    private void cadastrarUsuario() {
        Usuario usuario = validarUsuario();
        if (usuario != null) {
            gerenciadorUsuarios.cadastrar(usuario);
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Usuário cadastrado com sucesso!" + ConsoleColors.RESET);
        }
    }

    public Usuario validarUsuario() {
        String valorInserido;
        String nome;
        String username;
        String email;
        String senha;
        Boolean nomeValido;
        Boolean senhaValida;
        Usuario usuario = null;

        System.out.println(ConsoleColors.BLUE_BACKGROUND + "==== Criação de Usuário ====" + ConsoleColors.RESET);

        while (true) {
            System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "\nNome completo: " + ConsoleColors.RESET);
            valorInserido = leitor.nextLine().trim();
            if (valorInserido.length() < 2) {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "O nome de usuário deve conter 2 ou mais caracteres." + ConsoleColors.RESET);
            } else {
                nomeValido = true;
                for (Character c : valorInserido.toLowerCase().toCharArray()) {
                    if (!Character.isLetter(c) && c != 32) {
                        nomeValido = false;
                        System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "São permitidas apenas letras no nome." + ConsoleColors.RESET);
                        break;
                    }
                }

                if (nomeValido) {
                    nome = valorInserido;
                    break;
                }
            }
        }

        while (true) {
            System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "\nUsername: " + ConsoleColors.RESET);
            valorInserido = leitor.nextLine().trim();

            if (gerenciadorUsuarios.buscarPorUsername(valorInserido) != null) {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Username já em uso." + ConsoleColors.RESET);
            } else {
                username = valorInserido;
                break;
            }
        }

        while (true) {
            System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "\nEmail: " + ConsoleColors.RESET);
            valorInserido = leitor.nextLine().trim();

            if (!validarEmail(valorInserido)) {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Este email é inválido!" + ConsoleColors.RESET);
            } else if (gerenciadorUsuarios.buscarPorEmail(valorInserido) != null) {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Este email já está em uso." + ConsoleColors.RESET);
            } else {
                email = valorInserido;
                break;
            }
        }

        while (true) {
            System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "\nSenha: " + ConsoleColors.RESET);
            valorInserido = leitor.nextLine().trim();

            senhaValida = valorInserido.length() >= 6 && valorInserido.matches(".*[0-9].*") && senhaContemMaiuscula(valorInserido) && senhaContemMinuscula(valorInserido);

            if (senhaValida) {
                try {
                    senha = encriptarSenha(valorInserido, chave);
                    break;
                } catch (Exception e) {
                    System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Ocorreu um erro ao criar a senha. Tente novamente.\nMensagem de erro: " + e.getMessage() + ConsoleColors.RESET);
                }
            }

            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Senha inválida! Motivos:" +
                    (valorInserido.length() < 6 ? "\n  • A senha possui menos que 6 caracteres." : "") +
                    (!valorInserido.matches(".*[0-9].*") ? "\n  • A senha não possui números." : "") +
                    (!senhaContemMaiuscula(valorInserido) ? "\n  • A senha não possui pelo menos 1 letra maiúscula." : "") +
                    (!senhaContemMinuscula(valorInserido) ? "\n  • A senha não possui pelo menos 1 letra minúscula." : "") + ConsoleColors.RESET);
        }

        usuario = new Usuario(nome, username, email, senha, LocalDateTime.now());

        return usuario;
    }

    private void exibirMenuLogado(Usuario usuario) {
        MenuUsuario menu = new MenuUsuario();
        menu.setUsuarioLogado(usuario);
        menu.exibirMenu();
    }

    public Integer validarEntradaInteira(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            System.out.println(STR."A opção selecionada é inválida. \{e.getMessage()}");
            return null;
        }
    }

    public boolean validarEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public boolean senhaContemMaiuscula(String senha) {
        for (Character c : senha.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }

        return false;
    }

    public boolean senhaContemMinuscula(String senha) {
        for (Character c : senha.toCharArray()) {
            if (Character.isLowerCase(c)) {
                return true;
            }
        }

        return false;
    }

    private static SecretKey gerarChave() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(algoritmo);
        keyGen.init(128);
        return keyGen.generateKey();
    }

    public static String encriptarSenha(String senha, SecretKey chave) throws Exception {
        Cipher cipher = Cipher.getInstance(algoritmo);
        cipher.init(Cipher.ENCRYPT_MODE, chave);
        byte[] encryptedBytes = cipher.doFinal(senha.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decriptarSenha(String senhaEncriptada, SecretKey chave) throws Exception {
        Cipher cipher = Cipher.getInstance(algoritmo);
        cipher.init(Cipher.DECRYPT_MODE, chave);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(senhaEncriptada));
        return new String(decryptedBytes);
    }
}
