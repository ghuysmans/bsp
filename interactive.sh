#!/bin/sh
ant build
rlwrap -c java -cp build ui.TestCompare $1
