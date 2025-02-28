package it.santosalvatore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*La classe GestoreDatabase contiene metodi per poter gestire il database Mysql */
//Metodi di set e get dei prodotti o degli utenti (o delle strutture dati utilizzate)

public class GestoreDatabase {
    private static final String SELECT_ALL_PRODUCTS = "SELECT * FROM prodotti";
    private static final String SELECT_PRODUCTS_NON_FINITI = "SELECT * FROM prodotti where amount > 0";
    private static final String SELECT_ALL_ORDER_WHERE = "SELECT * FROM ordini WHERE username = ? AND password = ?";
    private static final String SELECT_ALL_CARRELLO_WHERE = "SELECT * FROM carrello WHERE username = ? AND password = ?";
    private static final String SELECT_ALL_UTENTI = "SELECT * FROM utenti";
    private static final String SELECT_ALL_VENDITORE = "SELECT * FROM venditore";
    private static final String DELETE_ONE_CARRELLO = "DELETE FROM carrello WHERE username = ? AND password = ? AND id =  ?";
    private static final String UPDATE_ONE_CARRELLO = "UPDATE carrello SET amount = ? WHERE username = ? AND password = ? AND id = ?";
    public int maxIndex;
    DatabaseSubject databaseSubject = new DatabaseSubject();
    ProductFactory factory = new ConcreteProductFactory();
    UtenteFactory utenteFactory = new ConcreteUtenteFactory();
    VenditoreFactory venditoreFactory = new ConcreteVenditoreFactory();


    public int getMaxIndex() {
        int maxIndex = 0;

        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT MAX(id) AS max_indice FROM prodotti");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                maxIndex = resultSet.getInt("max_indice");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maxIndex;
    }





    public void getAllProductsDB(List<Product> listaProdotti) {
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (listaProdotti == null) {
                listaProdotti = new ArrayList<>();
            }
            while (resultSet.next()) {
                Product prodotto = factory.createProduct(0, "", 0, 0, "", "");
                prodotto.setId_Product(resultSet.getInt("id"));
                prodotto.setName(resultSet.getString("name"));
                prodotto.setPrice(resultSet.getDouble("price"));
                prodotto.setAmount(resultSet.getInt("amount"));
                prodotto.setColor(resultSet.getString("color"));
                prodotto.setImage(resultSet.getString("image"));

                // Verifica se il prodotto è già presente nella lista
                boolean alreadyExists = false;
                for (Product existingProduct : listaProdotti) {
                    if (existingProduct.getId_Product() == prodotto.getId_Product()) {
                        alreadyExists = true;
                        break;
                    }
                }

                // Se il prodotto non è già presente, aggiungilo alla lista
                if (!alreadyExists) {
                    listaProdotti.add(prodotto);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Product> getAllProductsDB() {
        List<Product> listaProdotti = new ArrayList<>();

        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Product prodotto = factory.createProduct(0, "", 0, 0, "", "");
                prodotto.setId_Product(resultSet.getInt("id"));
                prodotto.setName(resultSet.getString("name"));
                prodotto.setPrice(resultSet.getDouble("price"));
                prodotto.setAmount(resultSet.getInt("amount"));
                prodotto.setColor(resultSet.getString("color"));
                prodotto.setImage(resultSet.getString("image"));

                // Verifica se il prodotto è già presente nella lista
                boolean alreadyExists = false;
                for (Product existingProduct : listaProdotti) {
                    if (existingProduct.getId_Product() == prodotto.getId_Product()) {
                        alreadyExists = true;
                        break;
                    }
                }

                // Se il prodotto non è già presente, aggiungilo alla lista
                if (!alreadyExists) {
                    listaProdotti.add(prodotto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaProdotti;
    }


    public List<Product> getProductsNonFinitiDB() {
        List<Product> listaProdotti = new ArrayList<>();

        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCTS_NON_FINITI);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Product prodotto = factory.createProduct(0, "", 0, 0, "", "");
                prodotto.setId_Product(resultSet.getInt("id"));
                prodotto.setName(resultSet.getString("name"));
                prodotto.setPrice(resultSet.getDouble("price"));
                prodotto.setAmount(resultSet.getInt("amount"));
                prodotto.setColor(resultSet.getString("color"));
                prodotto.setImage(resultSet.getString("image"));

                // Verifica se il prodotto è già presente nella lista
                boolean alreadyExists = false;
                for (Product existingProduct : listaProdotti) {
                    if (existingProduct.getId_Product() == prodotto.getId_Product()) {
                        alreadyExists = true;
                        break;
                    }
                }

                // Se il prodotto non è già presente, aggiungilo alla lista
                if (!alreadyExists) {
                    listaProdotti.add(prodotto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaProdotti;
    }


    public void clearOneCarrelloDB(Product product, Utente utente) {
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ONE_CARRELLO)) {

            //Set dei parametri della Query
            preparedStatement.setString(1, utente.getUsername());
            preparedStatement.setString(2, utente.getPassword());
            preparedStatement.setInt(3, product.getId_Product());

            //Esegui la Query e cancella il risultato dal database
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Errore cancellamento CarrelloDB: " + e.getMessage());
        }
    }

    public boolean modificaOneCarrelloDB(Product product, Utente utente, int nuova_quantita) {
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ONE_CARRELLO)) {

            //Set dei parametri della Query
            preparedStatement.setInt(1, nuova_quantita);
            preparedStatement.setString(2, utente.getUsername());
            preparedStatement.setString(3, utente.getPassword());
            preparedStatement.setInt(4, product.getId_Product());

            //Esegui la Query e cancella il risultato dal database
            preparedStatement.executeUpdate();
            return true;



        } catch (SQLException e) {

            System.out.println("Errore cancellamento CarrelloDB: " + e.getMessage());
            return false;
        }
    }


    public List<Product> getAllOrdiniDB(Utente utente) {
        List<Product> listaOrdini = new ArrayList<>();


        try (Connection connection = DatabaseConnector.connect();

             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ORDER_WHERE)) {

            preparedStatement.setString(1, utente.getUsername());
            preparedStatement.setString(2, utente.getPassword());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    Product prodotto = factory.createProduct(0, "", 0, 0, "", "");
                    prodotto.setId_Product(resultSet.getInt("id"));
                    prodotto.setName(resultSet.getString("name"));
                    prodotto.setPrice(resultSet.getDouble("price"));
                    prodotto.setAmount(resultSet.getInt("amount"));
                    prodotto.setColor(resultSet.getString("color"));
                    prodotto.setImage(resultSet.getString("image"));

                    listaOrdini.add(prodotto);
                }

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
            maxIndex = getMaxIndex();
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return listaOrdini;
    }





    public List<Product> getAllCarrelloDB(Utente utente) {
        List<Product> listaCarrello = new ArrayList<>();

        try (Connection connection = DatabaseConnector.connect();

             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CARRELLO_WHERE)) {

            preparedStatement.setString(1, utente.getUsername());
            preparedStatement.setString(2, utente.getPassword());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    Product prodotto = factory.createProduct(0, "", 0, 0, "", "");
                    prodotto.setId_Product(resultSet.getInt("id"));
                    prodotto.setName(resultSet.getString("name"));
                    prodotto.setPrice(resultSet.getDouble("price"));
                    prodotto.setAmount(resultSet.getInt("amount"));
                    prodotto.setColor(resultSet.getString("color"));
                    prodotto.setImage(resultSet.getString("image"));

                    listaCarrello.add(prodotto);
                }

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
            maxIndex = getMaxIndex();
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return listaCarrello;
    }





