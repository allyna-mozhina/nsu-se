; По аналогии с задачей дифференцирования, рассматриваемой в [1,3]
; реализовать представление символьных булевых выражений с
; операциями конъюнкции, дизъюнкции, отрицания, импликации.
; Выражения могут включать как булевы константы, так и переменные.
; Реализовать подстановку значения переменной в выражение с его 
; приведением к ДНФ. Код должен быть покрыт тестами, публичный
; программный интерфейс документирован. 

(ns task7.main)

(defn const 
  [bool]
  {:pre [(or (= 0 bool) (= 1 bool))]}
  (list ::const bool))

(defn const?
  [expr]
  (= ::const (first expr)))

(defn const-val
  [const]
  (second const))

(defn variable
  [name]
  {:pre [(keyword? name)]}
  (list ::var name))

(defn variable?
  [expr]
  (= ::var (first expr)))

(defn conjunc
  [lexpr rexpr]
  (cons ::and (cons lexpr rexpr)))

(defn conjunc?
  [expr]
  (= ::and (first expr)))

(defn disjunc
  [lexpr rexpr]
  (cons ::or (cons lexpr rexpr)))

(defn disjunc?
  [expr]
  (= ::or (first expr)))

(defn negat
  [expr]
  (list ::neg expr))

(defn negat?
  [expr]
  (= ::neg (first expr)))

(defn then
  [premise conclusion]
  (cons ::then (cons premise conclusion)))

(defn then?
  [expr]
  (= ::then (first expr)))

(defn args
  [expr]
  (rest expr))

(def dnf-rules
  (list
    [(fn [expr] (const? expr)) identity]
    [(fn [expr] (variable? expr)) identity]
    [(fn [expr] 
       (if (negat? expr)
         (or
           (variable? (args expr))
           (const? (args expr))
         false)) identity]
    [(fn [expr] (disjunc? expr))
     (fn [expr]
       (apply disjunc (map convert-to-dnf (expr args))))]
    [(fn [expr] 
       (if 
         (and
           (negat? expr)
           (negat? (args expr)))))
     (fn [expr] (convert-to-dnf (args (args expr))))]
    [(fn [expr] (then? expr))
     (fn [expr] 
       (let [arg (args expr)] 
         (disjunc 
           (convert-to-dnf (negat (first arg))) 
           (convert-to-dnf (second arg)))))]
    [(fn [expr]
       (if (negat? expr)
         (not 
           (or 
             (variable? (args expr))
             (const? (args expr))
             (negat? (args expr))))
         false))
     (fn [expr]
       (let [negated (convert-to-dnf (args expr))
             neg-args (map negat (args negated))]
       (if (conjunc? negated)
         (apply disjunc neg-args)
         (apply conjunc neg-args)))))]
    [(fn [expr] 
       (and
         (conjunc? expr)
         ()
         (not (co))))]))

