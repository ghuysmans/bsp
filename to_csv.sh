#!/bin/bash
while read -a line; do
	for x in ${line[@]}; do echo -n $x,; done
	echo
done
