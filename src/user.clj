(ns user
  (:import java.util.Base64)
  (:require [reloaded.repl :refer [system init start stop go reset reset-all]]
            [clj-http.client :as client]
            [clojure.data.json :as json]
            [org.galaxyproject.clj-blend.auth :as auth]
            [org.galaxyproject.clj-blend.users :as users]
            [org.galaxyproject.clj-blend.histories :as histories]
            [org.galaxyproject.clj-blend.util :as util]))


(comment
  
  (util/b64-encode-str "admin@galaxy.org:admin")
  
  (def server {:api-root "http://localhost:49999/api/"})
  
  (def user {:email "admin@galaxy.org" :password "admin"})
  
  (def api-key (:api-key (auth/authenticate server user)))

  (def client {:url (:api-root server) :api-key api-key})

  (try
    (users/get-current-user client)
    (catch Exception e (:cause (Throwable->map e))))

  (try
    (users/get-users client)
    (catch Exception e (:cause (Throwable->map e))))

  (try
    (users/get-user-by-id client (:id (get-current-user client)))
    (catch Exception e (:cause (Throwable->map e))))

  (try
    (histories/get-histories client)
    (catch Exception e (:cause (Throwable->map e))))
  
  (try
    (histories/get-history-most-recently-used client)
    (catch Exception e (:cause (Throwable->map e))))

  (try
    (histories/get-history-contents client (:id (get-history-most-recently-used client)))
    (catch Exception e (:cause (Throwable->map e))))
  )
    
