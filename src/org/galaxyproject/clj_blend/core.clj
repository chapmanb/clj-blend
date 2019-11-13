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
  {:url galaxy-url :api-key api-key})

(def get-user-info users/get-current-user)

(def user users/get-current-user)

(defn list-histories
  [client]
  [(histories/get-current-history client)])

(def get-datasets-by-type histories/get-datasets-by-type)
(def get-dataset-by-id histories/get-dataset-by-id)

(def download-dataset histories/download-dataset)
(def upload-to-history tools/upload-to-history)

(comment
  (def galaxy-client (get-client "http://localhost:8080" "admin"))  )
