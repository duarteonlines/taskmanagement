# Gerenciamento de Tarefas

## Descrição do Projeto
Este é um projeto para gerenciamento de tarefas e empregados de uma empresa fictícia. Ele facilita a identificação de tarefas e responsáveis por essas tarefas. O fluxo do processo é o seguinte:

1. **Administrador**:
    - Responsável por cadastrar as tarefas e os usuários (empregados).
    - Faz a atribuição de uma tarefa a um empregado.
    - Atualiza, exclui e marca tarefas como concluídas.

2. **Empregado**:
    - Pode fazer login e visualizar as tarefas atribuídas a ele.
    - Na primeira versão, cada empregado é responsável por apenas uma tarefa.

## Requisitos Funcionais
- **Administrador**:
    - Cadastrar Administrador
    - Cadastrar Empregado
    - Atualizar Empregado
    - Deletar Empregado
    - Criar Tarefa
    - Atualizar Tarefa
    - Deletar Tarefa
    - Atribuir Tarefa para Empregado
    - Marcar Tarefa como Concluída
- **Empregado**:
    - Visualizar Tarefas Atribuídas

## Requisitos Não Funcionais
1. **Segurança**: Autenticação e autorização para garantir que apenas administradores possam realizar ações críticas.
2. **Performance**: O sistema deve responder a requisições em até 2 segundos.
3. **Manutenibilidade**: Código estruturado e comentado para facilitar futuras manutenções e extensões.

## Arquitetura Utilizada
A arquitetura utilizada foi a de camadas, pois diminui a complexidade do sistema e facilita a manutenção. As principais camadas são:

- **Camada de Apresentação**: Interface do usuário.
- **Camada de Aplicação**: Contém a lógica de negócios.
- **Camada de Persistência**: Gerencia o acesso aos dados.
- **Camada de Banco de Dados**: Armazena os dados da aplicação.

## Modelagem de Dados
A modelagem de dados é baseada na relação entre tarefas e empregados. As principais entidades são:

- **User**:
    - `ID`: Identificador único do usuário.
    - `NAME`: Nome do usuário.
    - `USERNAME`: Nome de usuário para login.
    - `PASSWORD`: Senha do usuário.
    - `EMAIL`: Email do usuário (único).
    - `ROLES`: Lista de papéis do usuário (por exemplo, Administrador, Empregado).
    - `TASK`: Tarefa atribuída ao usuário.

- **Task**:
    - `ID`: Identificador único da tarefa.
    - `TITLE`: Título da tarefa.
    - `DESCRIÇÃO`: Descrição da tarefa.
    - `USER`: Usuário responsável pela tarefa.

## Tecnologias Utilizadas
- **Java**
- **Spring Framework**
- **PostgreSQL**

## Guia para Rodar o Projeto
## Pré-requisitos

Certifique-se de que você tem os seguintes softwares instalados em sua máquina:

- [JDK 21](https://www.azul.com/downloads/#zulu)
- [Maven 3.6.3+](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/downloads)

## Clonando o Repositório

Primeiro, clone o repositório do projeto:

```bash
git clone https://github.com/oliveiraordep/taskmanagement.git
cd taskmanagement
```
## Configurando o projeto

É importante lembrar de criar a classe principal com o nome **TaskmanagementApplication** e adicionar a anotação **@SpringBootApplication**

Abaixo um exemplo
```
package com.pedro.taskmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskmanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskmanagementApplication.class, args);
	}

}
```

Também será necessário configurar o **application.properties** de acordo com seu banco de dados relacional.

## Buildando e rodando o projeto

```bash
mvn clean install
```

```bash
mvn spring-boot:run
```


## Contribuição
Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e pull requests.

## Licença
Este projeto está licenciado sob a [MIT License](LICENSE).
