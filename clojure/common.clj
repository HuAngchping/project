(ns utils.common)

(defn getParam [param default]
  (if-let [value (System/getProperty param)]
    value
    default
    ))

(defn md5-sum
  "Generate a md5 checksum for the given string"
  [token]
  (let [hash-bytes
        (doto (java.security.MessageDigest/getInstance "MD5")
          (.reset)
          (.update (.getBytes token)))]
    (.toString
      (new java.math.BigInteger 1 (.digest hash-bytes))
      16))) ;Use base16 i.e. hex

(defn now
  "Generate current time"
  []
  (.getTime (java.util.Calendar/getInstance)))

(defn pretty-date
  "format date time -> MMM dd, yyyy"
  [date]
  (.format (java.text.SimpleDateFormat. "MMM dd, yyyy")
    (date)))

(defn mdy-date
  "Generate now -> pretty date"
  []
  (pretty-date (now)))

(defn rfc822-date
  [date]
  (let [f (java.text.SimpleDateFormat "EEE', 'dd' 'MMM' 'yyyy' 'HH:mm:ss' 'Z")]
    (.format f date)))

(defn get-random-id
  [length]
  (let [alpha-numeric "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"]
    (apply str (repeatedly length #(rand-nth alpha-numeric)))))

(defn currenttime
  []
  (System/currentTimeMillis))

(definline now-seconds
  []
  `(quot (System/currentTimeMillis) 1000))


(defn pid
  "Generate the current process's PID, as a String"
  []
  (or
    (System/getProperty "pid")
    (first
      (->
        (.getName (java.lang.management.ManagementFactory/getRuntimeMXBean))
        (.split "@")))))

(defn device-mac-bytes
  "deivce mac bytes array"
  []
  (if-let [ni (java.net.NetworkInterface/getByInetAddress (java.net.InetAddress/getLocalHost))]
    (.getHardwareAddress ni)))

(defn mac-bytes-to-string
  "mac device string"
  [mac-bytes]
  (let [v (apply vector (map #(Integer/toHexString (bit-and % 0xff)) mac-bytes))]
    (apply str (interpose ":" v))))

(defn string-bytes
  "string to byte array
  Return bytes array."
  [string]
  (.getBytes string))

(defn bytes-short
  "bytes to short integer, 2 btyes to short ,
  Return short integer."
  [bytes]
  (.getShort (java.nio.ByteBuffer/wrap bytes)))

(defn decimal-biginteger
  "convert java.math.Decimal -> java.match.BigInteger"
  [big-dec]
  (.toBigInteger big-dec)
  )
