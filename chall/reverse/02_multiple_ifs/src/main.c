#include <stdio.h>
#include <string.h>

int main(int argc, char ** argv)
{
	if (argc >= 2
			&& argv[1][8] == 'l'
			&& argv[1][10] == 'g'
			&& argv[1][0] == 'T'
			&& argv[1][1] == 'h'
			&& argv[1][6] == 'A'
			&& argv[1][5] == 's'
			&& argv[1][2] == 'i'
			&& argv[1][4] == 'I'
			&& argv[1][9] == 'a'
			&& argv[1][3] == 's'
			&& argv[1][7] == 'F'
			)
	{
		printf("good flag\n");
		return 0;
	}
	else
	{
		printf("nope\n");
		return 1;
	}
}
