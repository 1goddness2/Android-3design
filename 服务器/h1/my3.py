from flask import Flask,request,render_template,abort
app = Flask(__name__)
@app.route('/search',methods=['GET'])
def search_form():
    return  render_template('search.html')

#如果访问的是/search页面的post请求，则调用send_post（）方法
@app.route('/search',methods=['POST'])
def search():
    name = request.form['username']

#封装请求百度接口的方法
def send_post(name):
    url = 'http://sp0.baidu.com/common/api/yaohao'
    data = {
        'name': name,
        'city': '深圳'
    }
    #使用requests的get请求，返回json格式
    res = request.get(url=url,params=data).json()
    #json.dumps将字典转换为json
    return "<h3>"+json.dumps(res)+"</h3>"


if __name__ == '__main__':
    app.run()