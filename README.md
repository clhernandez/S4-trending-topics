# Twitter trending topics

Instalación
---
Para la configuración del motor de procesamiento de stream S4, se deberán instalar ciertos paquetes, por lo que se deberán seguir los siguientes pasos:

Instalar OpenJDK

	$ sudo apt-get install openjdk-7-jdk

Configuración de OpenJDK

	$ sudo update-alternatives --install "/usr/bin/java" "java" "/usr/lib/jvm/java-7-openjdk-amd64/bin/java" 1
	$ sudo update-alternatives --install "/usr/bin/javac" "javac" "/usr/lib/jvm/java-7-openjdk-amd64/bin/javac" 1
	$ sudo update-alternatives --install "/usr/bin/javaws" "javaws" "/usr/lib/jvm/java-7-openjdk-amd64/bin/javaws" 1

Apache S4

	Download: http://archive.apache.org/dist/incubator/s4/s4-0.6.0-incubating/apache-s4-0.6.0-incubating-src.zip

Gradle

	Download: https://services.gradle.org/distributions/gradle-1.4-all.zip

Instalar S4

En la carpeta de S4, se deberá realizar el siguiente comando:

	$ ../gradle-1.4/bin/gradle wrapper

Luego deberá ejecutar el siguiente comando,

	$ ./gradlew install -DskipTests
	$ ./gradlew s4-tools:

Instalar Redis

Ejecutar el script:

	$ chmod +x installRedis.sh
	$ ./installRedis.sh

Instalar NodeJS

	$ chmod +x installNodejs.sh
	$ ./installNodejs.sh


Create App

Para crear una nueva aplicación, primero sera necesario cambiar el nombre de la carpeta topicsEngine por topicsEngine_, luego, se deberá ejecutar el siguiente comando (dentro de la carpeta apache-s4):

	$ ./s4 newApp topicsEngine -parentDir=../

donde 'topicsEngine' es el nombre de la aplicación y ../ corresponde al directorio donde será guardado el proyecto. Finalmente, debera copiar y reemplazar el contenido de las carpetas topicsEngine_/src en topicsEngine/src y mover la carpeta topicsEngine_/conf a topicsEngine/ , luego, puede borrar la carpeta topicsEngine_


Execute App

Ejecutar en la carpeta de Apache S4, el siguiente comando:

	$ ./s4 zkServer -clusters=c=cluster1:flp=12000:nbTasks=1 -clean

Luego, se deberá levantar un nodo, el cual se deberá realizar desde la carpeta de la aplicación con el siguiente comando:

	$ ./s4 node -c=cluster1

Para poder compilar el programa, se deberán ejecutar los dos siguientes comandos:

	$ ./s4 s4r -a=topology.Topology -b=`pwd`/build.gradle topicsEngine
	$ ./s4 deploy -s4r=`pwd`/build/libs/topicsEngine.s4r -c=cluster1 -appName=topicsEngine

Debe tomarse en consideración que 'hello.HelloApp' se deberá cambiar por el nombre de la clase con su correspondiente package que está incorporado. Así mismo 'topicsEngine', que dependerá del nombre de la aplicación que se colocó.

Para poder verificar el estado del sistema, puede utilizarse el siguiente comando

	$ ./s4 status

Finalmente, para poder escuchar los datos entregados a cada uno de los PE, se necesita crear un adapter, el cual estará encargado de entregar los flujos de datos a cada uno de los nodos. Por lo tanto, para poder realizar esto, se deberá ejecutar los siguientes comandos:

	$ ./s4 newCluster -c=cluster2 -nbTasks=1 -flp=13000
	$ ./s4 deploy -appClass=adapter.TwitterAllAdapter -p=s4.adapter.output.stream=Tweetinput -c=cluster2 -appName=adapter
	$ ./s4 adapter -c=cluster2


Si se desea utilizar las métricas por defecto de S4, se debe utilizar como parámetro el siguiente comando:

	$ -p=s4.metrics.config=csv:/path/to/directory:TIME:TIMEUNIT

	
