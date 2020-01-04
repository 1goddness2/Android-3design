from flask import Flask, request, render_template, redirect
from functools import wraps
from pow1 import Block, BlockChain
import json
from message2 import  MessageThread1, MessageThread2
import qrcode
import smtplib
from email.mime.text import MIMEText
from email.header import Header

app = Flask(__name__)

qr = qrcode.QRCode(
    version=1,
    error_correction=qrcode.constants.ERROR_CORRECT_L,
    box_size=10,
    border=4,
)
import pymysql
email=""
a = ""
b = ""
c = ""
d = ""
e = ""
f = ""
mycoin = BlockChain()

con = pymysql.connect(
    host='127.0.0.1',
    port=3306,
    user='root',
    password='70582829',
    db='book1',
    cursorclass=pymysql.cursors.DictCursor
    # charset='utf8'
)

smtp_host = "smtp.sina.com"
pop_host = "pop.sina.com"
port = 25
username = "ypb18811021669@sina.com"
password = "yqy19991223"


@app.route('/', methods=['GET', 'POST'])
def v_index():
    # if request.method =='GET':
    #    return render_template('register.html')
    # else:
    return render_template('login.html')


# sql1 = "SELECT * FROM user1"
# cur.execute(sql1)
# results=cur.fetchall()

@app.route('/login', methods=['POST'])
def v_login():
    cur = con.cursor()
    sql = "SELECT * FROM user1"
    cur.execute(sql)
    results = cur.fetchall()
    flag = 0
    uid = request.form['username']
    pwd = request.form['password']

    for it in results:
        if uid == it['id'] and pwd == it['pwd']:
            global a
            a = it['id']
            global b
            b = it['pwd']
            global c
            c = it['phone']
            global d
            d = it['email']
            global e
            e = it['descrip']
            global f
            f = it['class']
            flag = 1

    if (flag == 0):
        return 'fail'
    else:
        no=[]
        cur1=con.cursor()
        sql10="SELECT * FROM product"
        cur1.execute(sql10)
        r=cur1.fetchall()
        if f == '2':
            for one in r:
                if one['state'] == '0':
                    no.append(one)
            # print(no)
            #a1=MessageThread1()
            #a1.start()
            return render_template('index1.html', product1=no)
        else:
            #b1=MessageThread2()
            #b1.start()
            return render_template('index1.html', product1=r)



@app.route('/register.html', methods=['GET'])
def v_index1():
    return render_template('register.html')


@app.route('/register.html', methods=['POST'])
def v_register():
    uid = request.form['username']
    pwd = request.form['password']
    email = request.form['example-email']
    phone = request.form['phone']
    mess = request.form['mess']
    cur = con.cursor()
    sql1 = 'insert into user1(id,pwd,email,phone,descrip) values({},{},{},{},{})'.format(uid, pwd, email, phone, mess)
    cur.execute(sql1)
    con.commit()
    return render_template('login.html')


@app.route('/index1.html')
def v_index2():
    no=[]
    cur1 = con.cursor()
    sql10 = "SELECT * FROM product"
    cur1.execute(sql10)
    r = cur1.fetchall()
    if f == '2':
        for one in r:
            if one['state']=='0':
                no.append(one)
        #print(no)
        return render_template('index1.html',product1=no)
    else:
        return render_template('index1.html', product1=r)

@app.route('/pages-profile.html', methods=['GET'])
def v_profile():
    #print(type(a))
    #print(type(d))
    return render_template('pages-profile.html', id=a, pwd=b, phone=c, email=d, descrip=e)
   # return render_template('pages-profile.html')

