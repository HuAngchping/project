#!/usr/local/bin/python
# -*- coding: utf-8 -*-
import sys
import pycurl
import StringIO
import hashlib
import simplejson as json
import random
import string
import base64


HAOKE_HOST = 'http://haoke.maxrocky.cn'
START = 1
END = 30
PAGESIZE = 5
CATEGORIES = ['newest','hottest']
HEADERS = ['Accept: application/json',
           'Contet-Type: application/json',
           'User-Agent:Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.97 Safari/537.11']

username = ''
user_id = 0
password = ''
access_token = ''

def curl_get(url):
    buf = StringIO.StringIO()
    c = pycurl.Curl()
    c.setopt(pycurl.URL, url)
    c.setopt(pycurl.HTTPHEADER, HEADERS)
    c.setopt(pycurl.WRITEFUNCTION, buf.write)
    c.perform()
    resp =buf.getvalue()
    c.close()
    return resp

def curl_post(url, headers, post_data):
    buf = StringIO.StringIO()
    c = pycurl.Curl()
    c.setopt(pycurl.URL, url)
    c.setopt(pycurl.HTTPHEADER, HEADERS + headers)
    c.setopt(pycurl.POST, 1)
    c.setopt(pycurl.POSTFIELDS, post_data)
    c.setopt(pycurl.WRITEFUNCTION, buf.write)
    c.perform()
    resp = buf.getvalue()
    c.close()
    return resp

def curl_delete(url, header):
    buf = StringIO.StringIO()
    c = pycurl.Curl()
    c.setopt(pycurl.URL, url)
    c.setopt(pycurl.HTTPHEADER, HEADERS + header)
    c.setopt(pycurl.CUSTOMREQUEST, "DELETE")
    c.setopt(pycurl.WRITEFUNCTION, buf.write)
    c.perform()
    resp = buf.getvalue()
    c.close()
    return resp

def curl_put(url, header, put_data):
    buf = StringIO.StringIO()
    c = pycurl.Curl()
    c.setopt(pycurl.URL, url)
    c.setopt(pycurl.HTTPHEADER, HEADERS + header)
    c.setopt(pycurl.CUSTOMREQUEST, "PUT")
    #c.setopt(pycurl.PUT, 1)
    c.setopt(pycurl.POSTFIELDS, put_data)
    c.setopt(pycurl.WRITEFUNCTION, buf.write)
    c.perform()
    resp = buf.getvalue()
    c.close()
    return resp

def string_to_json(content):
    return json.loads(content)

def get_sort_keys():
    url = HAOKE_HOST + '/haoke/property'
    resp = curl_get(url)
    resp_json = string_to_json(resp)
    sort_keys = map(lambda t: t['id'],resp_json['List'])

    return sort_keys

def get_error_code(resp_json):
    if resp_json.has_key('error_code') is False:
        return "100000"
    return resp_json['error_code']

def valid_token_null(error_code):
    return True if error_code == "102102" else False

def valid_user_null(error_code):
    return True if error_code == "104101" else False

def valid_record_null(error_code):
    return True if error_code == "106101" else False

def valid_keep_null(error_code):
    return True if error_code == "206101" else False

def valid_system_error(error_code):
    return True if error_code == "107101" else False

def valid_login_error(error_code):
    '''username and password error'''
    return True if error_code == "101103" else False

def valid_logout_error(error_code):
    '''username and password error'''
    return True if error_code == "101103" else False

def valid_register_error(error_code):
    '''username already exists'''
    return True if error_code == "103102" else False

def valid_keep_error(error_code):
    '''teacher is not exists'''
    return True if error_code == "207101" else False

def valid_keep_cancel_error(error_code):
    '''teacher has cancel.'''
    return True if error_code == "209101" else False

def gen_username_password():
    israndom = True
    if israndom:
        s = string.lowercase + string.digits
        username = ''.join(random.sample(s, 8))
    else:
        username = 'haoke66'

    pwd = '123456'
    md5 = hashlib.md5()
    md5.update(pwd)
    pwd_md5 = md5.hexdigest()

    return (username, pwd_md5)

