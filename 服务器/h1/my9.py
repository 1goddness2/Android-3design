import json
import  zmq
context = zmq.Context()
print("Connecting")
socket = context.socket(zmq.REQ)
socket.connect("tcp://192.168.43.33:5555")
for i in range(3):
    with open("D:/result2.json", 'r') as f:
        temp = json.loads(f.read())
        print(temp)
        socket.send_json(temp)

        message=socket.recv()
        print(message)