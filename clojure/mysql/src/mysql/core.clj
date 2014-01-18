(ns mysql.core
    (:require [clojure.java.jdbc :as sql]))

(def db {:classname "com.mysql.jdbc.Driver"
         :subprotocol "mysql"
         :subname "//127.0.0.1:3306/parent"
         :user "root"
         :password "root"})

(defn list-news []
  (sql/with-connection db
    (sql/with-query-results rows
      ["select * from PARENT_NEWS"]
      (println (count rows)))))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
