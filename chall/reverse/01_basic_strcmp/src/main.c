#include <stdio.h>
#include <string.h>

int main(int argc, char ** argv)
{
	if (argc >= 2 && strcmp(argv[1], "AyylmaoWassupImGood") == 0)
	{
		printf("%s\n", "ayylmao");
		return 0;
	}
	else
	{
		printf("nope\n");
		return 1;
	}
}
