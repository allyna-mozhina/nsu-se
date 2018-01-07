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
  "Class definition macro. Adds a map containing class information to class hierarchy."
  [name & sections]
  {:pre (keyword? name)}
  (let [s-map (if (empty? sections)
                {}
                (apply assoc 
                  (cons {} (mapcat identity sections))))
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

(defn super-class
  "Returns parent class of given class."
  [class]
  {:pre (keyword? class)}
  (if (contains? @class-hierarchy class)
    ((@class-hierarchy class) ::super)
    (throw (Exception. "Unknown class."))))

; (defn has-field?
;   "Checks whether class contains field including super classes."
;   [class field]
;   {:pre [(keyword? class) (keyword? field)]}
;   )

(def mixins-methods (ref {}))

 (defmacro enable-method
   [name mixin]
   (let [method-desc ((mixins-methods mixin) (keyword name))]
    `(defn ~name ~(method-desc ::args)
      ~@(method-desc ::body))))

(defmacro def-mixin-method
    [name mixin args & body]
    (let [method-desc {::args args ::body body}
          curr-mixin-methods (mixins-methods mixin)]
    (dosync
      (alter mixins-methods 
        (fn [mm] 
          (assoc mm mixin 
            (assoc curr-mixin-methods (keyword name) method-desc)))))
    ()))

(defn -main 
  [& raw-args] 
  ;(println (macroexpand `(def-mixin-method locate :GPSHolder [obj]
    ;(println "Hello, position of" obj "!"))))
  (def-mixin-method locate :GPSHolder [obj]
    (println "Hello, position of" obj"!")
    (println "Bye," obj"!"))
  (enable-method locate :GPSHolder)
  (locate "Kitty"))
  ; (println @class-hierarchy)
  ; (println (macroexpand `(def-class :Animal (:fields (:name)))))
  ; (def-class :Animal 
  ;   (:fields (:name)))
  ; (def-class :Cat 
  ;   (:super :Animal)
  ;   (:fields (:breed)))
  ; (println @class-hierarchy))
