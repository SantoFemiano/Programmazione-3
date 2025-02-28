package it.santosalvatore;

/*La classe setPagamentoStrategy è la classe concreta che fa parte del Pattern strategy e contiene  metodi per il set del metodo del pagamento e del processo e quindi "pagamento" che viene effettuato con quel metodo.*/

public class setPagamentoStrategy {
    private final PagamentoStrategy pagamentoStrategy;

    public setPagamentoStrategy(PagamentoStrategy pagamentoStrategy) {
        this.pagamentoStrategy = pagamentoStrategy;
    }

    public void processoPagamento(double amount) {
        pagamentoStrategy.pagamento(amount);
    }
}
