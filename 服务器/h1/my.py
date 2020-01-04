import pymysql
import pandas as pd
con=pymysql.connect(
    host='127.0.0.1',
    port=3306,
    user='root',
    password='70582829',
    db='book1',
    cursorclass=pymysql.cursors.DictCursor
    #charset='utf8'
)
#sqlcmd="select col_name,col_type,col_desc from itf_datadic_dtl_d limit 10"
cur=con.cursor()
#sql = "SELECT * FROM user1"
#cur.execute(sql)
#results=cur.fetchall()
#for it in results:
#    print(type(it['id']))

def insertRecord(con):
    cursor=con.cursor()
    cursor.execute("INSERT INTO user1(id,pwd)\
                           VALUES(17301055,666)");
    cursor.execute("INSERT INTO user1(id,pwd)\
                           VALUES(17301056,777)");
    con.commit()
    return True

insertRecord(con)
#row = cur.execute('show tables')
#print(row)
#all = cur.fetchall()
#for i in all:
#    print(i)
#print(all)
