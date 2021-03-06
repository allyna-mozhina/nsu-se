; Реализовать решение Ruby1 на языке Clojure.
; Демонстрацию работоспособности обеспечить с 
; помощью модульных тестов. Данное требование 
; относится ко всем последующим задачам.

(ns task4.main)

(defn generate-without-doubles 
  [n, alphabet]
  (let [generate-next (fn [list] (map #(cons % list) (filter #(not= % (first list)) alphabet)))
        permutation (iterate #(mapcat generate-next %) (list '()))]
  (nth permutation n)
  ))

(defn -main
  [& raw-args]
  (println (generate-without-doubles 3 '(a b c))))
