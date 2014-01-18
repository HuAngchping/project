(ns hello-world.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]))

(defn hello-user [id]
  (str "{username:" id "}"))

(defroutes app-routes
  (GET "/" [] "Hello World")
  ;(GET "/user/:id" [id] (str "<h1>Hello User " id "</h1>"))
  (GET "/user/:id" [id] (hello-user id))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
