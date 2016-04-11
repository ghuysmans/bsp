#!/bin/sh
echo test $2|rlwrap -c java -cp build ui.TestCompare $1|./to_csv.sh
