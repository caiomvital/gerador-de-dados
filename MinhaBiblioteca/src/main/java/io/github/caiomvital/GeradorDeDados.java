package io.github.caiomvital;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GeradorDeDados {

	public static String gerarNomeAleatorio() {
	    try {
	        URI uri = URI.create("https://randomuser.me/api/?nat=BR");
	        URL url = uri.toURL();

	        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
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

	            String json = resposta.toString();
	            String nome = json.split("\"first\":\"")[1].split("\"")[0];
	            //String sobrenome = json.split("\"last\":\"")[1].split("\"")[0];

	            return nome; // + " " + sobrenome;
	        } else {
	            return null;
	        }
	    } catch (Exception e) {
	        return null;
	    }
	}
		
    public static String gerarNomeCompletoAleatorio() {
        try {
            URI uri = URI.create("https://randomuser.me/api/?nat=BR");
            URL url = uri.toURL();

            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
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

                String json = resposta.toString();
                String nome = json.split("\"first\":\"")[1].split("\"")[0];
                String sobrenome = json.split("\"last\":\"")[1].split("\"")[0];

                return nome + " " + sobrenome;
            } else {
                return "Erro na requisição";
            }
        } catch (Exception e) {
            return "Erro: " + e.getMessage();
        }
    }
    
 // Método para gerar um número de CPF aleatório
    public static String gerarCPF() {
        Random random = new Random();
        StringBuilder cpf = new StringBuilder();

        for (int i = 0; i < 9; i++) {
            cpf.append(random.nextInt(10)); // Adiciona números aleatórios
            if (i == 2 || i == 5) {
                cpf.append("."); // Adiciona pontos no formato
            }
        }
        cpf.append("-");
        for (int i = 0; i < 2; i++) {
            cpf.append(random.nextInt(10)); // Adiciona os dois últimos dígitos
        }

        return cpf.toString();
    }

    // Método para gerar um número de telefone fixo aleatório
    public static String gerarTelefoneFixo() {
        Random random = new Random();
        StringBuilder telefoneFixo = new StringBuilder();

        telefoneFixo.append("(");
        telefoneFixo.append(11 + random.nextInt(89)); // Agora gera DDDs de 11 a 99
        telefoneFixo.append(") ");
        telefoneFixo.append(2 + random.nextInt(8)); // O primeiro dígito deve ser entre 2 e 9
        for (int i = 0; i < 7; i++) {
            telefoneFixo.append(random.nextInt(10)); // 8 dígitos
        }

        return telefoneFixo.toString();
    }

    // Método para gerar um número de telefone celular aleatório
    public static String gerarTelefoneCelular() {
        Random random = new Random();
        StringBuilder telefoneCelular = new StringBuilder();

        telefoneCelular.append("(");
        telefoneCelular.append(11 + random.nextInt(89)); // Agora gera DDDs de 11 a 99
        telefoneCelular.append(") ");
        telefoneCelular.append(9); // O primeiro dígito deve ser sempre 9 para celulares
        for (int i = 0; i < 8; i++) {
            telefoneCelular.append(random.nextInt(10)); // 9 dígitos
        }

        return telefoneCelular.toString();
    }
    
    public static String gerarEnderecoAleatorio() {
        try {
            // Conecta à API usando URI (não mais URL direto)
            URI uri = URI.create("https://randomuser.me/api/?nat=BR");
            URL url = uri.toURL();  // Convertendo a URI para URL

            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
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

                // Obtém a resposta JSON como String
                String jsonResponse = resposta.toString();

                // Manipulação do JSON manual com verificações
                String nomeRua = extractJsonValue(jsonResponse, "\"name\":\"", "\"");
                String numeroRuaStr = extractJsonValue(jsonResponse, "\"number\":", ",");
                String cidade = extractJsonValue(jsonResponse, "\"city\":\"", "\"");
                String estado = extractJsonValue(jsonResponse, "\"state\":\"", "\"");
                String cep = extractJsonValue(jsonResponse, "\"postcode\":", ",");

                // Verifica se o número da rua pode ser convertido para um inteiro
                int numeroRua = 0;
                try {
                    numeroRua = Integer.parseInt(numeroRuaStr);
                } catch (NumberFormatException e) {
                    numeroRua = 0; // Valor default caso a conversão falhe
                }

                // Formata o CEP para o padrão "00.000-000"
                String cepFormatado = formatarCep(cep);

                // Garantir que o nome da rua e o número fiquem bem formatados (sem espaços extras)
                String enderecoFormatado = nomeRua.trim() + " " + numeroRua + ", " + cidade + " - " + estado + " - " + cepFormatado;

                // Retornando o endereço formatado
                return enderecoFormatado;
            } else {
                return "Erro na requisição";
            }
        } catch (Exception e) {
            return "Erro: " + e.getMessage();
        }
    }

    // Método auxiliar para extrair valores do JSON de forma segura
    private static String extractJsonValue(String json, String start, String end) {
        try {
            int startIndex = json.indexOf(start);
            if (startIndex == -1) return "Valor não encontrado";
            startIndex += start.length();
            int endIndex = json.indexOf(end, startIndex);
            if (endIndex == -1) return "Valor não encontrado";
            return json.substring(startIndex, endIndex).trim();
        } catch (Exception e) {
            return "Erro ao extrair valor";
        }
    }

    // Método para formatar o CEP no padrão brasileiro "00.000-000"
    private static String formatarCep(String cep) {
        // Se o CEP tem menos de 8 dígitos, adiciona os 3 dígitos finais de forma aleatória
        if (cep.length() == 5) {
            cep = cep + (int) (Math.random() * 1000);  // Adiciona 3 dígitos aleatórios
        }
        // Formata o CEP como "00.000-000"
        if (cep.length() == 8) {
            return cep.substring(0, 5) + "-" + cep.substring(5);
        } else {
            return "CEP inválido";
        }
    }
    
    public static String gerarRuaENumero() {
        try {
            // Conecta à API usando URI (não mais URL direto)
            URI uri = URI.create("https://randomuser.me/api/?nat=BR");
            URL url = uri.toURL();  // Convertendo a URI para URL

            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
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

                // Obtém a resposta JSON como String
                String jsonResponse = resposta.toString();

                // Manipulação do JSON manual com verificações
                String nomeRua = extractJsonValue(jsonResponse, "\"name\":\"", "\"");
                String numeroRuaStr = extractJsonValue(jsonResponse, "\"number\":", ",");

                // Verifica se o número da rua pode ser convertido para um inteiro
                int numeroRua = 0;
                try {
                    numeroRua = Integer.parseInt(numeroRuaStr);
                } catch (NumberFormatException e) {
                    numeroRua = 0; // Valor default caso a conversão falhe
                }

                // Retornando a rua e o número formatados
                return nomeRua.trim() + ", número " + numeroRua;
            } else {
                return "Erro na requisição";
            }
        } catch (Exception e) {
            return "Erro: " + e.getMessage();
        }
    }
    
    public static String gerarEnderecoSimplificado() {
        try {
            // Conecta à API usando URI (não mais URL direto)
            URI uri = URI.create("https://randomuser.me/api/?nat=BR");
            URL url = uri.toURL();  // Convertendo a URI para URL

            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
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

                // Obtém a resposta JSON como String
                String jsonResponse = resposta.toString();

                // Manipulação do JSON manual com verificações
                String nomeRua = extractJsonValue(jsonResponse, "\"name\":\"", "\"");
                String numeroRuaStr = extractJsonValue(jsonResponse, "\"number\":", ",");
                String cidade = extractJsonValue(jsonResponse, "\"city\":\"", "\"");
                String estado = extractJsonValue(jsonResponse, "\"state\":\"", "\"");

                // Verifica se o número da rua pode ser convertido para um inteiro
                int numeroRua = 0;
                try {
                    numeroRua = Integer.parseInt(numeroRuaStr);
                } catch (NumberFormatException e) {
                    numeroRua = 0; // Valor default caso a conversão falhe
                }

                // Verifica se o estado é válido e extrai a sigla do estado
                String siglaEstado = getSiglaEstado(estado);

                // Retornando o endereço simplificado
                return "Rua " + nomeRua.trim() + ", número " + numeroRua + ", " + cidade + " - " + siglaEstado;
            } else {
                return "Erro na requisição";
            }
        } catch (Exception e) {
            return "Erro: " + e.getMessage();
        }
    }

    private static String getSiglaEstado(String estado) {
        // Mapeamento de estados e suas siglas
        Map<String, String> estadosSiglas = new HashMap<>();
        estadosSiglas.put("Acre", "AC");
        estadosSiglas.put("Alagoas", "AL");
        estadosSiglas.put("Amapá", "AP");
        estadosSiglas.put("Amazonas", "AM");
        estadosSiglas.put("Bahia", "BA");
        estadosSiglas.put("Ceará", "CE");
        estadosSiglas.put("Distrito Federal", "DF");
        estadosSiglas.put("Espírito Santo", "ES");
        estadosSiglas.put("Goiás", "GO");
        estadosSiglas.put("Maranhão", "MA");
        estadosSiglas.put("Mato Grosso", "MT");
        estadosSiglas.put("Mato Grosso do Sul", "MS");
        estadosSiglas.put("Minas Gerais", "MG");
        estadosSiglas.put("Pará", "PA");
        estadosSiglas.put("Paraíba", "PB");
        estadosSiglas.put("Paraná", "PR");
        estadosSiglas.put("Pernambuco", "PE");
        estadosSiglas.put("Piauí", "PI");
        estadosSiglas.put("Rio de Janeiro", "RJ");
        estadosSiglas.put("Rio Grande do Norte", "RN");
        estadosSiglas.put("Rio Grande do Sul", "RS");
        estadosSiglas.put("Rondônia", "RO");
        estadosSiglas.put("Roraima", "RR");
        estadosSiglas.put("Santa Catarina", "SC");
        estadosSiglas.put("São Paulo", "SP");
        estadosSiglas.put("Sergipe", "SE");
        estadosSiglas.put("Tocantins", "TO");

        // Retorna a sigla do estado ou "Desconhecido" se não for encontrado
        return estadosSiglas.getOrDefault(estado, "Desconhecido");
    }
  
    //private static final String URL_PASTEBIN = "https://pastebin.com/raw/gfimE4Kn";
    private static final String URL_GITHUB = "https://raw.githubusercontent.com/caiomvital/json-files/refs/heads/main/ruas_rmr.json";

 // Função para desfazer as entidades HTML
 private static String desfazerEntidadesHTML(String input) {
     input = input.replace("&quot;", "\"");
     input = input.replace("&amp;", "&");
     input = input.replace("&lt;", "<");
     input = input.replace("&gt;", ">");
     input = input.replace("&nbsp;", " ");
     return input;
 }

 private static String obterRuaAleatoria() {
     try {
         // Criando URI e convertendo para URL
         URI uri = new URI(URL_GITHUB);
         URL url = uri.toURL();

         // Fazendo a requisição para o Pastebin
         HttpURLConnection connection = (HttpURLConnection) url.openConnection();
         connection.setRequestMethod("GET");

         // Lendo a resposta
         BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
         StringBuilder sb = new StringBuilder();
         String line;
         while ((line = reader.readLine()) != null) {
             sb.append(line);
         }
         reader.close();

         // Obtendo o conteúdo da resposta
         String resposta = sb.toString();

         // Remover tags HTML
         String respostaSemHTML = resposta.replaceAll("<[^>]*>", "");

         // Desfazer entidades HTML
         String respostaFinal = desfazerEntidadesHTML(respostaSemHTML);

         // Agora, vamos procurar pelas listas de ruas das cidades
         String recifeRuasString = respostaFinal.substring(respostaFinal.indexOf("\"recife\"") + 8, respostaFinal.indexOf("\"olinda\"")).trim();
         String olindaRuasString = respostaFinal.substring(respostaFinal.indexOf("\"olinda\"") + 8, respostaFinal.lastIndexOf("}")).trim();

         // Processar ruas de Recife
         String[] recifeRuasArray = recifeRuasString.substring(recifeRuasString.indexOf("[") + 1, recifeRuasString.lastIndexOf("]")).split(",");
         // Processar ruas de Olinda
         String[] olindaRuasArray = olindaRuasString.substring(olindaRuasString.indexOf("[") + 1, olindaRuasString.lastIndexOf("]")).split(",");

         // Gerar um índice aleatório para selecionar uma rua
         Random random = new Random();
         String ruaSelecionada;
         String cidadeSelecionada;
         String estado = "PE"; // Como Recife e Olinda estão em Pernambuco

         // Selecionando aleatoriamente de Recife ou Olinda
         if (random.nextBoolean()) {
             cidadeSelecionada = "Recife";
             ruaSelecionada = recifeRuasArray[random.nextInt(recifeRuasArray.length)].trim().replace("\"", "");
         } else {
             cidadeSelecionada = "Olinda";
             ruaSelecionada = olindaRuasArray[random.nextInt(olindaRuasArray.length)].trim().replace("\"", "");
         }

         // Retorna a rua, cidade e estado no formato desejado
         return ruaSelecionada + ", " + cidadeSelecionada + " - " + estado;

     } catch (Exception e) {
         e.printStackTrace();
         return "Erro ao obter rua: " + e.getMessage();
     }
 }
 
 private static String adicionarNumeroNoMeio(String endereco) {
	    // Dividindo o endereço para inserir o número
	    int indiceVirgula = endereco.indexOf(",");
	    
	    // Verificando se encontrou a vírgula para inserir o número após a rua
	    if (indiceVirgula != -1) {
	        // Gerando um número aleatório entre 1 e 999
	        Random random = new Random();
	        int numeroAleatorio = random.nextInt(999) + 1;

	        // Construindo a nova string
	        String parteRua = endereco.substring(0, indiceVirgula);  // Rua + o que vem antes da vírgula
	        String parteRestante = endereco.substring(indiceVirgula);  // O restante (Cidade - Estado)
	        
	        // Inserindo o número após a rua
	        return parteRua + ", nº " + numeroAleatorio + parteRestante;
	    } else {
	        // Caso não consiga encontrar a vírgula para dividir, retornar o endereço sem alteração
	        return endereco;
	    }
	}

 public static String obterRua() {
	 return adicionarNumeroNoMeio(obterRuaAleatoria());
 }
 
}
