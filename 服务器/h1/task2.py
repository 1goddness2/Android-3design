import zmq
import time

sendData={
  '2016-07-21':{'value':3934,
    'titles':[u'标题1',u'标题2',u'标题3']
   }
}


context = zmq.Context()
socket = context.socket(zmq.PUB)
socket.bind("tcp://*:5555")
socket1 = context.socket(zmq.SUB)
socket1.connect("tcp://localhost:5556")
socket1.setsockopt_string(zmq.SUBSCRIBE,'')

while True:
  print('发送消息')
#socket.send_string("消息群发")
  socket.send_string(repr(sendData))
  time.sleep(1)
  d=socket1.recv()
  print(d)
  #print(eval(d))
  time.sleep(1)