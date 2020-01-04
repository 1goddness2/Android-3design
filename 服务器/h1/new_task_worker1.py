import zmq
import time

context = zmq.Context()

recive = context.socket(zmq.PULL)
recive.connect('tcp://172.20.10.4:5557')

sender = context.socket(zmq.PUSH)
sender.connect('tcp://172.20.10.4:5558')

while True:
    for i in range(1,10):
      data = recive.recv()
      print(data)
      print("正在转发...")
      time.sleep(3)
      sender.send(data)