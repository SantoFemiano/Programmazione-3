package it.santosalvatore;
/*Classe concreta PagamentoBonificoBancario del Pattern Strategy che implementa l'interfaccia PagamentoStrategy */
//La classe Rappresenta un modalità di pagamento ed effettua l'override del metodo "pagamento".
public class PagamentoBonificoBancario implements PagamentoStrategy {
    private final String numeroIban;

    public PagamentoBonificoBancario(String numeroIban) {
        this.numeroIban = numeroIban;
    }

    @Override
    public void pagamento(double prezzoOrdine) {
        System.out.println("Pagamento con bonifico bancario di $" + prezzoOrdine);
    }
}
