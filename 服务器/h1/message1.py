import threading
import zmq
import json
import time
import json
from hashlib import sha256

t=0
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

class MessageThread2(threading.Thread):

    def __init__(self, threadID, name, endpoint):
        threading.Thread.__init__(self)
        self.REQUEST_TIMEOUT = 2000  # ms
        self.threadID = threadID
        self.name = name
        self.endpoint = endpoint
        self.__flag = threading.Event()  # 用于暂停线程的标识
        self.__flag.set()  # 设置为True
        self.__running = threading.Event()  # 用于停止线程的标识
        self.__running.set()

    def run(self):  # 把要执行的代码写到run函数里面 线程在创建后会直接运行run函数
        print("Starting " + self.name)
        context2 = zmq.Context()
        socket2 = context2.socket(zmq.PUB)
        socket2.bind(self.endpoint)
        #threadLock.acquire()
        for i in range(3):
            print('发送成功,请接受')
            with open("D:/result2.json", 'r') as f:
                temp = json.loads(f.read())
                # for one in temp:
                # li.append(one)
                socket2.send_json(temp)
            #time.sleep(5)
        socket2.close()
        #threadLock.release()
        print("Exiting " + self.name)

    def stop(self):
        self.__flag.set()  # 将线程从暂停状态恢复, 如何已经暂停的话
        self.__running.clear()  # 设置为False
class MessageThread(threading.Thread):

    def __init__(self, threadID, name, endpoint):
        threading.Thread.__init__(self)
        self.REQUEST_TIMEOUT = 2000  # ms
        self.threadID = threadID
        self.name = name
        self.endpoint = endpoint
        self.__flag = threading.Event()  # 用于暂停线程的标识
        self.__flag.set()
        self.__running = threading.Event()  # 用于停止线程的标识
        self.__running.set()
    def run(self):  # 把要执行的代码写到run函数里面 线程在创建后会直接运行run函数
        print("Starting " + self.name)
        context = zmq.Context()
        socket = context.socket(zmq.SUB)
        socket.connect(self.endpoint)
        socket.setsockopt(zmq.SUBSCRIBE, b'')  # Terminate early
        while True:
            rep = socket.recv_json()
            #reply = json.loads(rep)
            print(rep)
            #if rep!=None:
            global t
            t=1
            print("已经接受")
            f_o = open('D:/result2.json', 'w')
            json.dump(rep, f_o, ensure_ascii=False)
             #   break
        socket.close()
        print("Exiting " + self.name)
    def stop(self):
        self.__flag.set()  # 将线程从暂停状态恢复, 如何已经暂停的话
        self.__running.clear()
class MessageThread1(threading.Thread):

    def __init__(self, threadID, name, endpoint):
        threading.Thread.__init__(self)
        self.REQUEST_TIMEOUT = 2000  # ms
        self.threadID = threadID
        self.name = name
        self.endpoint = endpoint

    def run(self):  # 把要执行的代码写到run函数里面 线程在创建后会直接运行run函数
        print("Starting " + self.name)
        context1 = zmq.Context()
        socket1 = context1.socket(zmq.PUB)
        socket1.bind(self.endpoint)
        i=0
        #threadLock.acquire()
        for i in range(2):
            print('已计算成功，请接受成果')
            with open("D:/result2.json", 'r') as f:
                temp = json.loads(f.read())
                # for one in temp:
                # li.append(one)
                socket1.send_json(temp)
            time.sleep(5)
        socket1.close()
        #threadLock.release()
        print("Exiting " + self.name)

class MessageThread3(threading.Thread):

    def __init__(self, threadID, name):
        threading.Thread.__init__(self)
        self.REQUEST_TIMEOUT = 2000  # ms
        self.threadID = threadID
        self.name = name


    def run(self):  # 把要执行的代码写到run函数里面 线程在创建后会直接运行run函数
        context = zmq.Context()
        socket = context.socket(zmq.SUB)
        socket.connect("tcp://192.168.43.81:5555")
        socket.setsockopt_string(zmq.SUBSCRIBE, '')  # 消息过滤
        for i in range(0, 2):
            response = socket.recv();
            # print(eval(response))
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

            an.append({'num': a.data['num'], 'name': a.data['name'],'phone1':a.data['phone1'],'phone2':a.data['phone2'],'phone3':a.data['phone3'], 'place1': a.data['place1'],'place2': a.data['place2'],'place3': a.data['place3'],
                       'description1': a.data['description1'],'description2': a.data['description2'],'description3': a.data['description3'], 'previousHash': a.previousHash, 'hash': a.hash})

            f_o = open('D:/result2.json', 'w')
            json.dump(an, f_o, ensure_ascii=False)

        context1 = zmq.Context()
        socket1 = context1.socket(zmq.PUB)
        socket1.bind("tcp://*:5553")

        li = []
        for i in range(0, 3):
            print('发送消息')
            with open("D:/result2.json", 'r') as f:
                temp = json.loads(f.read())
                # for one in temp:
                # li.append(one)
                socket1.send_json(temp)

#if __name__ == "__main__":
#    threads=[]
#    a1=MessageThread2(1,"Thread-4",endpoint="tcp://*:5555")#

#    a1.run()

#    b1=MessageThread(3,"Thread-4",endpoint="tcp://172.20.10.3:5553")
#    b2 = MessageThread(3, "Thread-4", endpoint="")
#    threads.append(b1)
#    threads.append(b2)

   # while True:
#    b1.start()
#    b2.start()
#    for y in threads:
#        y.join()

     # b2.run()
   #   if t==1:
   #       b1.stop()
     #     b2.stop()
   #       break
#    c1=MessageThread1(2,"Thread-5",endpoint="tcp://*:5520")

   # b3.run()
   # b4.run()
