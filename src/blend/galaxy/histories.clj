(ns blend.galaxy.histories
  "Retrieve Galaxy history information."
  (:use [clojure.java.io])
  (:require [clojure.string :as string]
            [fs.core :as fs]))

(defn- get-history-dataset
  "Retrieve a history dataset converted into a clojure map."
  [hist-client hist hist-contents]
  (let [ds (.showDataset hist-client (:id hist) (.getId hist-contents))]
    {:id (.getId ds)
     :name (.getName ds)
     :data-type (.getDataType ds)
     :genome-build (.getGenomeBuild ds)
     :file-size (.getFileSize ds)
     :deleted (.getDeleted ds)
     :visible (.getVisible ds)
     :state (.getState ds)
     :download-url (.getDownloadUrl ds)}))

(defn- get-history-datasets
  "Retrieve history datasets, flattened into Clojure maps."
  [hist-client hist]
  (->> (.showHistoryContents hist-client (:id hist))
       (filter #(= (.getType %) "file"))
       (map (partial get-history-dataset hist-client hist))))

(defn- is-ftype?
  "Check if a dataset is the given filetype, cleanly handling keywords"
  [ftype dataset]
  (= (keyword ftype)
     (keyword (:data-type dataset))))

(defn get-current-history
  "Retrieve current history for API user.
   XXX Needs update when API handles current retrieval."
  [client]
  (letfn [(history-to-map [x]
            {:id (.getId x)
             :name (.getName x)})]
    (-> client
        .getHistoriesClient
        .getHistories
        first
        history-to-map)))

(defn get-datasets-by-type
  "Retrieve datasets from the current active history by filetype."
  [client ftype]
  (let [hist-client (.getHistoriesClient client)]
    (->> (get-current-history client)
         (get-history-datasets hist-client)
         (filter (partial is-ftype? ftype)))))

(defn get-base-url [client]
  (-> client
      .getWebResource
      .getURI
      .toString
      (string/split #"/api")
      first))

(defn download-dataset
  "Retrieve remote history dataset to a local file or directory"
  [client dataset path]
  (let [base-url (get-base-url client)
        fname (if (fs/directory? path)
                (str (file path (:name dataset)))
                path)]
    (when (or (not (fs/exists? fname))
              (not= (fs/size fname) (:file-size dataset)))
      (with-open [rdr (reader (str base-url (:download-url dataset)))]
        (copy rdr (file fname))))
    fname))
