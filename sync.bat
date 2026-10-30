@echo off
rem Sync-Skript Windows: lokale Aenderungen committen + pushen, danach remote aktualisieren.
rem Aufruf im Projekt-Root (dort wo .git liegt):  sync.bat "optional: Commit-Nachricht"
setlocal
cd /d "%~dp0"

set "MSG=%~1"
if "%MSG%"=="" set "MSG=chore: sync from Android Studio"

git add -A
git diff --cached --quiet
if errorlevel 1 (
    git commit -m "%MSG%"
    echo Committet: %MSG%
) else (
    echo Keine lokalen Aenderungen.
)

git pull --rebase origin main
if errorlevel 1 (
    echo ACHTUNG: Merge-Konflikt beim pull --rebase. Loesen, dann: git rebase --continue
    exit /b 1
)
git push origin main
echo Sync fertig: lokal und remote auf dem gleichen Stand.
endlocal
