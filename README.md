<p align="center"><img src="https://i.postimg.cc/4yx8V0Jc/Quixel-Texel-Logo.png" alt="Quixel_Texel_Logo" height="400"></p>

# Quixel Texel

Quixel Texel intende migliorare il flusso di lavoro dello sviluppo indie attraverso un servizio web. Questa scelta è fondata sulla volontà di abbattere sia i costi economici che temporali dello sviluppo videoludico indie. Si intende quindi offrire un servizio che non richiede una licenza a pagamento per poter essere utilizzato. Il sistema si pone l’obiettivo di risultare chiaro e facile da utilizzare, in grado di abbattere i costi temporali inerenti al training del personale..

# Autori

### ***Il Quixel Team***

- **Emmanuel De Luca** - [avatarkorraa](https://github.com/avatarkorraa)
  
- **Giovanni Carbone** - [Giovannicar201](https://github.com/Giovannicar201)
  
- **Angelo Antonio Prisco** - [AngeloAntonioPrisco](https://github.com/AngeloAntonioPrisco)

# Documentazione

### Benvenuti nella sezione dedicata alla documentazione ufficiale di Quixel Texel.
#### Qui troverete la JavaDoc di Quixel Texel.

*Link alla JavaDoc:* <a href="https://Giovannicar201.github.io/Quixel_Texel/">***JavaDoc di Quixel Texel***</a>.

# Installazione

Per poter eseguire il progetto è necessario avere installato Intellij 2023.1.2 e MySQL Workbench 8.0 CE.
A questo punto è possibile scaricare il file .zip del progetto dalla repository GitHub ed aprirlo tramite Intellij.
Prima di poter eseguire l'applicazione è necessario creare una connessione ad un database tramite MySQL Workbench.
Una volta aperto MySQL Workbench deve quindi essere creata una nuova connessione.

*Esempio di connessione tramite MYSQL Workbench:*

```bash
hostname = localhost
port = 3306
username = UsernameEsempio
password = PasswordEsempio
```

Fatto ciò, è necessario tornare su Intellij per poter prima stabilire la connessione e poi creare un nuovo schema.

Per stabilire la connessione è possibile seguire la serie di passi riportati alla seguente pagina: https://www.jetbrains.com/help/idea/connecting-to-a-database.html.

Prima va configurato il proprio `application.properties`, in modo tale da configurare appropriatamente la propria connessione al database.

*Esempio di application.properties configurato:*

```bash
 spring.datasource.url = jdbc:mysql://localhost:3306/tilemap
 spring.datasource.username = UsernameEsempio
 spring.datasource.password = PasswordEsempio
 spring.datasource.driver-class-name= com.mysql.cj.jdbc.Driver
 spring.jpa.hibernate.ddl-auto= update
 spring.jpa.show-sql= true
```

Una volta stabilita la connessione è necessario creare un nuovo schema.

*Esempio schema:*

```bash
nome = tilemap
```

Per fare questo è possibile seguire la serie di passi riportati alla seguente pagina: https://www.jetbrains.com/help/idea/schemas.html

A questo punto basterà selezionare lo schema creato ed avviare il progetto.

# Built With

- [Java](https://www.oracle.com/java/) - Il linguaggio di programmazione utilizzato per lo sviluppo back-end.
- [Spring Framework](https://spring.io/) - Il framework Java utilizzato per lo sviluppo (Spring MVC/Web).
- [Maven](https://maven.apache.org/) - Gestione delle dipendenze.
- [HTML5](https://developer.mozilla.org/en-US/docs/Web/Guide/HTML/HTML5) - Il linguaggio di programmazione utilizzato per lo sviluppo front-end.
- [jQuery](https://jquery.com/) - Libreria JavaScript per semplificare la manipolazione del DOM e l'interazione con il front-end.
- [Thymeleaf](https://www.thymeleaf.org/) - Template engine Java per la generazione di pagine statiche in pagine dinamiche.
