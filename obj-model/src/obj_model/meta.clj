(ns obj-model.meta)

(def class-hierarchy (ref {}))

(dosync
  (alter 
    class-hierarchy
    (fn [ch]
      (assoc 
        ch :Object
        {::type :Object
         ::super nil
         ::fields #{}}))))

(defmacro def-class 
  [name & sections]
  (let [s-map (apply assoc 
                  (cons {} (mapcat identity sections)))
        super-class (or (s-map :super) :Object)
        fields (set (s-map :fields))] 
        `(dosync
          (alter
            class-hierarchy
            (fn [ch#]
              (assoc 
                ch# ~name
                {::type ~name
                 ::super ~super-class
                 ::fields ~fields}))))))

(defn -main 
  [& raw-args]  
  (println @class-hierarchy)
  (println (macroexpand `(def-class :Animal (:fields (:name)))))
  (def-class :Animal 
    (:fields (:name)))
  (def-class :Cat 
    (:super :Animal)
    (:fields (:breed)))
  (println @class-hierarchy))
