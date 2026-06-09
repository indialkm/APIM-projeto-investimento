# Organização projeto

## Modelagem

### Classes

#### Usuario

A classe usuário representa o usuário que fara login e terá acessoa s funcionaldiades do app, ele tem os seguintes campos.

* **id:** O id é um tipo Long do tipo único, a PK do banco de dados utilizada para registro de cada objeto.

* **nome:** Nome completo do usuário, do tipo String e obrigatório no banco de dados.

* **email:** Endereço de e-mail do usuário, do tipo String, obrigatório e único (não permite dois usuários com o mesmo e-mail).

* **carteiras:** Lista de carteiras associadas ao usuário. Representa um relacionamento um-para-muitos (`@OneToMany`), onde a remoção ou atualização de um usuário pode se propagar para suas carteiras em cascata e utilizamos o Orphan true para dizer que se usuário é deletado carteira tambem deve ser para não criar um item orfão.

* **carteiras:** É usado para desabilitação e não exclusão direta de um objeto, focando assim em uma melhor rastreabilidade na questãod a auditoria.

#### Carteira
* **id:** O id é um tipo Long do tipo único, a PK do banco de dados utilizada para registro de cada objeto.

* **usuario:** O usuário proprietário desta carteira. Representa uma chave estrangeira (`@ManyToOne`) carregada de forma preguiçosa, só vai carregar o que é necessário (`FetchType.LAZY`) para otimização de performance.

* **nome:** Nome dado à carteira (ex: "Ações", "Reserva de Emergência"), do tipo String e obrigatório.

* **descricao:** Texto livre e opcional para detalhar o objetivo ou observações da carteira.

* **transacoes:** Lista de transações financeiras realizadas dentro desta carteira (`@OneToMany`).

* **ativos:** Lista de ativos atualmente presentes na carteira com seus respectivos saldos (`@OneToMany` apontando para a tabela associativa).

* **ativo:** Indicador booleano que define se a carteira está ativa (`true`) ou inativada (`false`) no sistema. Útil para aplicar exclusão lógica (soft delete), mantendo o histórico de dados intacto mesmo que o usuário não utilize mais a carteira.

#### CarteiraAtivo
* **id:** Objeto de chave primária composta (`@EmbeddedId`), instanciado através da classe `CarteiraAtivoId`. Ele encapsula a combinação única dos IDs de carteira e ativo, garantindo que não existam registros duplicados para o mesmo ativo dentro de uma mesma carteira.

* **carteira:** Relacionamento muitos-para-um (`@ManyToOne`) com a entidade `Carteira`. Utiliza a estratégia de carregamento preguiçoso (`FetchType.LAZY`) para melhor desempenho e faz o mapeamento do ID da carteira para a chave composta através do `@MapsId("carteiraId")`.

* **ativo:** Relacionamento muitos-para-um (`@ManyToOne`) com a entidade `Ativo`. Também mapeado com carregamento preguiçoso (`FetchType.LAZY`), vinculando o ID do ativo à chave composta através do `@MapsId("ativoId")`.

* **quantidade:** Representa o saldo atual acumulado daquele ativo específico dentro daquela carteira (resultado de todas as compras menos as vendas). Utiliza o tipo `BigDecimal` com precisão de 18 dígitos e 4 casas decimais para suportar frações exatas de ativos (como frações de fundos ou criptoativos) sem perda de precisão numérica.

* **precoEntrada:** Armazena o preço médio ponderado de entrada (preço médio de compra) do ativo na carteira do usuário. Definido como `BigDecimal` com precisão de 18 dígitos e 4 casas decimais para mitigar quaisquer erros de arredondamento nos cálculos financeiros e de rentabilidade.

#### CarteiraAtivoId
* **serialVersionUID:** Um identificador de versão estático e exclusivo (`private static final long`) utilizado pelo Java durante o processo de serialização. Ele garante que a classe gravada na memória ou em cache seja compatível com a classe lida pelo sistema, evitando erros de desserialização (`InvalidClassException`).

* **carteiraId:** Atributo do tipo Long que armazena o identificador único da entidade `Carteira`. Ele compõe a primeira metade da chave primária desta tabela e é preenchido automaticamente pelo JPA através do mapeamento `@MapsId` na entidade principal.

* **ativoId:** Atributo do tipo Long que armazena o identificador único da entidade `Ativo`. Ele compõe a segunda metade da chave primária desta tabela, garantindo que o vínculo entre uma carteira específica e um ativo específico seja tratado como um registro único no banco de dados.

