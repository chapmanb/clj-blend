(ns blend.galaxy.core
  "Top level Galaxy interaction"
  (:import [com.github.jmchilton.blend4j.galaxy GalaxyInstanceFactory])
  (:require [blend.galaxy.histories :as histories]
            [blend.galaxy.tools :as tools]))

(defn get-client
  [galaxy-url api-key]
  (GalaxyInstanceFactory/get galaxy-url api-key))

(def get-datasets-by-type histories/get-datasets-by-type)
(def download-dataset histories/download-dataset)
(def upload-to-history tools/upload-to-history)