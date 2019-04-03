(ns user
  (:import java.util.Base64)
  (:require [reloaded.repl :refer [system init start stop go reset reset-all]]
            [clj-http.client :as client]
            [clojure.data.json :as json]
            [org.galaxyproject.clj-blend.util :as util]))

(defn authenticate
  [server user]
  (let [b64 (util/b64-encode-str (str (:email user) ":" (:password user)))]
  (json/read-str
   (:body (client/get (str (:api-root server) "authenticate/baseauth")
                      {:headers {"Authorization" (str "Basic " b64)}}))
   :key-fn util/key-fn)))

(defn get-current-user
  [client]
  (json/read-str
   (:body (client/get (str (client :url) "users/current")
                      {:headers {"x-api-key" (client :api-key)}}))
   :key-fn util/key-fn))

(defn get-users
  [client]
  (json/read-str
   (:body (client/get (str (client :url) "users")
                      {:headers {"x-api-key" (client :api-key)}}))
   :key-fn util/key-fn))

(defn get-histories
  [client]
  (json/read-str
   (:body (client/get (str (client :url) "histories")
                      {:headers {"x-api-key" (client :api-key)}}))
   :key-fn util/key-fn))

(defn get-history-most-recently-used
  [client]
  (json/read-str
   (:body (client/get (str (client :url) "histories/most_recently_used")
                      {:headers {"x-api-key" (client :api-key)}}))
   :key-fn util/key-fn))

              

(comment
  
  (util/b64-encode-str "admin@galaxy.org:admin")
  
  (def server {:api-root "http://localhost:49999/api/"})
  
  (def user {:email "admin@galaxy.org" :password "admin"})
  
  (def api-key (:api-key (authenticate server user)))

  (def client {:url (:api-root server) :api-key api-key})

  (try
    (get-current-user client)
    (catch Exception e (:cause (Throwable->map e))))

  (try
    (get-users client)
    (catch Exception e (:cause (Throwable->map e))))

  (try
    (get-histories client)
    (catch Exception e (:cause (Throwable->map e))))
  
  (try
    (get-history-most-recently-used client)
    (catch Exception e (:cause (Throwable->map e))))

  )
    
