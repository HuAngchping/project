(ns customer.models.repair-bills
  (:use [korma.db]
        [korma.core])
  (:use [database.database]
        [utils.common])
  (:require [clojure.string :as str]
            [customer.search.core :as s]
            [clj-http.client :as client]
            [cheshire.core :as json]
            [utils.log :as log]))

(defstruct repair-bill :repair-id :repair-type :real-name :mobile :gender :spare-mobile :province :city :county :address :car-owner :car-owner-tel :car-vin :car-type :car-number :car-trip :car-buy-date :customer-desc :car-info-id :remark :cs-id :cs-name :create-at)

(defn convert-docs2-map [docs]
  (log/info (str "[convert docs]==>") docs)

  (into []
    (map
      (fn [doc]
        (into {}
          (map
            (fn [field] [(keyword (.name field)) (.stringValue field)] )
            (.getFields doc))))
      docs)
    )
  )

(defn update-repair-bills!
  "更新维修接待单"
  [{:keys [repair_num repair_type customer_name customer_tel customer_gender customer_backup_tel province city county address owner_name owner_tel frame_num vehicle_type plate_num km purchase_date failure_desc complete_vehicle_id]} spanner_user_name spanner_user_username ]
  (transaction
   (exec-raw ["update repair_bills set repair_type=?,customer_name=?,customer_tel=?,customer_gender=?,customer_backup_tel=?,province=?,city=?,county=?,address=?,owner_name=?,owner_tel=?,frame_num=?,vehicle_type=?,plate_num=?,km=?,purchase_date=?,failure_desc=?,complete_vehicle_id=?,service_name=?,service_username=? where repair_num=?" [ repair_type customer_name customer_tel customer_gender customer_backup_tel province city county address owner_name owner_tel frame_num vehicle_type plate_num km purchase_date failure_desc complete_vehicle_id spanner_user_name spanner_user_username repair_num]])))

(defn repeat-repair-id!
  "检查repair-id是否重复"
  [repair-num]
  (transaction
   (exec-raw ["insert into repair_bills (repair_num) values (?)" [repair-num]])))

(defn repair-bills-id!
  "通过id查询维修接待单"
  [repair-num]
  (exec-raw ["select repair_num,repair_type,customer_name,customer_tel,customer_gender,customer_backup_tel,province,city,county,address,owner_name,owner_tel,frame_num,vehicle_type,plate_num,km,purchase_date,failure_desc,complete_vehicle_id,service_name,service_username,to_char(created_at,'YYYY-MM-DD HH24:MI:SS') as created_at from repair_bills where repair_num=?" [(str repair-num)]] :results))

(defn search-repair-bills-mobile!
  "通过电话查找维修接待单"
  [mobile]
  ;(exec-raw ["select * from repair_bills where mobile=? limit 5" [(str mobile)]] :results)
  (json/parse-string (:body (client/get (str (getParam "repair_system" "http://192.168.2.231:8181/repair") "/search?t=" mobile) )))
)

(defn vehicle-id!
  "通过电话查找维修接待单"
  [vehicle-id]
  (log/info "repair-bills vehicle-id=>" vehicle-id )
  ;(exec-raw ["select * from repair_bills where mobile=? limit 5" [(str mobile)]] :results)
  (json/parse-string (:body (client/get (str (getParam "repair_system" "http://192.168.2.231:8181/repair") "/vehicle?id=" vehicle-id) )))
)

