#include <linux/init.h>
#include <linux/module.h>
#include <linux/sched.h>
MODULE_LICENSE("Dual BSD/GPL");

static int hello_init(void)
{
    int i;
    printk(KERN_ALERT "Hello, world\n");
    printk(KERN_INFO "The process is \"%s\" (pid %i)\n",
           current->comm, current->pid);
    for (i = 0; i < 15; ++i) 
        printk(KERN_INFO "%d\n", i+1);
    return 0;
}
static void hello_exit(void)
{
    printk(KERN_ALERT "Goodbye, cruel world\n");
}

module_init(hello_init);
module_exit(hello_exit);
