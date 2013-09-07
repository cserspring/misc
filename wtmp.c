#include<stdio.h>
#include<fcntl.h>
#include<utmp.h>
#include <stdlib.h>

int main()
{
    int fd;
    struct utmp cr;
    int reclen = sizeof(struct utmp);
    
    fd = open(WTMP_FILE, O_RDONLY);
    if (fd == -1){
        perror("oops");
        exit(1);
    }
    while (read(fd, &cr, reclen) == reclen)
        printf("-- %s\n", cr.ut_user);
    
    close (fd);
    return 0;
}
