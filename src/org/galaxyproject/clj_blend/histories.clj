(ns org.galaxyproject.clj-blend.histories
  "Retrieve Galaxy history information."
  (:use [clojure.java.io])
  (:require [clojure.string :as string]
            [clj-http.client :as client]
            [clojure.data.json :as json]
            [org.galaxyproject.clj-blend.util :as util]
            [fs.core :as fs]))

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

(defn get-history-by-id
  [client history-id]
  (json/read-str
   (:body (client/get (str (client :url) "histories/" history-id)
                      {:headers {"x-api-key" (client :api-key)}}))
   :key-fn util/key-fn))

(defn create-history
  [client history]
  (json/read-str
   (:body (client/post (str (client :url) "histories")
                       {:headers {"x-api-key" (client :api-key)}
                        :content-type :json
                        :body (json/write-str history)}))
   :key-fn util/key-fn))

(defn delete-history
  [client history-id]
  (json/read-str
   (:body (client/delete (str (client :url) "histories/" history-id)
                         {:headers {"x-api-key" (client :api-key)}}))
   :key-fn util/key-fn))

(defn get-history-contents
  "Retrieve history datasets, flattened into Clojure maps."
  [client history-id]
  (json/read-str
   (:body (client/get (str (client :url) "histories/" history-id "/contents")
              {:headers {"x-api-key" (client :api-key)}}))
   :key-fn util/key-fn))

(defn get-history-contents-by-id
  "Retrieve a history dataset converted into a clojure map."
  [client history-id content-id]
  (json/read-str
   (:body (client/get (str (client :url) "histories/" history-id "/contents/" content-id)
              {:headers {"x-api-key" (client :api-key)}}))
   :key-fn util/key-fn))

(defn get-history-status
  [client history-id]
  (select-keys (get-history-by-id client history-id) [:state :state-details]))

(defn- is-ftype?
  "Check if a dataset is the given filetype, cleanly handling keywords"
  [ftype dataset]
  (= (keyword ftype)
     (keyword (:data-type dataset))))


(defn get-history-contents
  [client history-id]
  (json/read-str
   (:body (client/get (str (client :url) "histories/" history-id "/contents")
                      {:headers {"x-api-key" (client :api-key)}}))
   :key-fn util/key-fn))

(defn get-history-contents-by-type
  "Retrieve datasets from the current active history by filetype."
  [client history-id ftype]
  (filter (partial is-ftype? ftype)
          (get-history-contents client history-id)))