(defn  help-write-index
  "将维修接待单创建索引"
  [{:keys [repair_num repair_type customer_name customer_tel customer_gender customer_backup_tel province city county address owner_name owner_tel frame_num vehicle_type plate_num km purchase_date failure_desc complete_vehicle_id]} spanner_user_name spanner_user_username status create_in]
  (log/info (str "[repair index field]=>") repair_num " " repair_type " " customer_name " " customer_tel " " customer_gender " " customer_backup_tel " " province " " city " " county " " address " " owner_name " " owner_tel " " frame_num " " vehicle_type " " plate_num " " km " " purchase_date " " failure_desc " " complete_vehicle_id " "  spanner_user_name " " spanner_user_username " " create_in " " "*")
    (let [dir_dir (s/create-directory (getParam "index_dir" "/tmp/customer_index"))
        t_doc (s/create-document-with-field
               (s/create-field-of-index "fulltext"
                                        (str repair_num " " repair_type " " customer_name " " customer_tel " " customer_gender " " customer_backup_tel " " province " " city " " county " " address " " owner_name " " owner_tel " " frame_num " " vehicle_type " " plate_num " " km " " purchase_date " " failure_desc " " complete_vehicle_id " "  spanner_user_name " " spanner_user_username " " create_in " " "*"))
               (s/create-field-of-stored "repair_num" repair_num)
               (s/create-field-of-stored "repair_type" repair_type)
               (s/create-field-of-stored "customer_name" customer_name)
               (s/create-field-of-stored "customer_tel" customer_tel)
               (s/create-field-of-stored "customer_gender" customer_gender)
               (s/create-field-of-stored "customer_backup_tel" customer_backup_tel)
               (s/create-field-of-stored "province" province)
               (s/create-field-of-stored "city" city)
               (s/create-field-of-stored "county" county)
               (s/create-field-of-stored "address" address)
               (s/create-field-of-stored "owner_name" owner_name)
               (s/create-field-of-stored "owner_tel" owner_tel)
               (s/create-field-of-stored "frame_num" frame_num)
               (s/create-field-of-stored "vehicle_type" vehicle_type)
               (s/create-field-of-stored "plate_num" plate_num)
               (s/create-field-of-stored "km" km)
               (s/create-field-of-stored "purchase_date" purchase_date)
               (s/create-field-of-stored "failure_desc" failure_desc)
               (s/create-field-of-stored "complete_vehicle_id" complete_vehicle_id)
;               (s/create-field-of-stored "service_uid" spanner_user_uid)
               (s/create-field-of-stored "create_in" create_in)
               (s/create-field-of-stored "service_name" spanner_user_name)
               (s/create-field-of-stored "service_username" spanner_user_username)
               (s/create-field-of-stored "status" status)
               )]
    (s/write-index-with-doc! dir_dir t_doc)
    )
  )

(defn update-index
  [repair-id status]
  (log/info "update lucene index. repair-id:" repair-id " status:" status)
  (let [repair-bill (repair-bills-id! repair-id)
        dir (s/create-directory (getParam "index_dir" "/tmp/customer_index"))
        analyzer (s/create-analyzer)
        q1 (s/create-query "fulltext" repair-id analyzer)
        q2 (s/add-filter-to-query q1
             (s/create-filter-of-exist "repair_num" repair-id)
             )]
    (s/del-index-with-query dir q1)
    (s/del-index-with-query dir q2)
    (let [bill (first repair-bill) ]
      (log/info (str "[update-index]=>")  bill (:created_at bill) (:service_name bill) (:service_username bill) status )
      (help-write-index bill (:service_name bill) (:service_username bill) status (:created_at bill)))))

(defn search-repair-bill
  [scope status keyword pagenum pagesize spanner_user_name]
  (println scope status keyword pagenum pagesize spanner_user_name)
  (let [dir (s/create-directory (getParam "index_dir" "/tmp/customer_index"))
        analyzer (s/create-analyzer)
        q1 (s/create-query "fulltext" keyword analyzer)
        q2 (s/add-filter-to-query q1
             (s/create-filter-of-exist "spanner_user_name" spanner_user_name)
             (s/create-filter-of-exist "status" status))
        q3 (s/add-filter-to-query q1
             (s/create-filter-of-exist "status" status))
        q4 (s/add-filter-to-query q1
             (s/create-filter-of-exist "spanner_user_name" status))
        reader (s/create-index-reader dir)
        result1 (s/search-paging q1 pagenum pagesize reader "create_in")
        result2 (s/search-paging q2 pagenum pagesize reader "create_in")
        result3 (s/search-paging q3 pagenum pagesize reader "create_in")
        result4 (s/search-paging q4 pagenum pagesize reader "create_in")
        docs1 (s/get-docs reader (:docs result1))
        docs2 (s/get-docs reader (:docs result2))
        docs3 (s/get-docs reader (:docs result3))
        docs4 (s/get-docs reader (:docs result4))]
    (if (= scope "all")
      (if (= status "all")
        (convert-docs2-map docs1)
        (convert-docs2-map docs3))
      (if (= status "all")
        (convert-docs2-map docs4)
        (convert-docs2-map docs2)
        )
  )))

(defn init-clear!
  []
  (transaction
   (exec-raw ["truncate table repair_bills"])
   (exec-raw ["truncate table repair_status"]))
  (doseq [f (.listFiles (java.io.File. (getParam "index_dir" "/tmp/customer_index")))] (.delete f))

)
