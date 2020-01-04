import zmq
import time

context = zmq.Context()
socket = context.socket(zmq.PUSH)
socket.bind("tcp://*:5557")

socket1 = context.socket(zmq.PULL)
socket1.connect("tcp://172.20.10.3:5559")


while True:
    for i in range(1,10):
     socket.send_string("测试消息")
     print("已发送")
     time.sleep(5)
     d=socket1.recv()
     print(d)