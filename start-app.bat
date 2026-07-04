@echo off
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.11.10-hotspot
set PATH=%JAVA_HOME%\bin;C:\Program Files\apache-maven-3.9.11\bin;%PATH%
echo Starting Campus Event Tracker...
echo Java version:
java -version
echo.
mvn spring-boot:run
