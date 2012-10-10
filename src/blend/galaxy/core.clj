(ns blend.galaxy.core
  "Top level Galaxy interaction"
  (:import [com.github.jmchilton.blend4j.galaxy GalaxyInstanceFactory])
  (:require [blend.galaxy.histories :as histories]
            [blend.galaxy.tools :as tools]
            [blend.galaxy.users :as users]))

(defn get-client
  [galaxy-url api-key]
  (GalaxyInstanceFactory/get galaxy-url api-key))

(def get-user-info users/get-user-info)

(defn list-histories
  [client]
  [(histories/get-current-history client)])

(def get-datasets-by-type histories/get-datasets-by-type)
(def get-dataset-by-id histories/get-dataset-by-id)

(def download-dataset histories/download-dataset)
(def upload-to-history tools/upload-to-history)

