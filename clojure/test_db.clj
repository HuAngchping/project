(ns small_repair.test.test_db
  (:use [clojure.test])
  (:require [small_repair.sql.repair :as repair]))

(deftest test-insert
  (testing "insert into value..."
    (let [results (repair/create! "testvalue")])))