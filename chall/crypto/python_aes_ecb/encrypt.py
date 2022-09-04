#!/usr/bin/python3

from Crypto.Cipher import AES
import sys

def main():
    if len(sys.argv) != 4:
        print(sys.argv[0] + " <input> <output> <encryption_key>")
        return

    aes = AES.new(sys.argv[3].rjust(16, '\x00'), AES.MODE_ECB, "My IV")

    with open(sys.argv[1], "rb") as f:
        content = f.read()
        with open(sys.argv[2], "wb") as f:
            f.write(aes.encrypt(content))

if __name__ == "__main__":
    main()