@app.route('/pages-profile.html', methods=['POST'])
def v_porfile():
    l1 = request.form['name']
    l2 = request.form['example-email']
    l3 = request.form['pwd']
    l4 = request.form['phone']
    l5 = request.form['mess']
    #print(l1)
    #print(l3)
    cur = con.cursor()
    sql = "SELECT * FROM user1"
    cur.execute(sql)
    result = cur.fetchall()
    #print(type(l1))
    flag = 0
    for it in result:
        #print(it)
        print(type(it['id']))
        if l1 == it['id']:
            flag = 1
            sql1 = "update user1 set pwd='{}',email='{}',phone='{}',descrip='{}' where id='{}'".format(l3,l2,l4,l5,l1)
            print(sql1)
            #args=(l3,l2,l4,l5,l1)
            cur.execute(sql1)
            con.commit()
            break
    print(flag)
    if flag == 0:
        print("please regesit")
        return render_template('register.html')
    elif flag == 1:
        return render_template('index.html')


@app.route('/search.html', methods=['GET'])
def v_search():
    return render_template('search.html')


@app.route('/search.html', methods=['POST'])
def v_search1():
    cur = con.cursor()
    sql3 = "SELECT * FROM product"
    cur.execute(sql3)
    result2 = cur.fetchall()

    name = request.form['ProductName']
    # print(type(name))

    for it in result2:
        if name == it['name']:
            if  f == '1':
              print(it)
              data1={'num':it['num'],'name':it['name'],'place':it['place1'],'phone':it['phone1']}
              qr.add_data(data1)
              qr.make(fit=True)
              img1 = qr.make_image()
              img1.save('D:/h1/static/assets/images/big/img1.jpg.png')
              data2 = {'num': it['num'], 'name': it['name'], 'place': it['place2'], 'phone': it['phone2']}
              qr.add_data(data2)
              qr.make(fit=True)
              img2 = qr.make_image()
              img2.save('D:/h1/static/assets/images/big/img2.jpg.png')
              data3 = {'num': it['num'], 'name': it['name'], 'place': it['place3'], 'phone': it['phone3']}
              qr.add_data(data3)
              qr.make(fit=True)
              img3 = qr.make_image()
              img3.save('D:/h1/static/assets/images/big/img3.jpg.png')
              return render_template('result.html', product=it)
            elif it['state']=='0'and f == '2':
              data1 = {'num': it['num'], 'name': it['name'], 'place': it['place1'], 'phone': it['phone1']}
              qr.add_data(data1)
              qr.make(fit=True)
              img4 = qr.make_image()
              img4.save('D:/h1/static/assets/images/big/img1.jpg.png')
              data2 = {'num': it['num'], 'name': it['name'], 'place': it['place2'], 'phone': it['phone2']}
              qr.add_data(data2)
              qr.make(fit=True)
              img5 = qr.make_image()
              img5.save('D:/h1/static/assets/images/big/img2.jpg.png')
              data3 = {'num': it['num'], 'name': it['name'], 'place': it['place3'], 'phone': it['phone3']}
              qr.add_data(data3)
              qr.make(fit=True)
              img6 = qr.make_image()
              img6.save('D:/h1/static/assets/images/big/img3.jpg.png')
              return render_template('result.html', product=it)


@app.route('/input.html', methods=['GET'])
def v_input():
    return render_template('input.html')


