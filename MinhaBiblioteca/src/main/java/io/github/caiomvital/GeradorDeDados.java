package io.github.caiomvital;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Classe responsável por gerar dados fictícios para testes,
 * como nomes, CPFs, telefones e endereços brasileiros aleatórios.
 */
public class GeradorDeDados {

    private static final String URL_NOMES = "https://raw.githubusercontent.com/caiomvital/json-files/refs/heads/main/nomes_proprios.json";
    private static final String URL_RUAS = "https://raw.githubusercontent.com/caiomvital/json-files/refs/heads/main/ruas_rmr.json";
    private static final String URL_ESTADOS_CAPITAIS = "https://raw.githubusercontent.com/caiomvital/json-files/refs/heads/main/estados_capitais.json";
    private static final String URL_RUAS_POR_CIDADE = "https://raw.githubusercontent.com/caiomvital/json-files/refs/heads/main/ruas_por_cidade.json";

    private static Map<String, Map<String, String>> estadosCapitaisMap = null;
    private static Map<String, List<String>> ruasPorCidadeMap = null;
    private static Map<String, List<String>> nomesMap = null;
    private static Map<String, List<String>> ruasMap = null;
    private static final Random random = new Random();

    // --- Carregadores ---

    private static void carregarNomes() {
        if (nomesMap != null) return;
        try {
            URI uri = new URI(URL_NOMES);
            HttpURLConnection conexao = (HttpURLConnection) uri.toURL().openConnection();
            conexao.setRequestMethod("GET");
            if (conexao.getResponseCode() == 200) {
                BufferedReader leitor = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
                StringBuilder resposta = new StringBuilder();
                String linha;
                while ((linha = leitor.readLine()) != null) {
                    resposta.append(linha);
                }
                leitor.close();
                conexao.disconnect();
                ObjectMapper mapper = new ObjectMapper();
                nomesMap = mapper.readValue(resposta.toString(), new TypeReference<Map<String, List<String>>>() {});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void carregarRuas() {
        if (ruasMap != null) return;
        try {
            URI uri = new URI(URL_RUAS);
            HttpURLConnection conexao = (HttpURLConnection) uri.toURL().openConnection();
            conexao.setRequestMethod("GET");
            if (conexao.getResponseCode() == 200) {
                BufferedReader leitor = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
                StringBuilder resposta = new StringBuilder();
                String linha;
                while ((linha = leitor.readLine()) != null) {
                    resposta.append(linha);
                }
                leitor.close();
                conexao.disconnect();
                ObjectMapper mapper = new ObjectMapper();
                ruasMap = mapper.readValue(resposta.toString(), new TypeReference<Map<String, List<String>>>() {});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void carregarEstadosCapitais() {
        if (estadosCapitaisMap != null) return;
        try {
            URI uri = new URI(URL_ESTADOS_CAPITAIS);
            HttpURLConnection conexao = (HttpURLConnection) uri.toURL().openConnection();
            conexao.setRequestMethod("GET");
            if (conexao.getResponseCode() == 200) {
                BufferedReader leitor = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
                StringBuilder resposta = new StringBuilder();
                String linha;
                while ((linha = leitor.readLine()) != null) {
                    resposta.append(linha);
                }
                leitor.close();
                conexao.disconnect();
                ObjectMapper mapper = new ObjectMapper();
                estadosCapitaisMap = mapper.readValue(resposta.toString(), new TypeReference<Map<String, Map<String, String>>>() {});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void carregarRuasPorCidade() {
        if (ruasPorCidadeMap != null) return;
        try {
            URI uri = new URI(URL_RUAS_POR_CIDADE);
            HttpURLConnection conexao = (HttpURLConnection) uri.toURL().openConnection();
            conexao.setRequestMethod("GET");
            if (conexao.getResponseCode() == 200) {
                BufferedReader leitor = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
                StringBuilder resposta = new StringBuilder();
                String linha;
                while ((linha = leitor.readLine()) != null) {
                    resposta.append(linha);
                }
                leitor.close();
                conexao.disconnect();
                ObjectMapper mapper = new ObjectMapper();
                ruasPorCidadeMap = mapper.readValue(resposta.toString(), new TypeReference<Map<String, List<String>>>() {});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gera um endereço aleatório a partir da sigla de um estado.
     * Seleciona uma rua da capital do estado correspondente.
     *
     * @param sigla Sigla do estado brasileiro (ex: "SP", "RJ", "BA").
     * @return Endereço formatado no padrão "Rua Nome, número X, Cidade - Estado".
     */
    public static String gerarRuaPorEstado(String sigla) {
        carregarEstadosCapitais();
        carregarRuasPorCidade();

        Map<String, String> estadoInfo = estadosCapitaisMap.get(sigla.toUpperCase());
        if (estadoInfo == null) {
            return "Sigla inválida.";
        }

        String capital = estadoInfo.get("capital");
        List<String> ruas = ruasPorCidadeMap.get(capital);

        if (ruas == null || ruas.isEmpty()) {
            return "Nenhuma rua encontrada para este estado.";
        }

        String rua = ruas.get(random.nextInt(ruas.size()));
        int numero = random.nextInt(9999) + 1;

        return rua + ", número " + numero + ", " + capital.replace("_", " ").toUpperCase() + " - " + sigla.toUpperCase();
    }

    // --- Geradores de nomes ---

    private static String gerarNomeMasculino() {
        carregarNomes();
        List<String> masculinos = nomesMap.get("nomes_masculinos");
        return masculinos.get(random.nextInt(masculinos.size()));
    }

    private static String gerarNomeFeminino() {
        carregarNomes();
        List<String> femininos = nomesMap.get("nomes_femininos");
        return femininos.get(random.nextInt(femininos.size()));
    }

    private static String gerarNomeNeutro() {
        carregarNomes();
        List<String> neutros = nomesMap.get("nomes_neutros");
        return neutros.get(random.nextInt(neutros.size()));
    }

    private static String gerarSobrenome() {
        carregarNomes();
        List<String> sobrenomes = nomesMap.get("sobrenomes");
        return sobrenomes.get(random.nextInt(sobrenomes.size()));
    }

    private static String gerarNomeCompletoMasculino() {
        return gerarNomeMasculino() + " " + gerarSobrenome();
    }

    private static String gerarNomeCompletoFeminino() {
        return gerarNomeFeminino() + " " + gerarSobrenome();
    }

    private static String gerarNomeCompletoNeutro() {
        return gerarNomeNeutro() + " " + gerarSobrenome();
    }

    /**
     * Gera um nome aleatório masculino, feminino ou neutro.
     *
     * @return Um nome próprio aleatório.
     */
    public static String gerarNomeAleatorio() {
        carregarNomes();
        int tipo = random.nextInt(3);
        if (tipo == 0) {
            return gerarNomeMasculino();
        } else if (tipo == 1) {
            return gerarNomeFeminino();
        } else {
            return gerarNomeNeutro();
        }
    }

    /**
     * Gera um nome completo aleatório (nome + sobrenome), podendo ser masculino, feminino ou neutro.
     *
     * @return Nome completo aleatório.
     */
    public static String gerarNomeCompletoAleatorio() {
        carregarNomes();
        int tipo = random.nextInt(3);
        if (tipo == 0) {
            return gerarNomeCompletoMasculino();
        } else if (tipo == 1) {
            return gerarNomeCompletoFeminino();
        } else {
            return gerarNomeCompletoNeutro();
        }
    }

    /**
     * Gera um nome aleatório de acordo com o gênero informado.
     *
     * @param genero "masculino", "feminino" ou "neutro".
     * @return Nome próprio aleatório.
     */
    public static String gerarNomeAleatorio(String genero) {
        carregarNomes();
        genero = genero.toLowerCase();
        if (genero.startsWith("m")) return gerarNomeMasculino();
        else if (genero.startsWith("f")) return gerarNomeFeminino();
        else return gerarNomeNeutro();
    }

    /**
     * Gera um nome completo aleatório (nome + sobrenome) de acordo com o gênero informado.
     *
     * @param genero "masculino", "feminino" ou "neutro".
     * @return Nome completo aleatório.
     */
    public static String gerarNomeCompletoAleatorio(String genero) {
        return gerarNomeAleatorio(genero) + " " + gerarSobrenome();
    }

    // --- Gerador de CPF ---

    /**
     * Gera um CPF válido brasileiro, com dígitos verificadores corretos.
     *
     * @return CPF no formato "000.000.000-00".
     */
    public static String gerarCPF() {
        int[] cpf = new int[11];
        for (int i = 0; i < 9; i++) {
            cpf[i] = random.nextInt(10);
        }
        cpf[9] = calcularDigitoVerificador(cpf, 9);
        cpf[10] = calcularDigitoVerificador(cpf, 10);

        return String.format("%d%d%d.%d%d%d.%d%d%d-%d%d",
            cpf[0], cpf[1], cpf[2],
            cpf[3], cpf[4], cpf[5],
            cpf[6], cpf[7], cpf[8],
            cpf[9], cpf[10]
        );
    }

    private static int calcularDigitoVerificador(int[] cpf, int pos) {
        int soma = 0;
        for (int i = 0; i < pos; i++) {
            soma += cpf[i] * (pos + 1 - i);
        }
        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }

    // --- Geradores de telefone ---

    /**
     * Gera um telefone fixo brasileiro aleatório.
     *
     * @return Telefone no formato "(XX) XXXX-XXXX".
     */
    public static String gerarTelefoneFixo() {
        int ddd = 11 + random.nextInt(89);
        int primeiroDigito = 2 + random.nextInt(7);
        int restante = random.nextInt(9000000) + 1000000;
        return String.format("(%02d) %d%07d", ddd, primeiroDigito, restante);
    }

    /**
     * Gera um telefone celular brasileiro aleatório.
     *
     * @return Telefone no formato "(XX) 9XXXX-XXXX".
     */
    public static String gerarTelefoneCelular() {
        int ddd = 11 + random.nextInt(89);
        int restante = random.nextInt(90000000) + 10000000;
        return String.format("(%02d) 9%08d", ddd, restante);
    }

    // --- Gerador de endereço RMR (Recife/Olinda) ---

    /**
     * Gera um endereço aleatório entre Recife e Olinda usando dados reais.
     *
     * @return Endereço no formato "Rua Nome, nº X, Cidade - PE".
     */
    public static String gerarEnderecoAleatorio() {
        carregarRuas();
        boolean escolherRecife = random.nextBoolean();
        List<String> ruas = escolherRecife ? ruasMap.get("recife") : ruasMap.get("olinda");
        String rua = ruas.get(random.nextInt(ruas.size()));
        int numero = random.nextInt(9999) + 1;
        String cidade = escolherRecife ? "Recife" : "Olinda";
        return rua + ", nº " + numero + ", " + cidade + " - PE";
    }
}
