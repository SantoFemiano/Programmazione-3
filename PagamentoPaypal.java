package it.santosalvatore;

/*Classe concreta PagamentoPaypal del Pattern Strategy che implementa l'interfaccia PagamentoStrategy */
//La classe Rappresenta un modalità di pagamento ed effettua l'override del metodo "pagamento".

public class PagamentoPaypal implements PagamentoStrategy {
    private final String email;

    public PagamentoPaypal(String email) {
        this.email = email;
    }

    @Override
    public void pagamento(double prezzoOrdine) {
        System.out.println("Pagamento con PayPal di $" + prezzoOrdine);
    }

}