def user_login():
    global access_token
    version = 1
    header = ['clientid: ios','version: %d' % version]
    url = HAOKE_HOST + '/haoke/user/login'
    params =  '{"username" : "%s", "password" : "%s"}' % (username, password)
    resp = curl_post(url, header, params)
    resp_json = string_to_json(resp)
    error_code = get_error_code(resp_json)
    if valid_login_error(error_code) is True:
        print 'Username or password is error.'
        print "User login is failed."
        sys.exit(0)
    else:
        if valid_system_error(error_code) is True:
            print "Username or password is error."
            print "System Error: ", url
            sys.exit(0)
    valid, key = valid_login_register_keys(resp_json)
    if valid is False:
        print "Get Error:", url
        print "    The %s is not exist." % key
        print "Username or password is error."
        sys.exit(0)
    print "User login is Success."
    access_token = resp_json['access_token']

def user_register():
    global username
    global password
    url = HAOKE_HOST + '/haoke/user/register'
    header = ['clientid: ios']
    username, password = gen_username_password()
    params = '{"username":"%s","password":"%s"}' % (username, password)
    resp = curl_post(url, header, params)
    resp_json = string_to_json(resp)
    error_code = get_error_code(resp_json)
    if valid_register_error(error_code) is True:
        print 'Username is exist.'
        print "User register is failed."
        sys.exit(0)
    else:
        if valid_system_error(error_code) is True:
            print "User register is failed."
            print "System Error: ", url
            sys.exit(0)
    valid, key = valid_login_register_keys(resp_json)
    if valid is False:
        print "Get Error:", url
        print "    The %s is not exist." % key
        print "User register is failed."
        sys.exit(0)
    print "User register is Success."

def user_logout():
    url = HAOKE_HOST + '/haoke/user/logout'
    header = ['access_token: %s' % str(access_token)]
    url = HAOKE_HOST + '/haoke/user/logout'
    resp = curl_delete(url, header)
    resp_json = string_to_json(resp)
    error_code = get_error_code(resp_json)
    if valid_logout_error(error_code) is True:
        print 'User has exit.'
        print "User logout is failed."
        sys.exit(0)
    else:
        if valid_system_error(error_code) is True:
            print "User logout is failed."
            print "System Error: ", url
            sys.exit(0)
    valid, key = valid_result_keys(resp_json)
    if valid is False:
        print "Get Error:", url
        print "    The %s is not exist." % key
        print "User register is failed."
        sys.exit(0)
    print "User logout is Success."

def get_user_info():
    global user_id
    user_info_url = HAOKE_HOST + "/haoke/user?access_token=%s"
    url =  user_info_url % str(access_token)
    resp = curl_get(url)
    resp_json = string_to_json(resp)
    error_code = get_error_code(resp_json)
    if valid_token_null(error_code) is True:
        print 'User token invalid.'
        sys.exit(0)
    else:
        if valid_system_error(error_code) is True:
            print "User info is failed."
            print "System Error: ", url
            sys.exit(0)
    valid, key = valid_user_keys(resp_json)
    if valid is False:
        print "User info is failed."
        print "Get Error:", url
        print "    The %s is not exist." % key
        sys.exit(0)
    #user_id = resp_json['id']
    print "Get user info is Success."

def image_base64():
    image = ''
    with open("header_2.jpg", "rb") as f:
        data = f.read()
        image = base64.b64encode(data)

    return image

