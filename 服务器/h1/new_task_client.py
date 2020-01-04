import zmq
import sys

context = zmq.Context()
socket = context.socket(zmq.PULL)
socket.bind("tcp://localhost:5554")

sender = context.socket(zmq.PUSH)
sender.connect('tcp://*:5559')

while True:
    response = socket.recv();
    print("response: %s" % response)
    sender.send(response)