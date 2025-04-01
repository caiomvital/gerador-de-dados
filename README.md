# GeradorDeDados

Uma biblioteca Java para gerar dados aleatórios, como nomes, endereços, números de telefone, CPF e muito mais. O **GeradorDeDados** facilita a criação de informações fictícias úteis para testes, simulações ou preenchimento de dados.

## Funcionalidades

- **Geração de Nomes Aleatórios:** Cria nomes realistas utilizando a API `randomuser.me`.
- **Geração de CPF:** Produz CPFs válidos no formato brasileiro.
- **Geração de Telefones:** Telefones fixos e celulares com DDDs realistas.
- **Endereços Aleatórios:** Rua, número, cidade e estado de forma consistente.
- **Extensível:** Código organizado para fácil adição de novas funcionalidades.

## Requisitos

- **Java**: Versão 17 ou superior.
- Conexão com a internet para funcionalidades que usam APIs externas.

## Como usar

1. Adicione a dependência ao seu projeto Maven:
   ```xml
   <dependency>
       <groupId>br.caiomvital</groupId>
       <artifactId>gerador-de-dados</artifactId>
       <version>1.0.0</version>
   </dependency>
