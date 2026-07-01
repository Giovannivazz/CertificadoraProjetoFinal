# Gerenciador de Projetos

- Giovanni Vaz de Andrade, RA: 2349736


 Objetivo do Sistema
Esse aplicativo serve para gerenciar os projetos e atividades de forma prática direto pelo celular, ajudando na organização do dia a dia.

 Funcionalidades Prontas para Avaliação
- Tela principal com a lista de todos os projetos
- Cadastro de novos projetos com validação para não deixar campo vazio
- Opção de editar os dados dos projetos já cadastrados
- Opção de excluir os registros da lista

 Como Compilar e Rodar o Sistema

 Ferramentas usadas
- Android Studio Panda 3 (Versao 2025.3.3)
- Gradle (Versao 9.3.1)
- Banco de dados: Biblioteca Room (SQLite Local)

 Como rodar o banco de dados
Como o projeto usa a biblioteca Room, o banco de dados SQLite é criado e configurado de forma totalmente automática dentro do próprio celular assim que o app abre pela primeira vez. Nao precisa configurar nenhum servidor externo.

 Como compilar o aplicativo
1. Baixe ou clone o código do repositório para o seu computador.
2. Abra o Android Studio e clique em "Open Project" para carregar a pasta do projeto.
3. Espere o Gradle carregar e sincronizar todas as dependências.
4. Conecte um emulador (usamos o Pixel 8 - API 36) ou um celular físico.
5. Clique no botão verde de Run (o play) no menu de cima para instalar e rodar o app.

 Roteiro de Testes

 Dados de acesso padrão
Se o app pedir login, use o usuário admin e a senha admin para entrar no painel.

 Como testar as funções
1. Testar Cadastro: Na tela inicial, clique no botão de mais (+), preencha as informações do formulário e clique em salvar. O projeto tem que aparecer na lista.
2. Testar Edição: Clique em cima de um projeto que já está na lista, mude algum texto e salve para ver se alterou.
3. Testar Exclusão: Clique no botão de deletar o item e veja se ele sumiu da listagem principal.

4. Link do video (instalação - Execucao): https://drive.google.com/file/d/1HIrbA2J9i07Q-2YOeu_CgMBCl5bPfl2s/view?usp=sharing


