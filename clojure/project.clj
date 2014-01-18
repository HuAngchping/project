(defproject small-customer-service "0.1.0"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :repositories {"local" "http://develop.haotiben.com:8002/nexus-2.0.5/content/groups/public"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5"]
                 [ring/ring-json "0.2.0"]
                 [com.taoensso/carmine "2.1.0"]
                 [org.clojure/tools.logging "0.2.6"]
                 [ring/ring-jetty-adapter "1.1.6"]
                 [lq/lucene-queryparser "4.2.0"]
                 [log4j/log4j "1.2.16" :exclusions [javax.mail/mail
                                                     javax.jms/jms
                                                     com.sun.jdmk/jmxtools
                                                     com.sun.jmx/jmxri]]
                  [korma "0.3.0-RC4"]
                  [clj-http "0.7.3"]
                  [IK/IK "2012"]
                  [org.apache.lucene/lucene-core "4.0.0"]
                  [org.clojars.kbarber/postgresql "9.2-1002.jdbc4"]
                  [lobos "1.0.0-20120828.002929-9"]]
  :plugins [[lein-ring "0.8.5"]]
  :ring {:handler server.server/app}
  :main server.server
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.5"]]}})
