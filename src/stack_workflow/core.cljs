
(ns stack-workflow.core
  (:require [respo.core :refer [render! clear-cache!]]
            [stack-workflow.comp.container :refer [comp-container]]
            [cljs.reader :refer [read-string]]
            [stack-workflow.updater :refer [updater]]))

(def initial-store {:selected nil, :id nil, :data []})

(defonce store-ref (atom initial-store))

(defn dispatch! [op op-data]
  (let [new-store (updater @store-ref op op-data)]
    (reset! store-ref new-store)))

(defonce states-ref (atom {}))

(defn render-app! []
  (let [target (.querySelector js/document "#app")]
    (render! (comp-container @store-ref) target dispatch! states-ref)))

(defn on-jsload []
  (clear-cache!)
  (render-app!)
  (println "code update."))

(defn -main []
  (enable-console-print!)
  (render-app!)
  (add-watch store-ref :changes render-app!)
  (add-watch states-ref :changes render-app!)
  (println "app started!"))

(set! (.-onload js/window) -main)
