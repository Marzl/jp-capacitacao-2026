# JP Capacitação 2026 - API de Gerenciamento de Produtos e Estoque

## Descrição
Projeto desenvolvido em Spring Boot para gerenciamento de produtos, categorias, estoque e histórico de preços.

A aplicação permite cadastrar produtos e categorias, organizar categorias em hierarquia, controlar movimentações de estoque e registrar alterações de preço para fins de rastreabilidade.

## Tecnologias utilizadas
- Java 21
- Spring Boot 3.5.11
- Spring Web
- Spring Data JPA
- Spring Validation
- Oracle Database (OJDBC11)
- Maven
- Lombok
- Swagger / OpenAPI

## Funcionalidades

### Categorias
- Criar categoria
- Listar categorias ativas
- Buscar categoria por id
- Atualizar categoria
- Inativar categoria (delete lógico)
- Relacionar categoria pai e subcategoria

### Produtos
- Criar produto
- Listar produtos ativos
- Buscar produto por id
- Atualizar produto
- Inativar produto (delete lógico)
- Atualizar preço do produto
- Consultar histórico de preço por produto
- Definir estoque mínimo

### Estoque
- Entrada de estoque
- Saída de estoque
- Consulta de estoque
- Identificação de estoque baixo

## Regras de negócio
- Todo produto deve pertencer a uma categoria
- Categorias podem possuir hierarquia
- Não pode existir categoria com o mesmo nome no mesmo nível
- Categoria não pode ser categoria pai dela mesma
- Alterações de preço geram histórico
- Produtos e categorias utilizam delete lógico
- Não é permitido inativar categoria com produtos ativos vinculados
- Não é permitido inativar categoria com subcategorias ativas vinculadas
- Apenas registros ativos são considerados nas operações principais

## Como executar o projeto
1. Clone este repositório
2. Certifique-se de estar usando Java 21
3. Configure o banco de dados Oracle
4. Ajuste as credenciais no arquivo de configuração da aplicação
5. Execute a aplicação pela IDE ou com Maven
6. Acesse a documentação Swagger

## Documentação da API
A documentação dos endpoints pode ser acessada pelo Swagger em:

`http://localhost:9090/swagger-ui.html`

## Estrutura do projeto
- `controller` - endpoints da API
- `service` - regras de negócio
- `repository` - acesso ao banco de dados
- `model` - entidades
- `service.dto` - DTOs de entrada e saída
- `exceptions` - tratamento global de exceções

## Exemplos de endpoints
- `POST /categorias`
- `GET /categorias`
- `PUT /categorias/{id}`
- `DELETE /categorias/{id}`
- `POST /produtos`
- `GET /produtos`
- `PATCH /produtos/{id}/preco`
- `GET /produtos/{id}/historico-preco`
