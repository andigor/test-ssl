server : main.o
	g++ $< -o $@ -L /usr/lib/x86_64-linux-gnu/ -lssl -lcrypto

main.o : serv.cpp
	g++ -c $< -o $@ 

.PHONY: clean


clean: 
	rm *.o server	
