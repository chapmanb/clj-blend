(ns blend.galaxy.tools
  "Run Galaxy tools through the remote API"
  (:import [com.github.jmchilton.blend4j.galaxy.beans ToolInputs])
  (:require [clojure.string :as string]
            [blend.galaxy.histories :as histories]))

(defn run-tool
  "Run a remote tool on Galaxy server"
  [client tool-id params]
  (let [clean-params (reduce (fn [coll [k v]]
                               (assoc coll (string/replace (name k) "-" "_") v))
                             {} params)
        tool-client (.getToolsClient client)]
    (.create tool-client (histories/get-current-history client)
             (ToolInputs. tool-id clean-params))))

(defn upload-to-history
  "Upload a file to the current Galaxy history via URL."
  [client file-url dbkey ftype]
  (run-tool client "upload1"
            {:file-type (name ftype)
             :dbkey (name dbkey)
             "files_0|url_paste" file-url
             "files_0|NAME" (last (string/split file-url #"/"))}))
