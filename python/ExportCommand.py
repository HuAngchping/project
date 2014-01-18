# coding=utf-8
from datetime import datetime
import httplib, json, xlwt, time, sys, os

HOST = "125.208.9.90"
PORT = "8603"

def requestMessage(ip, port, method, url):
  conn = httplib.HTTPConnection(ip, port)
  conn.request(method, url)
  response = conn.getresponse()
  res = response.read()
  #print response.status
  results = json.loads(res)
  return results


# url = http://125.208.9.90:8603/oauth/manage/allusers.json
def request_message(url, method="GET"):
#  results = requestMessage(HOST, PORT, "GET", url)
  conn = httplib.HTTPConnection(HOST, PORT)
  conn.request(method, url)
  response = conn.getresponse()
  res = response.read()
  #print response.status
  results = json.loads(res)

  return results

def ws_write(ws, param):
  x,y = param
  ws.write(0, x, y)

def ws_sheet_init(wb):
  ws = wb.add_sheet("Export User Sheet")
  #title = ["0,'用户ID'", "1,'用户名'", "2, '真实姓名'", "3, '学生性别'", "4, '身份证'", "5, '学生邮箱'", "6, '学生手机号'", "7, '序列号'", "8, '省份'", "9, '城市'", "10, '学校'", "11, '年级'", "12, '班级'", "13, '家长姓名'", "14, '关系'", "15, '家长性别'", "16, '家长邮箱'", "17, '家长手机号'", "18, '学生学科教材'", "19, '套餐类型'", "20, '注册时间'", "21, '是否删除'"]
  title = [(0,"用户ID"), (1,"用户名"), (2,"真实姓名"), (3,"学生性别"), (4,"身份证"), (5,"学生邮箱"), (6,"学生手机号"), (7,"序列号"),
            (8,"省份"), (9,"城市"), (10,"学校"), (11,"年级"), (12,"班级"), (13,"家长姓名"), (14,"关系"), (15,"家长性别"), (16,"家长邮箱"),
             (17,"家长手机号"), (18,"学生学科教材"), (19,"套餐类型"), (20,"注册时间"), (21,"是否删除")]

  map(lambda param: ws_write(ws, param), title)
  return ws

def ws_create(ws):
  user_url = "/oauth/manage/allusers.json"
  province_url = "/oauth/baseinfo/database/province/%s.json"
  city_url = "/oauth/baseinfo/database/city/%s.json"
  grade_url = "/oauth/baseinfo/database/grade/%s.json"
  relation_url =  "/oauth/baseinfo/database/relations/%s.json"
  book_url =  "/oauth/baseinfo/database/book/%s.json"

  results = request_message(user_url)
  j = 0
  # users = [{}]
  for result in results["users"]:
    j = j + 1
    ws.write(j, 0, result["userid"])
    ws.write(j, 1, result["username"])
    ws.write(j, 2, result["student_name"])
    ws.write(j, 3, result["student_sex"])
    ws.write(j, 4, result["id_card"])
    ws.write(j, 5, result["student_email"])
    ws.write(j, 6, result["telephone"])
    ws.write(j, 7, result["serial_number"])
    # 省份 城市
    province = request_message(province_url % result["student_province"])
    ws.write(j, 8, province["name"])
    city = request_message(city_url % result["student_city"])
    if city["name"] == "":
      ws.write(j, 9, result["student_city"])
    else:
      ws.write(j, 9, city["name"])

    ws.write(j, 10, result["student_school"])

    grade = request_message(grade_url % result["student_grade"])
    if grade["name"] == "":
      ws.write(j, 11, result["student_grade"])
    else:
      ws.write(j, 11, grade["name"])
    ws.write(j, 12, result["student_class"])
    ws.write(j, 13, result["parent_name"])
    relat = request_message(relation_url % result["relationship"])
    ws.write(j, 14, relat["name"])
    ws.write(j, 15, result["parent_sex"])
    ws.write(j, 16, result["parent_email"])
    ws.write(j, 17, result["parent_phone"])

    books = ""
    for bookcode in result["student_book"]:
      book = request_message(book_url % bookcode)
      books += book + "\n"
    ws.write(j, 18, books)
    ws.write(j, 19, result["feeType"])
    #print result["createAt"]
    ws.write(j, 20, str(datetime.fromtimestamp(result["createAt"]/1000)))
    # 是否删除
    if result["valid"] == 1:
      ws.write(j, 21, "是")
    else:
      ws.write(j, 21, "否")
    #print result["telephone"]

def ws_save(wb):
  if os.path.exists(exportpath):
    wb.save(exportpath + "/userinfo.xls")
  else:
    print 'Please input save path.'
def init():
  wb = xlwt.Workbook(encoding="utf-8")
  ws = ws_sheet_init(wb)
  ws_create(ws)
  ws_save(wb)

if __name__ == '__main__':
  exportpath = "./"
  if len(sys.argv) > 1:
    exportpath = sys.argv[1]
  init()
