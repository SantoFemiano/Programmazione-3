package it.santosalvatore;
/*Classe concreta PagamentoCartaCredito del Pattern Strategy che implementa l'interfaccia PagamentoStrategy */
//La classe Rappresenta un modalità di pagamento ed effettua l'override del metodo "pagamento".

public class PagamentoCartaCredito implements PagamentoStrategy {
    private final String numeroCarta;
    private final String nomeCarta;
    private final String Scadenza;


    public PagamentoCartaCredito(String numeroCarta, String nomeCarta, String Scadenza) {
        this.numeroCarta = numeroCarta;
        this.nomeCarta = nomeCarta;
        this.Scadenza = Scadenza;
    }

    @Override
    public void pagamento(double prezzoOrdine) {
        System.out.println("Pagamento con carta di credito di $" + prezzoOrdine);
    }
}
