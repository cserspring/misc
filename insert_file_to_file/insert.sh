#!/bin/bash
awk '1;/hun yin/{system("cat 1.txt")}' 2.txt > output_insert.txt
#sed -i '/da ge da/a `cat 1.txt`' 2.txt
