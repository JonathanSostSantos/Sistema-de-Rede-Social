package ui;

import gerenciador.GerenciadorPosts;
import gerenciador.GerenciadorUsuarios;
import modelo.Post;
import modelo.Usuario;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Classe responsável pela interface do menu principal com o usuário via console.
 * Gerencia todas as interações do usuário com o menu principal do sistema.
 *
 * @author Jonathan Sost Dos Santos
 */
public class MenuPrincipal {
    private Scanner leitor;
    private GerenciadorUsuarios gerenciadorUsuarios;
    private GerenciadorPosts gerenciadorPosts;
    private static final String algoritmo = "AES";
    private SecretKey chave;
    private MenuUsuario menu;

    /**
     * Construtor da classe MenuPrincipal.
     * Inicializa o scanner, o gerenciador de posts e o gerenciador de usuários.
     */
    public MenuPrincipal() {
        this.leitor = new Scanner(System.in);
        this.gerenciadorPosts = new GerenciadorPosts();
        this.gerenciadorUsuarios = new GerenciadorUsuarios();
    }

    /**
     * Exibe o menu principal e processa as escolhas do usuário.
     * Loop principal do programa.
     */
    public void exibirMenu() {
        Integer opcaoSelecionada;
        Usuario usuario1;
        Usuario usuario2;

        //Inicializa o menu do usuário que será utilizado posteriormente
        //Atribui os gerenciadores de posts e usuários para o menu que também serão utilizados por lá
        menu = new MenuUsuario();
        menu.setGerenciadorPosts(gerenciadorPosts);
        menu.setGerenciadorUsuarios(gerenciadorUsuarios);
        menu.getGerenciadorPosts().setGerenciadorUsuarios(gerenciadorUsuarios);
        menu.getGerenciadorUsuarios().setGerenciadorPosts(gerenciadorPosts);

        //Tenta gerar a chave de encriptação e joga uma mensagem de erro case falhar
        try {
            chave = gerarChave();
            menu.setChave(chave);
        } catch (Exception e) {
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Ocorreu um erro inesperado ao gerar a chave de encriptação.\nMensagem de erro: " + e.getMessage() + ConsoleColors.RESET);
        }

        //Tenta criar os usuários e posts para teste unitário rápido
        try {
            usuario1 = new Usuario("teste da silva", "teste", "teste@gmail.com", encriptarSenha("Teste1200", chave));
            gerenciadorUsuarios.cadastrar(usuario1);
            usuario2 = new Usuario("abcd", "abcd", "abcd@gmail.com", encriptarSenha("Abcd1234", chave));
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

            gerenciadorUsuarios.adicionarAmizade(usuario1.getId(), usuario2.getId());
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao gerar os testes unitários.\nMensagem de erro: " + e.getMessage());
        }

        //Loop principal do menu
        while (true) {
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "===███████ ██    ██ ███    ██ ██     ██ ██ ████████ ████████ ███████ ██████===  \n" +
                    "===██      ██    ██ ████   ██ ██     ██ ██    ██       ██    ██      ██   ██=== \n" +
                    "===█████   ██    ██ ██ ██  ██ ██  █  ██ ██    ██       ██    █████   ██████===  \n" +
                    "===██      ██    ██ ██  ██ ██ ██ ███ ██ ██    ██       ██    ██      ██   ██=== \n" +
                    "===██       ██████  ██   ████  ███ ███  ██    ██       ██    ███████ ██   ██=== " + ConsoleColors.RESET);
            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "\n\n1 -> Fazer login");
            System.out.println("\n2 -> Cadastrar usuário" + ConsoleColors.RESET);

            //Faz a validação do input gerado pelo usuário para garantir que não ocorra uma Exception ao tentar converter a String para int
            opcaoSelecionada = validarEntradaInteira(leitor.nextLine().trim());

            //Caso seja um número, fazer o switch
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

    /**
     * Solicita as informações para login e valida se as informações correspondem a um usuário.
     */
    private void fazerLogin() {
        String username;
        Usuario usuario;

        //Loop da tela de login
        while (true) {
            System.out.print(ConsoleColors.WHITE_UNDERLINED + "Informe seu nome de usuário:" + ConsoleColors.RESET);
            username = leitor.nextLine();

            //Verifica se o usuário com o username informado pelo usuário existe
            usuario = gerenciadorUsuarios.buscarPorUsername(username);

            //Caso não exista, informa ao usuário para tentar novamente
            if (usuario == null) {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Não há um usuário cadastrado com este nome. Tente novamente." + ConsoleColors.RESET);
            } else {
                while (true) {
                    System.out.print(ConsoleColors.WHITE_UNDERLINED + "Informe sua senha:" + ConsoleColors.RESET);
                    try {
                        //Encripta a senha informada e compara com a do usuário cujo username foi informado anteriormente. Caso não batam, solicita que o usuário tente novamente.
                        if (usuario.getSenha().equals(encriptarSenha(leitor.nextLine().trim(), chave))) {
                            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Login efetuado!" + ConsoleColors.RESET);

                            //Caso o login seja efetuado, abre o menu de usuário.
                            exibirMenuLogado(usuario);
                            return;
                        }
                    } catch (Exception e) {
                        System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Houve um erro inesperado ao tentar acessar sua conta.\nMensagem de erro: " + e.getMessage() + ConsoleColors.RESET);
                    }
                    System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Senha incorreta. Tente novamente." + ConsoleColors.RESET);
                }
            }
        }
    }

