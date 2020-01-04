from flask import Flask,request,render_template,send_file
import pymysql

con=pymysql.connect(
    host='127.0.0.1',
    port=3306,
    user='root',
    password='70582829',
    db='book1',
    cursorclass=pymysql.cursors.DictCursor
    #charset='utf8'
)

app = Flask(__name__)

@app.route('/',methods=['GET'])
def v_index():
    return  '''
        <form action="/login" method="POST">
            <input type="text" name="uid" placeholder="input your user id"> <br />
            <input type="password" name="pwd" placeholder="input your password"> <br />
            <input type="submit" value="Login">
        </form>
    '''
    #return render_template('bootstrap.html')
cur=con.cursor()
def insertRecord(con,num1,num2):
    cursor=con.cursor()
    #cursor.execute("INSERT INTO user1(id,pwd)\
    #                       VALUES(num1,num2)");
    #sql_2="insert into user1 (id,pwd) values('%s','%s')"%(num1,num2)
   # cursor.execute(sql_2)
    sql='insert into user1(id,pwd) values({},{})'.format(num1,num2)
    cursor.execute(sql)
    con.commit()
@app.route('/login',methods=['POST'])
def v_login():
    flag=0
    uid = request.form['uid']
    pwd = request.form['pwd']
    insertRecord(con,uid,pwd)
    #system = request.form['system']
    return render_template('search.html')


app.run(port=8080)