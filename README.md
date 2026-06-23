# java-api-template-bff

Template de API Backend-for-Frontend (BFF) em Java com Spring Boot — base organizada segundo princípios de Clean Architecture. Projeto voltado para compor um BFF leve que integra serviços (ex.: consulta de endereço por CEP).

---

## 🚀 Tecnologias detectadas

- Java: 25
- Spring Boot (parent): 3.5.15
- Spring Web (spring-boot-starter-web)
- Spring JDBC (spring-boot-starter-jdbc)
- MySQL Connector: 9.2.0
- SpringDoc OpenAPI (springdoc-openapi-starter-webmvc-ui): 2.5.0
- Logback (logback-classic)
- JaCoCo: 0.8.15
- LogCaptor (teste): 2.9.3
- Maven (wrapper incluso)

---

## 🏗️ Arquitetura

Segregação por camadas (inspirada em Clean Architecture):

- presentation/ — Controllers, filtros e interceptors (API layer)
- application/ — Casos de uso, DTOs, mappers e regras de negócio
- domain/ — Entidades de domínio
- infrastructure/ — Persistência e integrações externas
- shared/ — Componentes reutilizáveis (notification pattern, utilitários)

A aplicação utiliza Notification Pattern para acumular mensagens de domínio sem lançar exceções de negócio.

---

## 📡 Endpoints principais (detectados)

Base URL: http://localhost:8080

- GET /api/v1/address?cep={cep} — Busca endereço por CEP (200 / 400 / 404 / 500)
- GET / -> redireciona para Swagger UI

A lista completa de endpoints depende dos controllers implementados.

---

## 🗄️ Banco de Dados

Acesso via Spring JDBC. Dependência MySQL presente (mysql-connector-j). Exemplo de configuração (application.yml):

```yaml
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/appdb
    username: root
    password: pwdmysql
    driver-class-name: com.mysql.cj.jdbc.Driver
```

Executando MySQL via Docker

Para subir um container MySQL localmente (porta 3306) com volume persistente, usar:

```bash
docker run --name mysql -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=pwdmysql -v mysql:/var/lib/mysql mysql:8.0.34
```

- `--name mysql`: nome do container
- `-p 3306:3306`: mapeia a porta para acesso local
- `-e MYSQL_ROOT_PASSWORD=pwdmysql`: define senha do usuário root (evite usar senhas em texto em produção)
- `-v mysql:/var/lib/mysql`: volume Docker para persistência dos dados
- imagem usada: `mysql:8.0.34`

Observação: este BFF pode atuar sem persistência própria dependendo do caso — ajuste conforme necessidade.

---

## ⚙️ Como executar

Pré-requisitos:
- Java 21+ (pom declara 25)
- Maven 3.8+ (usar wrapper incluso)

Executando localmente (módulo bff):

```bash
# na raiz do repositório
cd bff
./mvnw clean package
./mvnw spring-boot:run
```

A aplicação inicia na porta 8080 por padrão.

---

## 📖 Documentação da API (Swagger)

Com a aplicação em execução, acessar:

- http://localhost:8080/swagger-ui/index.html
- ou http://localhost:8080/swagger-ui.html

(O HomeController redireciona para a UI do Swagger.)

---

## 🧪 Testes

Executar suíte de testes com Maven:

```bash
./mvnw test
```

JaCoCo configurado no pom.xml para geração de relatório de cobertura.

---

## 🔄 CI/CD

Adicionar workflows em `.github/workflows/` para CI (build, testes, relatório de cobertura). Recomenda-se pipeline: build → test → jacoco report.

---

## 📌 Observações importantes

- Centralizar variáveis sensíveis (senha DB, URLs) em variáveis de ambiente ou secret manager.
- Manter DTOs entre camadas para evitar vazamento de modelos de domínio.
- Validar e padronizar respostas de erro usando Notification Pattern e filtros.

---

## Licença

Ajustar conforme política do projeto.

