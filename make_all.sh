#!/bin/bash

make -C chall/exploit/00_ret_into_system "$@"
make -C chall/exploit/01_ret_into_shellcode "$@"

make -C chall/reverse/00_basic_if "$@"
make -C chall/reverse/01_basic_strcmp "$@"
make -C chall/reverse/02_multiple_ifs "$@"

make -C chall/fixme/java_int_overflow "$@"

