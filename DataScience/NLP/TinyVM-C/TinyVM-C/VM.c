#include <stdio.h>
//#include <stdbool.h>

typedef enum { false, true } bool;


void VM(int pmt_code[], int pmt_main, int pmt_dataSize)
{

	int globals[1];
	int code = {
		PRINT
	};
	int stack[100];

	int ip = pmt_main;
	int sp = -1;
	int fp;

	bool trace = false;


}

int main()
{



	printf_s("Teste");

	return 0;
}