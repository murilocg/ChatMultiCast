## Executando o Servidor
Para executar o servidor do chat a classe server.ServerRunner deve ser executada.

## Executando o Cliente
Antes de iniciar o cliente não se esqueça de iniciar o Servidor. Para iniciar o cliente execute o classe client.ClientRunner.

### Entrando em uma sala
Para entrar em uma sala basta executar o comando `JOIN <nomeDaSala>`. Obs: Ainda nã é suportado mais de uma sala.

### Enviando uma mensagem
Antes de enviar uma mensagem será necessário entrar em uma sala, após entrar apenas digite a mensagem a ser enviada para o grupo de chat.

### Sair da Sala
Para sair de uma sala digite LEAVE

## Protocolo
A comunicação entre o cliente e o servidor permite três ações possíveis JOIN, SEND e LEAVE, são essas as definições de cada uma:

- JOIN: Ação para entrar em uma sala.
- SEND: Ação para enviar uma mensagem para uma sala.
- LEAVE: Ação para sair de uma sala.

Cada request que é enviada para o servidor é compactada em uma string e enviada em uma conexão UDP. As request seguem o padrão `action=<ProtocolAction>,username="<nomeDoUsuario>",chatroom="<nomeDoChat>";`. A request pode ter quanto atributos forem necessários, separando cada atributo por "," e terminado a request com ";".
 
Cada response que é recebida é compactada em uma string e enviada em uma conexão UDP. As response seguem o padrão `action=<ProtocolAction>,statu=<Status>,message="<mensagemDeErro>";`. Outros atributos podem ser adicionados, como exemplo temos a ResponseEntrar que possui os atributos chatHost e chatPort.

## Responsabilidade das Classes

### server

ServerRunner: Responsável pro tratar os dados das requisições e passar dos dados para a classe Server
Server: Implementa as regras de negócio do chat.
Chatroom: representação de uma sala de chat.

### client

ClientRunner: Responsável por inicializar o client e enviar requisições para o servidor.
Client: Implementa as regras de negócio do client.
ReadClient: Escuta as mensagens que veem do multicast e renderiza na tela.

### protocol
Regras de protocolo que serão trocadas entre o Cliente e o Servidor.

ProtocolAction: Enum que define quais são as ações possíveis de comunicação entre o cliente e o servidor. Valores válidos: JOIN, SEND, LEAVE, INVALID
Unpacker: Utilitário que auxilia os desenpacotadores a extrair atributos da string de Response e Request.

### request

Request: Representação de uma requisição.
RequestSend: Tipo de request para a ação de protocolo do tipo ProtocoloAction.SEND, essa classe é filha da Request.
RequestPacker: converte as mensagens enviadas pelo usuário para uma Request.
RequestUnpacker: Descompacta um requisição e retorna uma obeto Request.

### response

Response: Representação de uma resposta do servidor.
ResponseEntrar: Tipo de resposta para request do tipo ProtocolAction.JOIN, essa classe é filha da Response.
ResponseUnpacker: Utilizada pelo cliente para descompactar um response do servidor.
Status: Enum que representa o statu da Response. Valores válidos: OK, ERROR

### connection

MulticasConnection: Gerencia as comunicações multicast; recebe e envia mensagens em uma rede multicast.
UDPConnection: Gerencia as comunicações UDP; envia e recebe mensagens em uma rede UDP.

### config

ConfigServer: Configurações gerais de um servidor.
