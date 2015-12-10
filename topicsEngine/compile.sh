clear
./s4 s4r -a=topology.Topology -b=`pwd`/build.gradle topicsEngine
./s4 deploy -s4r=`pwd`/build/libs/topicsEngine.s4r -c=cluster1 -appName=topicsEngine
