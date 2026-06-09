# Documentação de Requisitos e Planejamento de Sprints
## Dashboard de Gestão de Carteiras de Investimentos

**Autores:** * Maria Fernanda Barreto dos Anjos  
* Ingrid Santos Alkimim Rocha  

---

## 1. Introdução
Este documento apresenta as histórias de usuário elaboradas a partir dos épicos definidos para o sistema de Dashboard de gestão de carteiras de investimentos, contendo contexto, critérios de aceitação e definição de pronto (*Definition of Done - DoD*). Também é apresentado o planejamento das entregas estruturado em sprints.

---

## 2. Épicos e Histórias de Usuário

### Épico 1: Gestão de Múltiplas Carteiras
Permitir que cada investidor administre várias carteiras de investimentos, cada uma com suas características específicas e objetivos distintos.

#### História de Usuário 1
> **Como** investidor,  
> **Eu gostaria de** cadastrar múltiplas carteiras,  
> **A fim de** organizar meus investimentos por diferentes objetivos.

* **Contexto:** O usuário pode ter diferentes estratégias (curto prazo, aposentadoria, etc.), exigindo a separação lógica e visual de suas carteiras.
* **Critérios de Aceitação:**
    * O sistema deve permitir o cadastro de múltiplas carteiras.
    * Cada carteira deve possuir obrigatoriamente um nome e uma descrição.
    * O usuário deve conseguir visualizar a lista completa de carteiras cadastradas.
* **Definição de Pronto (DoD):**
    * [ ] Funcionalidade implementada na interface e no backend.
    * [ ] Dados persistidos corretamente no banco de dados.
    * [ ] Testes unitários e integrados realizados com sucesso.

#### História de Usuário 2
> **Como** investidor,  
> **Eu gostaria de** adicionar e gerenciar ativos em cada carteira,  
> **A fim de** controlar meus investimentos de forma centralizada.

* **Contexto:** Cada carteira pode conter diferentes classes e tipos de ativos, como renda fixa e renda variável.
* **Critérios de Aceitação:**
    * Permitir a adição de novos ativos a uma carteira específica.
    * Permitir a remoção de ativos existentes na carteira.
    * Exibir a listagem atualizada de ativos por carteira selecionada.
* **Definição de Pronto (DoD):**
    * [ ] Operações de CRUD de ativos 100% implementadas.
    * [ ] Testes validados e sem quebras de regressão.

---

### Épico 2: Registro de Transações
Possibilitar o registro de transações de compra e venda de ativos em cada carteira, incluindo o tipo de ativo, quantidade, preço e data da transação.

#### História de Usuário 3
> **Como** investidor,  
> **Eu gostaria de** registrar transações de compra e venda de ativos,  
> **A fim de** acompanhar meu histórico financeiro e manter a carteira atualizada.

* **Contexto:** O registro minucioso das transações é o que permite o cálculo correto e retroativo da rentabilidade e do preço médio.
* **Critérios de Aceitação:**
    * Registrar o tipo da transação de forma explícita (Compra ou Venda).
    * Informar obrigatoriamente a quantidade do ativo, preço unitário e data da operação.
    * Disponibilizar uma tela ou seção com o histórico completo de transações efetuadas.
* **Definição de Pronto (DoD):**
    * [ ] Funcionalidade de movimentação financeira implementada.
    * [ ] Validação rigorosa de dados de entrada realizada (evitar valores negativos ou datas futuras).
    * [ ] Persistência correta e íntegra no banco de dados.

---

### Épico 3: Cálculo da Rentabilidade
Realizar o cálculo preciso da rentabilidade de cada ativo e de cada carteira, considerando o ajuste do preço médio de entrada conforme o investidor adiciona fundos.

#### História de Usuário 4
> **Como** investidor,  
> **Eu gostaria de** visualizar a rentabilidade dos meus ativos,  
> **A fim de** analisar criticamente o desempenho dos meus investimentos.

* **Contexto:** A rentabilidade deve considerar matematicamente o preço médio ponderado de aquisição e as variações de mercado em tempo real.
* **Critérios de Aceitação:**
    * Calcular de forma automática a rentabilidade consolidada.
    * Exibir os dados de rendimento detalhados por ativo individual e de forma agregada por carteira.
    * Atualizar os indicadores instantaneamente após a inserção de novas transações.
* **Definição de Pronto (DoD):**
    * [ ] Motores de cálculo de rentabilidade validados matematicamente.
    * [ ] Testes automatizados de cálculo implementados.
    * [ ] Exibição correta e formatada dos valores percentuais e nominais na interface do usuário.

---

### Épico 4: Apresentação Visual da Rentabilidade
Apresentar a rentabilidade das carteiras por meio de gráficos visuais, como gráficos de pizza para destacar a participação de cada ativo e gráficos comparativos para mostrar a rentabilidade relativa entre diferentes carteiras.

