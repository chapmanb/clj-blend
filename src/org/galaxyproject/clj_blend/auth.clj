(ns org.galaxyproject.clj-blend.auth
  "Galaxy Authentication."
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]
            [org.galaxyproject.clj-blend.util :as util]))

(defn authenticate
  [server user]
  (let [b64 (util/b64-encode-str (str (:email user) ":" (:password user)))]
    (json/read-str
     (:body (client/get (str (:api-root server) "authenticate/baseauth")
                        {:headers {"Authorization" (str "Basic " b64)}}))
     :key-fn util/key-fn)))
