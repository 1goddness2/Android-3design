from my7 import create_blockChain,next_block,Block

blockchain=create_blockChain()
print(blockchain)
for j in blockchain:
    if j.data["人物"] == "希尔森":
        print("success")