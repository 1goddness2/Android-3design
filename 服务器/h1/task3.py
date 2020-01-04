import zmq
import sys
import time

context = zmq.Context()
socket = context.socket(zmq.SUB)
socket.connect("tcp://localhost:5555")
socket.setsockopt_string(zmq.SUBSCRIBE,'')  # 消息过滤

context1=zmq.Context()
socket1 = context1.socket(zmq.PUB)
socket1.bind("tcp://*:5556")
while True:
    response = socket.recv();
    print("response: %s" % eval(response))
    socket1.send_string("hello")
    time.sleep(1)