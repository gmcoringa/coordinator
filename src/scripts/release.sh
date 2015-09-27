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
gradle release -Prelease.useAutomaticVersion=true -PRELEASE -x test

info "Publising Released version Artifacts"
git fetch --tag
TAG=`git describe --abbrev=0 --tags`
git checkout tags/$TAG
gradle clean build -x test
gradle uploadArchives -PRELEASE -PPUBLISH -x test

info "Release Complete!"