def update_user_info():
    realname = 'rocky'
    sex = 'male'
    headImage = str(image_base64())
    extension = 'jpg'
    url = HAOKE_HOST + '/haoke/user/modify'
    header = ['access_token: %s' % str(access_token)]
    params = '{"realname":"%s","sex":"%s","headImageUrl":"%s","extension":"%s"}' % (realname, sex, headImage, extension)
    resp = curl_put(url, header, params)
    resp_json = string_to_json(resp)
    error_code = get_error_code(resp_json)

    if valid_user_null(error_code) is True:
        print 'User name invalid.'
        sys.exit(0)
    else:
        if valid_system_error(error_code) is True:
            print "User info is failed."
            print "System Error: ", url
            sys.exit(0)

    valid, key = valid_result_keys(resp_json)
    if valid is False:
        print "Update user info is failed."
        print "Get Error:", url
        print "    The [%s] is not exist." % key
        sys.exit(0)
    print "Update user info is Success."

def valid_user_keys(resp_json):
    user_keys = ["headImageUrl",
                 "realName",
                 "sex",
                 "teacherCount",
                 "username"]
    for k in user_keys:
        if not resp_json.has_key(k):
            return (False, k)

    return (True, None)

def valid_login_register_keys(resp_json):
    login_register_keys = ["access_token","expires_in","refresh_token"]
    for k in login_register_keys:
        if not resp_json.has_key(k):
            return (False, k)
    return (True, None)


def valid_result_keys(resp_json):
    result_keys = ["result"]
    for k in result_keys:
        if not resp_json.has_key(k):
            return (False, k)
    return (True, None)

def get_teachers_id():
    category_url = HAOKE_HOST + "/haoke/teacher?category=%s&curpage=%s&pagesize=%s"
    curpage = 1
    pagesize = 10
    category = 'newest'
    url = category_url % (category, curpage, pagesize)
    resp = curl_get(url)
    resp_json = string_to_json(resp)
    error_code = get_error_code(resp_json)
    if valid_record_null(error_code) is True:
        print 'Search teachers failed.'
        sys.exit(0)

    if valid_system_error(error_code) is True:
        print "Search teachers System Error: ", url
        sys.exit(0)

    valid, key = valid_teachers_keys(resp_json)
    if valid is False:
        print "Keep teachers Get Error:", url
        print "    The %s is not exist." % key
        sys.exit(0)

    teachers_id = map(lambda t: t['id'], resp_json['teacher'])

    return teachers_id

def keep_teachers(ids):
    teacher_info_url = HAOKE_HOST + "/haoke/keep/teacher"
    header = ['access_token: %s' % str(access_token)]
    for i in ids:
        url = teacher_info_url
        params =  '{"teacher" : %d}' % i
        resp = curl_post(url, header, params)
        resp_json = string_to_json(resp)
        error_code = get_error_code(resp_json)
        if valid_keep_error(error_code) is True:
            break

        if valid_system_error(error_code) is True:
            print "Keep teachers System Error: ", url
            sys.exit(0)

        if str(resp_json['result']) == "success":
            print "Keep teacher id: %d is success." % i
        else:
            print "Keep teacher id: %d is failed." % i

def unkeep_teachers(ids):
    cancel_teacher_url = HAOKE_HOST + "/haoke/keep/cancel?teacher=%d"
    header = ['access_token: %s' % str(access_token)]
    for i in ids:
        url = cancel_teacher_url % i
        resp = curl_delete(url, header)
        resp_json = string_to_json(resp)
        error_code = get_error_code(resp_json)
        if valid_keep_cancel_error(error_code) is True:
            break

        if valid_system_error(error_code) is True:
            print "Keep teachers System Error: ", url
            sys.exit(0)

        if str(resp_json['result']) == "success":
            print "Keep teacher id: %d cancel is success." % i
        else:
            print "Keep teacher id: %d cancel is failed." % i


def teachers_of_keep():
    keep_url = HAOKE_HOST + "/haoke/keep?access_token=%s&curpage=%s&pagesize=%s"
    ok = True

    for i in range(START, END):
        url = keep_url % (str(access_token), i, PAGESIZE)
        resp = curl_get(url)
        resp_json = string_to_json(resp)
        error_code = get_error_code(resp_json)
        if valid_keep_null(error_code) is True:
            break
        else:
            if valid_system_error(error_code) is True:
                print "System Error: ", url
                ok = False
                break

        valid, key = valid_teachers_keys(resp_json)
        if valid is False:
            ok = False
            print "Get Error:", url
            print "    The %s is not exist." % key
            print "Json: ", resp_json

    if ok:
        print "List of Keep Teachers is OK!"
    else:
        print "List of Keep Teachers is NOT!"

