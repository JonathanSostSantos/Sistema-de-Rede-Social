package ui;

import gerenciador.GerenciadorUsuarios;
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
    private GerenciadorUsuarios gerenciador;
    private static final String algoritmo = "AES";
    private SecretKey chave;
    private Usuario usuarioLogado;

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public MenuPrincipal() {
        this.leitor = new Scanner(System.in);
        this.gerenciador = new GerenciadorUsuarios();
    }

    public void exibirMenu() {
        Integer opcaoSelecionada;
        try {
            chave = gerarChave();
        } catch (Exception e) {
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Ocorreu um erro inesperado ao gerar a chave de encriptação.\nMensagem de erro: " + e.getMessage() + ConsoleColors.RESET);
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
            System.out.print(ConsoleColors.WHITE_UNDERLINED + "Informe seu nome de usuário: " + ConsoleColors.RESET);
            username = leitor.nextLine();
            usuario = gerenciador.buscarPorUsername(username);

            if (usuario == null) {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Não há um usuário cadastrado com este nome. Tente novamente." + ConsoleColors.RESET);
            } else {
                while (true) {
                    System.out.print(ConsoleColors.WHITE_UNDERLINED + "Informe sua senha: " + ConsoleColors.RESET);
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
        String valorInserido;
        String nome;
        String username;
        String email;
        String senha;
        Boolean nomeValido;
        Boolean senhaValida;
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

            if (gerenciador.buscarPorUsername(valorInserido) != null) {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Username já em uso." + ConsoleColors.RESET);
            } else {
                username = valorInserido;
                break;
            }
        }

        while(true) {
            System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "\nEmail: " + ConsoleColors.RESET);
            valorInserido = leitor.nextLine().trim();

            if (!validarEmail(valorInserido)) {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Este email é inválido!" + ConsoleColors.RESET);
            } else if(gerenciador.buscarPorEmail(valorInserido) != null) {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Este email já está em uso." + ConsoleColors.RESET);
            } else {
                email = valorInserido;
                break;
            }
        }

        while(true) {
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

        gerenciador.cadastrar(new Usuario(nome, username, email, senha, LocalDateTime.now()));
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Usuário cadastrado com sucesso!" + ConsoleColors.RESET);
    }

    private void exibirMenuLogado(Usuario usuario) {
        MenuUsuario menu = new MenuUsuario();
        usuarioLogado = usuario;
        menu.exibirMenu();
    }

    private Integer validarEntradaInteira(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            System.out.println(STR."A opção selecionada é inválida. \{e.getMessage()}");
            return null;
        }
    }

    public boolean validarEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
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
