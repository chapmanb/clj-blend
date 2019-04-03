(ns org.galaxyproject.clj-blend.histories
  "Retrieve Galaxy history information."
  (:use [clojure.java.io])
  (:require [clojure.string :as string]
            [fs.core :as fs]))

(defn- get-history-dataset
  "Retrieve a history dataset converted into a clojure map."
  [hist-client hist ds-id]
  (let [ds (.showDataset hist-client (:id hist) ds-id)]
    {:id (.getId ds)
     :history-id (:id hist)
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
       (map #(get-history-dataset hist-client hist (.getId %)))))

(defn get-dataset-by-id
  "Retrieve a history dataset from history and dataset identifier."
  [client history-id ds-id]
  (let [hist-client (.getHistoriesClient client)]
    (get-history-dataset hist-client {:id history-id} ds-id)))

(defn- is-ftype?
  "Check if a dataset is the given filetype, cleanly handling keywords"
  [ftype dataset]
  (= (keyword ftype)
     (keyword (:data-type dataset))))

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

(defn get-current-history
  "Retrieve current history for API user."
  [client]
  (get-history-most-recently-used client))

(defn get-datasets-by-type
  "Retrieve datasets from the current active history by filetype."
  [client ftype & {:keys [history-id]}]
  (let [hist-client (.getHistoriesClient client)
        hist (if (seq history-id)
               {:id history-id}
               (get-current-history client))]
    (->> hist
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
