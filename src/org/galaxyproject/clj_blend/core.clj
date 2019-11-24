(ns org.galaxyproject.clj-blend.core
  "Top level Galaxy interaction"
  (:require [clj-http.client :as client]
            [org.galaxyproject.clj-blend.users :as users]
            [org.galaxyproject.clj-blend.histories :as histories]
            [org.galaxyproject.clj-blend.tools :as tools]))


(defn authenticate
  [server user]
  (client/get (str (:api-root server) "authenticate/baseauth")
              :headers {"Authorization" (str "Basic " )}))


(defn get-client
  [galaxy-url api-key]
  {:url (str galaxy-url "/api/") :api-key api-key})

(def get-user-info users/get-current-user)

(defn list-histories
  [client]
  [(histories/get-history-most-recently-used client)])

(def get-history-contents-by-type histories/get-history-contents-by-type)
(def get-history-contents-by-id histories/get-history-contents-by-id)

(def download-history-contents histories/download-history-contents)
(def upload-to-history tools/upload-to-history)

(comment
  (def client (get-client "http://localhost:8080" "admin"))
  )