@app.route('/input.html', methods=['POST'])
def v_input1():
    cur = con.cursor()
    l1 = request.form['bianhao']
    l2 = request.form['add']
    l3 = request.form['renwu']
    l4 = request.form['phone']
    l5 = request.form['area']
    # if l5 == "原产地":
    #    sql2 = "INSERT INTO product(num,place1,name,man1,phone1,state) VALUES('%s','%s','%s','%s','%s')"%(l1, l5, l2, l3, l4)
    # elif l5 == "加工厂":
    #    sql2 = "INSERT INTO product(num,place2,name,man2,phone2,state) VALUES('%s','%s','%s','%s','%s')" % (
    #    l1, l5, l2, l3, l4)
    # elif l5 == "物流装货":
    #    sql2 = "INSERT INTO product(num,place3,name,man3,phone3,state) VALUES('%s','%s','%s','%s','%s')" % (
    #    l1, l5, l2, l3, l4)

    if request.form['up'] == 'up1':
        l6 = '1'
        if l5 == "原产地":
            sql2 = "INSERT INTO product(num,place1,name,man1,phone1,state) VALUES('%s','%s','%s','%s','%s','%s')" % (
                l1, l5, l2, l3, l4, l6)
            cur.execute(sql2)
            con.commit()
        elif l5 == "加工厂":
            sql2 = "INSERT INTO product(num,place2,name,man2,phone2,state) VALUES('%s','%s','%s','%s','%s','%s')" % (
                l1, l5, l2, l3, l4, l6)
            cur.execute(sql2)
            con.commit()
        elif l5 == "物流装货":
            sql2 = "INSERT INTO product(num,place3,name,man3,phone3,state) VALUES('%s','%s','%s','%s','%s','%s')" % (
                l1, l5, l2, l3, l4, l6)
            cur.execute(sql2)
            con.commit()

    elif request.form['up'] == 'up2':

        cur1 = con.cursor()
        l6 = '0'
        if l5 == "原产地":
            sql7 = "INSERT INTO product(num,place1,name,man1,phone1,state) VALUES('%s','%s','%s','%s','%s','%s')" % (
                l1, l5, l2, l3, l4, l6)
            cur1.execute(sql7)
            con.commit()
        elif l5 == "加工厂":
            sql7 = "INSERT INTO product(num,place2,name,man2,phone2,state) VALUES('%s','%s','%s','%s','%s','%s')" % (
                l1, l5, l2, l3, l4, l6)
            cur1.execute(sql7)
            con.commit()
        elif l5 == "物流装货":
            sql7 = "INSERT INTO product(num,place3,name,man3,phone3,state) VALUES('%s','%s','%s','%s','%s','%s')" % (
                l1, l5, l2, l3, l4, l6)
            cur1.execute(sql7)
            con.commit()
        filename = "D:\\result2.json"
        f_o = open(filename, 'w')
        sql3 = "SELECT * FROM product"
        cur1.execute(sql3)
        result = cur1.fetchall()
        ar = []
        for er in result:
            # print(type(er['state']))
            if er['state'] == '0':
                mycoin.addBlock(Block(er))
                ar.append(er)
        # print(len(ar))
        li = []
        sql4 = "SELECT * FROM product"
        i = 0
        cur1.execute(sql4)
        for k in mycoin.chain:
            # print(i)
            if i < len(ar):
                # result = cur1.fetchone()
                result = ar[i]
                # if  result['state'] == '0':
                # print(result['state'])
                # if result!=None:
                li.append({'num': result['num'], 'name': result['name'], 'phone1': result['phone1'],
                           'phone2': result['phone2'], 'phone3': result['phone3'], 'place1': result['place1'],
                           'place2': result['place2'], 'place3': result['place3'],
                           'description1': result['description1'], 'description2': result['description2'],
                           'description3': result['description3'], 'previousHash': k.previousHash, 'hash': k.hash})
                i = i + 1
        json.dump(li, f_o, ensure_ascii=False)
    return render_template('index.html')

@app.route('/forget.html')
def v_forget():
    return render_template('forget.html')

@app.route('/forget.html',methods=['post'])
def send_val_code(self, receiver, code):
    message = MIMEText("您的验证码是: " + str(code), 'plain', 'utf-8')
    message['from'] = Header(username)
    message['To'] = Header(receiver)
    message['Subject'] = Header('商品溯源系统验证邮件', 'utf-8')
    try:
        stmpObj = smtplib.SMTP(smtp_host, port)
        stmpObj.login(username, password)
        stmpObj.sendmail(username, receiver, message.as_string())
        print("发送邮件成功")
        return True
    except smtplib.SMTPException as e:
        print("Error: 无法发送邮件 ", e)
        return False


#@app.route('/forget.html',methods=['post'])
#def send_email():
#    global email
#    email=request.form['email']

@app.route('/show.html', methods=['GET'])
def v_show():
    with open("D:\\result2.json", 'r') as f:
        temp = json.loads(f.read())
       # print(temp)
    return render_template('show.html',items=temp)


#app.run(host="0.0.0.0",port=8080)
app.run(port=8080)
# @app.route('/proudct',methods=['POST'])
# def product_show():
