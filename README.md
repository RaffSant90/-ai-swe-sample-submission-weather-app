# 🌤 Weather App (Java Console)

Applicazione Java da console che consente di cercare il meteo attuale di una o più città utilizzando le API di Open-Meteo.

---

## 🚀 Funzionalità

* 🔍 Ricerca città tramite nome
* 📍 Selezione tra più risultati (se ambigui)
* 🌡 Visualizzazione temperatura attuale
* 🌍 Supporto a città + nazione (es: `Napoli, Italia`)
* ➕ Supporto multi-città (separate da `-`)
* ⚡ Risultato immediato se la città è univoca

---

## 🧠 Esempi di utilizzo

### Singola città

```
Roma
```

### Città + nazione

```
Napoli, Italia
```

### Più città

```
Roma - Milano - Parigi
```

---

## 🏗 Struttura del progetto

```
weather-app/
│
├── pom.xml
└── src/
    ├── main/java/com/raffaele/weatherapp/
    │   ├── client/        → chiamate API
    │   ├── model/         → classi dati
    │   ├── service/       → logica applicativa
    │   └── ui/            → interfaccia console
    │
    └── test/java/com/raffaele/weatherapp/
        └── service/       → test JUnit
```

---

## ⚙️ Tecnologie utilizzate

* Java 17+
* Maven
* HttpClient (Java standard)
* JUnit 5
* Open-Meteo API

---

## ▶️ Esecuzione del progetto

### Compilazione

```
mvn compile
```

### Avvio applicazione

```
mvn exec:java
```

*(oppure eseguire la classe `Main` dall’IDE)*

---

## 🧪 Esecuzione test

```
mvn test
```

### Test inclusi

* ✔ Ricerca città (`searchLocations`)
* ✔ Recupero meteo (`getWeatherFromLocation`)

---

## ⚠️ Note

* I test utilizzano API reali → è necessaria connessione Internet
* In caso di più risultati simili, l’utente deve scegliere manualmente
* Possibili duplicati nella ricerca (limite API)

---

## 📌 Possibili miglioramenti futuri

* Parsing JSON con libreria (es. Jackson)
* Gestione errori più robusta
* Cache delle richieste
* Interfaccia grafica (JavaFX / Web)
* Test con mock (Mockito)

---

## 👨‍💻 Autore

Progetto sviluppato a scopo didattico.

---

🔐 Sicurezza ed etica

Sicurezza

* Validazione input utente per evitare errori o comportamenti imprevisti
* Gestione delle eccezioni nelle chiamate HTTP (`try-catch`)
* Nessuna gestione di dati sensibili o credenziali
* Utilizzo di API pubbliche e documentate

Licenze

* API Open-Meteo utilizzata secondo termini open/free
* Librerie (JUnit, Maven) open source
* Progetto destinato a uso didattico

Uso responsabile dell’intelligenza artificiale

* Il codice è stato sviluppato con supporto AI
* Revisione manuale di tutte le parti generate
* L’AI è usata come supporto, non come sostituto della comprensione
