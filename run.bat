@echo off
REM run.bat - Build and run Campus Event Tracker (Windows)

SETLOCAL ENABLEDELAYEDEXPANSION

REM Change to project root
CD /D "%~dp0"

echo Project root: %CD%

REM Rename application.properties if needed
IF EXIST "src\main\resources\application.properties.txt" IF NOT EXIST "src\main\resources\application.properties" (
  echo Renaming application.properties.txt -> application.properties
  ren "src\main\resources\application.properties.txt" "application.properties"
)

REM Build the project
echo Running: mvn clean package -DskipTests
mvn clean package -DskipTests
IF %ERRORLEVEL% NEQ 0 (
  echo Maven build failed.
  pause
  EXIT /B %ERRORLEVEL%
)

REM Find JAR
for %%F in (target\*.jar) do (
  set JAR=%%F
  goto runjar
)

echo No JAR found in target folder.
pause
EXIT /B 1

:runjar
echo Running jar: %JAR%
java -jar "%JAR%"

ENDLOCAL
