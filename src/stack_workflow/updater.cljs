
(ns stack-workflow.updater
  (:require [clojure.string :as string]))

(def nouns
 (string/split
   "table,chair,house,bbq,desk,car,pony,cookie,sandwich,burger,pizza,mouse,keyboard"
   ","))

(def id-ref (atom 0))

(defn get-id [] (swap! id-ref inc) @id-ref)

(defn -random [max]
  (.round js/Math (mod (* 1000 (.random js/Math)) max)))

(def adjectives
 (string/split
   "pretty,large,big,small,tall,short,long,handsome,plain,quaint,clean,elegant,easy,angry,crazy,helpful,mushy,odd,unsightly,adorable,important,inexpensive,cheap,expensive,fancy"
   ","))

(def colours
 (string/split
   "red,yellow,blue,green,pink,brown,purple,brown,white,black,orange"
   ","))

(defn build-data [length]
  (into
    []
    (->>
      (repeat length 0)
      (map
        (fn [zero]
          {:label
           (str
             (get adjectives (-random (count adjectives)))
             " "
             (get colours (-random (count colours)))
             " "
             (get nouns (-random (count nouns)))),
           :id (get-id)})))))

(defn updater [store op op-data]
  (case
    op
    :run
    store
    :add
    (-> store (assoc :selected nil) (assoc :data (build-data 1000)))
    :update
    store
    :select
    store
    :run-lots
    store
    :clear
    store
    :swap-rows
    store
    store))
