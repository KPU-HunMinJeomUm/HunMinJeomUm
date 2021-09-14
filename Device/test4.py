# coding: utf-8
from bluetooth import *
import threading
import time
from pyfirmata import Arduino, util
import serial
import RPi.GPIO as GPIO

shared_variable = b''
count = 0
lock = threading.Lock()

server_sock=BluetoothSocket(RFCOMM)
client_socket=BluetoothSocket(RFCOMM)
server_sock.bind(("", PORT_ANY))
server_sock.listen(1)
client_socket.connect(("00:20:12:08:1D:58", 1))
print("bluetooth Connected to Arduino!")

port = server_sock.getsockname()[1]

uuid="00001801-0000-1000-8000-00805f9b34fb"

advertise_service( server_sock, "SampleServer", service_id=uuid, service_classes=[uuid, SERIAL_PORT_CLASS], profiles=[SERIAL_PORT_PROFILE])

def led_on(pin):
	GPIO.setmode(GPIO.BCM)
	GPIO.setup(pin, GPIO.OUT)
	GPIO.output(pin, True)

def led_off(pin):
	GPIO.setmode(GPIO.BCM)
	GPIO.setup(pin, GPIO.OUT)
	GPIO.cleanup(pin)

def job():
	global shared_variable
	global count
	while(True):
		print("thread1 start")
		try: 
			recv_data = client_sock.recv(1024)
			shared_variable = recv_data
			if recv_data:
				while recv_data:
					recv_data = client_sock.recv(1024)
					shared_variable = shared_variable + recv_data
			else:
				break 
			tmp = shared_variable
			count = 0
			if tmp.decode('utf-8') == "/ㅁ" : break
			time.sleep(1)
		except BluetoothError:
			print("BluetoothError in thread1")
			led_off(18)
			led_on(23)
			time.sleep(1)
			break
	print("thread1 exit")

def job2():
	global shared_variable
	global count 
	while(True):
		print("thread2 start")
		time.sleep(1)
		tmp = shared_variable
		tmp_c = count
		if not work.is_alive():
			break
		try:
			check_tmp = tmp.decode('utf-8')[tmp_c*3:(tmp_c+1)*3]
			if(check_tmp == "/ㅁ"): break
			print(count)
			print(check_tmp)
			client_socket.send(check_tmp)
			count = tmp_c + 1
			time.sleep(8.6)
		except BluetoothError:
			print("Bluetooth Error in thread2")
			led_off(18)
			led_on(23)
			time.sleep(1)
			break
	print("thread2 exit")

led_on(18)
led_off(23)
print ("Waiting for connection on FRCOMM channel %d" %port)
client_sock, client_info = server_sock.accept()
print ("Accepted connection from ",client_info)

work = threading.Thread(target=job, args=())
work2 = threading.Thread(target=job2, args=())
work.setDaemon(True)
work2.setDaemon(True)

try:
	led_off(18)
	work.start()
	work2.start()
	work.join()
	work2.join()

except IOError:
	print(IOError)
	led_off(18)
	led_on(23)
	time.sleep(1)
	pass
except KeyboardInterrupt:
	print("You pushed CTRL+C")
	led_off(18)
	led_on(23)
	time.sleep(1)
	pass


print ("disconnected")
led_off(18)
led_off(23)

client_socket.close()
client_sock.close()
server_sock.close()
print ("all done")

