#!/bin/bash

set -e

function info {
  printf '\e[1;32m%-6s\e[m\n' " - $1"
}

info "Starting Coodinator Release"
gradle release -Prelease.useAutomaticVersion=true -x test -PRELEASE

info "Done!"
