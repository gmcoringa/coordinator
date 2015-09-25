#!/bin/bash
set -e

function info {
  printf '\e[1;32m%-6s\e[m\n' " - $1"
}

info "Setup credentials"
git config credential.helper "store --file=.git/credentials"
echo "https://${GH_TOKEN}:@github.com/gmcoringa/coordinator" > .git/credentials

info "Checkout master"
git checkout master
git pull

info "Starting Coodinator Release"
gradle release \
    -Prelease.useAutomaticVersion=true \
    -PRELEASE \
    -Psigning.keyId=$SIGNING_KEY \
    -Psigning.password=$SIGNING_PASS \
    -Psigning.secretKeyRingFile=ext/secring.gpg \
    -PossrhUsername=$OSSRH_USER \
    -PossrhPassword=$OSSRH_PASS \
    -x test

info "Remove credentials"
rm -fv .git/credentials

info "Done!"
