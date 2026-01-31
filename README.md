# 🚀 FlowPay Support Manager - Desafio Técnico

Sistema inteligente de gestão de atendimentos e triagem automática de chamados, desenvolvido para otimizar a distribuição de carga entre especialistas de suporte.

---

## 🧠 Arquitetura e Design
O projeto foi estruturado seguindo uma **Arquitetura em Camadas com foco em Domínio (DDD)** e princípios **SOLID**, garantindo um código limpo, testável e escalável.

### Decisões Técnicas de Alto Nível:
* **Rich Domain Model:** As entidades (Atendente e Atendimento) possuem comportamento próprio. Lógicas de negócio residem no domínio, não apenas nos serviços.
* **Single Responsibility Principle (SRP):** Criação do `TriagemService` exclusivo para a lógica de classificação de texto, isolando-a da orquestração de persistência.
* **Encapsulamento via DTOs:** Uso de **Java Records** para transferir dados entre camadas, protegendo a integridade das entidades do banco de dados e evitando acoplamento.
* **Global Exception Handling:** Tratamento centralizado de erros com `@ControllerAdvice`, garantindo respostas padronizadas e códigos HTTP semânticos (ex: 422 para erros de negócio).

---

## 📁 Estrutura do Projeto
A organização dos pacotes reflete a separação clara de responsabilidades:

* **`api`**: Camada de interface, contendo Controllers e DTOs.
* **`config`**: Configurações de sistema, como Swagger e Bean Validation.
* **`domain`**: O núcleo do sistema, contendo o modelo de domínio (`model`) e as regras de negócio (`service`).
* **`repository`**: Camada de infraestrutura para persistência de dados.
* **`exception`**: Exceções customizadas e o manipulador global de erros.

---

## 🛠️ Tecnologias Utilizadas
* **Back-end:** Java 21, Spring Boot 3.4.2, Spring Data JPA, H2 Database, Lombok.
* **Documentação:** Swagger UI (OpenAPI 3).
* **Validação:** Jakarta Bean Validation.

---

## ⚙️ Como Executar o Projeto

### 1. Pré-requisitos
* Java 21 instalado.
* Maven 3.x (ou utilize o `mvnw` incluso).

### 2. Execução
1. Na raiz do projeto, execute:
   ```bash
   mvn clean install
   mvn spring-boot:run

O servidor iniciará em: [http://localhost:8080](http://localhost:8080).

### Testando a API

- **Swagger UI**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- **H2 Console**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

**JDBC URL**: `jdbc:h2:mem:testdb`

**User**: `sa` | **Password**: `password`

### 📈 Regras de Negócio Implementadas

- **Triagem Automática**: Classificação do chamado por especialidade (PIX, CARTÃO, EMPRÉSTIMO) baseada no assunto.
- **Limite de Carga**: Cada atendente suporta no máximo 3 atendimentos simultâneos.
- **Fila de Espera**: Chamados excedentes entram em fila automática e são distribuídos conforme a disponibilidade dos especialistas.
- **Distribuição Inteligente**: O sistema prioriza atendentes com menor carga de trabalho dentro da mesma especialidade.

**Desenvolvido por João Meneses** 🚀
