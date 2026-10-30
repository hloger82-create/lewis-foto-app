# Workflow: Android Studio <-> GitHub (lewis-foto-app)

## Wichtig zuerst: ZIP-Entpackung ersetzen durch echten Git-Klon

`C:\Users\braun\LewisApp\lewis-foto-app-main` ist nur eine **entpackte ZIP** – dort gibt es kein `.git`, daher sind Aenderungen dort **nicht** mit GitHub verknuepft.

### Schritt 1: Alten Ordner sichern (falls du dort etwas geaendert hast)
- `C:\Users\braun\LewisApp\lewis-foto-app-main` umbenennen, z. B. in `lewis-foto-app-main-alt`

### Schritt 2: Repo als echten Klon holen
```bat
cd C:\Users\braun\LewisApp
git clone https://github.com/hloger82-create/lewis-foto-app.git
```
(oder in Android Studio: **File > New > Project from Version Control** → `https://github.com/hloger82-create/lewis-foto-app.git`)

Falls GitHub nach Zugangsdaten fragt: im Browser auf github.com einloggen und unter
**Settings > Developer settings > Personal access tokens > Tokens (classic)** einen Token mit Scope `repo` erzeugen und als Passwort verwenden –
oder den Windows Credential Manager nutzen (einmalig anmelden).

### Schritt 3: In Android Studio oeffnen
- **File > Open** → `C:\Users\braun\LewisApp\lewis-foto-app` waehlen
- Gradle-Sync abwarten, dann Run auf dem Pixel 6

## Taeglicher Workflow

1. In Android Studio arbeiten (Code, Ressourcen, ggf. KI-Assistent)
2. Sync ausfuehren (Terminal in Android Studio, unten, im Projekt-Root):
   ```bat
   sync.bat "kurze Beschreibung der Aenderung"
   ```
   Das Skript macht: `git add -A` → `git commit` → `git pull --rebase` → `git push`
3. Aenderungen, die ich (Vibe) im Repo mache, holst du mit:
   ```bat
   git pull
   ```
   (oder ebenfalls `sync.bat` ohne eigene Aenderungen – dann nur pull+push)

## Konflikte
Falls `git pull --rebase` stoppt mit Konflikt:
- Datei in Android Studio oeffnen, Konflikt-Marker (`<<<<<<<`) loesen
- `git add <datei>` dann `git rebase --continue` und erneut `sync.bat`

## Schnell-Referenz
| Aktion | Befehl |
|---|---|
| Aenderungen pushen | `sync.bat "msg"` |
| Meine Aenderungen holen | `git pull` |
| Status ansehen | `git status` |
| Letzte Commits | `git log --oneline -5` |
