#!/bin/bash
#Colors are completely ignored.
#Don't pipe this directly to xclip since:
#- the error message is long enough to crash your desktop manager
#- the syntax error can be fixed by removing the last comma and appending }]
read #drop
echo -n 'Execute[{'
while read -a line; do
	echo -n "\"Segment[(${line[0]},${line[1]}),(${line[2]},${line[3]})]\","
done
