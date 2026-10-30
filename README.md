# Lewis & Harris Fotospots (Android)

App für Natur- und Wildlife-Fotografie auf Lewis & Harris (Äußere Hebriden, Schottland).
Interaktive OpenStreetMap-Karte mit farbcodierten Fotospots je Motiv, saisonalen
Wahrscheinlichkeiten für dein Reisedatum, Gezeiten-Hinweisen, Tipps, Park- und
Anfahrtsinformationen sowie Sicherheitshinweisen.

## Funktionen
- **10 Fotomotive** (farbcodiert wie in der Designvorlage): Otter, Robben & Seehunde, Limikolen, Hirsche, Wale & Delfine, Adler, sonstige Tiere, Landschaft, Strände, Sonstiges
- **Wählbares Reisedatum** mit saisonalen Hinweisen (z. B. Kegelrobben-Welpenzeit Okt–Dez, Trauerenten in der Broad Bay im Winter, Brunft-Ende, Papageitaucher nur Apr–Jul)
- **OSM-Karte (osmdroid)** mit Markern pro Spot, farblich nach Motiv; Filter-Chips für Motive; Kachel-Cache für Offline-Nutzung
- **Spot-Detailseiten**: Häufigkeits-/Wahrscheinlichkeitsbalken für die Saison des Reisedatums, beste Zeit, Gezeitenhinweis, Location-Tipps, Sicherheit & Ethik, Anfahrt (ab Stornoway/Tarbert), Parkmöglichkeit, Route in Google Maps
- **Merkliste (Favoriten)** – lokal gespeichert (DataStore)
- **Dunkelmodus** für frühe/späte Fotostunden
- **Datenquellen**-Übersicht (HWDT, RSPB, NatureScot, Visit Outer Hebrides, VisitScotland, Scottish Wildlife Trust, WalkHighlands, Grinneabhat, Western Isles Wildlife, OpenStreetMap)

## Projekt öffnen
1. Repo klonen: `git clone https://github.com/hloger82-create/lewis-foto-app.git`
   (oder lokal nach `C:\Users\braun\LewisApp` kopieren)
2. In Android Studio: **File > Open** → Projektordner wählen
3. Gradle syncen lassen (Wrapper lädt Gradle 8.9), dann auf Emulator/Gerät starten

## Technik
- Kotlin + Jetpack Compose (Material 3), Navigation-Compose, DataStore (Preferences)
- osmdroid 6.1.20 (OpenStreetMap-Tiles, OSM-Attributierung im Karten-Copyright)
- minSdk 26, targetSdk 35, AGP 8.7.3, Kotlin 2.0.21, Compose BOM 2024.10.01
- Spot-Daten lokal gebündelt (`data/SpotRepository.kt`) – keine Laufzeit-Abfrage externer Seiten, daher offline nutzbar

## Struktur
```
lewis-foto-app/
├── app/src/main/java/com/lewisfoto/app/
│   ├── MainActivity.kt               (Bottom-Nav + NavHost)
│   ├── data/                         (Motif, Season, FotoSpot, SeasonNotes, SpotRepository)
│   ├── viewmodel/AppViewModel.kt     (Trip-Date, DarkMode, Favoriten via DataStore)
│   ├── util/                         (AppPreferences, GeoUtils)
│   └── ui/                           (theme, home, map, detail, favorites, sources, settings, components)
└── app/src/main/res/                 (Motiv-Icons, Launcher-Icon, Farben, Strings)
```

Hinweis: Saisonale Bewertungen sind kuratierte Erfahrungswerte aus den genannten Quellen und keine Garantie für Sichtungen.
