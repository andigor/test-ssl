server : main.o
	g++ $< -o $@ -L /usr/lib/x86_64-linux-gnu/ -lssl

main.o : main.cpp main.h
	g++ -c $< -o $@

.PHONY: clean


clean: 
	rm *.o server	
