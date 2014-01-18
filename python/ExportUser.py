# coding=utf-8
import Tkinter, tkFileDialog, httplib, json, xlwt

class TkFileDialogExample(Tkinter.Frame):
	def __init__(self, root):
		self.frame = Tkinter.Frame.__init__(self, root)
		self.label0 = Tkinter.Label(self, text="导出路径：").grid(row=1, column=0)
		global va
		self.va = Tkinter.StringVar()
		self.va.set("")

		self.label1 = Tkinter.Label(self, textvariable=self.va).grid(row=1, column=1)
		self.button0 = Tkinter.Button(self, text='选择', command=self.opendialog).grid(row=1, column=2)
		self.button1 = Tkinter.Button(self, text="导出", command=self.export).grid(row=1, column=3)

	def opendialog(self):
		dirname = tkFileDialog.askdirectory()
		if len(dirname) > 0:
			self.va.set(dirname)
			print dirname
	def export(self):
		oauthurl = "125.208.9.90"
		oauthport = "8603"
		results = self.requestMessage(oauthurl, oauthport, "GET", "/oauth/manage/users.json?cp=1&ls=10000&keyword=")
		i = len(results["users"])
		#print i
		wb = xlwt.Workbook(encoding="utf-8")
		ws = wb.add_sheet("Export User Sheet")
		ws.write(0, 0, "用户ID")
		ws.write(0, 1, "用户名")
		ws.write(0, 2, "真实姓名")
		ws.write(0, 3, "学生性别")
		ws.write(0, 4, "身份证")
		ws.write(0, 5, "学生邮箱")
		ws.write(0, 6, "学生手机号")
		ws.write(0, 7, "序列号")
		ws.write(0, 8, "省份")
		ws.write(0, 9, "城市")
		ws.write(0, 10, "学校")
		ws.write(0, 11, "年级")
		ws.write(0, 12, "班级")
		ws.write(0, 13, "家长姓名")
		ws.write(0, 14, "关系")
		ws.write(0, 15, "家长性别")
		ws.write(0, 16, "家长邮箱")
		ws.write(0, 17, "家长手机号")
		ws.write(0, 18, "学生学科教材")
		ws.write(0, 19, "套餐类型")
		ws.write(0, 20, "注册时间")
		ws.write(0, 21, "是否删除")
		#print type(results)
		j = 1
		for result in results["users"]:
			ws.write(j, 0, result["userid"])
			ws.write(j, 1, result["username"])
			ws.write(j, 2, result["student_name"])
			ws.write(j, 3, result["student_sex"])
			ws.write(j, 4, result["id_card"])
			ws.write(j, 5, result["student_email"])
			ws.write(j, 6, result["telephone"])
			ws.write(j, 7, result["serial_number"])
			# 省份 城市
			province = self.requestMessage(oauthurl, oauthport, "GET", "/oauth/baseinfo/database/province/" + result["student_province"] + ".json")
			ws.write(j, 8, province["name"])
			city = self.requestMessage(oauthurl, oauthport, "GET", "/oauth/baseinfo/database/city/" + result["student_city"] + ".json")
			if city["name"] == "":
				ws.write(j, 9, result["student_city"])
			else:
				ws.write(j, 9, city["name"])

			ws.write(j, 10, result["student_school"])
			grade = self.requestMessage(oauthurl, oauthport, "GET", "/oauth/baseinfo/database/grade/" + result["student_grade"] + ".json")
			if grade["name"] == "":
				ws.write(j, 11, result["student_grade"])
			else:
				ws.write(j, 11, grade["name"])
			ws.write(j, 12, result["student_class"])
			ws.write(j, 13, result["parent_name"])
			ws.write(j, 14, result["relationship"])
			ws.write(j, 15, result["parent_sex"])
			ws.write(j, 16, result["parent_email"])
			ws.write(j, 17, result["parent_phone"])

			books = ""
			for bookcode in result["student_book"]:
				book = self.requestMessage(oauthurl, oauthport, "GET", "/oauth/baseinfo/database/book/" + bookcode + ".json")
				books += book + "\n"
			ws.write(j, 18, books)
			ws.write(j, 19, result["feeType"])
			ws.write(j, 20, result["createAt"])
			# 是否删除
			if result["valid"] == 1:
				ws.write(j, 21, "是")
			else:
				ws.write(j, 21, "否")
			#print result["telephone"]
			j = j + 1
			if j > i :
				break
		export_path = self.va.get()
		if len(export_path) > 0:
			wb.save(export_path + "/userinfo.xls")
		else:
			wb.save("userinfo.xls")
	def requestMessage(self, ip, port, method, url):
		conn = httplib.HTTPConnection(ip, port)
		conn.request(method, url)
		response = conn.getresponse()
		res = response.read()
		#print response.status
		results = json.loads(res)
		return results
root = Tkinter.Tk()
root.title("用户信息导出")
TkFileDialogExample(root).grid()
root.mainloop()