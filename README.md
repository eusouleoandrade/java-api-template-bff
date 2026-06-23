# java-api-template-bff

Template de API Backend-for-Frontend (BFF) em Java usando Spring Boot. Este repositório fornece uma base organizada segundo princípios de Clean Architecture para acelerar o desenvolvimento de APIs BFF.

---

## 🚀 Tecnologias

- Java 21+
- Spring Boot 3.5.x
- Spring JDBC
- SpringDoc OpenAPI (Swagger)
- Jackson
- Logback
- Maven (com wrapper)

---

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas (inspirada em Clean Architecture):

- presentation/ — Controllers e filtros (API layer)
- application/ — Casos de uso, DTOs e regras de negócio
- domain/ — Entidades de domínio
- infrastructure/ — Persistência e integrações externas
- shared/ — Componentes reutilizáveis (notificações, utils)

---

## 📡 Endpoints (exemplo)

Base URL local: `http://localhost:8080/api/v1`

- `GET /api/v1/health` — Status da aplicação
- `GET /api/v1/resource` — Lista de recursos
- `POST /api/v1/resource` — Cria novo recurso
- `PUT /api/v1/resource/{id}` — Atualiza recurso
- `DELETE /api/v1/resource/{id}` — Remove recurso

> Ajustar rotas reais conforme implementação do módulo.

---

## 🗄️ Banco de Dados

Este template suporta acesso a banco relacional via Spring JDBC. Exemplos de configuração (`application.yml`):

```yaml
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/appdb
    username: root
    password: pwdmysql
    driver-class-name: com.mysql.cj.jdbc.Driver
```

A estrutura de tabelas depende dos módulos implementados no projeto.

---

## ⚙️ Como executar

Pré-requisitos:
- Java 21+
- Maven 3.8+

Executando localmente:

```bash
# Na raiz do repositório
./mvnw clean package
./mvnw spring-boot:run
```

A aplicação inicia na porta 8080 por padrão.

---

## 📖 Documentação da API (Swagger)

Com a aplicação em execução, acessar:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 🧪 Testes

Executar testes com Maven:

```bash
./mvnw test
```

---

## 🔄 CI/CD

Adicionar workflows no diretório `.github/workflows/` para CI (build, testes, cobertura). Este template não pressupõe um pipeline específico — adapte conforme sua organização.

---

## 📌 Boas práticas

- Separar regras de negócio da camada de apresentação
- Usar DTOs para comunicação entre camadas
- Centralizar tratamento de erros e padrões de resposta
- Incluir monitoramento (Actuator) e correlação de logs (correlationId)

---

## Licença

Licença padrão do projeto (adapte conforme necessidade).

