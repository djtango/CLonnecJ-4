(defproject clonnecj-4 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure          "1.7.0"]
                 [compojure                    "1.4.0"]
                 [ring/ring-defaults           "0.1.5"]]
  :plugins      [[lein-ring                    "0.9.7"]
                 [speclj                       "3.3.1"]]
  :ring {:handler clonnecj-4.handler/app}
  :profiles
  {:dev-local {:dependencies [[javax.servlet/servlet-api "2.5"]
                              [ring/ring-mock "0.3.0"]
                              [speclj         "3.3.1"]]
               }
   :test-local {:dependencies [[javax.servlet/servlet-api "2.5"]
                               [ring/ring-mock "0.3.0"]
                               [speclj         "3.3.1"]]}
   :dev-env-vars {}
   :test-env-vars {}

   :test [:test-local :test-env-vars]
   :dev [:dev-local :dev-env-vars]}
  :test-paths ["spec"])
