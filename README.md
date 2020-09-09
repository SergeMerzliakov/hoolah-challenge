# Hoolah Code Test

Demo code for a simple transaction processor, which reads data in CSV file format.

## Data Format

The data is expected to be multiple transactions, with a transaction per row where each transaction is of the
form:

* ID - A string representing the transaction id
* Date - The date and time when the transaction took place (format "DD/MM/YYYY hh:mm:ss")
* Amount - The value of the transaction (dollars and cents)
* Merchant - The name of the merchant this transaction belongs to
* Type - The type of the transaction, which could be either PAYMENT or REVERSAL

The application only loads data in CSV format, with comma delimiters and no string 
delimiters

### A Sample File

`ID, Date, Amount, Merchant, Type, Related Transaction
 WLMFRDGD, 20/08/2018 12:45:33, 59.99, Kwik-E-Mart, PAYMENT,
 YGXKOEIA, 20/08/2018 12:46:17, 10.95, Kwik-E-Mart, PAYMENT,
 LFVCTEYM, 20/08/2018 12:50:02, 5.00, MacLaren, PAYMENT,
 SUOVOISP, 20/08/2018 13:12:22, 5.00, Kwik-E-Mart, PAYMENT,
 AKNBVHMN, 20/08/2018 13:14:11, 10.95, Kwik-E-Mart, REVERSAL, YGXKOEIA
 JYAPKZFZ, 20/08/2018 14:07:10, 99.50, MacLaren, PAYMENT,` 


## Setup

The project requires:
    * Java 8+ JDK
    
gradle is run via the wrapper which is part of the repo

## Running

The simplest way is with the "gradle run" command with the following arguments:

* merchant name
* start date in DD/MM/YYYY hh:mm:ss format
* end date in DD/MM/YYYY hh:mm:ss
* full file path of the CSV transaction file


An example is below:

    gradlew run --args="Kwik-E-Mart  '20/08/2018 11:59:00' '20/08/2018 13:00:00' /Users/serge/dev/hoolah-challenge/src/test/resources/transactions1.csv"
    

### Building and Testing

Build with:

    gradlew build
    
    
Run tests with:

    gradlew test    


### Limitations
* The coverage of unit tests is not exhaustive, but illustrates how a full suite of tests would look like
* Simplistic transaction database not suitable for large numbers of transactions
