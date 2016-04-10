#!/bin/sh
ant build
rlwrap -c java -cp build core.TestCompare $1
