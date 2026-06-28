# java-api-template-bff

API **Backend-for-Frontend (BFF)** construída com **Java 25** e **Spring Boot 3.5.15**, seguindo os princípios de **Clean Architecture** com separação em camadas bem definidas. Integra serviços externos como consulta de endereço por CEP (ViaCEP e BrasilAPI).

---

## 🚀 Tecnologias

| Tecnologia | Versão | Descrição |
|---|---|---|
| Java | 25 | Linguagem de programação |
| Spring Boot | 3.5.15 | Framework principal |
| Spring Web | — | Camada REST |
| Spring JDBC | — | Acesso a banco de dados relacional |
| Spring Actuator | — | Monitoramento e health checks |
| Spring DevTools | — | Reload automático em desenvolvimento |
| MySQL Connector | 9.2.0 | Driver de conexão com MySQL |
| SpringDoc OpenAPI | 2.5.0 | Documentação Swagger/OpenAPI |
| Jackson | — | Serialização/deserialização JSON |
| Logback | — | Sistema de logging estruturado |
| JaCoCo | 0.8.15 | Relatório de cobertura de testes |
| LogCaptor | 2.9.3 | Captura de logs em testes unitários |

---

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas inspirada em **Clean Architecture**, com as seguintes divisões:

```
src/main/java/com/project/bff/
│
├── domain/                              # Entidades de domínio
│   ├── common/
│   │   └── BaseEntity.java
│   └── entities/
│       └── AddressAudit.java
│
├── application/                         # Regras de negócio e casos de uso
│   ├── dtos/
│   │   ├── models/                      # Modelos intermediários
│   │   ├── responses/                   # DTOs de saída
│   │   └── wrappers/                    # Wrappers de resposta
│   ├── exceptions/
│   │   └── AppException.java
│   ├── interfaces/
│   │   ├── clients/                     # Contratos de clientes externos
│   │   ├── repositories/               # Contratos de repositórios
│   │   ├── services/                    # Contratos de serviços
│   │   └── useCases/                    # Contratos de casos de uso
│   ├── mappings/                        # Mapeadores entre DTOs e entidades
│   └── useCases/
│       └── GetAddressUseCase.java
│
├── infrastructure/                      # Implementações de infra
│   ├── clients/                         # Clientes HTTP externos (ViaCEP, BrasilAPI)
│   │   ├── BrasilApiCepClient.java
│   │   └── ViaCepClient.java
│   ├── persistence/
│   │   └── repositories/
│   │       └── AddressAuditRepositoryAsync.java
│   └── services/
│       └── CepService.java
│
├── presentation/                        # Controllers, filtros e interceptors
│   ├── controllers/
│   │   ├── HomeController.java
│   │   └── v1/
│   │       └── AddressController.java
│   ├── filters/
│   │   └── ErrorHandlerFilter.java
│   └── interceptors/
│       ├── CorrelationIdInterceptor.java
│       └── NotificationContextInterceptor.java
│
└── shared/                              # Componentes compartilhados
    ├── notifications/                   # Padrão de notificação de domínio
    │   ├── abstractions/
    │   ├── contexts/
    │   ├── interfaces/
    │   └── models/
    └── utils/
        └── MsgUtil.java
```

> A aplicação utiliza o **Notification Pattern** para acumular mensagens de domínio sem lançar exceções de negócio.

---

## 📡 Endpoints da API

Base URL: `http://localhost:8080`

| Método | Endpoint | Descrição | Status |
|---|---|---|---|
| `GET` | `/api/v1/address?cep={cep}` | Busca endereço por CEP | `200` `400` `404` `500` |
| `GET` | `/` | Redireciona para o Swagger UI | `302` |

---

## 🗄️ Banco de Dados

Acesso via **Spring JDBC** com MySQL. Configure as credenciais em `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/bffdb
    username: root
    password: pwdmysql
    driver-class-name: com.mysql.cj.jdbc.Driver
```

### Executando MySQL via Docker

```bash
docker run --name mysql -d \
  -p 3306:3306 \
  -e MYSQL_ROOT_PASSWORD=pwdmysql \
  -v mysql:/var/lib/mysql \
  mysql:8.0.34
```

### Criando o schema e a tabela de auditoria

Execute no MySQL Workbench ou via linha de comando:

```sql
CREATE DATABASE IF NOT EXISTS bffdb;
USE bffdb;

CREATE TABLE addressAudit (
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    cep      VARCHAR(8)   NOT NULL,
    dataHora DATETIME     NOT NULL,
    PRIMARY KEY (id)
);
```

Alternativa via Docker CLI:

```bash
# Criar o banco de dados
docker exec -i mysql mysql -uroot -ppwdmysql \
  -e "CREATE DATABASE IF NOT EXISTS bffdb;"

# Executar o script SQL
docker cp bff/src/main/java/com/project/bff/infrastructure/persistence/scripts/AddressAudit.sql \
  mysql:/tmp/AddressAudit.sql

docker exec -i mysql mysql -uroot -ppwdmysql bffdb < /tmp/AddressAudit.sql
```

> ⚠️ Em produção, utilize variáveis de ambiente ou um secret manager para gerenciar credenciais. Nunca versione senhas em texto.

---

## ⚙️ Como executar

**Pré-requisitos:**
- Java 21+ (o `pom.xml` declara Java 25)
- Maven 3.8+ (use o wrapper incluso)

```bash
# Na raiz do repositório
cd bff
./mvnw clean package
./mvnw spring-boot:run
```

A aplicação sobe na porta `8080` por padrão.

---

## 📖 Documentação da API (Swagger)

Com a aplicação em execução, acesse:

```
http://localhost:8080/swagger-ui/index.html
```

> O `HomeController` redireciona automaticamente a raiz `/` para a UI do Swagger.

---

## 🧪 Testes

```bash
./mvnw test
```

O **JaCoCo** está configurado no `pom.xml` para gerar o relatório de cobertura em `target/site/jacoco/index.html`.

---

## 🔄 CI/CD

Adicione workflows em `.github/workflows/` para automatizar o pipeline:

```
build → test → jacoco report
```

---

## 📌 Boas práticas aplicadas

- **Notification Pattern** — Acumula erros de domínio sem lançar exceções desnecessárias
- **Separation of Concerns** — DTOs distintos por camada evitam vazamento de modelos de domínio
- **Correlation ID** — Rastreamento de requisições via interceptor dedicado
- **Padronização de erros** — Respostas de erro centralizadas via `ErrorHandlerFilter`
- **Secrets** — Centralize variáveis sensíveis em variáveis de ambiente ou secret manager
