#include <stdio.h>

void bar(void *a)
{
    int val = *(int *)a;
    if (val > 5)
        printf("greater than 5\n");
    else if (val < 5)
        printf("less than 5\n");
    else
        printf("equal to 5\n");
}

void foo(int *a, void (*f)(void *))
{
    f(a);
}

int main()
{
    int a = 6;
    void (*func_ptr)(void *) = NULL;
    func_ptr = bar;
    foo(&a, func_ptr);
    return 0;
}
