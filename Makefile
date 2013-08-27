alu : alu.o seq_ops.o
	gcc -o alu alu.o seq_ops.o

alu.o : alu.c seq_ops.h
	gcc -c alu.c

seq_ops.o : seq_ops.S
	gcc -c seq_ops.S

clean :
	rm alu alu.o seq_ops.o
