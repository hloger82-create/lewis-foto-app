#!/bin/sh
# Sync-Skript: lokale Aenderungen committen + pushen, danach neueste Remote-Aenderungen holen.
# Aufruf im Projekt-Root (wo .git liegt):  sh sync.sh "optional: Commit-Nachricht"
set -e
cd "$(dirname "$0")"

MSG="${1:-chore: sync from Android Studio}"

git add -A
if git diff --cached --quiet; then
    echo "Keine lokalen Aenderungen."
else
    git commit -m "$MSG"
    echo "Committet: $MSG"
fi

git pull --rebase origin main
git push origin main
echo "Sync fertig: lokal und remote auf dem gleichen Stand."
