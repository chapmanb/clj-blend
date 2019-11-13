(ns org.galaxyproject.clj-blend.users
  "Retrieve information on Galaxy users."
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]
            [org.galaxyproject.clj-blend.util :as util]))

(defn get-users
  [client]
  (json/read-str
   (:body (client/get (str (client :url) "users")
                      {:headers {"x-api-key" (client :api-key)}}))
   :key-fn util/key-fn))

(defn get-current-user
  [client]
  (json/read-str
   (:body (client/get (str (client :url) "users/current")
              {:headers {"x-api-key" (client :api-key)}}))
   :key-fn util/key-fn))

(defn get-user-by-id
  [client id]
  (json/read-str
   (:body (client/get (str (client :url) "users/" id)
              {:headers {"x-api-key" (client :api-key)}}))
   :key-fn util/key-fn))

(defn get-user-by-username
  [client username]
  (first (filter #(= (:username %) username) (get-users client))))

(defn create-user
  [client user]
  (json/read-str
   (:body (client/post (str (client :url) "users/")
                       {:headers {"x-api-key" (client :api-key)}
                        :content-type :json
                        :body (json/write-str user)}))
   :key-fn util/key-fn))

(defn update-user
  [client user-id updated-attributes]
  (json/read-str
   (:body (client/put (str (client :url) "users/" user-id)
                       {:headers {"x-api-key" (client :api-key)}
                        :content-type :json
                        :body (json/write-str updated-attributes)}))
   :key-fn util/key-fn))

(defn delete-user
  [client user-id]
  (json/read-str
   (:body (client/delete (str (client :url) "users/" user-id)
                         {:headers {"x-api-key" (client :api-key)}}))
   :key-fn util/key-fn))
