
(ns stack-workflow.comp.container
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo.alias :refer [create-comp create-element div span]]
            [respo.comp.space :refer [comp-space]]
            [respo.comp.text :refer [comp-text]]
            [respo.comp.debug :refer [comp-debug]]
            [stack-workflow.comp.header :refer [comp-header]]
            [stack-workflow.comp.row :refer [comp-row]]))

(defn table [props & children] (create-element :table props children))

(defn tbody [props & children] (create-element :tbody props children))

(defn render [store]
  (fn [state mutate!]
    (div
      {:attrs {:class-name "container"}}
      (comp-header)
      (comment comp-debug store)
      (table
        {:attrs
         {:class-name "table table-hover table-striped test-data"}}
        (tbody
          {}
          (->>
            (:data store)
            (map
              (fn [row-data] [(:id row-data)
                              (comp-row
                                row-data
                                (if
                                  (= (:id row-data) (:selected store))
                                  "danger"
                                  ""))])))))
      (span
        {:attrs
         {:aria-hidden "true",
          :class-name "preloadicon glyphicon glyphicon-remove"}}))))

(def comp-container (create-comp :container render))
