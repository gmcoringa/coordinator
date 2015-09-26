#!/bin/bash
set -e

function info {
  printf '\e[1;32m%-6s\e[m\n' " - $1"
}

info "Starting Coodinator Release"

info "Checkout Master"
git checkout master
git pull

info "Performing Release"
gradle release \
    -Prelease.useAutomaticVersion=true \
    -PRELEASE \
    -x test

info "Release Complete!"
