#!/usr/bin/env bash

set -euo pipefail

cd "$(dirname "$0")"

./build.sh

java \
    -Dfile.encoding=UTF-8 \
    -classpath ./out \
    io.github.mamachanko.Lox \
    "$@"
