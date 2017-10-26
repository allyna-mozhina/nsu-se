; Реализовать решение Ruby1 на языке Clojure.
; Демонстрацию работоспособности обеспечить с 
; помощью модульных тестов. Данное требование 
; относится ко всем последующим задачам.

(ns task4.main)

(defn generate_without_doubles 
  [n, alphabet]
  (let [generate_next (fn [list] (map #(cons % list) (filter #(not= % (first list)) alphabet)))
        permutation (iterate #(mapcat generate_next %) (list '()))]
  (nth permutation n)
  ))

(defn -main
  [& raw-args]
  (println (generate_without_doubles 2 (list "a" "b" "c"))))
