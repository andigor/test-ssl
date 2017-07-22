server : main.o
	g++ $< -o $@

main.o : main.cpp main.h
	g++ -c $< -o $@

.PHONY: clean


clean: 
	rm *.o server	
