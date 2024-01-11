# Quixel Texel

Quixel Texel intende migliorare il flusso di lavoro dello sviluppo indie attraverso un servizio web. Questa scelta è fondata sulla volontà di abbattere sia i costi economici che temporali dello sviluppo videoludico indie. Si intende quindi offrire un servizio che non richiede una licenza a pagamento per poter essere utilizzato. Il sistema si pone l’obiettivo di risultare chiaro e facile da utilizzare, in grado di abbattere i costi temporali inerenti al training del personale..

# Modulo di IA
Quixel Texel offre la possibilità di sfruttare un modulo di IA in grado di generare porzioni di tile map in maniera automatica. Tale modulo è contenuto all'interno del package FIA.
All'interno di tale package è inoltre possibile trovare una serie di documenti utili alla comprensione e all'utilizzo del modulo di IA. 
Tali documenti si trovano sotto il package Resources.

# Installazione

Per poter eseguire il progetto è necessario avere installato Intellij 2023.1.2 e MySQL Workbench 8.0 CE.
A questo punto è possibile scaricare il file .zip del progetto dalla repository GitHub ed aprirlo tramite Intellij.
Prima di poter eseguire l'applicazione è necessario creare una connessione ad un database tramite MySQL Workbench.
Una volta aperto MySQL Workbench deve quindi essere creata una nuova connessione avente come:

```bash
hostname = localhost
port = 3306
username = root
password = adminroot
```

Fatto ciò, è necessario tornare su Intellij per poter prima stabilire la connessione e poi creare un nuovo schema.
Per stabilire la connessione è possibile seguire la serie di passi riportati alla seguente pagina: https://www.jetbrains.com/help/idea/connecting-to-a-database.html

Una volta stabilita la connessione è necessario creare un nuovo schema avente come:

```bash
nome = tilemap
```

Per fare questo è possibile seguire la serie di passi riportati alla seguente pagina: https://www.jetbrains.com/help/idea/schemas.html

A questo punto basterà selezionare lo schema creato ed avviare il progetto.

