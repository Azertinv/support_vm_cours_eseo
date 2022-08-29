#!/bin/bash

make -C chall/exploit/c_ret_into_system "$@"
make -C chall/exploit/c_ret_into_shellcode "$@"
make -C chall/exploit/java_int_overflow "$@"

make -C chall/reverse/c_basic_if "$@"
make -C chall/reverse/c_basic_strcmp "$@"
make -C chall/reverse/c_multiple_ifs "$@"

make -C chall/crypto/java_bad_rng "$@"

