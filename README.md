# Gerador de Dados

[![Maven Central](https://img.shields.io/maven-central/v/io.github.caiomvital/gerador-de-dados.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/io.github.caiomvital/gerador-de-dados)

Uma biblioteca Java para gerar dados aleatórios como nomes, endereços, números de telefone, CPFs e muito mais.  
O **GeradorDeDados** facilita a criação de informações fictícias úteis para testes, simulações e preenchimento de dados em ambientes de desenvolvimento.

## Funcionalidades

- ✅ **Geração de Nomes Aleatórios:** Nomes comuns serão gerados a partir de um JSON.
- ✅ **Geração de Nome Completo:** Combinação de nomes e sobrenomes realistas.
- ✅ **Geração de CPF:** Cria CPFs válidos e formatados no padrão brasileiro.
- ✅ **Geração de Telefones:** Telefones fixos e celulares com DDDs realistas (11 a 99).
- ✅ **Geração de Endereços:** Inclui rua, número, cidade e estado, com dados consistentes.
- ✅ **Geração de Endereços por Estado:** Gerar endereços aleatórios em qualquer estado do Brasil.

## Requisitos

- **Java**: Versão 17 ou superior _(idealmente compatível com 21/23 também)_.
- **Maven**: Para gerenciamento de dependências.

## Como usar

### 1. Adicione a dependência ao seu projeto Maven:

```xml
<dependency>
    <groupId>io.github.caiomvital</groupId>
    <artifactId>gerador-de-dados</artifactId>
    <version>1.1.0</version>
</dependency>
