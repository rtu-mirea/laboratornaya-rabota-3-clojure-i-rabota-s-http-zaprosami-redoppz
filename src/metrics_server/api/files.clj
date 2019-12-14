(ns metrics-server.api.files
  (:require [metrics-server.core :refer [call-api check-required-params with-collection-format]])
  (:import (java.io File)))

(defn get-files-with-http-info
  "Get files in directory on server"
  []
  (call-api "/files" :get
            {:path-params   {}
             :header-params {}
             :query-params  {}
             :form-params   {}
             :content-types []
             :accepts       []
             :auth-names    []}))

(defn get-files
  "Get files in directory on server"
  []
  (:data (get-files-with-http-info)))

;; Задание 2.1
(defn second_task1 [files] (filter (fn [a] (not (get a :directory))) files))

;; Задание 2.2
(defn second_task2 [files] (filter (fn [a] (not (get a :executable))) files))

;; Задание 2.3
(defn second_task3 [files]
  (map (fn [file] {
          :name (clojure.string/replace (get file :name) #".conf" ".cfg") :size (get file :size) 
		  :changed (get file :changed) :directory (get file :directory) :executable (get file :executable)
        }
        ) files)
  )

;; Задание 3
(defn third_task [files] (/ 
(reduce + (map (fn [file] (get file :size)) 
(filter (fn [a] (not (get a :directory))) files)))
(count (filter (fn [a] (not (get a :directory))) files))
))

;; Главный метод
(defn -main [& args] (println (second_task1 (get-files))) (println (second_task2 (get-files))) (println (second_task3 (get-files))) (println (third_task (get-files))))