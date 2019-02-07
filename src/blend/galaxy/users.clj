(ns blend.galaxy.users
  "Retrieve information on Galaxy users."
  (:require [org.httpkit.client :as http]
            [clojure.data.json :as json]))

(defn get-user-info
  "Retrieve user information for current API client."
  [client]
  (letfn [(user-to-map [user]
            {:username (.getUsername user)
             :email (.getEmail user)})]
    (-> client
        .getUsersClient
        (.showUser "current")
        user-to-map)))

(defn get-users
  [client]
  )

(defn get-current-user
  [client]
  (json/read-str
   (:body @(http/get (str (client :url) "users/current")
              {:headers {"x-api-key" (client :api-key)}}))
   :key-fn #(keyword (clojure.string/replace % "_" "-"))))
