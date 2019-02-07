(ns user
  (:import java.util.Base64)
  (:require [reloaded.repl :refer [system init start stop go reset reset-all]]
            [org.httpkit.client :as http]
            [clojure.data.json :as json]))

(defn key-fn [key]
  (keyword (clojure.string/replace key "_" "-")))

(defn b64-encode-str [to-encode]
  (.encodeToString (Base64/getEncoder) (.getBytes to-encode)))

(defn authenticate
  [server user]
  (let [b64 (b64-encode-str (str (:email user) ":" (:password user)))])
  (json/read-str
   (:body @(http/get (str (:api-root server) "authenticate/baseauth")
                     {:headers {"Authorization" (str "Basic " b64)}}))
   :key-fn key-fn))

(defn get-current-user
  [client]
  (json/read-str
   (:body @(http/get (str (client :url) "users/current")
              {:headers {"x-api-key" (client :api-key)}}))
   :key-fn key-fn))

(comment
  
  (b64-encode-str "admin@galaxy.org:admin")
  
  (def server {:api-root "http://localhost:49999/api/"})
  
  (def user {:email "admin@galaxy.org" :password "admin"})
  
  (def api-key (:api-key (authenticate server user)))

  (def client {:url (:api-root server) :api-key api-key})
  
  (get-current-user client)

  )
    