def user_feedback():
    url = HAOKE_HOST + '/haoke/idea/feedback'
    params = '{"name":"庆伟", "phone":"1889992211", "content":"我们都很喜欢！"}'
    header = []
    resp = curl_post(url, header, params)
    resp_json = string_to_json(resp)
    error_code = get_error_code(resp_json)
    if valid_user_null(error_code) is True:
        print 'Username is exist.'
        print "User feedback is failed."
        sys.exit(0)
    else:
        if valid_system_error(error_code) is True:
            print "User feedback is failed."
            print "System Error: ", url
            sys.exit(0)
    valid, key = valid_result_keys(resp_json)
    if valid is False:
        print "Get Error:", url
        print "    The %s is not exist." % key
        print "User feedback is failed."
        sys.exit(0)
    print "User feedback is Success."

def do_teachers():
    ids = get_teachers_id()
    keep_teachers(ids)
    teachers_of_keep()
    unkeep_teachers(ids)

def do_user():
    get_user_info()
    update_user_info()

def user_access():
    user_register()
    user_login()
    do_user()
    do_teachers()
    user_logout()
    user_feedback()

def teachers_with_category(category):
    category_url = HAOKE_HOST + "/haoke/teacher?category=%s&curpage=%s&pagesize=%s"
    ok = True

    for i in range(START, END):
        url = category_url % (category, i, PAGESIZE)
        resp = curl_get(url)
        resp_json = string_to_json(resp)
        error_code = get_error_code(resp_json)
        if valid_record_null(error_code) is True:
            break
        else:
            if valid_system_error(error_code) is True:
                print "System Error: ", url
                ok = False
                break

        valid, key = valid_teachers_keys(resp_json)
        if valid is False:
            ok = False
            print "Get Error:", url
            print "    The %s is not exist." % key

    if ok:
        print "Teachers %s is OK!" % category
    else:
        print "Teachers: %s, is NOT!" % category


def teachers_with_category_and_sort(category,sort):
    category_and_sort_url = HAOKE_HOST + "/haoke/teacher?category=%s&type=%s&curpage=%s&pagesize=%s"
    ok = True

    for i in range(START, END):
        url = category_and_sort_url % (category, sort, i, PAGESIZE)
        resp = curl_get(url)
        print resp
        resp_json = string_to_json(resp)
        error_code = get_error_code(resp_json)
        if valid_record_null(error_code) is True:
            break
        else:
            if valid_system_error(error_code) is True:
                print "System Error: ", url
                ok = False
                break
        valid, key = valid_teachers_keys(resp_json)
        if valid is False:
            ok = False
            print url
            print "    The %s is not exist." % key
    if ok:
        print "Teachers: %s ,and Type: %s, is OK!" % (category, sort)
    else:
        print "Teachers: %s ,and Type: %s, is NOT!" % (category, sort)

def teachers_search(name):
    teacher_search_url = HAOKE_HOST + "/haoke/teacher?name=%s&curpage=%s&pagesize=%s"
    ok = True
    for i in range(START, END):
        url = teacher_search_url % (name, i, PAGESIZE)
        resp = curl_get(url)
        resp_json = string_to_json(resp)
        error_code = get_error_code(resp_json)
        if valid_record_null(error_code) is True:
            break
        else:
            if valid_system_error(error_code) is True:
                print "System Error: ", url
                ok = False
                break

        valid, key = valid_teachers_keys(resp_json)
        if valid is False:
            ok = False
            print url
            print "    The %s is not exist." % key
        get_teacher_info(resp_json)

    if ok:
        print "Courses Seach Name: %s, is OK!" % (name)
    else:
        print "Courses Seach Name: %s, is NOT!" % (name)

