client: main.o
	g++ $< -o $@ -L /usr/lib/x86_64-linux-gnu/ -lssl -lcrypto

main.o : client.cpp
	g++ -c $< -o $@ 

.PHONY: clean


clean: 
	rm *.o client
