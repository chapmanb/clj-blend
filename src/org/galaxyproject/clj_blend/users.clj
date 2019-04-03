(ns org.galaxyproject.clj-blend.users
  "Retrieve information on Galaxy users."
  (:require [org.httpkit.client :as http]
            [clojure.data.json :as json]
            [blend.galaxy.util :as util]))

(defn get-users
  [client]
  (json/read-str
   (:body (client/get (str (client :url) "users")
                      {:headers {"x-api-key" (client :api-key)}}))
   :key-fn key-fn))

(defn get-current-user
  [client]
  (json/read-str
   (:body @(http/get (str (client :url) "users/current")
              {:headers {"x-api-key" (client :api-key)}}))
   :key-fn #(keyword (clojure.string/replace % "_" "-"))))

(defn get-user-by-id
  [client id]
  
