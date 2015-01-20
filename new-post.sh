#!/bin/bash

DATE=$(date "+%Y-%m-%d")
TIME=$(date "+%H:%M:%S")
FILENAME="$DATE-$1.md"
echo "---" >> _posts/"$FILENAME"
echo "layout: post" >> _posts/"$FILENAME"
echo "title: $1" >> _posts/"$FILENAME"
echo "date: $DATE $TIME" >> _posts/"$FILENAME"
echo "---" >> _posts/"$FILENAME"
vim _posts/"$DATE-$1.md"
