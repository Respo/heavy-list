
(ns stack-workflow.measure)

(def last-measure (atom nil))

(def start-time (atom nil))

(defn start-measure [name']
  (reset! start-time (.now js/performance))
  (reset! last-measure name'))

(defn stop-measure []
  (if (some? @last-measure)
    (.setTimeout
      js/window
      (fn []
        (let [stop (.now js/performance) duration 0]
          (.log
            js/console
            (str @last-measure " took " (- stop @start-time))))
        (reset! last-measure nil)))))
