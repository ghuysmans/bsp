#!/bin/sh
echo test $2|rlwrap -c java -cp build core.TestCompare $1|./to_csv.sh
