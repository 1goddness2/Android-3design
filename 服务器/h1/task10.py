import zmq
import time
import json

context = zmq.Context()
socket = context.socket(zmq.PUB)
socket.bind("tcp://*:5555")

li=[]
for i in range(0,3):
    print('发送消息')
    with open("D:/result2.json", 'r') as f:
        temp = json.loads(f.read())
        #for one in temp:
            #li.append(one)
        print(temp)
        socket.send_json(temp)
    time.sleep(5)
