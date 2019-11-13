(ns org.galaxyproject.clj-blend.tools
  "Run Galaxy tools through the remote API"
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]
            [clojure.string :as string]
            [org.galaxyproject.clj-blend.histories :as histories]
            [org.galaxyproject.clj-blend.util :as util]))

(defn list-tools
  [client & tool-id]
  (json/read-str
   (:body (client/get (str (client :url) "tools")
              {:headers {"x-api-key" (client :api-key)}}))
   :key-fn util/key-fn))

(defn run-tool
  "Run a remote tool on Galaxy server"
  [client tool-id params & {:keys [history-id]}]
  (json/read-str
   (:body (client/post (str (client :url) "tools")
              {:headers {"x-api-key" (client :api-key)}}))
   :key-fn util/key-fn))

(defn upload-to-history
  "Upload a file via URL to a Galaxy history, defaulting to the current."
  [client file-url dbkey ftype & {:keys [history-id display-name]}]
  (run-tool client "upload1"
            {:file-type (name ftype)
             :dbkey (name dbkey)
             "files_0|url_paste" file-url
             "files_0|NAME" (or display-name (last (string/split file-url #"/")))}
            :history-id history-id))
