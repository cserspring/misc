#include <sys/types.h>    
#include <dirent.h>    
#include <stdio.h>    
#include <errno.h>    
#include <sys/stat.h>

int main(int argc,char *argv[])    
{    
    DIR *dp;    
    struct dirent *dirp;    
    int n=0;    
    if (argc!=2) {    
        printf("a single argument is required\n");    
        return 0;    
    }    
    if((dp=opendir(argv[1]))==NULL)    
        printf("can't open %s",argv[1]);    
    while ((dirp=readdir(dp))!=NULL ) {   
        printf("%s\n",dirp->d_name);
        closedir(dirp);    
    }    
    printf("\n");    
    closedir(dp);   
    int a;
    if((a = mkdir("abc/", S_IWUSR | S_IRUSR)) < 0 && errno == EEXIST)
        perror("fd\n"); 
    return 0; 
}
