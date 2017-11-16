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
  [expr & rest]
  (cons ::and (cons expr rest)))

(defn conjunc?
  [expr]
  (= ::and (first expr)))

(defn disjunc
  [expr & rest]
  (cons ::or (cons expr rest)))

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
