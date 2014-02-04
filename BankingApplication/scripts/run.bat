cd ..
cp -r bin/code .

start java code.server.BankServer SER01 127.0.0.1 1221
start java code.server.BankServer SER02 127.0.0.1 1222
start java code.server.BankServer SER03 127.0.0.1 1223
sleep 1
start java code.client.BankClient CLI01 127.0.0.1 1224
cd scripts