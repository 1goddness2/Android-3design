import threading
import zmq
import json
import time
import json
from hashlib import sha256

class new_Block:

    def __init__(self, data, previousHash=""):

        self.data = data
        self.previousHash = previousHash
        self.nonce = 0 #// 代表当前计算了多少次hash计算
        self.hash = self.calculateHash()

    def calculateHash(self):
        plainData = str(self.data) + str(self.nonce)
        return sha256(plainData.encode('utf-8')).hexdigest()

    # 挖矿 difficulty代表复杂度 表示前difficulty位都为0才算成功
    def minerBlock(self, difficulty):
        while (self.hash[0:difficulty] != str(0).zfill(difficulty)):
            self.nonce += 1
            self.hash = self.calculateHash()

    def __str__(self):
        return str(self.__dict__)

class MessageThread1(threading.Thread):
    def __init__(self):
        threading.Thread.__init__(self)
        self.REQUEST_TIMEOUT = 2000  # ms
    def run(self):
        context = zmq.Context()
        print("Connecting")
        socket = context.socket(zmq.REQ)
        socket.connect("tcp://*:5555")
        for i in range(3):
            with open("D:/result2.json", 'r') as f:
                temp = json.loads(f.read())
                print(temp)
                socket.send_json(temp)
                message = socket.recv()
                print(message)
class MessageThread2(threading.Thread):
    def __init__(self):
        threading.Thread.__init__(self)
        self.REQUEST_TIMEOUT = 2000  # ms
    def run(self):
        context = zmq.Context()
        socket = context.socket(zmq.REP)
        socket.bind("tcp://192.168.43.33:5555")
        while True:
            response = socket.recv();
            b = eval(response)
            li = list(b)

            num1 = len(li) - 1
            num2 = len(li) - 2
            prhash = li[num2]['hash']
            print(prhash)
            a = new_Block(li[num1], previousHash=prhash)
            a.minerBlock(4)

            an = []
            for i in range(0, num2):
                an.append(li[i])

            an.append(
                {'num': a.data['num'], 'name': a.data['name'], 'phone1': a.data['phone1'], 'phone2': a.data['phone2'],
                 'phone3': a.data['phone3'], 'place1': a.data['place1'], 'place2': a.data['place2'],
                 'place3': a.data['place3'],
                 'description1': a.data['description1'], 'description2': a.data['description2'],
                 'description3': a.data['description3'], 'previousHash': a.previousHash, 'hash': a.hash})

            #f_o = open('D:/result2.json', 'w')
            #json.dump(an, f_o, ensure_ascii=False)
            #with open("D:/result2.json", 'r') as f:
            #  temp = json.loads(f.read())
            socket.send_json(an)