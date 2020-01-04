from hashlib import sha256
import hashlib as hasher
import pymysql
import json

con=pymysql.connect(
    host='127.0.0.1',
    port=3306,
    user='root',
    password='70582829',
    db='book1',
    cursorclass=pymysql.cursors.DictCursor
    #charset='utf8'
)

cur=con.cursor()
sql="SELECT * FROM product"
cur.execute(sql)


class Block:

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


class BlockChain:

    def __init__(self):
        self.chain = [self.createGenesisBlock()]
        self.difficulty = 4

    def createGenesisBlock(self):
        return Block({'num':'0','name':'无极之剑','place':'哇奎恩','man':'易','phone':'和','descrip':'hah'},)

    def getLatestBlock(self):
        return self.chain[len(self.chain) - 1]

    # 添加区块前需要 做一道计算题😶,坐完后才能把区块加入到链上
    def addBlock(self, newBlock):
        newBlock.previousHash = self.getLatestBlock().hash
        newBlock.minerBlock(self.difficulty)
        self.chain.append(newBlock)

    def __str__(self):
        return str(self.__dict__)

    def chainIsValid(self):
        for index in range(1, len(self.chain)):
            currentBlock = self.chain[index]
            previousBlock = self.chain[index - 1]
            if (currentBlock.hash != currentBlock.calculateHash()):
                return False
            if previousBlock.hash != currentBlock.previousHash:
                return False
        return True


#myCoin = BlockChain()

# 下面打印了每个区块挖掘需要的时间 比特币通过一定的机制控制在10分钟出一个块
# 其实就是根据当前网络算力 调整我们上面difficulty值的大小,如果你在
# 本地把上面代码difficulty的值调很大你可以看到很久都不会出计算结果
#startMinerFirstBlockTime = time.time()
#print("start to miner first block time :" + str(startMinerFirstBlockTime))

#myCoin.addBlock(Block(1, "02/01/2018", "{amount:4}"))

#print("miner first block time completed" + ",used " + str(time.time() - startMinerFirstBlockTime) + "s")

#startMinerSecondBlockTime = time.time()

#print("start to miner first block time :" + str(startMinerSecondBlockTime))

#myCoin.addBlock(Block(2, "03/01/2018", "{amount:5}"))
#cur1=con.cursor()
#print("miner second block time completed" + ",used " + str(time.time() - startMinerSecondBlockTime) + "s\n")
#sql2="SELECT * FROM product"
#cur1.execute(sql2)

#for i in range(1,4):
#    result=cur.fetchone()
    #print(result)
 #   myCoin.addBlock(Block(result))

# print block info
#print("print block info ####:\n")
#for block in myCoin.chain:
#    print("\n")
#    print(block)

#filename="D:\\result2.json"
#f_o=open(filename,'w')

#li=[]
#for k in myCoin.chain:
#    result1=cur1.fetchone()
    #print(result1)
   # json.dump('[', f_o, ensure_ascii=False)
#    if result1 != None:
#         li.append({'num':result1['num'], 'name':result1['name'], 'place':result1['place'], 'phone':result1['phone'], 'description':result1['description'], 'previousHash': k.previousHash, 'hash':k.hash})
    #json.dump({'num':result['num'], 'name':result['name'], 'place':result['place']}, f_o, ensure_ascii=False)
    #json.dump(',', f_o, ensure_ascii=False)
#json.dump(']', f_o,ensure_ascii=False)
#print(li)
#json.dump(li,f_o,ensure_ascii=False)
#print(type(li))
#json.dump(li,f_o,ensure_ascii=False)
#for k in myCoin.chain:
#    one = k.data
#    mess = k.previousHash+";"+k.hash
#    two={"mess":mess}
#    d=dict(one,**two)
#    json.dump(d,f_o,ensure_ascii=False)
 #check blockchain is valid
#print("before tamper block,blockchain is valid ###")
#print(myCoin.chainIsValid())

# tamper the blockinfo
#myCoin.chain[1].data = "{amount:1002}"
#print("after tamper block,blockchain is valid ###")
#print(myCoin.chainIsValid())