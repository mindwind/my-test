IP=10.28.170.56
RP=/export/test/tomcat6.0.33/webapps/craft-cell-test
#RP=/export/test/tomcat7.0.35/webapps/craft-cell-test
LP=/workspace/project/i-craft/craft-cell/craft-cell-test

scp -r $LP/target/craft-cell-test-1.0.0-SNAPSHOT/* root@$IP:$RP