    /**
     * Solicita as informações para a criação de um usuário e valida as mesmas antes de cadastrar.
     */
    private void cadastrarUsuario() {
        //Valida as informações que serão passadas pelo usuário por console.
        Usuario usuario = validarUsuario(1);

        //Caso as informações estejam de acordo, cadastra o usuário.
        if (usuario != null) {
            gerenciadorUsuarios.cadastrar(usuario);
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Usuário cadastrado com sucesso!" + ConsoleColors.RESET);
        }
    }

    /**
     * Faz a validação das informações que serão utilizadas para cadastrar ou atualizar um usuário.
     *
     * @param operacao O tipo de operação que acontecerá - 1 = Criação | 2 = Atualização
     * @param usuarioSendoAlterado O usuário cujas informações serão atualizadas (Caso seja atualização)
     * @param chave A chave de encriptação utilizada na senha
     * @return O usuário com as informações passadas
     */
    public Usuario validarUsuario(int operacao, Usuario usuarioSendoAlterado, SecretKey chave) {
        String valorInserido;
        String nome;
        String username;
        String email;
        String senha;
        Boolean nomeValido;
        Boolean senhaValida;
        Usuario usuario;

        //Texto dinâmico para criação/alteração de usuário
        System.out.println(ConsoleColors.BLUE_BACKGROUND + ConsoleColors.BLACK_BOLD + "==== " + (operacao == 1 ? "Criação" : "Alteração") + " de Usuário ====" + ConsoleColors.RESET);

        while (true) {
            //Caso seja alteração de usuário, apresenta os dados atuais do mesmo.
            if (operacao == 2) {
                System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "Nome atual: " + ConsoleColors.RESET + ConsoleColors.BLUE_BOLD_BRIGHT + usuarioSendoAlterado.getNome());
            }
            System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "\nNome completo: " + ConsoleColors.RESET);
            valorInserido = leitor.nextLine().trim();

