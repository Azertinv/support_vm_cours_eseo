#!/usr/bin/python

import sys

if __name__ == "__main__":
    amount = int(sys.argv[1])
    # Check the amount plus 1 because we want to print some stuff after
    if amount + 1 > 11:
        print("You're trying to print too much")
    else:
        i = 0
        while i < amount:
            print(i)
            i += 1
        print("And we're done")
