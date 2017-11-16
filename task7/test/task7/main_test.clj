(ns task7.main-test
  (:require [clojure.test :refer :all]
            [task7.main :refer :all]))

(deftest bool-expr-construction-test
  (testing "Testing boolean expressions construction correctness"
    (is (const? (const 1)))
    (is (= 0 (const-val (const 0))))
    (is (variable? (variable :a)))
    (is (conjunc? (conjunc (variable :a) (const 0))))
    (is (disjunc? (disjunc (variable :a) (variable :b) (const 1))))
    (is (negat? (negat (variable :c))))
    (is (then? (then (const 0) (const 1))))))
