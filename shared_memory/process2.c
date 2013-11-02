#include <stdio.h>
#include <sys/shm.h>
#include <sys/stat.h>
#include <stdlib.h>

#define M 3
#define N 10

int main ()
{
	int i;
    int segment_id;
    char **shared_memory = (char **)malloc(M*sizeof(char *));
	for (i = 0; i < M; ++i)
		shared_memory[i] = (char *)malloc(N*sizeof(char));

    int segment_size;
    key_t shm_key;
    const int shared_segment_size = 0x400;
    /* Allocate a shared memory segment. */
    segment_id = shmget (shm_key, shared_segment_size,
                         S_IRUSR | S_IWUSR);
    /* Attach the shared memory segment. */
    shared_memory = (char**) shmat (segment_id, 0, 0);
    printf ("shared memory22 attached at address %p\n", shared_memory);
    printf ("name=%s\n", shared_memory[0]);
    printf ("%s\n", shared_memory[1]);
    printf ("%s\n", shared_memory[2]);
    /* Detach the shared memory segment. */
    shmdt (shared_memory);
    return 0;
}