#### História de Usuário 5
> **Como** investidor,  
> **Eu gostaria de** visualizar a rentabilidade dos meus ativos por meio de dashboards e comparar carteiras entre si,  
> **A fim de** facilitar a interpretação dos dados e agilizar a tomada de decisões financeiras.

* **Contexto:** A visualização gráfica permite uma melhor interpretação dos dados financeiros. Serão utilizados gráficos de pizza para distribuição (*asset allocation*) e gráficos comparativos para análise temporal de desempenho.
* **Critérios de Aceitação:**
    * Exibir gráficos de pizza representando a distribuição percentual dos ativos dentro de cada carteira.
    * Exibir gráficos comparativos de rentabilidade temporal entre diferentes carteiras do usuário.
    * Permitir a visualização detalhada da rentabilidade isolada por ativo.
    * Atualizar dinamicamente os componentes gráficos conforme novas transações forem registradas.
* **Definição de Pronto (DoD):**
    * [ ] Componentes de gráficos implementados e totalmente integrados às APIs de dados.
    * [ ] Dados renderizados corretamente em conformidade com as regras de negócio de rentabilidade.
    * [ ] Interface de usuário (UI) validada e responsiva.
    * [ ] Testes de usabilidade e de carga básica realizados.

---

### Épico 5: Consideração de Impostos
Levar em conta a incidência de imposto de renda sobre as transações de compra e venda de ativos, garantindo uma análise financeira completa e transparente.

#### História de Usuário 6
> **Como** investidor,  
> **Eu gostaria de** visualizar os impostos incidentes sobre meus investimentos,  
> **A fim de** me planejar financeiramente e cumprir minhas obrigações fiscais.

* **Contexto:** Diferentes tipos de investimento (Renda Fixa, Ações, FIIs) possuem regras e alíquotas distintas de tributação de IR (ex: tabela regressiva vs. alíquota fixa de renda variável).
* **Critérios de Aceitação:**
    * Exibir de forma clara a alíquota de imposto aplicável a cada ativo/operação.
    * Informar ao usuário sobre a obrigatoriedade e estimativa de pagamento do tributo.
    * Diferenciar explicitamente as regras de cálculo para ativos de renda fixa e de renda variável.
* **Definição de Pronto (DoD):**
    * [ ] Regras fiscais e lógicas de tributação mapeadas e implementadas no código.
    * [ ] Informações de deduções de impostos exibidas corretamente nas telas de resumo e extrato.

---

### Épico 6: Valores Deflacionados
Permitir que os investidores verifiquem o rendimento das carteiras in valores deflacionados para um determinado período, possibilitando uma avaliação mais precisa do retorno real dos investimentos.

#### História de Usuário 7
> **Como** investidor,  
> **Eu gostaria de** visualizar os valores e rendimentos ajustados pela inflação,  
> **A fim de** compreender o ganho real de capital e o aumento do meu poder de compra.

* **Contexto:** A análise real de um investimento requer o desconto dos índices oficiais de inflação (como o IPCA) acumulados ao longo do tempo de aplicação.
* **Critérios de Aceitação:**
    * Aplicar o ajuste inflacionário com base em índices oficiais sobre o histórico de rendimentos.
    * Permitir a comparação lado a lado entre o rendimento nominal e o rendimento real (deflacionado).
    * Exibir de forma clara esses dados comparativos dentro do dashboard principal.
* **Definição de Pronto (DoD):**
    * [ ] Mecanismo de integração com APIs de índices econômicos (ex: API do IBGE) implementado.
    * [ ] Fórmulas e cálculos de deflacionamento exaustivamente validados.

---

### Épico 7: Atualização Automatizada das Cotações
Utilizar a API do Google Finance ou outra fonte de dados gratuita para atualizar automaticamente as cotações dos ativos em carteira, garantindo que as informações estejam sempre atualizadas.

#### História de Usuário 8
> **Como** investidor,  
> **Eu gostaria de** visualizar cotações atualizadas de mercado dos meus ativos,  
> **A fim de** acompanhar as oscilações do mercado sem a necessidade de inserção manual de preços.

* **Contexto:** A atualização automatizada dos preços elimina processos manuais e melhora a acurácia das tomadas de decisão.
* **Critérios de Aceitação:**
    * Atualizar de forma automática e periódica as cotações vigentes dos ativos listados.
    * Permitir a filtragem e busca de dados por período ou ticker do ativo.
    * Exibir os valores de mercado atualizados de forma destacada na interface.
* **Definição de Pronto (DoD):**
    * [ ] Integração robusta com a API externa (Google Finance ou similar) concluída.
    * [ ] Tratamento de falhas de conexão/indisponibilidade da API implementado.
    * [ ] Dados refletidos corretamente no frontend para o usuário final.
