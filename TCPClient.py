import socket
import sys

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
	amount_received = 0
	amount_expected = len(message.encode())
	result = ""
	while amount_received < amount_expected:
		data = sock.recv(16)
		amount_received += len(data)
		result += data.decode('utf-8')

	print ('received %s' % result)

finally:
	print('closing socket')
	sock.close()
