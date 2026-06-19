# 🛒 ShopApp — JavaFX E-Commerce Desktop Application

<div align="center">

[![Java](https://img.shields.io/badge/Java-17+-orange?logo=openjdk)](https://openjdk.org/)
[![JavaFX](https://img.shields.io/badge/JavaFX-Desktop%20UI-blue?logo=java)](https://openjfx.io/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue?logo=postgresql)](https://www.postgresql.org/)
[![Design Patterns](https://img.shields.io/badge/GoF%20Patterns-5%20implemented-purple)](https://en.wikipedia.org/wiki/Design_Patterns)
[![University](https://img.shields.io/badge/University-Parthenope%20Napoli-green)](https://www.uniparthenope.it/)

**Desktop e-commerce application built with JavaFX, PostgreSQL and 5 Gang of Four design patterns.**  
University project for the *Programmazione 3* course — Università degli Studi di Napoli Parthenope.

</div>

---

## 📌 Project Overview

**ShopApp** is a full-featured desktop e-commerce application developed as a university exam project for *Programmazione 3*. The application allows two types of users — **Clienti** (customers) and **Venditori** (sellers) — to interact on a shared marketplace: sellers manage product catalogues while customers browse, add items to cart, and complete purchases via multiple payment methods.

The project's primary academic objective was the **practical application of Gang of Four (GoF) design patterns** in a real, non-trivial Java application with a GUI, a relational database, and multi-role user management.

### Key Highlights for Recruiters

- ✅ **5 GoF Design Patterns** implemented in a real application context: Factory Method, Strategy, Command, Observer, Singleton
- ✅ **JavaFX GUI** with multi-screen navigation, FXML controllers, and custom `ListCell` rendering
- ✅ **PostgreSQL integration** via raw JDBC — connection pooling via Singleton `DatabaseConnector`
- ✅ **Multi-role system**: Cliente and Venditore with distinct dashboards and permissions
- ✅ **3 payment strategies**: PayPal, Carta di Credito, Bonifico Bancario (pluggable via Strategy pattern)
- ✅ **Command pattern** for payment actions with undo-ready architecture
- ✅ **Observer pattern** for real-time order notifications across user sessions
- ✅ **Factory Method pattern** for decoupled object creation (Utente, Product, Venditore)
- ✅ **Full cart management**: add, remove, quantity management, total calculation
- ✅ **Seller dashboard**: add, edit, delete product listings with real-time DB sync

---

## 🛠️ Tech Stack

| Layer | Technology | Purpose |
|---|---|---|
| **Language** | Java 17+ | Core language |
| **UI Framework** | JavaFX + FXML | Desktop GUI & screen management |
| **Database** | PostgreSQL | Relational data persistence |
| **DB Access** | Raw JDBC | Manual SQL queries, no ORM |
| **Connection** | Custom `DatabaseConnector` (Singleton) | Single DB connection instance |
| **Build** | IntelliJ IDEA / manual classpath | University project setup |
| **Assets** | PNG images, PDF presentation | UI branding + academic deliverable |

---

## 🎨 Design Patterns

This project is a deliberate showcase of **5 Gang of Four patterns** applied to a coherent real-world problem. Each pattern solves a specific design challenge in the application:

### 1. 🏭 Factory Method
**Files:** `ProductFactory` / `ConcreteProductFactory`, `UtenteFactory` / `ConcreteUtenteFactory`, `VenditoreFactory` / `ConcreteVenditoreFactory`

Decouples object creation from business logic. The application creates `Product`, `Utente`, and `Venditore` objects through factory interfaces, making it easy to extend with new user or product types without modifying existing code.

```
ProductFactory (interface)
    └── ConcreteProductFactory → creates ConcreteProduct instances

UtenteFactory (interface)
    └── ConcreteUtenteFactory → creates ConcreteUtente instances

VenditoreFactory (interface)
    └── ConcreteVenditoreFactory → creates ConcreteVenditore instances
```

### 2. 💳 Strategy
**Files:** `PagamentoStrategy`, `PagamentoPaypal`, `PagamentoCartaCredito`, `PagamentoBonificoBancario`, `setPagamentoStrategy`

The payment system uses the Strategy pattern to make payment algorithms interchangeable at runtime. The controller selects the active strategy based on user choice, without any `if/else` branching in business logic.

```
PagamentoStrategy (interface)
    ├── PagamentoPaypal
    ├── PagamentoCartaCredito
    └── PagamentoBonificoBancario
```

### 3. 🎮 Command
**Files:** `Command`, `PaypalCommand`, `CartaCreditoCommand`, `BonificoCommand`, `IndietroCommand`

Payment actions are encapsulated as Command objects, enabling a clean separation between the UI trigger (button click) and the payment execution. The `IndietroCommand` demonstrates the navigation use case. This architecture is ready for undo/redo extension.

```
Command (interface)
    ├── PaypalCommand       → executes PayPal payment
    ├── CartaCreditoCommand → executes credit card payment
    ├── BonificoCommand     → executes bank transfer
    └── IndietroCommand     → navigates back
```

### 4. 👁️ Observer
**Files:** `OrdiniObserver`, `ConcreteOrdiniObserver`, `DatabaseSubject`

The order management system uses the Observer pattern: `DatabaseSubject` acts as the subject that notifies registered observers when new orders are placed. `ConcreteOrdiniObserver` reacts to updates, enabling real-time order list refresh in the seller's dashboard without polling.

```
DatabaseSubject (Subject)
    └── notifies → OrdiniObserver (interface)
                       └── ConcreteOrdiniObserver (updates seller order view)
```

### 5. 🔌 Singleton
**File:** `DatabaseConnector`

Guarantees a single shared JDBC connection to PostgreSQL across the entire application lifecycle. Prevents multiple concurrent connections and centralises connection lifecycle management.

---

## 🏗️ Architecture

The project follows an **MVC-inspired architecture** adapted for JavaFX, with FXML-defined views and Java controller classes. All data access is centralised in `GestoreDatabase`, a 23KB data-access class with all SQL queries for the application.

```
Root (flat package structure)
│
├── Main.java / MainApp.java              # JavaFX entry points
│
├── — VIEW CONTROLLERS —
├── WelcomeController.java                # Login screen, role selection
├── ClienteController.java                # Customer main dashboard
├── VenditoreController.java              # Seller main dashboard
├── ProdottiController.java               # Product browsing (customer)
├── ProdottiVenditoreController.java      # Product management (seller)
├── AggiungiAlCarrelloController.java     # Product detail + add to cart
├── CarrelloController.java               # Cart view, quantities, totals
├── SceltaPagamentoController.java        # Payment method selection
├── PaypalController.java                 # PayPal payment screen
├── CartaDiCreditoController.java         # Credit card payment screen
├── BonificoController.java               # Bank transfer screen
├── OrdiniController.java                 # Order history (customer + seller)
├── AggiungiProdottoController.java       # Add new product (seller)
├── EliminaProdottoController.java        # Delete product (seller)
├── ProdottoModificaController.java       # Edit product (seller)
├── ModificaController.java               # Generic edit screen
├── InfoAccountController.java            # User account info
├── ProgressBarController.java            # Payment progress animation
│
├── — DOMAIN MODEL —
├── Utente.java / ConcreteUtente.java     # User entity
├── Venditore.java / ConcreteVenditore.java # Seller entity
├── Product.java / ConcreteProduct.java   # Product entity
│
├── — PATTERNS —
├── *Factory.java / Concrete*Factory.java # Factory Method
├── PagamentoStrategy.java + impls        # Strategy
├── Command.java + *Command.java          # Command
├── OrdiniObserver.java + impls           # Observer
├── DatabaseConnector.java                # Singleton
│
├── — DATA ACCESS —
├── GestoreDatabase.java                  # All SQL queries (JDBC)
├── DatabaseSubject.java                  # Observable DB wrapper
│
└── — UTILITIES —
    ├── CambioSchermata.java              # Screen navigation helper
    ├── GestoreCarrello.java              # Cart state manager
    ├── LoginUtente.java / RegistrazioneUtente.java
    ├── AzioniCliente.java
    ├── Avvisi.java / ListaAvvisi.java    # Notification system
    ├── OrderListCell.java                # Custom JavaFX ListCell renderer
    └── DataInitializable.java            # Controller data injection interface
```

---

## 🔄 Application Flow

```
Launch → WelcomeController
              │
    ┌─────────┴─────────┐
    ▼                   ▼
ClienteController   VenditoreController
    │                   │
    ├── Browse Products  ├── Add/Edit/Delete Products
    ├── Add to Cart      ├── View Orders (Observer notified)
    ├── View Cart        └── Manage Account
    ├── Choose Payment
    │     ├── PayPal     (Strategy + Command)
    │     ├── Carta      (Strategy + Command)
    │     └── Bonifico   (Strategy + Command)
    └── View Orders
```

---

## 🗃️ Data Model

| Entity | Description |
|---|---|
| `Utente` | Registered user (email, password, name, address) |
| `Venditore` | Seller account linked to a user |
| `Product` | Catalogue item (name, description, price, quantity, seller) |
| `Carrello` | Cart: items associated to a user session |
| `Ordine` | Completed order with payment method and status |
| `Avviso` | Notification/alert for users |

All persistence is handled via raw JDBC in `GestoreDatabase.java`, with PostgreSQL as the backing store.

---

## 🚀 Setup & Run

### Prerequisites

- Java 17+
- JavaFX SDK (configured on classpath)
- PostgreSQL database

### 1. Clone the repository

```bash
git clone https://github.com/SantoFemiano/Programmazione-3.git
cd Programmazione-3
```

### 2. Configure the database connection

Edit `DatabaseConnector.java` with your PostgreSQL credentials:

```java
private static final String URL = "jdbc:postgresql://localhost:5432/shopapp";
private static final String USER = "your_user";
private static final String PASSWORD = "your_password";
```

### 3. Run the application

```bash
# With JavaFX on module path
java --module-path /path/to/javafx-sdk/lib \
     --add-modules javafx.controls,javafx.fxml \
     -cp . MainApp
```

---

## 📎 Academic Context

| Field | Details |
|---|---|
| **Course** | Programmazione 3 |
| **University** | Università degli Studi di Napoli Parthenope |
| **Objective** | Apply GoF design patterns in a full JavaFX application |
| **Deliverable** | Working desktop app + `Presentazione.pdf` |
| **Patterns demonstrated** | Factory Method, Strategy, Command, Observer, Singleton |

> 📄 The full academic presentation (slides + UML diagrams) is available in [`Presentazione.pdf`](./Presentazione.pdf).

---

## 👤 Author

**Santo Femiano**
- GitHub: [@SantoFemiano](https://github.com/SantoFemiano)
- University: Università degli Studi di Napoli Parthenope
