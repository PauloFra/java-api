# Java API - Sistema de Produtos

Uma API REST simples para gerenciar produtos, desenvolvida com Spring Boot e PostgreSQL. Este projeto foi criado para estudar e praticar conceitos de desenvolvimento backend com Java.

## Sobre o Projeto

Esta aplicação permite realizar operações básicas de CRUD (criar, ler, atualizar, deletar) em uma base de dados de produtos. Utilizei Spring Boot pela facilidade de configuração e PostgreSQL como banco de dados principal.

## Tecnologias Utilizadas

- **Java 17** - Linguagem de programação
- **Spring Boot 3.1.5** - Framework principal
- **Spring Data JPA** - Para persistência de dados
- **PostgreSQL** - Banco de dados
- **Docker** - Para containerização
- **Maven** - Gerenciamento de dependências
- **Flyway** - Controle de migrações do banco
- **Swagger/OpenAPI** - Documentação da API

## Como Executar

### Usando Docker (mais fácil)

1. Clone o repositório:

   ```bash
   git clone https://github.com/PauloFra/java-api.git
   cd java-api
   ```

2. Execute com Docker Compose:

   ```bash
   docker-compose up -d
   ```

3. A API estará disponível em: http://localhost:8080

### Executando Localmente

Se preferir rodar sem Docker, você precisará ter Java 17 e PostgreSQL instalados.

1. Configure as variáveis no arquivo `application.properties`
2. Execute: `./mvnw spring-boot:run`

## Endpoints da API

A API possui os seguintes endpoints para gerenciar produtos:

- `GET /api/products` - Lista todos os produtos
- `GET /api/products/{id}` - Busca um produto específico
- `POST /api/products` - Cria um novo produto
- `PUT /api/products/{id}` - Atualiza um produto
- `DELETE /api/products/{id}` - Remove um produto

### Exemplo de uso

Criando um produto:

```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Notebook",
    "description": "Notebook para desenvolvimento",
    "price": 2500.00
  }'
```

## Documentação

Após iniciar a aplicação, você pode acessar a documentação interativa em:

- Swagger UI: http://localhost:8080/swagger-ui.html

## Estrutura do Banco

O projeto utiliza uma tabela simples de produtos:

```sql
CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
```

## Comandos Úteis

```bash
# Parar os containers
docker-compose down

# Ver logs da aplicação
docker-compose logs -f app

# Reconstruir a aplicação
docker-compose up --build

# Executar testes
./mvnw test
```

## Configuração

As principais configurações estão nos arquivos:

- `application.properties` - Configurações para desenvolvimento local
- `application-docker.properties` - Configurações para Docker
- `docker-compose.yml` - Configuração dos containers

Para usar senhas personalizadas, crie um arquivo `.env` na raiz:

```
POSTGRES_PASSWORD=suasenha123
```

## Sobre o Desenvolvimento

Este projeto foi desenvolvido seguindo uma arquitetura em camadas:

- **Controllers** - Recebem as requisições HTTP
- **Services** - Contêm a lógica de negócio
- **Repositories** - Fazem o acesso aos dados
- **Models** - Representam as entidades
