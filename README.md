# TIN

TIN è un Web Application di vendita online che offre agli utenti la possibilità di acquistare e vendere una vasta gamma di prodotti. La piattaforma fornisce un ambiente sicuro e conveniente per gli utenti che desiderano acquistare articoli di seconda mano o nuovi a prezzi competitivi o che desiderano vendere oggetti che non utilizzano più. Inoltre, gli utenti possono comunicare tra loro tramite una chat relativa ad un prodotto d’acquisto, e possono aggiungere o ricevere recensioni alla fine di una compravendita. Infine, l’esperienza è personalizzata in base alle preferenze degli utenti.

## Architettura

L'architettura di TIN è una **3-tier** che suddivide il sistema in tre livelli distinti, ognuno con un ruolo specifico.

Il primo livello è chiamato **Presentation Tier**. È il livello con cui gli utenti interagiscono direttamente. Qui vengono gestite le interfacce utente e la presentazione dei dati. È responsabile di mostrare i risultati delle operazioni agli utenti e di ricevere le loro richieste. È dove si troveranno elementi come l'HTML, il CSS e il JavaScript per la costruzione dell'interfaccia utente.

Il secondo livello è l'**Application Tier**. Qui risiede la logica di business dell'applicazione. Questo livello elabora le richieste provenienti dal livello di presentazione e coordina le operazioni con il livello di dati. È responsabile di gestire la logica dell'applicazione, come l'autenticazione degli utenti, il recupero dei dati e le operazioni di business. In questo livello, viene utilizzato il linguaggio di programmazione lato server Java, insieme a framework e librerie per lo sviluppo di applicazioni web.

Il terzo livello è il **Data Tier**. Questo livello è dedicato alla memorizzazione e alla gestione dei dati dell'applicazione. Include il database relazionale. Questo livello gestisce l'accesso ai dati, inclusa la creazione, la modifica, la cancellazione e la query. 

la scelta di questa architettura favorisce la modularità, la manutenibilità e la scalabilità dell'applicazione web, poiché ogni livello è separato dagli altri e comunica attraverso interfacce ben definite. Questa separazione consente una gestione più efficiente e una maggiore flessibilità nello sviluppo e nell'evoluzione dell'applicazione nel tempo.
