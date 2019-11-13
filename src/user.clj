(ns user
  (:import java.util.Base64)
  (:require [reloaded.repl :refer [system init start stop go reset reset-all]]
            [org.galaxyproject.clj-blend.auth :as auth]
            [org.galaxyproject.clj-blend.users :as users]
            [org.galaxyproject.clj-blend.histories :as histories]
            [org.galaxyproject.clj-blend.util :as util]))


(comment
  
  (util/b64-encode-str "admin@galaxy.org:admin")
  
  (def server {:api-root "http://localhost:8080/api/"})
  
  (def user {:email "admin@galaxy.org" :password "admin"})
  
  (def api-key (:api-key (auth/authenticate server user)))

  (def client {:url (:api-root server) :api-key api-key})

  (def newuser0 {:username "newuser0" :email "newuser0@example.org" :password "helloworld0"})
  (def newuser1 {:username "newuser0" :email "newuser1@example.org" :password "helloworld1"})
  (def user-to-delete {:username "deleteme" :email "deleteme@example.org" :password "deleteme"})

  (try
    (users/get-current-user client)
    (catch Exception e (:cause (Throwable->map e))))

  (try
    (users/get-users client)
    (catch Exception e (:cause (Throwable->map e))))

  (try
    (users/get-user-by-id client (:id (users/get-current-user client)))
    (catch Exception e (:cause (Throwable->map e))))

  (try
    (users/create-user client newuser0)
    (catch Exception e (:cause (Throwable->map e))))

  (try
    (users/create-user client newuser1)
    (catch Exception e (:cause (Throwable->map e))))

  (try
    (users/update-user client (:id (users/get-user-by-username client "newuser0")) {:password "goodbyeworld"})
    (catch Exception e (:cause (Throwable->map e))))

  (try
    (users/create-user client user-to-delete)
    (users/delete-user client (:id (users/get-user-by-username client "deleteme")))
    (catch Exception e (:cause (Throwable->map e))))
  
  (try
    (histories/get-histories client)
    (catch Exception e (:cause (Throwable->map e))))
  
  (try
    (histories/get-history-most-recently-used client)
    (catch Exception e (:cause (Throwable->map e))))

  (try
    (histories/get-history-contents client (:id (histories/get-history-most-recently-used client)))
    (catch Exception e (:cause (Throwable->map e))))
  
  )
    
