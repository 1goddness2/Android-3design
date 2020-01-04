import zmq
import sys

context = zmq.Context()
socket = context.socket(zmq.SUB)
socket.connect("tcp://localhost:5555")
socket.setsockopt_unicode(zmq.SUBSCRIBE,'')
while True:
    response = socket.recv()
    print(response)

    socket.send_json(repr(response))