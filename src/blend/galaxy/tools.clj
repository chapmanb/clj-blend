(ns blend.galaxy.tools
  "Run Galaxy tools through the remote API"
  (:import [com.github.jmchilton.blend4j.galaxy.beans ToolInputs History])
  (:require [clojure.string :as string]
            [blend.galaxy.histories :as histories]))

(defn run-tool
  "Run a remote tool on Galaxy server"
  [client tool-id params & {:keys [history-id]}]
  (let [clean-params (reduce (fn [coll [k v]]
                               (assoc coll (string/replace (name k) "-" "_") v))
                             {} params)
        tool-client (.getToolsClient client)
        upload-history (doto (History.)
                         (.setId 
                          (if (nil? history-id)
                            (:id (histories/get-current-history client))
                            history-id)))]
    (.create tool-client upload-history
             (ToolInputs. tool-id clean-params))))

(defn upload-to-history
  "Upload a file via URL to a Galaxy history, defaulting to the current."
  [client file-url dbkey ftype & {:keys [history-id]}]
  (run-tool client "upload1"
            {:file-type (name ftype)
             :dbkey (name dbkey)
             "files_0|url_paste" file-url
             "files_0|NAME" (last (string/split file-url #"/"))}
            :history-id history-id))
