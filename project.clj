(defproject clj-blend "0.2.0-SNAPSHOT"
  :description "Clojure library for interacting with the Galaxy Bioinformatics Workflow Platform"
  :url "http://github.com/chapmanb/clj-blend"
  :license {:name "MIT"
            :url "http://www.opensource.org/licenses/mit-license.html"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [http-kit "2.3.0"]
                 [org.clojure/data.json "0.2.6"]
                 [org.clojure/data.codec "0.1.1"]
                 [fs "1.3.2"]]
  :profiles {:dev {:dependencies [[reloaded.repl "0.2.4"]]}})
