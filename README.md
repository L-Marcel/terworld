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

## Instruções

Use `a`/`d` para andar, `k` para pular, `j` para atacar, `s` para descer e `Ctrl+C` para sair do jogo. Movimento, pulo e ataque podem acontecer juntos. Chegue ao `?` para avançar.

## Lembretes

* Não deixar nenhum `//@ assume` no código
* Ter apenas uma clausura de condição de quadro `//@ assignable` por especificação
* Buscar aumentar o conjunto das pre-condições e diminuir o conjunto das pós-condições
* Lembrar de especificar construtores com `//@ pure`
* Lembrar de especificaar `//@ public normal_behavior`

## Frustrações

### Por que `--smt-strings=false`?

Essa `flag` desativa um resolvedor nativo de Strings, que permite ao `OpenJML` entender de forma nativa operações como: concatenação, `substring`, `contains`, `indexOf`, etc. Infelizmente, isso estava ocasionando explosão de casos de verificação, então desativamos.

Felizmente, isso não causa perda de segurança nas especificações formais do `OpenJML`, apenas nos força, ocasionalmente, a reescrever algumas verificações de strings de forma mais manual.

### Precisamos definir algumas especificações 'nativas'

No arquivo original de especificação de `List` na versão do `OpenJML` que pegamos existia literalmente `TO DO` de `FIXME` nas especificações de métodos como `remove`.

```java
/*-RAC@
@ also public normal_behavior
@   requires values.contains(o);
@   assignable values;
@   ensures !\result ==> values.length == \old(values.length);
@   ensures \result ==> values.length == \old(values.length-1);
@*/
/* FIXME @ ensures (\exists \bigint j; 0<=j && j<\old(size()) && Collection.nullequals(o,get(content,j));
        (\forall \bigint k; 0<=k && k<j; get(content,k) == \old(get(content,k)))
    &&  (\forall \bigint k; j<k && k<content.theSize; get(content,k-1)==\old(get(content,k+1)))); */
/*-RAC@ also public exceptional_behavior
@   requires !values.contains(o);
@   assignable \nothing;
@   signals_only NoSuchElementException;
@*/
// FIXME - watch out for optional exceptions
/*+RAC@ also public behavior requires true; */
boolean remove(Object o);
```

Por conta disso, importamos logo todas para dentro do projeto para podermos fazer as correções necessárias e conseguirmos rodar a validação com sucesso.