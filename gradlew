#!/bin/sh
APP_HOME=$(cd "$(dirname "$0")" && pwd)
CLASSPATH="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"
if [ ! -f "$CLASSPATH" ]; then
  echo "gradle-wrapper.jar fehlt - bitte einmal 'gradle wrapper' lokal ausfuehren oder Android Studio oeffnen (erzeugt den Wrapper)." >&2
  exit 1
fi
exec java -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
