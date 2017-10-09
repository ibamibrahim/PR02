import socket
import sys
import struct

# Create TCP/IP socket

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# connect socket to the port where server is listening
server_address = ('localhost', 6789)
print("connecting to address %s port %s" % (server_address))
sock.connect(server_address)

# method below is after the connection established

try:
	message = input(">> ")
	message = message + "\n"
	sock.send(message.encode())

	# Look resposne
	text = ''
	chunk = b''
	while True:
   		chunk += sock.recv(16)
   		if not chunk:
   			break
   		else:
   			text += chunk.decode()

	print (text)
	
finally:
	print('closing socket')
	#sock.close()
