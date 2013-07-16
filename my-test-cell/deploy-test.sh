IP=10.28.170.56
RP=/export/test/craft.cell.test/
LP=/workspace/project/i-craft/craft-cell/craft-cell-test

scp -r $LP/target/craft-cell-test-1.0.0-SNAPSHOT-all/* root@$IP:$RP
scp -r $LP/target/craft-cell-test-1.0.0-SNAPSHOT/WEB-INF/classes root@$IP:$RP