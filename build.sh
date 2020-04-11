#!/usr/bin/env bash

set -euo pipefail

cd "$(dirname "$0")"

javac \
    -d ./out \
    src/io/github/mamachanko/*.java
