package it.santosalvatore;
/*La classe PagamentoStrategy è l'interfaccia del pattern Strategy che contiene un metodo "pagamento" che viene implementato dalle classi concrete*/
public interface PagamentoStrategy {
    void pagamento(double prezzoOrdine);
}
