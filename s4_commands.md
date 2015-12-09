#terminal 1 - crear nueva aplicacion
./s4 newApp trendingApp -parentDir=../
#iniciar el servidor zookeper
./s4 zkServer -clusters=c=cluster1:flp=12000:nbTasks=2 -clean

#terminal 2 en carpeta app
./s4 node -c=cluster1
#terminal 3
./s4 s4r -a=topology.Topology -b=`pwd`/build.gradle trendingApp
./s4 deploy -s4r=`pwd`/build/libs/trendingApp.s4r -c=cluster1 -appName=trendingApp
./s4 node -c=cluster1

#terminal 4
./s4 newCluster -c=cluster2 -nbTasks=1 -flp=13000
./s4 deploy -appClass=adapter.TwitterAllAdapter -p=s4.adapter.output.stream=inputStream -c=cluster2 -appName=adapter
./s4 adapter -c=cluster2

#terminal 5
echo "Nombre" | nc localhost 15000

#opcional para configurar el src para importarlo a eclipse
./gradlew eclipse

#location desde los heroes a los dominicos
http://www.gps-coordinates.net/gps-coordinates-converter
https://stream.twitter.com/1.1/statuses/filter.json?locations=-33.44618179999999,-70.66042089999996,-33.41312980000001,-70.54787110000001
