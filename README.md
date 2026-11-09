# Terworld

Um jogo simples feito para terminal com aplicações do `OpenJML`. Se trata de uma proposta da disciplina de `Lógica Aplicada a Engenharia de Software` que envolve aplicar em um projeto real anotações do `OpenJML` para praticar o `Design By Contract`.

Requer Java 25 e Maven. Para compilar e rodar o jogo, execute na pasta do projeto:

```sh
mvn
```

Para validar com o `OpenJML`:

```sh
./verify.sh
```

> Tem que ter o `OpenJML v21.0.29` instalado.

### Instruções

Use `a`/`d` para andar, `k` para pular, `j` para atacar, `s` para descer e `Ctrl+C` para sair do jogo. Movimento, pulo e ataque podem acontecer juntos. Chegue ao `?` para avançar.

### Lembretes

* Não deixar nenhum `//@ assume` no código
* Ter apenas uma clausura de condição de quadro `//@ assigns` por especificação
* Buscar aumentar o conjunto das pre-condições e diminuir o conjunto das pós-condições
* Lembrar de especificar construtores com `//@ pure`
* Lembrar de especificaar `//@ public normal_behavior`