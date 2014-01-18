# coding=utf-8
import Tkinter, tkFileDialog

class App:
	def __init__(self, master):
		master.title("用户信息导出")
		frame = Frame(master)
		frame.pack()

		self.label = Label(frame, text="路径：")
		self.label.pack(side=LEFT)
		self.label = Label(frame, text="")
		self.label.pack(side=LEFT)
		self.button = Button(frame, text="选择", command=self.open_dialog)
		self.button.pack(side=LEFT)
		self.button = Button(frame, text="导出", command=frame.quit)
		self.button.pack(side=LEFT)
	def open_dialog(self):
		dirname = tkFileDialog.askdirectory(parent=root, title="Please select a directory")
root = Tkinter.Tk()
app = App(root)
root.mainloop()
