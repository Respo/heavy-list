
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
    (-> store (assoc :selected nil) (assoc :data (build-data 1000)))
    :add
    (-> store
     (update
       :data
       (fn [old-data] (into [] (concat old-data (build-data 1000))))))
    :update
    (-> store
     (update
       :data
       (fn [old-data]
         (->>
           old-data
           (map-indexed
             (fn [idx row]
               (if (zero? (mod idx 10))
                 (update row :label (fn [text] (str text " !!!")))
                 row)))
           (into [])))))
    :select
    (assoc store :selected op-data)
    :run-lots
    (-> store (assoc :selected nil) (assoc :data (build-data 10000)))
    :clear
    (-> store (assoc :selected nil) (assoc :data []))
    :delete
    (-> store
     (update
       :data
       (fn [old-data]
         (->>
           old-data
           (filter (fn [row] (not= (:id row) op-data)))
           (into [])))))
    :swap-rows
    (-> store
     (update
       :data
       (fn [old-data]
         (if (> (count old-data) 10)
           (-> old-data
            (assoc 4 (get old-data 9))
            (assoc 9 (get old-data 4)))
           old-data))))
    store))
