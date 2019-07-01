#Description

L’application permet de traiter les fichiers de transaction et de référence de carrefour.
Les Quantités vendues et le chiffre d’affaires obtenu sont triés et le top 100 est écrit dans un fichier.

#Utilisation du code
Pour tester le code:
Récupérer le code au sein d’un IDE pour le langage Java. Puis l’exporter en tant que .jar exécutable

#Build

    mvn clean compile assembly:single

#Run

    java -Xmx512m -jar target/TestCarrefour-1.0-SNAPSHOT-jar-with-dependencies.jar arg1 arg2 arg3 arg4 arg5
        arg1 : Path to Transaction
        arg2: Path to reference
        arg3: path to TMP directory
        arg4: Path to Result directory( Top 100 product)
        arg5: Path to result directory( Top CA)



