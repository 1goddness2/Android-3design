import threading
import time
import zmq
import json

t=0
class threadM(threading.Thread):

    def __init__(self,endpoint):
        threading.Thread.__init__(self)
        self.__flag = threading.Event()     # 用于暂停线程的标识
        self.__flag.set()       # 设置为True
        self.__running = threading.Event()      # 用于停止线程的标识
        self.__running.set()      # 将running设置为True
        self.endpoint = endpoint
    def run(self):
        while self.__running.isSet():
            #self.__flag.wait()      # 为True时立即返回, 为False时阻塞直到内部的标识位为True后返回
            context1=zmq.Context()
            socket1=context1.socket(zmq.PUB)
            threadLock.acquire()
            for i in range(5):
                print('发送成功,请接受')
                with open("D:/result2.json", 'r') as f:
                    temp = json.loads(f.read())
                    socket1.send_json(temp)
            socket1.close()
            threadLock.release()
    def pause(self):
        self.__flag.clear()     # 设置为False, 让线程阻塞

    def resume(self):
        self.__flag.set()    # 设置为True, 让线程停止阻塞

    def stop(self):
        self.__flag.set()       # 将线程从暂停状态恢复, 如何已经暂停的话
        self.__running.clear()        # 设置为False

class threadN(threading.Thread):

    def __init__(self,endpoint):
        threading.Thread.__init__(self)
        self.__flag = threading.Event()     # 用于暂停线程的标识
        self.__flag.set()       # 设置为True
        self.__running = threading.Event()      # 用于停止线程的标识
        self.__running.set()      # 将running设置为True
        self.endpoint = endpoint
    def run(self):
        while self.__running.isSet():
            self.__flag.wait()      # 为True时立即返回, 为False时阻塞直到内部的标识位为True后返回
            context2 = zmq.Context()
            socket2 = context2.socket(zmq.SUB)
            socket2.connect(self.endpoint)
            while True:
                rep = socket2.recv_json()
                # reply = json.loads(rep)
                print(rep)
                if rep != None:
                    global t
                    t = 1
                    break
            socket2.close()

    def pause(self):
        self.__flag.clear()     # 设置为False, 让线程阻塞

    def resume(self):
        self.__flag.set()    # 设置为True, 让线程停止阻塞

    def stop(self):
        self.__flag.set()       # 将线程从暂停状态恢复, 如何已经暂停的话
        self.__running.clear()        # 设置为False

class threadP(threading.Thread):

    def __init__(self,endpoint):
        threading.Thread.__init__(self)
        self.__flag = threading.Event()     # 用于暂停线程的标识
        self.__flag.set()       # 设置为True
        self.__running = threading.Event()      # 用于停止线程的标识
        self.__running.set()      # 将running设置为True
        self.endpoint = endpoint
    def run(self):
        while self.__running.isSet():
            self.__flag.wait()      # 为True时立即返回, 为False时阻塞直到内部的标识位为True后返回
            context3=zmq.Context()
            socket3=context3.socket(zmq.PUB)
            threadLock.acquire()
            for i in range(5):
                print('计算已结束')
                with open("D:/result2.json", 'r') as f:
                    temp = json.loads(f.read())
                    socket3.send_json(temp)
            socket3.close()
            threadLock.release()
    def pause(self):
        self.__flag.clear()     # 设置为False, 让线程阻塞

    def resume(self):
        self.__flag.set()    # 设置为True, 让线程停止阻塞

    def stop(self):
        self.__flag.set()       # 将线程从暂停状态恢复, 如何已经暂停的话
        self.__running.clear()        # 设置为False

if __name__ == "__main__":
    threadLock=threading.Lock
    a1=threadM(endpoint="tcp://*:5555")
    time.sleep(2)
    a1.stop()
    b1=threadN(endpoint="tcp://172.20.10.3:5553")
   # b2=threadN(endpoint="")
    while True:
        b1.run()
    #    b2.run()
        if t==1:
            b1.stop()
     #       b2.stop()
            break
    c1=threadP(endpoint="tcp://*:55")
    c1.run()
    time.sleep(10)
    c1.stop()
