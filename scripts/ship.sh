#!/usr/bin/env bash

set -euo pipefail

cd "$(dirname "$0")"

cd ..

main() {
  assert_clean
  build
  ship
}

assert_clean() {
  if [ "$(git status -s)" ]; then
    echo "❗ repository not clean️"
    echo "🙅‍♀️ aborting ship"
    exit 1
  fi
}

build() {
  ./scripts/build.sh

  tempDir=$(mktemp -d)
  generate_ast "$tempDir"
  cat "$tempDir/Expr.java"
  
  jlox hello-world.lox
}

ship() {
  git push
  echo "🚢 successfully shipped"
}

main
