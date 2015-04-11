import threading
import socket
import ssl

HOST = "dedominic.pw"
PORT = 6556

SERVER_SOCKET = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
SERVER_SOCKET.bind(HOST, PORT)
SERVER_SOCKET.listen(5000);

CONNECTED_SOCKETS = []

listenThread = listen
listenThread.run()

class listen(threading.Thread):
    def __init__():
        super(listen, self).__init__()

    /**
     * listen socket waiting for connection
     */
    def run(self):
        asocket, addr = SERVER_SOCKET.accept(); 
        CONNECTED_SOCKETS.append(connected(asocket, addr)
        
class connected(threading.Thread):
    def __init__(csocket, addr):
        super(connected, self).__init__()
        self.clientsocket = csocket
        self.address = addr

    def run(self):
        clientsocket.recv

    def write(*args):
        for val in args:
            clientsocket.sendall(val)

