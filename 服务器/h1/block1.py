import hashlib as hasher
import pymysql
import numpy as np

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


def create_genesis_block():
  # Manually construct a block with
  # index zero and arbitrary previous hash
  return Block({'前置物品编号':'0','区块名称':'无极之剑','地点':'哇奎恩','人物':'易','联系方式':'和','商品描述':'hah'}, "0",hasher.sha256().update(("Genesis Block"+"0").encode('utf-8')))

def next_block(last_block,new_data):
  this_data = new_data
  previous_hash=last_block.hash
  this_hash=hasher.sha256().update((str(this_data)+str(previous_hash)).encode('utf-8'))
  return Block(this_data,previous_hash,this_hash)

class Block:
  def __init__(self,data, previous_hash,this_hash):
    self.data = data
    self.previous_hash = previous_hash
    self.hash = self.hash_block()

  def hash_block(self):
    sha = hasher.sha256()
    sha.update((str(self.data) +
               str(self.previous_hash)).encode('utf-8'))
    return sha.hexdigest()

# Create the blockchain and add the genesis block
def create_blockChain():
   blockchain = [create_genesis_block()]
   previous_block = blockchain[0]

# How many blocks should we add to the chain
# after the genesis block
   num_of_blocks_to_add = 4
# Add blocks to the chain
   for i in range(0, num_of_blocks_to_add):
       result=cur.fetchone()
       #print(type(result))
       #a=result['人物']
       block_to_add = next_block(previous_block,result)
       blockchain.append(block_to_add)
       previous_block = block_to_add
    return blockchain
blockchain=create_blockChain()
for j in blockchain:
    if j.data["人物"] == "希尔森":
        print("success")

