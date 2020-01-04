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

@app.route('/')
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
sql = "SELECT * FROM user1"
cur.execute(sql)
results=cur.fetchall()
@app.route('/login',methods=['POST'])
def v_login():
    flag=0
    uid = request.form['uid']
    pwd = request.form['pwd']
    #system = request.form['system']
    for it in results:
        if uid == it['id'] and pwd == it['pwd']:
            flag=1

    if(flag == 0):
        return 'fail'
    else:
        return send_file('templates/hah.html')


app.run(port=8080)
