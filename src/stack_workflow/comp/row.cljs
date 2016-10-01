
(ns stack-workflow.comp.row
  (:require [respo.alias :refer [create-comp create-element a span]]))

(defn td [props & children] (create-element :td props children))

(defn on-click [id] (fn [e dispatch!] (dispatch! :select id)))

(defn tr [props & children] (create-element :tr props children))

(defn on-delete [id] (fn [e dispatch!] (dispatch! :delete id)))

(defn render [data style-class]
  (fn [state mutate!]
    (tr
      {:attrs {:class-name style-class}}
      (td
        {:attrs
         {:inner-text (str (:id data)), :class-name "col-md-1"}})
      (td
        {:attrs {:class-name "col-md-4"}}
        (a
          {:event {:click (on-click (:id data))},
           :attrs {:inner-text (:label data)}}))
      (td
        {:attrs {:class-name "col-md-1"}}
        (a
          {:event {:click (on-delete (:id data))}}
          (span
            {:attrs
             {:aria-hidden "true",
              :class-name "glyphicon glyphicon-remove"}})))
      (td {:attrs {:class-name "col-md-6"}}))))

(def comp-row (create-comp :row render))
