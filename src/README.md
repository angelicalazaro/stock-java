# Store Management System

A comprehensive Java application for managing a retail store inventory, built with Object-Oriented Programming principles and featuring both file-based and MySQL database persistence.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Database Schema](#database-schema)
- [Installation](#installation)
- [Usage](#usage)
- [Business Rules](#business-rules)
- [Key Classes](#key-classes)
- [File Format](#file-format)
- [Testing](#testing)
- [Future Enhancements](#future-enhancements)
- [Academic Context](#academic-context)
- [Author](#author)

## Overview

This project simulates a complete store management system with support for articles, suppliers (local and foreign), stock management, sales operations, and data persistence through both text files and MySQL database.

## Features

### Core Functionality

- **Article Management**: Create, read, update, and delete articles with automatic code-based sorting
- **Supplier Management**: Support for both local and foreign suppliers with currency conversion
- **Stock Operations**: 
  - Add new articles to inventory
  - Sell articles with automatic stock updates
  - Restock existing articles
  - Remove articles with zero quantity
- **Interactive Menu System**: Console-based user interface with multiple management sections

### Data Persistence

- **File-based storage**: Automatic save/load to text files (articles.txt, fournisseurs.txt)
- **MySQL Database**: Full CRUD operations with relational data structure
- **Dual persistence**: Choose between file system or database storage

### Object-Oriented Design

- Encapsulation with private attributes and public accessors
- Inheritance (Fournisseur → FournisseurEtranger)
- Polymorphism (overridden methods for foreign suppliers)
- Exception handling for business rule validation
- Copy constructors for safe object duplication

## Technologies Used

- **Language**: Java 16+
- **Database**: MySQL 8.0+
- **JDBC**: MySQL Connector/J 9.4.0
- **Collections**: ArrayList, HashMap
- **I/O**: BufferedReader/Writer for file operations

## Project Structure

```
Store-Management-System/
│
├── src/
│   │
│   ├── Base/                         # Core business entities
│   │   ├── Article.java              # Product entity with code, price, quantity
│   │   ├── Fournisseur.java          # Local supplier with validation & fusion
│   │   ├── FournisseurEtranger.java  # Foreign supplier with currency conversion
│   │   ├── Stock.java                # Stock management with sorted ArrayList
│   │   └── Magasin.java              # Store entity containing stock
│   │
│   ├── BD/                           # Database layer
│   │   └── dbUtil.java               # MySQL connection & CRUD operations
│   │
│   ├── Persistance/                  # File persistence layer
│   │   └── GestionFichiers.java      # CSV-style file I/O for articles/suppliers
│   │
│   ├── Tests/                        # Test suite
│   │   └── TestDB.java               # Database operation tests
│   │
│   └── Main.java                     # Application entry point with menu system
│
├── lib/                              # External libraries
│   └── mysql-connector-j-9.4.0.jar   # JDBC driver
│
├── articles.txt                      # Generated: Article data storage
├── fournisseurs.txt                  # Generated: Supplier data storage
│
└── README.md                         # This file
```

### Class Diagram Overview

```
┌─────────────────┐
│    Magasin      │
│  - name         │
│  - owner        │
│  - description  │
│  - stock        │───────┐
└─────────────────┘       │
                          │ has-a
                          ▼
                  ┌──────────────┐
                  │    Stock     │
                  │ - articles[] │
                  └──────────────┘
                          │ contains
                          ▼
                  ┌──────────────┐
                  │   Article    │
                  │ - code       │
                  │ - price      │
                  │ - quantity   │
                  │ - name       │
                  │ - supplier   │────────┐
                  └──────────────┘        │ references
                                          ▼
                                  ┌────────────────┐
                                  │  Fournisseur   │
                                  │  - id          │
                                  │  - name        │
                                  │  - revenue     │
                                  └────────────────┘
                                          △
                                          │ extends
                                          │
                              ┌───────────────────────┐
                              │ FournisseurEtranger   │
                              │ - country             │
                              │ - currency            │
                              │ - exchangeRate        │
                              └───────────────────────┘
```

## Database Schema

### Article Table

```sql
CREATE TABLE Article (
    codeArticle INT PRIMARY KEY,
    prixArticle DECIMAL(10,2),
    nomArticle VARCHAR(100),
    qtiteStock INT,
    idFournisseur INT,
    CONSTRAINT ck_prix CHECK (prixArticle > 0),
    FOREIGN KEY (idFournisseur) REFERENCES Fournisseur(idFournisseur)
);
```

### Fournisseur Table

```sql
CREATE TABLE Fournisseur (
    idFournisseur INT PRIMARY KEY,
    raisonSociale VARCHAR(100),
    adresse VARCHAR(200),
    matriculeFiscale VARCHAR(50),
    chiffreAffaire INT,
    groupe VARCHAR(50),
    type VARCHAR(20),              -- 'LOCAL' or 'ETRANGER'
    pays VARCHAR(50),              -- NULL for local suppliers
    devise VARCHAR(20),            -- NULL for local suppliers
    coursDeChange DECIMAL(10,4)    -- 0 for local suppliers
);
```

### Entity Relationship

```
┌─────────────┐           ┌──────────────────┐
│   Article   │ N       1 │   Fournisseur    │
│             │───────────│                  │
│ idFournisseur│ (FK)      │ idFournisseur(PK)│
└─────────────┘           └──────────────────┘
```

## Installation

### Prerequisites

- Java JDK 16 or higher
- MySQL Server 8.0 or higher
- MySQL Connector/J (JDBC driver)

### Setup Steps

#### 1. Clone the repository

```bash
git clone https://github.com/yourusername/store-management-system.git
cd store-management-system
```

#### 2. Install MySQL and create database

```bash
mysql -u root -p
```

Execute the following SQL commands:

```sql
CREATE DATABASE gestionStock;
USE gestionStock;

CREATE TABLE Fournisseur (
    idFournisseur INT PRIMARY KEY,
    raisonSociale VARCHAR(100),
    adresse VARCHAR(200),
    matriculeFiscale VARCHAR(50),
    chiffreAffaire INT,
    groupe VARCHAR(50),
    type VARCHAR(20),
    pays VARCHAR(50),
    devise VARCHAR(20),
    coursDeChange DECIMAL(10,4)
);

CREATE TABLE Article (
    codeArticle INT PRIMARY KEY,
    prixArticle DECIMAL(10,2),
    nomArticle VARCHAR(100),
    qtiteStock INT,
    idFournisseur INT,
    CONSTRAINT ck_prix CHECK (prixArticle > 0),
    FOREIGN KEY (idFournisseur) REFERENCES Fournisseur(idFournisseur)
);
```

#### 3. Download MySQL Connector/J

- Download from [MySQL Official Site](https://dev.mysql.com/downloads/connector/j/)
- Extract the ZIP file
- Locate `mysql-connector-j-X.X.X.jar`
- Place it in the `lib/` directory

#### 4. Configure database connection

Edit `BD/dbUtil.java` with your MySQL credentials:

```java
private String dbURL = "jdbc:mysql://localhost:3306/gestionStock";
private String dbUserName = "root";
private String dbPwd = "your_password_here";
```

#### 5. Compile and run

**On Linux/Mac:**

```bash
javac -cp ".:lib/mysql-connector-j-9.4.0.jar" Main.java
java -cp ".:lib/mysql-connector-j-9.4.0.jar" Main
```

**On Windows:**

```bash
javac -cp ".;lib\mysql-connector-j-9.4.0.jar" Main.java
java -cp ".;lib\mysql-connector-j-9.4.0.jar" Main
```

## Usage

### Main Menu Structure

```
Main Menu
├── 1. Article Management
│   ├── Add new article
│   ├── Display all articles
│   ├── Delete article
│   └── Return to main menu
│
├── 2. Store Management (Sales/Inventory)
│   ├── Sell article
│   ├── Restock inventory
│   ├── Display current stock
│   └── Return to main menu
│
├── 3. Database Operations
│   ├── Save stock to MySQL
│   ├── Load stock from MySQL
│   ├── Display database contents
│   └── Return to main menu
│
└── 4. Exit (auto-saves to text files)
```

### Example Workflow

1. **Launch the application**
   ```
   Enter store name, owner, and description
   ```

2. **Add articles with suppliers**
   ```
   Menu 1 → Option 1
   Enter article details
   Choose supplier type (local/foreign)
   ```

3. **Perform sales operations**
   ```
   Menu 2 → Option 1
   Enter article code and quantity
   ```

4. **Save data**
   ```
   Menu 3 → Option 1 (database)
   or automatic file save on exit
   ```

5. **Reload data on next startup**
   ```
   Menu 3 → Option 2 (from database)
   or automatic file load on startup
   ```

## Business Rules

### Validation Rules

- Article codes must be unique within the stock
- Supplier IDs must be greater than 0
- Supplier revenue (chiffre d'affaire) must be at least 1000
- Article prices must be positive
- Articles can only be deleted when stock quantity equals 0

### Operations

- **Sale**: Decrements stock quantity and returns total price
- **Restock**: Increments stock quantity for existing articles
- **Fusion**: Merges two suppliers with same fiscal ID and group
- **Currency Conversion**: Foreign supplier revenue automatically converted to euros

## Key Classes

### Article

Represents a product in the inventory.

**Attributes:**
- `code`: Unique identifier (int)
- `prix`: Price in euros (double)
- `quantiteStock`: Available quantity (int)
- `nomArticle`: Product name (String)
- `fournisseur`: Associated supplier (Fournisseur, nullable)

**Key Methods:**
- `compare(Article)`: Compares articles by code for sorting
- `toString()`: Formatted string representation

### Fournisseur (Supplier)

Base class for suppliers with business logic validation.

**Attributes:**
- `id`: Unique identifier (int)
- `raisonSociale`: Company name (String)
- `adresse`: Address (String)
- `matriculeFiscale`: Tax ID (String)
- `chiffreAffaire`: Revenue (int)
- `groupe`: Business group (String)

**Key Methods:**
- `fusion(Fournisseur, Fournisseur)`: Merges two compatible suppliers
- `get_ca_en_euro()`: Returns revenue in euros

**Exception Handling:**
- `InvalidFoException`: Thrown when ID ≤ 0 or revenue < 1000

### FournisseurEtranger (Foreign Supplier)

Extends Fournisseur with international operations support.

**Additional Attributes:**
- `pays`: Country (String)
- `devise`: Currency (String)
- `coursDeChange`: Exchange rate to euro (double)

**Overridden Methods:**
- `get_ca_en_euro()`: Returns revenue * exchange rate

### Stock

Manages article collection with automatic sorting and business rules enforcement.

**Implementation:**
- Uses `ArrayList<Article>` for storage
- Maintains sorted order by article code
- Enforces unique code constraint

**Key Methods:**
- `insertion(Article)`: Adds article with duplicate check
- `vendre(int code, int quantity)`: Processes sales
- `achat(int code, int quantity)`: Processes restocking
- `supprime(int code)`: Removes article if quantity is 0
- `affiche()`: Displays formatted stock listing

### GestionFichiers

Handles file-based persistence with CSV-style format.

**Methods:**
- `sauvegarderFichiers(Stock)`: Saves articles and suppliers to text files
- `chargerFichiers(Stock)`: Loads data from text files on startup
- `sauvegardeFournisseurs(Stock)`: Extracts unique suppliers from articles
- `chargerFournisseurs()`: Reconstructs supplier objects from file

**Format Features:**
- Semicolon-separated values
- Type differentiation (LOCAL/ETRANGER)
- Handles null values gracefully

### dbUtil

Provides MySQL database connectivity and operations.

**Connection Management:**
- Singleton pattern for connection reuse
- Automatic driver loading
- Connection validation

**CRUD Operations:**
- Article: insert, read, update, delete
- Fournisseur: insert, read by ID, delete
- Stock: bulk save/load with transaction support

**Methods:**
- `sauvegarderStock(Stock)`: Saves all articles and suppliers to database
- `chargerStock(Stock)`: Loads complete stock with supplier relationships
- `insererArticle(Article)`: Adds single article
- `insererFournisseur(Fournisseur)`: Handles both local and foreign suppliers

## File Format

### articles.txt

Semicolon-separated values format:

```
code;price;quantity;name;supplierId
101;599.99;10;Screen 24 inch;1
102;29.99;50;Mechanical Keyboard;1
103;15.50;100;USB Mouse;2
104;8.99;200;USB Cable;-1
```

**Note:** `supplierId = -1` indicates no supplier

### fournisseurs.txt

Extended format with type differentiation:

```
type;id;name;address;taxId;revenue;group;country;currency;rate
LOCAL;1;TechCorp;123 rue Paris;FR-001;50000;Tech;;;
ETRANGER;2;ChinaTech;Beijing Street 45;CN-002;80000;Tech;China;Yuan;0.13
```

**Format Rules:**
- LOCAL suppliers: last 3 fields are empty (;;;)
- ETRANGER suppliers: all 10 fields populated
- Semicolon separator maintains consistent column count

## Testing

### Manual Testing

Run the application and test each menu option:

```bash
java -cp ".:lib/mysql-connector-j-9.4.0.jar" Main
```

### Database Testing

Execute the test suite:

```bash
java -cp ".:lib/mysql-connector-j-9.4.0.jar" Tests.TestDB
```

**Test Coverage:**
- Connection establishment
- CRUD operations for articles
- CRUD operations for suppliers
- Foreign key relationships
- Transaction rollback scenarios

## Future Enhancements

### Technical Improvements

- Implement DAO pattern for better separation of concerns
- Add connection pooling for improved performance
- Implement prepared statement caching
- Add logging framework (SLF4J/Logback)
- Unit tests with JUnit 5
- Integration tests with TestContainers

### Features

- Web-based user interface (JavaFX or Spring Boot + Thymeleaf)
- Multi-user support with role-based authentication
- Advanced reporting and analytics dashboard
- Export to PDF/Excel functionality
- Barcode scanner integration
- Email notifications for low stock alerts
- Integration with payment systems
- REST API for mobile applications
- Real-time inventory updates with WebSockets

### Business Logic

- Supplier rating system
- Purchase order management
- Invoice generation
- Profit margin calculations
- Seasonal pricing strategies
- Loyalty program integration

## Academic Context

This project was developed as part of a Java Object-Oriented Programming course for the RNCP "Concepteur Développeur d'Applications" certification.

### Learning Objectives Covered

**OOP Principles:**
- Encapsulation: Private attributes with controlled access
- Inheritance: Fournisseur → FournisseurEtranger hierarchy
- Polymorphism: Method overriding (toString, get_ca_en_euro)
- Abstraction: Interface separation between UI and business logic

**Advanced Concepts:**
- Exception handling with custom exceptions
- Collection framework (ArrayList, HashMap)
- File I/O with BufferedReader/Writer
- JDBC connectivity and PreparedStatements
- Design patterns (Singleton for DB connection)

**Software Engineering:**
- Package organization and modularization
- Version control with Git
- Code documentation
- Testing strategies

## License

This project is for educational purposes as part of academic coursework.

## Author

**Angelica Lazaro**  
Application Development and Design Student  
RNCP "Concepteur Développeur d'Applications" Program

## Acknowledgments

- Course instructors for comprehensive project specifications and guidance
- MySQL documentation for JDBC best practices and optimization techniques
- Java official documentation for collections framework and I/O operations

---

**Project Status:** Completed (TP1 & TP2)  
**Last Updated:** November 2025  
**Java Version:** 16+  
**Database:** MySQL 8.0+
