import socket
import time

def recv_timeout(the_socket,timeout=2):
    #make socket non blocking
    the_socket.setblocking(0)
     
    #total data partwise in an array
    total_data=[];
    data='';
     
    #beginning time
    begin=time.time()
    while 1:
        #if you got some data, then break after timeout
        if total_data and time.time()-begin > timeout:
            break
         
        #if you got no data at all, wait a little longer, twice the timeout
        elif time.time()-begin > timeout*2:
            break
         
        #recv something
        try:
            data = the_socket.recv(8192)
            if data:
                total_data.append(data.decode())
                #change the beginning time for measurement
                begin=time.time()
            else:
                #sleep for sometime to indicate a gap
                time.sleep(0.1)
        except:
            pass
     
    #join all parts to make final string
    return ''.join(total_data)

host = input("Enter host to connect: ")    
port = input("Enter port to connect: ")                   # The same port as used by the server

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
while True:
	try:
		print("Connecting to IP %s Port %d..." % (host, int(port)))
		s.connect((host, int(port)))
		print("Connected! \n")
		message = input(">> ")

		message += "\n"
		s.send(message.encode())

		# function recv_timeout taken from 
		# http://www.binarytides.com/receive-full-data-with-the-recv-socket-function-in-python/


		print (recv_timeout(s))

		if(message.partition(' ')[0] == "LOGIN"):
			s.close()
	except ConnectionRefusedError:
		print("The server is off; Turn on the server first!")