            //Caso seja criação de usuário, garantir que o valor digitado pelo usuário não tenha menos que 2 caracteres.
            //Caso seja alteração de usuário, garantir apenas que o valor não tenha 1 caractere (caso possua zero caracteres, não será atualizado)
            if ((valorInserido.length() < 2 && operacao == 1) || (valorInserido.length() == 1 && operacao == 2)) {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "O nome de usuário deve conter 2 ou mais caracteres." + ConsoleColors.RESET);
            } else {
                nomeValido = true;
                for (Character c : valorInserido.toLowerCase().toCharArray()) {
                    //Certifica que os caracteres do nome são apenas letras ou espaços
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
            if (operacao == 2) {
                System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "Username atual: " + ConsoleColors.RESET + ConsoleColors.BLUE_BOLD_BRIGHT + usuarioSendoAlterado.getUsername());
            }
            System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "\nUsername: " + ConsoleColors.RESET);
            valorInserido = leitor.nextLine().trim();

            //Se houver outro usuário com o mesmo username, avisará que já está em uso e não prosseguirá.
            if (gerenciadorUsuarios.buscarPorUsername(valorInserido) != null) {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Username já em uso." + ConsoleColors.RESET);
            } else if (valorInserido.isBlank() && operacao == 1) {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Username não pode ser vazio." + ConsoleColors.RESET);
            } else {
                username = valorInserido;
                break;
            }
        }

        while (true) {
            if (operacao == 2) {
                System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "Email atual: " + ConsoleColors.RESET + ConsoleColors.BLUE_BOLD_BRIGHT + usuarioSendoAlterado.getEmail());
            }
            System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "\nEmail: " + ConsoleColors.RESET);
            valorInserido = leitor.nextLine().trim();

            if (operacao == 2 && valorInserido.isBlank()) {
                email = valorInserido;
                break;
            } else if (!validarEmail(valorInserido)) {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Este email é inválido!" + ConsoleColors.RESET);
            } else if (gerenciadorUsuarios.buscarPorEmail(valorInserido) != null) {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Este email já está em uso." + ConsoleColors.RESET);
            } else {
                email = valorInserido;
                break;
            }
        }

        while (true) {
            if (operacao == 2) {
                //Apresenta a senha do usuário censurada com o caractere '*'
                System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "Senha atual: " + ConsoleColors.RESET + ConsoleColors.BLUE_BOLD_BRIGHT + censurarSenha(usuarioSendoAlterado.getSenha(), chave) + ConsoleColors.RESET);
            }
            System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "\nSenha: " + ConsoleColors.RESET);
            valorInserido = leitor.nextLine().trim();

            if (operacao == 2 && valorInserido.isBlank()) {
                senha = valorInserido;
                break;
            } else {
                //Verifica se a senha possui 6 ou mais caracteres, se possui pelo menos 1 número e se possui pelo menos 1 letra maiúscula e 1 minúscula.
                senhaValida = valorInserido.length() >= 6 && valorInserido.matches(".*[0-9].*") && senhaContemMaiuscula(valorInserido) && senhaContemMinuscula(valorInserido);

                //Se a senha for válida, realiza a encriptação para cadastrar o usuário.
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
        }

        //Se for criação, retorna o usuário sem ID. Caso contrário, retorna o usuário com o ID do usuário que está sendo alterado.
        if (operacao == 1) {
            usuario = new Usuario(nome, username, email, senha);
        } else {
            usuario = new Usuario(usuarioSendoAlterado.getId(), nome, username, email, senha);
        }

        return usuario;
    }

    /**
     * Realiza a validação dos dados do usuário informados passando apenas a operação que está sendo realizada como parãmetro.
     * Método utilizado únicamente para a criação de usuário.
     *
     * @param operacao A operação que está sendo realizada - 1 = Criação | 2 = Alteração
     * @return O usuário já validado.
     */
    public Usuario validarUsuario(int operacao) {
        return validarUsuario(operacao, null, null);
    }

    /**
     * Exibe o menu do usuário após realizar o login.
     *
     * @param usuario O usuário que será logado.
     */
    private void exibirMenuLogado(Usuario usuario) {
        menu.setUsuarioLogado(usuario);
        menu.exibirMenu();
    }

    /**
     * Faz a validação para que haja a conversão de String para int sem a possibilidade de jogar uma Exception.
     *
     * @param value O valor a ser convertido para int.
     * @return O valor convertido.
     */
    public Integer validarEntradaInteira(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            System.out.println(STR."A opção selecionada é inválida. \{e.getMessage()}");
            return null;
        }
    }

    /**
     * Realiza a validação de email para certificar de que o mesmo é válido.
     *
     * @param email O email a ser validado.
     * @return Retorna true se o email for válido e false se não for.
     */
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

    /**
     * Verifica se a senha informada possui letras maiúsculas.
     *
     * @param senha A senha a ser verificada.
     * @return Retorna true se a senha possuir letras maiúsculas e false caso contrário.
     */
    public boolean senhaContemMaiuscula(String senha) {
        for (Character c : senha.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Verifica se a senha informada possui letras minúsculas.
     *
     * @param senha A senha a ser verificada.
     * @return Retorna true se a senha possuir letras minúsculas e false caso contrário.
     */
    public boolean senhaContemMinuscula(String senha) {
        for (Character c : senha.toCharArray()) {
            if (Character.isLowerCase(c)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Gera a chave que será utilizada para encriptar as senhas.
     *
     * @return A chave de encriptação.
     * @throws Exception se não for possível gerar a chave
     */
    private static SecretKey gerarChave() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(algoritmo);
        keyGen.init(128);
        return keyGen.generateKey();
    }

    /**
     * Encripta a senha utilizando a chave gerada.
     *
     * @param senha A senha a ser encriptada.
     * @param chave A chave de encriptação.
     * @return A senha encriptada.
     * @throws Exception se houver falha ao encriptar a senha
     */
    public static String encriptarSenha(String senha, SecretKey chave) throws Exception {
        Cipher cipher = Cipher.getInstance(algoritmo);
        cipher.init(Cipher.ENCRYPT_MODE, chave);
        byte[] encryptedBytes = cipher.doFinal(senha.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * Decripta a senha utilizando a chave gerada.
     *
     * @param senhaEncriptada A senha a ser decriptada.
     * @param chave A chave de encriptação.
     * @return A senha decriptada.
     * @throws Exception se houver falha ao decriptar a senha.
     */
    public static String decriptarSenha(String senhaEncriptada, SecretKey chave) throws Exception {
        Cipher cipher = Cipher.getInstance(algoritmo);
        cipher.init(Cipher.DECRYPT_MODE, chave);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(senhaEncriptada));
        return new String(decryptedBytes);
    }

    /**
     * Censura a senha do usuário com o caracter '*'.
     *
     * @param senha A senha encriptada.
     * @param chave A chave utilizada para decriptar a senha.
     * @return A senha censurada.
     */
    private String censurarSenha(String senha, SecretKey chave) {
        char[] caracteres;
        try {
            caracteres = new char[decriptarSenha(senha, chave).length()];
            Arrays.fill(caracteres, '*');

            return new String(caracteres);
        } catch (Exception e) {
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Ocorreu um erro ao trazer a senha.\nMensagem de erro: " + e.getMessage() + ConsoleColors.RESET);
        }

        return null;
    }
}
