(ns user
  (:import java.util.Base64)
  (:require [reloaded.repl :refer [system init start stop go reset reset-all]]
            [org.galaxyproject.clj-blend.auth :as auth]
            [org.galaxyproject.clj-blend.users :as users]
            [org.galaxyproject.clj-blend.histories :as histories]
            [org.galaxyproject.clj-blend.tools :as tools]
            [org.galaxyproject.clj-blend.util :as util]))
  
(util/b64-encode-str "admin@galaxy.org:admin")  
(def server {:api-root "http://localhost:8080/api/"})
(def admin-user {:email "admin@galaxy.org" :password "admin"})
(def admin-user-api-key (:api-key (auth/authenticate server admin-user)))
(def client {:url (:api-root server) :api-key admin-user-api-key})
(def new-user-0 {:username "newuser0" :email "newuser0@example.org" :password "helloworld0"})
(def new-user-1 {:username "newuser1" :email "newuser1@example.org" :password "helloworld1"})
(def user-to-delete {:username "deleteme" :email "deleteme@example.org" :password "deleteme"})

(comment

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
    (users/delete-user client (:id (users/get-user-by-username client (:username new-user-0))))
    (catch Exception e (:cause (Throwable->map e))))

  (try
    (users/delete-user client (:id (users/get-user-by-username client (:username new-user-1))))
    (catch Exception e (:cause (Throwable->map e))))
  
  (try
    (users/create-user client new-user-0)
    (catch Exception e (:cause (Throwable->map e))))

  (try
    (users/create-user client new-user-1)
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
    (histories/get-history-by-id client (:id (first (histories/get-histories client))))
    (catch Exception e (:cause (Throwable->map e))))

  (try
    (histories/get-history-status client (:id (first (histories/get-histories client))))
    (catch Exception e (:cause (Throwable->map e))))

  (try
    (histories/delete-history client (:id (histories/create-history client {:name "New History"})))
    (catch Exception e (:cause (Throwable->map e))))
  
  (try
    (histories/get-history-most-recently-used client)
    (catch Exception e (:cause (Throwable->map e))))

  (try
    (histories/get-history-contents client (:id (histories/get-history-most-recently-used client)))
    (catch Exception e (:cause (Throwable->map e))))

  (try
    (map :id (flatten (map :elems (tools/list-tools client))))
    (catch Exception e (:cause (Throwable->map e))))

  (try
    (histories/get-history-contents-by-id client
                                         (:id (histories/get-history-most-recently-used client))
                                         (:id (first (histories/get-history-contents client (:id (histories/get-history-most-recently-used client))))))
    (catch Exception e (:cause (Throwable->map e))))
  
  (try
    (histories/download-history-contents client
                                         (:id (histories/get-history-most-recently-used client))
                                         (:dataset-id (first (histories/get-history-contents client (:id (histories/get-history-most-recently-used client)))))
                                         (str (System/getProperty "user.home") "/Downloads/" "test-download.txt"))
    (catch Exception e (:cause (Throwable->map e))))
  )
    
