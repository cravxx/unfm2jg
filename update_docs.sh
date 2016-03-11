#!/bin/bash

test=$(git branch | grep "*")
branch=${test:2}


if ["$branch" = "master"] ;then
    echo $branch
    echo "Publishing javadoc...\n"

    cp -R doc $HOME/javadoc-latest

    cd $HOME
    git config --global user.email "travis@travis-ci.org"
    git config --global user.name "travis-ci"
    git clone --quiet --branch=gh-pages https://${GH_TOKEN}@github.com/HulaSamsquanch/unfm2jg gh-pages > /dev/null

    cd gh-pages
    git rm -rf .
    cp -Rf $HOME/javadoc-latest .
    git add -f .
    git commit -m "Latest javadoc on successful travis build $TRAVIS_BUILD_NUMBER auto-pushed to gh-pages"
    git push -fq origin gh-pages > /dev/null

    echo "Published Javadoc to gh-pages.\n"
else
    echo $branch
    echo "Not on master, not updating.\n"
fi



