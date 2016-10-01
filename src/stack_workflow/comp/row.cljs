
(ns stack-workflow.comp.row
  (:require [respo.alias :refer [create-comp create-element a span]]))

(defn render [data on-click on-delete style-class]
  (fn [state mutate!] (span {})))

(def comp-row (create-comp :row render))
