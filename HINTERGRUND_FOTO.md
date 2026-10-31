# Eigenes Landschaftsfoto als Home-Hintergrund

Standardmäßig liegt in `app/src/main/res/drawable/bg_home_photo.xml` ein
gezeichneter Sonnenuntergang (funktioniert offline, ohne Foto).

## Eigenes Foto einbauen (z. B. Luskentyre bei Sonnenuntergang)

1. Foto auf dem PC bereitlegen (Querformat, mind. 1080×2400 px, JPG/PNG)
2. Datei kopieren nach:
   `app/src/main/res/drawable/bg_home_photo.jpg`
   (alte `bg_home_photo.xml` dann löschen: `git rm app/src/main/res/drawable/bg_home_photo.xml`)
3. In `HomeScreen.kt` bleibt `painterResource(R.drawable.bg_home_photo)` gleich –
   nur die Dateiendung ändert den R-Namen automatisch.
4. Build → Clean Project → Run

## Tipps fürs Foto
- Querformat, hohe Auflösung (mind. 1080 px breit)
- Motiv mittig/rechts lassen – links sitzen die transluzenten Karten (dunkles Overlay 0x33–0x66 darüber)
- Etwas dunklere Fotos funktionieren besser, weil Text weiß ist
