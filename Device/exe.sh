#!/bin/bash
sudo python3 /home/pi/Desktop/test4.py

while [ 1 ]
do
	pid=`ps -ef | grep "test4" | grep -v 'grep' | awk ' {print $2}'`
	if [ -z $pid ]
	then
		sudo python3 /home/pi/Desktop/test4.py &
	fi
	sleep 2
done


