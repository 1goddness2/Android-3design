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

#print(temp)
#print(len(li))
#print(type(li[0]))
#print(li[3]['hash'])
data={'num':'4','name':'刀锋意志','place':'艾欧尼亚','phone':'qqq','description':'never give up'}
#plain=str(data)
#li.append({'num':'4','name':'刀锋意志','place':'艾欧尼亚','phone':'qqq','description':'never give up'})
li=[]
with open("D:/result2.json", 'r') as f:
  temp = json.loads(f.read())
  for one in temp:
      li.append(one)
print(type(li))
b=len(li)-1
c=li[b-1]['hash']
#print(c)
a=new_Block(data,previousHash=c)
a.minerBlock(4)
print(a.hash)
li.append({'num':a.data['num'],'name':a.data['name'],'place':a.data['place'],'description':a.data['description'],'previousHash':a.previousHash,'hash':a.hash})
#f_o=open('D:/result2.json','w')
#json.dump(li,f_o,ensure_ascii=False)