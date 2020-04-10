#!/usr/bin/env bash

set -euo pipefail

cd "$(dirname "$0")"

javac \
    -d ./out \
    src/io/github/mamachanko/Lox.java

java \
    -Dfile.encoding=UTF-8 \
    -classpath ./out \
    io.github.mamachanko.Lox

