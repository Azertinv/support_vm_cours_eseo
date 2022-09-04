#!/usr/bin/python3

import sys

if len(sys.argv) != 2:
    print("Key not provided")
    quit()

plaintext = sys.stdin.buffer.read()

def rep(s, m):
    a, b = divmod(m, len(s))
    return s * a + s[:b]

key = rep(bytes(sys.argv[1], "utf-8"), len(plaintext))

cipher = bytearray()
for k, p in zip(plaintext, key):
    cipher.append(k ^ p)

sys.stdout.buffer.write(cipher)