def get_teacher_info(resp_json):
    teacher_info_url = HAOKE_HOST + "/haoke/teacher?id=%d"
    ok = True
    teachers = resp_json['teacher']
    for t in teachers:
        url =  teacher_info_url % t['id']
        resp = curl_get(url)
        resp_json = string_to_json(resp)
        error_code = get_error_code(resp_json)

        if valid_record_null(error_code) is True:
            break
        else:
            if valid_system_error(error_code) is True:
                print "System Error: ", url
                ok = False
                break

        valid, key = valid_teacher_keys(resp_json)
        if valid is False:
            ok = False
            print url
            print "    The %s is not exist." % key
    if ok:
        print "Teacher info without token is OK!"
    else:
        print "Teacher info without token is NOT!"

def get_teacher_info_with_token():

    teacher_info_url = HAOKE_HOST + "/haoke/teacher?id=%d"
    ok = True
    teachers = resp_json['teacher']
    for t in teachers:
        url =  teacher_info_url % t['id']
        resp = curl_get(url)
        resp_json = string_to_json(resp)
        error_code = get_error_code(resp_json)

        if valid_record_null(error_code) is True:
            break
        else:
            if valid_system_error(error_code) is True:
                print "System Error: ", url
                ok = False
                break

        valid, key = valid_teacher_keys(resp_json)
        if valid is False:
            ok = False
            print url
            print "    The %s is not exist." % key
    if ok:
        print "Teacher info with token is OK!"
    else:
        print "Teacher info with token is NOT!"

def valid_teacher_keys(resp_json):
    teacher_keys = ['username', 'courseCount', 'describe', 'sex', 'intro', 'createTime', 'begood', 'name', 'imageUrl', 'id', 'age', 'followed']
    for t in teacher_keys:
        if not resp_json.has_key(t):
            return (False, k)

    return (True, None)

def valid_teachers_keys(resp_json):
    teachers_keys = ['count', 'teacher']
    teacher_keys = ['username', 'courseCount', 'describe', 'sex', 'intro', 'createTime', 'begood', 'name', 'imageUrl', 'id', 'age', 'followed']
    for k in teachers_keys:
        if not resp_json.has_key(k):
            return (False, k)
    for teacher in resp_json['teacher']:
        for k in teacher_keys:
            if not teacher.has_key(k):
                return (False, k)

    return (True, None)

def teachers():
    for c in CATEGORIES:
        teachers_with_category(c)

    #sort_keys = get_sort_keys()
    #for c in CATEGORIES:
    #    for s in sort_keys:
    #        teachers_with_category_and_sort(c,s)

    search_keys = ['臧菲菲','张宽宽', '李哇哇']
    for name in search_keys:
        teachers_search(name)

def courses_with_category(category):
    category_url = HAOKE_HOST + "/haoke/course?category=%s&curpage=%s&pagesize=%s"
    ok = True

    for i in range(START, END):
        url = category_url % (category, i, PAGESIZE)
        resp = curl_get(url)
        resp_json = string_to_json(resp)
        error_code = get_error_code(resp_json)
        if valid_record_null(error_code) is True:
            break
        else:
            if valid_system_error(error_code) is True:
                print "System Error: ", url
                ok = False
                break

        valid, key = valid_courses_keys(resp_json)
        if valid is False:
            ok = False
            print "The %s is not exist." % key
            print "Url:", url

    if ok:
        print "Courses %s is OK!" % category
    else:
        print "Courses %s, is NOT!" % category

def courses_with_category_and_sort(category,sort):
    category_and_sort_url = HAOKE_HOST + "/haoke/course?category=%s&type=%s&curpage=%s&pagesize=%s"
    ok = True

    for i in range(START, END):
        url = category_and_sort_url % (category, sort, i, PAGESIZE)
        resp = curl_get(url)
        resp_json = string_to_json(resp)
        error_code = get_error_code(resp_json)
        if valid_record_null(error_code) is True:
            break
        else:
            if valid_system_error(error_code) is True:
                print "System Error: ", url
                ok = False
                break

        valid, key = valid_courses_keys(resp_json)
        if valid is False:
            ok = False
            print "The %s is not exist." % key
            print "Url: ", url
    if ok:
        print "Courses: %s ,and Type: %s, is OK!" % (category, sort)
    else:
        print "Courses: %s ,and Type: %s, is NOT!" % (category, sort)

