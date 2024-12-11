## Prerequisitos
* node.js
* npm
* ng (npm install -g @angular/cli)
* spring (spring initializr)

## Comandos importantes
```bash
ng build
```
Genera el contenido HTML y CSS que ocupa la página web. Cabe destacar que la carpeta que genera como *static/* debe estar presente tanto al construir con **mvn build** en el directorio *target/classes/*, commo en el directorio *src/main/resources/*
<br>
<br>
Para ejecutar el programa, será necesario ocupar el comando
```bash
mvn spring-boot:run
```
o
```bash
java -jar target/app.jar
```
Al ejecutar el programa, se iniciará un servidor en el socket http://localhost:8080 