#### Ativo
* **id:** O id é um tipo Long do tipo único, a PK do banco de dados utilizada para registro de cada objeto.

* **ticker:** O código de negociação do ativo no mercado (ex: PETR4, IVVB11), do tipo String, obrigatório e único (não permite dois ativos cadastrados com o mesmo ticker).

* **nome:** Nome da empresa ou a descrição nominal do ativo (ex: "Petróleo Brasileiro S.A."), do tipo String e obrigatório.

* **tipoAtivo:** Categoria de classificação do ativo (ex: "Ações", "FII", "CDB"), mapeada para a coluna `tipo_ativo` do tipo String.

* **carteiras:** Lista de posições atuais que ligam este ativo às diferentes carteiras dos usuários (`@OneToMany` apontando para a tabela associativa `CarteiraAtivo`).

* **historicos:** Lista contendo todos os registros históricos de preços e indexadores atrelados a este ativo específico ao longo do tempo (`@OneToMany`).

* **ativo:** Indicador booleano que define se o ativo está atualmente disponível para operações (`true`) ou inativado (`false`) no sistema. Ideal para realizar a exclusão lógica (soft delete) ou suspender temporariamente um ativo sem perder o histórico das transações passadas dos usuários.

#### Transacao
* **id:** O id é um tipo Long do tipo único, a PK do banco de dados utilizada para registro de cada objeto.

* **carteira:** A carteira onde a operação financeira foi realizada. Representa uma chave estrangeira (`@ManyToOne`) obrigatória, configurada com carregamento preguiçoso (`FetchType.LAZY`) para otimização de performance.

* **ativo:** O ativo que foi negociado na operação (ex: PETR4). Representa uma chave estrangeira (`@ManyToOne`) obrigatória e associada de forma preguiçosa (`FetchType.LAZY`).

* **tipo:** Identificador do tipo da operação (mapeado através do enum `TipoTransacao`), gravado no banco de dados como String (`EnumType.STRING`) para determinar se a movimentação foi uma COMPRA ou uma VENDA.

* **quantidade:** O volume/número de cotas negociadas na transação. Utiliza `BigDecimal` com precisão de 18 dígitos e 4 casas decimals para garantir exatidão no suporte a frações de ativos.

* **precoUnitario:** O preço individual pago ou recebido por cada unidade do ativo no momento exato da operação. Mapeado como `BigDecimal` com precisão de 18 dígitos e 2 casas decimais para valores monetários.

* **data:** Data e hora precisas em que a transação financeira ocorreu no mercado, armazenada com o tipo `LocalDateTime`.

* **impostoRetido:** Valor opcional correspondente a impostos retidos na fonte (como o "dedo-duro" do Imposto de Renda) ou taxas de corretagem e emolumentos da operação. Definido como `BigDecimal` com precisão de 18 dígitos e 2 casas decimais.

#### Historico
* **id:** O id é um tipo Long do tipo único, a PK do banco de dados utilizada para registro de cada objeto.

* **ativo:** O ativo ao qual esta linha de histórico pertence. Representa uma chave estrangeira (`@ManyToOne`) obrigatória e configurada com carregamento preguiçoso (`FetchType.LAZY`) para evitar consultas desnecessárias ao banco de dados.

* **precoFechamento:** O preço final de mercado registrado para o ativo no fechamento da data informada. Utiliza o tipo `BigDecimal` com precisão de 18 dígitos e 2 casas decimais, ideal para armazenar valores monetários exatos de cotações.

* **data:** Data e hora exatas de referência daquele registro histórico de preço do ativo, mapeada com o tipo `LocalDateTime`.

* **acumuloInflacao:** Valor percentual acumulado de inflação (como IPCA ou IGPM) registrado até esta data específica. Mapeado como `BigDecimal` com precisão de 5 dígitos e 4 casas decimais para suportar taxas e indexadores econômicos fracionados com precisão matemática.

---

### Relação : Carteira → Carteira Ativo → Ativo

Uma das regras de negócio é a associação entre a carteira, ativos e usuário. Cada usuário pode ter multiplas carteiras que vão receber os ativos. Contudo, cada ativo é um tipo comum para todos ele não terá o preço que foi pago pelo usuário, por conta disso é usado a associação de tabelas onde termos CarteiraAtivo que vai receber uma c