def courses_search(name):
    course_search_url = HAOKE_HOST + "/haoke/course?name=%s&curpage=%s&pagesize=%s"
    ok = True
    for i in range(START, END):
        url = course_search_url % (name, i, PAGESIZE)
        resp = curl_get(url)
        resp_json = string_to_json(resp)
        error_code = get_error_code(resp_json)
        if valid_record_null(error_code) is True:
            break
        else:
            if valid_system_error(error_code) is True:
                print "System Error: ", url
                ok = False
                break

        valid, key = valid_courses_keys(resp_json)
        if valid is False:
            ok = False
            print url
            print "    The %s is not exist." % key
    if ok:
        print "Courses Seach Name: %s, is OK!" % (name)
    else:
        print "Courses Seach Name: %s, is NOT!" % (name)

def valid_courses_keys(resp_json):
    course_keys = ['count', 'courseList']
    courseList_keys = ['heat', 'video', 'name', 'time', 'imageUrl', 'teacher', 'id', 'introduce', 'createTime']
    courseList_teacher_keys = ['username', 'describe', 'id', 'name']

    for k in course_keys:
        if not resp_json.has_key(k):
            return (False, k)
    for course in resp_json['courseList']:
        for k in courseList_keys:
            if not course.has_key(k):
                return (False, k)
        for k in courseList_teacher_keys:
            if not course['teacher'].has_key(k):
                return (False, k)

    return (True, None)

def courses():
    for c in CATEGORIES:
        courses_with_category(c)

    sort_keys = get_sort_keys()
    for c in CATEGORIES:
        for s in sort_keys:
            courses_with_category_and_sort(c,s)

    search_keys = ['真人实战','留学']
    for name in search_keys:
        courses_search(name)
#        print "Course search name: %s is OK!" % name

def header_info():
    header_url = HAOKE_HOST + "/haoke/course/head"
    ok = True
    resp = curl_get(header_url)
    resp_json = string_to_json(resp)
    error_code = get_error_code(resp_json)
    if valid_system_error(error_code) is True:
        print "System Error: ", url
        ok = False

    valid, key = valid_courses_keys(resp_json)
    if valid is False:
        ok = False
        print "    The %s is not exist." % key

    if ok:
        print "Courses Header is OK!"
    else:
        print "Courses Header is NOT!"

def teacher_chosen():
    teacher_url = HAOKE_HOST + "/haoke/teacher"
    ok = True
    resp = curl_get(teacher_url)
    resp_json = string_to_json(resp)
    error_code = get_error_code(resp_json)
    if valid_system_error(error_code) is True:
        print "System Error: ", url
        ok = False

    valid, key = valid_teachers_keys(resp_json)
    if valid is False:
        ok = False
        print "    The %s is not exist." % key

    if ok:
        print "Teachers Chosen is OK!"
    else:
        print "Teachers Chosen is NOT!"

def course_chosen():
    course_url = HAOKE_HOST + "/haoke/course"
    ok = True
    resp = curl_get(course_url)
    resp_json = string_to_json(resp)
    error_code = get_error_code(resp_json)
    if valid_system_error(error_code) is True:
        print "System Error: ", url
        ok = False

    valid, key = valid_courses_keys(resp_json)
    if valid is False:
        ok = False
        print "    The %s is not exist." % key

    if ok:
        print "Courses Chosen is OK!"
    else:
        print "Courses Chosen is NOT!"


def haoke_home():
    header_info()
    teacher_chosen()
    course_chosen()

if __name__ == "__main__":
    haoke_home()
    courses()
    teachers()
    user_access()