    public void setUtenteDB(Utente utente, List<Utente> listaUtenti) {

        try (Connection connection = DatabaseConnector.connect()) {
            String insertQuery = "INSERT INTO `e-commerce`.utenti (username, password, indirizzo_di_spedizione, email, numero_di_telefono) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);


            // Incrementa l'indice per ogni prodotto
            if (true) {
                preparedStatement.setString(1, utente.getUsername());
                preparedStatement.setString(2, utente.getPassword());
                preparedStatement.setString(3, utente.getIndirizzo());
                preparedStatement.setString(4, utente.getEmail());
                preparedStatement.setString(5, utente.getNumeroTel());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void getAllUtentiDB(List<Utente> listaUtenti) {

        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_UTENTI);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Utente utente = utenteFactory.createUtente("", "", "", "", "");
                utente.setUsername(resultSet.getString("username"));
                utente.setPassword(resultSet.getString("password"));
                utente.setIndirizzo(resultSet.getString("indirizzo_di_spedizione"));
                utente.setEmail(resultSet.getString("email"));
                utente.setNumeroTel(resultSet.getString("numero_di_telefono"));


                listaUtenti.add(utente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Utente> getAllUtentiDB() {
        List<Utente> listaUtenti = new ArrayList<>();
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_UTENTI);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Utente utente = utenteFactory.createUtente("", "", "", "", "");
                utente.setUsername(resultSet.getString("username"));
                utente.setPassword(resultSet.getString("password"));
                utente.setIndirizzo(resultSet.getString("indirizzo_di_spedizione"));
                utente.setEmail(resultSet.getString("email"));
                utente.setNumeroTel(resultSet.getString("numero_di_telefono"));


                listaUtenti.add(utente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return listaUtenti;
    }


    public void getAllVenditoriDB(List<Venditore> listaVenditore) {

        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_VENDITORE);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Venditore venditore = venditoreFactory.createVenditore("", "");
                venditore.setUsername(resultSet.getString("username"));
                venditore.setPassword(resultSet.getString("password"));


                listaVenditore.add(venditore);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Venditore> getAllVenditoriDBreturn() {
        List<Venditore> listaVenditore = new ArrayList<>();
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_VENDITORE);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Venditore venditore = venditoreFactory.createVenditore("", "");
                venditore.setUsername(resultSet.getString("username"));
                venditore.setPassword(resultSet.getString("password"));
                // OrdiniObserver ordiniObserver = new ConcreteOrdiniObserver(venditore);


                listaVenditore.add(venditore);
            }
            return listaVenditore;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public boolean setAllOrdiniDB(Utente utente, List<Product> listaCarrello) {


        try (Connection connection = DatabaseConnector.connect()) {


            maxIndex++; // Incrementa l'indice per ogni prodotto

            java.util.Date currentDate = new java.util.Date();
            java.sql.Timestamp timestamp = new java.sql.Timestamp(currentDate.getTime());

            for (Product product : listaCarrello) {
                String insertQuery = "INSERT INTO `e-commerce`.ordini (id, name, price, amount, color, image, username, password, data) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

                preparedStatement.setInt(1, product.getId_Product());
                preparedStatement.setString(2, product.getName());
                preparedStatement.setDouble(3, product.getPrice());
                preparedStatement.setInt(4, product.getAmount());
                preparedStatement.setString(5, product.getColor());
                preparedStatement.setString(6, product.getImage());
                preparedStatement.setString(7, utente.getUsername());
                preparedStatement.setString(8, utente.getPassword());
                preparedStatement.setTimestamp(9, timestamp);

                preparedStatement.addBatch();
                preparedStatement.executeBatch();// Aggiungi la query al batch anziché eseguirla immediatamente

                List<Venditore> listaVenditore = new ArrayList<>();
                listaVenditore = getAllVenditoriDBreturn();

                for (Venditore venditore : listaVenditore) {
                    databaseSubject.ordiniInsertDetected(venditore);
                }
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }


    public boolean setOneOrdiniAmountDB(Utente utente, Product product, int amount) {


        try (Connection connection = DatabaseConnector.connect()) {


            java.util.Date currentDate = new java.util.Date();
            java.sql.Timestamp timestamp = new java.sql.Timestamp(currentDate.getTime());


            String insertQuery = "INSERT INTO `e-commerce`.ordini (id, name, price, amount, color, image, username, password, data) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setInt(1, product.getId_Product());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setInt(4, amount);
            preparedStatement.setString(5, product.getColor());
            preparedStatement.setString(6, product.getImage());
            preparedStatement.setString(7, utente.getUsername());
            preparedStatement.setString(8, utente.getPassword());
            preparedStatement.setTimestamp(9, timestamp);

            preparedStatement.addBatch();
            preparedStatement.executeBatch();// Aggiungi la query al batch anziché eseguirla immediatamente



            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;

        }

    }


    public boolean setOneCarrelloAmountDB(Utente utente, Product product, int amount) {


        try (Connection connection = DatabaseConnector.connect()) {


            String insertQuery = "INSERT INTO `e-commerce`.carrello (id, name, price, amount, color, image, username, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE amount = carrello.amount + ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setInt(1, product.getId_Product());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setInt(4, amount);
            preparedStatement.setString(5, product.getColor());
            preparedStatement.setString(6, product.getImage());
            preparedStatement.setString(7, utente.getUsername());
            preparedStatement.setString(8, utente.getPassword());
            preparedStatement.setInt(9, amount);


            preparedStatement.addBatch();
            preparedStatement.executeBatch();// Aggiungi la query al batch anziché eseguirla immediatamente


            return true;

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            return false;
        }

    }


    public boolean setOneProdottoAutoIDDB(Product product) {
        int id = getMaxIndex() + 1;


        try (Connection connection = DatabaseConnector.connect()) {


            String insertQuery = "INSERT INTO `e-commerce`.prodotti (id, name, price, amount, color, image) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, product.getName());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setInt(4, product.getAmount());
            preparedStatement.setString(5, product.getColor());
            preparedStatement.setString(6, product.getImage());


            preparedStatement.addBatch();
            preparedStatement.executeBatch();// Aggiungi la query al batch anziché eseguirla immediatamente


            return true;

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            return false;
        }

    }


    public boolean setOneProdottoIDDB(Product product, int id) {


        try (Connection connection = DatabaseConnector.connect()) {


            String insertQuery = "INSERT INTO `e-commerce`.prodotti (id, name, price, amount, color, image) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, product.getName());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setInt(4, product.getAmount());
            preparedStatement.setString(5, product.getColor());
            preparedStatement.setString(6, product.getImage());


            preparedStatement.addBatch();
            preparedStatement.executeBatch();// Aggiungi la query al batch anziché eseguirla immediatamente


            return true;

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            return false;
        }

    }


    public boolean removeOneProductDB(Product product) {

        try (Connection connection = DatabaseConnector.connect()) {
            String deleteQuery = "DELETE FROM prodotti WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, product.getId_Product()); // Imposta il valore dell'indice come parametro
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

}